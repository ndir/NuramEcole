package com.chaka.parametrage.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import com.chaka.parametrage.entity.*;
import com.chaka.projet.entity.Fonction;

@Scope(ScopeType.APPLICATION)
@Name("listeParamStock")
public class ListeParam implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 2774659771071458273L;
	
	
	
	private List<Fonction> listeFonction =new ArrayList<Fonction>();
	

	
	private List<Institution> listeInstitution=new ArrayList<Institution>();
	
	private List<Lst_CategoriProf> listeLst_CategoriProf =new ArrayList<Lst_CategoriProf>();
	
	private List<Lst_Filiere> listeLst_Filiere =new ArrayList<Lst_Filiere>();
	
	private List<Lst_Annee> listeLst_Annee=new ArrayList<Lst_Annee>();
	
	private List<Lst_NivEtude> listeLst_NivEtude=new ArrayList<Lst_NivEtude>();
	
	private List<Lst_TypeAyantDroit> listeLst_TypeAyantDroit=new ArrayList<Lst_TypeAyantDroit>();
	
	private List<Lst_Pathologie> listeLst_Pathologie = new ArrayList<Lst_Pathologie>();
	
	private List<Lst_Service> listeLst_Service=new ArrayList<Lst_Service>();
	
	private List<Lst_Sexe> listeLst_Sexe=new ArrayList<Lst_Sexe>();
	
	private List<Lst_SituationMatri> listeLst_SituationMatri=new ArrayList<Lst_SituationMatri>();
	
	private List<Lst_TypeAbsence> listeLst_TypeAbsence=new ArrayList<Lst_TypeAbsence>();
	
	private List<Lst_TypeContrat> listeLst_TypeContrat=new ArrayList<Lst_TypeContrat>();
	
	private List<Lst_MotifDepart> listeLst_MotifDepart=new ArrayList<Lst_MotifDepart>();
	
	private List<Lst_MotifAbsence> listeLst_MotifAbsence=new ArrayList<Lst_MotifAbsence>();
	
	private List<Lst_MotifRejet> listeLst_MotifRejet=new ArrayList<Lst_MotifRejet>();
	
	private List<Lst_StatutValidation> listeLst_StatutValidation=new ArrayList<Lst_StatutValidation>();
	
	private List<Lst_TypePiece> listeLst_TypePiece=new ArrayList<Lst_TypePiece>();
	
	private List<Lst_Statut> listeLst_Statut = new ArrayList<Lst_Statut>();
	
	private Institution institution =new Institution();
	
	
	
	
	
	


	/**
	 * Session hibernate permettant le dialogue avec la base de données.
	 */
	@In
	private Session dataSource;
	
	@Create
	public void ChargerLesListesParamStock(){
		System.out.print("#----------------------------------------- CHARGER LES LISTES PARAMETRAGES  -------------------------------#");
		
//		this.actualiserListeFonction();
//		this.actualiserListeInstitution();
//		
//		 actualiserMotifAbsence ();
//		 actualiserCategoriProf ();
//		 actualiserFiliere ();
//		 actualiserNivEtude ();
//		 actualiserAnnee ();
//		 actualiserLst_TypeAyantDroit ();
//		 actualiserLst_Pathologie();
//		 actualiserLst_Service();
//		 actualiserLst_Sexe();
//		 actualiserLst_TypeAbsence();
//		 actualiserLst_SituationMatri();
//		 actualiserLst_TypeContrat();
//		 actualiserLst_MotifDepart();
//		 actualiserLst_MotifRejet();
//		 actualiserLst_TypePiece();
//		 actualiserLst_StatutValidation();
//		 actualiserListeStatut();
		 
	}
	
	public void actualiserListeInstitution (){
		this.listeInstitution =	dataSource.createQuery(" from Institution  order by libelle ").list();
		if(listeInstitution.size()!=0) institution=listeInstitution.get(0);
	}
	
	public void actualiserListeStatut(){
		this.setListeLst_Statut(dataSource.createQuery("from Lst_Statut  order by libelle").list());
	}
	
	public void actualiserFiliere(){
		this.setListeLst_Filiere(dataSource.createQuery("from Lst_Filiere  order by libelle").list());
	}
	
	
	public void actualiserMotifAbsence (){
		this.listeLst_MotifAbsence =	dataSource.createQuery(" from Lst_MotifAbsence  order by libelle ").list();
	}
	
	public void actualiserNivEtude (){
		this.listeLst_NivEtude=	dataSource.createQuery(" from Lst_NivEtude  order by libelle ").list();
	}
	
	public void actualiserAnnee (){
		this.listeLst_Annee =	dataSource.createQuery(" from Lst_Annee  order by libelle ").list();
	}
	
	public void actualiserLst_TypeAyantDroit (){
		this.listeLst_TypeAyantDroit =	dataSource.createQuery(" from Lst_TypeAyantDroit  order by libelle ").list();
	}
	
	public void actualiserCategoriProf (){
		this.listeLst_CategoriProf =	dataSource.createQuery(" from Lst_CategoriProf  order by libelle ").list();
	}
	
	public void actualiserLst_Pathologie(){
		this.listeLst_Pathologie =	dataSource.createQuery(" from Lst_Pathologie  order by libelle ").list();
	}
	
	public void actualiserLst_Service(){
		this.listeLst_Service =	dataSource.createQuery(" from Lst_Service  order by libelle ").list();
	}
	public void actualiserLst_Sexe(){
		this.listeLst_Sexe =	dataSource.createQuery(" from Lst_Sexe  order by libelle ").list();
	}
	public void actualiserLst_TypeAbsence(){
		this.listeLst_TypeAbsence =	dataSource.createQuery(" from Lst_TypeAbsence  order by libelle ").list();
	}
	
	public void actualiserLst_SituationMatri(){
		this.listeLst_SituationMatri =	dataSource.createQuery(" from Lst_SituationMatri  order by libelle ").list();
	}
	
	public void actualiserLst_TypeContrat(){
		this.listeLst_TypeContrat =	dataSource.createQuery(" from Lst_TypeContrat  order by libelle ").list();
	}
	
	public void actualiserLst_MotifDepart(){
		this.listeLst_MotifDepart =	dataSource.createQuery(" from Lst_MotifDepart  order by libelle ").list();
	}
	
	public void actualiserLst_MotifRejet(){
		this.setListeLst_MotifRejet(dataSource.createQuery(" from Lst_MotifRejet  order by libelle ").list());
	}

	
	public void actualiserLst_MotifAbsence(){
		this.listeLst_MotifAbsence =	dataSource.createQuery(" from Lst_MotifAbsence  order by libelle ").list();
	}
	
	public void actualiserLst_StatutValidation(){
		this.listeLst_StatutValidation =	dataSource.createQuery(" from Lst_StatutValidation  order by libelle ").list();
	}
	
	public void actualiserLst_TypePiece(){
		this.listeLst_TypePiece =	dataSource.createQuery(" from Lst_TypePiece  order by libelle ").list();
	}
	
	
	
	
	
	
	public void actualiserListeFonction (){
		this.listeFonction=	dataSource.createQuery("from Fonction order by nomFonction ").list();
	}
	

	
	public List<Fonction> getListeFonction() {
		return listeFonction;
	}
	public void setListeFonction(List<Fonction> listeFonction) {
		this.listeFonction = listeFonction;
	}

	public List<Institution> getListeInstitution() {
		return listeInstitution;
	}

	public void setListeInstitution(List<Institution> listeInstitution) {
		this.listeInstitution = listeInstitution;
	}

	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	public List<Lst_CategoriProf> getListeLst_CategoriProf() {
		return listeLst_CategoriProf;
	}

	public void setListeLst_CategoriProf(
			List<Lst_CategoriProf> listeLst_CategoriProf) {
		this.listeLst_CategoriProf = listeLst_CategoriProf;
	}

	public List<Lst_Annee> getListeLst_Annee() {
		return listeLst_Annee;
	}

	public void setListeLst_Annee(List<Lst_Annee> listeLst_Annee) {
		this.listeLst_Annee = listeLst_Annee;
	}

	public List<Lst_NivEtude> getListeLst_NivEtude() {
		return listeLst_NivEtude;
	}

	public void setListeLst_NivEtude(List<Lst_NivEtude> listeLst_NivEtude) {
		this.listeLst_NivEtude = listeLst_NivEtude;
	}

	public List<Lst_TypeAyantDroit> getListeLst_TypeAyantDroit() {
		return listeLst_TypeAyantDroit;
	}

	public void setListeLst_TypeAyantDroit(
			List<Lst_TypeAyantDroit> listeLst_TypeAyantDroit) {
		this.listeLst_TypeAyantDroit = listeLst_TypeAyantDroit;
	}

	public List<Lst_Pathologie> getListeLst_Pathologie() {
		return listeLst_Pathologie;
	}

	public void setListeLst_Pathologie(List<Lst_Pathologie> listeLst_Pathologie) {
		this.listeLst_Pathologie = listeLst_Pathologie;
	}

	public List<Lst_Service> getListeLst_Service() {
		return listeLst_Service;
	}

	public void setListeLst_Service(List<Lst_Service> listeLst_Service) {
		this.listeLst_Service = listeLst_Service;
	}

	public List<Lst_Sexe> getListeLst_Sexe() {
		return listeLst_Sexe;
	}

	public void setListeLst_Sexe(List<Lst_Sexe> listeLst_Sexe) {
		this.listeLst_Sexe = listeLst_Sexe;
	}

	public List<Lst_SituationMatri> getListeLst_SituationMatri() {
		return listeLst_SituationMatri;
	}

	public void setListeLst_SituationMatri(
			List<Lst_SituationMatri> listeLst_SituationMatri) {
		this.listeLst_SituationMatri = listeLst_SituationMatri;
	}

	public List<Lst_TypeAbsence> getListeLst_TypeAbsence() {
		return listeLst_TypeAbsence;
	}

	public void setListeLst_TypeAbsence(List<Lst_TypeAbsence> listeLst_TypeAbsence) {
		this.listeLst_TypeAbsence = listeLst_TypeAbsence;
	}

	public List<Lst_TypeContrat> getListeLst_TypeContrat() {
		return listeLst_TypeContrat;
	}

	public void setListeLst_TypeContrat(List<Lst_TypeContrat> listeLst_TypeContrat) {
		this.listeLst_TypeContrat = listeLst_TypeContrat;
	}

	public List<Lst_MotifDepart> getListeLst_MotifDepart() {
		return listeLst_MotifDepart;
	}

	public void setListeLst_MotifDepart(List<Lst_MotifDepart> listeLst_MotifDepart) {
		this.listeLst_MotifDepart = listeLst_MotifDepart;
	}

	public List<Lst_MotifAbsence> getListeLst_MotifAbsence() {
		return listeLst_MotifAbsence;
	}

	public void setListeLst_MotifAbsence(
			List<Lst_MotifAbsence> listeLst_MotifAbsence) {
		this.listeLst_MotifAbsence = listeLst_MotifAbsence;
	}

	public List<Lst_StatutValidation> getListeLst_StatutValidation() {
		return listeLst_StatutValidation;
	}

	public void setListeLst_StatutValidation(
			List<Lst_StatutValidation> listeLst_StatutValidation) {
		this.listeLst_StatutValidation = listeLst_StatutValidation;
	}

	public List<Lst_TypePiece> getListeLst_TypePiece() {
		return listeLst_TypePiece;
	}

	public void setListeLst_TypePiece(List<Lst_TypePiece> listeLst_TypePiece) {
		this.listeLst_TypePiece = listeLst_TypePiece;
	}

	public List<Lst_Statut> getListeLst_Statut() {
		return listeLst_Statut;
	}

	public void setListeLst_Statut(List<Lst_Statut> listeLst_Statut) {
		this.listeLst_Statut = listeLst_Statut;
	}

	public List<Lst_Filiere> getListeLst_Filiere() {
		return listeLst_Filiere;
	}

	public void setListeLst_Filiere(List<Lst_Filiere> listeLst_Filiere) {
		this.listeLst_Filiere = listeLst_Filiere;
	}

	public List<Lst_MotifRejet> getListeLst_MotifRejet() {
		return listeLst_MotifRejet;
	}

	public void setListeLst_MotifRejet(List<Lst_MotifRejet> listeLst_MotifRejet) {
		this.listeLst_MotifRejet = listeLst_MotifRejet;
	}
	
	
	public Lst_StatutValidation getStatutValid(String code) {
		for(Lst_StatutValidation statut : listeLst_StatutValidation) {
			if(statut.getLibelle_court().equals(code))
				return statut;
		}
		return null;
	}

}
