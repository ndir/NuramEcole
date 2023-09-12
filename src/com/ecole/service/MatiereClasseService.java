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
import com.ecole.entity.Evaluation;
import com.ecole.entity.Matiere;
import com.ecole.entity.MatiereClasse;
import com.ecole.entity.Niveau;

/**
 * @author a626257
 * 
 */
@Scope(ScopeType.SESSION)
@Name("matclasseService")
public class MatiereClasseService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@In
	private Session dataSource;

	private List<Classe> listeClasse = new ArrayList<Classe>();

	private List<Matiere> listeMatiere = new ArrayList<Matiere>();

	private List<Niveau> listeNiveau = new ArrayList<Niveau>();

	private MatiereClasse matClasse = new MatiereClasse();

	private Niveau niveau = new Niveau();

	private AnneeScolaire anneeScolaire = new AnneeScolaire();

	private List<Evaluation> listeEval = new ArrayList<Evaluation>();

	private String typenote;

	public Niveau getNiveau() {
		return niveau;
	}

	public void setNiveau(Niveau niveau) {
		this.niveau = niveau;
	}

	private List<MatiereClasse> listeMatClasse = new ArrayList<MatiereClasse>();

	@SuppressWarnings("unchecked")
	public void chargerListe() {
		listeNiveau = new ArrayList<Niveau>();
		listeNiveau = dataSource.createQuery(" From Niveau ").list();
		List<AnneeScolaire> listeAnnee = dataSource.createQuery("From AnneeScolaire order by idannee desc ").list();
		if (listeAnnee.size() > 0) {
			anneeScolaire = listeAnnee.get(0);
		}
		listeEval = new ArrayList<Evaluation>();
		listeEval = dataSource.createQuery("From Evaluation ").list();
	}

	@SuppressWarnings("unchecked")
	public void chargerMatieres() {
		listeMatiere = new ArrayList<Matiere>();
		listeMatiere = dataSource.createQuery(" From Matiere m inner join fetch m.niveau n where n =:pn")
				.setParameter("pn", niveau).list();

	}

	@SuppressWarnings("unchecked")
	public void voirMatiereClasse() {

		listeMatClasse = new ArrayList<MatiereClasse>();
		listeMatClasse = dataSource.createQuery(
				"From MatiereClasse m inner join fetch m.matiere ma inner join fetch m.classe c inner join fetch m.eval ev"
						+ " where  m.annee_scol =:pan  ")
				.setParameter("pan", anneeScolaire.getAnneeScolaire()).list();

	}

	public String versClasseMat() {
		this.setMatClasse(new MatiereClasse());
		chargerListe();
		listeMatiere = new ArrayList<Matiere>();
		return "/pages/nuramecole/matclasse.xhtml";
	}

	@SuppressWarnings("unchecked")
	public void chargerListeClasse() {
		listeClasse = new ArrayList<Classe>();
		listeClasse = dataSource.createQuery(" From Classe c inner join fetch c.niveau n where n=:pn")
				.setParameter("pn", niveau).list();
		if (niveau.getLibelle().equalsIgnoreCase("Primaire")) {
			typenote = "1";
		} else {
			typenote = "2";
		}

	}

	public void ajouterClasseMatiere() {
		for (Matiere mat : listeMatiere) {
			if (mat.getCoef() > 0) {
				MatiereClasse m = (MatiereClasse) dataSource.createQuery(
						"From MatiereClasse m inner join fetch m.matiere ma inner join fetch m.classe c inner join fetch m.eval ev"
								+ " where ma =:pma and c =:pc and m.annee_scol =:pan and ev =:pev ")
						.setParameter("pma", mat).setParameter("pc", matClasse.getClasse())
						.setParameter("pan", anneeScolaire.getAnneeScolaire()).setParameter("pev", matClasse.getEval())
						.uniqueResult();
				if (m == null) {
					MatiereClasse mc = new MatiereClasse();
					mc.setAnnee_scol(anneeScolaire.getAnneeScolaire());
					mc.setClasse(matClasse.getClasse());
					mc.setCoef(mat.getCoef());
					mc.setMatiere(mat);
					mc.setEval(matClasse.getEval());
					dataSource.save(mc);
				} else {
					m.setCoef(mat.getCoef());
					dataSource.update(m);
				}
			}
		}
		this.setMatClasse(new MatiereClasse());
		listeMatiere = new ArrayList<Matiere>();
		listeClasse = new ArrayList<Classe>();
		// listeEval = new ArrayList<Evaluation>();
		FacesMessages.instance().addToControlFromResourceBundle("infoGenerique", "Opération effectuée avec succés");

	}

	public void supprimerMatClasse(MatiereClasse mat) {
		dataSource.delete(mat);
		voirMatiereClasse();
	}

	public Session getDataSource() {
		return dataSource;
	}

	public void setDataSource(Session dataSource) {
		this.dataSource = dataSource;
	}

	public List<Classe> getListeClasse() {
		return listeClasse;
	}

	public void setListeClasse(List<Classe> listeClasse) {
		this.listeClasse = listeClasse;
	}

	public List<Matiere> getListeMatiere() {
		return listeMatiere;
	}

	public void setListeMatiere(List<Matiere> listeMatiere) {
		this.listeMatiere = listeMatiere;
	}

	public MatiereClasse getMatClasse() {
		return matClasse;
	}

	public void setMatClasse(MatiereClasse matClasse) {
		this.matClasse = matClasse;
	}

	public List<MatiereClasse> getListeMatClasse() {
		return listeMatClasse;
	}

	public void setListeMatClasse(List<MatiereClasse> listeMatClasse) {
		this.listeMatClasse = listeMatClasse;
	}

	public List<Niveau> getListeNiveau() {
		return listeNiveau;
	}

	public void setListeNiveau(List<Niveau> listeNiveau) {
		this.listeNiveau = listeNiveau;
	}

	public AnneeScolaire getAnneeScolaire() {
		return anneeScolaire;
	}

	public void setAnneeScolaire(AnneeScolaire anneeScolaire) {
		this.anneeScolaire = anneeScolaire;
	}

	public List<Evaluation> getListeEval() {
		return listeEval;
	}

	public void setListeEval(List<Evaluation> listeEval) {
		this.listeEval = listeEval;
	}

	public String getTypenote() {
		return typenote;
	}

	public void setTypenote(String typenote) {
		this.typenote = typenote;
	}

}
