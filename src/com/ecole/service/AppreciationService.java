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
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;

import com.ecole.entity.Appreciation;

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

	public String versAp() {
		this.setAp(new Appreciation());
		chargerListeAp();
		return "/pages/nuramecole/appreciation.xhtml";
	}

	@SuppressWarnings("unchecked")
	public void chargerListeAp() {
		listeAp = new ArrayList<Appreciation>();
		listeAp = dataSource.createQuery("From Appreciation ").list();
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
			dataSource.save(ap);
		} else {
			dataSource.update(ap);
		}
		this.setAp(new Appreciation());
		chargerListeAp();
		FacesMessages.instance().addToControlFromResourceBundle("infoGenerique", "Opération effectuée avec succès");
	}
	
	public void annulerAp() {
		this.setAp(new Appreciation());
	}

	public void versmodifierAp(Appreciation ap) {
		this.setAp(ap);
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

}
