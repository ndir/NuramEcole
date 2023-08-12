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

import com.chaka.parametrage.entity.Lst_Statut;
import com.chaka.parametrage.service.ListeParam;

/**
 * @author a626257
 *
 */
@Name("statutService")
@Scope(ScopeType.SESSION)
public class StatutService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@In
	private Session dataSource;

	private Lst_Statut statut = new Lst_Statut();


	@Out(required = false)
	private ListeParam listeParamStock = (ListeParam) Component.getInstance("listeParamStock");


	public String versStatut() {
		return "/pages/parametrage/rhospi/statut.xhtml";
	}


	/**
	 * cette methode permet d'ajouter un personnel
	 */
	public void ajouterParam() {
		if (statut.getIdStatut() == null) {
			statut.setDateCreation(new Date());
			dataSource.save(statut);
		    listeParamStock.actualiserListeStatut();
		} else {
			statut.setDateMaj(new Date());
			 dataSource.update(statut);
			 listeParamStock.actualiserLst_TypeContrat();
		}
		dataSource.flush();
		statut =new Lst_Statut();
	
		FacesMessages.instance().addToControlFromResourceBundle("infoGenerique",
				" Opération effectuée avec succes");
			
		
	}
	
	
	/**
	 * cette methode permet de modifier un personnel
	 * 
	 * @param personnel
	 */
	public void modifierParam(Lst_Statut statut) {
		this.statut =statut;
	}

	/**
	 * cette methode permet de supprimer un personnel
	 * 
	 * @param personnel
	 */
	public void supprimerParam(Lst_Statut statut) {
		dataSource.delete(statut);
		dataSource.flush();
		 listeParamStock.actualiserListeStatut();
		FacesMessages.instance().addToControlFromResourceBundle("infoGenerique", "Statut supprimé avec succes");
	}


	public Lst_Statut getStatut() {
		return statut;
	}


	public void setStatut(Lst_Statut statut) {
		this.statut = statut;
	}

	

}
