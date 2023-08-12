/**
 * 
 */
package com.rhospi.commons;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;

import com.chaka.projet.entity.Utilisateur;
import com.rhospi.commons.ChakaUtils.ExportOption;


/**
 * @author Gora
 *
 */
@Name("fileUploadService")
@Scope(ScopeType.SESSION)
public class FileUploadService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 712785088206643892L;

	/****
	 * contenu
	 */
	private byte [] contenu;

	/****
	 * nom du fichier
	 */
	private String nomFichier;
	
	
	/***
	 * 
	 */
	private Integer id = 1;

	
	private byte [] bytesContent;
	/**
	 * 
	 */
	public FileUploadService() {
		// TODO Auto-generated constructor stub
	}
	
	
    public void imprimer(String repertoire,String reportShortName, Map<String,Object> params, Collection  listeDonnees, Utilisateur utilisateur,ExportOption pdf){
    	id++;
    	if(ChakaUtils.ExportOption.PDF.equals(pdf))
    	   bytesContent = ChakaUtils.chakaUtils.getContentBytesPdf(repertoire, reportShortName, params, listeDonnees, null, utilisateur);
    	else ChakaUtils.chakaUtils.generateOtherReport(repertoire, reportShortName, params, listeDonnees, null, utilisateur, pdf);
    	
    }
	
	public void paintPdf(OutputStream out,Object data) throws IOException {    	       	       	       	    	  	
  		if(bytesContent!=null)  						   			
			out.write(bytesContent);
  		  	
	}

	
	public void fileUploadListenerDoc(UploadEvent event) {
		id++;
		if (event == null) {
		
		} else {
			// on recupere l'item envoye
			UploadItem uploadItem = event.getUploadItem();
			
			contenu = uploadItem.getData();
			nomFichier = uploadItem.getFileName();
		}

	}
	
	public void paint(OutputStream out,Object data) throws IOException {    	       	       	       	    	  	
  		if(contenu!=null)  						   			
			out.write(contenu);
  		  	
	}


	/**
	 * @return the contenu
	 */
	public byte[] getContenu() {
		return contenu;
	}

	/**
	 * @param contenu the contenu to set
	 */
	public void setContenu(byte[] contenu) {
		this.contenu = contenu;
	}

	/**
	 * @return the nomFichier
	 */
	public String getNomFichier() {
		return nomFichier;
	}

	/**
	 * @param nomFichier the nomFichier to set
	 */
	public void setNomFichier(String nomFichier) {
		this.nomFichier = nomFichier;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}


}
