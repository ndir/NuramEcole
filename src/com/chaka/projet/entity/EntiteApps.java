/**
 * 
 */
package com.chaka.projet.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

/**
 * @author Dramé
 *
 */
@Entity
public class EntiteApps implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5768081131650846552L;

	/** Clé primaire .*/
	private Long idEntite;

	/** Libelle .*/
	private String libelle;
	
	/** le nom de la classe complet .*/
	private String target;

	/** date creation .*/
	private Date dateCreation;
	
	/** date modification .*/
	private Date dateModification;

	/**
	 * @return the idEntite
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "seq_entite")  
	@SequenceGenerator(name="seq_entite", sequenceName = "seq_entite", allocationSize=1)
	public Long getIdEntite() {
		return idEntite;
	}

	/**
	 * @param idEntite the idEntite to set
	 */
	public void setIdEntite(Long idEntite) {
		this.idEntite = idEntite;
	}

	/**
	 * @return the libelle
	 */
	public String getLibelle() {
		return libelle;
	}

	/**
	 * @param libelle the libelle to set
	 */
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	/**
	 * @return the target
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * @param target the target to set
	 */
	public void setTarget(String target) {
		this.target = target;
	}

	/**
	 * @return the dateCreation
	 */
	public Date getDateCreation() {
		return dateCreation;
	}

	/**
	 * @param dateCreation the dateCreation to set
	 */
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	/**
	 * @return the dateModification
	 */
	public Date getDateModification() {
		return dateModification;
	}

	/**
	 * @param dateModification the dateModification to set
	 */
	public void setDateModification(Date dateModification) {
		this.dateModification = dateModification;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idEntite == null) ? 0 : idEntite.hashCode());
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
		EntiteApps other = (EntiteApps) obj;
		if (idEntite == null) {
			if (other.idEntite != null)
				return false;
		} else if (!idEntite.equals(other.idEntite))
			return false;
		return true;
	}
	
}
