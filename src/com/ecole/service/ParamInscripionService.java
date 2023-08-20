/**
 * 
 */
package com.ecole.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;

import com.chaka.projet.entity.Utilisateur;
import com.ecole.entity.AnneeScolaire;
import com.ecole.entity.Classe;
import com.ecole.entity.Niveau;
import com.ecole.entity.ParamInscription;

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

	@SuppressWarnings("unchecked")
	public String versCreer() {
		param = new ParamInscription();
		param.setAnnee(annee);
		chargerliste();
		//listeNiveau = new ArrayList<Niveau>();
		//listeNiveau = dataSource.createQuery("From Niveau ").list();
		//if(listeNiveau.size() > 0) {
			setListeClasse(new ArrayList<Classe>());
			setListeClasse(dataSource.createQuery(" From Classe c inner join fetch c.niveau ")
					.list());
		//}
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
	public void chargerListeClasse() {
		
		setListeClasse(new ArrayList<Classe>());
		setListeClasse(dataSource.createQuery(" From Classe c inner join fetch c.niveau n where n=:pn")
				.setParameter("pn", niveau).list());
	}

	public void versModifierInscription(ParamInscription param) {
		this.setParam(param);
	}

	public void ajouterInscription() {
		if (param.getClasse() == null) {
			FacesMessages.instance().addToControlFromResourceBundle("erreurGenerique", "Classe obligatoire");
			return;
		}

		if (param.getId() == null) {
			dataSource.save(param);
		} else {
			dataSource.update(param);
		}
		this.setParam(new ParamInscription());
		param.setAnnee(annee);
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

}
