/**
 * 
 */
package com.chaka.projet.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.ajax4jsf.component.html.HtmlAjaxCommandLink;
import org.hibernate.Session;
import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.security.Identity;

import com.chaka.parametrage.service.ListeParam;
import com.chaka.projet.entity.DroitUserAccess;
import com.chaka.projet.entity.Fonction;
import com.chaka.projet.entity.Utilisateur;
import com.rhospi.commons.ChakaUtils;

/**
 * @author Dramé
 *
 */
@Name(value = "fMenu")
@Scope(ScopeType.SESSION)
public class FMenu implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@In
	private Session dataSource;
	
	@In(required = false)
	@Out(required = false)
	private Utilisateur utilisateur;
	
	List<DroitUserAccess> listDroitsUser = null;
	
	final String prefixExcept = "except";
	
//	private Institution currentInstitution;
		
	
	List<Fonction> listFonctionsStored = null;
	
	@Out(required = false)
	private ListeParam listeParamStock=(ListeParam)Component.getInstance("listeParamStock");
	
	/**
	 * 
	 */
	public FMenu() {
		// TODO Auto-generated constructor stub
	}
	
	@SuppressWarnings("unchecked")
	private void chargerListFonctions(){
		StringBuilder hqlQuery = new StringBuilder();
		hqlQuery.append("from Fonction ");	
		listFonctionsStored = dataSource.createQuery(hqlQuery.toString()).list();		
	}
	
	@SuppressWarnings("unchecked")
	public boolean init(){		
		//if(currentInstitution == null){
		//	currentInstitution = (Institution)dataSource.createQuery("from Institution").uniqueResult();
	//	}
		if(listDroitsUser == null){
			StringBuilder hqlQuery = new StringBuilder();
			hqlQuery.append("select distinct d from DroitUserAccess d");
			hqlQuery.append(" inner join fetch d.fonction");			
			hqlQuery.append(" where d.profile = :paramProfile ");
			listDroitsUser = dataSource.createQuery(hqlQuery.toString())                //"select d from DroitUserAccess d where d.user.idUtilisateur = :user"
			                 .setParameter("paramProfile",utilisateur.getProfile()).list();
		}	
	//	ChakaUtils.bindeule("long droit d accee = "+listDroitsUser.size()+"");
		UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
		UIComponent component = viewRoot.findComponent(":frmMenuPrincipalHeader:idContentMenu");				
		changeStatutMenu(component, listDroitsUser);		
		if(listeParamStock.getInstitution().getIdInstitution()!=null && listeParamStock.getInstitution().getRecreatMenu()){
			if(listFonctionsStored == null) chargerListFonctions();
			miseAJourMenus(component);
			listeParamStock.getInstitution().setRecreatMenu(false);
			dataSource.update(listeParamStock.getInstitution());
			dataSource.flush();
			listeParamStock.actualiserListeInstitution();
		}
		return true;
	}
		
	private String getId(String chaine,boolean isParent){
		String [] result = null;
		if(chaine.contains("_"))
			result = chaine.split("_");
		if(result == null)return null;
		if(isParent){
			if(result.length==2) return null;
			else return chaine.substring(chaine.indexOf('_')+1,chaine.lastIndexOf('_'));
		}
		else return chaine.substring(chaine.indexOf('_')+1,chaine.length());
	}
	
	private Fonction findElement(List<Fonction> listFonctions,String ligFonction){
		for(Fonction f :listFonctions){
			if(f.getLigFonction().equals(ligFonction))
				return f;
		}
		return null;
	}
	
	private void createFonctionIfNotExist(HtmlAjaxCommandLink link){
		String chaine = link.getId();
		if(chaine!=null){			
			String id = getId(chaine, false);
			if(findElement(listFonctionsStored, id) == null)
			{
				String idParent = getId(chaine, true);			
				String libelle = link.getValue() != null ? link.getValue().toString() : "Indéfinie";		
				Fonction f = new Fonction();
				f.setNomFonction(libelle);		
				f.setLigFonction(id);
				f.setActif(true);
				//ChakaUtils.println("idParent "+idParent+ " id "+id+ "  libelle "+ libelle);
				Fonction parent = findElement(listFonctionsStored,idParent);
				f.setParent(parent);							
				dataSource.save(f);
				dataSource.flush();
				listFonctionsStored.add(f);
			}				
		}
	}

public void miseAJourMenus(UIComponent component){		
		
		if(component!=null && component instanceof HtmlAjaxCommandLink){			
			HtmlAjaxCommandLink link = (HtmlAjaxCommandLink) component ;			
			createFonctionIfNotExist(link);
		}
		if(component!=null)
			for(UIComponent fils:component.getChildren()){
				miseAJourMenus(fils);
		}
	}

	public void changeStatutMenu(UIComponent component, List<DroitUserAccess> listDroits){		
		
		if(component!=null && component instanceof HtmlAjaxCommandLink){			
			HtmlAjaxCommandLink link = (HtmlAjaxCommandLink) component ;
			String id = link.getId();					
		//	ChakaUtils.bindeule("chaine coupe id "+id.substring(2));
			
			if((listeParamStock.getInstitution().getDateMaj()!=null && ChakaUtils.addNdays(listeParamStock.getInstitution().getDateMaj(), 30).before(new Date())) ||
					(listeParamStock.getInstitution().getDateMaj()!=null && listeParamStock.getInstitution().getDateModification()!=null && listeParamStock.getInstitution().getDateMaj().after(listeParamStock.getInstitution().getDateModification())))
			{
				link.setRendered(false);
				ChakaUtils.bindeule("expired ............");
				return;
			}
			
			if((id.startsWith(prefixExcept) && Identity.instance().hasRole("admin"))
			//		|| !id.substring(2).contains("_")
			){ 
				link.setStatus("waitStatus");
			}			  
			else if (okDroit(getId(id, false), listDroits)  ){		
			//	ChakaUtils.bindeule("lig fonction trouver "+id.trim());
				link.setStatus("waitStatus");
			}
			else {				
				if(listeParamStock.getInstitution().getIdInstitution()!=null && !listeParamStock.getInstitution().getCacherMenuON()){
					link.setDisabled(true);
					link.setStyle("color:#B7AEAE;background:#F0F0F0;border-color: #B7AEAE");
				}
				else 
					if(listeParamStock.getInstitution().getIdInstitution()!=null && listeParamStock.getInstitution().getCacherMenuON()){
						link.setRendered(false);
					}
			}							
		}
		if(component!=null)
			for(UIComponent fils:component.getChildren()){
				changeStatutMenu(fils, listDroits);
			}
	}
	
	private boolean okDroit(String ligFonction,List<DroitUserAccess> listDroits){
		if(ligFonction == null)return false;
		for(DroitUserAccess droit : listDroits){
			//ChakaUtils.bindeule("lig fonction "+droit.getFonction().getLigFonction()+" lig fonction = "+ligFonction.trim());
			if(droit.getFonction().getLigFonction().trim().equals(ligFonction.trim()))
				return true;
		}
		return false;
	}
	
	
		
}
