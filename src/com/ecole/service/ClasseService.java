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

import com.ecole.entity.AnneeScolaire;
import com.ecole.entity.Classe;
import com.ecole.entity.MatiereClasse;
import com.ecole.entity.Niveau;

/**
 * @author a626257
 * 
 */
@Scope(ScopeType.SESSION)
@Name("classeService")
public class ClasseService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Classe classe = new Classe();

	private List<Classe> listeClasse = new ArrayList<Classe>();

	private List<MatiereClasse> listeMatClasse = new ArrayList<MatiereClasse>();

	@In
	private Session dataSource;
	
	
	@In
	private AnneeScolaire annee;

	@SuppressWarnings("unchecked")
	public void chargerListeClasse() {
		listeClasse = new ArrayList<Classe>();
		listeClasse = dataSource.createQuery(" From Classe ").list();
	}

	public String versClasse() {
		this.setClasse(new Classe());
		chargerListeClasse();
		System.out.println("Annee "+annee.getAnneeScolaire());
		return "/pages/nuramecole/creerclasse.xhtml";
	}

	@SuppressWarnings("unchecked")
	public List<Niveau> listeNiveau() {
		return dataSource.createQuery(" From Niveau").list();
	}

	public void annulerAjout() {
		this.setClasse(new Classe());
	}

	public void ajouterClasse() {
		if (this.classe.getLibelle().isEmpty()) {
			FacesMessages.instance().addToControlFromResourceBundle(
					"erreurGenerique", "Veuillez renseigner le libellé");
			return;
		}
		if (this.classe.getNiveau() == null) {
			FacesMessages.instance().addToControlFromResourceBundle(
					"erreurGenerique", "Veuillez chosir un niveau");
			return;
		}
		if (classe.getIdclasse() == null) {
			dataSource.save(classe);
		} else {
			dataSource.update(classe);
		}
		chargerListeClasse();
		this.setClasse(new Classe());
		FacesMessages.instance().addToControlFromResourceBundle(
				"infoGenerique", "Classe ajoutée avec succés");
	}

	public void versModifierClasse(Classe classe) {
		this.setClasse(classe);
	}

	@SuppressWarnings("unchecked")
	public void zoomListeMatiere(Classe classe) {
		listeMatClasse = new ArrayList<MatiereClasse>();
		listeMatClasse = dataSource
				.createQuery(
						" From MatiereClasse m inner join fetch m.classe c inner join fetch m.matiere where c=:pc ")
				.setParameter("pc", classe).list();
	}
	
	public void supprimerMatiere(MatiereClasse mat){
		dataSource.delete(mat);
	}

	public Classe getClasse() {
		return classe;
	}

	public void setClasse(Classe classe) {
		this.classe = classe;
	}

	public List<Classe> getListeClasse() {
		return listeClasse;
	}

	public void setListeClasse(List<Classe> listeClasse) {
		this.listeClasse = listeClasse;
	}

	public List<MatiereClasse> getListeMatClasse() {
		return listeMatClasse;
	}

	public void setListeMatClasse(List<MatiereClasse> listeMatClasse) {
		this.listeMatClasse = listeMatClasse;
	}

}
