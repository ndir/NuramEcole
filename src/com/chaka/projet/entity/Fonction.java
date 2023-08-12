/**
 * 
 */
package com.chaka.projet.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

/**
 * @author Serigne FALL
 *
 */
@Entity
public class Fonction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6756474021512212761L;
	private Long idFonction;
	private String numFonction;
	private String ligFonction;
	private Boolean actif;
	private String nomFonction;
	//private String action;
	//private String type;
	//private String rang ;
	//private String ligFonctionPrt;
	//private String numFonctionPrt;
	private List <Fonction> listChildren = new ArrayList<Fonction>();
	private Fonction parent;
	private Boolean activedSelect = false;
	private String reference;
	private Boolean selected = false;
	private Boolean canModifyBfV = false;
	private Boolean canInsert = false;
	private Boolean canDelete = false;
	private Boolean canPrint = false;
	private Boolean canModifyAfV = false;
	private Boolean canValider = false;
//	private EntiteApps entite;		
	private Boolean dejaAttribuee = false;
	private Boolean cochezTout=false;
	/**
	 * @return the idFonction
	 */
	@Id
	@GeneratedValue 
	public Long getIdFonction() {
		return idFonction;
	}
	/**
	 * @param idFonction the idFonction to set
	 */
	public void setIdFonction(Long idFonction) {
		this.idFonction = idFonction;
	}
	/**
	 * @return the numFonction
	 */
	public String getNumFonction() {
		return numFonction;
	}
	/**
	 * @param numFonction the numFonction to set
	 */
	public void setNumFonction(String numFonction) {
		this.numFonction = numFonction;
	}
	/**
	 * @return the ligFonction
	 */
	public String getLigFonction() {
		return ligFonction;
	}
	/**
	 * @param ligFonction the ligFonction to set
	 */
	public void setLigFonction(String ligFonction) {
		this.ligFonction = ligFonction;
	}
	/**
	 * @return the actif
	 */
	public Boolean getActif() {
		return actif;
	}
	/**
	 * @param actif the actif to set
	 */
	public void setActif(Boolean actif) {
		this.actif = actif;
	}
	/**
	 * @return the nomFonction
	 */
	public String getNomFonction() {
		return nomFonction;
	}
	/**
	 * @param nomFonction the nomFonction to set
	 */
	public void setNomFonction(String nomFonction) {
		this.nomFonction = nomFonction;
	}
	/**
	 * @return the action
	 *//*
	public String getAction() {
		return action;
	}
	*//**
	 * @param action the action to set
	 *//*
	public void setAction(String action) {
		this.action = action;
	}
	*//**
	 * @return the type
	 *//*
	public String getType() {
		return type;
	}
	*//**
	 * @param type the type to set
	 *//*
	public void setType(String type) {
		this.type = type;
	}
	*//**
	 * @return the rang
	 *//*
	public String getRang() {
		return rang;
	}
	*//**
	 * @param rang the rang to set
	 *//*
	public void setRang(String rang) {
		this.rang = rang;
	}*/
	/**
	 * @return the listChildren
	 */
	@Transient
	public List<Fonction> getListChildren() {
		return listChildren;
	}
	/**
	 * @param listChildren the listChildren to set
	 */
	public void setListChildren(List<Fonction> listChildren) {
		this.listChildren = listChildren;
	}
	/**
	 * @return the parent
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idParent")
	public Fonction getParent() {
		return parent;
	}
	/**
	 * @param parent the parent to set
	 */
	public void setParent(Fonction parent) {
		this.parent = parent;
	}
	/**
	 * @return the activedSelect
	 */
	@Transient
	public Boolean getActivedSelect() {
		return activedSelect;
	}
	/**
	 * @param activedSelect the activedSelect to set
	 */
	public void setActivedSelect(Boolean activedSelect) {
		this.activedSelect = activedSelect;
	}
	/**
	 * @return the reference
	 */
	@Transient
	public String getReference() {
		return reference;
	}
	/**
	 * @param reference the reference to set
	 */
	public void setReference(String reference) {
		this.reference = reference;
	}
	/**
	 * @return the selected
	 */
	@Transient
	public Boolean getSelected() {
		return selected;
	}
	/**
	 * @param selected the selected to set
	 */
	public void setSelected(Boolean selected) {
		this.selected = selected;
	}
	/**
	 * @return the canModifyBfV
	 */
	@Transient
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
	 * @return the canInsert
	 */
	@Transient
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
	@Transient
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
	@Transient
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
	 * @return the canModifyAfV
	 */
	@Transient
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
	 * @return the canValider
	 */
	@Transient
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
	 * @return the dejaAttribuee
	 */
	@Transient
	public Boolean getDejaAttribuee() {
		return dejaAttribuee;
	}
	/**
	 * @param dejaAttribuee the dejaAttribuee to set
	 */
	public void setDejaAttribuee(Boolean dejaAttribuee) {
		this.dejaAttribuee = dejaAttribuee;
	}
	/**
	 * @return the cochezTout
	 */
	@Transient
	public Boolean getCochezTout() {
		return cochezTout;
	}
	/**
	 * @param cochezTout the cochezTout to set
	 */
	public void setCochezTout(Boolean cochezTout) {
		this.cochezTout = cochezTout;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idFonction == null) ? 0 : idFonction.hashCode());
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
		Fonction other = (Fonction) obj;
		if (idFonction == null) {
			if (other.idFonction != null)
				return false;
		} else if (!idFonction.equals(other.idFonction))
			return false;
		return true;
	}
	
	
}
