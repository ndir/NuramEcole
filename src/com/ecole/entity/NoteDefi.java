package com.ecole.entity;

import java.io.InputStream;
import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "notedefi")
public class NoteDefi implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idNote;
	private Eleve eleve;
	private Matiere matiere;
	private Classe cl;
	private float note;
	private AnneeScolaire annee;
	private int coef;
	private String ecole;
	private String tel;
	private String slogan;
	private String eff;
	private String ap;
	private String moy;
	
	private Double total;
	private InputStream logo;
	private float note1;
	private float note2;
	private float note3;
	private int coef1;
	private int coef2;
	private int coef3;
	private String total1;
	private String total2;
	private String total3;
	private String ap1;
	private String ap2;
	private String ap3;
	
	private String moy1;
	private String moy2;
	private String moy3;
	private String moyan;
	
	private Defi defi;
	private Institution institution;

	@Id
	@GeneratedValue
	public Long getIdNote() {
		return idNote;
	}

	public void setIdNote(Long idNote) {
		this.idNote = idNote;
	}

	public float getNote() {
		return note;
	}

	public void setNote(float note) {
		this.note = note;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idEleve")
	public Eleve getEleve() {
		return eleve;
	}

	public void setEleve(Eleve eleve) {
		this.eleve = eleve;
	}



	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idannee")
	public AnneeScolaire getAnnee() {
		return annee;
	}

	public void setAnnee(AnneeScolaire annee) {
		this.annee = annee;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idclasse")
	public Classe getCl() {
		return cl;
	}

	public void setCl(Classe cl) {
		this.cl = cl;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idmatiere")
	public Matiere getMatiere() {
		return matiere;
	}

	public void setMatiere(Matiere matiere) {
		this.matiere = matiere;
	}



	public int getCoef() {
		return coef;
	}

	public void setCoef(int coef) {
		this.coef = coef;
	}

	@Transient
	public String getEcole() {
		return ecole;
	}

	public void setEcole(String ecole) {
		this.ecole = ecole;
	}

	@Transient
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Transient
	public String getSlogan() {
		return slogan;
	}

	public void setSlogan(String slogan) {
		this.slogan = slogan;
	}

	public String getEff() {
		return eff;
	}

	public void setEff(String eff) {
		this.eff = eff;
	}

	@Transient
	public String getAp() {
		return ap;
	}

	public void setAp(String ap) {
		this.ap = ap;
	}

	@Transient
	public String getMoy() {
		return moy;
	}

	public void setMoy(String moy) {
		this.moy = moy;
	}



	@Transient
	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	@Transient
	public InputStream getLogo() {
		return logo;
	}

	public void setLogo(InputStream logo) {
		this.logo = logo;
	}

	@Transient
	public float getNote1() {
		return note1;
	}

	public void setNote1(float note1) {
		this.note1 = note1;
	}
	@Transient
	public float getNote2() {
		return note2;
	}

	public void setNote2(float note2) {
		this.note2 = note2;
	}
	@Transient
	public float getNote3() {
		return note3;
	}

	public void setNote3(float note3) {
		this.note3 = note3;
	}
	@Transient
	public int getCoef1() {
		return coef1;
	}

	public void setCoef1(int coef1) {
		this.coef1 = coef1;
	}
	@Transient
	public int getCoef2() {
		return coef2;
	}

	public void setCoef2(int coef2) {
		this.coef2 = coef2;
	}
	@Transient
	public int getCoef3() {
		return coef3;
	}

	public void setCoef3(int coef3) {
		this.coef3 = coef3;
	}
	
	@Transient
	public String getAp1() {
		return ap1;
	}

	public void setAp1(String ap1) {
		this.ap1 = ap1;
	}
	@Transient
	public String getAp2() {
		return ap2;
	}

	public void setAp2(String ap2) {
		this.ap2 = ap2;
	}
	@Transient
	public String getAp3() {
		return ap3;
	}

	public void setAp3(String ap3) {
		this.ap3 = ap3;
	}
	

	@Transient
	public String getMoy1() {
		return moy1;
	}

	public void setMoy1(String moy1) {
		this.moy1 = moy1;
	}

	@Transient
	public String getMoy2() {
		return moy2;
	}

	public void setMoy2(String moy2) {
		this.moy2 = moy2;
	}

	@Transient
	public String getMoy3() {
		return moy3;
	}

	public void setMoy3(String moy3) {
		this.moy3 = moy3;
	}

	@Transient
	public String getMoyan() {
		return moyan;
	}

	public void setMoyan(String moyan) {
		this.moyan = moyan;
	}

	@Transient
	public String getTotal1() {
		return total1;
	}

	public void setTotal1(String total1) {
		this.total1 = total1;
	}
	@Transient
	public String getTotal2() {
		return total2;
	}

	public void setTotal2(String total2) {
		this.total2 = total2;
	}

	@Transient
	public String getTotal3() {
		return total3;
	}

	public void setTotal3(String total3) {
		this.total3 = total3;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idinstitution")
	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "iddefi")
	public Defi getDefi() {
		return defi;
	}

	public void setDefi(Defi defi) {
		this.defi = defi;
	}

}
