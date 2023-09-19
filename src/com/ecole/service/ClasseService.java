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
import com.ecole.entity.ParamInscription;

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

	private String niv;
	
	private String typeNote;

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

		return "/pages/nuramecole/creerclasse.xhtml";
	}

	public String versAcceuil() {
		return "/pages/nuramecole/template/index.xhtml";
	}

	@SuppressWarnings("unchecked")
	public List<Niveau> listeNiveau() {
		return dataSource.createQuery(" From Niveau").list();
	}

	public void annulerAjout() {
		this.setClasse(new Classe());
	}
	
	public void chargerListeNiveau() {
		if (classe.getNiveau().getCode().equalsIgnoreCase("ELE")) {
			setTypeNote("1");
		}
		if (classe.getNiveau().getCode().equalsIgnoreCase("MOY")) {
			setTypeNote("2");
		}

		if (classe.getNiveau().getCode().equalsIgnoreCase("SEC")) {
			setTypeNote("3");
		}
		if (classe.getNiveau().getCode().equalsIgnoreCase("PRE")) {
			setTypeNote("4");
		}
	}

	public void ajouterClasse() {
		if (!niv.isEmpty())
			classe.setNiv(niv);
		if (this.classe.getLibelle().isEmpty()) {
			FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique",
					"Veuillez renseigner le libellé");
			return;
		}
		if (this.classe.getNiveau() == null) {
			FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique", "Veuillez chosir un niveau");
			return;
		}
		if (classe.getIdclasse() == null) {
			dataSource.save(classe);
		} else {

			dataSource.update(classe);
		}
		chargerListeClasse();
		this.setClasse(new Classe());
		FacesMessages.instance().addToControlFromResourceBundle("infoGenerique", "Classe ajoutée avec succés");
	}

	public Double getDroitInscription(Classe classe) {

		double droit = 0.0;

		ParamInscription p = (ParamInscription) dataSource
				.createQuery("From ParamInscription m inner join fetch m.classe c  where c=:pc")
				.setParameter("pc", classe).uniqueResult();
		if (p != null && p.getDroit_ins() != null)
			droit = p.getDroit_ins();
		return droit;
	}

	public Double getMensualite(Classe classe) {

		double droit = 0.0;

		ParamInscription p = (ParamInscription) dataSource
				.createQuery("From ParamInscription m inner join fetch m.classe c  where c=:pc")
				.setParameter("pc", classe).uniqueResult();
		if (p != null && p.getMensualite() != null)
			droit = p.getMensualite();
		return droit;
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

	public void supprimerMatiere(MatiereClasse mat) {
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

	public String getNiv() {
		return niv;
	}

	public void setNiv(String niv) {
		this.niv = niv;
	}

	public String getTypeNote() {
		return typeNote;
	}

	public void setTypeNote(String typeNote) {
		this.typeNote = typeNote;
	}

}
