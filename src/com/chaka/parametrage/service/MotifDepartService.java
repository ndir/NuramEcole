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

import com.chaka.parametrage.entity.Lst_MotifDepart;
import com.chaka.parametrage.service.ListeParam;

/**
 * @author a626257
 *
 */
@Name("motifDepartService")
@Scope(ScopeType.SESSION)
public class MotifDepartService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@In
	private Session dataSource;

	private Lst_MotifDepart motifDepart = new Lst_MotifDepart();


	@Out(required = false)
	private ListeParam listeParamStock = (ListeParam) Component.getInstance("listeParamStock");


	public String versMotifDepart() {
		return "/pages/parametrage/rhospi/motifDepart.xhtml";
	}


	/**
	 * cette methode permet d'ajouter un personnel
	 */
	public void ajouterParam() {
		if (motifDepart.getIdMotifDepart() == null) {
			motifDepart.setDateCreation(new Date());
			dataSource.save(motifDepart);
		    listeParamStock.actualiserLst_MotifDepart();
		} else {
			motifDepart.setDateMaj(new Date());
			 dataSource.update(motifDepart);
			 listeParamStock.actualiserLst_TypeContrat();
		}
		dataSource.flush();
		motifDepart =new Lst_MotifDepart();
	
		FacesMessages.instance().addToControlFromResourceBundle("infoGenerique",
				" Opération effectuée avec succes");
			
		
	}
	
	
	/**
	 * cette methode permet de modifier un personnel
	 * 
	 * @param personnel
	 */
	public void modifierParam(Lst_MotifDepart motifDepart) {
		this.motifDepart =motifDepart;
	}

	/**
	 * cette methode permet de supprimer un personnel
	 * 
	 * @param personnel
	 */
	public void supprimerParam(Lst_MotifDepart motifDepart) {
		dataSource.delete(motifDepart);
		dataSource.flush();
		 listeParamStock.actualiserLst_MotifDepart();
		FacesMessages.instance().addToControlFromResourceBundle("infoGenerique", "MotifDepart supprimé avec succes");
	}


	public Lst_MotifDepart getMotifDepart() {
		return motifDepart;
	}


	public void setMotifDepart(Lst_MotifDepart motifDepart) {
		this.motifDepart = motifDepart;
	}

	

}
