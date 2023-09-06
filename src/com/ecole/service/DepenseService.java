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
import com.ecole.entity.AnneeScolaire;
import com.ecole.entity.Classe;
import com.ecole.entity.Depense;
import com.ecole.entity.TypeDepense;

/**
 * @author A626257
 *
 */
@Scope(ScopeType.SESSION)
@Name("depService")
public class DepenseService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@In
	private Session dataSource;

	private Depense depense = new Depense();

	private List<Depense> listeDepense = new ArrayList<Depense>();

	private List<TypeDepense> listeTypeDep = new ArrayList<TypeDepense>();

	@In
	private AnneeScolaire annee;

	@In(required = false)
	@Out(required = false)
	private Utilisateur utilisateur;

	@SuppressWarnings("unchecked")
	public String versDepense() {
		listeTypeDep = new ArrayList<TypeDepense>();
		listeTypeDep = dataSource.createQuery("From TypeDepense ").list();
		chargerListeDepense();
		return "/pages/nuramecole/depense.xhtml";
	}

	@SuppressWarnings("unchecked")
	public void chargerListeDepense() {
		listeDepense = new ArrayList<Depense>();
		listeDepense = dataSource.createQuery(
				"From Depense d inner join fetch d.typeDepense inner join fetch d.utilisateur inner join fetch d.annee an where an =:pan ")
				.setParameter("pan", annee).list();
	}

	public void ajouterDepense() {
		if (depense.getTypeDepense() == null) {
			FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique",
					"Veuillez choisir un type de dépense");
			return;
		}
		if (depense.getMontantPaye() <= 0) {
			FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique",
					"Veuillez renseigner le montant");
			return;
		}
		if (depense.getDateDepense() == null) {
			FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique", "Veuillez renseigner la date");
			return;
		}
		depense.setAnnee(annee);
		depense.setUtilisateur(utilisateur);
		if (depense.getIdDepense() == null) {
			dataSource.save(depense);
		} else {
			dataSource.update(depense);
		}
		FacesMessages.instance().addToControlFromResourceBundle("infoGenerique", "Dépense ajoutée avec succès");
		this.setDepense(new Depense());
		chargerListeDepense();
	}

	public void versModifierDepense(Depense depense) {
		this.setDepense(depense);
	}

	public void annulerAjout() {
		this.setDepense(new Depense());
	}

	public Depense getDepense() {
		return depense;
	}

	public void setDepense(Depense depense) {
		this.depense = depense;
	}

	public List<Depense> getListeDepense() {
		return listeDepense;
	}

	public void setListeDepense(List<Depense> listeDepense) {
		this.listeDepense = listeDepense;
	}

	public List<TypeDepense> getListeTypeDep() {
		return listeTypeDep;
	}

	public void setListeTypeDep(List<TypeDepense> listeTypeDep) {
		this.listeTypeDep = listeTypeDep;
	}
}
