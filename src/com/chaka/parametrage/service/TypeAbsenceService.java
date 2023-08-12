/**
 * 
 */
package com.chaka.parametrage.service;

import java.io.Serializable;
import java.util.Date;


import org.hibernate.Session;
import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;

import com.chaka.parametrage.entity.Lst_TypeAbsence;
import com.chaka.parametrage.service.ListeParam;

/**
 * @author a626257
 *
 */
@Name("typeAbsenceService")
@Scope(ScopeType.SESSION)
public class TypeAbsenceService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@In
	private Session dataSource;

	private Lst_TypeAbsence typeAbsence = new Lst_TypeAbsence();


	@Out(required = false)
	private ListeParam listeParamStock = (ListeParam) Component.getInstance("listeParamStock");


	public String versTypeAbsence() {
		return "/pages/parametrage/rhospi/typeAbsence.xhtml";
	}


	/**
	 * cette methode permet d'ajouter un personnel
	 */
	public void ajouterParam() {
		if (typeAbsence.getIdTypeAbsence() == null) {
			typeAbsence.setDateCreation(new Date());
			dataSource.save(typeAbsence);
		    listeParamStock.actualiserLst_TypeAbsence();
		} else {
			typeAbsence.setDateMaj(new Date());
			 dataSource.update(typeAbsence);
			 listeParamStock.actualiserLst_TypeContrat();
		}
		dataSource.flush();
		typeAbsence =new Lst_TypeAbsence();
	
		FacesMessages.instance().addToControlFromResourceBundle("infoGenerique",
				" Opération effectuée avec succes");
			
		
	}
	
	
	/**
	 * cette methode permet de modifier un personnel
	 * 
	 * @param personnel
	 */
	public void modifierParam(Lst_TypeAbsence typeAbsence) {
		this.typeAbsence =typeAbsence;
	}

	/**
	 * cette methode permet de supprimer un personnel
	 * 
	 * @param personnel
	 */
	public void supprimerParam(Lst_TypeAbsence typeAbsence) {
		dataSource.delete(typeAbsence);
		dataSource.flush();
		 listeParamStock.actualiserLst_TypeAbsence();
		FacesMessages.instance().addToControlFromResourceBundle("infoGenerique", "TypeAbsence supprimé avec succes");
	}


	public Lst_TypeAbsence getTypeAbsence() {
		return typeAbsence;
	}


	public void setTypeAbsence(Lst_TypeAbsence typeAbsence) {
		this.typeAbsence = typeAbsence;
	}

	

}
