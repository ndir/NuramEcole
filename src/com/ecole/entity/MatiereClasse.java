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
 * @author a626257
 *
 */
@Entity
@Table(name="matiereclasse")
public class MatiereClasse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long idmatclasse;
	
	private Matiere matiere;
	
	private Classe classe;
	
	private int coef;
	
	private String annee_scol;
	
	private Evaluation eval;

	@Id
	@GeneratedValue
	public Long getIdmatclasse() {
		return idmatclasse;
	}

	public void setIdmatclasse(Long idmatclasse) {
		this.idmatclasse = idmatclasse;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idmatiere")
	public Matiere getMatiere() {
		return matiere;
	}

	public void setMatiere(Matiere matiere) {
		this.matiere = matiere;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idclasse")
	public Classe getClasse() {
		return classe;
	}

	public void setClasse(Classe classe) {
		this.classe = classe;
	}

	public int getCoef() {
		return coef;
	}

	public void setCoef(int coef) {
		this.coef = coef;
	}

	public String getAnnee_scol() {
		return annee_scol;
	}

	public void setAnnee_scol(String annee_scol) {
		this.annee_scol = annee_scol;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idmatclasse == null) ? 0 : idmatclasse.hashCode());
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
		MatiereClasse other = (MatiereClasse) obj;
		if (idmatclasse == null) {
			if (other.idmatclasse != null)
				return false;
		} else if (!idmatclasse.equals(other.idmatclasse))
			return false;
		return true;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ideval")
	public Evaluation getEval() {
		return eval;
	}

	public void setEval(Evaluation eval) {
		this.eval = eval;
	}
	

}
