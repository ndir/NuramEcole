package com.chaka.parametrage.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.hibernate.Session;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import com.ecole.entity.Eleve;
@Name("eleveConverter")
public class EleveConverter implements Converter {
	@In
	private Session dataSource;
	
	public EleveConverter() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) throws ConverterException {
		// TODO Auto-generated method stub
		Eleve eleve= (Eleve) this.dataSource.get(Eleve.class, new Long(arg2));
		return eleve;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) throws ConverterException {
		// TODO Auto-generated method stub
		Eleve eleve= (Eleve) arg2;
		String resultat="";
		if(eleve != null && eleve.getIdeleve()!=null)
			resultat=eleve.getIdeleve().toString();
		return resultat;
	}

}
