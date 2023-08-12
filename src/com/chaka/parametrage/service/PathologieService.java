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

import com.chaka.parametrage.entity.Lst_Pathologie;
import com.chaka.parametrage.service.ListeParam;

/**
 * @author a626257
 *
 */
@Name("pathologieService")
@Scope(ScopeType.SESSION)
public class PathologieService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@In
	private Session dataSource;

	private Lst_Pathologie pathologie = new Lst_Pathologie();


	@Out(required = false)
	private ListeParam listeParamStock = (ListeParam) Component.getInstance("listeParamStock");


	public String versPathologie() {
		return "/pages/parametrage/rhospi/pathologie.xhtml";
	}


	/**
	 * cette methode permet d'ajouter un personnel
	 */
	public void ajouterParam() {
		if (pathologie.getIdPathologie() == null) {
			pathologie.setDateCreation(new Date());
			dataSource.save(pathologie);
		    listeParamStock.actualiserLst_Pathologie();
		} else {
			pathologie.setDateMaj(new Date());
			 dataSource.update(pathologie);
			 listeParamStock.actualiserLst_TypeContrat();
		}
		dataSource.flush();
		pathologie =new Lst_Pathologie();
	
		FacesMessages.instance().addToControlFromResourceBundle("infoGenerique",
				" Opération effectuée avec succes");
			
		
	}
	
	
	/**
	 * cette methode permet de modifier un personnel
	 * 
	 * @param personnel
	 */
	public void modifierParam(Lst_Pathologie pathologie) {
		this.pathologie =pathologie;
	}

	/**
	 * cette methode permet de supprimer un personnel
	 * 
	 * @param personnel
	 */
	public void supprimerParam(Lst_Pathologie pathologie) {
		dataSource.delete(pathologie);
		dataSource.flush();
		 listeParamStock.actualiserLst_Pathologie();
		FacesMessages.instance().addToControlFromResourceBundle("infoGenerique", "Pathologie supprimé avec succes");
	}


	public Lst_Pathologie getPathologie() {
		return pathologie;
	}


	public void setPathologie(Lst_Pathologie pathologie) {
		this.pathologie = pathologie;
	}

	

}
