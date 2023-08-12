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

import com.chaka.parametrage.entity.Lst_SituationMatri;
import com.chaka.parametrage.service.ListeParam;

/**
 * @author a626257
 *
 */
@Name("situationMatriService")
@Scope(ScopeType.SESSION)
public class SituationMatriService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@In
	private Session dataSource;

	private Lst_SituationMatri situationMatri = new Lst_SituationMatri();


	@Out(required = false)
	private ListeParam listeParamStock = (ListeParam) Component.getInstance("listeParamStock");


	public String versSituationMatri() {
		return "/pages/parametrage/rhospi/situationMatri.xhtml";
	}


	/**
	 * cette methode permet d'ajouter un personnel
	 */
	public void ajouterParam() {
		if (situationMatri.getIdSituationMatri() == null) {
			situationMatri.setDateCreation(new Date());
			dataSource.save(situationMatri);
		    listeParamStock.actualiserLst_SituationMatri();
		} else {
			situationMatri.setDateMaj(new Date());
			 dataSource.update(situationMatri);
			 listeParamStock.actualiserLst_TypeContrat();
		}
		dataSource.flush();
		situationMatri =new Lst_SituationMatri();
	
		FacesMessages.instance().addToControlFromResourceBundle("infoGenerique",
				" Opération effectuée avec succes");
			
		
	}
	
	
	/**
	 * cette methode permet de modifier un personnel
	 * 
	 * @param personnel
	 */
	public void modifierParam(Lst_SituationMatri situationMatri) {
		this.situationMatri =situationMatri;
	}

	/**
	 * cette methode permet de supprimer un personnel
	 * 
	 * @param personnel
	 */
	public void supprimerParam(Lst_SituationMatri situationMatri) {
		dataSource.delete(situationMatri);
		dataSource.flush();
		 listeParamStock.actualiserLst_SituationMatri();
		FacesMessages.instance().addToControlFromResourceBundle("infoGenerique", "SituationMatri supprimé avec succes");
	}


	public Lst_SituationMatri getSituationMatri() {
		return situationMatri;
	}


	public void setSituationMatri(Lst_SituationMatri situationMatri) {
		this.situationMatri = situationMatri;
	}

	

}
