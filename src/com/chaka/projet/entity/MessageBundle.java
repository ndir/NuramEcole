package com.chaka.projet.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.chaka.projet.entity.Utilisateur;

/**.
 * @author koutolito
 * entite StatutOperation
 */

public class MessageBundle implements Serializable{

	
	/**.
	 * id 
	 */
	private Long idMessage;
	/**.
	 * cle
	 */
	private String cle;
	/**.
	 * valeur
	 */
	private String valeur;

	/**.
	 * valeur
	 */
	private String traduction;
	/**
	 * @return the idMessage
	 */
	public Long getIdMessage() {
		return idMessage;
	}
	/**
	 * @param idMessage the idMessage to set
	 */
	public void setIdMessage(Long idMessage) {
		this.idMessage = idMessage;
	}
	/**
	 * @return the cle
	 */
	public String getCle() {
		return cle;
	}
	/**
	 * @param cle the cle to set
	 */
	public void setCle(String cle) {
		this.cle = cle;
	}
	/**
	 * @return the valeur
	 */
	public String getValeur() {
		return valeur;
	}
	/**
	 * @param valeur the valeur to set
	 */
	public void setValeur(String valeur) {
		this.valeur = valeur;
	}
	/**
	 * @return the traduction
	 */
	public String getTraduction() {
		return traduction;
	}
	/**
	 * @param traduction the traduction to set
	 */
	public void setTraduction(String traduction) {
		this.traduction = traduction;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idMessage == null) ? 0 : idMessage.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MessageBundle other = (MessageBundle) obj;
		if (idMessage == null) {
			if (other.idMessage != null)
				return false;
		} else if (!idMessage.equals(other.idMessage))
			return false;
		return true;
	}
}
