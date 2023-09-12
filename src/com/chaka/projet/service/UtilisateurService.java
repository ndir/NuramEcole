package com.chaka.projet.service;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.Transient;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;

import com.chaka.projet.entity.Profile;
import com.chaka.projet.entity.Utilisateur;
import com.chaka.projet.entity.UtilisateurSecurise;
import com.chaka.projet.service.utils.ServiceCryptage;
import com.ecole.entity.AnneeScolaire;
import com.ecole.entity.Classe;
import com.ecole.entity.ClasseEnseignant;
import com.ecole.entity.Niveau;

@Name("utilisateurService")
@Scope(ScopeType.SESSION)
public class UtilisateurService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3131849938087110342L;

	@In
	private Session dataSource;

	@In(required = false)
	@Out(required = false)
	private Utilisateur utilisateur;

	@In
	FacesMessages facesMessages;

	private String nomSearchString;

	private String prenomSearchString;

	private Profile profileSearch;

	private List<Utilisateur> listeUtilisateurs;

	private List<Profile> listeProfilesSearch;

	private Utilisateur utilisateurCreationModification;

	private boolean enCreation;

	private String password1;

	private String password2;

	@In
	private AnneeScolaire annee;
	private String ens;

	private Integer nbreUsers = null;
	private List<ClasseEnseignant> listeClEn = new ArrayList<ClasseEnseignant>();
	private List<Niveau> listeNiveau = new ArrayList<Niveau>();
	
	private List<Classe> listeClasse = new ArrayList<Classe>();
	
	private Classe classe = new Classe();
	private Niveau niveau = new Niveau();

	/**
	 * Constructeur
	 */
	public UtilisateurService() {
		super();
	}

	@SuppressWarnings("unchecked")
	public void listeClasseEnseignant(Utilisateur u) {
		listeNiveau = new ArrayList<Niveau>();
		listeNiveau = dataSource.createQuery("From Niveau ").list();
		this.setEns(u.getNomComplet());
		listeClEn = new ArrayList<ClasseEnseignant>();
		listeClEn = dataSource
				.createQuery("From ClasseEnseignant cl inner join fetch cl.classe inner join fetch cl.enseignant "
						+ " inner join fetch cl.annee an where an =:pan ")
				.setParameter("pan", annee).list();
	}
	
	@SuppressWarnings("unchecked")
	public void chargerListeClasse() {
		listeClasse = new ArrayList<Classe>();
		listeClasse = dataSource.createQuery(" From Classe c inner join fetch c.niveau n where n=:pn")
				.setParameter("pn", niveau).list();
	}
	@SuppressWarnings("unchecked")
	public void find() {

		listeUtilisateurs.clear();

		if (this.nomSearchString != null)
			this.nomSearchString = nomSearchString.toLowerCase();

		if (this.prenomSearchString != null)
			this.prenomSearchString = prenomSearchString.toLowerCase();

		String filtreNom = "%" + this.nomSearchString + "%";
		String filtrePrenom = "%" + this.prenomSearchString + "%";

		/*
		 * listeUtilisateurs = this.dataSource.createQuery(
		 * "select distinct u  from Utilisateur u " +
		 * "where  lower(u.nom) like :nomSearchStr and  lower(u.prenom) like :prenomSearchStr "
		 * + "and (:profileSearch is null or u.profile = :profileSearch )  " +
		 * "order by u.nom asc, u.prenom asc") .setParameter("nomSearchStr", filtreNom)
		 * .setParameter("prenomSearchStr", filtrePrenom) .setParameter("profileSearch",
		 * profileSearch) .list();
		 */

		Criteria criteres = this.dataSource.createCriteria(Utilisateur.class);

		if (filtreNom != null && !filtreNom.isEmpty())
			criteres.add(Restrictions.ilike("nom", filtreNom));
		if (filtrePrenom != null && !filtrePrenom.isEmpty())
			criteres.add(Restrictions.ilike("prenom", filtrePrenom));
		if (profileSearch != null)
			criteres.add(Restrictions.eq("profile", profileSearch));

		criteres.addOrder(Order.asc("nom"));
		criteres.addOrder(Order.asc("prenom"));

		listeUtilisateurs = criteres.list();
		// listeUtilisateurs = this.dataSource.createQuery("select distinct u from
		// Utilisateur u ").list();
	}

	public String retourRechercheUtilisateurs() {
		return "/pages/utilisateur/utilisateurListe.xhtml";
	}

	public String versCreationUtilisateurAgence() {
		this.enCreation = true;

		this.utilisateurCreationModification = new Utilisateur();
		this.utilisateurCreationModification.setActif(true);

		return "/pages/utilisateur/utilisateurCreationModication.xhtml";
	}

	public String versCreationUtilisateurIntervenant() {
		this.enCreation = true;

		this.utilisateurCreationModification = new Utilisateur();
		this.utilisateurCreationModification.setActif(true);

		Profile profileIntervenant = new Profile();
		profileIntervenant.setIdProfile(3L);
		this.utilisateurCreationModification.setProfile(profileIntervenant);

		return "/pages/utilisateur/utilisateurIntervenantCreationModication.xhtml";
	}

	public String versCreationUtilisateurBeneficiaire() {
		this.enCreation = true;

		this.utilisateurCreationModification = new Utilisateur();
		this.utilisateurCreationModification.setActif(true);

		Profile profileBeneficiaire = new Profile();
		profileBeneficiaire.setIdProfile(4L);
		this.utilisateurCreationModification.setProfile(profileBeneficiaire);

		return "/pages/utilisateur/utilisateurBeneficiaireCreationModication.xhtml";
	}

	public String consulterUtilisateur(Utilisateur utilisateurAConsulter) {
		this.enCreation = false;

		this.utilisateurCreationModification = (Utilisateur) this.dataSource.get(Utilisateur.class,
				utilisateurAConsulter.getIdUtilisateur());

		return "/pages/utilisateur/utilisateurCreationModication.xhtml";

	}

	public String consulterMySelf() {
		this.enCreation = false;

		this.utilisateurCreationModification = (Utilisateur) this.dataSource.get(Utilisateur.class,
				utilisateur.getIdUtilisateur());

		return "/pages/utilisateur/monUtilisateurCreationModication.xhtml";
	}

	public String initialisationPassword() {
		this.password1 = "";
		this.password2 = "";
		return "";
	}

	public void validerPassword() {
		String passwordChoisi = password1;

		if (!password1.equals(password2)) {
			facesMessages.addToControlFromResourceBundle("erreurGenerique", "msg.passwordPasIdentiques");
		} else {

			UtilisateurSecurise us = (UtilisateurSecurise) dataSource.get(UtilisateurSecurise.class,
					utilisateurCreationModification.getIdUtilisateur());

			ServiceCryptage sc = new ServiceCryptage();
			try {

				byte[] salt = sc.generateSalt();
				byte[] securePass = sc.getEncryptedPassword(passwordChoisi, salt);

				us.setSalt(salt);
				us.setSecurePassword(securePass);

				dataSource.update(us);
				dataSource.flush();

			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (InvalidKeySpecException e) {
				e.printStackTrace();
			}

			facesMessages.addToControlFromResourceBundle("infoGenerique", "msg.utilisateur.modification.ok");
		}

	}

	public String creerModifierUtilisateur() {
		String passwordChoisi = utilisateurCreationModification.getPassword();

		if (enCreation && !this.utilisateurCreationModification.getPassword()
				.equals(this.utilisateurCreationModification.getPassword2())) {
			facesMessages.addToControlFromResourceBundle("erreurGenerique", "msg.passwordPasIdentiques");
			return "";
		}

		// Vérification de l'unicité du login !!
		Number nb = null;
		if (this.utilisateurCreationModification.getIdUtilisateur() == null) {
			String hql = "select count(u) from Utilisateur u where u.login = :login";
			nb = (Number) this.dataSource.createQuery(hql)
					.setParameter("login", this.utilisateurCreationModification.getLogin()).uniqueResult();
		} else {
			String hql = "select count(u) from Utilisateur u where u.login = :login and u.idUtilisateur != :idUtilisateur ";
			nb = (Number) this.dataSource.createQuery(hql)
					.setParameter("login", this.utilisateurCreationModification.getLogin())
					.setParameter("idUtilisateur", utilisateurCreationModification.getIdUtilisateur()).uniqueResult();
		}

		if (nb.intValue() > 0) {
			facesMessages.addToControlFromResourceBundle("erreurGenerique", "msg.loginIndisponible");
			return "";
		}

		this.utilisateurCreationModification.setDateMaj(new Date());

		if (this.utilisateurCreationModification.getIdUtilisateur() == null) {
			this.utilisateurCreationModification.setDateCreation(new Date());
			this.dataSource.save(this.utilisateurCreationModification);
		} else {
			this.dataSource.update(this.utilisateurCreationModification);
		}

		this.dataSource.flush();

		if (enCreation) {
			// préparation du password :

			UtilisateurSecurise us = (UtilisateurSecurise) dataSource.get(UtilisateurSecurise.class,
					utilisateurCreationModification.getIdUtilisateur());

			ServiceCryptage sc = new ServiceCryptage();
			try {

				byte[] salt = sc.generateSalt();
				byte[] securePass = sc.getEncryptedPassword(passwordChoisi, salt);

				us.setSalt(salt);
				us.setSecurePassword(securePass);

				dataSource.update(us);
				dataSource.flush();

			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (InvalidKeySpecException e) {
				e.printStackTrace();
			}

		}

		if (utilisateur.getIdUtilisateur() != null
				&& utilisateur.getIdUtilisateur().equals(utilisateurCreationModification.getIdUtilisateur())) {
			utilisateur = (Utilisateur) dataSource.load(Utilisateur.class, utilisateur.getIdUtilisateur());
		}

		if (enCreation) {
			utilisateurCreationModification = new Utilisateur();
			facesMessages.addToControlFromResourceBundle("infoGenerique", "msg.utilisateur.creation.ok");
		} else {
			facesMessages.addToControlFromResourceBundle("infoGenerique", "msg.utilisateur.modification.ok");
		}

		this.enCreation = false;

		return null;
	}

	public String versGestionUtilisateurs() {
		this.nomSearchString = "";
		this.prenomSearchString = "";
		this.listeProfilesSearch = Collections.emptyList();
		this.profileSearch = null;
		this.listeUtilisateurs = Collections.emptyList();

		find();

		return "/pages/utilisateur/utilisateurListe.xhtml";
	}

	public void activer(Utilisateur utilisateurAActiver) {
		utilisateurAActiver.setActif(true);
		utilisateurAActiver.setDateMaj(new Date());
		this.dataSource.update(utilisateurAActiver);
		this.dataSource.flush();
	}

	public void desactiver(Utilisateur utilisateurAActiver) {
		utilisateurAActiver.setActif(false);
		utilisateurAActiver.setDateMaj(new Date());
		this.dataSource.update(utilisateurAActiver);
		this.dataSource.flush();
	}

	public String getNomSearchString() {
		return nomSearchString;
	}

	public void setNomSearchString(String nomSearchString) {
		this.nomSearchString = nomSearchString;
	}

	public String getPrenomSearchString() {
		return prenomSearchString;
	}

	public void setPrenomSearchString(String prenomSearchString) {
		this.prenomSearchString = prenomSearchString;
	}

	public Profile getProfileSearch() {
		return profileSearch;
	}

	public void setProfileSearch(Profile profileSearch) {
		this.profileSearch = profileSearch;
	}

	public List<Utilisateur> getListeUtilisateurs() {
		return listeUtilisateurs;
	}

	public void setListeUtilisateurs(List<Utilisateur> listeUtilisateurs) {
		this.listeUtilisateurs = listeUtilisateurs;
	}

	public Utilisateur getUtilisateurCreationModification() {
		return utilisateurCreationModification;
	}

	public void setUtilisateurCreationModification(Utilisateur utilisateurCreationModification) {
		this.utilisateurCreationModification = utilisateurCreationModification;
	}

	public boolean isEnCreation() {
		return enCreation;
	}

	public void setEnCreation(boolean enCreation) {
		this.enCreation = enCreation;
	}

	public String getPassword1() {
		return password1;
	}

	public void setPassword1(String password1) {
		this.password1 = password1;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	/**
	 * @return the listeProfilesSearch
	 */
	public List<Profile> getListeProfilesSearch() {
		return listeProfilesSearch;
	}

	/**
	 * @param listeProfilesSearch the listeProfilesSearch to set
	 */
	public void setListeProfilesSearch(List<Profile> listeProfilesSearch) {
		this.listeProfilesSearch = listeProfilesSearch;
	}

	/**
	 * @return the firstAdmin
	 */
	@Transient
	public boolean isFirstAdmin() {
		if (nbreUsers == null) {
			String hql = "select count(u) from Utilisateur u";
			nbreUsers = ((Number) this.dataSource.createQuery(hql).uniqueResult()).intValue();
		}
		return nbreUsers == 0;
	}

	public List<ClasseEnseignant> getListeClEn() {
		return listeClEn;
	}

	public void setListeClEn(List<ClasseEnseignant> listeClEn) {
		this.listeClEn = listeClEn;
	}

	public String getEns() {
		return ens;
	}

	public void setEns(String ens) {
		this.ens = ens;
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

}
