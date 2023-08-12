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

import com.chaka.parametrage.entity.Lst_Service;
import com.chaka.parametrage.service.ListeParam;

/**
 * @author a626257
 *
 */
@Name("serviceService")
@Scope(ScopeType.SESSION)
public class ServiceService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@In
	private Session dataSource;

	private Lst_Service service = new Lst_Service();


	@Out(required = false)
	private ListeParam listeParamStock = (ListeParam) Component.getInstance("listeParamStock");


	public String versService() {
		return "/pages/parametrage/rhospi/service.xhtml";
	}


	/**
	 * cette methode permet d'ajouter un personnel
	 */
	public void ajouterParam() {
		if (service.getIdService() == null) {
			service.setDateCreation(new Date());
			dataSource.save(service);
		    listeParamStock.actualiserLst_Service();
		} else {
			service.setDateMaj(new Date());
			 dataSource.update(service);
			 listeParamStock.actualiserLst_TypeContrat();
		}
		dataSource.flush();
		service =new Lst_Service();
	
		FacesMessages.instance().addToControlFromResourceBundle("infoGenerique",
				" Opération effectuée avec succes");
			
		
	}
	
	
	/**
	 * cette methode permet de modifier un personnel
	 * 
	 * @param personnel
	 */
	public void modifierParam(Lst_Service service) {
		this.service =service;
	}

	/**
	 * cette methode permet de supprimer un personnel
	 * 
	 * @param personnel
	 */
	public void supprimerParam(Lst_Service service) {
		dataSource.delete(service);
		dataSource.flush();
		 listeParamStock.actualiserLst_Service();
		FacesMessages.instance().addToControlFromResourceBundle("infoGenerique", "Service supprimé avec succes");
	}


	public Lst_Service getService() {
		return service;
	}


	public void setService(Lst_Service service) {
		this.service = service;
	}

	

}
