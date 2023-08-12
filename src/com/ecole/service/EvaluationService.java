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
import org.jboss.seam.annotations.Scope;


import org.jboss.seam.faces.FacesMessages;


import com.ecole.entity.Evaluation;

/**
 * @author a626257
 *
 */
@Scope(ScopeType.SESSION)
@Name("evaluationService")
public class EvaluationService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Evaluation> listeEval = new ArrayList<Evaluation>();
	
	private Evaluation eval = new Evaluation();
	
	
	@In
	private Session dataSource;

	@SuppressWarnings("unchecked")
	public void chargerListeEval() {
		listeEval = new ArrayList<Evaluation>();
		listeEval = dataSource.createQuery(" From Evaluation ").list();
	}

	public String versEvaluation() {
		this.setEval(new Evaluation());
		chargerListeEval();
		return "/pages/ecole/creereval.xhtml";
	}
	
	public void annulerAjout(){
		this.setEval(new Evaluation());
	}

	public void ajouterEal() {
		if (this.eval.getLibelle().isEmpty()) {
			FacesMessages.instance().addToControlFromResourceBundle(
					"erreurGenerique", "Veuillez renseigner le libellé");
			return;
		}
		
		if (eval.getIdEvaluation()== null) {
			dataSource.save(eval);
		} else {
			dataSource.update(eval);
		}
		chargerListeEval();;
		this.setEval(new Evaluation());
		FacesMessages.instance().addToControlFromResourceBundle(
				"infoGenerique", "Evaluation ajoutée avec succés");
	}
	
	public void versModifierClasse(Evaluation eval){
		this.setEval(eval);
	}
	

	public List<Evaluation> getListeEval() {
		return listeEval;
	}

	public void setListeEval(List<Evaluation> listeEval) {
		this.listeEval = listeEval;
	}

	public Evaluation getEval() {
		return eval;
	}

	public void setEval(Evaluation eval) {
		this.eval = eval;
	}

}
