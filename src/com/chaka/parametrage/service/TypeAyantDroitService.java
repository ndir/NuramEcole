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

import com.chaka.parametrage.entity.Lst_TypeAyantDroit;
import com.chaka.parametrage.service.ListeParam;

/**
 * @author a626257
 *
 */
@Name("typeAyantDroitService")
@Scope(ScopeType.SESSION)
public class TypeAyantDroitService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@In
	private Session dataSource;

	private Lst_TypeAyantDroit typeAyantDroit = new Lst_TypeAyantDroit();


	@Out(required = false)
	private ListeParam listeParamStock = (ListeParam) Component.getInstance("listeParamStock");


	public String versTypeAyantDroit() {
		return "/pages/parametrage/rhospi/typeAyantDroit.xhtml";
	}


	/**
	 * cette methode permet d'ajouter un personnel
	 */
	public void ajouterParam() {
		if (typeAyantDroit.getIdTypeAyantDroit() == null) {
			typeAyantDroit.setDateCreation(new Date());
			dataSource.save(typeAyantDroit);
		    listeParamStock.actualiserLst_TypeAyantDroit();
		} else {
			typeAyantDroit.setDateMaj(new Date());
			 dataSource.update(typeAyantDroit);
			 listeParamStock.actualiserLst_TypeContrat();
		}
		dataSource.flush();
		typeAyantDroit =new Lst_TypeAyantDroit();
	
		FacesMessages.instance().addToControlFromResourceBundle("infoGenerique",
				" Opération effectuée avec succes");
			
		
	}
	
	
	/**
	 * cette methode permet de modifier un personnel
	 * 
	 * @param personnel
	 */
	public void modifierParam(Lst_TypeAyantDroit typeAyantDroit) {
		this.typeAyantDroit =typeAyantDroit;
	}

	/**
	 * cette methode permet de supprimer un personnel
	 * 
	 * @param personnel
	 */
	public void supprimerParam(Lst_TypeAyantDroit typeAyantDroit) {
		dataSource.delete(typeAyantDroit);
		dataSource.flush();
		 listeParamStock.actualiserLst_TypeAyantDroit();
		FacesMessages.instance().addToControlFromResourceBundle("infoGenerique", "TypeAyantDroit supprimé avec succes");
	}


	public Lst_TypeAyantDroit getTypeAyantDroit() {
		return typeAyantDroit;
	}


	public void setTypeAyantDroit(Lst_TypeAyantDroit typeAyantDroit) {
		this.typeAyantDroit = typeAyantDroit;
	}

	

}
