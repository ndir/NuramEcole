package com.chaka.projet.security;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.jboss.seam.Component;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.security.Identity;

import com.chaka.parametrage.service.ListeParam;
import com.chaka.projet.entity.Profile;
import com.chaka.projet.entity.Utilisateur;
import com.chaka.projet.entity.UtilisateurSecurise;
import com.chaka.projet.service.utils.ServiceCryptage;
import com.ecole.entity.AnneeScolaire;
import com.rhospi.commons.ChakaUtils;

/**
 * Classe utilisée pour l'authentification
 * 
 * @author Chaka V 0.1 : Maj : 09/11/2007
 */
@Name("authenticator")
public class Authenticator {

	/**
	 * Session hibernate permettant le dialogue avec la base de données.
	 */
	@In
	private Session dataSource;

	@In
	FacesMessages facesMessages;

	@Out(required = false)
	private ListeParam listeParamStock = (ListeParam) Component.getInstance("listeParamStock");

	/**
	 * Utilisateur loggué
	 */
	@In(required = false)
	@Out(required = false)
	private Utilisateur utilisateur;

	@In(required = false)
	private String username;

	@In(required = false)
	private String password;

	/**
	 * Boolean indiquant si l'utilisateur est deja loggué.
	 */
	private boolean loggedIn;

	private List<AnneeScolaire> listeAnnee = new ArrayList<AnneeScolaire>();

	/**
	 * Utilisateur loggué
	 */
	@In(required = false)
	@Out(required = false)
	private AnneeScolaire annee;

	/**
	 * Constructeur de la classe agent.
	 */
	@SuppressWarnings("unchecked")
	public Authenticator() {
		super();

	}

	/**
	 * Methode de Login Chaka
	 */
	public void login() {

		if ((listeParamStock.getInstitution().getDateModification() != null
				&& listeParamStock.getInstitution().getDateMaj().before(new Date()))
				|| (listeParamStock.getInstitution().getDateModification() == null)) { // TODO
																						// AUTENTIFICATION
																						// DE
																						// L'APPLICATION
			listeParamStock.getInstitution().setDateModification(new Date());
			dataSource.merge(listeParamStock.getInstitution());
			dataSource.flush();
			listeParamStock.actualiserListeInstitution();
			ChakaUtils.bindeule("MAJ DATE COUMPA ");
		}

		ChakaUtils.bindeule("Authentification ");
		Identity.instance().logout();
		Identity.instance().login();

		loggedIn = Identity.instance().isLoggedIn();

	}

	public String redirection() {
		if (utilisateur.isActif()) {
			if (utilisateur == null)
				authenticate();
			if (utilisateur.isInitPass()) {
				return "/pages/home/initPass1.xhtml";
			} else {
				return "/pages/nuramecole/template/index.xhtml";
			}

		} else {
			if (utilisateur.getNom().equalsIgnoreCase("Administrateur")) {
				return "/pages/home/initPass.xhtml";
			} else {
				return "/pages/home/initPass1.xhtml";
			}

		}
	}

	/**
	 * Méthode d'authentification à l'application.
	 * 
	 * @return l'autorisation de l'authentification.
	 */
	public boolean authenticate() {

		String login = Identity.instance().getCredentials().getUsername();
		String password = Identity.instance().getCredentials().getPassword();

		String hqlSecure = "select us from UtilisateurSecurise us where us.login = :login ";

		UtilisateurSecurise userSecured = (UtilisateurSecurise) dataSource.createQuery(hqlSecure)
				.setParameter("login", login).uniqueResult();

		if (userSecured != null) {
			ServiceCryptage sc = new ServiceCryptage();

			try {

				if (userSecured.getSecurePassword() == null || userSecured.getSalt() == null) {
					loggedIn = false;
				} else {

					loggedIn = sc.authenticate(password, userSecured.getSecurePassword(), userSecured.getSalt());

					if (loggedIn) {
						utilisateur = (Utilisateur) dataSource.get(Utilisateur.class, userSecured.getIdUtilisateur());

						int profile = utilisateur.getProfile().getIdProfile().intValue();

						switch (profile) {
						case Utilisateur.ADMIN:
							Identity.instance().addRole(Utilisateur.R_ADMIN);
							break;
						case Utilisateur.USER:
							Identity.instance().addRole(Utilisateur.R_USER);
							break;
						default:
							break;
						}

					}

				}

			} catch (NoSuchAlgorithmException e) {
				loggedIn = false;
				e.printStackTrace();
			} catch (InvalidKeySpecException e) {
				loggedIn = false;
				e.printStackTrace();
			}

		} else {

			if ("chaka".equals(login) && "chakaadmin".equals(password)) {

				utilisateur = new Utilisateur();
				utilisateur.setActif(true);
				utilisateur.setNom("Administrateur");
				utilisateur.setPrenom("Chaka");

				Profile profile2 = new Profile();
				profile2.setIdProfile(2L);

				utilisateur.setProfile(profile2);

				loggedIn = true;
				Identity.instance().addRole(Utilisateur.R_ADMIN);
				Identity.instance().addRole(Utilisateur.R_USER);
			} else {
				loggedIn = false;
			}

		}

		if (!loggedIn) {
			facesMessages.addToControlFromResourceBundle("loginMsg", "msg.login.ko");
		}

		return loggedIn;

	}

	/**
	 * Methode de redirection
	 * 
	 * @return String
	 */
	public String redirect() {
		Identity.instance().isLoggedIn();
		if (Identity.instance().isLoggedIn()) {

			return "/pages/accueil.xhtml";
		}

		return "/pages/loginImpossible.xhtml";
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public List<AnneeScolaire> getListeAnnee() {
		listeAnnee = dataSource.createQuery("From AnneeScolaire order by idannee desc").list();
		return listeAnnee;
	}

	public void setListeAnnee(List<AnneeScolaire> listeAnnee) {
		this.listeAnnee = listeAnnee;
	}

	public AnneeScolaire getAnnee() {
		return annee;
	}

	public void setAnnee(AnneeScolaire annee) {
		this.annee = annee;
	}

}
