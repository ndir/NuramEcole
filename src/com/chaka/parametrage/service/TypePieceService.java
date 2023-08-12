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

import com.chaka.parametrage.entity.Lst_TypePiece;
import com.chaka.parametrage.service.ListeParam;

/**
 * @author a626257
 *
 */
@Name("typePieceService")
@Scope(ScopeType.SESSION)
public class TypePieceService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@In
	private Session dataSource;

	private Lst_TypePiece typePiece = new Lst_TypePiece();


	@Out(required = false)
	private ListeParam listeParamStock = (ListeParam) Component.getInstance("listeParamStock");


	public String versTypePiece() {
		return "/pages/parametrage/rhospi/typePiece.xhtml";
	}


	/**
	 * cette methode permet d'ajouter un personnel
	 */
	public void ajouterParam() {
		if (typePiece.getIdTypePiece() == null) {
			typePiece.setDateCreation(new Date());
			dataSource.save(typePiece);
		    listeParamStock.actualiserLst_TypePiece();
		} else {
			typePiece.setDateMaj(new Date());
			 dataSource.update(typePiece);
			 listeParamStock.actualiserLst_TypeContrat();
		}
		dataSource.flush();
		typePiece =new Lst_TypePiece();
	
		FacesMessages.instance().addToControlFromResourceBundle("infoGenerique",
				" Opération effectuée avec succes");
			
		
	}
	
	
	/**
	 * cette methode permet de modifier un personnel
	 * 
	 * @param personnel
	 */
	public void modifierParam(Lst_TypePiece typePiece) {
		this.typePiece =typePiece;
	}

	/**
	 * cette methode permet de supprimer un personnel
	 * 
	 * @param personnel
	 */
	public void supprimerParam(Lst_TypePiece typePiece) {
		dataSource.delete(typePiece);
		dataSource.flush();
		 listeParamStock.actualiserLst_TypePiece();
		FacesMessages.instance().addToControlFromResourceBundle("infoGenerique", "TypePiece supprimé avec succes");
	}


	public Lst_TypePiece getTypePiece() {
		return typePiece;
	}


	public void setTypePiece(Lst_TypePiece typePiece) {
		this.typePiece = typePiece;
	}

	

}
