/**
 * 
 */
package com.ecole.service;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.hibernate.Session;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;
import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;

import com.chaka.projet.entity.Profile;
import com.chaka.projet.entity.Utilisateur;
import com.chaka.projet.entity.UtilisateurSecurise;
import com.chaka.projet.service.utils.ServiceCryptage;
import com.ecole.entity.Institution;
import com.ecole.entity.SendMail;

/**
 * @author A626257
 * 
 */
@Name("instService")
@Scope(ScopeType.SESSION)
public class InstitutionService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Institution institution = new Institution();

	private List<Institution> listeIns = new ArrayList<Institution>();

	private Utilisateur user = new Utilisateur();

	private String password = "";

	@In
	private Session dataSource;

	@In(required = false)
	@Out(required = false)
	private Utilisateur utilisateur;

	public Institution getInstitution() {
		return institution;
	}

	@SuppressWarnings("unchecked")
	public String versInstitution() {
		chargerListeInst();
		return "/pages/nuramecole/institution.xhtml";
	}

	public void annulerajoutAdmin() {
		this.setUser(new Utilisateur());
	}

	public Boolean verifiePwd() {
		if (this.user.getPassword().isEmpty() || this.password.isEmpty()
				|| !this.user.getPassword().equalsIgnoreCase(password))
			return false;
		else
			return true;
	}

	public Boolean verifieLogin(String login) {
		boolean exist = false;
		Utilisateur u = (Utilisateur) dataSource
				.createQuery("From Utilisateur u where login=:plogin")
				.setParameter("plogin", login).uniqueResult();
		if (u != null)
			exist = true;
		return exist;
	}

	public void ajouterInstitution() {
		if (institution.getId() == null) {
			institution.setActif(true);
			dataSource.save(institution);
		} else {
			dataSource.update(institution);

		}
		chargerListeInst();
		this.setInstitution(new Institution());
		FacesMessages.instance().addToControlFromResourceBundle(
				"infoGenerique", "Institution ajoutée avec succés");
	}

	public String getAdmin(Institution inst) {
		Utilisateur u = (Utilisateur) dataSource
				.createQuery(
						"From Utilisateur u inner join fetch u.profile p inner join fetch u.institution i where i=:pi"
								+ " and p.libelle_court=:plib ")
				.setParameter("pi", inst).setParameter("plib", "ad")
				.uniqueResult();
		if (u != null) {
			return u.getNomComplet();
		} else {
			return "";
		}
	}

	public Profile getProfileAdmin() {
		return (Profile) dataSource
				.createQuery("From Profile p where libelle_court=:plib")
				.setParameter("plib", "ad").uniqueResult();
	}

	public String modifierPwd() {
		if (utilisateur.getPassword().isEmpty()) {
			FacesMessages.instance().addToControlFromResourceBundle(
					"erreurGenerique", "Veuillez renseigner le mot de passe");
			return null;
		} else {

			Utilisateur user1 = (Utilisateur) dataSource.get(Utilisateur.class,
					utilisateur.getIdUtilisateur());
			if (user1 == null) {
				FacesMessages.instance().addToControlFromResourceBundle(
						"erreurGenerique", "Utilisateur inexistant ");
			} else {
				Calendar cal = Calendar.getInstance();
				cal.setTime(new java.util.Date());
				String password = utilisateur.getPassword();
				user1.setActif(true);
				user1.setInitPass(true);
				dataSource.update(user1);
				FacesMessages.instance().addToControlFromResourceBundle(
						"infoGenerique", "Opération Effectué avec succès: ");

				// préparation du password :
				UtilisateurSecurise us = (UtilisateurSecurise) dataSource.get(
						UtilisateurSecurise.class, user1.getIdUtilisateur());
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
			
			if (utilisateur.getProfile().getLibelle_court()
					.equalsIgnoreCase("ad")
					|| utilisateur.getProfile().getLibelle_court()
							.equalsIgnoreCase("adg")
					|| utilisateur.getProfile().getLibelle_court()
							.equalsIgnoreCase("pr")
					|| utilisateur.getProfile().getLibelle_court()
							.equalsIgnoreCase("se"))
				
				return "/pages/accueil.xhtml";
			if (utilisateur.getProfile().getLibelle_court()
					.equalsIgnoreCase("el"))
				return "/pages/ecole/utilisateurEcole/eleve.xhtml";
			if (utilisateur.getProfile().getLibelle_court()
					.equalsIgnoreCase("Tu"))
				return "/pages/ecole/utilisateurEcole/titeur.xhtml";
			return "";
		}
	}

	@SuppressWarnings("unchecked")
	public void ajouterAdmin() {
		Boolean res = true;
		if (user.getIdUtilisateur() == null) {
			res = verifiePwd();
		}
		if (res == false) {
			FacesMessages.instance().addToControlFromResourceBundle(
					"erreurGenerique",
					"Les deux mots de passe ne sont pas identiques");
			return;
		}
		if (this.user.getEmail().isEmpty()) {
			FacesMessages.instance().addToControlFromResourceBundle(
					"erreurGenerique", "l'adresse mail est obligatoire");
			return;
		}
		if (verifieLogin(user.getLogin())) {
			FacesMessages.instance().addToControlFromResourceBundle(
					"erreurGenerique", "Ce login exite déja");
			return;
		}
		String password = this.user.getPassword();
		if (this.user.getIdUtilisateur() == null) {
			this.user.setActif(true);
			//user.setInstitution(institution);
			user.setProfile(getProfileAdmin());
			dataSource.save(this.user);
			dataSource.flush();
			FacesMessages.instance().addToControlFromResourceBundle(
					"infoGenerique", "Opération Effectué avec succès");
			// préparation du password :
			UtilisateurSecurise us = (UtilisateurSecurise) dataSource.get(
					UtilisateurSecurise.class, user.getIdUtilisateur());
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
			SendMail sendM = new SendMail();
			String mes = " Mme/Mr. "
					+ user.getPrenom()
					+ user.getNom()
					+ " / "
					+ " Votre compte est créé dans e-school. \n Votre  mot de passe est   "
					+ user.getPassword()
					+ ". Vous devez le changer lors de votre premier connexion";
			sendM.sendMail(user.getEmail(), mes,
					" Notification de création de compte   ");
			this.setUser(new Utilisateur());
		} else {
			user.setInitPass(false);
			dataSource.merge(this.user);
			FacesMessages.instance().addToControlFromResourceBundle(
					"infoGenerique", "Opération Effectué avec succès");
			this.setUser(new Utilisateur());
		}

	}

	public void paintLogo(OutputStream stream, Object object)
			throws IOException {
		Institution inst = null;
		if (stream == null || object == null) {
			return;
		}
		Long id = (Long) object;
		inst = (Institution) dataSource.get(Institution.class, id);
		try {
			// byte[] img = (byte[]) object;
			if (inst != null && inst.getLogo() != null) {
				stream.write(inst.getLogo());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new IOException(e.getLocalizedMessage());
		}

	}

	public String etatInstitution(Institution inst) {
		if (inst.isActif())
			return "Actif";
		if (!inst.isActif())
			return "Inactif";
		return "";
	}

	public void annulerAjout() {
		this.setInstitution(new Institution());
	}

	@SuppressWarnings("unchecked")
	public void chargerListeInst() {
		listeIns = new ArrayList<Institution>();
		listeIns = dataSource.createQuery("From Institution i").list();
	}

	public void ajouterAdmin(Institution ins) {
		this.setInstitution(ins);
	}

	public void modifierInst(Institution ins) {
		this.setInstitution(ins);
	}

	/**
	 * @param event
	 *            .
	 */
	public void fileUploadListener(UploadEvent event) {
		if (event == null) {
			// logger.warn("Invalid upload event");
		} else {
			// on recupere l'item envoye
			UploadItem uploadItem = event.getUploadItem();

			String fileName = uploadItem.getFileName();

			int index = fileName.lastIndexOf('\\');

			if (index != -1) {
				fileName = fileName.substring(index + 1);
			}
			institution.setLogo(uploadItem.getData());

		}

	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	public List<Institution> getListeIns() {
		return listeIns;
	}

	public void setListeIns(List<Institution> listeIns) {
		this.listeIns = listeIns;
	}

	public Utilisateur getUser() {
		return user;
	}

	public void setUser(Utilisateur user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

}
