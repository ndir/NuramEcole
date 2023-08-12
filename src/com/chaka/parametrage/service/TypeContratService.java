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

import com.chaka.parametrage.entity.Lst_TypeContrat;
import com.chaka.parametrage.service.ListeParam;

/**
 * @author a626257
 *
 */
@Name("typeContratService")
@Scope(ScopeType.SESSION)
public class TypeContratService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@In
	private Session dataSource;

	private Lst_TypeContrat typeContrat = new Lst_TypeContrat();


	@Out(required = false)
	private ListeParam listeParamStock = (ListeParam) Component.getInstance("listeParamStock");


	public String versTypeContrat() {
		return "/pages/parametrage/rhospi/typeContrat.xhtml";
	}


	/**
	 * cette methode permet d'ajouter un personnel
	 */
	public void ajouterParam() {
			if (typeContrat.getIdTypeContrat() == null) {
				typeContrat.setDateCreation(new Date());
				dataSource.save(typeContrat);
			    listeParamStock.actualiserLst_TypeContrat();
			} else {
				typeContrat.setDateMaj(new Date());
				 dataSource.update(typeContrat);
				 listeParamStock.actualiserLst_TypeContrat();
			}
			dataSource.flush();
			typeContrat =new Lst_TypeContrat();
		
			FacesMessages.instance().addToControlFromResourceBundle("infoGenerique",
					" Opération effectuée avec succes");
			
		
	}
	
	
	/**
	 * cette methode permet de modifier un personnel
	 * 
	 * @param personnel
	 */
	public void modifierParam(Lst_TypeContrat typeContrat) {
		
		this.typeContrat =typeContrat;
	}

	/**
	 * cette methode permet de supprimer un personnel
	 * 
	 * @param personnel
	 */
	public void supprimerParam(Lst_TypeContrat typeContrat) {
		dataSource.delete(typeContrat);
		dataSource.flush();
		 listeParamStock.actualiserLst_TypeContrat();
		FacesMessages.instance().addToControlFromResourceBundle("infoGenerique", "Personnel supprimé avec succes");
	}


	public Lst_TypeContrat getTypeContrat() {
		return typeContrat;
	}


	public void setTypeContrat(Lst_TypeContrat typeContrat) {
		this.typeContrat = typeContrat;
	}

	

}
