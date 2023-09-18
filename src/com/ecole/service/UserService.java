package com.ecole.service;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.hibernate.Session;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.security.Identity;

import com.chaka.projet.entity.Profile;
import com.chaka.projet.entity.Utilisateur;
import com.chaka.projet.entity.UtilisateurSecurise;
import com.chaka.projet.service.ProfileList;
import com.chaka.projet.service.utils.ServiceCryptage;
import com.ecole.entity.AnneeScolaire;
import com.ecole.entity.Classe;
import com.ecole.entity.ClasseEnseignant;
import com.ecole.entity.Niveau;
import com.google.gdata.data.dublincore.Date;

@Scope(ScopeType.SESSION)
@Name("userService")
public class UserService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@In
	private Session dataSource;

	@In(required = false)
	@Out(required = false)
	private Utilisateur utilisateur;

	private List<Profile> lstProfiles = new ArrayList<Profile>();

	private List<Utilisateur> listeUser = new ArrayList<Utilisateur>();

	private String password = "";
	private String password2 = "";
	private String password1 = "";

	private String login;

	private String mail;

	@In
	private AnneeScolaire annee;
	private String ens;

	private Integer nbreUsers = null;
	private List<ClasseEnseignant> listeClEn = new ArrayList<ClasseEnseignant>();
	private List<Niveau> listeNiveau = new ArrayList<Niveau>();

	private List<Classe> listeClasse = new ArrayList<Classe>();

	private Classe classe = new Classe();
	private Niveau niveau = new Niveau();

	private ClasseEnseignant clen = new ClasseEnseignant();

	/**
	 * Boolean indiquant si l'utilisateur est deja loggué.
	 */
	private boolean loggedIn;

	public String versCreerUser() {
		chargerListeUser();
		return "/pages/nuramecole/creerutilisateur.xhtml";
	}

	public void annulerajout() {
		this.setUser(new Utilisateur());
	}

	@SuppressWarnings("unchecked")
	public void listeClasseEnseignant(Utilisateur u) {
		clen = new ClasseEnseignant();
		listeNiveau = new ArrayList<Niveau>();
		listeNiveau = dataSource.createQuery("From Niveau ").list();
		this.setEns(u.getNomComplet());
		clen.setEnseignant(u);
		listeClEn = new ArrayList<ClasseEnseignant>();
		listeClEn = dataSource
				.createQuery("From ClasseEnseignant cl inner join fetch cl.classe inner join fetch cl.enseignant en "
						+ " inner join fetch cl.annee an where an =:pan and en =:pen ")
				.setParameter("pan", annee).setParameter("pen", u).list();
	}

	@SuppressWarnings("unchecked")
	public void ajouterClasse() {
        clen.setAnnee(annee);
		clen.setClasse(classe);
		dataSource.save(clen);
		listeClEn = new ArrayList<ClasseEnseignant>();
		listeClEn = dataSource
				.createQuery("From ClasseEnseignant cl inner join fetch cl.classe inner join fetch cl.enseignant en "
						+ " inner join fetch cl.annee an where an =:pan and en =:pen ")
				.setParameter("pan", annee).setParameter("pen", clen.getEnseignant()).list();

	}

	@SuppressWarnings("unchecked")
	public void chargerListeClasse() {
		listeClasse = new ArrayList<Classe>();
		listeClasse = dataSource.createQuery(" From Classe c inner join fetch c.niveau n where n=:pn")
				.setParameter("pn", niveau).list();
	}

	@SuppressWarnings("unchecked")
	public void chargerListeUser() {
		listeUser = new ArrayList<Utilisateur>();
		listeUser = dataSource.createQuery(" from Utilisateur u  ").list();
	}

	public Boolean verifiePwd() {
		if (this.user.getPassword().isEmpty() || this.password.isEmpty()
				|| !this.user.getPassword().equalsIgnoreCase(password))
			return false;
		else
			return true;
	}

	public void bloquerDebloquerUser(String context, Utilisateur user) {
		Utilisateur u = (Utilisateur) dataSource.get(Utilisateur.class, user.getIdUtilisateur());

//		if (context.equalsIgnoreCase("de")) {
//			u.setBloquer(false);
//		} else {
//			u.setBloquer(true);
//		}
		dataSource.update(u);
		chargerListeUser();

	}

	public String modifierPwd() {
		if (utilisateur.getPassword().isEmpty()) {
			FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique",
					"Veuillez renseigner le mot de passe");
			return null;
		} else {

			Utilisateur user1 = (Utilisateur) dataSource.get(Utilisateur.class, utilisateur.getIdUtilisateur());
			if (user1 == null) {
				FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique", "Utilisateur inexistant ");
			} else {
				Calendar cal = Calendar.getInstance();
				cal.setTime(new java.util.Date());
				String password = utilisateur.getPassword();
				user1.setActif(true);
				user1.setInitPass(true);
				dataSource.update(user1);
				FacesMessages.instance().addToControlFromResourceBundle("infoGenerique",
						"Opération Effectué avec succès: ");

				// préparation du password :
				UtilisateurSecurise us = (UtilisateurSecurise) dataSource.get(UtilisateurSecurise.class,
						user1.getIdUtilisateur());
				ServiceCryptage sc = new ServiceCryptage();
				try {
					byte[] salt = sc.generateSalt();
					byte[] securePass = sc.getEncryptedPassword(password, salt);
					us.setSalt(salt);
					us.setSecurePassword(securePass);
					dataSource.update(us);
					dataSource.flush();
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				} catch (InvalidKeySpecException e) {
					e.printStackTrace();
				}

				this.setUser(new Utilisateur());

			}
			return "/pages/home/accueil.xhtml";
		}

	}

	public void ajouterUtilisateur() {
		Boolean res = true;
		if (user.getIdUtilisateur() == null) {
			res = verifiePwd();
		}
		if (res == false) {
			FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique",
					"Les deux mots de passe ne sont pas identiques");
			return;
		}
		if (this.user.getEmail().isEmpty()) {
			FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique",
					"l'adresse mail est obligatoire");
			return;
		}
		if (this.user.getNom().isEmpty()) {
			FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique", "le nom est obligatoire");
			return;
		}
		if (this.user.getLogin().isEmpty()) {
			FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique", "le login est obligatoire");
			return;
		}
		String password = this.user.getPassword();
		if (this.user.getIdUtilisateur() == null) {
			this.user.setActif(true);
			// this.user.setBloquer(false);
			user.setInitPass(true);
			dataSource.save(this.user);
			dataSource.flush();
			FacesMessages.instance().addToControlFromResourceBundle("infoGenerique", "Opération Effectué avec succès");
			// préparation du password :
			UtilisateurSecurise us = (UtilisateurSecurise) dataSource.get(UtilisateurSecurise.class,
					user.getIdUtilisateur());
			ServiceCryptage sc = new ServiceCryptage();
			try {
				byte[] salt = sc.generateSalt();
				byte[] securePass = sc.getEncryptedPassword(password, salt);
				us.setSalt(salt);
				us.setSecurePassword(securePass);
				dataSource.update(us);
				dataSource.flush();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (InvalidKeySpecException e) {
				e.printStackTrace();
			}
			this.setUser(new Utilisateur());
		} else {

			// préparation du password :
			UtilisateurSecurise us = (UtilisateurSecurise) dataSource.get(UtilisateurSecurise.class,
					user.getIdUtilisateur());
			ServiceCryptage sc = new ServiceCryptage();
			try {
				password = "passer";
				byte[] salt = sc.generateSalt();
				byte[] securePass = sc.getEncryptedPassword(password, salt);
				us.setSalt(salt);
				us.setSecurePassword(securePass);
				dataSource.update(us);
				dataSource.flush();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (InvalidKeySpecException e) {
				e.printStackTrace();
			}

			user.setInitPass(false);
			dataSource.merge(this.user);
			FacesMessages.instance().addToControlFromResourceBundle("infoGenerique", "Opération Effectué avec succès");
			this.setUser(new Utilisateur());
		}
		chargerListeUser();
	}

	public void modifierMotdePasseUser() {
		if (this.user.getPassword().isEmpty()) {
			String password = this.user.getPassword();
			user.setActif(true);
			dataSource.update(user);
			dataSource.flush();
			FacesMessages.instance().addToControlFromResourceBundle("infoGenerique",
					"Opération Effectué avec succès: Veuillez vous déconnecter est cliquer sur le lien qui est envoyé dans votre adresse mail");
			// préparation du password :
			UtilisateurSecurise us = (UtilisateurSecurise) dataSource.get(UtilisateurSecurise.class,
					user.getIdUtilisateur());
			ServiceCryptage sc = new ServiceCryptage();
			try {
				byte[] salt = sc.generateSalt();
				byte[] securePass = sc.getEncryptedPassword(password, salt);
				us.setSalt(salt);
				us.setSecurePassword(securePass);
				dataSource.update(us);
				dataSource.flush();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (InvalidKeySpecException e) {
				e.printStackTrace();
			}

			this.setUser(new Utilisateur());

		} else {
			FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique", "Mot de passe obligatoire ");
		}
	}

	public void modifierMotdePasse() {
		StringBuffer hql = new StringBuffer();
		hql.append(" From Utilisateur u where u.login=:plog  and u.email=:pmail");
		Utilisateur user1 = (Utilisateur) dataSource.createQuery(hql.toString()).setParameter("plog", user.getLogin())
				.setParameter("pmail", user.getEmail()).uniqueResult();
		if (user1 == null) {
			FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique", "Utilisateur inexistant ");
		} else {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new java.util.Date());
			this.user.setPassword("passer" + "gede" + cal.getTimeInMillis());
			String password = this.user.getPassword();
			user1.setActif(true);
			user1.setInitPass(false);
			dataSource.update(user1);
			dataSource.flush();
			FacesMessages.instance().addToControlFromResourceBundle("infoGenerique",
					"Opération Effectué avec succès: Veuillez vous déconnecter est cliquer sur le lien qui est envoyé dans votre adresse mail");
			SendMail sendM = new SendMail();
			String mes = " Mme/Mr. " + user1.getPrenom() + user1.getNom() + " / "
					+ " votre nouveau  mot de passe est   " + user.getPassword()
					+ ". Vous devez le changer lors de votre prochaine connexion \n lien: http://www.gede.sn";
			sendM.sendMail(user1.getEmail(), mes, " Notification réinitialisation mot de passe  ");
			// préparation du password :
			UtilisateurSecurise us = (UtilisateurSecurise) dataSource.get(UtilisateurSecurise.class,
					user1.getIdUtilisateur());
			ServiceCryptage sc = new ServiceCryptage();
			try {
				byte[] salt = sc.generateSalt();
				byte[] securePass = sc.getEncryptedPassword(password, salt);
				us.setSalt(salt);
				us.setSecurePassword(securePass);
				dataSource.update(us);
				dataSource.flush();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (InvalidKeySpecException e) {
				e.printStackTrace();
			}

			this.setUser(new Utilisateur());

		}
	}

	public String versmodifierMotdePasse() {

		return "/pages/asufor/modifPwd.xhtml";
	}

	public void modifierUtilisateur(Utilisateur user) {
		this.user = (Utilisateur) dataSource.get(Utilisateur.class, user.getIdUtilisateur());

	}

	public void supprimerUtilisateur(Utilisateur user) {
		try {
			user.setActif(false);
			dataSource.merge(user);
			chargerListeUser();
			FacesMessages.instance().addToControlFromResourceBundle("infoGenerique", "Opération Effectué avec succès");
		} catch (Exception e) {
			FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique",
					"Cette utilisateur ne peux pas etre supprimer");
		}

	}

	private Utilisateur user = new Utilisateur();

	public Utilisateur getUser() {
		return user;
	}

	public void setUser(Utilisateur user) {
		this.user = user;
	}

	@SuppressWarnings("unchecked")
	public List<Profile> getLstProfiles() {
		lstProfiles = dataSource.createQuery(" from Profile").list();
		return lstProfiles;
	}

	public void setLstProfiles(List<Profile> lstProfiles) {

		this.lstProfiles = lstProfiles;
	}

	public List<Utilisateur> getListeUser() {
		return listeUser;
	}

	public void setListeUser(List<Utilisateur> listeUser) {
		this.listeUser = listeUser;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public String getPassword1() {
		return password1;
	}

	public void setPassword1(String password1) {
		this.password1 = password1;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public Session getDataSource() {
		return dataSource;
	}

	public void setDataSource(Session dataSource) {
		this.dataSource = dataSource;
	}

	public AnneeScolaire getAnnee() {
		return annee;
	}

	public void setAnnee(AnneeScolaire annee) {
		this.annee = annee;
	}

	public String getEns() {
		return ens;
	}

	public void setEns(String ens) {
		this.ens = ens;
	}

	public Integer getNbreUsers() {
		return nbreUsers;
	}

	public void setNbreUsers(Integer nbreUsers) {
		this.nbreUsers = nbreUsers;
	}

	public List<ClasseEnseignant> getListeClEn() {
		return listeClEn;
	}

	public void setListeClEn(List<ClasseEnseignant> listeClEn) {
		this.listeClEn = listeClEn;
	}

	public List<Niveau> getListeNiveau() {
		return listeNiveau;
	}

	public void setListeNiveau(List<Niveau> listeNiveau) {
		this.listeNiveau = listeNiveau;
	}

	public List<Classe> getListeClasse() {
		return listeClasse;
	}

	public void setListeClasse(List<Classe> listeClasse) {
		this.listeClasse = listeClasse;
	}

	public Classe getClasse() {
		return classe;
	}

	public void setClasse(Classe classe) {
		this.classe = classe;
	}

	public Niveau getNiveau() {
		return niveau;
	}

	public void setNiveau(Niveau niveau) {
		this.niveau = niveau;
	}

	public ClasseEnseignant getClen() {
		return clen;
	}

	public void setClen(ClasseEnseignant clen) {
		this.clen = clen;
	}

}
