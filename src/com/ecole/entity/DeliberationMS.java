/**
 * 
 */
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

/**
 * @author A626257
 *
 */
@Entity
@Table(name = "DeliberationMS")
public class DeliberationMS implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private Eleve eleve;

	private Matiere matiere;

	private String moyenneD;

	private String moyenneC;

	private Double moyD;

	private Double moyC;

	private int coef;

	private int rang;

	private Appreciations appreciation;

	private Appreciations appreciationgen;

	private String totals;

	private Double total;

	private int totalcoef;

	private String ranggen;

	private String ranggan;

	private int absence;

	private int retard;

	private String app;

	private AnneeScolaire annee;

	private Double cumul;

	private String cumuls;

	private Semestres semestre;

	private Classe classe;

	private Double moyenne;

	private String moyennes;

	private String apgen;

	private Double moyenneAn;

	private String moyenneAns;

	private Double moyenne1;

	private String moyenne1s;

	private String decision;

	private Appreciations appreciationan;

	private String moyx;

	private String ecole;
	private String tel;
	private String slogan;
	private String eff;
	private String ia;
	private String ief;
	private InputStream logo;
	private String d1;
	private String d2;
	private String d3;
	
	private String ranga;
	private String rangm;
	private String use;
	private String usean;
	private String userang;
	private Institution institution;

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ideleve")
	public Eleve getEleve() {
		return eleve;
	}

	public void setEleve(Eleve eleve) {
		this.eleve = eleve;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idmatiere")
	public Matiere getMatiere() {
		return matiere;
	}

	public void setMatiere(Matiere matiere) {
		this.matiere = matiere;
	}

	public String getMoyenneD() {
		return moyenneD;
	}

	public void setMoyenneD(String moyenneD) {
		this.moyenneD = moyenneD;
	}

	public String getMoyenneC() {
		return moyenneC;
	}

	public void setMoyenneC(String moyenneC) {
		this.moyenneC = moyenneC;
	}

	public int getCoef() {
		return coef;
	}

	public void setCoef(int coef) {
		this.coef = coef;
	}

	public int getRang() {
		return rang;
	}

	public void setRang(int rang) {
		this.rang = rang;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idappreciation")
	public Appreciations getAppreciation() {
		return appreciation;
	}

	public void setAppreciation(Appreciations appreciation) {
		this.appreciation = appreciation;
	}

	public int getTotalcoef() {
		return totalcoef;
	}

	public void setTotalcoef(int totalcoef) {
		this.totalcoef = totalcoef;
	}

	public String getRanggen() {
		return ranggen;
	}

	public void setRanggen(String ranggen) {
		this.ranggen = ranggen;
	}

	public int getAbsence() {
		return absence;
	}

	public void setAbsence(int absence) {
		this.absence = absence;
	}

	public int getRetard() {
		return retard;
	}

	public void setRetard(int retard) {
		this.retard = retard;
	}

	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idannee")
	public AnneeScolaire getAnnee() {
		return annee;
	}

	public void setAnnee(AnneeScolaire annee) {
		this.annee = annee;
	}

	public Double getMoyD() {
		return moyD;
	}

	public void setMoyD(Double moyD) {
		this.moyD = moyD;
	}

	public Double getMoyC() {
		return moyC;
	}

	public void setMoyC(Double moyC) {
		this.moyC = moyC;
	}

	public String getTotals() {
		return totals;
	}

	public void setTotals(String totals) {
		this.totals = totals;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idsemestre")
	public Semestres getSemestre() {
		return semestre;
	}

	public void setSemestre(Semestres semestre) {
		this.semestre = semestre;
	}

	public Double getCumul() {
		return cumul;
	}

	public void setCumul(Double cumul) {
		this.cumul = cumul;
	}

	public String getCumuls() {
		return cumuls;
	}

	public void setCumuls(String cumuls) {
		this.cumuls = cumuls;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idclasse")
	public Classe getClasse() {
		return classe;
	}

	public void setClasse(Classe classe) {
		this.classe = classe;
	}

	public Double getMoyenne() {
		return moyenne;
	}

	public void setMoyenne(Double moyenne) {
		this.moyenne = moyenne;
	}

	public String getMoyennes() {
		return moyennes;
	}

	public void setMoyennes(String moyennes) {
		this.moyennes = moyennes;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idap")
	public Appreciations getAppreciationgen() {
		return appreciationgen;
	}

	public void setAppreciationgen(Appreciations appreciationgen) {
		this.appreciationgen = appreciationgen;
	}

	public String getApgen() {
		return apgen;
	}

	public void setApgen(String apgen) {
		this.apgen = apgen;
	}

	public Double getMoyenneAn() {
		return moyenneAn;
	}

	public void setMoyenneAn(Double moyenneAn) {
		this.moyenneAn = moyenneAn;
	}

	public String getMoyenneAns() {
		return moyenneAns;
	}

	public void setMoyenneAns(String moyenneAns) {
		this.moyenneAns = moyenneAns;
	}

	public Double getMoyenne1() {
		return moyenne1;
	}

	public void setMoyenne1(Double moyenne1) {
		this.moyenne1 = moyenne1;
	}

	public String getMoyenne1s() {
		return moyenne1s;
	}

	public void setMoyenne1s(String moyenne1s) {
		this.moyenne1s = moyenne1s;
	}

	public String getDecision() {
		return decision;
	}

	public void setDecision(String decision) {
		this.decision = decision;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idan")
	public Appreciations getAppreciationan() {
		return appreciationan;
	}

	public void setAppreciationan(Appreciations appreciationan) {
		this.appreciationan = appreciationan;
	}

	public String getRanggan() {
		return ranggan;
	}

	public void setRanggan(String ranggan) {
		this.ranggan = ranggan;
	}

	public String getMoyx() {
		return moyx;
	}

	public void setMoyx(String moyx) {
		this.moyx = moyx;
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

	@Transient
	public String getEff() {
		return eff;
	}

	public void setEff(String eff) {
		this.eff = eff;
	}

	@Transient
	public String getIa() {
		return ia;
	}

	public void setIa(String ia) {
		this.ia = ia;
	}

	@Transient
	public String getIef() {
		return ief;
	}

	public void setIef(String ief) {
		this.ief = ief;
	}
	@Transient
	public InputStream getLogo() {
		return logo;
	}

	public void setLogo(InputStream logo) {
		this.logo = logo;
	}

	public String getD1() {
		return d1;
	}

	public void setD1(String d1) {
		this.d1 = d1;
	}

	public String getD2() {
		return d2;
	}

	public void setD2(String d2) {
		this.d2 = d2;
	}

	public String getD3() {
		return d3;
	}

	public void setD3(String d3) {
		this.d3 = d3;
	}

	@Transient
	public String getRanga() {
		return ranga;
	}

	public void setRanga(String ranga) {
		this.ranga = ranga;
	}

	@Transient
	public String getUse() {
		return use;
	}

	public void setUse(String use) {
		this.use = use;
	}

	public String getUsean() {
		return usean;
	}
	@Transient
	public void setUsean(String usean) {
		this.usean = usean;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idinstitution")
	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	public String getUserang() {
		return userang;
	}

	public void setUserang(String userang) {
		this.userang = userang;
	}

	public String getRangm() {
		return rangm;
	}

	public void setRangm(String rangm) {
		this.rangm = rangm;
	}

}
