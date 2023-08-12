/**
 * 
 */
package com.chaka.parametrage.service;

import java.io.Serializable;
import java.util.Date;

import org.jboss.seam.Component;



import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;
import org.hibernate.Session;

import com.chaka.parametrage.entity.Institution;
import com.chaka.parametrage.service.ListeParam;

/**
 * @author antou
 * 
 */
@Name("institutionService")
@Scope(ScopeType.SESSION)
public class InstitutionService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Institution intitutionEncours = new Institution();


	@In
	private Session dataSource;
	
	@Out(required = false)
	private ListeParam listeParamStock=(ListeParam)Component.getInstance("listeParamStock");
	


	/**
	 * cette methode nous redirige vers la page de creation des sens
	 * 
	 * @return
	 */
	public String versGestionInstitution() {
		this.intitutionEncours=listeParamStock.getInstitution();
		return "/pages/parametrage/rhospi/institution.xhtml";
	}

	
	/**
	 * cette methode permet d'ajouter un sens
	 */
	public void ajouterInstitution() {
        
		try {
			if (intitutionEncours.getIdInstitution() == null) {
				intitutionEncours.setDateMaj(new Date());
				dataSource.save(intitutionEncours);
				dataSource.flush();
				listeParamStock.actualiserListeInstitution();
				FacesMessages.instance().addToControlFromResourceBundle(
						"infoGenerique", "Création Effectué avec succès");
			} else {
				if(intitutionEncours.getDateMaj()==null)
					intitutionEncours.setDateMaj(new Date());
				dataSource.update(intitutionEncours);
				dataSource.flush();
				
				listeParamStock.actualiserListeInstitution();
				FacesMessages.instance().addToControlFromResourceBundle(
						"infoGenerique", "Modification Effectué avec succès");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * cette methode permet de modifier un sens
	 * 
	 * @param intitutionEncours
	 */
	public void initmodifier(Institution op) {
		
		intitutionEncours = (Institution) dataSource.get(Institution.class, op.getIdInstitution());
	}
	
  

	/**
	 * cette methode permet de supprimer un sens
	 * 
	 * @param sens
	 */
	public void supprimer(Institution op) {
		 try {
				dataSource.delete(op);
				listeParamStock.actualiserListeInstitution();
				FacesMessages.instance().addToControlFromResourceBundle(
						"infoGenerique", "Opération Effectué avec succès");
			 }catch (Throwable t) {
				   t.printStackTrace();
			 }
	}
	
   public void modifier( ) {
	   try {
			this.dataSource.merge(intitutionEncours);
			this.dataSource.flush();
			listeParamStock.actualiserListeInstitution();
	   }catch (Throwable t) {
		   t.printStackTrace();
	   }
	}


public Institution getintitutionEncours() {
	return intitutionEncours;
}


public void setintitutionEncours(Institution intitutionEncours) {
	this.intitutionEncours = intitutionEncours;
}



	

}
