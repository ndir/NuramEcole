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
@Name("niveauAdminEnCreation")
@Table(name="niveauAdministratif")
public class NiveauAdministratif implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3463892148915777622L;

	/**
	 * identifiant Taxes.
	 */
	private Long idNiveauAdministratif;
	/**
	 * commentaire NievauAdministratif.
	 */
	private String niveauAdmin;
	/**
	 * commentaire NievauAdministratif.
	 */
	private String niveau;
	/**
	/**
	 * Date de dernière creation NievauAdministratif.
	 */
	private Date dateCreation;

	/**
	 * Date de dernière modification NievauAdministratif.
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
	public NiveauAdministratif()
	{
		
	}


	
	/**
	 * @return the idNiveauAdministratif
	 */
	@Id
	@GeneratedValue
	public Long getIdNiveauAdministratif() {
		return idNiveauAdministratif;
	}



	/**
	 * @param idNiveauAdministratif the idNiveauAdministratif to set
	 */
	public void setIdNiveauAdministratif(Long idNiveauAdministratif) {
		this.idNiveauAdministratif = idNiveauAdministratif;
	}



	/**
	 * @return the dateCreation
	 */
	public Date getDateCreation() {
		return dateCreation;
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
				+ ((idNiveauAdministratif == null) ? 0 : idNiveauAdministratif
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
		NiveauAdministratif other = (NiveauAdministratif) obj;
		if (idNiveauAdministratif == null) {
			if (other.idNiveauAdministratif != null)
				return false;
		} else if (!idNiveauAdministratif.equals(other.idNiveauAdministratif))
			return false;
		return true;
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
	 * @return the niveauAdmin
	 */
	public String getNiveauAdmin() {
		return niveauAdmin;
	}



	/**
	 * @param niveauAdmin the niveauAdmin to set
	 */
	public void setNiveauAdmin(String niveauAdmin) {
		this.niveauAdmin = niveauAdmin;
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
