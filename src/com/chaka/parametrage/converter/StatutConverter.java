package com.chaka.parametrage.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.hibernate.Session;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import com.chaka.parametrage.entity.Lst_Statut;



@Name("statutConverter")
public class StatutConverter implements Converter {
	
	/**
	 * Session Hibernate
	 */
	@In
	private Session dataSource;
	
	/**
	 * Constructeur
	 */
	public StatutConverter() {
		super();
	}
	
	/**
	 * Transforme la string pass�e en parametre en objet.
	 * 
	 * @param myContextFaces
	 *            le contexte courant.
	 * @param pComponent
	 *            le composant li� � la transformation.
	 * @param pValeur
	 *            la valeur de l'objet � transformer.
	 * @return l'objet transform�.
	 * @throws ConverterException
	 * 
	 */
	public Object getAsObject(FacesContext myContextFaces, UIComponent pComponent, String pValeur) {
		Lst_Statut statut = (Lst_Statut)this.dataSource.get(Lst_Statut.class, 
				new Long(pValeur));
		return statut;
	}
	/**
	 * Transforme l'objet pass� en parametre en string.
	 * 
	 * @param myContextFaces
	 *            le contexte courant.
	 * @param uiComponent
	 *            le composant li� � la transformation.
	 * @param objet
	 *            la valeure de l'objet � transformer.
	 * @return l'objet transform�.
	 * 
	 */
	public String getAsString(FacesContext myContextFaces, UIComponent uiComponent, Object objet) {
		Lst_Statut statut = (Lst_Statut)objet;
		String resultat = "";
		if (statut!= null) {
			resultat = statut.getIdStatut().toString();
		}
		return resultat;
	}
}
