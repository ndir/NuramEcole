package com.ecole.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.poi.util.SystemOutLogger;
import org.hibernate.Session;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;

import com.ecole.entity.AnneeScolaire;
import com.ecole.entity.Classe;
import com.ecole.entity.Eleve;
import com.ecole.entity.Evaluation;
import com.ecole.entity.Inscription;
import com.ecole.entity.Matiere;
import com.ecole.entity.MatiereClasse;
import com.ecole.entity.Niveau;
import com.ecole.entity.Note;
import com.ecole.entity.Notes;
import com.ecole.entity.ParamInscription;
import com.ecole.entity.Semestres;
import com.ecole.entity.TypeNote;

@Scope(ScopeType.SESSION)
@Name("noteService")
public class NoteService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@In
	private Session dataSource;
	private Eleve eleve = new Eleve();
	private Classe classe = new Classe();
	private Niveau niveau = new Niveau();
	private List<Eleve> listeEleves = new ArrayList<Eleve>();
	private List<Niveau> listeNiveau = new ArrayList<Niveau>();
	private AnneeScolaire anneeScolaire = new AnneeScolaire();
	private List<Classe> listeClasse = new ArrayList<Classe>();
	private List<String> heures = new ArrayList<String>();
	private List<MatiereClasse> listeMatiereClasse = new ArrayList<MatiereClasse>();
	private List<Matiere> listeMatiere = new ArrayList<Matiere>();
	private MatiereClasse matClasse = new MatiereClasse();
	private List<Eleve> eleves = new ArrayList<Eleve>();
	private List<Evaluation> listeEval = new ArrayList<Evaluation>();
	private List<Evaluation> listeEval1 = new ArrayList<Evaluation>();
	private Evaluation eval = new Evaluation();
	private Note note = new Note();
	private List<String> lsite = new ArrayList<String>();
	// private String ev = new String();
	private List<Note> listeNotes = new ArrayList<Note>();
	private List<Notes> listeNotess = new ArrayList<Notes>();
	private TypeNote typeNote = new TypeNote();
	private List<TypeNote> listeTypeNotes = new ArrayList<TypeNote>();
	private Evaluation ev = new Evaluation();
	private boolean choix;

	@In
	private AnneeScolaire annee;

	private String typenote;

	private List<Semestres> listeSemestre = new ArrayList<Semestres>();

	private Semestres semestre = new Semestres();

	@SuppressWarnings("unchecked")
	public void chargerListeMatClasse() {
		// Evaluation e = (Evaluation) dataSource.createQuery("FRom Evaluation e where
		// libelle=:plib")
		// .setParameter("plib", ev).uniqueResult();

		// System.out.println("Entrer "+e.getIdEvaluation());

		listeMatiereClasse = new ArrayList<MatiereClasse>();
		if (typenote.equalsIgnoreCase("1")) {
			if (ev.getIdEvaluation() != null) {
				listeMatiereClasse = dataSource.createQuery(
						"From MatiereClasse m inner join fetch m.classe c inner join fetch m.matiere inner join fetch m.eval ev where c=:pc and m.annee_scol=:pannee "
								+ " and ev =:pev")
						.setParameter("pc", note.getCl()).setParameter("pannee", annee.getAnneeScolaire())
						.setParameter("pev", ev).list();
			} else {
				FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique", "Evaluation Obligatoire");
				return;
			}
		} else {

			listeMatiereClasse = dataSource.createQuery(
					"From MatiereClasse m inner join fetch m.classe c inner join fetch m.matiere  where c=:pc and m.annee_scol=:pannee "
							+ " ")
					.setParameter("pc", note.getCl()).setParameter("pannee", annee.getAnneeScolaire()).list();
		}
		listeMatiere = new ArrayList<Matiere>();

		if (listeMatiereClasse.size() > 0) {
			for (MatiereClasse mc : listeMatiereClasse) {
				listeMatiere.add(mc.getMatiere());
			}
		}

	}

	public void getEval1() {
		ev = new Evaluation();
		ev = (Evaluation) dataSource.get(Evaluation.class, note.getEvaluation().getIdEvaluation());

	}

	public void annulerAjoutNote() {
		listeMatiereClasse = new ArrayList<MatiereClasse>();
		listeEleves = new ArrayList<Eleve>();
		listeMatiere = new ArrayList<Matiere>();
		listeClasse = new ArrayList<Classe>();
		this.setNote(new Note());
	}

	public int getCof(Matiere m) {
		int cof = 0;
		for (MatiereClasse mc : listeMatiereClasse) {
			if (mc.getMatiere().getIdmatiere().equals(m.getIdmatiere())) {
				cof = mc.getCoef();
				break;
			}
		}
		return cof;
	}

	public void ajouterNote() {
		if (typenote.equalsIgnoreCase("1")) {
			if (note.getEvaluation() == null) {
				FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique", "Evaluation Obligatoire");
				return;
			}

			for (Eleve eleve : listeEleves) {

				if (eleve.getNote() > eleve.getCoef()) {
					FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique",
							"Note supérieure au coéfficient pour l'éléve " + eleve.getNom() + " " + eleve.getPrenom());
					return;
				}
			}
			for (Eleve eleve : listeEleves) {
				if (eleve.isChoix() & !eleve.isExiste()) {
					Note n = new Note();
					n.setAnnee(annee);
					n.setCl(note.getCl());
					n.setEleve(eleve);
					n.setEvaluation(note.getEvaluation());
					n.setMatiere(note.getMatiere());
					n.setNote(eleve.getNote());
					n.setCoef(getCof(note.getMatiere()));

					dataSource.save(n);
				}
				if (eleve.isChoix() & eleve.isExiste()) {
					Note n = (Note) dataSource.get(Note.class, eleve.getIdNote());
					if (n.getIdNote() != null) {
						n.setNote(eleve.getNote());
						dataSource.update(n);
					}
				}
			}
			FacesMessages.instance().addToControlFromResourceBundle("infoGenerique", "Notes sauvegardées avec succès");
			annulerAjoutNote();
		} else {
			if (semestre == null) {
				FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique", "Semestre Obligatoire");
				return;
			}


			for (Eleve eleve : listeEleves) {
				if (eleve.isChoix() & !eleve.isExiste()) {
					Notes n = new Notes();
					n.setAnnee(annee);
					n.setClasse(note.getCl());
					n.setSemestre(semestre);
					n.setEleve(eleve);
					n.setMatiere(note.getMatiere());
					n.setNote(eleve.getNote());
					n.setTypeNote(typeNote);
					n.setCoef(getCof(note.getMatiere()));
					dataSource.save(n);
				}

				if (eleve.isChoix() & eleve.isExiste()) {
					Notes n = (Notes) dataSource.get(Notes.class, eleve.getIdNote());
					if (n.getId() != null) {
						n.setNote(eleve.getNote());
						dataSource.update(n);
					}
				}
			}
			this.setNiveau(new Niveau());
			this.setSemestre(new Semestres());
			FacesMessages.instance().addToControlFromResourceBundle("infoGenerique", "Notes sauvegardées avec succès");
			annulerAjoutNote();
		}

	}

	@SuppressWarnings("unchecked")
	public void chargerListeClasse() {
		setListeClasse(new ArrayList<Classe>());
		setListeClasse(dataSource.createQuery(" From Classe c inner join fetch c.niveau n where n=:pn")
				.setParameter("pn", niveau).list());
		listeEval = new ArrayList<Evaluation>();
		listeEval = dataSource.createQuery("From  Evaluation ").list();
		if (niveau.getCode().equalsIgnoreCase("ELE") || niveau.getCode().equalsIgnoreCase("PRE")) {
			typenote = "1";
		} else {
			typenote = "2";
			listeSemestre = new ArrayList<Semestres>();
			listeSemestre = dataSource.createQuery("From Semestres ").list();
			listeTypeNotes = new ArrayList<TypeNote>();
			listeTypeNotes = dataSource.createQuery("From TypeNote ").list();
		}

	}

	@SuppressWarnings("unchecked")
	public void chargerListeEleve() {
		System.out.println("enter classe "+note.getCl().getLibelle() );
		MatiereClasse mc = new MatiereClasse();
		ParamInscription p = (ParamInscription) dataSource
				.createQuery("From ParamInscription p inner join fetch p.classe c inner join fetch p.annee pa "
						+ " where c =:pc and pa =:pa ")
				.setParameter("pc", note.getCl()).setParameter("pa", annee).uniqueResult();

		if (p != null) {
			System.out.println("not null");
			this.setChoix(true);
			List<Inscription> liste = dataSource
					.createQuery(
							"FRom Inscription i inner join fetch i.eleve inner join fetch i.paramins p where p =:pp")
					.setParameter("pp", p).list();
			System.out.println("taille liste "+liste.size() + "param "+p.getId());
			listeEleves = new ArrayList<Eleve>();
			if (typenote.equalsIgnoreCase("1")) {
				mc = (MatiereClasse) dataSource
						.createQuery("From MatiereClasse mc inner join fetch mc.classe c inner join fetch mc.matiere m "
								+ " inner join fetch mc.eval ev where c=:pc and m=:pm and ev=:pev and mc.annee_scol =:pan")
						.setParameter("pc", note.getCl()).setParameter("pm", note.getMatiere())
						.setParameter("pev", note.getEvaluation()).setParameter("pan", annee.getAnneeScolaire())
						.uniqueResult();
			} else {
				mc = (MatiereClasse) dataSource
						.createQuery("From MatiereClasse mc inner join fetch mc.classe c inner join fetch mc.matiere m "
								+ " where c=:pc and m=:pm  and mc.annee_scol =:pan")
						.setParameter("pc", note.getCl()).setParameter("pm", note.getMatiere())
						.setParameter("pan", annee.getAnneeScolaire()).uniqueResult();
			}
			for (Inscription in : liste) {
				in.getEleve().setChoix(true);
				noteExiste(in.getEleve());
				in.getEleve().setCoef(mc.getCoef());
				listeEleves.add(in.getEleve());
			}
		}
	}

	public void noteExiste(Eleve e) {

		Note n = (Note) dataSource.createQuery("From Note n inner join fetch n.cl c inner join fetch n.matiere m "
				+ " inner join fetch n.eleve e inner join fetch n.annee an inner join fetch n.evaluation ev where c=:pc and m=:pm and an=:pan and e=:pe and ev =:pev ")
				.setParameter("pc", note.getCl()).setParameter("pm", note.getMatiere()).setParameter("pan", annee)
				.setParameter("pe", e).setParameter("pev", note.getEvaluation()).uniqueResult();
		if (n != null) {
			e.setExiste(true);
			e.setIdNote(n.getIdNote());
			e.setNote(n.getNote());
		}

	}

	public void cocherTout() {
		for (Eleve e : listeEleves) {
			e.setChoix(choix);
		}
	}

	@SuppressWarnings("unchecked")
	public void chargerListeNotes() {
		listeNotes = new ArrayList<Note>();
		listeNotess = new ArrayList<Notes>();
		if (typenote.equalsIgnoreCase("1")) {
			listeNotes = dataSource.createQuery("From Note n inner join fetch n.cl c inner join fetch n.matiere m "
					+ "  inner join fetch n.eleve e inner join fetch n.evaluation ev inner join fetch n.annee an "
					+ "  where c=:pc and ev=:pev and an=:pan and m=:pm").setParameter("pc", note.getCl())
					.setParameter("pev", ev).setParameter("pan", annee).setParameter("pm", note.getMatiere()).list();
			if (listeNotes.size() == 0) {
				FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique",
						"Aucune(s) Note(s) pour la matière " + note.getMatiere().getLibelle() + " pour  "
								+ ev.getLibelle());
			}
		} else {
			listeNotess = dataSource
					.createQuery("From Notes n inner join fetch n.classe c inner join fetch n.matiere m "
							+ "  inner join fetch n.eleve e inner join fetch n.semestre ev inner join fetch n.annee an inner join fetch n.typeNote ty "
							+ "  where c=:pc and ev=:pev and an=:pan and m=:pm and ty=:pty")
					.setParameter("pc", note.getCl()).setParameter("pev", semestre).setParameter("pan", annee)
					.setParameter("pm", note.getMatiere()).setParameter("pty", typeNote).list();
			if (listeNotess.size() == 0) {
				FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique",
						"Aucune(s) Note(s) pour la matière " + note.getMatiere().getLibelle() + " pour  "
								+ semestre.getLibelle());
				
			}
		}

	}

	public void modifierNotes(Notes notes) {
		dataSource.update(notes);
	}

	@SuppressWarnings("unchecked")
	public String saisirNotes() {
		listeNiveau = new ArrayList<Niveau>();
		listeNiveau = dataSource.createQuery("From Niveau where code <>:pcode").setParameter("pcode", "PRE").list();
		this.setNiveau(new Niveau());
		this.setClasse(new Classe());
		this.setNote(new Note());
		listeEleves = new ArrayList<Eleve>();
		listeEval = new ArrayList<Evaluation>();
		listeEval = dataSource.createQuery("From  Evaluation ").list();
		listeSemestre = new ArrayList<Semestres>();
		listeSemestre = dataSource.createQuery("From Semestres ").list();
		listeTypeNotes = new ArrayList<TypeNote>();
		listeTypeNotes = dataSource.createQuery("From TypeNote ").list();
		return "/pages/nuramecole/note.xhtml";
	}

	@SuppressWarnings("unchecked")
	public String visualiserNotes() {
		listeNiveau = new ArrayList<Niveau>();
		listeNiveau = dataSource.createQuery("From Niveau where code <>:pcode").setParameter("pcode", "PRE").list();
		this.setNiveau(new Niveau());
		this.setClasse(new Classe());
		this.setNote(new Note());
		listeEleves = new ArrayList<Eleve>();
		listeNotess = new ArrayList<Notes>();
		listeNotes = new ArrayList<Note>();
		return "/pages/nuramecole/voirnote.xhtml";
	}

	public Session getDataSource() {
		return dataSource;
	}

	public void setDataSource(Session dataSource) {
		this.dataSource = dataSource;
	}

	public Eleve getEleve() {
		return eleve;
	}

	public void setEleve(Eleve eleve) {
		this.eleve = eleve;
	}

	public Classe getClasse() {
		return classe;
	}

	public void setClasse(Classe classe) {
		this.classe = classe;
	}

	public Niveau getNiveau() {
		return niveau;
	}

	public void setNiveau(Niveau niveau) {
		this.niveau = niveau;
	}

	public List<Eleve> getListeEleves() {
		return listeEleves;
	}

	public void setListeEleves(List<Eleve> listeEleves) {
		this.listeEleves = listeEleves;
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

	public List<String> getHeures() {
		return heures;
	}

	public void setHeures(List<String> heures) {
		this.heures = heures;
	}

	public List<MatiereClasse> getListeMatiereClasse() {
		return listeMatiereClasse;
	}

	public void setListeMatiereClasse(List<MatiereClasse> listeMatiereClasse) {
		this.listeMatiereClasse = listeMatiereClasse;
	}

	public MatiereClasse getMatClasse() {
		return matClasse;
	}

	public void setMatClasse(MatiereClasse matClasse) {
		this.matClasse = matClasse;
	}

	public List<Eleve> getEleves() {
		return eleves;
	}

	public void setEleves(List<Eleve> eleves) {
		this.eleves = eleves;
	}

	public Note getNote() {
		return note;
	}

	public void setNote(Note note) {
		this.note = note;
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

	public List<Evaluation> getListeEval() {
		return listeEval;
	}

	public void setListeEval(List<Evaluation> listeEval) {
		this.listeEval = listeEval;
	}

	public Evaluation getEval() {
		return eval;
	}

	public void setEval(Evaluation eval) {
		this.eval = eval;
	}

	public List<Evaluation> getListeEval1() {
		return listeEval1;
	}

	public void setListeEval1(List<Evaluation> listeEval1) {
		this.listeEval1 = listeEval1;
	}

	public List<String> getLsite() {
		return lsite;
	}

	public void setLsite(List<String> lsite) {
		this.lsite = lsite;
	}

//	public String getEv() {
//		return ev;
//	}
//
//	public void setEv(String ev) {
//		this.ev = ev;
//	}

	public List<Note> getListeNotes() {
		return listeNotes;
	}

	public void setListeNotes(List<Note> listeNotes) {
		this.listeNotes = listeNotes;
	}

	public boolean isChoix() {
		return choix;
	}

	public void setChoix(boolean choix) {
		this.choix = choix;
	}

	public List<Semestres> getListeSemestre() {
		return listeSemestre;
	}

	public void setListeSemestre(List<Semestres> listeSemestre) {
		this.listeSemestre = listeSemestre;
	}

	public String getTypenote() {
		return typenote;
	}

	public void setTypenote(String typenote) {
		this.typenote = typenote;
	}

	public Semestres getSemestre() {
		return semestre;
	}

	public void setSemestre(Semestres semestre) {
		this.semestre = semestre;
	}

	public TypeNote getTypeNote() {
		return typeNote;
	}

	public void setTypeNote(TypeNote typeNote) {
		this.typeNote = typeNote;
	}

	public List<TypeNote> getListeTypeNotes() {
		return listeTypeNotes;
	}

	public void setListeTypeNotes(List<TypeNote> listeTypeNotes) {
		this.listeTypeNotes = listeTypeNotes;
	}

	public List<Notes> getListeNotess() {
		return listeNotess;
	}

	public void setListeNotess(List<Notes> listeNotess) {
		this.listeNotess = listeNotess;
	}

}
