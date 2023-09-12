package com.chaka.parametrage.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.hibernate.Session;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import com.ecole.entity.AnneeScolaire;
import com.ecole.entity.TypeNote;

@Name("typenoteConverter")
public class TypeNoteConverter implements Converter {

	/**
	 * Session Hibernate
	 */
	@In
	private Session dataSource;

	/**
	 * Constructeur
	 */
	public TypeNoteConverter() {
		super();
	}

	/**
	 * Transforme la string pass�e en parametre en objet.
	 * 
	 * @param myContextFaces le contexte courant.
	 * @param pComponent     le composant li� � la transformation.
	 * @param pValeur        la valeur de l'objet � transformer.
	 * @return l'objet transform�.
	 * @throws ConverterException
	 * 
	 */
	public Object getAsObject(FacesContext myContextFaces, UIComponent pComponent, String pValeur) {
		TypeNote annee = (TypeNote) this.dataSource.get(TypeNote.class, new Long(pValeur));
		return annee;
	}

	/**
	 * Transforme l'objet pass� en parametre en string.
	 * 
	 * @param myContextFaces le contexte courant.
	 * @param uiComponent    le composant li� � la transformation.
	 * @param objet          la valeure de l'objet � transformer.
	 * @return l'objet transform�.
	 * 
	 */
	public String getAsString(FacesContext myContextFaces, UIComponent uiComponent, Object objet) {
		TypeNote annee = (TypeNote) objet;
		String resultat = "";
		if (annee != null) {
			resultat = annee.getId().toString();
		}
		return resultat;
	}
}
