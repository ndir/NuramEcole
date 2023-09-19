/**
 * 
 */
package com.chaka.parametrage.converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.hibernate.Session;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import com.ecole.entity.MenuParent;





/**
 * @author antou
 * 
 */
@Name("menuParentConverter")
public class MenuParentConverter implements Serializable, Converter {

	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;
	@In
	private Session dataSource;

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1,
			String pValeur) throws ConverterException {
		MenuParent p = (MenuParent) this.dataSource.get(MenuParent.class, new Long(pValeur));
		return p;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object objet)
			throws ConverterException {
		MenuParent p = (MenuParent) objet;
		String resultat = null;
		if (p != null && p.getId() != null) {
			resultat = p.getId().toString();
		}
		return resultat;
	}
}
