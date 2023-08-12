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

import com.chaka.parametrage.entity.Lst_MotifRejet;
import com.chaka.parametrage.service.ListeParam;

/**
 * @author a626257
 *
 */
@Name("motifRejetService")
@Scope(ScopeType.SESSION)
public class MotifRejetService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@In
	private Session dataSource;

	private Lst_MotifRejet motifRejet = new Lst_MotifRejet();


	@Out(required = false)
	private ListeParam listeParamStock = (ListeParam) Component.getInstance("listeParamStock");


	public String versMotifRejet() {
		return "/pages/parametrage/rhospi/motifRejet.xhtml";
	}


	/**
	 * cette methode permet d'ajouter un personnel
	 */
	public void ajouterParam() {
		if (motifRejet.getIdMotif() == null) {
			motifRejet.setDateCreation(new Date());
			dataSource.save(motifRejet);
		    listeParamStock.actualiserLst_MotifRejet();
		} else {
			motifRejet.setDateMaj(new Date());
			 dataSource.update(motifRejet);
			 listeParamStock.actualiserLst_MotifRejet();
		}
		dataSource.flush();
		motifRejet =new Lst_MotifRejet();
	
		FacesMessages.instance().addToControlFromResourceBundle("infoGenerique",
				" Opération effectuée avec succes");
			
		
	}
	
	
	/**
	 * cette methode permet de modifier un personnel
	 * 
	 * @param personnel
	 */
	public void modifierParam(Lst_MotifRejet motifRejet) {
		this.motifRejet =motifRejet;
	}

	/**
	 * cette methode permet de supprimer un personnel
	 * 
	 * @param personnel
	 */
	public void supprimerParam(Lst_MotifRejet motifRejet) {
		dataSource.delete(motifRejet);
		dataSource.flush();
		 listeParamStock.actualiserLst_MotifRejet();
		FacesMessages.instance().addToControlFromResourceBundle("infoGenerique", "MotifRejet supprimé avec succes");
	}


	public Lst_MotifRejet getMotifRejet() {
		return motifRejet;
	}


	public void setMotifRejet(Lst_MotifRejet motifRejet) {
		this.motifRejet = motifRejet;
	}

	

}
