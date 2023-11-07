package com.chaka.parametrage.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.hibernate.Session;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import com.chaka.parametrage.entity.Lst_Annee;
import com.ecole.entity.AnneeScolaire;
import com.ecole.entity.Defi;

@Name("defiConverter")
public class DefiConverter implements Converter {

	/**
	 * Session Hibernate
	 */
	@In
	private Session dataSource;

	/**
	 * Constructeur
	 */
	public DefiConverter() {
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
		Defi annee = (Defi) this.dataSource.get(Defi.class, new Long(pValeur));
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
		Defi annee = (Defi) objet;
		String resultat = "";
		if (annee != null && annee.getId() != null) {
			resultat = annee.getId().toString();
		}
		return resultat;
	}
}
