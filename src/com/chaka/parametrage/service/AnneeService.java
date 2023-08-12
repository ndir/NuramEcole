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

import com.chaka.parametrage.entity.Lst_Annee;
import com.chaka.parametrage.service.ListeParam;

/**
 * @author a626257
 *
 */
@Name("anneeService")
@Scope(ScopeType.SESSION)
public class AnneeService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@In
	private Session dataSource;

	private Lst_Annee annee = new Lst_Annee();


	@Out(required = false)
	private ListeParam listeParamStock = (ListeParam) Component.getInstance("listeParamStock");


	public String versAnnee() {
		return "/pages/parametrage/rhospi/annee.xhtml";
	}


	/**
	 * cette methode permet d'ajouter un personnel
	 */
	public void ajouterParam() {
			if (annee.getIdAnnee() == null) {
				annee.setDateCreation(new Date());
				dataSource.save(annee);
			    listeParamStock.actualiserAnnee();
			} else {
				annee.setDateMaj(new Date());
				 dataSource.update(annee);
				 listeParamStock.actualiserAnnee();
			}
			dataSource.flush();
			annee =new Lst_Annee();
		
			FacesMessages.instance().addToControlFromResourceBundle("infoGenerique",
					" Opération effectuée avec succes");
			
		
	}
	
	
	/**
	 * cette methode permet de modifier un personnel
	 * 
	 * @param personnel
	 */
	public void modifierParam(Lst_Annee annee) {
		
		this.annee =annee;
	}

	/**
	 * cette methode permet de supprimer un personnel
	 * 
	 * @param personnel
	 */
	public void supprimerParam(Lst_Annee annee) {
		dataSource.delete(annee);
		dataSource.flush();
		 listeParamStock.actualiserAnnee();
		FacesMessages.instance().addToControlFromResourceBundle("infoGenerique", "Annee supprimé avec succes");
	}


	public Lst_Annee getAnnee() {
		return annee;
	}


	public void setAnnee(Lst_Annee annee) {
		this.annee = annee;
	}

	

}
