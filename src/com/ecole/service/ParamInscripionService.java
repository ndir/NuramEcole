/**
 * 
 */
package com.ecole.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;

import com.chaka.projet.entity.Utilisateur;
import com.ecole.entity.AnneeScolaire;
import com.ecole.entity.Classe;
import com.ecole.entity.Inscription;
import com.ecole.entity.Institution;
import com.ecole.entity.Niveau;
import com.ecole.entity.ParamInscription;
import com.rhospi.commons.FileUploadService;
import com.rhospi.commons.ChakaUtils.ExportOption;

/**
 * @author A626257
 *
 */
@Name("piService")
@Scope(ScopeType.SESSION)
public class ParamInscripionService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@In
	private Session dataSource;

	@In
	private AnneeScolaire annee;

	private ParamInscription param;

	private List<ParamInscription> liste = new ArrayList<ParamInscription>();

	private List<Niveau> listeNiveau = new ArrayList<Niveau>();

	private Niveau niveau = new Niveau();

	private List<Classe> listeClasse = new ArrayList<Classe>();

	private List<Inscription> listeEleve = new ArrayList<Inscription>();

	private String libclasse;

	private String showModal = "";

	private String typeNote;

	FileUploadService filePrintService;

	@In(required = false)
	@Out(required = false)
	private Utilisateur utilisateur;

	private String niv;

	@SuppressWarnings("unchecked")
	public String versCreer() {
		param = new ParamInscription();
		param.setAnnee(annee);
		chargerliste();

		return "/pages/nuramecole/creerparaminscription.xhtml";
	}

	@SuppressWarnings("unchecked")
	public void chargerliste() {
		liste = new ArrayList<ParamInscription>();
		liste = dataSource
				.createQuery(
						"From ParamInscription p inner join fetch p.classe inner join fetch p.annee where p.annee =:pa")
				.setParameter("pa", annee).list();

	}

	@SuppressWarnings("unchecked")
	public void versListeEleves(ParamInscription param) {
		listeEleve = new ArrayList<Inscription>();
		setListeEleve(dataSource
				.createQuery("From Inscription i inner join fetch i.eleve inner join fetch i.paramins p where p =:pp")
				.setParameter("pp", param).list());
		this.setLibclasse(param.getClasse().getLibelle());
		this.setParam(param);
	}

	public void imprimerListeEleves() {
		Institution in = (Institution) dataSource.createQuery("From Institution").uniqueResult();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ecole", in.getNom());
		param.put("slogan", in.getSologan());
		param.put("tel", in.getTelephone());
		param.put("etat", "LISTE DES ELEVES DE LA CLASSE " + this.param.getClasse().getLibelle());
		getFilePrintService().imprimer("ecole", "eleve", param, listeEleve, utilisateur, ExportOption.PDF);
		this.setShowModal("javascript:Richfaces.showModalPanel('modalPdf')");
	}

	@SuppressWarnings("unchecked")
	public void chargerListeClasse() {
		System.out.println("Niveau " + niveau.getLibelle());
//		setListeClasse(new ArrayList<Classe>());
//		setListeClasse(dataSource.createQuery(" From Classe c inner join fetch c.niveau n where n=:pn")
//				.setParameter("pn", niveau).list());
		if (niveau.getCode().equalsIgnoreCase("ELE")) {
			typeNote = "1";
		}
		if (niveau.getCode().equalsIgnoreCase("MOY")) {
			typeNote = "2";
		}

		if (niveau.getCode().equalsIgnoreCase("SEC")) {
			typeNote = "3";
		}
		if (niveau.getCode().equalsIgnoreCase("PRE")) {
			typeNote = "4";
		}
	}
	

	public void versModifierInscription(ParamInscription param) {
		this.setParam(param);
	}

	@SuppressWarnings("unchecked")
	public void ajouterInscription() {
		if (niv.isEmpty()) {
			FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique", "Classe obligatoire");
			return;
		}
		if (param.getMensualite() <= 0) {
			FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique", "Mensualité obligatoire");
			return;
		}
		if (param.getDroit_ins() <= 0) {
			FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique", "Droit d'inscription obligatoire");
			return;
		}
		List<Classe> listeClasse = dataSource.createQuery("From Classe c where niv =:pniv").setParameter("pniv", niv)
				.list();
		for (Classe cl : listeClasse) {
			ParamInscription p = new ParamInscription();
			p.setAnnee(annee);
			p.setClasse(cl);
			p.setMensualite(param.getMensualite());
			p.setDroit_ins(param.getDroit_ins());
			dataSource.merge(p);
		}
		chargerliste();
		FacesMessages.instance().addToControlFromResourceBundle("infoGenerique", "Opération effectuée avec succés");

	}

	public void annulerAjout() {
		this.setParam(new ParamInscription());
		param.setAnnee(annee);
	}

	public ParamInscription getParam() {
		return param;
	}

	public void setParam(ParamInscription param) {
		this.param = param;
	}

	public List<ParamInscription> getListe() {
		return liste;
	}

	public void setListe(List<ParamInscription> liste) {
		this.liste = liste;
	}

	public List<Niveau> getListeNiveau() {
		return listeNiveau;
	}

	public void setListeNiveau(List<Niveau> listeNiveau) {
		this.listeNiveau = listeNiveau;
	}

	public Niveau getNiveau() {
		listeNiveau = new ArrayList<Niveau>();
		listeNiveau = dataSource.createQuery("From Niveau ").list();
		return niveau;
	}

	public void setNiveau(Niveau niveau) {
		this.niveau = niveau;
	}

	public List<Classe> getListeClasse() {
		return listeClasse;
	}

	public void setListeClasse(List<Classe> listeClasse) {
		this.listeClasse = listeClasse;
	}

	public List<Inscription> getListeEleve() {
		return listeEleve;
	}

	public void setListeEleve(List<Inscription> listeEleve) {
		this.listeEleve = listeEleve;
	}

	public String getLibclasse() {
		return libclasse;
	}

	public void setLibclasse(String libclasse) {
		this.libclasse = libclasse;
	}

	public FileUploadService getFilePrintService() {
		if (filePrintService == null) {
			filePrintService = (FileUploadService) Component.getInstance(FileUploadService.class);

		}
		return filePrintService;
	}

	public void setFilePrintService(FileUploadService filePrintService) {
		this.filePrintService = filePrintService;
	}

	public String getShowModal() {
		return showModal;
	}

	public void setShowModal(String showModal) {
		this.showModal = showModal;
	}

	public String getTypeNote() {
		return typeNote;
	}

	public void setTypeNote(String typeNote) {
		this.typeNote = typeNote;
	}

	public String getNiv() {
		return niv;
	}

	public void setNiv(String niv) {
		this.niv = niv;
	}

}
