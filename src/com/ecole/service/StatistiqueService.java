/**
 * 
 */
package com.ecole.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.hibernate.Session;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import com.ecole.entity.AnneeScolaire;
import com.ecole.entity.Classe;
import com.ecole.entity.Inscription;
import com.ecole.entity.Institution;
import com.ecole.entity.ParamInscription;
import com.rhospi.commons.ChakaUtils;

/**
 * @author A626257
 *
 */
@Scope(ScopeType.SESSION)
@Name("statService")
public class StatistiqueService implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@In
	private Session dataSource;

	private List<Classe> listeClasse = new ArrayList<Classe>();

	@In
	private AnneeScolaire annee;

	private List<Inscription> listeIns = new ArrayList<Inscription>();

	private int nbeleve;

	private int nbfille;

	private Institution ins = new Institution();

	private int nbgarcon;

	@SuppressWarnings({ "unused", "unchecked" })
	public void effectifarClasse(OutputStream out, Object data) {

		List<ParamInscription> listeParam = dataSource
				.createQuery("from ParamInscription p inner join fetch p.annee inner join fetch p.classe"
						+ " where p.annee =:pannee ")
				.setParameter("pannee", annee).list();
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		if (listeParam.size() > 0) {
			for (ParamInscription param : listeParam) {
				effectifClasse(param);
				dataset.addValue(getNombreEleve(param), param.getClasse().getLibelle(),
						ChakaUtils.formateDate(ChakaUtils.sysDate(), "dd/MM/yyyy"));
			}

		}
		JFreeChart barChart = ChartFactory.createBarChart("EFFECTIF PAR CLASSE", "", "NOMBRE D'ELEVE(S)", dataset,
				PlotOrientation.VERTICAL, true, true, false);
		BufferedImage bufferedImage = barChart.createBufferedImage(430, 300);
		try {
			ImageIO.write(bufferedImage, "gif", out);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			ChartUtilities.saveChartAsJPEG(new File("C:\\chart6.jpg"), barChart, 600, 400);
		} catch (Exception e) {
		}
	}

	@SuppressWarnings("unchecked")
	public int getNombreEleve(ParamInscription param) {
		List<Inscription> liste = dataSource
				.createQuery(
						"From Inscription i inner join fetch i.eleve" + "  inner join fetch i.paramins p where p =:pp")
				.setParameter("pp", param).list();
		return liste.size();
	}

	@SuppressWarnings("unchecked")
	@Create
	public void getEffectifClasses() {
		ins = (Institution) dataSource.createQuery("From Institution").uniqueResult();
		listeClasse = new ArrayList<Classe>();
		listeClasse = dataSource.createQuery("From Classe ").list();

		List<ParamInscription> listeParam = dataSource
				.createQuery("from ParamInscription p inner join fetch p.annee inner join fetch p.classe"
						+ " where p.annee =:pannee ")
				.setParameter("pannee", annee).list();

		if (listeParam.size() > 0) {
			nbeleve = 0;
			nbgarcon = 0;
			nbfille = 0;
			for (ParamInscription param : listeParam) {
				effectifClasse(param);

			}
			
		}
		listeIns = new ArrayList<Inscription>();
		listeIns = dataSource
				.createQuery("From Inscription i inner join fetch i.eleve"
						+ "  inner join fetch i.paramins p inner join fetch p.annee an where an =:pp")
				.setParameter("pp", annee).list();
		for (Classe cl : listeClasse) {
			getNombreFilleGarcon(listeIns, cl);
		}
		
		nbeleve = nbgarcon + nbfille;
		
	}

	@SuppressWarnings("unchecked")
	public void effectifClasse(ParamInscription param) {
		listeIns = new ArrayList<Inscription>();
		listeIns = dataSource
				.createQuery(
						"From Inscription i inner join fetch i.eleve" + "  inner join fetch i.paramins p where p =:pp")
				.setParameter("pp", param).list();
		for (Classe cl : listeClasse) {
			if (cl.getIdclasse().equals(param.getClasse().getIdclasse())) {
				cl.setNombre(listeIns.size());
			}
		}
	}

	public void getNombreFilleGarcon(List<Inscription> liste, Classe classe) {
		int nombref = 0;
		int nombreg = 0;

		for (Inscription ins : liste) {
			if (ins.getParamins().getClasse().getIdclasse().equals(classe.getIdclasse())) {
				if (ins.getEleve().getSexe().equalsIgnoreCase("M")) {
					nombreg++;
					nbgarcon++;
				} else {
					nombref++;
					nbfille++;
				}
			}
		}
		classe.setFille(nombref);
		classe.setGarcon(nombreg);
	}

	public List<Classe> getListeClasse() {
		return listeClasse;
	}

	public void setListeClasse(List<Classe> listeClasse) {
		this.listeClasse = listeClasse;
	}

	public int getNbeleve() {
		return nbeleve;
	}

	public void setNbeleve(int nbeleve) {
		this.nbeleve = nbeleve;
	}

	public int getNbfille() {
		return nbfille;
	}

	public void setNbfille(int nbfille) {
		this.nbfille = nbfille;
	}

	public int getNbgarcon() {
		return nbgarcon;
	}

	public void setNbgarcon(int nbgarcon) {
		this.nbgarcon = nbgarcon;
	}

	public Institution getIns() {
		return ins;
	}

	public void setIns(Institution ins) {
		this.ins = ins;
	}

}
