/**
 * @author Dell
 *
 */
package com.chaka.parametrage.entity.generaux;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.jboss.seam.annotations.Name;


/**
 * 
 * @author Souané
 *
 */
@Entity
@Name("niveauHieraEnCreation")
@Table(name="niveauHierarchique")
public class NiveauHierarchique implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3463892148915777622L;

	/**
	 * identifiant NiveauHierarchique.
	 */
	private Long idNiveauHierarchique;
	/**
	 * commentaire niveauHiera.
	 */
	private String niveauHiera;
	/**
	 * niveauniveauHiera.
	 */
	private String niveau;
	/**
	 * Date de dernière creation niveauAdministratif.
	 */
	private Date dateCreation;

	/**
	 * Date de dernière modification niveauAdministratif.
	 */
	private Date dateMaj;
	/**
	 * le siege associé.
	 */
	private Siege siege;
	/**
	 * Clé utilisée lorsque l'entité n'est pas encore persistée.
	 */
	private Long idTransient;
	
	/**
	 * methode construteur de la classe NiveauAdministratif.
	 */
	public NiveauHierarchique()
	{
		
	}


	
	
	/**
	 * @return the idNiveauAdministratif
	 */
	@Id
	@GeneratedValue
	public Long getIdNiveauHierarchique() {
		return idNiveauHierarchique;
	}




	/**
	 * @param idNiveauHierarchique the idNiveauHierarchique to set
	 */
	public void setIdNiveauHierarchique(Long idNiveauHierarchique) {
		this.idNiveauHierarchique = idNiveauHierarchique;
	}




	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((idNiveauHierarchique == null) ? 0 : idNiveauHierarchique
						.hashCode());
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
		NiveauHierarchique other = (NiveauHierarchique) obj;
		if (idNiveauHierarchique == null) {
			if (other.idNiveauHierarchique != null)
				return false;
		} else if (!idNiveauHierarchique.equals(other.idNiveauHierarchique))
			return false;
		return true;
	}




	/**
	 * @return the niveauHiera
	 */
	public String getNiveauHiera() {
		return niveauHiera;
	}




	/**
	 * @param niveauAdmin the niveauAdmin to set
	 */
	public void setNiveauHiera(String niveauAdmin) {
		this.niveauHiera = niveauAdmin;
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
	 * @return the dateMaj
	 */
	public Date getDateMaj() {
		return dateMaj;
	}


	/**
	 * @param dateMaj the dateMaj to set
	 */
	public void setDateMaj(Date dateMaj) {
		this.dateMaj = dateMaj;
	}




	/**
	 * @return the siege
	 */
	@ManyToOne
	@JoinColumn(name = "idSiege", insertable = false , updatable = false)
	public Siege getSiege() {
		return siege;
	}




	/**
	 * @param siege the siege to set
	 */
	public void setSiege(Siege siege) {
		this.siege = siege;
	}




	/**
	 * @return the idTransient
	 */
	@Transient
	public Long getIdTransient() {
		return idTransient;
	}




	/**
	 * @param idTransient the idTransient to set
	 */
	public void setIdTransient(Long idTransient) {
		this.idTransient = idTransient;
	}




	/**
	 * @return the niveau
	 */
	public String getNiveau() {
		return niveau;
	}




	/**
	 * @param niveau the niveau to set
	 */
	public void setNiveau(String niveau) {
		this.niveau = niveau;
	}
	
	
	
}
