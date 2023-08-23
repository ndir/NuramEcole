/**
 * 
 */
package com.ecole.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author A626257
 *
 */
@Entity
@Table(name = "insription")
public class Inscription  implements Serializable {

	/**
	 * inscription
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private ParamInscription paramins;
	
	private Eleve eleve;
	
	private Double reduction;
	
	private String commentaire;

	private Double reliquat;
	
	private Boolean mois1,mois2,mois3,mois4,mois5,mois6,mois7,mois8,mois9;
	
	private int nbreMoisPaye;
	
	

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idparamins")
	public ParamInscription getParamins() {
		return paramins;
	}

	public void setParamins(ParamInscription paramins) {
		this.paramins = paramins;
	}

	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ideleve")
	public Eleve getEleve() {
		return eleve;
	}

	public void setEleve(Eleve eleve) {
		this.eleve = eleve;
	}

	public Double getReduction() {
		return reduction;
	}

	public void setReduction(Double reduction) {
		this.reduction = reduction;
	}

	
	public Boolean getMois1() {
		return mois1;
	}

	public void setMois1(Boolean mois1) {
		this.mois1 = mois1;
	}

	public Boolean getMois2() {
		return mois2;
	}

	public void setMois2(Boolean mois2) {
		this.mois2 = mois2;
	}

	public Boolean getMois3() {
		return mois3;
	}

	public void setMois3(Boolean mois3) {
		this.mois3 = mois3;
	}

	public Boolean getMois4() {
		return mois4;
	}

	public void setMois4(Boolean mois4) {
		this.mois4 = mois4;
	}

	public Boolean getMois5() {
		return mois5;
	}

	public void setMois5(Boolean mois5) {
		this.mois5 = mois5;
	}

	public Boolean getMois6() {
		return mois6;
	}

	public void setMois6(Boolean mois6) {
		this.mois6 = mois6;
	}

	public Boolean getMois7() {
		return mois7;
	}

	public void setMois7(Boolean mois7) {
		this.mois7 = mois7;
	}

	public Boolean getMois8() {
		return mois8;
	}

	public void setMois8(Boolean mois8) {
		this.mois8 = mois8;
	}

	public Boolean getMois9() {
		return mois9;
	}

	public void setMois9(Boolean mois9) {
		this.mois9 = mois9;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((commentaire == null) ? 0 : commentaire.hashCode());
		result = prime * result + ((eleve == null) ? 0 : eleve.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((paramins == null) ? 0 : paramins.hashCode());
		result = prime * result + ((reduction == null) ? 0 : reduction.hashCode());
		result = prime * result + ((reliquat == null) ? 0 : reliquat.hashCode());
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
		Inscription other = (Inscription) obj;
		if (commentaire == null) {
			if (other.commentaire != null)
				return false;
		} else if (!commentaire.equals(other.commentaire))
			return false;
		if (eleve == null) {
			if (other.eleve != null)
				return false;
		} else if (!eleve.equals(other.eleve))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (paramins == null) {
			if (other.paramins != null)
				return false;
		} else if (!paramins.equals(other.paramins))
			return false;
		if (reduction == null) {
			if (other.reduction != null)
				return false;
		} else if (!reduction.equals(other.reduction))
			return false;
		if (reliquat == null) {
			if (other.reliquat != null)
				return false;
		} else if (!reliquat.equals(other.reliquat))
			return false;
		return true;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public Double getReliquat() {
		return reliquat;
	}

	public void setReliquat(Double reliquat) {
		this.reliquat = reliquat;
	}

	public int getNbreMoisPaye() {
		return nbreMoisPaye;
	}

	public void setNbreMoisPaye(int nbreMoisPaye) {
		this.nbreMoisPaye = nbreMoisPaye;
	}

}
