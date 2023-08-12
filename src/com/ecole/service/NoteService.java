package com.ecole.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import com.ecole.entity.AnneeScolaire;
import com.ecole.entity.Classe;
import com.ecole.entity.Eleve;
import com.ecole.entity.MatiereClasse;
import com.ecole.entity.Niveau;
import com.ecole.entity.Note;

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
	private MatiereClasse matClasse = new MatiereClasse();
	private List<Eleve> eleves = new ArrayList<Eleve>();
	private Note note = new Note();

	
	@SuppressWarnings("unchecked")
	public void chargerListeMatClasse() {
		listeMatiereClasse = new ArrayList<MatiereClasse>();
		listeMatiereClasse = dataSource.createQuery("From MatiereClasse m inner join fetch m.classe c where c=:pc")
				.setParameter("pc", classe).list();
	}

	@SuppressWarnings("unchecked")
	public void chargerListeClasse() {
		setListeClasse(new ArrayList<Classe>());
		setListeClasse(dataSource.createQuery(" From Classe c inner join fetch c.niveau n where n=:pn")
				.setParameter("pn", niveau).list());
	}
	@SuppressWarnings("unchecked")
	public void chargerListeEleve() {
		listeEleves = new ArrayList<Eleve>();
		listeEleves = dataSource.createQuery("From Eleve e inner join fetch e.classe c where c=:pc")
				.setParameter("pc", classe).list();
		eleves=listeEleves;

	}

	@SuppressWarnings("unchecked")
	public String saisirNotes() {
		listeNiveau = new ArrayList<Niveau>();
		listeNiveau = dataSource.createQuery("From Niveau ").list();
		return "/pages/ecole/note.xhtml";
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

}
