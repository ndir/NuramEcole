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
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;

import com.chaka.projet.entity.Utilisateur;
import com.ecole.entity.Matiere;
import com.ecole.entity.Niveau;

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

	private Niveau niveau = new Niveau();

	private List<Niveau> listeNiveau = new ArrayList<Niveau>();

	private String typenote;

	@In(required = false)
	@Out(required = false)
	private Utilisateur utilisateur;

	@SuppressWarnings("unchecked")
	public void chargerListeMat() {
		listeMatiere = new ArrayList<Matiere>();
		listeMatiere = dataSource.createQuery(" From Matiere m inner join fetch m.institution i where i=:pi")
				.setParameter("pi", utilisateur.getInstitution()).list();

	}

	public String versMatieres() {
		this.setMatiere(new Matiere());
		chargerListeMat();
		return "/pages/nuramecole/creermatiere.xhtml";
	}

	public void annulerAjout() {
		this.setMatiere(new Matiere());
	}

	public Matiere getMatiereFrom() {
		return (Matiere) dataSource.createQuery("From Matiere where libelle=:pl")
				.setParameter("pl", matiere.getLibelle()).uniqueResult();
	}

	public void ajouterMat() {
		if (this.matiere.getLibelle().isEmpty()) {
			FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique",
					"Veuillez renseigner le libellé");
			return;
		}

		if (niveau.getId() == null) {
			return;
		}

		Matiere m = getMatiereFrom();

		if (m != null) {
			FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique",
					"la matière " + matiere.getLibelle() + " existe déja");
			return;
		}

		if (matiere.getIdmatiere() == null) {
			matiere.setNiveau(niveau);
			matiere.setInstitution(utilisateur.getInstitution());
			dataSource.save(matiere);
		} else {
			dataSource.update(matiere);
		}
		chargerListeMat();
		this.setMatiere(new Matiere());
		FacesMessages.instance().addToControlFromResourceBundle("infoGenerique", "matiere ajoutée avec succés");
	}

	public void versModifierMat(Matiere matiere) {
		this.setNiveau(matiere.getNiveau());
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

	public Niveau getNiveau() {
		return niveau;
	}

	public void setNiveau(Niveau niveau) {
		this.niveau = niveau;
	}

	public List<Niveau> getListeNiveau() {
		listeNiveau = new ArrayList<Niveau>();
		listeNiveau = dataSource.createQuery(" From Niveau where code <>:pcode ").setParameter("pcode", "PRE").list();
		return listeNiveau;
	}

	public void setListeNiveau(List<Niveau> listeNiveau) {
		this.listeNiveau = listeNiveau;
	}

	public String getTypenote() {
		return typenote;
	}

	public void setTypenote(String typenote) {
		this.typenote = typenote;
	}

}
