package com.chaka.parametrage.service.generaux;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;

import com.chaka.parametrage.entity.generaux.Etablissement;
import com.chaka.projet.entity.Utilisateur;

@Scope(ScopeType.SESSION)
@Name("etablissementService")
public class EtablissementService implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 2774659771071458273L;


	/**
	 * Session hibernate permettant le dialogue avec la base de données.
	 */
	@In
	private Session dataSource;
	

	/**
	 * Instance courante de l'utilisateur manipulé par cette classe de service.
	 */
	@In(required = false)
	@Out(required = false)
	private Utilisateur utilisateur;
	@SuppressWarnings("unused")
	private List<Etablissement> listEtablissement;

	/**
	 * Constructeur
	 */
	public EtablissementService() {
		super();
	}
	/**
	 * @return the listEtablissement
	 */
	@SuppressWarnings("unchecked")
	public List<Etablissement> getListEtablissement() {
		String hql = "select e from Etablissement e order by e.libelle";		
		return this.dataSource.createQuery(hql)
		.list();
	}
	/**
	 * @param listEtablissement the listEtablissement to set
	 */
	public void setListEtablissement(List<Etablissement> listEtablissement) {
		this.listEtablissement = listEtablissement;
	}
	/**
	 * 
	 * @param monnaie
	 * @return
	 */
	public boolean supprimable(Etablissement etablissement)
	{
		String hql = "select count(i) from Utilisateur i where i.etablissement = :etablissement ";
		Number nb = (Number)this.dataSource.createQuery(hql).setParameter("etablissement", etablissement).uniqueResult();
		if (nb.intValue() > 0)
			return false;	
		return true;
	}
	
}
