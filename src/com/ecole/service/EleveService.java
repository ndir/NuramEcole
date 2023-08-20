/**
 * 
 */
package com.ecole.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.annotations.ForceDiscriminator;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;

import com.ecole.entity.Absence;
import com.ecole.entity.AnneeScolaire;
import com.ecole.entity.Classe;
import com.ecole.entity.Eleve;
import com.ecole.entity.Inscription;
import com.ecole.entity.Niveau;
import com.ecole.entity.ParamInscription;
import com.rhospi.commons.ChakaUtils;

/**
 * @author a626257
 * 
 */
@Scope(ScopeType.SESSION)
@Name("eleveService")
public class EleveService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@In
	private Session dataSource;

	private Eleve eleve = new Eleve();

	private List<Eleve> listeEleves = new ArrayList<Eleve>();

	private Classe classe = new Classe();

	private Niveau niveau = new Niveau();

	private List<Classe> listeClasse = new ArrayList<Classe>();

	private AnneeScolaire anneeScolaire = new AnneeScolaire();

	private List<Niveau> listeNiveau = new ArrayList<Niveau>();
	private Absence absence = new Absence();

	private List<Eleve> listeEleveById = new ArrayList<Eleve>();

	private Inscription inscription = new Inscription();

	ParamInscription p = new ParamInscription();

	@In
	private AnneeScolaire annee;

	@SuppressWarnings("unchecked")
	public String versEleve() {
		this.setEleve(new Eleve());
		List<AnneeScolaire> listeAnnee = dataSource.createQuery("From AnneeScolaire order by idannee desc ").list();
		if (listeAnnee.size() > 0) {
			anneeScolaire = listeAnnee.get(0);
		}
		listeNiveau = new ArrayList<Niveau>();
		listeNiveau = dataSource.createQuery("From Niveau ").list();

		return "/pages/nuramecole/creereleve.xhtml";
	}

	@SuppressWarnings("unchecked")
	public String versListeEleve() {
		List<AnneeScolaire> listeAnnee = dataSource.createQuery("From AnneeScolaire order by idannee desc ").list();
		if (listeAnnee.size() > 0) {
			anneeScolaire = listeAnnee.get(0);
		}
		listeNiveau = new ArrayList<Niveau>();
		listeNiveau = dataSource.createQuery("From Niveau ").list();
		return "/pages/ecole/listeeleve.xhtml";
	}

	@SuppressWarnings("unchecked")
	public void rechercherEleves() {

//		listeEleves = new ArrayList<Eleve>();
//		listeEleves = dataSource
//				.createQuery("From Eleve e inner join fetch e.classe c where c=:pc and e.annee_ins=:pan ")
//				.setParameter("pc", eleve.getClasse()).setParameter("pan", anneeScolaire.getAnneeScolaire()).list();
//		if (listeEleves.size() == 0) {
//			FacesMessages.instance().addToControlFromResourceBundle("infoGenerique", "Pas d'éléve pour la classe "
//					+ eleve.getClasse().getLibelle() + " pour l'année scolaire " + anneeScolaire.getAnneeScolaire());
//		} else {
//			FacesMessages.instance().addToControlFromResourceBundle("infoGenerique",
//					"Le nombre d'éleve de la classe " + eleve.getClasse().getLibelle() + " pour l'année scolaire "
//							+ anneeScolaire.getAnneeScolaire() + " est de " + listeEleves.size());
//		}
	}

	@SuppressWarnings("unchecked")
	public void chargerListeClasse() {
		listeClasse = new ArrayList<Classe>();
		listeClasse = dataSource.createQuery(" From Classe c inner join fetch c.niveau n where n=:pn")
				.setParameter("pn", niveau).list();
	}

	@SuppressWarnings("unchecked")
	public void chargerListe() {
		listeNiveau = new ArrayList<Niveau>();
		listeNiveau = dataSource.createQuery("From Niveau ").list();
	}

	public void ajouterEleve() {

		int year = ChakaUtils.getCurrentYear(ChakaUtils.sysDate());
		int year1 = ChakaUtils.getCurrentYear(eleve.getDateNaissance());
		eleve.setAge(year - year1);
		// eleve.setAnnee_ins(anneeScolaire.getAnneeScolaire());
		if (eleve.getIdeleve() == null) {
			dataSource.save(eleve);
		} else {
			dataSource.update(eleve);
		}

		this.setEleve(new Eleve());
		FacesMessages.instance().addToControlFromResourceBundle("infoGenerique", "Evaluation ajoutée avec succés");
	}

	public void ajouterInscription() {
		if (this.eleve.getNom().isEmpty() || this.eleve.getPrenom().isEmpty()) {
			FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique",
					"Veuillez renseigner le nom et le prénom de l'éléve");
			return;
		}

		if (this.eleve.getDateNaissance() == null) {
			FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique",
					"Veuillez renseigner la date de naissance");
			return;
		}

		if (this.classe == null) {
			FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique", "Veuillez chosir une classe");
			return;
		}

		// Classe cl = (Classe) dataSource.get(Classe.class, classe.getIdclasse());

		ParamInscription paramins = (ParamInscription) dataSource
				.createQuery("From ParamInscription p inner join fetch p.annee inner join fetch p.classe"
						+ " where p.annee =:pannee and p.classe =:pclasse")
				.setParameter("pannee", annee).setParameter("pclasse", classe).uniqueResult();
		if (paramins != null) {
			int year = ChakaUtils.getCurrentYear(ChakaUtils.sysDate());
			int year1 = ChakaUtils.getCurrentYear(eleve.getDateNaissance());
			eleve.setAge(year - year1);
			dataSource.save(eleve);
			inscription.setParamins(paramins);
			inscription.setEleve(eleve);
			dataSource.save(inscription);
			this.setEleve(new Eleve());
			this.setInscription(new Inscription());
			FacesMessages.instance().addToControlFromResourceBundle("infoGenerique", "Evaluation ajoutée avec succés");
		}
	}

	public AnneeScolaire getAnnee() {
		return annee;
	}

	public void setAnnee(AnneeScolaire annee) {
		this.annee = annee;
	}

	@SuppressWarnings("unchecked")
	public void listeEleves(Classe classe) {
		// this.eleve.setClasse(classe);
		p = (ParamInscription) dataSource
				.createQuery("From ParamInscription p inner join fetch p.classe c inner join fetch p.annee pa "
						+ " where c =:pc and pa =:pa ")
				.setParameter("pc", classe).setParameter("pa", annee).uniqueResult();
		if (p != null) {

			List<Inscription> liste = dataSource.createQuery(
					"FRom Inscription i inner join fetch i.eleve" + "  inner join fetch i.paramins p where p =:pp")
					.setParameter("pp", p).list();
			listeEleves = new ArrayList<Eleve>();
			for (Inscription in : liste) {
				listeEleves.add(in.getEleve());

			}
		}
	}

	public Double versGetReduction(Eleve eleve) {
		double resul = 0.0;
		Inscription ins = (Inscription) dataSource
				.createQuery("From Inscription i inner join fetch i.paramins p inner join fetch i.eleve e "
						+ " where p =:pp and e =:pi")
				.setParameter("pp", p).setParameter("pi", eleve).uniqueResult();
		if (ins != null && ins.getReduction() != null) {
			resul = ins.getReduction();
		}
		return resul;
	}

	@SuppressWarnings("unchecked")
	public void getEleveById(Long id, Classe classe) {
		// this.eleve.setClasse(classe);
		listeEleveById = new ArrayList<Eleve>();
		listeEleves = dataSource.createQuery("From Eleve e inner join fetch e.classe c where c=:pc and d:pd")
				.setParameter("pc", classe).setParameter("pd", id).list();
	}

	public void annulerAjout() {
		this.setEleve(new Eleve());
	}

	public void versCreerEleve(Classe classe) {
		// this.eleve.setClasse(classe);
	}

	public Eleve getEleve() {
		return eleve;
	}

	public void setEleve(Eleve eleve) {
		this.eleve = eleve;
	}

	public List<Eleve> getListeEleves() {
		return listeEleves;
	}

	public void setListeEleves(List<Eleve> listeEleves) {
		this.listeEleves = listeEleves;
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

	public List<Classe> getListeClasse() {
		return listeClasse;
	}

	public void setListeClasse(List<Classe> listeClasse) {
		this.listeClasse = listeClasse;
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

	public Absence getAbsence() {
		return absence;
	}

	public void setAbsence(Absence absence) {
		this.absence = absence;
	}

	public List<Eleve> getListeEleveById() {
		return listeEleveById;
	}

	public void setListeEleveById(List<Eleve> listeEleveById) {
		this.listeEleveById = listeEleveById;
	}

	public Inscription getInscription() {
		return inscription;
	}

	public void setInscription(Inscription inscription) {
		this.inscription = inscription;
	}

}
