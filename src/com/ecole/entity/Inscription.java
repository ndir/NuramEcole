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
	
	private double reduction = 0d;
	
	private String commentaire;

	private double reliquat = 0d,avoirEleve =0d, montantInscriptionPaye=0d;
	
	private boolean mois1=false,mois2=false,mois3=false,mois4=false,mois5=false,mois6=false,mois7=false,mois11=false,mois12=false;
	private boolean mois10=false;
	private int nbreMoisPaye;
	
	private int moisenCours;
	
	

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

	public double getReduction() {
		return reduction;
	}

	public void setReduction(double reduction) {
		this.reduction = reduction;
	}

	public boolean getMois1() {
		return mois1;
	}

	public void setMois1(boolean mois1) {
		this.mois1 = mois1;
	}

	public boolean getMois2() {
		return mois2;
	}

	public void setMois2(boolean mois2) {
		this.mois2 = mois2;
	}

	public boolean getMois3() {
		return mois3;
	}

	public void setMois3(boolean mois3) {
		this.mois3 = mois3;
	}

	public boolean getMois4() {
		return mois4;
	}

	public void setMois4(boolean mois4) {
		this.mois4 = mois4;
	}

	public boolean getMois5() {
		return mois5;
	}

	public void setMois5(boolean mois5) {
		this.mois5 = mois5;
	}

	public boolean getMois6() {
		return mois6;
	}

	public void setMois6(boolean mois6) {
		this.mois6 = mois6;
	}

	public boolean getMois7() {
		return mois7;
	}

	public void setMois7(boolean mois7) {
		this.mois7 = mois7;
	}

	public boolean getMois11() {
		return mois11;
	}

	public void setMois11(boolean mois11) {
		this.mois11 = mois11;
	}

	public boolean getMois12() {
		return mois12;
	}

	public void setMois12(boolean mois12) {
		this.mois12 = mois12;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(avoirEleve);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((commentaire == null) ? 0 : commentaire.hashCode());
		result = prime * result + ((eleve == null) ? 0 : eleve.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (mois1 ? 1231 : 1237);
		result = prime * result + (mois10 ? 1231 : 1237);
		result = prime * result + (mois11 ? 1231 : 1237);
		result = prime * result + (mois12 ? 1231 : 1237);
		result = prime * result + (mois2 ? 1231 : 1237);
		result = prime * result + (mois3 ? 1231 : 1237);
		result = prime * result + (mois4 ? 1231 : 1237);
		result = prime * result + (mois5 ? 1231 : 1237);
		result = prime * result + (mois6 ? 1231 : 1237);
		result = prime * result + (mois7 ? 1231 : 1237);
		result = prime * result + moisenCours;
		result = prime * result + nbreMoisPaye;
		result = prime * result + ((paramins == null) ? 0 : paramins.hashCode());
		temp = Double.doubleToLongBits(reduction);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(reliquat);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		if (Double.doubleToLongBits(avoirEleve) != Double.doubleToLongBits(other.avoirEleve))
			return false;
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
		if (mois1 != other.mois1)
			return false;
		if (mois10 != other.mois10)
			return false;
		if (mois11 != other.mois11)
			return false;
		if (mois12 != other.mois12)
			return false;
		if (mois2 != other.mois2)
			return false;
		if (mois3 != other.mois3)
			return false;
		if (mois4 != other.mois4)
			return false;
		if (mois5 != other.mois5)
			return false;
		if (mois6 != other.mois6)
			return false;
		if (mois7 != other.mois7)
			return false;
		if (moisenCours != other.moisenCours)
			return false;
		if (nbreMoisPaye != other.nbreMoisPaye)
			return false;
		if (paramins == null) {
			if (other.paramins != null)
				return false;
		} else if (!paramins.equals(other.paramins))
			return false;
		if (Double.doubleToLongBits(reduction) != Double.doubleToLongBits(other.reduction))
			return false;
		if (Double.doubleToLongBits(reliquat) != Double.doubleToLongBits(other.reliquat))
			return false;
		return true;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public double getReliquat() {
		return reliquat;
	}

	public void setReliquat(double reliquat) {
		this.reliquat = reliquat;
	}

	public int getNbreMoisPaye() {
		return nbreMoisPaye;
	}

	public void setNbreMoisPaye(int nbreMoisPaye) {
		this.nbreMoisPaye = nbreMoisPaye;
	}

	public int getMoisenCours() {
		return moisenCours;
	}

	public void setMoisenCours(int moisenCours) {
		this.moisenCours = moisenCours;
	}

	public double getAvoirEleve() {
		return avoirEleve;
	}

	public void setAvoirEleve(double avoirEleve) {
		this.avoirEleve = avoirEleve;
	}

	public boolean getMois10() {
		return mois10;
	}

	public void setMois10(boolean mois10) {
		this.mois10 = mois10;
	}

	public double getMontantInscriptionPaye() {
		return montantInscriptionPaye;
	}

	public void setMontantInscriptionPaye(double montantInscriptionPaye) {
		this.montantInscriptionPaye = montantInscriptionPaye;
	}
	

}
