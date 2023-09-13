/**
 * 
 */
package com.ecole.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.annotations.ForceDiscriminator;
import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;

import com.chaka.projet.entity.Utilisateur;
import com.ecole.entity.Absence;
import com.ecole.entity.AnneeScolaire;
import com.ecole.entity.Classe;
import com.ecole.entity.Eleve;
import com.ecole.entity.Inscription;
import com.ecole.entity.Institution;
import com.ecole.entity.Niveau;
import com.ecole.entity.ParamInscription;
import com.ecole.entity.Recette;
import com.ecole.entity.TypeRecette;
import com.rhospi.commons.ChakaUtils;
import com.rhospi.commons.FileUploadService;
import com.rhospi.commons.ChakaUtils.ExportOption;

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

	private Double mntIns = 0d;

	private Double mntPaye = 0d;

	private List<Inscription> listeIns = new ArrayList<Inscription>();

	@In
	private AnneeScolaire annee;

	FileUploadService filePrintService;

	@In(required = false)
	@Out(required = false)
	private Utilisateur utilisateur;

	private String showModal = "";

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

//	public String versListeEleve() {
//		this.setEleve(new Eleve());
//		List<AnneeScolaire> listeAnnee = dataSource.createQuery("From AnneeScolaire order by idannee desc ").list();
//		if (listeAnnee.size() > 0) {
//			anneeScolaire = listeAnnee.get(0);
//		}
//		listeNiveau = new ArrayList<Niveau>();
//		listeNiveau = dataSource.createQuery("From Niveau ").list();
//
//		return "/pages/nuramecole/creereleve.xhtml";
//	}

	@SuppressWarnings("unchecked")
	public String versListeEleve() {
		List<AnneeScolaire> listeAnnee = dataSource.createQuery("From AnneeScolaire order by idannee desc ").list();
		if (listeAnnee.size() > 0) {
			anneeScolaire = listeAnnee.get(0);
		}
		listeNiveau = new ArrayList<Niveau>();
		listeNiveau = dataSource.createQuery("From Niveau ").list();
		return "/pages/nuramecole/listeeleve.xhtml";
	}

	@SuppressWarnings("unchecked")
	public void rechercherEleves() {
		listeIns = new ArrayList<Inscription>();
		ParamInscription p = (ParamInscription) dataSource
				.createQuery("From ParamInscription p inner join fetch p.classe c inner join fetch p.annee pa "
						+ " where c =:pc and pa =:pa ")
				.setParameter("pc", classe).setParameter("pa", annee).uniqueResult();

		if (p != null) {
			listeIns = dataSource
					.createQuery(
							"From Inscription i inner join fetch i.eleve inner join fetch i.paramins p where p =:pp")
					.setParameter("pp", p).list();

		}

	}

	public void imprimerListeEleves() {
		Institution in = (Institution) dataSource.createQuery("From Institution").uniqueResult();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ecole", in.getNom());
		param.put("slogan", in.getSologan());
		param.put("tel", in.getTelephone());
		param.put("etat", "LISTE DES ELEVES DE LA CLASSE " + classe.getLibelle());
		getFilePrintService().imprimer("ecole", "eleve", param, listeIns, utilisateur, ExportOption.PDF);
		this.setShowModal("javascript:Richfaces.showModalPanel('modalPdf')");
	}

	@SuppressWarnings("unchecked")
	public void chargerListeClasse() {
		listeClasse = new ArrayList<Classe>();
		listeClasse = dataSource.createQuery(" From Classe c inner join fetch c.niveau n where n=:pn")
				.setParameter("pn", niveau).list();

		if (listeClasse.size() > 0) {
			mntIns = 0d;
			ParamInscription paramins = (ParamInscription) dataSource
					.createQuery("From ParamInscription p inner join fetch p.annee inner join fetch p.classe"
							+ " where p.annee =:pannee and p.classe =:pclasse")
					.setParameter("pannee", annee).setParameter("pclasse", listeClasse.get(0)).uniqueResult();

			if (paramins != null) {
				mntIns = paramins.getDroit_ins();
			}
		}
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

	public void getMntInscription() {
		mntIns = 0d;
		ParamInscription paramins = (ParamInscription) dataSource
				.createQuery("From ParamInscription p inner join fetch p.annee inner join fetch p.classe"
						+ " where p.annee =:pannee and p.classe =:pclasse")
				.setParameter("pannee", annee).setParameter("pclasse", classe).uniqueResult();

		if (paramins != null) {
			mntIns = paramins.getDroit_ins();
		}

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
			if (mntPaye > 0) {
				inscription.setMontantInscriptionPaye(mntPaye);
				if (mntPaye > mntIns) {
					inscription.setAvoirEleve(mntPaye - mntIns);
				}
				if (mntPaye < mntIns) {
					inscription.setReliquat_ins(mntIns - mntPaye);
				}
			}
			dataSource.save(inscription);
			if (mntPaye > 0) {
				Recette recette = new Recette();
				recette.setInscription(inscription);
				recette.setTypeRecette(getTypeRecetteByCode("INS"));
				recette.setDatePaiment(ChakaUtils.sysDate());
				recette.setMontantPaye(mntPaye);
				recette.setUtilisateur(utilisateur);
				dataSource.save(recette);

			}
			this.setEleve(new Eleve());
			this.setInscription(new Inscription());

			mntIns = paramins.getDroit_ins();

		
			mntIns = 0d;
			FacesMessages.instance().addToControlFromResourceBundle("infoGenerique", "Evaluation ajoutée avec succés");
		}
	}

	public TypeRecette getTypeRecetteByCode(String codeRecette) {
		return (TypeRecette) dataSource.createQuery(" From TypeRecette where code=:pc").setParameter("pc", codeRecette)
				.uniqueResult();
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

	public void versModifierEleve(Inscription ins) {
		this.setEleve(ins.getEleve());
		this.setInscription(ins);
	}

	public void modifierEleves() {
		dataSource.update(eleve);
		dataSource.update(inscription);
	}

	public Double versGetReduction(Eleve eleve) {
		double resul = 0.0;
		Inscription ins = (Inscription) dataSource
				.createQuery("From Inscription i inner join fetch i.paramins p inner join fetch i.eleve e "
						+ " where p =:pp and e =:pi")
				.setParameter("pp", p).setParameter("pi", eleve).uniqueResult();
		if (ins != null && ins.getReduction() != 0d) {
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

	public Double getMntIns() {
		return mntIns;
	}

	public void setMntIns(Double mntIns) {
		this.mntIns = mntIns;
	}

	public Double getMntPaye() {
		return mntPaye;
	}

	public void setMntPaye(Double mntPaye) {
		this.mntPaye = mntPaye;
	}

	public List<Inscription> getListeIns() {
		return listeIns;
	}

	public void setListeIns(List<Inscription> listeIns) {
		this.listeIns = listeIns;
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

}
