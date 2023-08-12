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

import com.chaka.parametrage.entity.Lst_Sexe;
import com.chaka.parametrage.service.ListeParam;

/**
 * @author a626257
 *
 */
@Name("sexeService")
@Scope(ScopeType.SESSION)
public class SexeService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@In
	private Session dataSource;

	private Lst_Sexe sexe = new Lst_Sexe();


	@Out(required = false)
	private ListeParam listeParamStock = (ListeParam) Component.getInstance("listeParamStock");


	public String versSexe() {
		return "/pages/parametrage/rhospi/sexe.xhtml";
	}


	/**
	 * cette methode permet d'ajouter un personnel
	 */
	public void ajouterParam() {
		if (sexe.getIdSexe() == null) {
			sexe.setDateCreation(new Date());
			dataSource.save(sexe);
		    listeParamStock.actualiserLst_Sexe();
		} else {
			sexe.setDateMaj(new Date());
			 dataSource.update(sexe);
			 listeParamStock.actualiserLst_TypeContrat();
		}
		dataSource.flush();
		sexe =new Lst_Sexe();
	
		FacesMessages.instance().addToControlFromResourceBundle("infoGenerique",
				" Opération effectuée avec succes");
			
		
	}
	
	
	/**
	 * cette methode permet de modifier un personnel
	 * 
	 * @param personnel
	 */
	public void modifierParam(Lst_Sexe sexe) {
		this.sexe =sexe;
	}

	/**
	 * cette methode permet de supprimer un personnel
	 * 
	 * @param personnel
	 */
	public void supprimerParam(Lst_Sexe sexe) {
		dataSource.delete(sexe);
		dataSource.flush();
		 listeParamStock.actualiserLst_Sexe();
		FacesMessages.instance().addToControlFromResourceBundle("infoGenerique", "Sexe supprimé avec succes");
	}


	public Lst_Sexe getSexe() {
		return sexe;
	}


	public void setSexe(Lst_Sexe sexe) {
		this.sexe = sexe;
	}

	

}
