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
import com.ecole.entity.Eleve;
import com.ecole.entity.Inscription;
import com.ecole.entity.Niveau;
import com.ecole.entity.ParamInscription;
import com.ecole.entity.Recette;
import com.ecole.entity.TypeRecette;

/**
 * @author MNDIR
 * Date : 15/08/2023
 * Lieu : Conakry
 *
 */
@Scope(ScopeType.SESSION)
@Name("recetteService")
public class RecetteService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Eleve getEleve() {
		return eleve;
	}

	public void setEleve(Eleve eleve) {
		this.eleve = eleve;
	}

	private List<Recette> listeRecette = new ArrayList<Recette>();

	private Recette recetteEnCreation = new Recette();
	
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



	@SuppressWarnings("unchecked")
	public void chargerListeRecette(String codeRecette) {
		listeRecette = new ArrayList<Recette>();
		listeRecette = dataSource.createQuery(" From Recette ").list();
	}

	public String versRecette() {
		this.setRecetteEnCreation(new Recette());
		//chargerListeRecette();
		return "/pages/ecole/creereval.xhtml";
	}
	/**
	 * Page vers la mensualité
	 * @return
	 */
	public String versMensualite() {
		System.out.println("anneé en cours "+annee.getAnneeScolaire());
		retreiveListNiveau();
		this.setRecetteEnCreation(new Recette());
		codeRecette = "MENS";
		chargerListeRecette(codeRecette);
		return "/pages/ecole/comptabilite/mensualite.xhtml";
	}
	/**
	 * get liste niveau
	 * @return
	 */
	public List<Niveau> retreiveListNiveau(){
		listeNiveau = new ArrayList<Niveau>();
		listeNiveau =  dataSource.createQuery("From Niveau ").list();
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
			listeEleve = dataSource.createQuery("Select i.eleve From Inscription i where i.paramins.classe=:pc and i.paramins.annee=:pa")
					.setParameter("pc", classe)
					.setParameter("pa", annee).list();
			
			//retreiveInfoIncription();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Inscription retreiveInfoIncription() {
		
		inscription= (Inscription) dataSource.createQuery(" From Inscription i where i.eleve=:pe and i.paramins.annee=:pa ")
				.setParameter("pe", this.eleve)
				.setParameter("pa", annee).uniqueResult();
		System.out.println("**La prinscription est la "+inscription.getParamins().getMensualite()+"\n reduction "+inscription.getReduction());
		return inscription;
	}
	public void ajouterRecette() {
		try {
			if (this.recetteEnCreation.getMontantPaye() == 0d) {
				FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique", "Veuillez saisir un montant");
				return;
			}

			if (recetteEnCreation.getIdRecette() == null) {
				recetteEnCreation.setInscription(inscription);
				recetteEnCreation.setTypeRecette(getTypeRecetteByCode(this.codeRecette));
				recetteEnCreation.setDatePaiment(new Date());
				recetteEnCreation.setUtilisateur(utilisateur);
				dataSource.saveOrUpdate(recetteEnCreation);
			} else {
				dataSource.update(recetteEnCreation);
			}
			//chargerListeRecette();
			this.setRecetteEnCreation(new Recette());
			FacesMessages.instance().addToControlFromResourceBundle("infoGenerique", "Evaluation ajoutée avec succés");
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public TypeRecette getTypeRecetteByCode(String codeRecette) {
		return (TypeRecette) dataSource.createQuery(" From TypeRecette where code=:pc")
				.setParameter("pc", codeRecette).uniqueResult();
	}
	public void versModifierClasse(Recette eval) {
		this.setRecetteEnCreation(eval);
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

}
