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

import com.chaka.parametrage.entity.Lst_MotifAbsence;
import com.chaka.parametrage.service.ListeParam;

/**
 * @author a626257
 *
 */
@Name("motifAbsenceService")
@Scope(ScopeType.SESSION)
public class MotifAbsenceService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@In
	private Session dataSource;

	private Lst_MotifAbsence motifAbsence = new Lst_MotifAbsence();


	@Out(required = false)
	private ListeParam listeParamStock = (ListeParam) Component.getInstance("listeParamStock");


	public String versMotifAbsence() {
		return "/pages/parametrage/rhospi/motifAbsence.xhtml";
	}


	/**
	 * cette methode permet d'ajouter un personnel
	 */
	public void ajouterParam() {
		if (motifAbsence.getIdMotifAbsence() == null) {
			motifAbsence.setDateCreation(new Date());
			dataSource.save(motifAbsence);
		    listeParamStock.actualiserLst_MotifAbsence();
		} else {
			motifAbsence.setDateMaj(new Date());
			 dataSource.update(motifAbsence);
			 listeParamStock.actualiserLst_TypeContrat();
		}
		dataSource.flush();
		motifAbsence =new Lst_MotifAbsence();
	
		FacesMessages.instance().addToControlFromResourceBundle("infoGenerique",
				" Opération effectuée avec succes");
			
		
	}
	
	
	/**
	 * cette methode permet de modifier un personnel
	 * 
	 * @param personnel
	 */
	public void modifierParam(Lst_MotifAbsence motifAbsence) {
		this.motifAbsence =motifAbsence;
	}

	/**
	 * cette methode permet de supprimer un personnel
	 * 
	 * @param personnel
	 */
	public void supprimerParam(Lst_MotifAbsence motifAbsence) {
		dataSource.delete(motifAbsence);
		dataSource.flush();
		 listeParamStock.actualiserLst_MotifAbsence();
		FacesMessages.instance().addToControlFromResourceBundle("infoGenerique", "MotifAbsence supprimé avec succes");
	}


	public Lst_MotifAbsence getMotifAbsence() {
		return motifAbsence;
	}


	public void setMotifAbsence(Lst_MotifAbsence motifAbsence) {
		this.motifAbsence = motifAbsence;
	}

	

}
