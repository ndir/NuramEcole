/**
 * 
 */
package com.chaka.projet.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author Dramé
 *
 */
@Entity
public class DroitUserAccess implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8874530187978894416L;

	/**
	 * 
	 */
	private Long idDroitUsr ;
	private Fonction fonction;
	private Profile profile ;
	//private Boolean canModify = false;
	private Boolean canInsert = false;
	private Boolean canDelete = false;
	private Boolean canPrint = false;
	private Boolean canValider = false;
	private Boolean canModifyAfV =false;
	private Boolean canModifyBfV= false;
	private Date dateEcheance = null;
	
	public DroitUserAccess() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the idDroitUsr
	 */
	@Id
	@GeneratedValue
	public Long getIdDroitUsr() {
		return idDroitUsr;
	}

	/**
	 * @param idDroitUsr the idDroitUsr to set
	 */
	public void setIdDroitUsr(Long idDroitUsr) {
		this.idDroitUsr = idDroitUsr;
	}

	/**
	 * @return the fonction
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "idFonction")
	public Fonction getFonction() {
		return fonction;
	}

	/**
	 * @param fonction the fonction to set
	 */
	public void setFonction(Fonction fonction) {
		this.fonction = fonction;
	}

	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "idProfile")
	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	/**
	 * @return the canInsert
	 */
	public Boolean getCanInsert() {
		return canInsert;
	}

	/**
	 * @param canInsert the canInsert to set
	 */
	public void setCanInsert(Boolean canInsert) {
		this.canInsert = canInsert;
	}

	/**
	 * @return the canDelete
	 */
	public Boolean getCanDelete() {
		return canDelete;
	}

	/**
	 * @param canDelete the canDelete to set
	 */
	public void setCanDelete(Boolean canDelete) {
		this.canDelete = canDelete;
	}

	/**
	 * @return the canPrint
	 */
	public Boolean getCanPrint() {
		return canPrint;
	}

	/**
	 * @param canPrint the canPrint to set
	 */
	public void setCanPrint(Boolean canPrint) {
		this.canPrint = canPrint;
	}

	/**
	 * @return the canValider
	 */
	public Boolean getCanValider() {
		return canValider;
	}

	/**
	 * @param canValider the canValider to set
	 */
	public void setCanValider(Boolean canValider) {
		this.canValider = canValider;
	}

	/**
	 * @return the canModifyAfV
	 */
	public Boolean getCanModifyAfV() {
		return canModifyAfV;
	}

	/**
	 * @param canModifyAfV the canModifyAfV to set
	 */
	public void setCanModifyAfV(Boolean canModifyAfV) {
		this.canModifyAfV = canModifyAfV;
	}

	/**
	 * @return the canModifyBfV
	 */
	public Boolean getCanModifyBfV() {
		return canModifyBfV;
	}

	/**
	 * @param canModifyBfV the canModifyBfV to set
	 */
	public void setCanModifyBfV(Boolean canModifyBfV) {
		this.canModifyBfV = canModifyBfV;
	}

	/**
	 * @return the dateEcheance
	 */
	@Temporal(TemporalType.DATE)
	public Date getDateEcheance() {
		return dateEcheance;
	}

	/**
	 * @param dateEcheance the dateEcheance to set
	 */
	public void setDateEcheance(Date dateEcheance) {
		this.dateEcheance = dateEcheance;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idDroitUsr == null) ? 0 : idDroitUsr.hashCode());
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
		DroitUserAccess other = (DroitUserAccess) obj;
		if (idDroitUsr == null) {
			if (other.idDroitUsr != null)
				return false;
		} else if (!idDroitUsr.equals(other.idDroitUsr))
			return false;
		return true;
	}
	
	

	
}
