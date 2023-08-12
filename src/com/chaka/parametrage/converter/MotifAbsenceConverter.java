package com.chaka.parametrage.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.hibernate.Session;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;


import com.chaka.parametrage.entity.Lst_MotifAbsence;



@Name("motifAbsenceConverter")
public class MotifAbsenceConverter implements Converter {
	
	/**
	 * Session Hibernate
	 */
	@In
	private Session dataSource;
	
	/**
	 * Constructeur
	 */
	public MotifAbsenceConverter() {
		super();
	}
	
	/**
	 * Transforme la string passée en parametre en objet.
	 * 
	 * @param myContextFaces
	 *            le contexte courant.
	 * @param pComponent
	 *            le composant lié à la transformation.
	 * @param pValeur
	 *            la valeur de l'objet à transformer.
	 * @return l'objet transformé.
	 * @throws ConverterException
	 * 
	 */
	public Object getAsObject(FacesContext myContextFaces, UIComponent pComponent, String pValeur) {
		Lst_MotifAbsence motifAbsence = (Lst_MotifAbsence)this.dataSource.get(Lst_MotifAbsence.class, 
				new Long(pValeur));
		return motifAbsence;
	}
	/**
	 * Transforme l'objet passé en parametre en string.
	 * 
	 * @param myContextFaces
	 *            le contexte courant.
	 * @param uiComponent
	 *            le composant lié à la transformation.
	 * @param objet
	 *            la valeure de l'objet à transformer.
	 * @return l'objet transformé.
	 * 
	 */
	public String getAsString(FacesContext myContextFaces, UIComponent uiComponent, Object objet) {
		Lst_MotifAbsence motifAbsence = (Lst_MotifAbsence)objet;
		String resultat = "";
		if (motifAbsence != null) {
			resultat = motifAbsence.getIdMotifAbsence().toString();
		}
		return resultat;
	}
}
