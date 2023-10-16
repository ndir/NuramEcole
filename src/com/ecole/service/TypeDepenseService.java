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

import com.ecole.entity.TypeDepense;



/**
 * @author a626257
 * 
 */
@Scope(ScopeType.SESSION)
@Name("typeDepenseService")
public class TypeDepenseService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@In
	private Session dataSource;

	private List<TypeDepense> listeTypeDepense = new ArrayList<TypeDepense>();

	private TypeDepense typeDepense = new TypeDepense();

	@SuppressWarnings("unchecked")
	public void chargerListeTypeDepense() {
		listeTypeDepense = new ArrayList<TypeDepense>();
		listeTypeDepense = dataSource.createQuery(" From TypeDepense ").list();
	}

	public String versTypeDepense() {
		this.setTypeDepense(new TypeDepense());
		chargerListeTypeDepense();
		return "/pages/nuramecole/TypeDepense.xhtml";
	}

	public void annulerAjout() {
		this.setTypeDepense(new TypeDepense());
	}

	public TypeDepense getTypeRecetteFrom() {
		return (TypeDepense) dataSource
				.createQuery("From TypeDepense where libelle=:pl")
				.setParameter("pl", typeDepense.getLibelle()).uniqueResult();
	}

	public void ajouterTypeDepense() {
		if (this.typeDepense.getLibelle().isEmpty()) {
			FacesMessages.instance().addToControlFromResourceBundle(
					"erreurGenerique", "Veuillez renseigner le libellé");
			return;
		}

		TypeDepense m = getTypeRecetteFrom();

		if (m != null) {
			FacesMessages.instance().addToControlFromResourceBundle(
					"erreurGenerique", "le type de depense "+typeDepense.getLibelle() +" existe déja");
			return;
		}

		if (typeDepense.getId() == null) {
			dataSource.save(typeDepense);
		} else {
			dataSource.update(typeDepense);
		}
		chargerListeTypeDepense();
		this.setTypeDepense(new TypeDepense());
		FacesMessages.instance().addToControlFromResourceBundle(
				"infoGenerique", "Type de depense ajoutée avec succés");
	}

	public void versModifierTypeDepense(TypeDepense typeDepense) {
		this.setTypeDepense(typeDepense);
	}

	public List<TypeDepense> getListeTypeDepense() {
		return listeTypeDepense;
	}

	public void setListeTypeDepense(List<TypeDepense> listeTypeDepense) {
		this.listeTypeDepense = listeTypeDepense;
	}

	public TypeDepense getTypeDepense() {
		return typeDepense;
	}

	public void setTypeDepense(TypeDepense typeDepense) {
		this.typeDepense = typeDepense;
	}

	
}
