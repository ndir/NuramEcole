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

import com.ecole.entity.TypeRecette;



/**
 * @author a626257
 * 
 */
@Scope(ScopeType.SESSION)
@Name("typeRecetteService")
public class TypeRecetteService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@In
	private Session dataSource;

	private List<TypeRecette> listeTypeRecette = new ArrayList<TypeRecette>();

	private TypeRecette typeRecette = new TypeRecette();

	@SuppressWarnings("unchecked")
	public void chargerListeTypeRecette() {
		listeTypeRecette = new ArrayList<TypeRecette>();
		listeTypeRecette = dataSource.createQuery(" From TypeRecette ").list();
	}

	public String versTypeRecette() {
		this.setTypeRecette(new TypeRecette());
		chargerListeTypeRecette();
		return "/pages/nuramecole/TypeRecette.xhtml";
	}

	public void annulerAjout() {
		this.setTypeRecette(new TypeRecette());
	}

	public TypeRecette getTypeRecetteFrom() {
		return (TypeRecette) dataSource
				.createQuery("From TypeRecette where libelle=:pl")
				.setParameter("pl", typeRecette.getLibelle()).uniqueResult();
	}

	public void ajouterTypeRecette() {
		if (this.typeRecette.getLibelle().isEmpty()) {
			FacesMessages.instance().addToControlFromResourceBundle(
					"erreurGenerique", "Veuillez renseigner le libellé");
			return;
		}

		TypeRecette m = getTypeRecetteFrom();

		if (m != null) {
			FacesMessages.instance().addToControlFromResourceBundle(
					"erreurGenerique", "la matière "+typeRecette.getLibelle() +" existe déja");
			return;
		}

		if (typeRecette.getId() == null) {
			dataSource.save(typeRecette);
		} else {
			dataSource.update(typeRecette);
		}
		chargerListeTypeRecette();
		this.setTypeRecette(new TypeRecette());
		FacesMessages.instance().addToControlFromResourceBundle(
				"infoGenerique", "Type de Recette ajoutée avec succés");
	}

	public void versModifierTypeRecette(TypeRecette typeRecette) {
		this.setTypeRecette(typeRecette);
	}

	public List<TypeRecette> getListeTypeRecette() {
		return listeTypeRecette;
	}

	public void setListeTypeRecette(List<TypeRecette> listeTypeRecette) {
		this.listeTypeRecette = listeTypeRecette;
	}

	public TypeRecette getTypeRecette() {
		return typeRecette;
	}

	public void setTypeRecette(TypeRecette typeRecette) {
		this.typeRecette = typeRecette;
	}

	
}
