/**
 * 
 */
package com.ecole.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Role;
import org.jboss.seam.annotations.Scope;

/**
 * @author a626257
 *
 */
@Entity
@Table(name="annee")
@Name("annee")
@Scope(ScopeType.SESSION)
@Role(name = "rannee", scope = ScopeType.CONVERSATION)
public class AnneeScolaire implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long idannee;
	
	private String anneeScolaire;

	@Id
	@GeneratedValue
	public Long getIdannee() {
		return idannee;
	}

	public void setIdannee(Long idannee) {
		this.idannee = idannee;
	}

	public String getAnneeScolaire() {
		return anneeScolaire;
	}

	public void setAnneeScolaire(String anneeScolaire) {
		this.anneeScolaire = anneeScolaire;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idannee == null) ? 0 : idannee.hashCode());
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
		AnneeScolaire other = (AnneeScolaire) obj;
		if (idannee == null) {
			if (other.idannee != null)
				return false;
		} else if (!idannee.equals(other.idannee))
			return false;
		return true;
	}
	
	
}
