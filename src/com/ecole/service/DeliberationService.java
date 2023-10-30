/**
 * 
 */
package com.ecole.service;

import java.io.InputStream;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.jboss.seam.Component;
import org.apache.poi.util.SystemOutLogger;
import org.hibernate.Session;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;

import com.chaka.projet.entity.Utilisateur;
import com.ecole.entity.Absence;
import com.ecole.entity.AnneeScolaire;
import com.ecole.entity.Appreciation;
import com.ecole.entity.Appreciations;
import com.ecole.entity.Classe;
import com.ecole.entity.Decision;
import com.ecole.entity.Deliberation;
import com.ecole.entity.DeliberationFinal;
import com.ecole.entity.DeliberationMS;
import com.ecole.entity.Eleve;
import com.ecole.entity.Evaluation;
import com.ecole.entity.Inscription;
import com.ecole.entity.Institution;
import com.ecole.entity.Matiere;
import com.ecole.entity.MatiereClasse;
import com.ecole.entity.Niveau;
import com.ecole.entity.Note;
import com.ecole.entity.Notes;
import com.ecole.entity.ParamInscription;
import com.ecole.entity.Retard;
import com.ecole.entity.Semestres;
import com.ecole.entity.TypeNote;
import com.rhospi.commons.ChakaUtils.ExportOption;

import jdk.nashorn.internal.codegen.ClassEmitter;

import com.rhospi.commons.FileUploadService;

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
	private List<MatiereClasse> listeMatClasse = new ArrayList<MatiereClasse>();
	private Evaluation evaluation = new Evaluation();
	private String rangs;
	private int rang;
	private int itter;
	private int itter1;
	private int ccoef;
	private String cnote;
	private String ccf;
	private List<Deliberation> listeDeliberation = new ArrayList<Deliberation>();
	private List<Deliberation> listeDeliberationS = new ArrayList<Deliberation>();
	private List<Deliberation> listeDeliberationSS = new ArrayList<Deliberation>();
	private List<Deliberation> listeDeliberationF = new ArrayList<Deliberation>();
	private List<Note> listeNote = new ArrayList<Note>();
	private String chaine;
	private String showModal = "";
	private boolean choix;
	private Eleve eleve;
	private List<DeliberationFinal> listeDeliF = new ArrayList<DeliberationFinal>();
	private List<DeliberationFinal> listeDeliFS = new ArrayList<DeliberationFinal>();
	private List<DeliberationFinal> listeDeliFSS = new ArrayList<DeliberationFinal>();
	private List<DeliberationFinal> listeDeliAn = new ArrayList<DeliberationFinal>();
	private String decision;
	List<Deliberation> listeDeli = new ArrayList<Deliberation>();
	@In
	private AnneeScolaire annee;
	FileUploadService filePrintService;
	@In(required = false)
	@Out(required = false)
	private Utilisateur utilisateur;

	private List<Semestres> listeSemestre = new ArrayList<Semestres>();

	private Semestres semestre = new Semestres();

	private List<DeliberationMS> listeDeliberationMS = new ArrayList<DeliberationMS>();

	private List<DeliberationMS> listeDeliberationRang = new ArrayList<DeliberationMS>();

	private List<DeliberationMS> listeDeliberationRang1 = new ArrayList<DeliberationMS>();

	private List<DeliberationMS> listeDeliberationRang2 = new ArrayList<DeliberationMS>();

	private List<DeliberationMS> listeDeliberationRang3 = new ArrayList<DeliberationMS>();

	private List<DeliberationMS> listeDeliberationS1 = new ArrayList<DeliberationMS>();

	private List<DeliberationMS> listeDeliberationMSF = new ArrayList<DeliberationMS>();

	private List<DeliberationMS> listeDeliberationMSF1 = new ArrayList<DeliberationMS>();

	private List<DeliberationMS> listeDeliberationMSFF = new ArrayList<DeliberationMS>();

	private List<DeliberationMS> listeDeliberationMSFFS = new ArrayList<DeliberationMS>();

	private List<DeliberationMS> listeDeliberationMSFFSS = new ArrayList<DeliberationMS>();

	private List<DeliberationMS> listeDeliberationMSFF2 = new ArrayList<DeliberationMS>();

	private List<DeliberationMS> listeDeliberationMSVIEW = new ArrayList<DeliberationMS>();

	private Semestres ss = new Semestres();
	private List<Evaluation> listeEval = new ArrayList<Evaluation>();

	private String moyenneGen;

	private String rangGen;

	private boolean notecomp;

	private String typenote;

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
		listeNiveau = dataSource.createQuery("From Niveau where code <>:pcode").setParameter("pcode", "PRE").list();
		listeEval = new ArrayList<Evaluation>();
		listeEval = dataSource.createQuery("From Evaluation ").list();
		listeElevesNonNote = new ArrayList<Eleve>();
		this.setClasse(new Classe());
		this.setEvaluation(new Evaluation());
		listeDeliberation = new ArrayList<Deliberation>();
		listeDeliberationF = new ArrayList<Deliberation>();
		listeDeliberationMS = new ArrayList<DeliberationMS>();
		listeDeliberationMSFF = new ArrayList<DeliberationMS>();
		listeDeliberationMSVIEW = new ArrayList<DeliberationMS>();
		return "/pages/nuramecole/deliberation.xhtml";
	}

	@SuppressWarnings("unchecked")
	public String versDeliberationAn() {
		listeNiveau = new ArrayList<Niveau>();
		listeNiveau = dataSource.createQuery("From Niveau where code <>:pcode").setParameter("pcode", "PRE").list();
		listeEval = new ArrayList<Evaluation>();
		listeEval = dataSource.createQuery("From Evaluation ").list();
		listeElevesNonNote = new ArrayList<Eleve>();
		this.setClasse(new Classe());
		this.setEvaluation(new Evaluation());
		listeDeliberation = new ArrayList<Deliberation>();
		listeDeliF = new ArrayList<DeliberationFinal>();
		listeDeliAn = new ArrayList<DeliberationFinal>();
		return "/pages/nuramecole/deliberationan.xhtml";
	}

	@SuppressWarnings("unchecked")
	public String versVoirDeliberation() {
		listeNiveau = new ArrayList<Niveau>();
		listeNiveau = dataSource.createQuery("From Niveau where code <>:pcode").setParameter("pcode", "PRE").list();
		listeEval = new ArrayList<Evaluation>();
		listeEval = dataSource.createQuery("From Evaluation ").list();
		listeElevesNonNote = new ArrayList<Eleve>();
		this.setClasse(new Classe());
		this.setEvaluation(new Evaluation());
		listeDeliberation = new ArrayList<Deliberation>();
		listeDeliberationF = new ArrayList<Deliberation>();
		listeDeliberationMS = new ArrayList<DeliberationMS>();
		listeDeliberationMSFF = new ArrayList<DeliberationMS>();
		listeDeliberationMSVIEW = new ArrayList<DeliberationMS>();
		return "/pages/nuramecole/voirdeliberation.xhtml";
	}

	@SuppressWarnings("unchecked")
	public void delibererAnnuelle() {

		if (classe == null) {
			FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique", "Veuillez choisir une classe ");
			return;
		}
		listeDeliberationF = dataSource
				.createQuery("From Deliberation d inner join fetch d.annee an inner join fetch d.evaluation ev "
						+ " inner join fetch d.eleve inner join fetch d.classe c where an =:pan and  c=:pc ")
				.setParameter("pan", annee).setParameter("pc", classe).list();

		boolean dexiste = false;
		for (Evaluation ev : listeEval) {
			dexiste = false;
			for (Deliberation d : listeDeliberationF) {

				if (ev.getIdEvaluation().equals(d.getEvaluation().getIdEvaluation())) {

					dexiste = true;
					break;
				}
			}

			if (dexiste == false) {

				FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique",
						"Veuillez d'abord délibérer pour " + ev.getLibelle());
				return;
			}
		}

		listeDeliAn = new ArrayList<DeliberationFinal>();

		listeDeliAn = dataSource.createQuery(
				"From DeliberationFinal d inner join fetch d.classe c inner join fetch d.annee inner join fetch d.eleve inner join fetch d.institution i"
						+ " where c =:pc and d.annee =:pan and i =:pi")
				.setParameter("pc", classe).setParameter("pan", annee).setParameter("pi", utilisateur.getInstitution())
				.list();

		if (listeDeliAn.size() == 0) {
			ParamInscription p = (ParamInscription) dataSource.createQuery(
					"From ParamInscription p inner join fetch p.classe c inner join fetch p.annee pa  inner join fetch p.institution i"
							+ " where c =:pc and pa =:pa and i =:pi ")
					.setParameter("pc", classe).setParameter("pa", annee)
					.setParameter("pi", utilisateur.getInstitution()).uniqueResult();

			if (p != null) {
				List<Inscription> liste = dataSource.createQuery(
						"From Inscription i inner join fetch i.eleve inner join fetch i.paramins p inner join fetch i.institution s"
								+ " where p =:pp and s =:ps")
						.setParameter("pp", p).setParameter("ps", utilisateur.getInstitution()).list();
				listeEleves = new ArrayList<Eleve>();

				for (Inscription in : liste) {
					listeEleves.add(in.getEleve());
				}
			}
			Double moyenne = 0.0;
			Double moyenneAn = 0.0;
			String moyennes;
			listeDeliF = new ArrayList<DeliberationFinal>();
			for (Eleve eleve : listeEleves) {
				DeliberationFinal df = new DeliberationFinal();
				moyenne = getCumulMoyenne(eleve);
				moyenneAn = moyenne / 3;
				df.setEleve(eleve);
				moyennes = "" + moyenneAn;
				df.setAnnee(annee);
				df.setClasse(classe);
				df.setMoyans(moyennes);
				df.setUse("Y");
				splitMoyenAn(df);
				df.setMoyan(Double.parseDouble(df.getMoyans()));
				for (Evaluation ev : listeEval) {
					alimenterDeliberation(df, ev, eleve);
				}
				listeDeliF.add(df);
			}

			listeDeliAn = new ArrayList<DeliberationFinal>();
			rang = 1;
			itter = listeDeliF.size();
			itter1 = 0;
			while (listeDeliF.size() > 0) {
				DeliberationFinal deli = gererSupAn();
				deli.setUse("X");
				List<DeliberationFinal> liste = new ArrayList<DeliberationFinal>();
				liste = getMoyenEqualFinal(deli);
				if (liste.size() > 1) {
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
				deli.setRangan(rangs);
				if (liste.size() > 0) {
					for (DeliberationFinal d : liste) {
						d.setRangan(rangs);
						d.setUse("X");
						listeDeliAn.add(d);
						listeDeliFS.add(d);
						rang = rang + 1;
					}
				} else {
					rang = rang + 1;
				}

				listeDeliFSS = new ArrayList<DeliberationFinal>();
				listeDeliFSS = listeDeliF;
				listeDeliF = new ArrayList<DeliberationFinal>();
				for (DeliberationFinal dddd : listeDeliFSS) {
					if (dddd.getUse().equalsIgnoreCase("X")) {
						itter1++;
					} else {
						listeDeliF.add(dddd);
					}
				}

			}
			List<Appreciation> listeAp = new ArrayList<Appreciation>();
			listeAp = dataSource.createQuery("From Appreciation a inner join fetch a.institution i where i =:pi ")
					.setParameter("pi", utilisateur.getInstitution()).list();
			Decision decision = (Decision) dataSource.createQuery("From Decision d  where d.code =:pcode  ")
					.setParameter("pcode", "ELE").uniqueResult();
			for (DeliberationFinal df : listeDeliAn) {
				Appreciation ap = getAp(listeAp, df.getMoyan());
				df.setApa(ap.getLibelle());
				if (df.getMoyan() < decision.getMoy()) {
					df.setDecision("Redouble");
				} else {
					df.setDecision("Passe en classe supérieure");
				}
				df.setInstitution(utilisateur.getInstitution());
				dataSource.save(df);
			}
		}
	}

	public void getSemestre1() {
		semestre = new Semestres();
		semestre = (Semestres) dataSource.get(Semestres.class, ss.getId());
	}

	public void changerDecision(DeliberationFinal deli) {
		deli.setDecision(decision);
		dataSource.update(deli);
	}

	@SuppressWarnings("unchecked")
	public void changerDecision(DeliberationMS deli) {
		List<DeliberationMS> liste = new ArrayList<DeliberationMS>();
		liste = dataSource.createQuery(
				"From DeliberationMS  d inner join fetch d.semestre s inner join fetch d.annee an inner join fetch d.eleve ev "
						+ " inner join fetch d.institution i  where   " + " s=:ps and an =:pan and ev=:pev and i =:pi")
				.setParameter("ps", semestre).setParameter("pan", annee).setParameter("pev", deli.getEleve())
				.setParameter("pi", utilisateur.getInstitution()).list();
		for (DeliberationMS d : liste) {
			d.setDecision(decision);
			dataSource.update(d);
		}
		deli.setDecision(decision);
	}

	public Double getCumulMoyenne(Eleve eleve) {
		Double cumul = 0.0;
		for (Deliberation d : listeDeliberationF) {
			if (d.getEleve().getIdeleve().equals(eleve.getIdeleve())) {
				cumul = cumul + d.getMoyenne();
			}
		}
		return cumul;
	}

	public void alimenterDeliberation(DeliberationFinal deli, Evaluation ev, Eleve eleve) {
		for (Deliberation d : listeDeliberationF) {
			if (eleve.getIdeleve().equals(d.getEleve().getIdeleve())
					&& ev.getIdEvaluation().equals(d.getEvaluation().getIdEvaluation())) {

				if (d.getEvaluation().getLibelle().charAt(0) == '1') {
					deli.setRang1(d.getRang());
					deli.setAp1(d.getAp());
					deli.setMoy1(d.getMoyenne());
					deli.setMoy1s(d.getMoy());

				}
				if (d.getEvaluation().getLibelle().charAt(0) == '2') {
					deli.setRang2(d.getRang());
					deli.setAp2(d.getAp());
					deli.setMoy2(d.getMoyenne());
					deli.setMoy2s(d.getMoy());
				}
				if (d.getEvaluation().getLibelle().charAt(0) == '3') {
					deli.setRang3(d.getRang());
					deli.setAp3(d.getAp());
					deli.setMoy3(d.getMoyenne());
					deli.setMoy3s(d.getMoy());
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void chargerListeClasse() {
		listeClasse = new ArrayList<Classe>();
		listeClasse = dataSource.createQuery(
				" From Classe c inner join fetch c.niveau n inner join fetch c.institution i where n=:pn and i =:pi")
				.setParameter("pn", niveau).setParameter("pi", utilisateur.getInstitution()).list();
		if (niveau.getCode().equalsIgnoreCase("ELE") || niveau.getCode().equalsIgnoreCase("PRE")) {
			typenote = "1";
		} else {
			typenote = "2";
			setListeSemestre(new ArrayList<Semestres>());
			setListeSemestre(dataSource.createQuery("From Semestres ").list());

		}
	}

	public void cocherTout() {

		for (Deliberation d : listeDeliberationF) {
			d.setChoix(choix);
		}
	}

	@SuppressWarnings("unchecked")
	public void voirdeliberer() {
		if (typenote.equalsIgnoreCase("1")) {
			if (evaluation == null || evaluation.getIdEvaluation() == null) {
				FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique", "Evaluation obligatoire");
				return;
			}
			if (classe == null || classe.getIdclasse() == null) {
				FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique", "Classe obligatoire");
				return;
			}
			ParamInscription p = (ParamInscription) dataSource.createQuery(
					"From ParamInscription p inner join fetch p.classe c inner join fetch p.annee pa inner join fetch p.institution i "
							+ " where c =:pc and pa =:pa and i =:pi")
					.setParameter("pc", classe).setParameter("pa", annee)
					.setParameter("pi", utilisateur.getInstitution()).uniqueResult();

			listeDeliberationF = new ArrayList<Deliberation>();
			listeDeliberationF = dataSource
					.createQuery("From Deliberation d inner join fetch d.classe c inner join fetch d.evaluation ev "
							+ " inner join fetch d.annee an inner join fetch d.institution i where c =:pc and an =:pan and ev =:pev and i =:pi")
					.setParameter("pc", classe).setParameter("pan", annee).setParameter("pev", evaluation)
					.setParameter("pi", utilisateur.getInstitution()).list();

			if (listeDeliberationF.size() == 0) {
				FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique",
						"Aucune délibération trouvée");
				return;
			} else {
				this.setChoix(true);
				for (Deliberation d : listeDeliberationF) {
					d.setChoix(true);
				}
			}
		} else {

			if (semestre == null || semestre.getId() == null) {
				FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique", "Semestre obligatoire");
				return;
			}
			if (classe == null || classe.getIdclasse() == null) {
				FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique", "Classe obligatoire");
				return;
			}
			ParamInscription p = (ParamInscription) dataSource.createQuery(
					"From ParamInscription p inner join fetch p.classe c inner join fetch p.annee pa inner join fetch p.institution i "
							+ " where c =:pc and pa =:pa and i =:pi")
					.setParameter("pc", classe).setParameter("pa", annee)
					.setParameter("pi", utilisateur.getInstitution()).uniqueResult();
			listeDeliberationMS = new ArrayList<DeliberationMS>();
			listeDeliberationMSF1 = new ArrayList<DeliberationMS>();
			listeDeliberationMS = dataSource
					.createQuery("From DeliberationMS d inner join fetch d.classe c inner join fetch d.annee an"
							+ " inner join fetch d.semestre ev inner join fetch d.eleve inner join fetch d.appreciation inner join fetch d.institution i"
							+ " inner join fetch d.appreciationgen inner join fetch d.matiere where c =:pc and an =:pan and ev =:pev and i =:pi order by d.total desc")
					.setParameter("pc", classe).setParameter("pan", annee).setParameter("pev", semestre)
					.setParameter("pi", utilisateur.getInstitution()).list();

			if (listeDeliberationMS.size() == 0) {
				FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique",
						"Classe " + classe.getLibelle() + " pas encore délibérée pour  " + semestre.getLibelle());
				return;
			}
			for (DeliberationMS d : listeDeliberationMS) {

				if (!donneesExiste(d.getEleve())) {
					listeDeliberationMSF1.add(d);
				}
			}
		}

	}

	@SuppressWarnings("unchecked")
	public String getRangAnnuel(DeliberationMS deli) {
		List<DeliberationMS> listedeli = dataSource.createQuery(
				"From DeliberationMS d inner join fetch d.eleve e inner join fetch d.annee a inner join fetch d.institution i "
						+ " inner join fetch d.semestre s where e=:pe and a=:pa and s=:ps and i =:pi and d.ranggan IS NOT NULL ")
				.setParameter("pe", deli.getEleve()).setParameter("pa", annee).setParameter("ps", semestre)
				.setParameter("pi", utilisateur.getInstitution()).list();

		return listedeli.get(0).getRanggan();
	}

	@SuppressWarnings("unchecked")
	public void imprimerTout() {
		Institution in = (Institution) dataSource.get(Institution.class, utilisateur.getInstitution().getId());
		String logo = "";
		logo = "/css2/" + in.getImage();
		ParamInscription p = (ParamInscription) dataSource.createQuery(
				"From ParamInscription p inner join fetch p.classe c inner join fetch p.annee pa inner join fetch p.institution i "
						+ " where c =:pc and pa =:pa and i =:pi ")
				.setParameter("pc", classe).setParameter("pa", annee).setParameter("pi", utilisateur.getInstitution())
				.uniqueResult();

		List<Inscription> liste = new ArrayList<Inscription>();
		if (p != null) {
			liste = dataSource.createQuery(
					"From Inscription i inner join fetch i.eleve inner join fetch i.paramins p inner join fetch i.institution i where p =:pp and i =:pi")
					.setParameter("pp", p).setParameter("pi", utilisateur.getInstitution()).list();

		}
		Map<String, Object> param = new HashMap<String, Object>();

		Double total = 0.0;
		listeDeli = new ArrayList<Deliberation>();
		for (Deliberation d : listeDeliberationF) {
			// if (d.isChoix()) {
			total = 0.0;
			listeNote = new ArrayList<Note>();
			listeNote = dataSource.createQuery("From Note n inner join fetch n.cl c inner join fetch n.eleve e "
					+ " inner join fetch n.evaluation ev inner join fetch n.annee an inner join fetch n.matiere  "
					+ " inner join fetch n.institution i"
					+ " where c =:pc and an =:pan and ev =:pev and e =:pe and i =:pi").setParameter("pc", classe)
					.setParameter("pan", annee).setParameter("pev", evaluation).setParameter("pe", d.getEleve())
					.setParameter("pi", utilisateur.getInstitution()).list();
//				for (Note n : listeNote) {
//					total = total + n.getNote();
//				}
			for (Note n : listeNote) {
				n.setEcole(in.getNom());
				n.setEff("" + liste.size());
				n.setSlogan(in.getSologan());
				n.setTel(in.getTelephone());
				n.setTotal1(d.getCumul());
				n.setMoy(d.getMoy());
				n.setAp(d.getAp());
				n.setRang(d.getRang());
				ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
				ServletContext sc = (ServletContext) ec.getContext();
				InputStream is = sc.getResourceAsStream(logo);
				n.setLogo(is);
			}
			d.setListeNote(listeNote);
			listeDeli.add(d);

			// }
		}

		getFilePrintService().imprimer("ecole", "allbulletin", param, listeDeli, utilisateur, ExportOption.PDF);
		this.setShowModal("javascript:Richfaces.showModalPanel('modalPdf')");
	}

	@SuppressWarnings("unchecked")
	public void imprimerToutMS() {
		Institution in = (Institution) dataSource.get(Institution.class, utilisateur.getInstitution().getId());
		String logo = "";
		logo = "/css2/" + in.getImage();
		ParamInscription p = (ParamInscription) dataSource.createQuery(
				"From ParamInscription p inner join fetch p.classe c inner join fetch p.annee pa inner join fetch p.institution i"
						+ " where c =:pc and pa =:pa and i =:pi")
				.setParameter("pc", classe).setParameter("pa", annee).setParameter("pi", utilisateur.getInstitution())
				.uniqueResult();

		List<Inscription> liste = new ArrayList<Inscription>();
		if (p != null) {
			liste = dataSource.createQuery(
					"From Inscription i inner join fetch i.eleve inner join fetch i.paramins p inner join fetch i.institution s where p =:pp and s =:ps")
					.setParameter("pp", p).setParameter("ps", utilisateur.getInstitution()).list();

			listeEleves = new ArrayList<Eleve>();
			for (Inscription ins : liste) {
				listeEleves.add(ins.getEleve());
			}

		}
		Map<String, Object> param = new HashMap<String, Object>();
		for (Eleve eleve : listeEleves) {
			listeDeliberationMS = new ArrayList<DeliberationMS>();
			listeDeliberationMS = dataSource
					.createQuery("From DeliberationMS d inner join fetch d.classe c inner join fetch d.annee an"
							+ " inner join fetch d.semestre ev inner join fetch d.eleve inner join fetch d.appreciation "
							+ " inner join fetch d.institution i"
							+ " inner join fetch d.appreciationgen inner join fetch d.matiere inner join fetch d.eleve el where  an =:pan and ev =:pev "
							+ " and el=:pel and i =:pi ")
					.setParameter("pan", annee).setParameter("pev", semestre).setParameter("pel", eleve)
					.setParameter("pi", utilisateur.getInstitution()).list();

			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			ServletContext sc = (ServletContext) ec.getContext();
			InputStream is = sc.getResourceAsStream(logo);

			listeDeliberationMS.get(0).setEcole(in.getNom());
			listeDeliberationMS.get(0).setEff("" + liste.size());
			listeDeliberationMS.get(0).setSlogan(in.getSologan());
			listeDeliberationMS.get(0).setTel(in.getTelephone());
			listeDeliberationMS.get(0).setIa(in.getIa());
			listeDeliberationMS.get(0).setIef(in.getIef());
			listeDeliberationMS.get(0).setLogo(is);

			// System.out.println("dec " +
			// listeDeliberationMS.get(listeDeliberationMS.size() - 1).getDecision());
			if (semestre.getCode().equalsIgnoreCase("2")) {

				if (listeDeliberationMS.get(listeDeliberationMS.size() - 1).getDecision()
						.equalsIgnoreCase("Passe en classe supérieure")) {
					listeDeliberationMS.get(listeDeliberationMS.size() - 1).setD1("X");
				} else {
					listeDeliberationMS.get(listeDeliberationMS.size() - 1).setD1(" ");
				}
				if (listeDeliberationMS.get(listeDeliberationMS.size() - 1).getDecision()
						.equalsIgnoreCase("Redouble")) {
					listeDeliberationMS.get(listeDeliberationMS.size() - 1).setD2("X");
				} else {
					listeDeliberationMS.get(listeDeliberationMS.size() - 1).setD2("");
				}
				if (listeDeliberationMS.get(listeDeliberationMS.size() - 1).getDecision()
						.equalsIgnoreCase("Exclu(e)")) {
					listeDeliberationMS.get(listeDeliberationMS.size() - 1).setD3("X");
				} else {
					listeDeliberationMS.get(listeDeliberationMS.size() - 1).setD3("");
				}
				listeDeliberationMS.get(listeDeliberationMS.size() - 1)
						.setRanga(getRangAnnuel(listeDeliberationMS.get(0)));
			}

			eleve.setListeDeli(listeDeliberationMS);
		}

		if (semestre.getCode().equalsIgnoreCase("1"))

		{
			getFilePrintService().imprimer("ecole", "allbulletinms", param, listeEleves, utilisateur, ExportOption.PDF);
		} else {
			getFilePrintService().imprimer("ecole", "allbulletinms2", param, listeEleves, utilisateur,
					ExportOption.PDF);
		}

		this.setShowModal("javascript:Richfaces.showModalPanel('modalPdf')");
	}

	@SuppressWarnings("unchecked")
	public void imprimerToutAn() {
		listeEval = new ArrayList<Evaluation>();
		listeEval = dataSource.createQuery("From Evaluation ").list();
		Institution in = (Institution) dataSource.get(Institution.class, utilisateur.getInstitution().getId());
		String logo = "";
		logo = "/css2/" + in.getImage();
		listeNote = new ArrayList<Note>();
		listeNote = new ArrayList<Note>();

		ParamInscription p = (ParamInscription) dataSource.createQuery(
				"From ParamInscription p inner join fetch p.classe c inner join fetch p.annee pa inner join fetch p.institution i "
						+ " where c =:pc and pa =:pa and i =:pi")
				.setParameter("pc", classe).setParameter("pa", annee).setParameter("pi", utilisateur.getInstitution())
				.uniqueResult();

		List<Inscription> liste = new ArrayList<Inscription>();
		if (p != null) {
			liste = dataSource.createQuery(
					"From Inscription i inner join fetch i.eleve inner join fetch i.paramins p inner join fetch i.institution s where p =:pp and s =:ps")
					.setParameter("pp", p).setParameter("ps", utilisateur.getInstitution()).list();

		}
		listeNote = dataSource
				.createQuery("From Note n inner join fetch n.cl c inner join fetch n.eleve e "
						+ " inner join fetch n.evaluation ev inner join fetch n.annee an inner join fetch n.matiere "
						+ " inner join fetch n.institution i" + " where c =:pc and an =:pan and i =:pi")
				.setParameter("pc", classe).setParameter("pan", annee).setParameter("pi", utilisateur.getInstitution())
				.list();
		List<Deliberation> listeD = new ArrayList<Deliberation>();
		for (DeliberationFinal d : listeDeliAn) {
			Deliberation deli = new Deliberation();
			deli.setListeNote(getNotesEleve(d.getEleve(), d));
			deli.getListeNote().get(0).setEcole(in.getNom());
			deli.getListeNote().get(0).setSlogan(in.getSologan());
			deli.getListeNote().get(0).setTel(in.getTelephone());
			deli.getListeNote().get(0).setEff("" + liste.size());
			deli.getListeNote().get(0).setAnnee(annee);
			deli.getListeNote().get(deli.getListeNote().size() - 1).setRangan(d.getRangan());
			deli.getListeNote().get(deli.getListeNote().size() - 1).setMoyan(d.getMoyans());
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			ServletContext sc = (ServletContext) ec.getContext();
			InputStream is = sc.getResourceAsStream(logo);
			deli.getListeNote().get(0).setLogo(is);

			if (d.getDecision().equalsIgnoreCase("Passe en classe supérieure")) {
				deli.getListeNote().get(deli.getListeNote().size() - 1).setDec1("X  Passe en classe supérieure");
			} else {
				deli.getListeNote().get(deli.getListeNote().size() - 1).setDec1("Passe en classe supérieure");
			}
			if (d.getDecision().equalsIgnoreCase("Redouble")) {
				deli.getListeNote().get(deli.getListeNote().size() - 1).setDec2("X Redouble");

			} else {
				deli.getListeNote().get(deli.getListeNote().size() - 1).setDec2("Redouble");
			}
			if (d.getDecision().equalsIgnoreCase("Exclu(e)")) {

				deli.getListeNote().get(deli.getListeNote().size() - 1).setDec3("X Exclu(e)");
			} else {
				deli.getListeNote().get(deli.getListeNote().size() - 1).setDec3("Exclu(e)");
			}
			listeD.add(deli);
		}
		Map<String, Object> param = new HashMap<String, Object>();
		getFilePrintService().imprimer("ecole", "allbulletanm", param, listeD, utilisateur, ExportOption.PDF);

		this.setShowModal("javascript:Richfaces.showModalPanel('modalPdf')");
	}

	public List<Note> getNotesEleve(Eleve eleve, DeliberationFinal deli) {

		List<Note> listeNoteD = new ArrayList<Note>();
		Double total = 0.0;
		Double total2 = 0.0;
		Double total3 = 0.0;
		boolean existe = false;
		Note note = new Note();
		int i = 1;
		for (Evaluation e : listeEval) {
			total = 0.0;
			if (i == 1) {
				for (Note n : listeNote) {
					if (eleve.getIdeleve().equals(n.getEleve().getIdeleve())
							&& e.getIdEvaluation().equals(n.getEvaluation().getIdEvaluation())) {
						note = new Note();
						note.setEleve(eleve);
						note.setCoef1(n.getCoef());
						note.setRang1(deli.getRang1());
						note.setCl(deli.getClasse());
						note.setAnnee(annee);
						note.setMatiere(n.getMatiere());
						note.setNote2(0);
						note.setNote3(0);
						note.setMoy1(deli.getMoy1s());
						note.setMoy2(deli.getMoy2s());
						note.setMoy3(deli.getMoy3s());
						note.setNote1(n.getNote());
						note.setAp1(deli.getAp1());
						note.setAp2(deli.getAp2());
						note.setEcole(chaine);
						note.setAp3(deli.getAp3());
						note.setRang1(deli.getRang1());
						note.setRang2(deli.getRang2());
						note.setRang3(deli.getRang3());
						listeNoteD.add(note);
					}
				}
			}
			if (i == 2) {
				for (Note n : listeNote) {
					if (eleve.getIdeleve().equals(n.getEleve().getIdeleve())
							&& e.getIdEvaluation().equals(n.getEvaluation().getIdEvaluation())) {
						existe = false;
						for (Note nx : listeNoteD) {
							if (nx.getMatiere().getIdmatiere().equals(n.getMatiere().getIdmatiere())) {
								nx.setEleve(deli.getEleve());
								nx.setAp2(deli.getAp2());
								nx.setCoef2(n.getCoef());
								nx.setRang2(deli.getRang2());
								nx.setCl(deli.getClasse());
								nx.setAnnee(annee);
								nx.setMatiere(n.getMatiere());

								nx.setNote2(n.getNote());
								nx.setMoy2(deli.getMoy2s());
								nx.setMoy1(deli.getMoy1s());
								nx.setMoy3(deli.getMoy3s());
								nx.setAp(deli.getAp1());
								nx.setAp2(deli.getAp2());
								nx.setAp3(deli.getAp3());
								nx.setRang1(deli.getRang1());
								nx.setRang2(deli.getRang2());
								nx.setRang3(deli.getRang3());
								existe = true;
								break;
							}
						}
						if (existe == false) {
							note = new Note();
							note.setEleve(deli.getEleve());
							note.setAp2(deli.getAp2());
							note.setCoef2(n.getCoef());
							note.setRang2(deli.getRang2());
							note.setCl(deli.getClasse());
							note.setAnnee(annee);
							note.setMatiere(n.getMatiere());
							note.setNote1(0);
							note.setNote3(0);
							note.setMoy2(deli.getMoy2s());
							note.setMoy1(deli.getMoy1s());
							note.setMoy3(deli.getMoy3s());
							note.setNote2(n.getNote());
							note.setAp1(deli.getAp1());
							note.setAp2(deli.getAp2());
							note.setAp3(deli.getAp3());
							note.setRang1(deli.getRang1());
							note.setRang2(deli.getRang2());
							note.setRang3(deli.getRang3());
							listeNoteD.add(note);
						}
					}
				}
			}
			if (i == 3) {
				for (Note n : listeNote) {
					if (eleve.getIdeleve().equals(n.getEleve().getIdeleve())
							&& e.getIdEvaluation().equals(n.getEvaluation().getIdEvaluation())) {
						existe = false;
						for (Note nx : listeNoteD) {
							if (nx.getMatiere().getIdmatiere().equals(n.getMatiere().getIdmatiere())) {
								nx.setEleve(deli.getEleve());
								nx.setAp3(deli.getAp3());
								nx.setCoef3(n.getCoef());
								nx.setRang3(deli.getRang3());
								nx.setCl(deli.getClasse());
								nx.setAnnee(annee);
								nx.setMatiere(n.getMatiere());
								nx.setMoy3(deli.getMoy3s());
								nx.setMoy1(deli.getMoy1s());
								nx.setMoy2(deli.getMoy2s());
								nx.setNote3(n.getNote());
								nx.setAp(deli.getAp1());
								nx.setAp2(deli.getAp2());
								nx.setAp3(deli.getAp3());
								nx.setRang1(deli.getRang1());
								nx.setRang2(deli.getRang2());
								nx.setRang3(deli.getRang3());
								existe = true;
								break;
							}
						}
						if (existe == false) {
							note = new Note();
							note.setEleve(deli.getEleve());
							note.setAp3(deli.getAp3());
							note.setCoef3(n.getCoef());
							note.setRang3(deli.getRang3());
							note.setCl(deli.getClasse());
							note.setAnnee(annee);
							note.setMatiere(n.getMatiere());
							note.setNote1(0);
							note.setNote2(0);
							note.setMoy1(deli.getMoy1s());
							note.setMoy2(deli.getMoy2s());
							note.setMoy3(deli.getMoy3s());
							note.setNote3(n.getNote());
							note.setRang1(deli.getRang1());
							note.setRang2(deli.getRang2());
							note.setRang3(deli.getRang3());
							note.setAp1(deli.getAp1());
							note.setAp2(deli.getAp2());
							note.setAp3(deli.getAp3());
							listeNoteD.add(note);
						}
					}
				}
			}
			i++;
		}
		return listeNoteD;
	}

	@SuppressWarnings("unchecked")
	public void imprimerBulletinAn(DeliberationFinal deli) {
		List<Note> listeNoteD = new ArrayList<Note>();
		listeEval = new ArrayList<Evaluation>();
		listeEval = dataSource.createQuery("From Evaluation ").list();
		Institution in = (Institution) dataSource.get(Institution.class, utilisateur.getInstitution().getId());
		String logo = "";
		logo = "/css2/" + in.getImage();
		listeNote = new ArrayList<Note>();
		listeNote = new ArrayList<Note>();
		listeNote = dataSource
				.createQuery("From Note n inner join fetch n.cl c inner join fetch n.eleve e "
						+ " inner join fetch n.evaluation ev inner join fetch n.annee an inner join fetch n.matiere "
						+ " inner join fetch n.institution i" + " where c =:pc and an =:pan  and e =:pe and i =:pi")
				.setParameter("pc", classe).setParameter("pan", annee).setParameter("pe", deli.getEleve())
				.setParameter("pi", utilisateur.getInstitution()).list();
		Double total = 0.0;
		Double total2 = 0.0;
		Double total3 = 0.0;
		boolean existe = false;
		Note note = new Note();
		int i = 1;
		for (Evaluation e : listeEval) {
			total = 0.0;
			if (i == 1) {
				for (Note n : listeNote) {
					if (e.getIdEvaluation().equals(n.getEvaluation().getIdEvaluation())) {
						note = new Note();
						note.setEleve(deli.getEleve());
						note.setCoef1(n.getCoef());
						note.setRang1(deli.getRang1());
						note.setCl(deli.getClasse());
						note.setAnnee(annee);
						note.setMatiere(n.getMatiere());
						note.setNote2(0);
						note.setNote3(0);
						note.setMoy1(deli.getMoy1s());
						note.setMoy2(deli.getMoy2s());
						note.setMoy3(deli.getMoy3s());
						note.setNote1(n.getNote());
						note.setAp1(deli.getAp1());
						note.setAp2(deli.getAp2());
						note.setAp3(deli.getAp3());
						note.setRang1(deli.getRang1());
						note.setRang2(deli.getRang2());
						note.setRang3(deli.getRang3());
						listeNoteD.add(note);
					}
				}
			}
			if (i == 2) {
//				listeNote = new ArrayList<Note>();
//				listeNote = dataSource.createQuery("From Note n inner join fetch n.cl c inner join fetch n.eleve e "
//						+ " inner join fetch n.evaluation ev inner join fetch n.annee an inner join fetch n.matiere "
//						+ " where c =:pc and an =:pan and ev =:pev and e =:pe").setParameter("pc", classe)
//						.setParameter("pan", annee).setParameter("pev", e).setParameter("pe", deli.getEleve()).list();

				for (Note n : listeNote) {
					if (e.getIdEvaluation().equals(n.getEvaluation().getIdEvaluation())) {
						existe = false;
						for (Note nx : listeNoteD) {
							if (nx.getMatiere().getIdmatiere().equals(n.getMatiere().getIdmatiere())) {
								nx.setEleve(deli.getEleve());
								nx.setAp2(deli.getAp2());
								nx.setCoef2(n.getCoef());
								nx.setRang2(deli.getRang2());
								nx.setCl(deli.getClasse());
								nx.setAnnee(annee);
								nx.setMatiere(n.getMatiere());

								nx.setNote2(n.getNote());
								nx.setMoy2(deli.getMoy2s());
								nx.setMoy1(deli.getMoy1s());
								nx.setMoy3(deli.getMoy3s());
								nx.setAp(deli.getAp1());
								nx.setAp2(deli.getAp2());
								nx.setAp3(deli.getAp3());
								nx.setRang1(deli.getRang1());
								nx.setRang2(deli.getRang2());
								nx.setRang3(deli.getRang3());
								existe = true;
								break;
							}
						}
						if (existe == false) {
							note = new Note();
							note.setEleve(deli.getEleve());
							note.setAp2(deli.getAp2());
							note.setCoef2(n.getCoef());
							note.setRang2(deli.getRang2());
							note.setCl(deli.getClasse());
							note.setAnnee(annee);
							note.setMatiere(n.getMatiere());
							note.setNote1(0);
							note.setNote3(0);
							note.setMoy2(deli.getMoy2s());
							note.setMoy1(deli.getMoy1s());
							note.setMoy3(deli.getMoy3s());
							note.setNote2(n.getNote());
							note.setAp1(deli.getAp1());
							note.setAp2(deli.getAp2());
							note.setAp3(deli.getAp3());
							note.setRang1(deli.getRang1());
							note.setRang2(deli.getRang2());
							note.setRang3(deli.getRang3());
							listeNoteD.add(note);
						}
					}
				}
			}
			if (i == 3) {
				for (Note n : listeNote) {
					if (e.getIdEvaluation().equals(n.getEvaluation().getIdEvaluation())) {
						existe = false;
						for (Note nx : listeNoteD) {
							if (nx.getMatiere().getIdmatiere().equals(n.getMatiere().getIdmatiere())) {
								nx.setEleve(deli.getEleve());
								nx.setAp3(deli.getAp3());
								nx.setCoef3(n.getCoef());
								nx.setRang3(deli.getRang3());
								nx.setCl(deli.getClasse());
								nx.setAnnee(annee);
								nx.setMatiere(n.getMatiere());
								nx.setMoy3(deli.getMoy3s());
								nx.setMoy1(deli.getMoy1s());
								nx.setMoy2(deli.getMoy2s());
								nx.setNote3(n.getNote());
								nx.setAp(deli.getAp1());
								nx.setAp2(deli.getAp2());
								nx.setAp3(deli.getAp3());
								nx.setRang1(deli.getRang1());
								nx.setRang2(deli.getRang2());
								nx.setRang3(deli.getRang3());
								existe = true;
								break;
							}
						}
						if (existe == false) {
							note = new Note();
							note.setEleve(deli.getEleve());
							note.setAp3(deli.getAp3());
							note.setCoef3(n.getCoef());
							note.setRang3(deli.getRang3());
							note.setCl(deli.getClasse());
							note.setAnnee(annee);
							note.setMatiere(n.getMatiere());
							note.setNote1(0);
							note.setNote2(0);
							note.setMoy1(deli.getMoy1s());
							note.setMoy2(deli.getMoy2s());
							note.setMoy3(deli.getMoy3s());
							note.setNote3(n.getNote());
							note.setRang1(deli.getRang1());
							note.setRang2(deli.getRang2());
							note.setRang3(deli.getRang3());
							note.setAp1(deli.getAp1());
							note.setAp2(deli.getAp2());
							note.setAp3(deli.getAp3());
							listeNoteD.add(note);
						}
					}
				}
			}
			i++;
		}
		ParamInscription p = (ParamInscription) dataSource.createQuery(
				"From ParamInscription p inner join fetch p.classe c inner join fetch p.annee pa inner join fetch p.institution i "
						+ " where c =:pc and pa =:pa and i =:pi")
				.setParameter("pc", classe).setParameter("pa", annee).setParameter("pi", utilisateur.getInstitution())
				.uniqueResult();

		List<Inscription> liste = new ArrayList<Inscription>();
		if (p != null) {
			liste = dataSource.createQuery(
					"From Inscription i inner join fetch i.eleve inner join fetch i.paramins p inner join fetch i.institution s where p =:pp and s =:ps")
					.setParameter("pp", p).setParameter("ps", utilisateur.getInstitution()).list();

		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ecole", in.getNom());
		param.put("slogan", in.getSologan());
		param.put("tel", in.getTelephone());
		param.put("moy", deli.getMoyan());
		param.put("rang", deli.getRangan());
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ServletContext sc = (ServletContext) ec.getContext();
		InputStream is = sc.getResourceAsStream(logo);
		param.put(logo, is);
		if (deli.getDecision().equalsIgnoreCase("Passe en classe supérieure")) {
			param.put("d1", "X  Passe en classe supérieure");
		} else {
			param.put("d1", "Passe en classe supérieure");
		}
		if (deli.getDecision().equalsIgnoreCase("Redouble")) {
			param.put("d2", "X Redouble");
		} else {
			param.put("d2", "Redouble");
		}
		if (deli.getDecision().equalsIgnoreCase("Exclu(e)")) {
			param.put("d3", "X Exclu(e)");
		} else {
			param.put("d3", "Exclu(e)");
		}

//		param.put("app", deli.getAp());
		param.put("eff", liste.size());
		listeNoteD.get(0).setLogo(is);
		getFilePrintService().imprimer("ecole", "bulletinan", param, listeNoteD, utilisateur, ExportOption.PDF);

		this.setShowModal("javascript:Richfaces.showModalPanel('modalPdf')");
	}

	@SuppressWarnings("unchecked")
	public void imprimerBulletin(Deliberation deli) {
		Institution in = (Institution) dataSource.get(Institution.class, utilisateur.getInstitution().getId());
		listeNote = new ArrayList<Note>();
		listeNote = dataSource
				.createQuery("From Note n inner join fetch n.cl c inner join fetch n.eleve e "
						+ " inner join fetch n.evaluation ev inner join fetch n.annee an inner join fetch n.matiere "
						+ " inner join fetch n.institution i"
						+ " where c =:pc and an =:pan and ev =:pev and e =:pe and i =:pi")
				.setParameter("pc", classe).setParameter("pan", annee).setParameter("pev", evaluation)
				.setParameter("pe", deli.getEleve()).setParameter("pi", utilisateur.getInstitution()).list();

		Double total = 0.0;

//		for (Note n : listeNote) {
//			total = total + n.getNote();
//		}

		ParamInscription p = (ParamInscription) dataSource.createQuery(
				"From ParamInscription p inner join fetch p.classe c inner join fetch p.annee pa inner join fetch p.institution i"
						+ " where c =:pc and pa =:pa and i =:pi ")
				.setParameter("pc", classe).setParameter("pa", annee).setParameter("pi", utilisateur.getInstitution())
				.uniqueResult();

		List<Inscription> liste = new ArrayList<Inscription>();
		if (p != null) {
			liste = dataSource.createQuery(
					"From Inscription i inner join fetch i.eleve inner join fetch i.paramins p inner join fetch i.institution s where p =:pp and s =:ps")
					.setParameter("pp", p).setParameter("ps", utilisateur.getInstitution()).list();

		}
		String logo = "";
		logo = "/css2/" + in.getImage();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ecole", in.getNom());
		param.put("slogan", in.getSologan());
		param.put("tel", in.getTelephone());
		param.put("moy", deli.getMoy());
		param.put("rang", deli.getRang());
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ServletContext sc = (ServletContext) ec.getContext();
		InputStream is = sc.getResourceAsStream(logo);
		System.out.println("LOGO " + logo);
		param.put(logo, is);
		param.put("total", deli.getCumul());
		param.put("app", deli.getAp());
		param.put("eff", liste.size());
		listeNote.get(0).setLogo(is);
		getFilePrintService().imprimer("ecole", "bulletin", param, listeNote, utilisateur, ExportOption.PDF);
		this.setShowModal("javascript:Richfaces.showModalPanel('modalPdf')");
	}

	@SuppressWarnings("unchecked")
	public void imprimerBulletinMS(DeliberationMS deli) {
		Institution in = (Institution) dataSource.get(Institution.class, utilisateur.getInstitution().getId());
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ecole", in.getNom());
		param.put("slogan", in.getSologan());
		param.put("tel", in.getTelephone());
		param.put("ia", in.getIa());
		param.put("ief", in.getIef());
		String logo = "";
		logo = "/css2/" + in.getImage();
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ServletContext sc = (ServletContext) ec.getContext();
		InputStream is = sc.getResourceAsStream(logo);
		param.put(logo, is);
		ParamInscription p = (ParamInscription) dataSource.createQuery(
				"From ParamInscription p inner join fetch p.classe c inner join fetch p.annee pa inner join fetch p.institution i"
						+ " where c =:pc and pa =:pa and i =:pi ")
				.setParameter("pc", classe).setParameter("pa", annee).setParameter("pi", utilisateur.getInstitution())
				.uniqueResult();

		List<Inscription> liste = new ArrayList<Inscription>();
		if (p != null) {
			liste = dataSource.createQuery(
					"From Inscription i inner join fetch i.eleve inner join fetch i.paramins p inner join fetch i.institution s where p =:pp and s =:ps")
					.setParameter("pp", p).setParameter("ps", utilisateur.getInstitution()).list();

		}
		param.put("eff", liste.size());
		param.put("pa", getHeuresAbsence(deli.getEleve()));
		param.put("pr", getRetard(deli.getEleve()));
		List<Appreciations> listeAp = new ArrayList<Appreciations>();
		listeAp = dataSource.createQuery("From Appreciations s inner join fetch s.institution i where i =:pi")
				.setParameter("pi", utilisateur.getInstitution()).list();
		List<DeliberationMS> listeDeli = new ArrayList<DeliberationMS>();

		// listeDeli.add(deli);
		listeDeliberationMS = new ArrayList<DeliberationMS>();
		listeDeliberationMS = dataSource
				.createQuery("From DeliberationMS d inner join fetch d.classe c inner join fetch d.annee an"
						+ " inner join fetch d.semestre ev inner join fetch d.eleve inner join fetch d.appreciation"
						+ " inner join fetch d.institution i"
						+ " inner join fetch d.appreciationgen inner join fetch d.matiere inner join fetch d.eleve el where  an =:pan and ev =:pev "
						+ " and el=:pel and i =:pi ")
				.setParameter("pan", annee).setParameter("pev", semestre).setParameter("pel", deli.getEleve())
				.setParameter("pi", utilisateur.getInstitution()).list();
		if (semestre.getCode().equalsIgnoreCase("1")) {
			listeDeliberationMS.get(0).setLogo(is);
			getFilePrintService().imprimer("ecole", "bulletinms", param, listeDeliberationMS, utilisateur,
					ExportOption.PDF);
		} else {
			if (deli.getDecision().equalsIgnoreCase("Passe en classe supérieure")) {
				param.put("d1", "X");
			} else {
				param.put("d1", " ");
			}
			if (deli.getDecision().equalsIgnoreCase("Redouble")) {
				param.put("d2", "X");
			} else {
				param.put("d2", "");
			}
			if (deli.getDecision().equalsIgnoreCase("Exclu(e)")) {
				param.put("d3", "X");
			} else {
				param.put("d3", "");
			}
			param.put("prang", getRangAnnuel(deli));
			listeDeliberationMS.get(0).setLogo(is);
			getFilePrintService().imprimer("ecole", "bulletinms2", param, listeDeliberationMS, utilisateur,
					ExportOption.PDF);
		}
		this.setShowModal("javascript:Richfaces.showModalPanel('modalPdf')");
	}

	@SuppressWarnings("unchecked")
	public int getHeuresAbsence(Eleve eleve) {
		int heures = 0;
		List<Absence> listeAbsence = new ArrayList<Absence>();
		listeAbsence = dataSource.createQuery(
				"From Absence ab inner join fetch ab.eleve ev inner join fetch ab.matiere inner join fetch "
						+ " ab.semestre inner join fetch ab.annee an inner join fetch ab.institution i where an=:pan and ev=:pev and i =:pi")
				.setParameter("pan", annee).setParameter("pev", eleve).setParameter("pi", utilisateur.getInstitution())
				.list();
		for (Absence ab : listeAbsence) {
			heures = heures + ab.getHeure();
		}
		return heures;
	}

	@SuppressWarnings("unchecked")
	public int getRetard(Eleve eleve) {
		int heures = 0;
		List<Retard> listeRetard = new ArrayList<Retard>();
		listeRetard = dataSource.createQuery(
				"From Retard ab inner join fetch ab.eleve ev inner join fetch ab.matiere inner join fetch ab.semestre "
						+ " inner join fetch ab.annee an inner join fetch ab.institution i where an=:pan and ev=:pev and i =:pi")
				.setParameter("pan", annee).setParameter("pev", eleve).setParameter("pi", utilisateur.getInstitution())
				.list();
		for (Retard ab : listeRetard) {
			heures = heures + ab.getHeures();
		}
		return heures;
	}

	public void delibereMSSemestre1() {

	}

	public void delibereMSSemestre2() {

	}

	@SuppressWarnings("unchecked")
	public void deliberer() {
		if (typenote.equalsIgnoreCase("1")) {
			if (evaluation == null || evaluation.getIdEvaluation() == null) {
				FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique", "Evaluation obligatoire");
				return;
			}
			if (classe == null || classe.getIdclasse() == null) {
				FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique", "Classe obligatoire");
				return;
			}
			ParamInscription p = (ParamInscription) dataSource.createQuery(
					"From ParamInscription p inner join fetch p.classe c inner join fetch p.annee pa inner join fetch p.institution i "
							+ " where c =:pc and pa =:pa and i =:pi ")
					.setParameter("pc", classe).setParameter("pa", annee)
					.setParameter("pi", utilisateur.getInstitution()).uniqueResult();

			if (p != null) {
				List<Inscription> liste = dataSource.createQuery(
						"From Inscription i inner join fetch i.eleve inner join fetch i.paramins p where p =:pp")
						.setParameter("pp", p).list();
				listeEleves = new ArrayList<Eleve>();

				for (Inscription in : liste) {
					listeEleves.add(in.getEleve());
				}
			}

			List<Deliberation> deliexiste = dataSource
					.createQuery("From Deliberation d inner join fetch d.classe c inner join fetch d.annee an"
							+ " inner join fetch d.evaluation ev inner join fetch d.institution i where c =:pc and an =:pan and ev =:pev and i =:pi")
					.setParameter("pc", classe).setParameter("pan", annee).setParameter("pev", evaluation)
					.setParameter("pi", utilisateur.getInstitution()).list();
			if (deliexiste.size() > 0) {
				FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique",
						"Classe " + classe.getLibelle() + " déja délibérée pour  " + evaluation.getLibelle());
				return;
			}
			listeMatiere = dataSource
					.createQuery("Select mc.matiere from MatiereClasse mc where mc.classe =:pc and mc.annee_scol =:pan "
							+ " and mc.eval =:peval and mc.institution =:pi")
					.setParameter("pc", classe).setParameter("pan", annee.getAnneeScolaire())
					.setParameter("peval", evaluation).setParameter("pi", utilisateur.getInstitution()).list();

			List<Note> listeNote = dataSource
					.createQuery("From Note n inner join fetch n.cl c inner join fetch n.annee an "
							+ " inner join fetch n.evaluation ev inner join fetch n.eleve"
							+ " inner join fetch n.institution i "
							+ " where c =:pc and an =:pan and ev =:pev and i =:pi")
					.setParameter("pc", classe).setParameter("pan", annee).setParameter("pev", evaluation)
					.setParameter("pi", utilisateur.getInstitution()).list();

			boolean existe = false;
			listeElevesNonNote = new ArrayList<Eleve>();

			System.out.println("Taille liste " + listeEleves.size());
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
				System.out.println("Taille liste " + listeEleves.size());
				listeDeliberationF = new ArrayList<Deliberation>();
				listeDeliberation = new ArrayList<Deliberation>();
				// Délibartion de la classe
				Double cumulnote;
				Double cumulcoef;
				Double moyen;
				String moyens;
				cumulcoef = CumulCeof();

				for (Eleve ev : listeEleves) {
					cumulnote = cumulNote(ev, listeNote);
					moyen = cumulnote / cumulcoef;
					moyens = "" + moyen;
					Deliberation d = new Deliberation();
					d.setAnnee(annee);
					cnote = "" + cumulnote;
					ccf = "" + cumulcoef;
					d.setMoy(moyens);
					splitMoyenE(d);
					d.setCumul("" + cnote);
					d.setTotalcoef("" + ccoef);
					d.setClasse(classe);
					d.setEleve(ev);
					d.setEvaluation(evaluation);
					d.setUse("Y");
					d.setMoyenne(Double.valueOf(d.getMoy()));
					d.setUser(utilisateur);
					listeDeliberation.add(d);
				}
				System.out.println("Taille liste  listeDeliberation" + listeDeliberation.size());
				rang = 1;
				itter = listeDeliberation.size();
				itter1 = 0;
				while (listeDeliberation.size() > 0) {
					// listeDeliberationS = new ArrayList<Deliberation>();
					Deliberation deli = gererSup();
					// if (!eleveExiste(deli.getEleve())) {
					List<Deliberation> liste = new ArrayList<Deliberation>();
					liste = getMoyenEqual(deli);
					if (liste.size() > 1) {
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
					// listeDeliberationF.add(deli);
					// listeDeliberationS.add(deli);
					if (liste.size() > 0) {
						// listeDeliberationF.add(deli);
						for (Deliberation d : liste) {
							d.setRang(rangs);
							listeDeliberationF.add(d);
							listeDeliberationS.add(d);
							// itter1++;
							rang = rang + 1;
						}
					} else {
						listeDeliberationF.add(deli);
						listeDeliberationS.add(deli);
						// itter1++;
						rang = rang + 1;
					}
					// System.out.println("Taille liste listeDeliberationF" +
					// listeDeliberationF.size());
					listeDeliberationSS = new ArrayList<Deliberation>();
					listeDeliberationSS = listeDeliberation;
					// System.out.println("listeDeliberationSS " + listeDeliberationSS.size());
					listeDeliberation = new ArrayList<Deliberation>();
					// System.out.println("Taille liste listeDeliberation avant" +
					// listeDeliberation.size());
					// int z = 0;
//					for (Deliberation ad : listeDeliberationSS ) {
//						//System.out.println("HERE");
//						for (Deliberation ad1 : listeDeliberationF) {
//							if (ad.getEleve().getIdeleve().equals(ad1.getEleve().getIdeleve())) {
//								System.out.println(
//										"Existe deja " + ad.getEleve().getPrenom() + " " + ad.getEleve().getNom());
//							} else {
//								z++;
//								System.out.println("AJOUT "+z);
//								listeDeliberation.add(ad);
//							}
//						}
//					}
					itter1 = listeDeliberationF.size();
					for (Deliberation dddd : listeDeliberationSS) {
						if (dddd.getUse().equalsIgnoreCase("X")) {
							// itter1++;
//							System.out.println("ELEVE " + dddd.getEleve().getPrenom() + " " + dddd.getEleve().getNom()
//									+ "Moyenne " + dddd.getMoyenne());
//							System.out.println("USE OK " + dddd.getUse());
						} else {
							listeDeliberation.add(dddd);
						}

					}
					// System.out.println("Taille liste listeDeliberation apres" +
					// listeDeliberation.size());
				}
				List<Appreciation> listeAp = new ArrayList<Appreciation>();
				listeAp = dataSource.createQuery("From Appreciation a inner join fetch a.institution i where i =:pi")
						.setParameter("pi", utilisateur.getInstitution()).list();
				for (Deliberation df : listeDeliberationF) {
					Appreciation ap = getAp(listeAp, df.getMoyenne());
					df.setAp(ap.getLibelle());
					df.setInstitution(utilisateur.getInstitution());
					dataSource.save(df);
				}

			}
		} else {
			if (semestre == null || semestre.getId() == null) {
				FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique", "Semestre obligatoire");
				return;
			}
			if (classe == null || classe.getIdclasse() == null) {
				FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique", "Classe obligatoire");
				return;
			}
			ParamInscription p = (ParamInscription) dataSource.createQuery(
					"From ParamInscription p inner join fetch p.classe c inner join fetch p.annee pa inner join fetch p.institution i "
							+ " where c =:pc and pa =:pa and i =:pi ")
					.setParameter("pc", classe).setParameter("pa", annee)
					.setParameter("pi", utilisateur.getInstitution()).uniqueResult();
			if (p != null) {
				List<Inscription> liste = dataSource.createQuery(
						"From Inscription i inner join fetch i.eleve inner join fetch i.paramins p inner join fetch p.institution i where p =:pp and i =:pi")
						.setParameter("pp", p).setParameter("pi", utilisateur.getInstitution()).list();
				listeEleves = new ArrayList<Eleve>();

				for (Inscription in : liste) {
					listeEleves.add(in.getEleve());
				}
			}
			List<Deliberation> deliexiste = dataSource
					.createQuery("From DeliberationMS d inner join fetch d.classe c inner join fetch d.annee an"
							+ " inner join fetch d.semestre ev inner join fetch d.institution i where c =:pc and an =:pan and ev =:pev and i =:pi")
					.setParameter("pc", classe).setParameter("pan", annee).setParameter("pev", semestre)
					.setParameter("pi", utilisateur.getInstitution()).list();
			if (deliexiste.size() > 0) {
				FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique",
						"Classe " + classe.getLibelle() + " déja délibérée pour  " + semestre.getLibelle());
				return;
				// voirdeliberer();
			}

			if (semestre.getCode().equalsIgnoreCase("2")) {
				listeDeliberationS1 = new ArrayList<DeliberationMS>();
				listeDeliberationS1 = dataSource
						.createQuery("From DeliberationMS d inner join fetch d.classe c inner join fetch d.annee an"
								+ " inner join fetch d.semestre ev inner join fetch d.eleve inner join fetch d.appreciation"
								+ " inner join fetch d.institution i"
								+ " inner join fetch d.appreciationgen inner join fetch d.matiere where c =:pc and an =:pan and ev.code =:pev and i =:pi")
						.setParameter("pc", classe).setParameter("pan", annee).setParameter("pev", "1")
						.setParameter("pi", utilisateur.getInstitution()).list();

			}

			listeMatClasse = dataSource
					.createQuery("from MatiereClasse mc inner join fetch mc.matiere inner join fetch mc.classe"
							+ "  inner join fetch mc.institution i where mc.classe =:pc and mc.annee_scol =:pan and i =:pi"
							+ " ")
					.setParameter("pc", classe).setParameter("pan", annee.getAnneeScolaire())
					.setParameter("pi", utilisateur.getInstitution()).list();

			List<Notes> listeNotes = dataSource
					.createQuery("From Notes n inner join fetch n.classe c inner join fetch n.annee an "
							+ " inner join fetch n.typeNote ev inner join fetch n.eleve inner join fetch n.semestre s"
							+ " inner join fetch n.institution i "
							+ " where c =:pc and an =:pan and ev.code <>:pev and s=:ps and i =:pi")
					.setParameter("pc", classe).setParameter("pan", annee).setParameter("pev", "COMP")
					.setParameter("ps", semestre).setParameter("pi", utilisateur.getInstitution()).list();

			List<Notes> listeNotesComp = dataSource
					.createQuery("From Notes n inner join fetch n.classe c inner join fetch n.annee an "
							+ " inner join fetch n.typeNote ev inner join fetch n.eleve inner join fetch n.semestre s"
							+ " inner join fetch n.institution i "
							+ " where c =:pc and an =:pan and ev.code =:pev and s=:ps and i =:pi")
					.setParameter("pc", classe).setParameter("pan", annee).setParameter("pev", "COMP")
					.setParameter("ps", semestre).setParameter("pi", utilisateur.getInstitution()).list();

			listeDeliberationMSFF = new ArrayList<DeliberationMS>();
			listeDeliberationMS = new ArrayList<DeliberationMS>();
			listeDeliberationMSF = new ArrayList<DeliberationMS>();
			listeDeliberationMSF1 = new ArrayList<DeliberationMS>();
			listeDeliberationMSFF2 = new ArrayList<DeliberationMS>();
			listeDeliberationMSFFS = new ArrayList<DeliberationMS>();
			listeDeliberationMSVIEW = new ArrayList<DeliberationMS>();
			// Délibartion de la classe
			Double cumulnote;
			Double cumulcoef = 0d;
			Double moyen;
			Double noteComp = 0d;
			String moyens;
			int coef = getTotolCoef();
			Double total = 0d;
			// cumulcoef = CumulCeof();
			for (Eleve ev : listeEleves) {
				total = 0d;
				listeDeliberationMS = new ArrayList<DeliberationMS>();
				for (MatiereClasse mc : listeMatClasse) {
					cumulnote = cumulNoteMS(ev, listeNotes, mc.getMatiere());
					noteComp = getNoteComposition(ev, listeNotesComp, mc.getMatiere());
					if (notecomp)
						moyen = (cumulnote + noteComp) / 2;
					else
						moyen = cumulnote;
					moyens = "" + moyen;
					DeliberationMS d = new DeliberationMS();
					d.setAnnee(annee);
					d.setMoyD(cumulnote);
					d.setMoyC(noteComp);
					d.setCoef(mc.getCoef());
					d.setCumul(moyen * mc.getCoef());
					d.setCumuls(moyens);
					moyens = "" + d.getCumul();
					d.setMoyx(moyens);
					if (notecomp) {
						moyens = "" + noteComp;
						d.setMoyenneC(moyens);
					} else {
						d.setMoyenneC("-");
					}
					moyens = "" + cumulnote;
					d.setMoyenneD(moyens);
					d.setMatiere(mc.getMatiere());
					d.setClasse(classe);
					d.setEleve(ev);
					d.setSemestre(semestre);
					d.setUse("Y");
					total = total + d.getCumul();
					listeDeliberationMS.add(d);
				}
				
				List<Appreciations> listeAp = new ArrayList<Appreciations>();
				listeAp = dataSource.createQuery("From Appreciations a inner join fetch a.institution i where i =:pi ")
						.setParameter("pi", utilisateur.getInstitution()).list();
				List<Retard> listeRetard = new ArrayList<Retard>();
				List<Absence> listeAbsence = new ArrayList<Absence>();
				listeRetard = dataSource.createQuery(
						"From Retard r inner join fetch r.eleve inner join fetch r.annee an inner join fetch r.institution i where an =:pan and i =:pi")
						.setParameter("pan", annee).setParameter("pi", utilisateur.getInstitution()).list();
				listeAbsence = dataSource.createQuery(
						"From Absence r inner join fetch r.eleve inner join fetch r.annee an inner join fetch r.institution i  where an =:pan and i =:pi")
				
						.setParameter("pan", annee).setParameter("pi", utilisateur.getInstitution()).list();
				
				for (DeliberationMS deli : listeDeliberationMS) {
					deli.setTotal(total);
					moyens = "" + total;
					deli.setTotals("" + moyens);
					deli.setTotalcoef(coef);
					deli.setMoyenne(total / coef);
					moyens = "" + deli.getMoyenne();
					// deli.setMoyennes("" + moyens.substring(0, 4));
					deli.setMoyennes(moyens);
					deli.setAppreciation(getApMS(listeAp, Double.parseDouble(deli.getCumuls())));
					deli.setAppreciationgen(getApMS(listeAp, deli.getMoyenne()));
					deli.setRetard(getRetard(deli.getEleve(), listeRetard));
					deli.setAbsence(getAbsence(deli.getEleve(), listeAbsence));
					deli.setApgen(deli.getAppreciationgen().getLibelle());
					deli.setUse("Y");
					if (semestre.getCode().equalsIgnoreCase("2")) {
						deli.setMoyenne1(getMoyenneSemestre1(deli));
						moyens = "" + deli.getMoyenne1();
						deli.setMoyenne1s(moyens);
						deli.setMoyenneAn((deli.getMoyenne1() + deli.getMoyenne()) / 2);
						moyens = "" + deli.getMoyenneAn();
						deli.setMoyenneAns(moyens);
						deli.setUsean("Y");

					}
					listeDeliberationMSF.add(deli);
				}
			}
			
			// rang = 1;
			listeDeliberationMSFF = new ArrayList<DeliberationMS>();
			listeDeliberationMSFFS = new ArrayList<DeliberationMS>();
			listeDeliberationMS = listeDeliberationMSF;

			boolean b = false;
			listeDeliberationMSF1 = new ArrayList<DeliberationMS>();

			listeDeliberationMSF1.add(listeDeliberationMSF.get(0));
			for (DeliberationMS d : listeDeliberationMSF) {
				b = false;
				b = donneesExiste(d.getEleve());

				if (b == false) {
					listeDeliberationMSF1.add(d);
				}
			}
			rang = 1;
			itter = listeDeliberationMSF1.size();
			itter1 = 0;
			System.out.println("Taille liste listeDeliberationMSF1 "+listeDeliberationMSF1.size());
			while (listeDeliberationMSF1.size() > 0) {
				DeliberationMS deli = gererSupMS();
				List<DeliberationMS> liste = new ArrayList<DeliberationMS>();
				deli.setUse("X");
				liste = getMoyenEqualMS(deli);
				if (liste.size() > 1) {
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
				deli.setRanggen(rangs);
				if (liste.size() > 0) {
					for (DeliberationMS d : liste) {
						d.setUse("X");
						d.setRanggen(rangs);
						listeDeliberationMSFF.add(d);
						listeDeliberationMSFFS.add(d);
						rang = rang + 1;
					}
				} else {
					listeDeliberationMSFF.add(deli);
					listeDeliberationMSFFS.add(deli);
					rang = rang + 1;

				}
				listeDeliberationMSFFSS = new ArrayList<DeliberationMS>();
				listeDeliberationMSFFSS = listeDeliberationMSF1;
				listeDeliberationMSF1 = new ArrayList<DeliberationMS>();
				//System.out.println("Taille liste listeDeliberationMSF1 BEFORE "+listeDeliberationMSF1.size()); 
				//System.out.println("Taille liste listeDeliberationMSFFSS"+listeDeliberationMSFFSS.size());
				for (DeliberationMS dddd : listeDeliberationMSFFSS) {
					if (dddd.getUse().equalsIgnoreCase("X")) {
						itter1++;
					} else {
						listeDeliberationMSF1.add(dddd);
					}
				}
				//System.out.println("Taille liste listeDeliberationMSF1 AFTER "+listeDeliberationMSF1.size());
			}
			
			for (DeliberationMS d : listeDeliberationMSFF) {
				for (DeliberationMS deli : listeDeliberationMS) {
					if (deli.getEleve().getIdeleve().equals(d.getEleve().getIdeleve())) {
						deli.setRanggen(d.getRanggen());

					}
				}
			}

			//System.out.println("Taille liste listeDeliberationMSFF FINAL "+listeDeliberationMSFF.size());
			if (semestre.getCode().equalsIgnoreCase("2")) {
				listeDeliberationMSFF = new ArrayList<DeliberationMS>();
				listeDeliberationMSFFS = new ArrayList<DeliberationMS>();
				b = false;
				listeDeliberationMSF1 = new ArrayList<DeliberationMS>();
				listeDeliberationMSF1.add(listeDeliberationMSF.get(0));
				for (DeliberationMS d : listeDeliberationMSF) {
					b = false;
					b = donneesExiste(d.getEleve());
					if (b == false) {
						listeDeliberationMSF1.add(d);
					}
				}
				rang = 1;
				itter = listeDeliberationMSF1.size();
				itter1 = 0;
				while (listeDeliberationMSF1.size() > 0) {
					DeliberationMS deli = gererSupMSAn();
					List<DeliberationMS> liste = new ArrayList<DeliberationMS>();
					deli.setUsean("X");
					liste = getMoyenEqualMSAn(deli);
					if (liste.size() > 1) {
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
					deli.setRanggan(rangs);
					if (liste.size() > 0) {
						for (DeliberationMS d : liste) {
							d.setUsean("X");
							d.setRanggan(rangs);
							listeDeliberationMSFF.add(d);
							listeDeliberationMSFFS.add(d);
							rang = rang + 1;
						}
					} else {
						rang = rang + 1;

					}
					listeDeliberationMSFFSS = new ArrayList<DeliberationMS>();
					listeDeliberationMSFFSS = listeDeliberationMSF1;
					listeDeliberationMSF1 = new ArrayList<DeliberationMS>();
					for (DeliberationMS dddd : listeDeliberationMSFFSS) {
						if (dddd.getUsean().equalsIgnoreCase("X")) {
							itter1++;
						} else {
							listeDeliberationMSF1.add(dddd);
						}
					}

				}
				for (DeliberationMS d : listeDeliberationMSF1) {
					for (DeliberationMS deli : listeDeliberationMS) {
						if (deli.getEleve().getIdeleve().equals(d.getEleve().getIdeleve())) {
							deli.setRanggan(d.getRanggan());

						}
					}
				}

				Decision decision = (Decision) dataSource.createQuery("From Decision d  where d.code =:pcode ")
						.setParameter("pcode", "MS").uniqueResult();
				for (DeliberationMS deli : listeDeliberationMS) {
					if (deli.getMoyenneAn() < decision.getMoy()) {
						deli.setDecision("Redouble");
					} else {
						deli.setDecision("Passe en classe supérieure");
					}
				}
			}

			
			
			
			for (DeliberationMS deli : listeDeliberationMS) {
				splitMoyen(deli);
				deli.setUserang("Y");
				deli.setInstitution(utilisateur.getInstitution());
			}

			for (MatiereClasse mc : listeMatClasse) {
				for (DeliberationMS deli : listeDeliberationMS) {
					if (deli.getMatiere().getIdmatiere().equals(mc.getMatiere().getIdmatiere())) {
						listeDeliberationRang.add(deli);
					}
				}
// Calcul rang par matiere
				itter = listeDeliberationRang.size();
				itter1 = 0;
				rang = 1;
				while (listeDeliberationRang.size() > 0) {
					DeliberationMS deli = gererSupMatiere();
					List<DeliberationMS> liste = new ArrayList<DeliberationMS>();
					deli.setUserang("X");
					liste = getMoyenEqualMSRang(deli);
					rangs = "" + rang;
//					if (liste.size() > 1) {
//						
//							rangs = rang + "er ex æquo";
//						
//					} else {
//						if (rang == 1) {
//							rangs = rang + "er";
//						} else {
//							rangs = rang + "eme";
//						}
//					}
					deli.setRangm(rangs);
					if (liste.size() > 0) {
						for (DeliberationMS d : liste) {
							d.setUserang("X");
							d.setRangm(rangs);
							listeDeliberationRang1.add(d);
							listeDeliberationRang2.add(d);
							rang = rang + 1;
						}
					} else {
						rang = rang + 1;
						listeDeliberationRang1.add(deli);
						listeDeliberationRang2.add(deli);

					}
					listeDeliberationRang1 = new ArrayList<DeliberationMS>();
					listeDeliberationRang1 = listeDeliberationRang;
					listeDeliberationRang = new ArrayList<DeliberationMS>();
					for (DeliberationMS dddd : listeDeliberationRang1) {
						if (dddd.getUserang().equalsIgnoreCase("X")) {
							itter1++;
						} else {
							listeDeliberationRang.add(dddd);
						}
					}
				}

				for (DeliberationMS deli1 : listeDeliberationRang2) {
					for (DeliberationMS deli : listeDeliberationMS) {
						if (deli.getEleve().getIdeleve().equals(deli1.getEleve().getIdeleve())
								&& deli.getMatiere().getIdmatiere().equals(deli1.getMatiere().getIdmatiere())) {
							deli.setRangm(deli1.getRangm());
						}
					}
				}
				listeDeliberationRang2 = new ArrayList<DeliberationMS>();
				listeDeliberationRang = new ArrayList<DeliberationMS>();
				listeDeliberationRang1 = new ArrayList<DeliberationMS>();

			}

			
//			System.out.println("listeDeliberationMSFF "+listeDeliberationMSFF.size());
//			System.out.println("listeDeliberationMSFF "+listeDeliberationMSFF.size());
//			System.out.println("listeDeliberationMS "+listeDeliberationMS.size());
//			
			for (DeliberationMS deli : listeDeliberationMS) {

//				System.out.println("ELEVE " + deli.getEleve().getNom() + "MATIERE " + deli.getMatiere().getLibelle()
//						+ "MOYENNE " + deli.getCumuls() + "APP1 " + deli.getAppreciation().getLibelle() + "APP2"
//						+ deli.getAppreciationgen().getLibelle());
				dataSource.save(deli);
			}

		}
	}

	public boolean eleveExiste(Eleve eleve) {
		boolean res = false;
		for (Deliberation d : listeDeliberationF) {
			if (d.getEleve().getIdeleve().equals(eleve.getIdeleve())) {
				res = true;
			}
		}

		return res;
	}

	public void splitMoyenE(Deliberation deli) {
		String[] arrSplit3 = deli.getMoy().split("\\.");
		for (int i = 0; i < arrSplit3.length; i++) {
			if (i == 1) {
				if (arrSplit3[i].equalsIgnoreCase("00") || arrSplit3[i].equalsIgnoreCase("0")) {
					deli.setMoy(arrSplit3[0]);
				} else {
					if (arrSplit3[i].length() >= 2) {
						deli.setMoy(arrSplit3[0] + "." + arrSplit3[i].substring(0, 2));
					} else {
						deli.setMoy(arrSplit3[0] + "." + arrSplit3[i].substring(0, 1));
					}
				}
			}
		}

		String[] arrSplit1 = cnote.split("\\.");
		for (int i = 0; i < arrSplit1.length; i++) {
			if (i == 1) {
				if (arrSplit1[i].equalsIgnoreCase("00") || arrSplit1[i].equalsIgnoreCase("0")) {
					cnote = arrSplit1[0];
				} else {
					if (arrSplit1[i].length() >= 2) {
						cnote = arrSplit1[0] + "." + arrSplit1[i].substring(0, 2);
					} else {
						cnote = arrSplit1[0] + "." + arrSplit1[i].substring(0, 1);
					}
				}
			}
		}

	}

	public void splitMoyenAn(DeliberationFinal deli) {
		String[] arrSplit3 = deli.getMoyans().split("\\.");
		for (int i = 0; i < arrSplit3.length; i++) {
			if (i == 1) {
				if (arrSplit3[i].equalsIgnoreCase("00") || arrSplit3[i].equalsIgnoreCase("0")) {
					deli.setMoyans(arrSplit3[0]);
				} else {
					if (arrSplit3[i].length() >= 2) {
						deli.setMoyans(arrSplit3[0] + "." + arrSplit3[i].substring(0, 2));
					} else {
						deli.setMoyans(arrSplit3[0] + "." + arrSplit3[i].substring(0, 1));
					}
				}
			}
		}
	}

	public void splitMoyen(DeliberationMS deli) {

		String[] arrSplit = deli.getMoyenneC().split("\\.");
		for (int i = 0; i < arrSplit.length; i++) {

			if (i == 1) {
				if (arrSplit[i].equalsIgnoreCase("00") || arrSplit[i].equalsIgnoreCase("0"))
					deli.setMoyenneC(arrSplit[0]);
			}

		}
		String[] arrSplit1 = deli.getMoyenneD().split("\\.");
		for (int i = 0; i < arrSplit1.length; i++) {

			if (i == 1) {
				if (arrSplit1[i].equalsIgnoreCase("00") || arrSplit1[i].equalsIgnoreCase("0")) {

					deli.setMoyenneD(arrSplit1[0]);
				}
			}
		}

		if (semestre.getCode().equalsIgnoreCase("2")) {
			String[] arrSplit3 = deli.getMoyenneAns().split("\\.");
			for (int i = 0; i < arrSplit3.length; i++) {

				if (i == 1) {
					if (arrSplit3[i].equalsIgnoreCase("00") || arrSplit3[i].equalsIgnoreCase("0")) {
						deli.setMoyenneAns(arrSplit[0]);
					} else {
						if (arrSplit3[i].length() >= 2) {
							deli.setMoyenneAns(arrSplit3[0] + "." + arrSplit3[i].substring(0, 2));
						} else {
							deli.setMoyenneAns(arrSplit3[0] + "." + arrSplit3[i].substring(0, 1));
						}
					}
				}

			}
			String[] arrSplit5 = deli.getMoyenne1s().split("\\.");
			for (int i = 0; i < arrSplit5.length; i++) {

				if (i == 1) {
					if (arrSplit5[i].equalsIgnoreCase("00") || arrSplit5[i].equalsIgnoreCase("0")) {
						deli.setMoyenne1s(arrSplit5[0]);
					} else {
						if (arrSplit5[i].length() >= 2) {
							deli.setMoyenne1s(arrSplit5[0] + "." + arrSplit5[i].substring(0, 2));
						} else {
							deli.setMoyenne1s(arrSplit5[0] + "." + arrSplit5[i].substring(0, 1));
						}
					}

				}
			}

		}
		String[] arrSplit4 = deli.getCumuls().split("\\.");
		for (int i = 0; i < arrSplit4.length; i++) {

			if (i == 1) {
				if (arrSplit4[i].equalsIgnoreCase("00") || arrSplit4[i].equalsIgnoreCase("0"))
					deli.setCumuls(arrSplit4[0]);
			}
		}

		String[] arrSplit6 = deli.getTotals().split("\\.");
		for (int i = 0; i < arrSplit6.length; i++) {

			if (i == 1) {
				if (arrSplit6[i].equalsIgnoreCase("00") || arrSplit6[i].equalsIgnoreCase("0"))
					deli.setTotals(arrSplit6[0]);
			}
		}

		String[] arrSplit7 = deli.getMoyennes().split("\\.");

		for (int i = 0; i < arrSplit7.length; i++) {
			if (i == 1) {
				if (arrSplit7[i].equalsIgnoreCase("00") || arrSplit7[i].equalsIgnoreCase("0")) {
					deli.setMoyennes(arrSplit7[0]);
				} else {
					if (arrSplit7[i].length() >= 2) {
						deli.setMoyennes(arrSplit7[0] + "." + arrSplit7[i].substring(0, 2));

					} else {
						deli.setMoyennes(arrSplit7[0] + "." + arrSplit7[i].substring(0, 1));
					}
				}
			}
		}
		// System.out.println("MOYENNE SSSF " + deli.getMoyennes());
		String[] arrSplit8 = deli.getMoyx().split("\\.");

		for (int i = 0; i < arrSplit8.length; i++) {
			if (i == 1) {
				if (arrSplit8[i].equalsIgnoreCase("00") || arrSplit8[i].equalsIgnoreCase("0")) {
					deli.setMoyx(arrSplit8[0]);
				} else {
					if (arrSplit8[i].length() >= 2) {
						deli.setMoyx(arrSplit8[0] + "." + arrSplit8[i].substring(0, 2));
					} else {
						deli.setMoyx(arrSplit8[0] + "." + arrSplit8[i].substring(0, 1));
					}
				}
			}

		}

	}

	public Double getMoyenneSemestre1(DeliberationMS deli) {
		Double moyenne = 0d;
		for (DeliberationMS d : listeDeliberationS1) {
			if (deli.getEleve().getIdeleve().equals(d.getEleve().getIdeleve())) {
				moyenne = d.getMoyenne();
				break;
			}
		}
		return moyenne;
	}

	public void detailsDeliberation(DeliberationMS deli) {
		listeDeliberationMSVIEW = new ArrayList<DeliberationMS>();
		eleve = deli.getEleve();
		for (DeliberationMS d : listeDeliberationMS) {
			if (d.getEleve().getIdeleve().equals(deli.getEleve().getIdeleve())) {
				listeDeliberationMSVIEW.add(d);
				moyenneGen = d.getMoyennes();
				rangGen = d.getRanggen();
			}
		}
	}

	public boolean donneesExiste(Eleve eleve) {
		boolean existe = false;
		for (DeliberationMS d : listeDeliberationMSF1) {

			if (d.getEleve().getIdeleve().equals(eleve.getIdeleve())) {

				existe = true;

				break;

			}
		}
		return existe;
	}

	public int getTotolCoef() {
		int coef = 0;
		for (MatiereClasse mc : listeMatClasse) {
			coef = coef + mc.getCoef();
		}
		return coef;
	}

	public Appreciation getAp(List<Appreciation> listeAp, Double moy) {
		Appreciation p = new Appreciation();

		for (Appreciation ap : listeAp) {
			if (ap.getInf() <= moy && ap.getSup() >= moy) {
				p = ap;

				break;
			}
		}

		return p;
	}

	public Appreciations getApMS(List<Appreciations> listeAp, Double moy) {
		Appreciations p = new Appreciations();

		for (Appreciations ap : listeAp) {
			if (ap.getInf() <= moy && ap.getSup() >= moy) {
				p = ap;
				break;
			}
		}
        System.out.println("Moyenne "+moy + "Appréciation "+p.getLibelle());
		return p;
	}

	public int getRetard(Eleve eleve, List<Retard> listeRetard) {
		int heures = 0;
		for (Retard ab : listeRetard) {
			if (ab.getEleve().getIdeleve().equals(eleve.getIdeleve())) {
				heures = heures + ab.getHeures();
			}
		}
		return heures;
	}

	public int getAbsence(Eleve eleve, List<Absence> listeAbsence) {
		int heures = 0;
		for (Absence ab : listeAbsence) {
			if (ab.getEleve().getIdeleve().equals(eleve.getIdeleve())) {
				heures = heures + ab.getHeure();
			}
		}
		return heures;
	}

	public Deliberation gererSup() {
		Deliberation d = listeDeliberation.get(0);
		int j = 0;
		for (int i = 1; i < listeDeliberation.size(); i++) {
			if (d.getMoyenne() < listeDeliberation.get(i).getMoyenne()) {
				d = listeDeliberation.get(i);

				j = i;
			}
		}
		d.setUse("X");
		listeDeliberation.get(j).setUse("X");
		;
		// itter = itter - 1;

		return d;

	}

//	public boolean eleveexisteDeja() {
//		boolean res = false;
//		for(Deliberation d:listeDeliberationS) {
//			if(d.get)
//		}
//		
//		return res;
//	}

	public DeliberationMS gererSupMS() {
		DeliberationMS d = listeDeliberationMSF1.get(0);
		int j = 0;
		for (int i = 1; i < listeDeliberationMSF1.size(); i++) {
			if (d.getMoyenne() < listeDeliberationMSF1.get(i).getMoyenne()) {
				d = listeDeliberationMSF1.get(i);
				j = i;
			}
		}

//		DeliberationMS d = listeDeliberationMSF1.get(0);
//		for (int i = 1; i < listeDeliberationMSF1.size(); i++) {
//			if (d.getMoyenne() < listeDeliberationMSF1.get(i).getMoyenne()) {
//			}
//				d = listeDeliberationMSF1.get(i);
//		}
//		listeDeliberationMSF1.remove(d);
		d.setUse("X");
		listeDeliberationMSF1.get(j).setUse("X");
		return d;
	}

	public DeliberationMS gererSupMSAn() {

		DeliberationMS d = listeDeliberationMSF1.get(0);
		int j = 0;
		for (int i = 1; i < listeDeliberationMSF1.size(); i++) {

			if (d.getMoyenneAn() < listeDeliberationMSF1.get(i).getMoyenneAn()) {
				d = listeDeliberationMSF1.get(i);
				j = i;
			}
		}
		d.setUsean("X");
		listeDeliberationMSF1.get(j).setUse("X");
		;
		return d;
	}

	public DeliberationMS gererSupMatiere() {

		DeliberationMS d = listeDeliberationRang.get(0);
		int j = 0;
		for (int i = 1; i < listeDeliberationRang.size(); i++) {

			if (Double.parseDouble(d.getCumuls()) < Double.parseDouble(listeDeliberationRang.get(i).getCumuls())) {
				d = listeDeliberationRang.get(i);
				j = i;
			}
		}
		d.setUserang("X");
		listeDeliberationRang.get(j).setUse("X");
		;
		return d;
	}

	public DeliberationFinal gererSupAn() {

		DeliberationFinal d = listeDeliF.get(0);
		int j = 0;
		for (int i = 1; i < listeDeliF.size(); i++) {
			if (d.getMoyan() < listeDeliF.get(i).getMoyan()) {
				d = listeDeliF.get(i);

				j = i;
			}
		}

//		System.out.println("SUP " + d.getEleve().getPrenom() + " " + d.getMoyan());
//
//		for (DeliberationFinal deli : listeDeliF) {
//			System.out.println("USE DANS SUP " + deli.getUse());
//		}
		// itter = itter - 1;
		d.setUse("X");
		listeDeliF.get(j).setUse("X");
		return d;

//		
//		DeliberationFinal d = listeDeliF.get(0);
//		for (int i = 1; i < listeDeliF.size(); i++) {
//			if (d.getMoyan() < listeDeliF.get(i).getMoyan()) {
//				d = listeDeliF.get(i);
//			}
//		}
//		listeDeliF.remove(d);
//
//		return d;

	}

	public List<Deliberation> getMoyenEqual(Deliberation d) {
		List<Deliberation> liste = new ArrayList<Deliberation>();

		for (int i = 0; i < listeDeliberation.size(); i++) {

			if (d.getMoyenne().equals(listeDeliberation.get(i).getMoyenne())) {
				listeDeliberation.get(i).setUse("X");
				liste.add(listeDeliberation.get(i));

			}
		}

		return liste;
	}

	public List<DeliberationMS> getMoyenEqualMS(DeliberationMS d) {
		List<DeliberationMS> liste = new ArrayList<DeliberationMS>();

		for (int i = 0; i < listeDeliberationMSF1.size(); i++) {

			if (d.getMoyenne().equals(listeDeliberationMSF1.get(i).getMoyenne())) {
				listeDeliberationMSF1.get(i).setUse("X");
				liste.add(listeDeliberationMSF1.get(i));
			}
		}

		return liste;
	}

	public List<DeliberationMS> getMoyenEqualMSAn(DeliberationMS d) {
		List<DeliberationMS> liste = new ArrayList<DeliberationMS>();

		for (int i = 0; i < listeDeliberationMSF1.size(); i++) {

			if (d.getMoyenneAn().equals(listeDeliberationMSF1.get(i).getMoyenneAn())) {
				listeDeliberationMSF1.get(i).setUse("X");
				liste.add(listeDeliberationMSF1.get(i));
			}
		}

		return liste;
	}

	public List<DeliberationMS> getMoyenEqualMSRang(DeliberationMS d) {
		List<DeliberationMS> liste = new ArrayList<DeliberationMS>();
		for (int i = 0; i < listeDeliberationRang.size(); i++) {
			if (Double.parseDouble(d.getCumuls()) == Double.parseDouble(listeDeliberationRang.get(i).getCumuls())) {
				liste.add(listeDeliberationRang.get(i));
				listeDeliberationRang.get(i).setUse("X");
			}
		}

		return liste;
	}

	public List<DeliberationFinal> getMoyenEqualFinal(DeliberationFinal d) {
		List<DeliberationFinal> liste = new ArrayList<DeliberationFinal>();

		for (int i = 0; i < listeDeliF.size(); i++) {

			if (d.getMoyan().equals(listeDeliF.get(i).getMoyan())) {

				liste.add(listeDeliF.get(i));
			}
		}

		return liste;

	}

	public Double cumulNote(Eleve e, List<Note> listeNote) {
		Double cumul = 0.0;
		for (Note n : listeNote) {
			if (n.getEleve().getIdeleve().equals(e.getIdeleve())) {
				cumul = cumul + n.getNote();
			}
		}
		return cumul;
	}

	public Double cumulNoteMS(Eleve e, List<Notes> listeNotes, Matiere matiere) {
		Double cumul = 0.0;
		int i = 0;
		for (Notes n : listeNotes) {
			if (n.getEleve().getIdeleve().equals(e.getIdeleve())
					&& n.getMatiere().getIdmatiere().equals(matiere.getIdmatiere())) {
				cumul = cumul + n.getNote();
				i++;
			}
		}
		cumul = cumul / i;
		return cumul;
	}

	public Double getNoteComposition(Eleve e, List<Notes> listeNotes, Matiere matiere) {
		Double note = 0d;
		notecomp = false;
		for (Notes n : listeNotes) {
			if (n.getEleve().getIdeleve().equals(e.getIdeleve())
					&& n.getMatiere().getIdmatiere().equals(matiere.getIdmatiere())) {
				note = (double) n.getNote();

				notecomp = true;
				break;

			}

		}
		return note;

	}

	@SuppressWarnings("unchecked")
	public Double CumulCeof() {
		Double cumul = 0.0;
		ccoef = 0;
		List<MatiereClasse> listeMc = dataSource
				.createQuery("From MatiereClasse mc inner join fetch mc.classe c inner join fetch mc.matiere m "
						+ " inner join  fetch mc.eval ev inner join fetch mc.institution i  where c =:pc and mc.annee_scol =:pan and ev =:pev and i =:pi")
				.setParameter("pc", classe).setParameter("pan", annee.getAnneeScolaire())
				.setParameter("pev", evaluation).setParameter("pi", utilisateur.getInstitution()).list();
		for (MatiereClasse n : listeMc) {

			cumul = cumul + n.getCoef();
			ccoef = ccoef + n.getCoef();

		}
		// ccoef = cumul;
		return (cumul * 10) / 100;
	}

	public void delibererAvecEleveExclu() {
		listeElevesNonNote = new ArrayList<Eleve>();
		this.setClasse(new Classe());
		this.setClasse(new Classe());
		this.setEvaluation(new Evaluation());
	}

	@SuppressWarnings("unchecked")
	public void versDetails(Deliberation deli) {
		chaine = "Eleve " + deli.getEleve().getNom() + " " + deli.getEleve().getPrenom() + "\n" + "Classe "
				+ deli.getClasse().getLibelle() + "\n" + "Evaluation " + deli.getEvaluation().getLibelle();
		listeNote = new ArrayList<Note>();
		listeNote = dataSource
				.createQuery("From Note n inner join fetch n.cl c inner join fetch n.eleve e "
						+ " inner join fetch n.evaluation ev inner join fetch n.annee an inner join fetch n.matiere"
						+ " inner join fetch n.institution i "
						+ " where c =:pc and an =:pan and ev =:pev and e =:pe and i =:pi")
				.setParameter("pc", classe).setParameter("pan", annee).setParameter("pev", evaluation)
				.setParameter("pe", deli.getEleve()).setParameter("pi", utilisateur.getInstitution()).list();

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

	public List<Note> getListeNote() {
		return listeNote;
	}

	public void setListeNote(List<Note> listeNote) {
		this.listeNote = listeNote;
	}

	public String getChaine() {
		return chaine;
	}

	public void setChaine(String chaine) {
		this.chaine = chaine;
	}

	public String getShowModal() {
		return showModal;
	}

	public void setShowModal(String showModal) {
		this.showModal = showModal;
	}

	public FileUploadService getFilePrintService() {
		if (filePrintService == null) {
			filePrintService = (FileUploadService) Component.getInstance(FileUploadService.class);

		}
		return filePrintService;
	}

	public void setFilePrintService(FileUploadService filePrintService) {
		this.filePrintService = filePrintService;
	}

	public boolean isChoix() {
		return choix;
	}

	public void setChoix(boolean choix) {
		this.choix = choix;
	}

	public List<DeliberationFinal> getListeDeliF() {
		return listeDeliF;
	}

	public void setListeDeliF(List<DeliberationFinal> listeDeliF) {
		this.listeDeliF = listeDeliF;
	}

	public List<DeliberationFinal> getListeDeliAn() {
		return listeDeliAn;
	}

	public void setListeDeliAn(List<DeliberationFinal> listeDeliAn) {
		this.listeDeliAn = listeDeliAn;
	}

	public String getDecision() {
		return decision;
	}

	public void setDecision(String decision) {
		this.decision = decision;
	}

	public String getTypenote() {
		return typenote;
	}

	public void setTypenote(String typenote) {
		this.typenote = typenote;
	}

	public List<Semestres> getListeSemestre() {
		return listeSemestre;
	}

	public void setListeSemestre(List<Semestres> listeSemestre) {
		this.listeSemestre = listeSemestre;
	}

	public Semestres getSemestre() {
		return semestre;
	}

	public void setSemestre(Semestres semestre) {
		this.semestre = semestre;
	}

	public Semestres getSs() {
		return ss;
	}

	public void setSs(Semestres ss) {
		this.ss = ss;
	}

	public boolean isNotecomp() {
		return notecomp;
	}

	public void setNotecomp(boolean notecomp) {
		this.notecomp = notecomp;
	}

	public List<DeliberationMS> getListeDeliberationMSFF() {
		return listeDeliberationMSFF;
	}

	public void setListeDeliberationMSFF(List<DeliberationMS> listeDeliberationMSFF) {
		this.listeDeliberationMSFF = listeDeliberationMSFF;
	}

	public List<Deliberation> getListeDeli() {
		return listeDeli;
	}

	public void setListeDeli(List<Deliberation> listeDeli) {
		this.listeDeli = listeDeli;
	}

	public List<DeliberationMS> getListeDeliberationMS() {
		return listeDeliberationMS;
	}

	public void setListeDeliberationMS(List<DeliberationMS> listeDeliberationMS) {
		this.listeDeliberationMS = listeDeliberationMS;
	}

	public List<DeliberationMS> getListeDeliberationMSF() {
		return listeDeliberationMSF;
	}

	public void setListeDeliberationMSF(List<DeliberationMS> listeDeliberationMSF) {
		this.listeDeliberationMSF = listeDeliberationMSF;
	}

	public List<DeliberationMS> getListeDeliberationMSF1() {
		return listeDeliberationMSF1;
	}

	public void setListeDeliberationMSF1(List<DeliberationMS> listeDeliberationMSF1) {
		this.listeDeliberationMSF1 = listeDeliberationMSF1;
	}

	public List<DeliberationMS> getListeDeliberationMSVIEW() {
		return listeDeliberationMSVIEW;
	}

	public void setListeDeliberationMSVIEW(List<DeliberationMS> listeDeliberationMSVIEW) {
		this.listeDeliberationMSVIEW = listeDeliberationMSVIEW;
	}

	public Eleve getEleve() {
		return eleve;
	}

	public void setEleve(Eleve eleve) {
		this.eleve = eleve;
	}

	public String getMoyenneGen() {
		return moyenneGen;
	}

	public void setMoyenneGen(String moyenneGen) {
		this.moyenneGen = moyenneGen;
	}

	public String getRangGen() {
		return rangGen;
	}

	public void setRangGen(String rangGen) {
		this.rangGen = rangGen;
	}

	public List<DeliberationMS> getListeDeliberationS1() {
		return listeDeliberationS1;
	}

	public void setListeDeliberationS1(List<DeliberationMS> listeDeliberationS1) {
		this.listeDeliberationS1 = listeDeliberationS1;
	}

}
