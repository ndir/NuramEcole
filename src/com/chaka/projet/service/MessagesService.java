package com.chaka.projet.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Map.Entry;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.hibernate.Session;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;

import com.chaka.projet.entity.MessageBundle;
import com.chaka.projet.entity.Utilisateur;


/**
 * @author owatt
 *
 */
@Scope(ScopeType.SESSION)
@Name("messagesService")
public class MessagesService implements Serializable {

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
	
    List<MessageBundle> listMessage =new ArrayList<MessageBundle>();

    private MessageBundle messageBundle ;

    
    public void initCreation(){
    	this.messageBundle = new MessageBundle();
    }
	
	/**
	 * Constructeur.
	 */
	public MessagesService() {
		super();
	}
	
	 public static Properties load(FileInputStream input) throws IOException, FileNotFoundException{
	      Properties properties = new Properties();

	      try{
	         properties.load(input);
	         return properties;
	      }
	              finally{
	         input.close();
	      }
	   }
	
	
	 public void chargerMessages(){
		    Properties prop;
		    Properties propEn;
			try {

				
				ExternalContext ec =	FacesContext.getCurrentInstance().getExternalContext();
	 			ServletContext sc = (ServletContext)ec.getContext();
	 			String chemin =  sc.getRealPath("/WEB-INF/classes");

				FileInputStream lifein = new FileInputStream(chemin+"/messages_fr.properties");
				prop = this.load(lifein);

				FileInputStream lifeinEn =  new FileInputStream(chemin+"/messages_en.properties");
				propEn = this.load(lifeinEn);

				listMessage = new ArrayList<MessageBundle>();
			    for(Entry<Object,Object> result:prop.entrySet()){
			    	MessageBundle messageBundle = new MessageBundle();
			    	messageBundle.setCle(result.getKey().toString());
			    	messageBundle.setValeur(result.getValue().toString());
			    	messageBundle.setTraduction(propEn.getProperty(messageBundle.getCle(),""));
				    listMessage.add(messageBundle);
			    }
	 			lifein.close();
	 			lifeinEn.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		    

	 }


	 /** Vers modification message .
	  * @return  */
	 public String versMessagesBundles() {
		 this.initCreation();
		 this.chargerMessages();
	        return "/pages/administration/paramMsg/paramMessages.xhtml";
	    }
	 
	 /** Initialisation modification message.
	  * @param messageBundle */
     public void consulterMessage(MessageBundle messageBundle){
    	 
    	 this.messageBundle = messageBundle;
     }
	
          
     /** Creation modification message .*/
     public void creerModifierMessage(){
    	 
		    Properties propFr;
		    Properties propEn;
			try {

				ExternalContext ec =	FacesContext.getCurrentInstance().getExternalContext();
	 			ServletContext sc = (ServletContext)ec.getContext();
	 			String chemin =  sc.getRealPath("/WEB-INF/classes");
	 			
				FileInputStream lifeinFr =  new FileInputStream(chemin+"/messages_fr.properties");
				propFr = this.load(lifeinFr);

				propFr.setProperty(messageBundle.getCle(),messageBundle.getValeur());
				FileOutputStream lifesetFr = new FileOutputStream(chemin+"/messages_fr.properties");
				propFr.store(lifesetFr,null);
				lifeinFr.close();
				lifesetFr.close();

				FileInputStream lifeinEn =  new FileInputStream(chemin+"/messages_en.properties");
				propEn = this.load(lifeinEn);
				propEn.setProperty(messageBundle.getCle(),messageBundle.getTraduction());

				FileOutputStream lifesetEn = new FileOutputStream(chemin+"/messages_en.properties");
				propEn.store(lifesetEn,null);
				lifeinEn.close();
				lifesetEn.close();

				FacesMessages.instance().addToControlFromResourceBundle("infoGenerique", "msg_message.creation.ok",messageBundle.getCle());
				this.initCreation();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique", "msg.creation.erreur");
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique", "msg.creation.erreur");
				e.printStackTrace();
			}
    	 this.chargerMessages();
     }
     
	 /**
		 * @return the listMessage
		 */
		public List<MessageBundle> getListMessage() {
			return listMessage;
		}

		/**
		 * @param listMessage the listMessage to set
		 */
		public void setListMessage(List<MessageBundle> listMessage) {
			this.listMessage = listMessage;
		}

		/**
		 * @return the messageBundle
		 */
		public MessageBundle getMessageBundle() {
			return messageBundle;
		}

		/**
		 * @param messageBundle the messageBundle to set
		 */
		public void setMessageBundle(MessageBundle messageBundle) {
			this.messageBundle = messageBundle;
		}
	
}
