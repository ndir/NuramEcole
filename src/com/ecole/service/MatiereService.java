/**
 * 
 */
package com.ecole.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;





import com.ecole.entity.Matiere;

/**
 * @author a626257
 * 
 */
@Scope(ScopeType.SESSION)
@Name("matiereService")
public class MatiereService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@In
	private Session dataSource;

	private List<Matiere> listeMatiere = new ArrayList<Matiere>();

	private Matiere matiere = new Matiere();

	@SuppressWarnings("unchecked")
	public void chargerListeMat() {
		listeMatiere = new ArrayList<Matiere>();
		listeMatiere = dataSource.createQuery(" From Matiere ").list();
	}

	public String versMatieres() {
		this.setMatiere(new Matiere());
		chargerListeMat();
		return "/pages/ecole/creermatiere.xhtml";
	}

	public void annulerAjout() {
		this.setMatiere(new Matiere());
	}

	public Matiere getMatiereFrom() {
		return (Matiere) dataSource
				.createQuery("From Matiere where libelle=:pl")
				.setParameter("pl", matiere.getLibelle()).uniqueResult();
	}

	public void ajouterMat() {
		if (this.matiere.getLibelle().isEmpty()) {
			FacesMessages.instance().addToControlFromResourceBundle(
					"erreurGenerique", "Veuillez renseigner le libell�");
			return;
		}

		Matiere m = getMatiereFrom();

		if (m != null) {
			FacesMessages.instance().addToControlFromResourceBundle(
					"erreurGenerique", "la mati�re "+matiere.getLibelle() +" existe d�ja");
			return;
		}

		if (matiere.getIdmatiere() == null) {
			dataSource.save(matiere);
		} else {
			dataSource.update(matiere);
		}
		chargerListeMat();
		this.setMatiere(new Matiere());
		FacesMessages.instance().addToControlFromResourceBundle(
				"infoGenerique", "matiere ajout�e avec succ�s");
	}

	public void versModifierMat(Matiere matiere) {
		this.setMatiere(matiere);
	}

	public List<Matiere> getListeMatiere() {
		return listeMatiere;
	}

	public void setListeMatiere(List<Matiere> listeMatiere) {
		this.listeMatiere = listeMatiere;
	}

	public Matiere getMatiere() {
		return matiere;
	}

	public void setMatiere(Matiere matiere) {
		this.matiere = matiere;
	}

}
