package com.ecole.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.hibernate.Session;
import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import com.chaka.projet.entity.Utilisateur;
import com.ecole.entity.AnneeScolaire;
import com.ecole.entity.Classe;
import com.ecole.entity.Depense;
import com.ecole.entity.Eleve;
import com.ecole.entity.Etats;
import com.ecole.entity.Inscription;
import com.ecole.entity.Institution;
import com.ecole.entity.ParamInscription;
import com.ecole.entity.Recette;
import com.ecole.entity.TypeDepense;
import com.ecole.entity.TypeRecette;
import com.rhospi.commons.ChakaUtils;
import com.rhospi.commons.FileUploadService;

import asposewobfuscated.ut;

import com.rhospi.commons.ChakaUtils.ExportOption;

/**
 * @author A626257
 *
 */
@Scope(ScopeType.SESSION)
@Name("statService")
public class StatistiqueService implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@In
	private Session dataSource;

	private List<Classe> listeClasse = new ArrayList<Classe>();

	@In
	private AnneeScolaire annee;

	private List<Inscription> listeIns = new ArrayList<Inscription>();

	private int nbeleve;

	private int nbfille;

	private Institution ins = new Institution();

	private int nbgarcon;

	private Date dateDeb;

	private Date dateFin;

	private List<Depense> listeDepense = new ArrayList<Depense>();

	private List<Recette> listeRecette = new ArrayList<Recette>();

	private List<Recette> listeRecettes = new ArrayList<Recette>();

	private List<TypeRecette> listeTypeRecette = new ArrayList<TypeRecette>();

	private List<TypeDepense> listeTypeDepense = new ArrayList<TypeDepense>();

	private List<Recette> listeRecetteZoom = new ArrayList<Recette>();

	private List<Depense> listeDepenseZoom = new ArrayList<Depense>();

	private Double recette;

	private Double depense;

	private Double solde;

	private String mois;

	private String moisch;

	private int lemois;

	private int nbjour;

	FileUploadService filePrintService;
	private List<ParamInscription> listeParm = new ArrayList<ParamInscription>();

	private String showModal = "";

	private List<Utilisateur> listeUser = new ArrayList<Utilisateur>();

	private List<Eleve> listeEleves = new ArrayList<Eleve>();

	private List<Inscription> listeInscrption = new ArrayList<Inscription>();

	@In(required = false)
	@Out(required = false)
	private Utilisateur utilisateur;

	public String versEntreeSortie() {
		listeDepense = new ArrayList<Depense>();
		listeRecette = new ArrayList<Recette>();
		listeTypeDepense = new ArrayList<TypeDepense>();
		listeTypeRecette = new ArrayList<TypeRecette>();
		return "/pages/nuramecole/etat.xhtml";
	}

	public String versEtatsMensuel() {
		listeParm = new ArrayList<ParamInscription>();
		listeIns = new ArrayList<Inscription>();
		return "/pages/nuramecole/etatmensuel.xhtml";
	}

	public void imprimerEtatsMensuel() {
		Institution in = (Institution) dataSource.get(Institution.class, utilisateur.getInstitution().getId());

		for (ParamInscription p : listeParm) {
			p.setApayer(getMontantAPayer(p));
			p.setPayer(getMontantPayer(p));
			p.setRpayer(p.getApayer() - p.getPayer());
			p.setTaux(getTauxRecouvrement(p));
		}

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ecole", in.getNom());
		param.put("slogan", in.getSologan());
		param.put("tel", in.getTelephone());
		retreiveMonthByString(mois);
		param.put("etat", "ETAT MENSUEL MOIS : " + moisch);
		getFilePrintService().imprimer("ecole", "etat", param, listeParm, utilisateur, ExportOption.PDF);
		this.setShowModal("javascript:Richfaces.showModalPanel('modalPdf')");
	}

	public void retreiveMonthByString(String month) {
		if (month != null) {
			if (month.equals("10")) {
				lemois = 10;
				nbjour = 31;
				moisch = "OCTOBRE";
			}
			if (month.equals("11")) {
				lemois = 11;
				nbjour = 31;
				moisch = "NOVEMBRE";
			}
			if (month.equals("12")) {
				lemois = 12;
				nbjour = 31;
				moisch = "DECEMBRE";
			}
			if (month.equals("1")) {
				lemois = 1;
				nbjour = 31;
				moisch = "JANVIER";
			}
			if (month.equals("2")) {
				lemois = 02;
				moisch = "FEVRIER";
			}
			if (month.equals("3")) {
				lemois = 3;
				nbjour = 31;
				moisch = "MARS";
			}
			if (month.equals("4")) {
				lemois = 4;
				nbjour = 30;
				moisch = "AVRIL";
			}
			if (month.equals("5")) {
				lemois = 5;
				nbjour = 31;
				moisch = "MAI";
			}

			if (month.equals("6")) {
				lemois = 6;
				nbjour = 30;
				moisch = "JUIN";
			}
			if (month.equals("7")) {
				lemois = 7;
				nbjour = 31;
				moisch = "JUILLET";
			}
		}

	}

	public boolean bissextile(int y) {
		boolean b = false;
		if (y % 400 == 0) {
			b = true;
		} else if (y % 100 == 0) {
			b = false;
		} else if (y % 4 == 0) {
			b = true;
		} else {
			b = false;
		}
		if (b == true) {

		} else {

		}
		return b;
	}

	@SuppressWarnings("unchecked")
	public void etatsMensuel() {
		int month = 0;
		listeParm = new ArrayList<ParamInscription>();
		listeParm = dataSource.createQuery(
				"From ParamInscription p inner join fetch p.classe inner join fetch p.annee an inner join fetch p.institution i where an =:pan and i =:pi ")
				.setParameter("pan", annee).setParameter("pi", utilisateur.getInstitution()).list();
		listeIns = new ArrayList<Inscription>();
		listeIns = dataSource
				.createQuery("From Inscription i inner join fetch i.paramins p inner join fetch i.institution s "
						+ " inner join fetch p.annee an inner join fetch i.eleve where an =:pan and s=:ps ")
				.setParameter("pan", annee).setParameter("ps", utilisateur.getInstitution()).list();
		listeRecettes = new ArrayList<Recette>();
		listeRecette = new ArrayList<Recette>();
		retreiveMonthByString(mois);

		listeRecettes = dataSource.createQuery("From Recette d inner join fetch d.typeRecette ty inner join fetch "
				+ " d.inscription i  inner join fetch i.paramins p inner join fetch p.annee an inner join fetch i.eleve inner join fetch d.institution s where an =:pan and ty.code =:pcode and s=:ps"
				+ " and d.moisPaye =:pmois ").setParameter("pan", annee)
				.setParameter("ps", utilisateur.getInstitution()).setParameter("pcode", "MENS")
				.setParameter("pmois", lemois).list();

	}

	@SuppressWarnings("unchecked")
	public void zoomPaiement(ParamInscription param) {
		listeRecette = new ArrayList<Recette>();
		listeEleves = new ArrayList<Eleve>();

		listeInscrption = dataSource.createQuery(
				"From Inscription i inner join fetch i.paramins p inner join fetch i.eleve inner join fetch i.institution s  where p=:pp and i.paiemens =:pm and s =:ps")
				.setParameter("pp", param).setParameter("pm", true).setParameter("ps", utilisateur.getInstitution())
				.list();

		for (Recette recette : listeRecettes) {
			if (recette.getInscription().getParamins().getId().equals(param.getId())) {
				listeRecette.add(recette);
			}
		}
	}

	public Double montantApayer(Inscription ins) {
		Double mnt = 0d;
		for (Recette recette : listeRecettes) {
			if (recette.getInscription().getId().equals(ins.getId())) {
				mnt = mnt + recette.getMontantPaye();
			}
//			if(recette.getAvoirUtilise() > 0) {
//				mnt = mnt + recette.getAvoirUtilise();
//			}
		}
		return mnt;
	}

	public void imprimerDetailsEtatsMensuel() {
		for (Inscription eleve : listeInscrption) {
			eleve.setaPayer(montantApayer(eleve));
			eleve.setDoitPayer(montantApayer(eleve));
			eleve.setResteApayer(resteApayer(eleve));
		}
		Institution in = (Institution) dataSource.get(Institution.class, utilisateur.getInstitution().getId());
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ecole", in.getNom());
		param.put("slogan", in.getSologan());
		param.put("tel", in.getTelephone());
		retreiveMonthByString(mois);
		param.put("libelle", "ETAT MENSUEL MOIS : " + moisch);
		getFilePrintService().imprimer("ecole", "details_etat", param, listeInscrption, utilisateur, ExportOption.PDF);
		this.setShowModal("javascript:Richfaces.showModalPanel('modalPdf')");
	}

	public Double resteApayer(Inscription ins) {
		Double reste = 0d;
		reste = montantDoitpayer(ins) - montantApayer(ins);
		if (reste < 0) {
			reste = 0d;
		}
		return reste;
	}

	public Double montantDoitpayer(Inscription ins) {
		Double mnt = 0d;
		mnt = ins.getParamins().getMensualite() - ins.getReduction();
		return mnt;
	}

	@SuppressWarnings({ "unused", "unchecked" })
	public void montantParClasse(OutputStream out, Object data) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		if (listeParm.size() > 0) {
			for (ParamInscription param : listeParm) {
				dataset.addValue(getMontantPayer(param), param.getClasse().getLibelle(),
						ChakaUtils.formateDate(ChakaUtils.sysDate(), "dd/MM/yyyy"));
			}

		}
		JFreeChart barChart = ChartFactory.createBarChart("MONTANT PAYER PAR CLASSE", "", "MONTANT PAYES", dataset,
				PlotOrientation.VERTICAL, true, true, false);
		BufferedImage bufferedImage = barChart.createBufferedImage(430, 300);
		try {
			ImageIO.write(bufferedImage, "gif", out);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			ChartUtilities.saveChartAsJPEG(new File("C:\\chart6.jpg"), barChart, 600, 400);
		} catch (Exception e) {
		}
	}

	public Double getMontantTotalAPayer() {
		Double mnt = 0d;
		for (ParamInscription param : listeParm) {
			for (Inscription in : listeIns) {
				if (in.getParamins().getId().equals(param.getId()) && in.isPaiemens()) {
					mnt = (mnt + param.getMensualite()) - in.getReduction();
				}
			}
		}
		return mnt;
	}

	public Double getMontantAPayer(ParamInscription param) {
		Double mnt = 0d;
		for (Inscription in : listeIns) {
			if (in.getParamins().getId().equals(param.getId()) && in.isPaiemens()) {
				mnt = (mnt + param.getMensualite()) - in.getReduction();
			}
		}
		return mnt;
	}

	public Double getTotalMontantPayer() {
		Double mnt = 0d;
		// for (ParamInscription param : listeParm) {
		for (Recette in : listeRecettes) {
			// if (in.getInscription().getParamins().getId().equals(param.getId())
			// && in.getInscription().isPaiemens()) {
			// mnt = (mnt + in.getMontantPaye() + in.getAvoirUtilise()) -
			// in.getInscription().getAvoirEleve();
//			System.out.println("RECETTE "+in.getMontantPaye() + "AVOIR "+in.getInscription().getAvoirEleve());
			mnt = (mnt + in.getMontantPaye());// - in.getInscription().getAvoirEleve();

		}
//				if(in.getAvoirUtilise() > 0) {
//					mnt = mnt + in.getAvoirUtilise();
//				}
		// }
		// }
		return mnt;
	}

	public Double getMontantPayer(ParamInscription param) {
		Double mnt = 0d;
		for (Recette in : listeRecettes) {

			if (in.getInscription().getParamins().getId().equals(param.getId())) {
				mnt = (mnt + in.getMontantPaye()); // - (in.getInscription().getAvoirEleve());
//				if (in.getMontantPaye() > in.getInscription().getParamins().getMensualite()
//						&& in.getInscription().getAvoirEleve() <= 0) {
//					mnt = mnt - (in.getMontantPaye() - in.getInscription().getParamins().getMensualite());
//				}
			}
		}
		return mnt;
	}

	public Double getMontantTotalResteAPayer() {
		Double apayer = 0d, payer = 0d, reste = 0d;
		for (ParamInscription param : listeParm) {
			// apayer = apayer + getMontantAPayer(param);
			// payer = payer + getMontantPayer(param);
			reste = reste + getMontantResteAPayer(param);
		}
		// reste = apayer - payer;
		return reste;
	}

	public Double getMontantResteAPayer(ParamInscription param) {
		Double apayer = 0d, payer = 0d, res = 0d;
		apayer = getMontantAPayer(param);
		payer = getMontantPayer(param);
		res = apayer - payer;
		if (res < 0d)
			res = 0d;

		return res;
	}

	public float getTauxRecouvrement(ParamInscription param) {
		float taux = 0;
		Double apayer = 0d, payer = 0d;
		apayer = getMontantAPayer(param);
		payer = getMontantPayer(param);
		if (payer > 0)
			taux = (float) ((payer * 100) / apayer);
		else
			taux = 0;
		if (taux > 100)
			taux = 100;

		return taux;
	}

	@SuppressWarnings("unchecked")
	public void etatsEntreeSortie() {
		listeDepense = new ArrayList<Depense>();
		listeRecette = new ArrayList<Recette>();
		if (dateDeb == null && dateFin == null) {
			FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique",
					"Veuillez renseigner au moins une date ");
			return;
		}
		if (dateDeb == null && dateFin != null) {
			dateDeb = dateFin;
		}
		if (dateFin == null && dateDeb != null) {
			dateFin = dateDeb;
		}

		listeDepense = dataSource.createQuery(
				"From Depense d inner join fetch d.typeDepense inner join fetch d.utilisateur u inner join fetch d.institution i where d.dateDepense between :p1 and :p2 and i=:pi")
				.setParameter("p1", dateDeb).setParameter("p2", dateFin)
				.setParameter("pi", utilisateur.getInstitution()).list();
		listeRecette = dataSource.createQuery(
				"From Recette d inner join fetch d.typeRecette inner join fetch d.utilisateur  u left outer join fetch"
						+ " d.inscription inner join fetch d.institution i where d.datePaiment between :p1 and :p2 and i =:pi ")
				.setParameter("p1", dateDeb).setParameter("p2", dateFin)
				.setParameter("pi", utilisateur.getInstitution()).list();

		depense = 0d;
		recette = 0d;

		for (Depense d : listeDepense) {
			depense = depense + d.getMontantPaye();
		}

		for (Recette r : listeRecette) {
			recette = recette + r.getMontantPaye();
			if (r.getInscription() != null && r.getInscription().getAvoirEleve() > 0) {

				recette = recette - r.getInscription().getAvoirEleve();
			}
		}
		solde = recette - depense;
		listeTypeRecette = new ArrayList<TypeRecette>();
		listeTypeDepense = new ArrayList<TypeDepense>();
		List<TypeRecette> listeTypeRecette1 = dataSource
				.createQuery("From TypeRecette t inner join fetch t.institution i where i =:pi")
				.setParameter("pi", utilisateur.getInstitution()).list();
		List<TypeDepense> listeTypeDepense1 = dataSource
				.createQuery("From TypeDepense t inner join fetch t.institution i where i =:pi ")
				.setParameter("pi", utilisateur.getInstitution()).list();

		Double recette = 0.0;
		Double depense = 0.0;
		for (TypeRecette rec : listeTypeRecette1) {
			recette = 0.0;
			for (Recette r : listeRecette) {
				if (rec.getId().equals(r.getTypeRecette().getId())) {
					recette = recette + r.getMontantPaye();
					if (r.getInscription() != null && r.getInscription().getAvoirEleve() > 0) {
						recette = recette - r.getInscription().getAvoirEleve();
					}
				}
			}
			if (recette > 0) {
				rec.setMontant(recette);
				listeTypeRecette.add(rec);
			}
		}

		for (TypeDepense dep : listeTypeDepense1) {
			depense = 0.0;
			for (Depense r : listeDepense) {
				if (dep.getId().equals(r.getTypeDepense().getId())) {
					depense = depense + r.getMontantPaye();
				}
			}
			if (depense > 0) {
				dep.setMontant(depense);
				listeTypeDepense.add(dep);
			}
		}
	}

	public void imprimerEtats() {
		Institution in = (Institution) dataSource.get(Institution.class, utilisateur.getInstitution().getId());
		List<Etats> listeEtats = new ArrayList<Etats>();
		int taille = 0;
		for (TypeRecette ty : listeTypeRecette) {
			Etats etat = new Etats();
			etat.setLibelle(ty.getLibelle());
			etat.setMontant(ty.getMontant());
			listeEtats.add(etat);
		}
		if (listeEtats.size() > 0 && listeTypeDepense.size() > 0) {
			taille = listeEtats.size() - 1;
			listeEtats.get(taille).setListeDep(listeTypeDepense);
		} else {
			for (TypeDepense ty : listeTypeDepense) {
				Etats etat = new Etats();
				etat.setLibelle(ty.getLibelle());
				etat.setMontant(ty.getMontant());
				listeEtats.add(etat);
			}
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ecole", in.getNom());
		param.put("slogan", in.getSologan());
		param.put("tel", in.getTelephone());
		param.put("etat", "DU: " + ChakaUtils.formateDate(dateDeb, "dd/MM/yyyy") + "AU"
				+ ChakaUtils.formateDate(dateFin, "dd/MM/yyyy"));
		param.put("en", recette);
		param.put("st", depense);
		param.put("sd", solde);
		getFilePrintService().imprimer("ecole", "etats", param, listeEtats, utilisateur, ExportOption.PDF);
		this.setShowModal("javascript:Richfaces.showModalPanel('modalPdf')");
	}

	public void imprimerEffectif() {
		Institution in = (Institution) dataSource.get(Institution.class, utilisateur.getInstitution().getId());

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ecole", in.getNom());
		param.put("slogan", in.getSologan());
		param.put("tel", in.getTelephone());

		param.put("eff", "EFFECTIF TOTAL      " + nbeleve);
		param.put("etat", "NOMBRE DE FILLE    " + nbfille);
		param.put("e", "EFFECTIF GARÇON      " + nbgarcon);
		getFilePrintService().imprimer("ecole", "recap", param, listeClasse, utilisateur, ExportOption.PDF);
		this.setShowModal("javascript:Richfaces.showModalPanel('modalPdf')");
	}

	public void zoomRecette(TypeRecette typeRecette) {
		listeRecetteZoom = new ArrayList<Recette>();
		for (Recette r : listeRecette) {
			if (typeRecette.getId().equals(r.getTypeRecette().getId())) {
				if (r.getInscription() != null && r.getInscription().getAvoirEleve() > 0) {
					r.setMontant(r.getMontantPaye() - r.getInscription().getAvoirEleve());
				} else {
					r.setMontant(r.getMontantPaye());
				}
				listeRecetteZoom.add(r);
			}
		}
	}

	public String getEleve(Recette recette) {
		String eleve = "";
		if (recette.getInscription() != null && recette.getInscription().getEleve() != null) {
			eleve = recette.getInscription().getEleve().getPrenom() + " "
					+ recette.getInscription().getEleve().getNom();
		}

		return eleve;
	}

	public void zoomDepense(TypeDepense typedepense) {
		listeDepenseZoom = new ArrayList<Depense>();
		for (Depense dep : listeDepense) {
			if (typedepense.getId().equals(dep.getTypeDepense().getId())) {
				listeDepenseZoom.add(dep);
			}
		}
	}

	public Double getMontantTypeRecette(TypeRecette rec) {
		Double recette = 0.0;
		for (Recette r : listeRecette) {
			if (rec.getId().equals(r.getTypeRecette().getId())) {
				recette = recette + r.getMontantPaye();
				if (r.getInscription() != null && r.getInscription().getAvoirEleve() > 0) {
					recette = recette - r.getInscription().getAvoirEleve();
				}
			}
		}
		return recette;
	}

	public Double getMontantTypeDepense(TypeDepense dep) {
		Double depense = 0.0;
		for (Depense d : listeDepense) {
			if (d.getTypeDepense().getId().equals(dep.getId())) {
				depense = depense + d.getMontantPaye();
			}
		}
		return depense;
	}

	@SuppressWarnings({ "unused", "unchecked" })
	public void effectifarClasse(OutputStream out, Object data) {

		List<ParamInscription> listeParam = dataSource.createQuery(
				"from ParamInscription p inner join fetch p.annee inner join fetch p.classe inner join fetch p.institution i "
						+ " where p.annee =:pannee and i =:pi ")
				.setParameter("pannee", annee).setParameter("pi", utilisateur.getInstitution()).list();
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		if (listeParam.size() > 0) {
			for (ParamInscription param : listeParam) {
				effectifClasse(param);
				dataset.addValue(getNombreEleve(param), param.getClasse().getLibelle(),
						ChakaUtils.formateDate(ChakaUtils.sysDate(), "dd/MM/yyyy"));
			}

		}
		JFreeChart barChart = ChartFactory.createBarChart("EFFECTIF PAR CLASSE", "", "NOMBRE D'ELEVE(S)", dataset,
				PlotOrientation.VERTICAL, true, true, false);
		BufferedImage bufferedImage = barChart.createBufferedImage(430, 300);
		try {
			ImageIO.write(bufferedImage, "gif", out);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			ChartUtilities.saveChartAsJPEG(new File("C:\\chart6.jpg"), barChart, 600, 400);
		} catch (Exception e) {
		}
	}

	@SuppressWarnings("unchecked")
	public int getNombreEleve(ParamInscription param) {
		List<Inscription> liste = dataSource
				.createQuery("From Inscription i inner join fetch i.eleve"
						+ "  inner join fetch i.paramins p inner join fetch i.institution s where p =:pp and s=:ps")
				.setParameter("pp", param).setParameter("ps", utilisateur.getInstitution()).list();
		return liste.size();
	}

	public int getNombreSecreataire() {
		int nombre = 0;

		for (Utilisateur u : listeUser) {
			if (u.getProfile().getLibelle_court().equalsIgnoreCase("SEC")) {
				nombre++;
			}
		}

		return nombre;
	}

	public int getNombreEnseignant() {
		int nombre = 0;

		for (Utilisateur u : listeUser) {
			if (u.getProfile().getLibelle_court().equalsIgnoreCase("ENS")) {
				nombre++;
			}
		}
		return nombre;
	}

	@SuppressWarnings("unchecked")
	@Create
	public void getEffectifClasses() {
		listeUser = new ArrayList<Utilisateur>();
		listeUser = dataSource.createQuery(
				"From Utilisateur u inner join fetch u.profile p inner join fetch u.institution i where p.libelle_court =:plib1 or p.libelle_court =:plib2 and i=:pi ")
				.setParameter("plib1", "ENS").setParameter("plib2", "SEC")
				.setParameter("pi", utilisateur.getInstitution()).list();
		ins = (Institution) dataSource.get(Institution.class, utilisateur.getInstitution().getId());
		listeClasse = new ArrayList<Classe>();
		listeClasse = dataSource.createQuery("From Classe ").list();

		List<ParamInscription> listeParam = dataSource.createQuery(
				"from ParamInscription p inner join fetch p.annee inner join fetch p.classe inner join fetch p.institution s"
						+ " where p.annee =:pannee and s =:ps")
				.setParameter("pannee", annee).setParameter("ps", utilisateur.getInstitution()).list();

		if (listeParam.size() > 0) {
			nbeleve = 0;
			nbgarcon = 0;
			nbfille = 0;
			for (ParamInscription param : listeParam) {
				effectifClasse(param);

			}

		}
		listeIns = new ArrayList<Inscription>();
		listeIns = dataSource.createQuery("From Inscription i inner join fetch i.eleve"
				+ "  inner join fetch i.paramins p inner join fetch p.annee an inner join fetch i.institution s where an =:pp and s=:ps")
				.setParameter("pp", annee).setParameter("ps", utilisateur.getInstitution()).list();
		for (Classe cl : listeClasse) {
			getNombreFilleGarcon(listeIns, cl);
		}

		nbeleve = nbgarcon + nbfille;

	}

	@SuppressWarnings("unchecked")
	public void effectifClasse(ParamInscription param) {
		listeIns = new ArrayList<Inscription>();
		listeIns = dataSource
				.createQuery("From Inscription i inner join fetch i.eleve"
						+ "  inner join fetch i.paramins p  inner join fetch i.institution s where p =:pp and s=:ps")
				.setParameter("pp", param).setParameter("ps", utilisateur.getInstitution()).list();
		for (Classe cl : listeClasse) {
			if (cl.getIdclasse().equals(param.getClasse().getIdclasse())) {
				cl.setNombre(listeIns.size());
			}
		}
	}

	public void getNombreFilleGarcon(List<Inscription> liste, Classe classe) {
		int nombref = 0;
		int nombreg = 0;

		for (Inscription ins : liste) {
			if (ins.getParamins().getClasse().getIdclasse().equals(classe.getIdclasse())) {
				if (ins.getEleve().getSexe().equalsIgnoreCase("M")) {
					nombreg++;
					nbgarcon++;
				} else {
					nombref++;
					nbfille++;
				}
			}
		}
		classe.setFille(nombref);
		classe.setGarcon(nombreg);
	}

	public List<Classe> getListeClasse() {
		return listeClasse;
	}

	public void setListeClasse(List<Classe> listeClasse) {
		this.listeClasse = listeClasse;
	}

	public int getNbeleve() {
		return nbeleve;
	}

	public void setNbeleve(int nbeleve) {
		this.nbeleve = nbeleve;
	}

	public int getNbfille() {
		return nbfille;
	}

	public void setNbfille(int nbfille) {
		this.nbfille = nbfille;
	}

	public int getNbgarcon() {
		return nbgarcon;
	}

	public void setNbgarcon(int nbgarcon) {
		this.nbgarcon = nbgarcon;
	}

	public Institution getIns() {
		return ins;
	}

	public void setIns(Institution ins) {
		this.ins = ins;
	}

	public Date getDateDeb() {
		return dateDeb;
	}

	public void setDateDeb(Date dateDeb) {
		this.dateDeb = dateDeb;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	public List<Depense> getListeDepense() {
		return listeDepense;
	}

	public void setListeDepense(List<Depense> listeDepense) {
		this.listeDepense = listeDepense;
	}

	public List<Recette> getListeRecette() {
		return listeRecette;
	}

	public void setListeRecette(List<Recette> listeRecette) {
		this.listeRecette = listeRecette;
	}

	public Double getRecette() {
		return recette;
	}

	public void setRecette(Double recette) {
		this.recette = recette;
	}

	public Double getDepense() {
		return depense;
	}

	public void setDepense(Double depense) {
		this.depense = depense;
	}

	public Double getSolde() {
		return solde;
	}

	public void setSolde(Double solde) {
		this.solde = solde;
	}

	public List<TypeRecette> getListeTypeRecette() {
		return listeTypeRecette;
	}

	public void setListeTypeRecette(List<TypeRecette> listeTypeRecette) {
		this.listeTypeRecette = listeTypeRecette;
	}

	public List<TypeDepense> getListeTypeDepense() {
		return listeTypeDepense;
	}

	public void setListeTypeDepense(List<TypeDepense> listeTypeDepense) {
		this.listeTypeDepense = listeTypeDepense;
	}

	public List<Recette> getListeRecetteZoom() {
		return listeRecetteZoom;
	}

	public void setListeRecetteZoom(List<Recette> listeRecetteZoom) {
		this.listeRecetteZoom = listeRecetteZoom;
	}

	public List<Depense> getListeDepenseZoom() {
		return listeDepenseZoom;
	}

	public void setListeDepenseZoom(List<Depense> listeDepenseZoom) {
		this.listeDepenseZoom = listeDepenseZoom;
	}

	public String getMois() {
		return mois;
	}

	public void setMois(String mois) {
		this.mois = mois;
	}

	public List<ParamInscription> getListeParm() {
		return listeParm;
	}

	public void setListeParm(List<ParamInscription> listeParm) {
		this.listeParm = listeParm;
	}

	public List<Recette> getListeRecettes() {
		return listeRecettes;
	}

	public void setListeRecettes(List<Recette> listeRecettes) {
		this.listeRecettes = listeRecettes;
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

	public String getShowModal() {
		return showModal;
	}

	public void setShowModal(String showModal) {
		this.showModal = showModal;
	}

	public List<Utilisateur> getListeUser() {
		return listeUser;
	}

	public void setListeUser(List<Utilisateur> listeUser) {
		this.listeUser = listeUser;
	}

	public List<Eleve> getListeEleves() {
		return listeEleves;
	}

	public void setListeEleves(List<Eleve> listeEleves) {
		this.listeEleves = listeEleves;
	}

	public List<Inscription> getListeInscrption() {
		return listeInscrption;
	}

	public void setListeInscrption(List<Inscription> listeInscrption) {
		this.listeInscrption = listeInscrption;
	}

}