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

import com.chaka.parametrage.entity.Lst_CategoriProf;
import com.chaka.parametrage.service.ListeParam;

/**
 * @author a626257
 *
 */
@Name("categoriProfService")
@Scope(ScopeType.SESSION)
public class CategoriProfService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@In
	private Session dataSource;

	private Lst_CategoriProf categoriProf = new Lst_CategoriProf();


	@Out(required = false)
	private ListeParam listeParamStock = (ListeParam) Component.getInstance("listeParamStock");


	public String versCategoriProf() {
		return "/pages/parametrage/rhospi/categoriProf.xhtml";
	}


	/**
	 * cette methode permet d'ajouter un personnel
	 */
	public void ajouterParam() {
		if (categoriProf.getIdCategoriProf() == null) {
			categoriProf.setDateCreation(new Date());
			dataSource.save(categoriProf);
		    listeParamStock.actualiserCategoriProf();
		} else {
			categoriProf.setDateMaj(new Date());
			 dataSource.update(categoriProf);
			 listeParamStock.actualiserLst_TypeContrat();
		}
		dataSource.flush();
		categoriProf =new Lst_CategoriProf();
	
		FacesMessages.instance().addToControlFromResourceBundle("infoGenerique",
				" Opération effectuée avec succes");
			
		
	}
	
	
	/**
	 * cette methode permet de modifier un personnel
	 * 
	 * @param personnel
	 */
	public void modifierParam(Lst_CategoriProf categoriProf) {
		this.categoriProf =categoriProf;
	}

	/**
	 * cette methode permet de supprimer un personnel
	 * 
	 * @param personnel
	 */
	public void supprimerParam(Lst_CategoriProf categoriProf) {
		dataSource.delete(categoriProf);
		dataSource.flush();
		 listeParamStock.actualiserCategoriProf();
		FacesMessages.instance().addToControlFromResourceBundle("infoGenerique", "CategoriProf supprimé avec succes");
	}


	public Lst_CategoriProf getCategoriProf() {
		return categoriProf;
	}


	public void setCategoriProf(Lst_CategoriProf categoriProf) {
		this.categoriProf = categoriProf;
	}

	

}
