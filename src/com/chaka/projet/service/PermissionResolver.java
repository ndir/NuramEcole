/**
 * 
 */
package com.chaka.projet.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.security.Identity;
import org.jboss.seam.security.SimplePrincipal;
import org.jboss.seam.security.permission.Permission;
import org.jboss.seam.security.permission.PermissionStore;

import com.chaka.projet.entity.DroitUserAccess;

/**
 * @author Dramé
 *
 */
@Name("permissionResolver")
@Scope(ScopeType.APPLICATION)
public class PermissionResolver implements Serializable,PermissionStore {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@In
	private Session dataSource;
	/**
	 * 
	 */
	public PermissionResolver() {
		// TODO Auto-generated constructor stub
		System.out.println("invoked");
	}

	@Override
	public void clearPermissions(Object arg0) {
		// TODO Auto-generated method stub
		System.out.println("2");
	}

	@Override
	public boolean grantPermission(Permission arg0) {
		// TODO Auto-generated method stub
		System.out.println("okkkkkkkkkkkkkk");
		return true;
	}

	@Override
	public boolean grantPermissions(List<Permission> arg0) {
		// TODO Auto-generated method stub
		System.out.println("3");
		return false;
	}

	@Override
	public List<String> listAvailableActions(Object arg0) {
		// TODO Auto-generated method stub
		System.out.println("4");
		return null;
	}

	@Override
	public List<Permission> listPermissions(Object arg0) {
		// TODO Auto-generated method stub
		System.out.println("5");
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Permission> listPermissions(Object arg0, String arg1) {
		// TODO Auto-generated method stub
		String login = Identity.instance().getCredentials().getUsername();
		String target = arg0.toString();
		if(target.contains("@")) 			
		   target = target.split("@")[0];
//		System.out.println("6 "+"login: "+ login + " target :" + target + "   action: "+arg1);
		List<Permission> list = new ArrayList<Permission>();
		
		StringBuilder hqlQuery = new StringBuilder();
		hqlQuery.append("select distinct d from DroitUserAccess d");
		hqlQuery.append(" inner join fetch d.fonction");
		hqlQuery.append(" where (d.user.login = :paramRecipient");
		hqlQuery.append(" and d.fonction.entite is not null and d.fonction.entite.target = :paramTarget )");
		hqlQuery.append(" and (");
		hqlQuery.append(" (:paramAction = 'modifyAV' and d.canModifyAfV = :paramValue)");
		hqlQuery.append(" or (:paramAction = 'modifyBV' and d.canModifyBfV = :paramValue)");
		hqlQuery.append(" or (:paramAction = 'insert' and d.canInsert = :paramValue)");
		hqlQuery.append(" or (:paramAction = 'validate' and d.canValider = :paramValue)");
		hqlQuery.append(" or (:paramAction = 'delete' and d.canDelete = :paramValue)");
		hqlQuery.append(" or (:paramAction = 'print' and d.canPrint = :paramValue)");
		hqlQuery.append(")");
		
		/*String hql = "from DroitUserAccess d where (d.user.login = :paramRecipient "+
		             " and d.fonction.entite is not null and d.fonction.entite.target = :paramTarget )"+
		             " and ("+
		             "  (:paramAction = 'modifyAV' and d.canModifyAfV = :paramValue)"+
		             "  or (:paramAction = 'modifyBV' and d.canModifyBfV = :paramValue)"+
		             "  or (:paramAction = 'insert' and d.canInsert = :paramValue)"+
		             "  or (:paramAction = 'delete' and d.canDelete = :paramValue)"+
		             "  or (:paramAction = 'validate' and d.canValider = :paramValue)"+
		             "  or (:paramAction = 'print' and d.canPrint = :paramValue)"+
		             ")";*/
		List<DroitUserAccess> listStored = dataSource.createQuery(hqlQuery.toString())		                                                      
		.setParameter("paramRecipient", login)
		.setParameter("paramTarget", target)
		.setParameter("paramAction", arg1)
		.setParameter("paramValue", true).list();
		

		for(DroitUserAccess permissionStored : listStored){			
			try {
				
				Permission permission = new Permission(Class.forName(null),arg1, new SimplePrincipal(login));//TODOO  null 
				list.add(permission);				
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return list;
		
	}

	@Override
	public List<Permission> listPermissions(Set<Object> arg0, String arg1) {
		// TODO Auto-generated method stub
		System.out.println("7");
		return null;
	}

	@Override
	public boolean revokePermission(Permission arg0) {
		// TODO Auto-generated method stub
		System.out.println("8");
		return false;
	}

	@Override
	public boolean revokePermissions(List<Permission> arg0) {
		// TODO Auto-generated method stub
		System.out.println("9");
		return false;
	}
}
