package com.chaka.projet.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 
 * @author souané
 *
 */
@Entity
@Table(name = "histo_connexion")

public class HistoConnectionUser {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2469249364076337956L;
	/**
	 * identifiant.
	 */
	private Long idHisto ;
	/**
	 * user concerné.
	 */
	private Utilisateur utilisateur;
	/**
	 * date connexion.
	 */
	private Date dateConnexion ;
	/**
	 * date deconnexion.
	 */
	private Date dateDeconnexion ;
	/**
	 * durée connexion.
	 */
	private int dureeConnexion;
	/**
	 * durée connexion.
	 */
	private String connectionTime;
	/**
	 * @return the idHisto
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "seq_historique")  
	@SequenceGenerator(name="seq_historique", sequenceName = "seq_historique", allocationSize=1)
	public Long getIdHisto() {
		return idHisto;
	}
	/**
	 * @param idHisto the idHisto to set
	 */
	public void setIdHisto(Long idHisto) {
		this.idHisto = idHisto;
	}
	/**
	 * @return the utilisateur
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idUtilisateur", nullable = false)
	public Utilisateur getUtilisateur() {
		return utilisateur;
	}
	/**
	 * @param utilisateur the utilisateur to set
	 */
	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}
	/**
	 * @return the dateConnexion
	 */
	public Date getDateConnexion() {
		return dateConnexion;
	}
	/**
	 * @param dateConnexion the dateConnexion to set
	 */
	public void setDateConnexion(Date dateConnexion) {
		this.dateConnexion = dateConnexion;
	}
	/**
	 * @return the dateDeconnexion
	 */
	public Date getDateDeconnexion() {
		return dateDeconnexion;
	}
	/**
	 * @param dateDeconnexion the dateDeconnexion to set
	 */
	public void setDateDeconnexion(Date dateDeconnexion) {
		this.dateDeconnexion = dateDeconnexion;
	}
	/**
	 * @return the dureeConnexion
	 */
	public int getDureeConnexion() {
		return dureeConnexion;
	}
	/**
	 * @param dureeConnexion the dureeConnexion to set
	 */
	public void setDureeConnexion(int dureeConnexion) {
		this.dureeConnexion = dureeConnexion;
	}
	/**
	 * @return the connectionTime
	 */
	public String getConnectionTime() {
		return connectionTime;
	}
	/**
	 * @param connectionTime the connectionTime to set
	 */
	public void setConnectionTime(String connectionTime) {
		this.connectionTime = connectionTime;
	}
}
