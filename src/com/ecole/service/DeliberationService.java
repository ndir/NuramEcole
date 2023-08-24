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
import com.ecole.entity.AnneeScolaire;
import com.ecole.entity.Classe;
import com.ecole.entity.Deliberation;
import com.ecole.entity.Eleve;
import com.ecole.entity.Evaluation;
import com.ecole.entity.Inscription;
import com.ecole.entity.Matiere;
import com.ecole.entity.MatiereClasse;
import com.ecole.entity.Niveau;
import com.ecole.entity.Note;
import com.ecole.entity.ParamInscription;

/**
 * @author A626257
 *
 */
@Scope(ScopeType.SESSION)
@Name("dService")
public class DeliberationService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@In
	private Session dataSource;
	private Classe classe = new Classe();
	private Niveau niveau = new Niveau();
	private List<Eleve> listeEleves = new ArrayList<Eleve>();
	private List<Eleve> listeElevesNonNote = new ArrayList<Eleve>();
	private List<Matiere> listeMatiere = new ArrayList<Matiere>();
	private Evaluation evaluation = new Evaluation();
	private String rangs;
	private int rang;
	private List<Deliberation> listeDeliberation = new ArrayList<Deliberation>();
	private List<Deliberation> listeDeliberationF = new ArrayList<Deliberation>();

	@In
	private AnneeScolaire annee;

	@In(required = false)
	@Out(required = false)
	private Utilisateur utilisateur;

	private List<Evaluation> listeEval = new ArrayList<Evaluation>();

	public Niveau getNiveau() {
		return niveau;
	}

	public void setNiveau(Niveau niveau) {
		this.niveau = niveau;
	}

	private List<Niveau> listeNiveau = new ArrayList<Niveau>();
	private AnneeScolaire anneeScolaire = new AnneeScolaire();
	private List<Classe> listeClasse = new ArrayList<Classe>();

	@SuppressWarnings("unchecked")
	public String versDeliberation() {
		listeNiveau = new ArrayList<Niveau>();
		listeNiveau = dataSource.createQuery("From Niveau ").list();
		listeEval = new ArrayList<Evaluation>();
		listeEval = dataSource.createQuery("From Evaluation ").list();
		listeElevesNonNote = new ArrayList<Eleve>();
		this.setClasse(new Classe());
		this.setEvaluation(new Evaluation());
		listeDeliberation = new ArrayList<Deliberation>();
		listeDeliberationF = new ArrayList<Deliberation>();
		return "/pages/nuramecole/deliberation.xhtml";
	}

	@SuppressWarnings("unchecked")
	public void chargerListeClasse() {
		listeClasse = new ArrayList<Classe>();
		listeClasse = dataSource.createQuery(" From Classe c inner join fetch c.niveau n where n=:pn")
				.setParameter("pn", niveau).list();
	}

	@SuppressWarnings("unchecked")
	public void deliberer() {
		if (evaluation == null || evaluation.getIdEvaluation() == null) {
			FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique", "Evaluation obligatoire");
			return;
		}
		if (classe == null || classe.getIdclasse() == null) {
			FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique", "Classe obligatoire");
			return;
		}
		ParamInscription p = (ParamInscription) dataSource
				.createQuery("From ParamInscription p inner join fetch p.classe c inner join fetch p.annee pa "
						+ " where c =:pc and pa =:pa ")
				.setParameter("pc", classe).setParameter("pa", annee).uniqueResult();

		if (p != null) {
			List<Inscription> liste = dataSource
					.createQuery(
							"From Inscription i inner join fetch i.eleve inner join fetch i.paramins p where p =:pp")
					.setParameter("pp", p).list();
			listeEleves = new ArrayList<Eleve>();

			for (Inscription in : liste) {
				listeEleves.add(in.getEleve());
			}
		}
		List<Deliberation> deliexiste = dataSource
				.createQuery("From Deliberation d inner join fetch d.classe c inner join fetch d.annee an"
						+ " inner join fetch d.evaluation ev where c =:pc and an =:pan and ev =:pev")
				.setParameter("pc", classe).setParameter("pan", annee).setParameter("pev", evaluation).list();
		if (deliexiste.size() > 0) {
			FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique",
					"Classe " + classe.getLibelle() + " déja délibérée pour  " + evaluation.getLibelle());
			return;
		}
		listeMatiere = dataSource
				.createQuery("Select mc.matiere from MatiereClasse mc where mc.classe =:pc and mc.annee_scol =:pan "
						+ " and mc.eval =:peval")
				.setParameter("pc", classe).setParameter("pan", annee.getAnneeScolaire())
				.setParameter("peval", evaluation).list();

		List<Note> listeNote = dataSource
				.createQuery("From Note n inner join fetch n.cl c inner join fetch n.annee an "
						+ " inner join fetch n.evaluation ev inner join fetch n.eleve "
						+ " where c =:pc and an =:pan and ev =:pev")
				.setParameter("pc", classe).setParameter("pan", annee).setParameter("pev", evaluation).list();
		boolean existe = false;
		listeElevesNonNote = new ArrayList<Eleve>();
		for (Eleve ev : listeEleves) {
			existe = false;
			for (Matiere m : listeMatiere) {
				for (Note n : listeNote) {

					if (n.getMatiere().getIdmatiere().equals(m.getIdmatiere())
							&& ev.getIdeleve().equals(n.getEleve().getIdeleve())) {
						existe = true;
						break;
					}
				}

				if (existe == false) { // eleve pas note pour la matiere
					ev.setIdMat(m.getIdmatiere());
					listeElevesNonNote.add(ev);
				}
				existe = false;
			}
		}

		if (listeElevesNonNote.size() == 0) {
// Délibartion de la classe
			float cumulnote;
			float cumulcoef;
			float moyen;
			cumulcoef = CumulCeof();
			System.out.println("cumulcoef " + cumulcoef);
			for (Eleve ev : listeEleves) {
				cumulnote = cumulNote(ev, listeNote);
				moyen = cumulnote / cumulcoef;

				Deliberation d = new Deliberation();
				d.setAnnee(annee);
				d.setClasse(classe);
				d.setEleve(ev);
				d.setEvaluation(evaluation);
				d.setMoyenne(moyen);
				d.setUser(utilisateur);
				listeDeliberation.add(d);
			}
			rang = 1;
			while (listeDeliberation.size() > 0) {
				Deliberation deli = gererSup();
				List<Deliberation> liste = new ArrayList<Deliberation>();
				liste = getMoyenEqual(deli);
				if (liste.size() > 0) {
					if (rang == 1) {
						rangs = rang + "er ex æquo";
					} else {
						rangs = rang + "eme ex æquo";
					}
				} else {
					if (rang == 1) {
						rangs = rang + "er";
					} else {
						rangs = rang + "eme";
					}
				}
				deli.setRang(rangs);
				listeDeliberationF.add(deli);
				if (liste.size() > 0) {
					for (Deliberation d : liste) {
						d.setRang(rangs);
						System.out.println("ajout ex " + rangs + d.getMoyenne() + d.getEleve().getPrenom());
						listeDeliberationF.add(d);
						listeDeliberation.remove(d);
						rang = rang + 1;
					}
					rang = rang + 1;
				} else {
					rang = rang + 1;
				}

			}

			for (Deliberation df : listeDeliberationF) {
				dataSource.save(df);
			}
		}
	}

	public Deliberation gererSup() {
		Deliberation d = listeDeliberation.get(0);
		for (int i = 1; i < listeDeliberation.size(); i++) {
			if (d.getMoyenne() < listeDeliberation.get(i).getMoyenne()) {
				d = listeDeliberation.get(i);
			}
		}

		listeDeliberation.remove(d);

		return d;
	}

	public List<Deliberation> getMoyenEqual(Deliberation d) {
		List<Deliberation> liste = new ArrayList<Deliberation>();
		for (int i = 0; i < listeDeliberation.size(); i++) {

			if (d.getMoyenne() == listeDeliberation.get(i).getMoyenne()) {
				System.out.println("OK");
				liste.add(listeDeliberation.get(i));
			}
		}

		return liste;
	}

	public float cumulNote(Eleve e, List<Note> listeNote) {
		float cumul = 0;
		for (Note n : listeNote) {
			if (n.getEleve().getIdeleve().equals(e.getIdeleve())) {
				cumul = cumul + n.getNote();
			}
		}
		return cumul;
	}

	@SuppressWarnings("unchecked")
	public float CumulCeof() {
		float cumul = 0;

		List<MatiereClasse> listeMc = dataSource
				.createQuery("From MatiereClasse mc inner join fetch mc.classe c inner join fetch mc.matiere m "
						+ " inner join  fetch mc.eval ev   where c =:pc and mc.annee_scol =:pan and ev =:pev")
				.setParameter("pc", classe).setParameter("pan", annee.getAnneeScolaire())
				.setParameter("pev", evaluation).list();
		for (MatiereClasse n : listeMc) {
			cumul = cumul + n.getCoef();
		}

		return (cumul * 10) / 100;
	}

	public void delibererAvecEleveExclu() {
		listeElevesNonNote = new ArrayList<Eleve>();
		this.setClasse(new Classe());
		this.setClasse(new Classe());
		this.setEvaluation(new Evaluation());
	}

	public String getMatiere(Eleve ev) {
		Matiere m = (Matiere) dataSource.get(Matiere.class, ev.getIdMat());
		return m.getLibelle();
	}

	public Session getDataSource() {
		return dataSource;
	}

	public void setDataSource(Session dataSource) {
		this.dataSource = dataSource;
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

	public List<Classe> getListeClasse() {
		return listeClasse;
	}

	public void setListeClasse(List<Classe> listeClasse) {
		this.listeClasse = listeClasse;
	}

	public Classe getClasse() {
		return classe;
	}

	public void setClasse(Classe classe) {
		this.classe = classe;
	}

	public List<Eleve> getListeEleves() {
		return listeEleves;
	}

	public void setListeEleves(List<Eleve> listeEleves) {
		this.listeEleves = listeEleves;
	}

	public Evaluation getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(Evaluation evaluation) {
		this.evaluation = evaluation;
	}

	public List<Evaluation> getListeEval() {
		return listeEval;
	}

	public void setListeEval(List<Evaluation> listeEval) {
		this.listeEval = listeEval;
	}

	public AnneeScolaire getAnnee() {
		return annee;
	}

	public void setAnnee(AnneeScolaire annee) {
		this.annee = annee;
	}

	public List<Matiere> getListeMatiere() {
		return listeMatiere;
	}

	public void setListeMatiere(List<Matiere> listeMatiere) {
		this.listeMatiere = listeMatiere;
	}

	public List<Eleve> getListeElevesNonNote() {
		return listeElevesNonNote;
	}

	public void setListeElevesNonNote(List<Eleve> listeElevesNonNote) {
		this.listeElevesNonNote = listeElevesNonNote;
	}

	public List<Deliberation> getListeDeliberation() {
		return listeDeliberation;
	}

	public void setListeDeliberation(List<Deliberation> listeDeliberation) {
		this.listeDeliberation = listeDeliberation;
	}

	public List<Deliberation> getListeDeliberationF() {
		return listeDeliberationF;
	}

	public void setListeDeliberationF(List<Deliberation> listeDeliberationF) {
		this.listeDeliberationF = listeDeliberationF;
	}

}
