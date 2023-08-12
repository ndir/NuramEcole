package com.chaka.projet.service;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ValueChangeEvent;

import org.hibernate.Session;
import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;

import com.chaka.parametrage.service.ListeParam;
import com.chaka.projet.entity.DroitUserAccess;
import com.chaka.projet.entity.Fonction;
import com.chaka.projet.entity.Profile;
import com.chaka.projet.entity.Utilisateur;
import com.rhospi.commons.ChakaUtils;


/**
 * @author Serigne FALL
 *
 */
@Scope(ScopeType.SESSION)
@Name("gestionMenuService")
public class GestionMenuService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2222247012601658604L;


	/**
	 * Session hibernate permettant le dialogue avec la base de données.
	 */
	@In
	private Session dataSource;

	/**
	 * utilisateur courant.
	 */
	@In(required = false)
	private Utilisateur utilisateur; 
	private Fonction fonctionCreation = new Fonction();
	
	private List<Profile> listeProfile=new ArrayList<Profile>();
	
	private List<Fonction> listeFonction =new ArrayList<Fonction>();
	
	private Profile profil=new Profile();
	
	private Profile profilSelected=new Profile();

	private List<Fonction> listModulesSelected = new ArrayList<Fonction>();
	private List<Fonction> listModules = new ArrayList<Fonction>();
	
	private List<Fonction> listMenuOfModule = new ArrayList<Fonction>();
	
	private Fonction moduleEncours=new Fonction();
	

	
	
	@Out(required = false)
	private ListeParam listeParamStock=(ListeParam)Component.getInstance("listeParamStock");
	
	//private  ContratDistribDistrib contrat;


	/**
	 * cette methode nous redirige vers la page de creation des sens
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String versCreationMenu() {
		this.fonctionCreation=new Fonction();
		rechercheProfile();
		rechercheFonction();
		return "/pages/administration/creationModificationMenu.xhtml";
	}
	
	public void selectMenu (Fonction itfonction){
		ChakaUtils.bindeule(" valeur select = "+itfonction.getSelected());
		boolean val=itfonction.getSelected()?false:true;
		for(int i=0;i<listModulesSelected.size();i++){
			if(listModulesSelected.get(i).equals(itfonction)) {
				listModulesSelected.get(i).setSelected(val);
				break;
			}
		}
	}
	@SuppressWarnings("unchecked")
	public void rechercheFonction() {
		listeFonction=dataSource.createQuery(" from Fonction f  "
				+ " where f.parent is null "
				+ "order by f.nomFonction ").list();
	}
	@SuppressWarnings("unchecked")
	public String versGestionProfile() {
		this.profil=new Profile();
		rechercheProfile();
		return "/pages/administration/gestionprofil.xhtml";
	}
	
	
	
	@SuppressWarnings("unchecked")
	public void rechercheProfile () {
		
		try {
			listeProfile = dataSource.createQuery(
					" select distinct f from Profile f "
					//		+ " left outer join fetch f.listeDroit l"
					//	+ " left outer join fetch l.fonction "
							+ " order by f.libelle ").list();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	/**
	 * cette methode permet d'ajouter un sens
	 */
	public void ajouterFonction() {

		if (fonctionCreation.getIdFonction() == null) {
			dataSource.save(fonctionCreation);
			
			listeParamStock.actualiserListeFonction();
		} else {
			dataSource.update(fonctionCreation);
			this.fonctionCreation=new Fonction();
			listeParamStock.actualiserListeFonction();
		FacesMessages.instance().addToControlFromResourceBundle(
				"infoGenerique", "Opération Effectué avec succès");
		}
		this.fonctionCreation=new Fonction();

	}
	public void ajouterModifierProfil(){
		dataSource.save(profil);
		dataSource.flush();
		//this.listeParamStock.a
		rechercheProfile();
	}

	/**
	 * cette methode permet de modifier un sens
	 * 
	 * @param fonctionCreation
	 */
	public void initmodifier(Fonction op) {
		
		fonctionCreation = (Fonction) dataSource.get(Fonction.class, op.getIdFonction());
	}
	
   public void deactiverActiver(Fonction op) {
		try {
			fonctionCreation = (Fonction) dataSource.get(Fonction.class,op.getIdFonction());
			fonctionCreation.setActif(!fonctionCreation.getActif());
			dataSource.merge(fonctionCreation);
			dataSource.flush();
			listeParamStock.actualiserListeFonction();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * cette methode permet de supprimer un sens
	 * 
	 * @param sens
	 */
	public void supprimer(Fonction op) {
		 try {
				dataSource.delete(op);
				listeParamStock.actualiserListeFonction();
				FacesMessages.instance().addToControlFromResourceBundle(
						"infoGenerique", "Opération Effectué avec succès");
			 }catch (Throwable t) {
				   t.printStackTrace();
			 }
	}
	
   public void modifier( ) {
	   try {
			this.dataSource.merge(fonctionCreation);
			this.dataSource.flush();
			listeParamStock.actualiserListeFonction();
	   }catch (Throwable t) {
		   t.printStackTrace();
	   }
	}
   public void iniDroitAccess(){
	   listModulesSelected.clear();
	   listModulesSelected=new ArrayList<Fonction>();
	   moduleEncours=new Fonction();
	   profilSelected=new Profile();
   }
   
   public boolean contains(List<DroitUserAccess> liste ,Fonction fonction) {
	   for(DroitUserAccess itF :liste) {
		   if(itF.getFonction().getIdFonction().equals(fonction.getIdFonction()))
			   return true;
	   }
	   return false;
   }
   
   public void deleteAllDroitOfModule(Profile profile ,List<Fonction> listFonction) {
	   List<DroitUserAccess> listTemp=new ArrayList<DroitUserAccess>();
	   for(DroitUserAccess itDroit :profile.getListeDroit()) {
		   for(Fonction ifFonction :listFonction) {
			   if(itDroit.getFonction().equals(ifFonction) && ifFonction.getSelected().equals(false)) {
				   listTemp.add(itDroit);
				   break;
			   }
		   }
	   }
	   ChakaUtils.bindeule("liste a supprimer "+listTemp.size());
	   ChakaUtils.bindeule("liste avant suppression "+ profile.getListeDroit().size());
	   profile.getListeDroit().removeAll(listTemp);
	   ChakaUtils.bindeule("liste apres suppression "+ profile.getListeDroit().size());
	   
   }
   public void saveOrUpdateDroits (){
	   try {
		   boolean trouveON=false;
		   if(profilSelected==null || profilSelected.getIdProfile()==null) {
			   return ;
			}
			 if (profilSelected.getListeDroit() == null)
			            profilSelected.setListeDroit(new ArrayList<DroitUserAccess>());
			 for (int i=0; i< listModulesSelected.size();i++) {
					if (listModulesSelected.get(i).getSelected().equals(true)) {
						trouveON=true;
						DroitUserAccess droit = new DroitUserAccess();
						droit.setProfile(profilSelected);
						droit.setFonction(listModulesSelected.get(i));
						if(!contains(profilSelected.getListeDroit(),listModulesSelected.get(i)))
						    profilSelected.getListeDroit().add(droit);
					}
			}
				if(moduleEncours.getSelected().equals(true)) {
					trouveON=true;
					DroitUserAccess droit = new DroitUserAccess();
					droit.setProfile(profilSelected);
					droit.setFonction(moduleEncours);
					if(!profilSelected.getListeDroit().contains(droit))
						    profilSelected.getListeDroit().add(droit);
					
				}
				deleteAllDroitOfModule(profilSelected,listModulesSelected);
				ChakaUtils.bindeule(" longeur liste droit of profile  " + profilSelected.getListeDroit().size());
				this.dataSource.merge(profilSelected);
				this.dataSource.flush();
				rechercheProfile();
				iniDroitAccess();
				if(trouveON)
				FacesMessages.instance().addToControlFromResourceBundle(
						"infoGenerique", "Opération Effectué avec succès");
				if(!trouveON)
					FacesMessages.instance().addToControlFromResourceBundle(
							"erreurGenerique", "Veuillez séléctionner au moins un menu ");
	    } catch (Throwable e) {
		      e.printStackTrace();
	    }
   }
   
   
   public void choixMultiple(ValueChangeEvent e) {
	   Boolean val=(Boolean)e.getNewValue();
	   moduleEncours.setSelected(val);
	   for(int i =0;i<listModulesSelected.size();i++) {
		   listModulesSelected.get(i).setSelected(val);
	   }
   }
   
   @SuppressWarnings("unchecked")
public void selectProfile(Profile p) {
	   iniDroitAccess();
			try {
				List<Profile> listeProfiles = dataSource.createQuery(
						" select distinct f from Profile f "
								+ " left outer join fetch f.listeDroit l"
							    + " left outer join fetch l.fonction "
								+ " where  f.idProfile =:paramID")
								.setParameter("paramID", p.getIdProfile()).list();
				profilSelected=listeProfiles.size()!=0?listeProfiles.get(0):null;
			} catch (Throwable e) {
				e.printStackTrace();
			}
	 //  profilSelected=null;;
	  
   }
   
   public boolean estParent(Fonction menu) {
	   
	   for(int i=0;i<listeFonction.size();i++){
		   if(listeFonction.get(i).getParent()!=null && listeFonction.get(i).getParent().getIdFonction().equals(menu.getIdFonction()))
			   return true;
	   }
	   return false;
   }
   
   
   public void selectModule(Fonction currentModule){		
		if(currentModule != null){			
			listModulesSelected.clear();
			moduleEncours=currentModule;
			
			//listModulesSelected.add(currentModule);	
			//listModulesSelected.addAll(
					getListeSousMenu(currentModule);//);
			
			 ChakaUtils.bindeule(" nombre droit = "+profilSelected.getListeDroit().size()+"");
			   for(DroitUserAccess itDroit :profilSelected.getListeDroit()) {
				   if(moduleEncours.getIdFonction().equals(itDroit.getFonction().getIdFonction()))
					   moduleEncours.setSelected(true);
				   for(int i=0;i<listModulesSelected.size();i++){
					   if(listModulesSelected.get(i).getIdFonction().equals(itDroit.getFonction().getIdFonction())) {
						   listModulesSelected.get(i).setSelected(true);
						   break;
					   }
				   }
			   }
			
			
		}											
   }
   
   
   public List<Fonction> getListeSousMenu2(Fonction module) {
	   List<Fonction> listResult =new ArrayList<Fonction>();
	   for(Fonction itFonction :listeParamStock.getListeFonction()) {
		   if(itFonction.getParent()!=null && itFonction.getParent().getIdFonction().equals(module.getIdFonction()))
			   itFonction.setSelected(false);
			   listResult.add(itFonction);
	   }
	   
	   return listResult;
   }
   
   @SuppressWarnings("unchecked")
   public void getListeSousMenu(Fonction module) {
	   listModulesSelected= dataSource.createQuery("from Fonction f where f.parent =:paramParent").setParameter("paramParent", module).list();
   }
   
   /**
	 * @param listModules the listModules to set
	 */
	public void setListModules(List<Fonction> listModules) {
		this.listModules = listModules;
	}
   
   public List<Fonction> getRoots(){		    
		return listModulesSelected;		  
 }
   
  
 
	
	
  
	public Fonction getFonctionCreation() {
		return fonctionCreation;
	}
	
	
	public void setFonctionCreation(Fonction fonctionCreation) {
		this.fonctionCreation = fonctionCreation;
	}


	public List<Profile> getListeProfile() {
		return listeProfile;
	}


	public void setListeProfile(List<Profile> listeProfile) {
		this.listeProfile = listeProfile;
	}


	public Profile getProfil() {
		return profil;
	}


	public void setProfil(Profile profil) {
		this.profil = profil;
	}


	public List<Fonction> getListeFonction() {
		return listeFonction;
	}


	public void setListeFonction(List<Fonction> listeFonction) {
		this.listeFonction = listeFonction;
	}


	public Profile getProfilSelected() {
		return profilSelected;
	}


	public void setProfilSelected(Profile profilSelected) {
		this.profilSelected = profilSelected;
	}


	public List<Fonction> getListModulesSelected() {
		return listModulesSelected;
	}


	public void setListModulesSelected(List<Fonction> listModulesSelected) {
		this.listModulesSelected = listModulesSelected;
	}


	public List<Fonction> getListModules() {
		return listModules;
	}


	public Fonction getModuleEncours() {
		return moduleEncours;
	}


	public void setModuleEncours(Fonction moduleEncours) {
		this.moduleEncours = moduleEncours;
	}


	
 
}
