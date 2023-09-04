/**
 * 
 */
package com.ecole.service;

import java.io.InputStream;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.jboss.seam.Component;
import org.hibernate.Session;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;

import com.chaka.projet.entity.Utilisateur;
import com.ecole.entity.AnneeScolaire;
import com.ecole.entity.Appreciation;
import com.ecole.entity.Classe;
import com.ecole.entity.Decision;
import com.ecole.entity.Deliberation;
import com.ecole.entity.DeliberationFinal;
import com.ecole.entity.Eleve;
import com.ecole.entity.Evaluation;
import com.ecole.entity.Inscription;
import com.ecole.entity.Institution;
import com.ecole.entity.Matiere;
import com.ecole.entity.MatiereClasse;
import com.ecole.entity.Niveau;
import com.ecole.entity.Note;
import com.ecole.entity.ParamInscription;
import com.rhospi.commons.ChakaUtils.ExportOption;
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
	private Evaluation evaluation = new Evaluation();
	private String rangs;
	private int rang;
	private List<Deliberation> listeDeliberation = new ArrayList<Deliberation>();
	private List<Deliberation> listeDeliberationF = new ArrayList<Deliberation>();
	private List<Note> listeNote = new ArrayList<Note>();
	private String chaine;
	private String showModal = "";
	private boolean choix;

	private List<DeliberationFinal> listeDeliF = new ArrayList<DeliberationFinal>();
	private List<DeliberationFinal> listeDeliAn = new ArrayList<DeliberationFinal>();
	private String decision;
	List<Deliberation> listeDeli = new ArrayList<Deliberation>();
	@In
	private AnneeScolaire annee;
	FileUploadService filePrintService;
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
	public String versDeliberationAn() {
		listeNiveau = new ArrayList<Niveau>();
		listeNiveau = dataSource.createQuery("From Niveau ").list();
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
		listeNiveau = dataSource.createQuery("From Niveau ").list();
		listeEval = new ArrayList<Evaluation>();
		listeEval = dataSource.createQuery("From Evaluation ").list();
		listeElevesNonNote = new ArrayList<Eleve>();
		this.setClasse(new Classe());
		this.setEvaluation(new Evaluation());
		listeDeliberation = new ArrayList<Deliberation>();
		listeDeliberationF = new ArrayList<Deliberation>();

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
				"From DeliberationFinal d inner join fetch d.classe c inner join fetch d.annee inner join fetch d.eleve "
						+ " where c =:pc and d.annee =:pan")
				.setParameter("pc", classe).setParameter("pan", annee).list();

		if (listeDeliAn.size() == 0) {
			ParamInscription p = (ParamInscription) dataSource
					.createQuery("From ParamInscription p inner join fetch p.classe c inner join fetch p.annee pa "
							+ " where c =:pc and pa =:pa ")
					.setParameter("pc", classe).setParameter("pa", annee).uniqueResult();

			if (p != null) {
				List<Inscription> liste = dataSource.createQuery(
						"From Inscription i inner join fetch i.eleve inner join fetch i.paramins p where p =:pp")
						.setParameter("pp", p).list();
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
				df.setMoyan(Double.parseDouble(moyennes));
				df.setAnnee(annee);
				df.setClasse(classe);
				df.setMoyans(moyennes.substring(0, 4));
				for (Evaluation ev : listeEval) {
					alimenterDeliberation(df, ev, eleve);
				}
				listeDeliF.add(df);
			}

			listeDeliAn = new ArrayList<DeliberationFinal>();
			rang = 1;
			while (listeDeliF.size() > 0) {
				DeliberationFinal deli = gererSupAn();
				List<DeliberationFinal> liste = new ArrayList<DeliberationFinal>();
				liste = getMoyenEqualFinal(deli);
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
				deli.setRangan(rangs);
				listeDeliAn.add(deli);
				if (liste.size() > 0) {
					for (DeliberationFinal d : liste) {
						d.setRangan(rangs);
						listeDeliAn.add(d);
						listeDeliF.remove(d);
						rang = rang + 1;
					}
					rang = rang + 1;
				} else {
					rang = rang + 1;
				}

			}
			List<Appreciation> listeAp = new ArrayList<Appreciation>();
			listeAp = dataSource.createQuery("From Appreciation ").list();
			Decision decision = (Decision) dataSource.createQuery("From Decision ").uniqueResult();
			for (DeliberationFinal df : listeDeliAn) {
				Appreciation ap = getAp(listeAp, df.getMoyan());
				df.setApa(ap.getLibelle());
				if (df.getMoyan() < decision.getMoy()) {
					df.setDecision("Redouble");
				} else {
					df.setDecision("Passe en classe supérieure");
				}
				dataSource.save(df);
			}

		}

	}

	public void changerDecision(DeliberationFinal deli) {
		deli.setDecision(decision);
		dataSource.update(deli);
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
		listeClasse = dataSource.createQuery(" From Classe c inner join fetch c.niveau n where n=:pn")
				.setParameter("pn", niveau).list();
	}

	public void cocherTout() {

		for (Deliberation d : listeDeliberationF) {
			d.setChoix(choix);
		}
	}

	@SuppressWarnings("unchecked")
	public void voirdeliberer() {
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

		listeDeliberationF = new ArrayList<Deliberation>();
		listeDeliberationF = dataSource
				.createQuery("From Deliberation d inner join fetch d.classe c inner join fetch d.evaluation ev "
						+ " inner join fetch d.annee an where c =:pc and an =:pan and ev =:pev")
				.setParameter("pc", classe).setParameter("pan", annee).setParameter("pev", evaluation).list();

		if (listeDeliberationF.size() == 0) {
			FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique", "Aucune délibération trouvée");
			return;
		} else {
			this.setChoix(true);
			for (Deliberation d : listeDeliberationF) {
				d.setChoix(true);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void imprimerTout() {
		Institution in = (Institution) dataSource.createQuery("From Institution").uniqueResult();
		ParamInscription p = (ParamInscription) dataSource
				.createQuery("From ParamInscription p inner join fetch p.classe c inner join fetch p.annee pa "
						+ " where c =:pc and pa =:pa ")
				.setParameter("pc", classe).setParameter("pa", annee).uniqueResult();

		List<Inscription> liste = new ArrayList<Inscription>();
		if (p != null) {
			liste = dataSource
					.createQuery(
							"From Inscription i inner join fetch i.eleve inner join fetch i.paramins p where p =:pp")
					.setParameter("pp", p).list();

		}
		Map<String, Object> param = new HashMap<String, Object>();

		Double total = 0.0;
		listeDeli = new ArrayList<Deliberation>();
		for (Deliberation d : listeDeliberationF) {
			if (d.isChoix()) {
				total = 0.0;

				listeNote = new ArrayList<Note>();
				listeNote = dataSource.createQuery("From Note n inner join fetch n.cl c inner join fetch n.eleve e "
						+ " inner join fetch n.evaluation ev inner join fetch n.annee an inner join fetch n.matiere "
						+ " where c =:pc and an =:pan and ev =:pev and e =:pe").setParameter("pc", classe)
						.setParameter("pan", annee).setParameter("pev", evaluation).setParameter("pe", d.getEleve())
						.list();
				for (Note n : listeNote) {
					total = total + n.getNote();
				}
				for (Note n : listeNote) {
					n.setEcole(in.getNom());
					n.setEff("" + liste.size());
					n.setSlogan(in.getSologan());
					n.setTel(in.getTelephone());
					n.setTotal(total);
					n.setMoy(d.getMoy());
					n.setAp(d.getAp());
					n.setRang(d.getRang());

					ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
					ServletContext sc = (ServletContext) ec.getContext();
					InputStream is = sc.getResourceAsStream("/css2/logogstock.jpg");
					n.setLogo(is);
				}
				d.setListeNote(listeNote);
				listeDeli.add(d);

			}
		}

		getFilePrintService().imprimer("ecole", "allbulletin", param, listeDeli, utilisateur, ExportOption.PDF);
		this.setShowModal("javascript:Richfaces.showModalPanel('modalPdf')");
	}

	@SuppressWarnings("unchecked")
	public void imprimerToutAn() {
		listeEval = new ArrayList<Evaluation>();
		listeEval = dataSource.createQuery("From Evaluation ").list();
		Institution in = (Institution) dataSource.createQuery("From Institution").uniqueResult();
		listeNote = new ArrayList<Note>();
		listeNote = new ArrayList<Note>();

		ParamInscription p = (ParamInscription) dataSource
				.createQuery("From ParamInscription p inner join fetch p.classe c inner join fetch p.annee pa "
						+ " where c =:pc and pa =:pa ")
				.setParameter("pc", classe).setParameter("pa", annee).uniqueResult();

		List<Inscription> liste = new ArrayList<Inscription>();
		if (p != null) {
			liste = dataSource
					.createQuery(
							"From Inscription i inner join fetch i.eleve inner join fetch i.paramins p where p =:pp")
					.setParameter("pp", p).list();

		}
		listeNote = dataSource.createQuery("From Note n inner join fetch n.cl c inner join fetch n.eleve e "
				+ " inner join fetch n.evaluation ev inner join fetch n.annee an inner join fetch n.matiere "
				+ " where c =:pc and an =:pan ").setParameter("pc", classe).setParameter("pan", annee).list();
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
			InputStream is = sc.getResourceAsStream("/css2/logogstock.jpg");
			deli.getListeNote().get(0).setLogo(is);
			System.out.println("dec "+d.getDecision());
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
		Institution in = (Institution) dataSource.createQuery("From Institution").uniqueResult();
		listeNote = new ArrayList<Note>();
		listeNote = new ArrayList<Note>();
		listeNote = dataSource
				.createQuery("From Note n inner join fetch n.cl c inner join fetch n.eleve e "
						+ " inner join fetch n.evaluation ev inner join fetch n.annee an inner join fetch n.matiere "
						+ " where c =:pc and an =:pan  and e =:pe")
				.setParameter("pc", classe).setParameter("pan", annee).setParameter("pe", deli.getEleve()).list();
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
		ParamInscription p = (ParamInscription) dataSource
				.createQuery("From ParamInscription p inner join fetch p.classe c inner join fetch p.annee pa "
						+ " where c =:pc and pa =:pa ")
				.setParameter("pc", classe).setParameter("pa", annee).uniqueResult();

		List<Inscription> liste = new ArrayList<Inscription>();
		if (p != null) {
			liste = dataSource
					.createQuery(
							"From Inscription i inner join fetch i.eleve inner join fetch i.paramins p where p =:pp")
					.setParameter("pp", p).list();

		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ecole", in.getNom());
		param.put("slogan", in.getSologan());
		param.put("tel", in.getTelephone());
		param.put("moy", deli.getMoyan());
		param.put("rang", deli.getRangan());
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
		getFilePrintService().imprimer("ecole", "bulletinan", param, listeNoteD, utilisateur, ExportOption.PDF);

		this.setShowModal("javascript:Richfaces.showModalPanel('modalPdf')");
	}

	@SuppressWarnings("unchecked")
	public void imprimerBulletin(Deliberation deli) {
		Institution in = (Institution) dataSource.createQuery("From Institution").uniqueResult();
		listeNote = new ArrayList<Note>();
		listeNote = dataSource
				.createQuery("From Note n inner join fetch n.cl c inner join fetch n.eleve e "
						+ " inner join fetch n.evaluation ev inner join fetch n.annee an inner join fetch n.matiere "
						+ " where c =:pc and an =:pan and ev =:pev and e =:pe")
				.setParameter("pc", classe).setParameter("pan", annee).setParameter("pev", evaluation)
				.setParameter("pe", deli.getEleve()).list();

		Double total = 0.0;

		for (Note n : listeNote) {
			total = total + n.getNote();
		}

		ParamInscription p = (ParamInscription) dataSource
				.createQuery("From ParamInscription p inner join fetch p.classe c inner join fetch p.annee pa "
						+ " where c =:pc and pa =:pa ")
				.setParameter("pc", classe).setParameter("pa", annee).uniqueResult();

		List<Inscription> liste = new ArrayList<Inscription>();
		if (p != null) {
			liste = dataSource
					.createQuery(
							"From Inscription i inner join fetch i.eleve inner join fetch i.paramins p where p =:pp")
					.setParameter("pp", p).list();

		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ecole", in.getNom());
		param.put("slogan", in.getSologan());
		param.put("tel", in.getTelephone());
		param.put("moy", deli.getMoy());
		param.put("rang", deli.getRang());
		param.put("total", total);
		param.put("app", deli.getAp());
		param.put("eff", liste.size());
		getFilePrintService().imprimer("ecole", "bulletin", param, listeNote, utilisateur, ExportOption.PDF);
		this.setShowModal("javascript:Richfaces.showModalPanel('modalPdf')");
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
				d.setCumul("" + cumulnote + "/" + cumulcoef);
				d.setClasse(classe);
				d.setEleve(ev);
				d.setMoy(moyens.substring(0, 4));
				d.setEvaluation(evaluation);
				d.setMoyenne(Double.valueOf(d.getMoy()));
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

						listeDeliberationF.add(d);
						listeDeliberation.remove(d);
						rang = rang + 1;
					}
					rang = rang + 1;
				} else {
					rang = rang + 1;
				}

			}
			List<Appreciation> listeAp = new ArrayList<Appreciation>();
			listeAp = dataSource.createQuery("From Appreciation ").list();
			for (Deliberation df : listeDeliberationF) {
				Appreciation ap = getAp(listeAp, df.getMoyenne());
				df.setAp(ap.getLibelle());
				dataSource.save(df);
			}
		}
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

	public DeliberationFinal gererSupAn() {
		DeliberationFinal d = listeDeliF.get(0);
		for (int i = 1; i < listeDeliF.size(); i++) {
			if (d.getMoyan() < listeDeliF.get(i).getMoyan()) {
				d = listeDeliF.get(i);
			}
		}
		listeDeliF.remove(d);

		return d;
	}

	public List<Deliberation> getMoyenEqual(Deliberation d) {
		List<Deliberation> liste = new ArrayList<Deliberation>();

		for (int i = 0; i < listeDeliberation.size(); i++) {

			if (d.getMoyenne().equals(listeDeliberation.get(i).getMoyenne())) {

				liste.add(listeDeliberation.get(i));
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

	@SuppressWarnings("unchecked")
	public Double CumulCeof() {
		Double cumul = 0.0;

		List<MatiereClasse> listeMc = dataSource
				.createQuery("From MatiereClasse mc inner join fetch mc.classe c inner join fetch mc.matiere m "
						+ " inner join  fetch mc.eval ev   where c =:pc and mc.annee_scol =:pan and ev =:pev")
				.setParameter("pc", classe).setParameter("pan", annee.getAnneeScolaire())
				.setParameter("pev", evaluation).list();
		for (MatiereClasse n : listeMc) {
			System.out.println("Matiere " + n.getMatiere().getLibelle() + "coef " + n.getMatiere().getCoef());
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

	@SuppressWarnings("unchecked")
	public void versDetails(Deliberation deli) {
		chaine = "Eleve " + deli.getEleve().getNom() + " " + deli.getEleve().getPrenom() + "\n" + "Classe "
				+ deli.getClasse().getLibelle() + "\n" + "Evaluation " + deli.getEvaluation().getLibelle();
		listeNote = new ArrayList<Note>();
		listeNote = dataSource
				.createQuery("From Note n inner join fetch n.cl c inner join fetch n.eleve e "
						+ " inner join fetch n.evaluation ev inner join fetch n.annee an inner join fetch n.matiere "
						+ " where c =:pc and an =:pan and ev =:pev and e =:pe")
				.setParameter("pc", classe).setParameter("pan", annee).setParameter("pev", evaluation)
				.setParameter("pe", deli.getEleve()).list();

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
}
