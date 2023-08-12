/**
 * 
 */
package com.chaka.projet.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

/**
 * @author Dramé
 *
 */
@Entity
//@Table(name="lnk_droit")
public class Droit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8770731898890496374L;

	private Long idDroit ;
	private Fonction fonction;
	private Profile profile ;
	
	private Boolean canInsert = false;
	private Boolean canDelete = false;
	private Boolean canPrint = false;
	private Boolean canValider = false;
	private Boolean canModifyAfV =false;
	private Boolean canModifyBfV= false;
	
	
	
	/**
	 * 
	 */
	public Droit() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @return the idDroit
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "seq_droit")  
	@SequenceGenerator(name="seq_droit", sequenceName = "seq_droit", allocationSize=1)
	public Long getIdDroit() {
		return idDroit;
	}
	/**
	 * @param idDroit the idDroit to set
	 */
	public void setIdDroit(Long idDroit) {
		this.idDroit = idDroit;
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
	/**
	 * @return the profile
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "idProfile")
	public Profile getProfile() {
		return profile;
	}
	/**
	 * @param profile the profile to set
	 */
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
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idDroit == null) ? 0 : idDroit.hashCode());
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
		Droit other = (Droit) obj;
		if (idDroit == null) {
			if (other.idDroit != null)
				return false;
		} else if (!idDroit.equals(other.idDroit))
			return false;
		return true;
	}
	
	
}
