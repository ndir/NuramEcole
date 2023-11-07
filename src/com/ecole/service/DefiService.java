/**
 * 
 */
package com.ecole.service;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.hibernate.Session;
import org.jboss.seam.Component;
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
import com.ecole.entity.Defi;
import com.ecole.entity.DefiMatiere;
import com.ecole.entity.Deliberation;
import com.ecole.entity.DeliberationDefi;
import com.ecole.entity.Eleve;
import com.ecole.entity.Evaluation;
import com.ecole.entity.Inscription;
import com.ecole.entity.Institution;
import com.ecole.entity.Matiere;
import com.ecole.entity.MatiereClasse;
import com.ecole.entity.Note;
import com.ecole.entity.NoteDefi;
import com.ecole.entity.ParamInscription;
import com.rhospi.commons.FileUploadService;
import com.rhospi.commons.ChakaUtils.ExportOption;

/**
 * @author A626257
 *
 */
@Scope(ScopeType.SESSION)
@Name("defiService")
public class DefiService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@In
	private Session dataSource;

	@In
	private AnneeScolaire annee;

	@In(required = false)
	@Out(required = false)
	private Utilisateur utilisateur;

	private Defi defi = new Defi();

	private List<Defi> listeDefi = new ArrayList<Defi>();

	private List<DefiMatiere> listeDefiMat = new ArrayList<DefiMatiere>();

	private List<Matiere> listeMat = new ArrayList<Matiere>();

	private NoteDefi notedefi = new NoteDefi();

	private List<Classe> listeClasse = new ArrayList<Classe>();

	private boolean choix;

	private int ccoef;

	private List<Eleve> listeEleves = new ArrayList<Eleve>();

	private List<Eleve> listeElevesNonNote = new ArrayList<Eleve>();

	private String cnote;
	private String ccf;

	private String showModal = "";

	FileUploadService filePrintService;

	private List<DeliberationDefi> listeDeliberationF = new ArrayList<DeliberationDefi>();
	private List<DeliberationDefi> listeDeliberation = new ArrayList<DeliberationDefi>();
	private List<DeliberationDefi> listeDeliberationS = new ArrayList<DeliberationDefi>();
	private List<DeliberationDefi> listeDeliberationSS = new ArrayList<DeliberationDefi>();

	public String versDefi() {
		chargerLiteDefi();
		return "/pages/nuramecole/defi.xhtml";
	}

	@SuppressWarnings("unchecked")
	public void ajouterMatiereDefi() {
		if (defi == null) {
			FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique", "Veuillez choisir un Défi");
			return;
		}
		for (Matiere mat : listeMat) {
			if (mat.getCoef() > 0) {
				DefiMatiere defmat = new DefiMatiere();
				defmat.setAnnee(annee);
				defmat.setCoef(mat.getCoef());
				defmat.setDefi(defi);
				defmat.setInstitution(utilisateur.getInstitution());
				defmat.setMatiere(mat);
				dataSource.save(defmat);
			}
		}
		listeMat = new ArrayList<Matiere>();
		listeMat = dataSource.createQuery(
				"From Matiere m inner join fetch m.institution i inner join fetch m.niveau n where i=:pi and n.code=:pn")
				.setParameter("pi", utilisateur.getInstitution()).setParameter("pn", "ELE").list();
		chargerListeDefiMat();
	}

	@SuppressWarnings("unchecked")
	public String versNoteDefi() {
		listeEleves = new ArrayList<Eleve>();
		chargerLiteDefi();
		listeClasse = new ArrayList<Classe>();
		listeClasse = dataSource
				.createQuery(" From Classe c  inner join fetch c.institution i" + "  where i =:pi and niv =:pniv ")
				.setParameter("pi", utilisateur.getInstitution()).setParameter("pniv", "CM2").list();
		listeMat = new ArrayList<Matiere>();
//		listeMat = dataSource.createQuery(
//				"From Matiere m inner join fetch m.institution i inner join fetch m.niveau n where i=:pi and n.code=:pn")
//				.setParameter("pi", utilisateur.getInstitution()).setParameter("pn", "ELE").list();

		listeDefiMat = new ArrayList<DefiMatiere>();
		listeDefiMat = dataSource.createQuery("From DefiMatiere d inner join fetch d.institution i "
				+ "inner join fetch d.defi dd inner join fetch d.annee an inner join fetch d.matiere m where i=:pi and an =:pan ")
				.setParameter("pi", utilisateur.getInstitution()).setParameter("pan", annee).list();
		for (DefiMatiere d : listeDefiMat) {
			listeMat.add(d.getMatiere());
		}
		return "/pages/nuramecole/notedefi.xhtml";
	}

	@SuppressWarnings("unchecked")
	public void chargerListeEleve() {

		ParamInscription p = (ParamInscription) dataSource.createQuery(
				"From ParamInscription p inner join fetch p.classe c inner join fetch p.annee pa inner join fetch p.institution i "
						+ " where c =:pc and pa =:pa and i =:pi ")
				.setParameter("pc", notedefi.getCl()).setParameter("pa", annee)
				.setParameter("pi", utilisateur.getInstitution()).uniqueResult();

		if (p != null) {

			this.setChoix(true);
			List<Inscription> liste = dataSource.createQuery(
					"From Inscription i inner join fetch i.eleve inner join fetch i.paramins p inner join fetch i.institution "
							+ "s where p =:pp and s =:ps")
					.setParameter("pp", p).setParameter("ps", utilisateur.getInstitution()).list();

			listeEleves = new ArrayList<Eleve>();

			listeDefiMat = new ArrayList<DefiMatiere>();
			listeDefiMat = dataSource.createQuery("From DefiMatiere d inner join fetch d.institution i "
					+ "inner join fetch d.defi dd inner join fetch d.annee an inner join fetch d.matiere m where i=:pi and dd =:pdd and an =:pan and m =:pm")
					.setParameter("pi", utilisateur.getInstitution()).setParameter("pdd", notedefi.getDefi())
					.setParameter("pan", annee).setParameter("pm", notedefi.getMatiere()).list();

			for (Inscription in : liste) {
				in.getEleve().setChoix(true);
				noteExiste(in.getEleve());
				in.getEleve().setCoef(listeDefiMat.get(0).getCoef());
				listeEleves.add(in.getEleve());
			}

		}

	}

	@SuppressWarnings("unchecked")
	public String versDelibererDefi() {
		chargerLiteDefi();
		listeClasse = new ArrayList<Classe>();
		listeClasse = dataSource
				.createQuery(" From Classe c  inner join fetch c.institution i" + "  where i =:pi and niv =:pniv ")
				.setParameter("pi", utilisateur.getInstitution()).setParameter("pniv", "CM2").list();
		listeMat = new ArrayList<Matiere>();

		listeDefiMat = new ArrayList<DefiMatiere>();
		listeDefiMat = dataSource.createQuery("From DefiMatiere d inner join fetch d.institution i "
				+ "inner join fetch d.defi dd inner join fetch d.annee an inner join fetch d.matiere m where i=:pi and an =:pan ")
				.setParameter("pi", utilisateur.getInstitution()).setParameter("pan", annee).list();
		return "/pages/nuramecole/deliberationdefi.xhtml";
	}

	@SuppressWarnings("unchecked")
	public Double CumulCeof() {
		Double cumul = 0.0;
		ccoef = 0;

		for (DefiMatiere n : listeDefiMat) {

			cumul = cumul + n.getCoef();
			ccoef = ccoef + n.getCoef();

		}

		// ccoef = cumul;
		return (cumul * 10) / 100;
	}

	@SuppressWarnings("unchecked")
	public void deliberer() {
		if (notedefi.getCl() == null) {
			FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique", "Classe Obligatoire");
			return;
		}
		if (notedefi.getDefi() == null) {
			FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique", "DEFI Obligatoire");
			return;
		}

		ParamInscription p = (ParamInscription) dataSource.createQuery(
				"From ParamInscription p inner join fetch p.classe c inner join fetch p.annee pa inner join fetch p.institution i "
						+ " where c =:pc and pa =:pa and i =:pi ")
				.setParameter("pc", notedefi.getCl()).setParameter("pa", annee)
				.setParameter("pi", utilisateur.getInstitution()).uniqueResult();

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

		List<DeliberationDefi> deliexiste = dataSource
				.createQuery("From DeliberationDefi d inner join fetch d.classe c inner join fetch d.annee an"
						+ " inner join fetch d.defi ev inner join fetch d.institution i where c =:pc and an =:pan and ev =:pev and i =:pi")
				.setParameter("pc", notedefi.getCl()).setParameter("pan", annee).setParameter("pev", notedefi.getDefi())
				.setParameter("pi", utilisateur.getInstitution()).list();
		if (deliexiste.size() > 0) {
			listeDeliberationF = new ArrayList<DeliberationDefi>();
			listeDeliberationF = dataSource
					.createQuery("From DeliberationDefi d inner join fetch d.defi df inner join fetch d.institution i"
							+ " inner join fetch d.annee an inner join fetch d.classe c where df =:pdf and i =:pi and an =:pan and c =:pc")
					.setParameter("pdf", notedefi.getDefi()).setParameter("pi", utilisateur.getInstitution())
					.setParameter("pan", annee).setParameter("pc", notedefi.getCl()).list();
		} else {
			listeDefiMat = new ArrayList<DefiMatiere>();
			listeDefiMat = dataSource.createQuery("From DefiMatiere d inner join fetch d.institution i "
					+ "inner join fetch d.defi dd inner join fetch d.annee an inner join fetch d.matiere m where i=:pi and dd =:pdd and an =:pan ")
					.setParameter("pi", utilisateur.getInstitution()).setParameter("pdd", notedefi.getDefi())
					.setParameter("pan", annee).list();

			List<NoteDefi> listeNote = dataSource
					.createQuery("From NoteDefi n inner join fetch n.cl c inner join fetch n.annee an "
							+ " inner join fetch n.defi ev inner join fetch n.eleve"
							+ " inner join fetch n.institution i "
							+ " where c =:pc and an =:pan and ev =:pev and i =:pi")
					.setParameter("pc", notedefi.getCl()).setParameter("pan", annee)
					.setParameter("pev", notedefi.getDefi()).setParameter("pi", utilisateur.getInstitution()).list();

			boolean existe = false;
			listeElevesNonNote = new ArrayList<Eleve>();

			for (Eleve ev : listeEleves) {
				existe = false;
				for (DefiMatiere m : listeDefiMat) {
					for (NoteDefi n : listeNote) {
						if (n.getMatiere().getIdmatiere().equals(m.getMatiere().getIdmatiere())
								&& ev.getIdeleve().equals(n.getEleve().getIdeleve())) {
							existe = true;
							break;
						}
					}
					if (existe == false) { // eleve pas note pour la matiere
						ev.setIdMat(m.getMatiere().getIdmatiere());
						listeElevesNonNote.add(ev);
					}
					existe = false;
				}
			}

			if (listeElevesNonNote.size() == 0) {
				System.out.println("Taille liste " + listeEleves.size());
				listeDeliberationF = new ArrayList<DeliberationDefi>();
				listeDeliberation = new ArrayList<DeliberationDefi>();
				// Délibartion de la classe
				Double cumulnote;
				Double cumulcoef;
				Double moyen;
				String moyens;
				cumulcoef = CumulCeof();
				System.out.println("CUMUL COEF " + cumulcoef);
				for (Eleve ev : listeEleves) {
					cumulnote = cumulNote(ev, listeNote);
					moyen = cumulnote / cumulcoef;
					moyens = "" + moyen;
					DeliberationDefi d = new DeliberationDefi();
					d.setAnnee(annee);
					cnote = "" + cumulnote;
					ccf = "" + cumulcoef;
					d.setMoy(moyens);
					splitMoyenE(d);
					d.setCumul("" + cnote);
					d.setTotalcoef("" + ccoef);
					d.setClasse(notedefi.getCl());
					d.setEleve(ev);
					d.setUse("Y");
					d.setDefi(notedefi.getDefi());
					d.setMoyenne(Double.valueOf(d.getMoy()));
					d.setUser(utilisateur);
					listeDeliberation.add(d);
				}

				int rang = 1;
				String rangs = "";
				while (listeDeliberation.size() > 0) {
					DeliberationDefi deli = gererSup();

					List<DeliberationDefi> liste = new ArrayList<DeliberationDefi>();
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

					if (liste.size() > 0) {

						for (DeliberationDefi d : liste) {
							d.setRang(rangs);
							listeDeliberationF.add(d);
							listeDeliberationS.add(d);

							rang = rang + 1;
						}
					} else {
						listeDeliberationF.add(deli);
						listeDeliberationS.add(deli);
						// itter1++;
						rang = rang + 1;
					}

					listeDeliberationSS = new ArrayList<DeliberationDefi>();
					listeDeliberationSS = listeDeliberation;

					listeDeliberation = new ArrayList<DeliberationDefi>();

					for (DeliberationDefi dddd : listeDeliberationSS) {
						if (dddd.getUse().equalsIgnoreCase("X")) {
						} else {
							listeDeliberation.add(dddd);
						}

					}

				}
				for (DeliberationDefi df : listeDeliberationF) {
					df.setInstitution(utilisateur.getInstitution());
					if (df.getMoyenne() >= 5d) {
						if (df.getEleve().getSexe().equalsIgnoreCase("F")) {
							df.setAdmis("ADMISE");
						} else {
							df.setAdmis("ADMIS");
						}
					} else {
						if (df.getEleve().getSexe().equalsIgnoreCase("F")) {
							df.setAdmis("NON ADMISE");
						} else {
							df.setAdmis("NON ADMIS");
						}
					}
					System.out.println("eleve " + df.getEleve().getPrenom() + "MOYEN " + df.getMoy() + "TOTOT COEF "
							+ df.getTotalcoef() + "CUMUL " + df.getCumul());
					dataSource.save(df);
				}
			}
		}
	}

	public List<DeliberationDefi> getMoyenEqual(DeliberationDefi d) {
		List<DeliberationDefi> liste = new ArrayList<DeliberationDefi>();

		for (int i = 0; i < listeDeliberation.size(); i++) {

			if (d.getMoyenne().equals(listeDeliberation.get(i).getMoyenne())) {
				listeDeliberation.get(i).setUse("X");
				liste.add(listeDeliberation.get(i));

			}
		}

		return liste;
	}

	public DeliberationDefi gererSup() {
		DeliberationDefi d = listeDeliberation.get(0);
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

	public void splitMoyenE(DeliberationDefi deli) {
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

	public Double cumulNote(Eleve e, List<NoteDefi> listeNote) {
		Double cumul = 0.0;
		for (NoteDefi n : listeNote) {
			if (n.getEleve().getIdeleve().equals(e.getIdeleve())) {
				cumul = cumul + n.getNote();
			}
		}
		System.out.println("ELEVE " + e.getPrenom() + "NOTE " + cumul);
		return cumul;
	}

	public void noteExiste(Eleve e) {

		NoteDefi n = (NoteDefi) dataSource.createQuery(
				"From NoteDefi n inner join fetch n.cl c inner join fetch n.matiere m inner join fetch n.institution i "
						+ " inner join fetch n.eleve e inner join fetch n.annee an  "
						+ " where c=:pc and m=:pm and an=:pan   and i =:pi and e =:pe")
				.setParameter("pc", notedefi.getCl()).setParameter("pm", notedefi.getMatiere())
				.setParameter("pan", annee).setParameter("pi", utilisateur.getInstitution()).setParameter("pe", e)
				.uniqueResult();

		if (n != null) {
			e.setExiste(true);
			e.setIdNote(n.getIdNote());
			e.setNote(n.getNote());
		}

	}

	@SuppressWarnings("unchecked")
	public void imprimerBulletin(DeliberationDefi deli) {
		Institution in = (Institution) dataSource.get(Institution.class, utilisateur.getInstitution().getId());
		List<NoteDefi> listeNote = new ArrayList<NoteDefi>();

		listeNote = dataSource
				.createQuery("From NoteDefi n inner join fetch n.cl c inner join fetch n.eleve e "
						+ " inner join fetch n.defi ev inner join fetch n.annee an inner join fetch n.matiere "
						+ " inner join fetch n.institution i"
						+ " where c =:pc and an =:pan and ev =:pev and e =:pe and i =:pi")
				.setParameter("pc", deli.getClasse()).setParameter("pan", annee).setParameter("pev", deli.getDefi())
				.setParameter("pe", deli.getEleve()).setParameter("pi", utilisateur.getInstitution()).list();

		Double total = 0.0;

//		for (Note n : listeNote) {
//			total = total + n.getNote();
//		}

		String logo = "";
		logo = "/css2/" + in.getImage();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ecole", in.getNom());
		param.put("slogan", in.getSologan());
		param.put("tel", in.getTelephone());
		param.put("moy", deli.getMoy());
		param.put("rang", deli.getAdmis());
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ServletContext sc = (ServletContext) ec.getContext();
		InputStream is = sc.getResourceAsStream(logo);
		System.out.println("LOGO " + logo);
		param.put(logo, is);
		String t = "" + deli.getCumul() + " / " + deli.getTotalcoef();
		param.put("total", t);

		listeNote.get(0).setLogo(is);
		getFilePrintService().imprimer("ecole", "bulletindefi", param, listeNote, utilisateur, ExportOption.PDF);
		this.setShowModal("javascript:Richfaces.showModalPanel('modalPdf')");
	}

	@SuppressWarnings("unchecked")
	public void imprimerTout() {
		Institution in = (Institution) dataSource.get(Institution.class, utilisateur.getInstitution().getId());
		String logo = "";
		logo = "/css2/" + in.getImage();

		Map<String, Object> param = new HashMap<String, Object>();

		Double total = 0.0;
		List<NoteDefi> listeNote = new ArrayList<NoteDefi>();
		List<DeliberationDefi> listeDeli = new ArrayList<DeliberationDefi>();
		for (DeliberationDefi d : listeDeliberationF) {
			// if (d.isChoix()) {
			total = 0.0;
			listeNote = new ArrayList<NoteDefi>();
			listeNote = dataSource.createQuery("From NoteDefi n inner join fetch n.cl c inner join fetch n.eleve e "
					+ " inner join fetch n.defi ev inner join fetch n.annee an inner join fetch n.matiere  "
					+ " inner join fetch n.institution i"
					+ " where c =:pc and an =:pan and ev =:pev and e =:pe and i =:pi").setParameter("pc", d.getClasse())
					.setParameter("pan", annee).setParameter("pev", d.getDefi()).setParameter("pe", d.getEleve())
					.setParameter("pi", utilisateur.getInstitution()).list();
//				for (Note n : listeNote) {
//					total = total + n.getNote();
//				}
			for (NoteDefi n : listeNote) {
				n.setEcole(in.getNom());
				n.setSlogan(in.getSologan());
				n.setTel(in.getTelephone());
				n.setTotal1(""+d.getCumul() + " / " +d.getTotalcoef());
				n.setMoy(d.getMoy());
				n.setMoy1(d.getAdmis());
				ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
				ServletContext sc = (ServletContext) ec.getContext();
				InputStream is = sc.getResourceAsStream(logo);
				n.setLogo(is);
			}
			d.setListeNote(listeNote);
			listeDeli.add(d);

			// }
		}

		getFilePrintService().imprimer("ecole", "allbulletindefi", param, listeDeli, utilisateur, ExportOption.PDF);
		this.setShowModal("javascript:Richfaces.showModalPanel('modalPdf')");
	}

	public void delibererAvecEleveExclu() {
		listeElevesNonNote = new ArrayList<Eleve>();

	}

	public String getMatiere(Eleve ev) {
		Matiere m = (Matiere) dataSource.get(Matiere.class, ev.getIdMat());
		return m.getLibelle();
	}

	public void ajouterNote() {
		for (Eleve eleve : listeEleves) {

			if (eleve.getNote() > eleve.getCoef()) {
				FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique",
						"Note supérieure au coéfficient pour l'éléve " + eleve.getNom() + " " + eleve.getPrenom());
				return;
			}
		}
		for (Eleve eleve : listeEleves) {
			if (eleve.isChoix() & !eleve.isExiste()) {
				NoteDefi n = new NoteDefi();
				n.setAnnee(annee);
				n.setCl(notedefi.getCl());
				n.setEleve(eleve);
				n.setMatiere(notedefi.getMatiere());
				n.setInstitution(utilisateur.getInstitution());
				n.setNote(eleve.getNote());
				n.setDefi(notedefi.getDefi());
				n.setCoef(listeDefiMat.get(0).getCoef());
				dataSource.save(n);
			}
			if (eleve.isChoix() & eleve.isExiste()) {
				NoteDefi n = (NoteDefi) dataSource.get(NoteDefi.class, eleve.getIdNote());
				if (n.getIdNote() != null) {
					n.setNote(eleve.getNote());
					dataSource.update(n);
				}
			}
		}
		FacesMessages.instance().addToControlFromResourceBundle("infoGenerique", "Notes sauvegardées avec succès");
		// annulerAjoutNote();
		this.setNotedefi(new NoteDefi());
		this.setListeEleves(new ArrayList<Eleve>());
	}

	public void supprimerMatClasse(DefiMatiere defimat) {
		dataSource.delete(defimat);
		chargerListeDefiMat();
	}

	public void voirMatiereDefi() {
		chargerListeDefiMat();
	}

	@SuppressWarnings("unchecked")
	public String versDefiMatiere() {
		chargerListeDefiMat();
		chargerLiteDefi();
		listeMat = new ArrayList<Matiere>();
		listeMat = dataSource.createQuery(
				"From Matiere m inner join fetch m.institution i inner join fetch m.niveau n where i=:pi and n.code=:pn")
				.setParameter("pi", utilisateur.getInstitution()).setParameter("pn", "ELE").list();
		return "/pages/nuramecole/defimatiere.xhtml";
	}

	@SuppressWarnings("unchecked")
	public void chargerListeDefiMat() {
		listeDefiMat = new ArrayList<DefiMatiere>();
		listeDefiMat = dataSource
				.createQuery("From DefiMatiere d inner join fetch d.matiere inner join fetch d.defi de "
						+ "inner join fetch d.annee an inner join fetch d.institution i where an =:pan and i=:pi ")
				.setParameter("pan", annee).setParameter("pi", utilisateur.getInstitution()).list();
	}

	@SuppressWarnings("unchecked")
	public void chargerLiteDefi() {
		listeDefi = new ArrayList<Defi>();
		listeDefi = dataSource.createQuery(
				"From Defi d inner join fetch d.annee an inner join fetch d.institution i where an =:pan and i =:pi")
				.setParameter("pan", annee).setParameter("pi", utilisateur.getInstitution()).list();
	}

	public void ajouterDefi() {
		if (defi.getLibelle().isEmpty()) {
			FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique",
					"Veuillez renseigner le libelle ");
			return;
		}

		if (defi.getMois().isEmpty()) {
			FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique", "Veuillez renseigner le mois ");
			return;
		}

		if (defi.getId() == null) {
			defi.setAnnee(annee);
			defi.setInstitution(utilisateur.getInstitution());
			dataSource.save(defi);
		} else {
			dataSource.update(defi);
		}

		this.setDefi(new Defi());
		chargerLiteDefi();
	}

	public void versModifieDefi(Defi defi) {
		this.setDefi(defi);
	}

	public Defi getDefi() {
		return defi;
	}

	public void setDefi(Defi defi) {
		this.defi = defi;
	}

	public List<Defi> getListeDefi() {
		return listeDefi;
	}

	public void setListeDefi(List<Defi> listeDefi) {
		this.listeDefi = listeDefi;
	}

	public List<DefiMatiere> getListeDefiMat() {
		return listeDefiMat;
	}

	public void setListeDefiMat(List<DefiMatiere> listeDefiMat) {
		this.listeDefiMat = listeDefiMat;
	}

	public List<Matiere> getListeMat() {
		return listeMat;
	}

	public void setListeMat(List<Matiere> listeMat) {
		this.listeMat = listeMat;
	}

	public NoteDefi getNotedefi() {
		return notedefi;
	}

	public void setNotedefi(NoteDefi notedefi) {
		this.notedefi = notedefi;
	}

	public List<Classe> getListeClasse() {
		return listeClasse;
	}

	public void setListeClasse(List<Classe> listeClasse) {
		this.listeClasse = listeClasse;
	}

	public boolean isChoix() {
		return choix;
	}

	public void setChoix(boolean choix) {
		this.choix = choix;
	}

	public List<Eleve> getListeEleves() {
		return listeEleves;
	}

	public void setListeEleves(List<Eleve> listeEleves) {
		this.listeEleves = listeEleves;
	}

	public AnneeScolaire getAnnee() {
		return annee;
	}

	public void setAnnee(AnneeScolaire annee) {
		this.annee = annee;
	}

	public List<Eleve> getListeElevesNonNote() {
		return listeElevesNonNote;
	}

	public void setListeElevesNonNote(List<Eleve> listeElevesNonNote) {
		this.listeElevesNonNote = listeElevesNonNote;
	}

	public int getCcoef() {
		return ccoef;
	}

	public void setCcoef(int ccoef) {
		this.ccoef = ccoef;
	}

	public String getCnote() {
		return cnote;
	}

	public void setCnote(String cnote) {
		this.cnote = cnote;
	}

	public String getCcf() {
		return ccf;
	}

	public void setCcf(String ccf) {
		this.ccf = ccf;
	}

	public List<DeliberationDefi> getListeDeliberationF() {
		return listeDeliberationF;
	}

	public void setListeDeliberationF(List<DeliberationDefi> listeDeliberationF) {
		this.listeDeliberationF = listeDeliberationF;
	}

	public List<DeliberationDefi> getListeDeliberation() {
		return listeDeliberation;
	}

	public void setListeDeliberation(List<DeliberationDefi> listeDeliberation) {
		this.listeDeliberation = listeDeliberation;
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

}
