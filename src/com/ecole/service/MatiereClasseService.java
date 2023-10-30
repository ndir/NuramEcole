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
import com.ecole.entity.Evaluation;
import com.ecole.entity.Matiere;
import com.ecole.entity.MatiereClasse;
import com.ecole.entity.Niveau;

/**
 * @author a626257
 * 
 */
@Scope(ScopeType.SESSION)
@Name("matclasseService")
public class MatiereClasseService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@In
	private Session dataSource;

	private List<Classe> listeClasse = new ArrayList<Classe>();

	private List<Matiere> listeMatiere = new ArrayList<Matiere>();

	private List<Niveau> listeNiveau = new ArrayList<Niveau>();

	private MatiereClasse matClasse = new MatiereClasse();

	private Niveau niveau = new Niveau();

	private AnneeScolaire anneeScolaire = new AnneeScolaire();

	private List<Evaluation> listeEval = new ArrayList<Evaluation>();

	private String typenote;

	private String niv;

	@In(required = false)
	@Out(required = false)
	private Utilisateur utilisateur;

	public Niveau getNiveau() {
		return niveau;
	}

	public void setNiveau(Niveau niveau) {
		this.niveau = niveau;
	}

	private List<MatiereClasse> listeMatClasse = new ArrayList<MatiereClasse>();

	@SuppressWarnings("unchecked")
	public void chargerListe() {
		listeNiveau = new ArrayList<Niveau>();
		listeNiveau = dataSource.createQuery(" From Niveau where code <>:pcode ").setParameter("pcode", "PRE").list();
		List<AnneeScolaire> listeAnnee = dataSource.createQuery("From AnneeScolaire order by idannee desc ").list();
		if (listeAnnee.size() > 0) {
			anneeScolaire = listeAnnee.get(0);
		}
		listeEval = new ArrayList<Evaluation>();
		listeEval = dataSource.createQuery("From Evaluation ").list();
	}

	@SuppressWarnings("unchecked")
	public void chargerMatieres() {
		listeMatiere = new ArrayList<Matiere>();
		listeMatiere = dataSource
				.createQuery(" From Matiere m inner join fetch m.niveau n inner join fetch m.institution i "
						+ " where n =:pn and i=:pi ")
				.setParameter("pn", niveau).setParameter("pi", utilisateur.getInstitution()).list();

	}

	@SuppressWarnings("unchecked")
	public void voirMatiereClasse() {
		listeMatClasse = new ArrayList<MatiereClasse>();
		listeMatClasse = dataSource.createQuery(
				"From MatiereClasse m inner join fetch m.matiere ma inner join fetch m.classe c left outer join fetch m.eval ev inner join fetch m.institution i"
						+ " where  m.annee_scol =:pan and i =:pi")
				.setParameter("pan", anneeScolaire.getAnneeScolaire()).setParameter("pi", utilisateur.getInstitution())
				.list();

	}

	public String versClasseMat() {
		this.setMatClasse(new MatiereClasse());
		chargerListe();
		listeMatiere = new ArrayList<Matiere>();
		return "/pages/nuramecole/matclasse.xhtml";
	}

	@SuppressWarnings("unchecked")
	public void chargerListeClasse() {
		listeClasse = new ArrayList<Classe>();
		listeClasse = dataSource
				.createQuery(" From Classe c inner join fetch c.niveau n inner join "
						+ " fetch c.institution i where n=:pn and i=:pi")
				.setParameter("pn", niveau).setParameter("pi", utilisateur.getInstitution()).list();
		if (niveau.getCode().equalsIgnoreCase("ELE")) {
			typenote = "1";
		}
		if (niveau.getCode().equalsIgnoreCase("MOY")) {
			typenote = "2";
		}

		if (niveau.getCode().equalsIgnoreCase("SEC")) {
			typenote = "3";
		}
	}

	@SuppressWarnings("unchecked")
	public void ajouterClasseMatiere() {
		List<Classe> listeClasse = dataSource.createQuery("From Classe c where niv =:pniv").setParameter("pniv", niv)
				.list();
		List<Evaluation> listeEval = dataSource.createQuery("From Evaluation ").list();
		for (Classe cl : listeClasse) {
			MatiereClasse m = new MatiereClasse();
			for (Matiere mat : listeMatiere) {
				if (mat.getCoef() > 0) {
					if (typenote.equalsIgnoreCase("1")) {
						m = (MatiereClasse) dataSource.createQuery(
								"From MatiereClasse m inner join fetch m.matiere ma inner join fetch m.classe c inner join fetch m.eval ev "
										+ " inner join fetch m.institution i "
										+ " where ma =:pma and c =:pc and m.annee_scol =:pan and ev =:pev and i=:pi")
								.setParameter("pma", mat).setParameter("pc", matClasse.getClasse())
								.setParameter("pan", anneeScolaire.getAnneeScolaire())
								.setParameter("pev", matClasse.getEval())
								.setParameter("pi", utilisateur.getInstitution()).uniqueResult();
					} else {
						m = (MatiereClasse) dataSource.createQuery(
								"From MatiereClasse m inner join fetch m.matiere ma inner join fetch m.classe c  inner join fetch m.institution i"
										+ " where ma =:pma and c =:pc and m.annee_scol =:pan and i=:pi  ")
								.setParameter("pma", mat).setParameter("pc", matClasse.getClasse())
								.setParameter("pan", anneeScolaire.getAnneeScolaire())
								.setParameter("pi", utilisateur.getInstitution()).uniqueResult();
					}
					if (m == null) {
						MatiereClasse mc = new MatiereClasse();
						mc.setAnnee_scol(anneeScolaire.getAnneeScolaire());
						mc.setClasse(cl);
						mc.setCoef(mat.getCoef());
						mc.setMatiere(mat);
						mc.setInstitution(utilisateur.getInstitution());
						if (typenote.equalsIgnoreCase("1")) {
							mc.setEval(listeEval.get(0));
						}
						dataSource.save(mc);
						if (typenote.equalsIgnoreCase("1")) {
							for (int i = 1; i < listeEval.size(); i++) {
								mc = new MatiereClasse();
								mc.setAnnee_scol(anneeScolaire.getAnneeScolaire());
								mc.setClasse(cl);
								mc.setCoef(mat.getCoef());
								mc.setMatiere(mat);
								mc.setInstitution(utilisateur.getInstitution());
								mc.setEval(listeEval.get(i));
								dataSource.save(mc);

							}
						}
					} else {
						m.setCoef(mat.getCoef());
						dataSource.update(m);
					}
				}
			}
		}
		this.setMatClasse(new MatiereClasse());
		listeMatiere = new ArrayList<Matiere>();
		listeClasse = new ArrayList<Classe>();
		FacesMessages.instance().addToControlFromResourceBundle("infoGenerique", "Opération effectuée avec succés");

	}

	public void supprimerMatClasse(MatiereClasse mat) {
		dataSource.delete(mat);
		voirMatiereClasse();
	}

	public Session getDataSource() {
		return dataSource;
	}

	public void setDataSource(Session dataSource) {
		this.dataSource = dataSource;
	}

	public List<Classe> getListeClasse() {
		return listeClasse;
	}

	public void setListeClasse(List<Classe> listeClasse) {
		this.listeClasse = listeClasse;
	}

	public List<Matiere> getListeMatiere() {
		return listeMatiere;
	}

	public void setListeMatiere(List<Matiere> listeMatiere) {
		this.listeMatiere = listeMatiere;
	}

	public MatiereClasse getMatClasse() {
		return matClasse;
	}

	public void setMatClasse(MatiereClasse matClasse) {
		this.matClasse = matClasse;
	}

	public List<MatiereClasse> getListeMatClasse() {
		return listeMatClasse;
	}

	public void setListeMatClasse(List<MatiereClasse> listeMatClasse) {
		this.listeMatClasse = listeMatClasse;
	}

	public List<Niveau> getListeNiveau() {
		return listeNiveau;
	}

	public void setListeNiveau(List<Niveau> listeNiveau) {
		this.listeNiveau = listeNiveau;
	}

	public AnneeScolaire getAnneeScolaire() {
		return anneeScolaire;
	}

	public void setAnneeScolaire(AnneeScolaire anneeScolaire) {
		this.anneeScolaire = anneeScolaire;
	}

	public List<Evaluation> getListeEval() {
		return listeEval;
	}

	public void setListeEval(List<Evaluation> listeEval) {
		this.listeEval = listeEval;
	}

	public String getTypenote() {
		return typenote;
	}

	public void setTypenote(String typenote) {
		this.typenote = typenote;
	}

	public String getNiv() {
		return niv;
	}

	public void setNiv(String niv) {
		this.niv = niv;
	}

}
