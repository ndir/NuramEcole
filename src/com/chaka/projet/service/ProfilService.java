package com.chaka.projet.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;

import com.chaka.projet.entity.Profile;
import com.chaka.projet.entity.Utilisateur;

@Scope(ScopeType.SESSION)
@Name("profilService")
public class ProfilService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2219683920607395630L;
	
	public Profile profil = new Profile();
	
	private List<Profile> lstProfils = new ArrayList<Profile>();
	
	@In
	private Session dataSource;
	
	@In(required = false)
	@Out(required = false)
	private Utilisateur utilisateur;
	
	public void init() {
		profil = new Profile();
		lstProfils = new ArrayList<Profile>();
	}
	public ProfilService() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * @return the profil
	 */
	public Profile getProfil() {
		return profil;
	}
	/**
	 * @param profil the profil to set
	 */
	public void setProfil(Profile profil) {
		this.profil = profil;
	}
	/**
	 * @return the lstProfils
	 */
	public List<Profile> getLstProfils() {
		return lstProfils;
	}
	/**
	 * @param lstProfils the lstProfils to set
	 */
	public void setLstProfils(List<Profile> lstProfils) {
		this.lstProfils = lstProfils;
	}
	
	
	public String versGestionMenu (){
		//init();
		chargerListe();
		return "/pages/administration/gestionprofil.xhtml";
		
	}
	
	@SuppressWarnings("unchecked")
	public void chargerListe(){
		String request = "select p from Profile p" ;//todo source erreur
		lstProfils=dataSource.createQuery(request).list();
	}
	
	
	public String ajouterModifierProfil(){
	
		
		if (this.profil.getIdProfile() == null) {
		//profil.setDateCreation(new Date());
	//	profil.setDateMaj(new Date());
			dataSource.save(profil);
			dataSource.flush();
			FacesMessages.instance().addToControlFromResourceBundle(
					"infoGenerique", "fonction.message.ajout");
			this.init();
			this.chargerListe();
			
			//log.info("Creation de la fonction  #0 par #1 le #2",
			//this.fonction.getIdFonction(), this.utilisateur.getIdUtilisateur(),new Date());
		} else {
		
			dataSource.update(profil);
			dataSource.flush();
			FacesMessages.instance().addToControlFromResourceBundle(
					"infoGenerique", "fonction.message.modification");
			this.chargerListe();
			
			// this.log.info("Modification d'une banque #0 par #1  le #2",
			//		 this.fonction.getIdFonction(), this.utilisateur.getIdUtilisateur(),new Date());
			 
		}
		return null;
	}

}
