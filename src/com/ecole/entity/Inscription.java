/**
 * 
 */
package com.ecole.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
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

}
