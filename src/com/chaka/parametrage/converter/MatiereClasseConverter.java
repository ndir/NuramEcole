package com.chaka.parametrage.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.hibernate.Session;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import com.ecole.entity.MatiereClasse;
@Name("matiereClasseConverter")
public class MatiereClasseConverter implements Converter{
	@In
	private Session dataSource;
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) throws ConverterException {
		// TODO Auto-generated method stub
		MatiereClasse matClasse = (MatiereClasse) this.dataSource.get(MatiereClasse.class, new Long(arg2));
		return matClasse;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) throws ConverterException {
		// TODO Auto-generated method stub
		MatiereClasse matClasse= (MatiereClasse) arg2;
		String resultat="";
		if(matClasse !=null && matClasse.getIdmatclasse()!=null) {
			resultat= matClasse.getIdmatclasse().toString();
		}
		return resultat;
	}

	public Session getDataSource() {
		return dataSource;
	}

	public void setDataSource(Session dataSource) {
		this.dataSource = dataSource;
	}

}
