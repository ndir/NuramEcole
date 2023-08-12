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
import com.ecole.entity.Eleve;
import com.ecole.entity.MatiereClasse;
import com.ecole.entity.Niveau;
import com.ecole.entity.Retard;

@Scope(ScopeType.SESSION)
@Name("retardService")
public class RetardService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@In
	private Session dataSource;
	private Retard retard = new Retard();
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
	private List<Eleve> el = new ArrayList<Eleve>();
	private List<Retard> listeRetards = new ArrayList<Retard>();

	public void ajouterRetard() {
		for (Retard r : listeRetards) {
			dataSource.save(r);
		}
		listeRetards.clear();
		FacesMessages.instance().addToControlFromResourceBundle("infoGenerique", "Opération éffectuée avec succés");

	}

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
	public void annulerAjout() {
		this.setRetard(new Retard());
	}

	public void marquerRetard(Eleve e) {
		retard.setEleve(e);
		listeRetards.add(retard);
		eleves.remove(e);
		this.setRetard(new Retard());
	}
	
	@SuppressWarnings("unchecked")
	public String versRetards() {
		listeNiveau = new ArrayList<Niveau>();
		listeNiveau = dataSource.createQuery("From Niveau ").list();
		heures = new ArrayList<String>();
		heures.add("8h-10h");
		heures.add("10h-12h");
		heures.add("12h-14h");
		heures.add("15h-17h");
		heures.add("17h-19h");
		return "/pages/ecole/retard.xhtml";
	}

	public Retard getRetard() {
		return retard;
	}

	public void setRetard(Retard retard) {
		this.retard = retard;
	}

	public Eleve getEleve() {
		return eleve;
	}

	public void setEleve(Eleve eleve) {
		this.eleve = eleve;
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

	public List<Eleve> getEl() {
		return el;
	}

	public void setEl(List<Eleve> el) {
		this.el = el;
	}

	public List<Retard> getListeRetards() {
		return listeRetards;
	}

	public void setListeRetards(List<Retard> listeRetards) {
		this.listeRetards = listeRetards;
	}

	public Classe getClasse() {
		return classe;
	}

	public void setClasse(Classe classe) {
		this.classe = classe;
	}

}
