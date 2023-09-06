/**
 * 
 */
package com.ecole.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
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
import com.ecole.entity.Depense;
import com.ecole.entity.Eleve;
import com.ecole.entity.Inscription;
import com.ecole.entity.Niveau;
import com.ecole.entity.ParamInscription;
import com.ecole.entity.Recette;
import com.ecole.entity.TypeRecette;

import sun.nio.cs.ext.ISCII91;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;

/**
 * @author MNDIR Date : 15/08/2023 Lieu : Conakry
 *
 */
@Scope(ScopeType.SESSION)
@Name("recetteService")
public class RecetteService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Recette> listeRecette = new ArrayList<Recette>();

	private Recette recetteEnCreation = new Recette();

	private List<TypeRecette> listeTypeRecette = new ArrayList<TypeRecette>();

	private List<Recette> listeRecettes = new ArrayList<Recette>();

	private String codeRecette;
	private List<Eleve> listeEleve = new ArrayList<Eleve>();
	private Eleve eleve = new Eleve();

	@In
	private Session dataSource;

	/**
	 * . injection de la variable factureService
	 */
	@In(required = false)
	@Out(required = false)
	private NoteService noteService;

	/**
	 * Utilisateur loggué
	 */
	@In(required = false)
	@Out(required = false)
	private Utilisateur utilisateur;

	@In(required = false)
	private AnneeScolaire annee;

	private List<Niveau> listeNiveau;
	private Niveau niveau;

	private Inscription inscription;
	private ParamInscription paramInscription;
	private Classe classe;
	private List<Classe> listeClasse;

	private String[] lesMois;

	private double montantPayerGenere, montantDu;

	private double reliquatInitial;

	private double montantPayeFinial = 0d;

	@SuppressWarnings("unchecked")
	public void chargerListeRecette(String codeRecette) {
		listeRecette = new ArrayList<Recette>();
		listeRecette = dataSource.createQuery(" From Recette  where inscription.eleve=:pe order by idRecette desc ")
				.setParameter("pe", this.eleve).list();
	}

	@SuppressWarnings("unchecked")
	public String versRecette() {
		listeTypeRecette = new ArrayList<TypeRecette>();
		listeTypeRecette = dataSource.createQuery("From TypeRecette where code <>:pcode1 and code <>:pcode2")
				.setParameter("pcode1", "INS").setParameter("pcode2", "MENS").list();
		chargerListeRecette();
		return "/pages/nuramecole/creerrecette.xhtml";
	}

	public void ajouterRecettes() {
		if (recetteEnCreation.getTypeRecette() == null) {
			FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique",
					"Veuillez choisir un type de recette");
			return;
		}
		if (recetteEnCreation.getMontantPaye() <= 0) {
			FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique",
					"Veuillez renseigner le montant");
			return;
		}
		if (recetteEnCreation.getDatePaiment() == null) {
			FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique", "Veuillez renseigner la date");
			return;
		}
		recetteEnCreation.setAnnee(annee);
		recetteEnCreation.setUtilisateur(utilisateur);
		if (recetteEnCreation.getIdRecette() == null) {
			dataSource.save(recetteEnCreation);
		} else {
			dataSource.update(recetteEnCreation);
		}
		FacesMessages.instance().addToControlFromResourceBundle("infoGenerique", "Recette ajoutée avec succès");
		this.setRecetteEnCreation(new Recette());
		chargerListeRecette();
	}

	public void versModifierRecettes(Recette recette) {
		this.setRecetteEnCreation(recette);
	}

	@SuppressWarnings("unchecked")
	public void chargerListeRecette() {
		listeRecettes = new ArrayList<Recette>();
		listeRecettes = dataSource
				.createQuery("From Recette r inner join fetch r.annee an inner join fetch r.typeRecette "
						+ " inner join fetch r.utilisateur where an =:pan ")
				.setParameter("pan", annee).list();
	}

	/**
	 * Page vers la mensualité
	 * 
	 * @return
	 */
	public String versMensualite() {
		retreiveListNiveau();
		// this.setRecetteEnCreation(new Recette());

		return "/pages/nuramecole/mensualite.xhtml";
	}

	/**
	 * get liste niveau
	 * 
	 * @return
	 */
	public List<Niveau> retreiveListNiveau() {
		listeNiveau = new ArrayList<Niveau>();
		listeNiveau = dataSource.createQuery("From Niveau ").list();
		return listeNiveau;
	}

	public void chargerListeClasse() {
		setListeClasse(new ArrayList<Classe>());
		setListeClasse(dataSource.createQuery(" From Classe c inner join fetch c.niveau n where n=:pn")
				.setParameter("pn", niveau).list());
	}

	public void annulerAjout() {
		this.setRecetteEnCreation(new Recette());
	}

	@SuppressWarnings("unchecked")
	public void chargerListeEleveClasse(Classe classe) {
		try {
			listeEleve = new ArrayList<Eleve>();
			listeEleve = dataSource
					.createQuery(
							"Select i.eleve From Inscription i where i.paramins.classe=:pc and i.paramins.annee=:pa")
					.setParameter("pc", classe).setParameter("pa", annee).list();

			// retreiveInfoIncription();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void retreiveInfoIncription() {

		try {
			inscription = (Inscription) dataSource
					.createQuery(" From Inscription i where i.eleve=:pe and i.paramins.annee=:pa ")
					.setParameter("pe", this.eleve).setParameter("pa", annee).uniqueResult();
			// System.out.println("**La prinscription est la
			// "+inscription.getParamins().getMensualite()+"\n reduction
			// "+inscription.getReduction());
			codeRecette = "MENS";

			chargerListeRecette(codeRecette);

			/*
			 * if(inscription .getReliquat() > 0d){
			 * System.out.println("** Dans if reliquat > 0 **"); recetteEnCreation =
			 * (Recette) dataSource
			 * .createQuery(" From Recette r where r.inscription=:pi order by idRecette desc limit 1"
			 * ) .setParameter("pi", this.inscription).setMaxResults(1); }
			 * 
			 * if(recetteEnCreation ==null && recetteEnCreation.getIdRecette()==null){
			 * recetteEnCreation = new Recette(); }
			 */

			if (inscription != null) {
				double mensualite = 0;
				if (inscription.getReliquat() == 0) {
					mensualite = inscription.getParamins().getMensualite();
					reliquatInitial = 0d;
					moisConcerne(inscription.getMoisenCours() == 0 ? 10 : inscription.getMoisenCours());
				} else {
					reliquatInitial = inscription.getReliquat();
					moisConcerne(inscription.getMoisenCours());
				}
				montantPayerGenere = mensualite + reliquatInitial
						- (inscription.getReduction() > 0 ? inscription.getReduction() : 0);
				setMontantPayeFinial(montantPayerGenere);

				moisConcerne(moisEnChiffre());

			}

		} catch (HibernateException e) {
			e.printStackTrace();
			// FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique",
			// "Aucun éléve trouvé !!");

		}

	}

	public void ajouterRecette() {
		try {
			recetteEnCreation.setMontantPaye(montantPayeFinial);// Montant payé

			if (this.recetteEnCreation.getMontantPaye() == 0d) {
				FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique",
						"Veuillez saisir un montant");
				return;
			}

			recetteEnCreation.setDatePaiment(new Date());
			recetteEnCreation.setUtilisateur(utilisateur);
			/** Mise à jour d'inscription **/

			if (montantPayerGenere <= recetteEnCreation.getMontantPaye()) {
				inscription.setReliquat(0d);
				inscription.setAvoirEleve(recetteEnCreation.getMontantPaye() - montantPayerGenere);
				/** Mise à jour d'inscription **/

				if (inscription.getMoisenCours() == 0) {
					recetteEnCreation.setMoisPaye(10);
				} else if (inscription.getMoisenCours() == 12) {
					inscription.setMoisenCours(1);
					recetteEnCreation.setMoisPaye(12);
				} else {
					recetteEnCreation.setMoisPaye(inscription.getMoisenCours());
					inscription.setMoisenCours(inscription.getMoisenCours() + 1);

				}
				recetteEnCreation.setEditable(true);
			} else {

				if (inscription.getMoisenCours() == 0) {
					inscription.setMoisenCours(10);
					recetteEnCreation.setMoisPaye(10);
				}
				recetteEnCreation.setMoisPaye(inscription.getMoisenCours());
				inscription.setReliquat(montantPayerGenere - recetteEnCreation.getMontantPaye());
				inscription.setAvoirEleve(0d);

			}

			System.out.println("**recetteEnCreation = " + recetteEnCreation);
			System.out.println("### recetteEnCreation = " + recetteEnCreation.getIdRecette());
			if (recetteEnCreation.getIdRecette() == null) {
				System.out.println("**1111** " + inscription.getReliquat());

				recetteEnCreation.setInscription(inscription);
				recetteEnCreation.setTypeRecette(getTypeRecetteByCode(this.codeRecette));
				// recetteEnCreation.setMoisPaye(moisEnChiffre()>0?moisEnChiffre()+1:10); // Si
				// 1er enregistrement mettre le mois de novembre
				// moisConcerne(moisEncoursDePaiement());
				System.out.println("**montant paye** " + recetteEnCreation.getMontantPaye());

				recetteEnCreation.setMontantPaye(montantPayeFinial);
				dataSource.save(recetteEnCreation);
				dataSource.flush();
			} else {

				System.out.println("**2222** " + inscription.getReliquat());
				recetteEnCreation.setMontantPaye(montantPayeFinial);
				dataSource.merge(recetteEnCreation);
			}

			// moisConcerne(inscription.getMoisenCours()); // Activer le mois suivant
			dataSource.merge(inscription);
			chargerListeRecette("MENS");
			retreiveInfoIncription();
			this.setRecetteEnCreation(new Recette());
			FacesMessages.instance().addToControlFromResourceBundle("infoGenerique", "Paiement effectué avec succés");
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int moisEnChiffre() {
		if (inscription.getMois1())
			return 1;
		if (inscription.getMois2())
			return 2;
		if (inscription.getMois3())
			return 3;
		if (inscription.getMois4())
			return 4;
		if (inscription.getMois5())
			return 5;
		if (inscription.getMois6())
			return 6;
		if (inscription.getMois10() || !inscription.getMois10())
			return 10;
		if (inscription.getMois11())
			return 11;
		if (inscription.getMois12())
			return 12;

		return 0;
	}

	public void moisConcerne(int mois) {
		if (mois == 1)
			inscription.setMois1(true);
		if (mois == 2)
			inscription.setMois2(true);
		if (mois == 3)
			inscription.setMois3(true);
		if (mois == 4)
			inscription.setMois4(true);
		if (mois == 5)
			inscription.setMois5(true);
		if (mois == 6)
			inscription.setMois6(true);
		if (mois == 7)
			inscription.setMois7(true);
		if (mois == 10 || mois == 0)
			inscription.setMois10(true);
		if (mois == 11)
			inscription.setMois11(true);
		if (mois == 12)
			inscription.setMois12(true);
	}

	public TypeRecette getTypeRecetteByCode(String codeRecette) {
		return (TypeRecette) dataSource.createQuery(" From TypeRecette where code=:pc").setParameter("pc", codeRecette)
				.uniqueResult();
	}

	public String retreiveMonthByInt(int month) {
		try {
			String monthString;

			switch (month) {
			case 1:
				monthString = "Janvier";
				break;
			case 2:
				monthString = "Février";
				break;
			case 3:
				monthString = "Mars";
				break;
			case 4:
				monthString = "Avril";
				break;
			case 5:
				monthString = "Mai";
				break;
			case 6:
				monthString = "Juin";
				break;
			case 7:
				monthString = "Juillet";
				break;
			case 10:
				monthString = "Octobre";
				break;
			case 11:
				monthString = "Novembre";
				break;
			case 12:
				monthString = "Décembre";
				break;
			default:
				monthString = "Invalid month";
				break;
			}
			System.out.println(monthString);

			monthString = new DateFormatSymbols().getMonths()[month - 1].toUpperCase();
			return monthString;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			return null;
		}
	}

	public void versModifierRecette(Recette recette) {
		this.setRecetteEnCreation(recette);
		double montantRestitue = recette.getMontantPaye() + inscription.getReliquat() + inscription.getAvoirEleve();
		montantPayeFinial = recette.getMontantPaye();
		montantPayerGenere = montantRestitue;

	}

	public List<Recette> getListeRecette() {
		return listeRecette;
	}

	public void setListeRecette(List<Recette> listeRecette) {
		this.listeRecette = listeRecette;
	}

	public Recette getRecetteEnCreation() {
		return recetteEnCreation;
	}

	public void setRecetteEnCreation(Recette recetteEnCreation) {
		this.recetteEnCreation = recetteEnCreation;
	}

	public String getCodeRecette() {
		return codeRecette;
	}

	public void setCodeRecette(String codeRecette) {
		this.codeRecette = codeRecette;
	}

	public List<Eleve> getListeEleve() {
		return listeEleve;
	}

	public void setListeEleve(List<Eleve> listeEleve) {
		this.listeEleve = listeEleve;
	}

	public NoteService getNoteService() {
		return noteService;
	}

	public void setNoteService(NoteService noteService) {
		this.noteService = noteService;
	}

	public List<Niveau> getListeNiveau() {
		return listeNiveau;
	}

	public void setListeNiveau(List<Niveau> listeNiveau) {
		this.listeNiveau = listeNiveau;
	}

	public Niveau getNiveau() {
		return niveau;
	}

	public void setNiveau(Niveau niveau) {
		this.niveau = niveau;
	}

	public Inscription getInscription() {
		return inscription;
	}

	public void setInscription(Inscription inscription) {
		this.inscription = inscription;
	}

	public Classe getClasse() {
		return classe;
	}

	public void setClasse(Classe classe) {
		this.classe = classe;
	}

	public List<Classe> getListeClasse() {
		return listeClasse;
	}

	public void setListeClasse(List<Classe> listeClasse) {
		this.listeClasse = listeClasse;
	}

	public ParamInscription getParamInscription() {
		return paramInscription;
	}

	public void setParamInscription(ParamInscription paramInscription) {
		this.paramInscription = paramInscription;
	}

	public Eleve getEleve() {
		return eleve;
	}

	public void setEleve(Eleve eleve) {
		this.eleve = eleve;
	}

	public String[] getLesMois() {
		return lesMois;
	}

	public void setLesMois(String[] lesMois) {
		this.lesMois = lesMois;
	}

	public double getMontantPayerGenere() {
		return montantPayerGenere;
	}

	public void setMontantPayerGenere(double montantPayerGenere) {
		this.montantPayerGenere = montantPayerGenere;
	}

	public double getMontantDu() {
		return montantDu;
	}

	public void setMontantDu(double montantDu) {
		this.montantDu = montantDu;
	}

	public double getReliquatInitial() {
		return reliquatInitial;
	}

	public void setReliquatInitial(double reliquatInitial) {
		this.reliquatInitial = reliquatInitial;
	}

	public double getMontantPayeFinial() {
		return montantPayeFinial;
	}

	public void setMontantPayeFinial(double montantPayeFinial) {
		this.montantPayeFinial = montantPayeFinial;
	}

	public List<TypeRecette> getListeTypeRecette() {
		return listeTypeRecette;
	}

	public void setListeTypeRecette(List<TypeRecette> listeTypeRecette) {
		this.listeTypeRecette = listeTypeRecette;
	}

	public List<Recette> getListeRecettes() {
		return listeRecettes;
	}

	public void setListeRecettes(List<Recette> listeRecettes) {
		this.listeRecettes = listeRecettes;
	}

}
