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

import com.chaka.parametrage.entity.Lst_NivEtude;
import com.chaka.parametrage.service.ListeParam;

/**
 * @author a626257
 *
 */
@Name("nivEtudeService")
@Scope(ScopeType.SESSION)
public class NivEtudeService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@In
	private Session dataSource;

	private Lst_NivEtude nivEtude = new Lst_NivEtude();


	@Out(required = false)
	private ListeParam listeParamStock = (ListeParam) Component.getInstance("listeParamStock");


	public String versNivEtude() {
		return "/pages/parametrage/rhospi/nivEtude.xhtml";
	}


	/**
	 * cette methode permet d'ajouter un personnel
	 */
	public void ajouterParam() {
		if (nivEtude.getIdNivEtude() == null) {
			nivEtude.setDateCreation(new Date());
			dataSource.save(nivEtude);
		    listeParamStock.actualiserNivEtude();
		} else {
			nivEtude.setDateMaj(new Date());
			 dataSource.update(nivEtude);
			 listeParamStock.actualiserLst_TypeContrat();
		}
		dataSource.flush();
		nivEtude =new Lst_NivEtude();
	
		FacesMessages.instance().addToControlFromResourceBundle("infoGenerique",
				" Opération effectuée avec succes");
			
		
	}
	
	
	/**
	 * cette methode permet de modifier un personnel
	 * 
	 * @param personnel
	 */
	public void modifierParam(Lst_NivEtude nivEtude) {
		this.nivEtude =nivEtude;
	}

	/**
	 * cette methode permet de supprimer un personnel
	 * 
	 * @param personnel
	 */
	public void supprimerParam(Lst_NivEtude nivEtude) {
		dataSource.delete(nivEtude);
		dataSource.flush();
		 listeParamStock.actualiserNivEtude();
		FacesMessages.instance().addToControlFromResourceBundle("infoGenerique", "NivEtude supprimé avec succes");
	}


	public Lst_NivEtude getNivEtude() {
		return nivEtude;
	}


	public void setNivEtude(Lst_NivEtude nivEtude) {
		this.nivEtude = nivEtude;
	}

	

}
