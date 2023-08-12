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

import com.chaka.parametrage.entity.Lst_StatutValidation;
import com.chaka.parametrage.service.ListeParam;

/**
 * @author a626257
 *
 */
@Name("statutValidationService")
@Scope(ScopeType.SESSION)
public class StatutValidationService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@In
	private Session dataSource;

	private Lst_StatutValidation statutValidation = new Lst_StatutValidation();


	@Out(required = false)
	private ListeParam listeParamStock = (ListeParam) Component.getInstance("listeParamStock");


	public String versStatutValidation() {
		return "/pages/parametrage/rhospi/statutValidation.xhtml";
	}


	/**
	 * cette methode permet d'ajouter un personnel
	 */
	public void ajouterParam() {
		if (statutValidation.getIdStatut() == null) {
			statutValidation.setDateCreation(new Date());
			dataSource.save(statutValidation);
		    listeParamStock.actualiserLst_StatutValidation();
		} else {
			statutValidation.setDateMaj(new Date());
			 dataSource.update(statutValidation);
			 listeParamStock.actualiserLst_TypeContrat();
		}
		dataSource.flush();
		statutValidation =new Lst_StatutValidation();
	
		FacesMessages.instance().addToControlFromResourceBundle("infoGenerique",
				" Opération effectuée avec succes");
			
		
	}
	
	
	/**
	 * cette methode permet de modifier un personnel
	 * 
	 * @param personnel
	 */
	public void modifierParam(Lst_StatutValidation statutValidation) {
		this.statutValidation =statutValidation;
	}

	/**
	 * cette methode permet de supprimer un personnel
	 * 
	 * @param personnel
	 */
	public void supprimerParam(Lst_StatutValidation statutValidation) {
		dataSource.delete(statutValidation);
		dataSource.flush();
		 listeParamStock.actualiserLst_StatutValidation();
		FacesMessages.instance().addToControlFromResourceBundle("infoGenerique", "StatutValidation supprimé avec succes");
	}


	public Lst_StatutValidation getStatutValidation() {
		return statutValidation;
	}


	public void setStatutValidation(Lst_StatutValidation statutValidation) {
		this.statutValidation = statutValidation;
	}

	

}
