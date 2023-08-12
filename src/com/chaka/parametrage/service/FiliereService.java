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

import com.chaka.parametrage.entity.Lst_Filiere;
import com.chaka.parametrage.service.ListeParam;

/**
 * @author a626257
 *
 */
@Name("filiereService")
@Scope(ScopeType.SESSION)
public class FiliereService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@In
	private Session dataSource;

	private Lst_Filiere filiere = new Lst_Filiere();


	@Out(required = false)
	private ListeParam listeParamStock = (ListeParam) Component.getInstance("listeParamStock");


	public String versFiliere() {
		return "/pages/parametrage/rhospi/filiere.xhtml";
	}


	/**
	 * cette methode permet d'ajouter un personnel
	 */
	public void ajouterParam() {
		if (filiere.getIdFiliere() == null) {
			filiere.setDateCreation(new Date());
			dataSource.save(filiere);
			listeParamStock.actualiserFiliere();
		} else {
		//	filiere.setDateMaj(new Date());
			 dataSource.update(filiere);
			 listeParamStock.actualiserFiliere();
		}
		dataSource.flush();
		filiere =new Lst_Filiere();
	
		FacesMessages.instance().addToControlFromResourceBundle("infoGenerique",
				" Opération effectuée avec succes");
			
		
	}
	
	
	/**
	 * cette methode permet de modifier un personnel
	 * 
	 * @param personnel
	 */
	public void modifierParam(Lst_Filiere filiere) {
		this.filiere =filiere;
	}

	/**
	 * cette methode permet de supprimer un personnel
	 * 
	 * @param personnel
	 */
	public void supprimerParam(Lst_Filiere filiere) {
		dataSource.delete(filiere);
		dataSource.flush();
		listeParamStock.actualiserFiliere();
		FacesMessages.instance().addToControlFromResourceBundle("infoGenerique", "Filiere supprimé avec succes");
	}


	public Lst_Filiere getFiliere() {
		return filiere;
	}


	public void setFiliere(Lst_Filiere filiere) {
		this.filiere = filiere;
	}

	

}
