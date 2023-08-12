/**
 * 
 */
package com.chaka.projet.service;

import java.io.Serializable;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.security.Identity;

import com.chaka.projet.entity.Utilisateur;

/**
 * @author Dramé
 *
 */
@Name(value = "securityService")
@Scope(ScopeType.SESSION)
public class SecurityService implements Serializable {


	/**
	 * #{s:hasPermission(unDossier,'modify')}
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	
	@In(required = false)
	@Out(required = false)
	private Utilisateur utilisateur;
	
	public SecurityService() {
		// TODO Auto-generated constructor stub
	}
	
	@SuppressWarnings("rawtypes")
	public Object completeTarget(Object target){
		if(target instanceof Class)
			return((Class)target).getName();
		else return target;
	}
	
	
	public boolean canModifyBeforeValidation(Object target){
		target = completeTarget(target);
		return Identity.instance().hasPermission(target, "modifyBV");
	}
	
	public boolean canInsert(Object target){
		target = completeTarget(target);
		return Identity.instance().hasPermission(target, "insert");
	}
	
	public boolean canDelete(Object target){
		target = completeTarget(target);
		return Identity.instance().hasPermission(target, "delete");
	}
	
	public boolean canValidate(Object target){
		target = completeTarget(target);
		return Identity.instance().hasPermission(target, "validate");
	}
	
	public boolean canPrint(Object target){
		target = completeTarget(target);
		return Identity.instance().hasPermission(target, "print");
	}
	
	public boolean canRead(Object target){
		target = completeTarget(target);
		return Identity.instance().hasPermission(target, "read");
	}
	
	public boolean canModifyAfterValidation(Object target){
		target = completeTarget(target);
		return Identity.instance().hasPermission(target, "modifyAV");
	}
}
