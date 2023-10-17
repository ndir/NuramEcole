/**
 * 
 */
package com.ecole.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;

import com.chaka.projet.entity.Utilisateur;
import com.ecole.entity.Appreciation;
import com.ecole.entity.AppreciationMS;

/**
 * @author A626257
 *
 */
@Scope(ScopeType.SESSION)
@Name("apService")
public class AppreciationService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@In
	private Session dataSource;

	private Appreciation ap = new Appreciation();

	private List<Appreciation> listeAp = new ArrayList<Appreciation>();

	private AppreciationMS apms = new AppreciationMS();

	private List<AppreciationMS> listeAps = new ArrayList<AppreciationMS>();

	@In(required = false)
	@Out(required = false)
	private Utilisateur utilisateur;

	public String versAp() {
		this.setAp(new Appreciation());
		chargerListeAp();
		return "/pages/nuramecole/appreciation.xhtml";
	}

	public String versApms() {
		this.setApms(new AppreciationMS());
		chargerListeAps();
		return "/pages/nuramecole/appreciations.xhtml";
	}

	@SuppressWarnings("unchecked")
	public void chargerListeAp() {
		listeAp = new ArrayList<Appreciation>();
		listeAp = dataSource.createQuery("From Appreciation a inner join fetch a.institution i where i =:pi ")
				.setParameter("pi", utilisateur.getInstitution()).list();
	}

	@SuppressWarnings("unchecked")
	public void chargerListeAps() {
		listeAps = new ArrayList<AppreciationMS>();
		listeAps = dataSource.createQuery("From AppreciationMS a inner join fetch a.institution i where i =:pi ")
				.setParameter("pi", utilisateur.getInstitution()).list();
	}

	public void ajouterAp() {
		if (ap.getInf() <= 0) {
			FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique",
					"Renseiger la moyenne inférieure");
			return;
		}

		if (ap.getSup() <= 0) {
			FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique",
					"Renseiger la moyenne supérieure");
			return;
		}
		if (ap.getLibelle().isEmpty()) {
			FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique", "Appréciation obligatoire");
			return;
		}
		if (ap.getId() == null) {
			ap.setInstitution(utilisateur.getInstitution());
			dataSource.save(ap);
		} else {
			dataSource.update(ap);
		}
		this.setAp(new Appreciation());
		chargerListeAp();
		FacesMessages.instance().addToControlFromResourceBundle("infoGenerique", "Opération effectuée avec succès");
	}

	public void ajouterAps() {
		if (apms.getInf() <= 0) {
			FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique",
					"Renseiger la moyenne inférieure");
			return;
		}

		if (apms.getSup() <= 0) {
			FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique",
					"Renseiger la moyenne supérieure");
			return;
		}
		if (apms.getLibelle().isEmpty()) {
			FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique", "Appréciation obligatoire");
			return;
		}
		if (apms.getId() == null) {
			apms.setInstitution(utilisateur.getInstitution());
			dataSource.save(apms);
		} else {
			dataSource.update(apms);
		}
		this.setApms(new AppreciationMS());
		chargerListeAps();
		FacesMessages.instance().addToControlFromResourceBundle("infoGenerique", "Opération effectuée avec succès");
	}

	public void annulerAp() {
		this.setAp(new Appreciation());
	}

	public void annulerAps() {
		this.setApms(new AppreciationMS());
	}

	public void versmodifierAp(Appreciation ap) {
		this.setAp(ap);
	}

	public void versmodifierAps(AppreciationMS ap) {
		this.setApms(ap);
	}

	public Appreciation getAp() {
		return ap;
	}

	public void setAp(Appreciation ap) {
		this.ap = ap;
	}

	public List<Appreciation> getListeAp() {
		return listeAp;
	}

	public void setListeAp(List<Appreciation> listeAp) {
		this.listeAp = listeAp;
	}

	public AppreciationMS getApms() {
		return apms;
	}

	public void setApms(AppreciationMS apms) {
		this.apms = apms;
	}

	public List<AppreciationMS> getListeAps() {
		return listeAps;
	}

	public void setListeAps(List<AppreciationMS> listeAps) {
		this.listeAps = listeAps;
	}

}
