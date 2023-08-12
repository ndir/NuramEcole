/**
 * @author Dell
 *
 */
package com.chaka.discution.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.chaka.projet.entity.Utilisateur;




/**
 * 
 * @author Serigne FALL
 *
 */
@Entity
@Table(name="message")
public class Message implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3463892148915777622L;

	/**
	 * identifiant .
	 */
	private Long idMessage;
	
	private String objet ;
	private String text ;
	
	private Utilisateur emeteur;
	
	private Utilisateur destinataire ;
	
	private Date dateEnvoie ;
	
    private Boolean vueON=false;
    
	private List<Utilisateur> listCcUtilisateur=new ArrayList<Utilisateur>();
	
	@Id
	@GeneratedValue
	public Long getIdMessage() {
		return idMessage;
	}

	public void setIdMessage(Long idMessage) {
		this.idMessage = idMessage;
	}

	public String getObjet() {
		return objet;
	}

	public void setObjet(String objet) {
		this.objet = objet;
	}
	 @Lob
	public String getText() {
			return text;
	}

	public void setText(String text) {
			this.text = text;
	}

		
		
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idEmeteur" )
	public Utilisateur getEmeteur() {
		return emeteur;
	}

	public void setEmeteur(Utilisateur emeteur) {
		this.emeteur = emeteur;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idDestinataire" )
	public Utilisateur getDestinataire() {
		return destinataire;
	}

	public void setDestinataire(Utilisateur destinataire) {
		this.destinataire = destinataire;
	}

	public Date getDateEnvoie() {
		return dateEnvoie;
	}

	public void setDateEnvoie(Date dateEnvoie) {
		this.dateEnvoie = dateEnvoie;
	}
   @Transient
	public List<Utilisateur> getListCcUtilisateur() {
		return listCcUtilisateur;
	}

	public void setListCcUtilisateur(List<Utilisateur> listCcUtilisateur) {
		this.listCcUtilisateur = listCcUtilisateur;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idMessage == null) ? 0 : idMessage.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Message other = (Message) obj;
		if (idMessage == null) {
			if (other.idMessage != null)
				return false;
		} else if (!idMessage.equals(other.idMessage))
			return false;
		return true;
	}

	public Boolean getVueON() {
		return vueON;
	}

	public void setVueON(Boolean vueON) {
		this.vueON = vueON;
	}
  
	
	
	
	
	

	
	
	
		
}
