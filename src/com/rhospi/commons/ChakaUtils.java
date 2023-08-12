/**
 * 
 */
package com.rhospi.commons;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.j2ee.servlets.BaseHttpServlet;

import org.jboss.seam.Component;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.faces.FacesMessages;

import com.chaka.constantes.Constantes;
import com.chaka.parametrage.service.ListeParam;
import com.chaka.projet.entity.Utilisateur;
import com.lowagie.text.pdf.PdfWriter;



/**
 * @author Gora
 *
 */
public class ChakaUtils implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**objet statique de la classe ChakaUtils.**/
	public static ChakaUtils chakaUtils = new ChakaUtils();
	
	public static enum ExportOption {PDF, HTML, EXCEL, RTF};
	
	@Out(required = false)
	private ListeParam listeParamStock=(ListeParam)Component.getInstance("listeParamStock");
	

	/**
	 * 
	 */
	public ChakaUtils() {
		// TODO Auto-generated constructor stub
	}
	/**@return date Serveur**/
	public static Date sysDate(){
		return new Date();
	}
	
	
	/** permet de constituer une date à partir de l'année, le mois et le jour.
	 * @param annee l'année.
	 * @param mois le mois.
	 * @param jour jour.
	 * @param heure 
	 * @param mn
	 * @param second  
	 * @return la date constituée.*/
	 
	public static Date fixerTime(Date date,int heure, int mn,int second){
		    Calendar calendar = Calendar.getInstance();	
		    calendar.clear();
		    calendar.setTime(date);
		    calendar.set(Calendar.HOUR_OF_DAY, heure);
		    calendar.set(Calendar.MINUTE, mn);
		    calendar.set(Calendar.SECOND, second);
		    return calendar.getTime();
	}


	

	/**méthode d'initialisation d'un objet calendar.
	 * permettant de manipuler les dates
	 * @param date date en question.
	 * @return un objet calendar.**/
	public static Calendar createCalendarFromDate(Date date){
		java.util.Calendar calendar=Calendar.getInstance();
		calendar.clear();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 1);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);	
		return calendar;
    }
	
	/**permet de récupérer l'année d'une date donnée.
	 @param dateRef date debut
	 * @return l'année correspondante à la date.
	 */
	public static int getCurrentYear(Date dateRef){
		   Calendar calendar=createCalendarFromDate(dateRef);
		   return calendar.get(Calendar.YEAR);
    }
	
	/**permet de récupérer le mois d'une date donnée.
	@param dateRef date debut
	 * @return le mois correspondant à la date.
	 */
	public static int getCurrentMonth(Date dateRef, boolean beginZeroAsJava){
		   Calendar calendar=createCalendarFromDate(dateRef);
		   return beginZeroAsJava ? calendar.get(Calendar.MONTH) : calendar.get(Calendar.MONTH) + 1;
	}
	
	/**permet de récupérer le jour d'une date donnée.
	 * @param dateRef date debut
	 * @return le jour correspondant à la date
	 */
	public static int getCurrentDay(Date dateRef){
		   Calendar calendar=createCalendarFromDate(dateRef);		   
		   return calendar.get(Calendar.DAY_OF_MONTH);
	}
	
	/**permet de récupérer la fin du mois précédant la date entrée en paramétre.
	 * @param dateRef date départ.
	 * @return la date de fin du mois précédant.
	 */
	public static Date getDateFinMoisPrecedant(Date dateRef){
		   Date dateFinMoisPrecedant=addNdays(dateRef,-getCurrentDay(dateRef));
		   return dateFinMoisPrecedant;
	}
	/**permet de récupérer la fin du mois suivant la date entrée en paramétre.
	 * @param dateRef date départ.
	 * @return la date de fin du mois précédant.
	 */
	public static Date getDateFinMoisSuivant(Date dateRef){
		   return addNmois(finDuMois(dateRef),1);		   
	}
	/**debut du mois donné en paramétre.
	@param dateRef date départ.
	 * @return le debut du mois.
	 */
	public static Date debutDuMois(Date dateRef){
		   return addNdays(getDateFinMoisPrecedant(dateRef),1);
	}
	/**le mois donné en paramétre.
	@param dateRef date en question.
	 * @return la fin du mois.
	 */
	public static Date finDuMois(Date dateRef){
	     Date dsLeMoisSuivant=addNdays(getDateFinMoisPrecedant(dateRef),32);
	     return addNdays(dsLeMoisSuivant, -getCurrentDay(dsLeMoisSuivant));
    }
	
	/**permet de récupérer le debut du mois suivant la date entrée en paramétre.
	 * @param dateRef date départ.
	 * @return la date de fin du mois précédant.
	 */
	public static Date getDateDebutMoisSuivant(Date dateRef){
	     Date dsLeMoisSuivant=addNdays(getDateFinMoisPrecedant(dateRef),33);
	     return debutDuMois(dsLeMoisSuivant);
    }
	/**permet de récupérer la nième fin de mois précédant la date entrée en paramétre.
	* @param dateRef date depart.
	 * @param n nombre de jours à soustraire.
	 * @return la date finale.*/
	public static Date getNiemeDateFinMoisPrecedant(Date dateRef,int n){
		   Date result = dateRef;
		   for(int i=n ;i>0; i--){			 
			   result = getDateFinMoisPrecedant(result);
		   }
		   return result;
	}
	
	/**permet de formatter la date entrée en paramétre.
	 * @param format format 
	 * @param locale pour la langue
	 * @param date date à formatter
	 * @return date formattée*/
	public static String formateDate(Date date,String format, Locale locale){			
		return new SimpleDateFormat(format,locale).format(date);
	}

	/**permet de formatter la date entrée en paramétre.
	 * @param format format 
	 * @param date date à formatter
	 * @return date formattée*/
	public static String formateDate(Date date,String format){			
		return new SimpleDateFormat(format).format(date);
	}

	
	/**ajoute n jours à une date donnée.
	* @param datDeb date depart.
	 * @param n nombre de jours à ajouter.
	 * @return la date finale.*/
	@SuppressWarnings("static-access")
	public static Date addNdays(Date datDeb,int n){
			Calendar calendar = Calendar.getInstance();
		    calendar.setTime(datDeb);
		    Calendar calendar2 = Calendar.getInstance();
		    calendar2.clear();
		    calendar2.set(calendar.get(calendar.YEAR), calendar.get(calendar.MONTH),calendar.get(calendar.DAY_OF_MONTH)+n);
		    return calendar2.getTime();
	}

	/**ajoute n jours à une date donnée.
	* @param datDeb date depart.
	 * @param n nombre de jours à ajouter.
	 * @return la date finale.*/
	@SuppressWarnings("static-access")
	public static Date addAnnees(Date datDeb,int n){
			Calendar calendar = Calendar.getInstance();
		    calendar.setTime(datDeb);
		    Calendar calendar2 = Calendar.getInstance();
		    calendar2.clear();
		    calendar2.set(calendar.get(calendar.YEAR)+n, calendar.get(calendar.MONTH),calendar.get(calendar.DAY_OF_MONTH));
		    return calendar2.getTime();
	}

	/** permet de constituer une date à partir de l'année, le mois et le jour.
	 * @param annee l'année.
	 * @param mois le mois.
	 * @param jour jour.
	 * @return la date constituée.*/
	public static Date constituerDate(int annee , int mois, int jour){
		    Calendar calendar = Calendar.getInstance();	
		    calendar.clear();
		    calendar.set(annee, mois, jour);
		    return calendar.getTime();
	}
	/**complete une chaine à partir d'une donnée de bourage.
	 * @param source chaine initiale.
	 * @param f caractère de bourage.
	 * @param longeur longueur finale de la chaine.
	 * @param IsBourrageGauche le bourrage se fait -il à gauche?.
	 * @return la chaine finale.*/
	public static String completeStr(String source,char f,int longeur,boolean IsBourrageGauche){
		if(IsBourrageGauche){
		 while(source.length()<longeur){
			 source=f+source;
		 }
		 }else{
			 while(source.length()<longeur){
				 source=source+f;
			 }
		 }
			 
	 return source;
  }
	
	
	/**complete une chaine à partir d'une donnée de bourage.
	 * @param codeSeq en parametre.
	 * @return la chaine finale*/
	public static String cleSequence(int codeSeq){
		String lettreSeq="";
		
		switch (codeSeq % 26) {
		case 0:lettreSeq = "Z";
			break;
		case 1:lettreSeq = "A";
		break;
		case 2:lettreSeq = "B";
		break;
		case 3:lettreSeq = "C";
		break;
		case 4:lettreSeq = "D";
		break;
		case 5:lettreSeq = "E";
		break;
		case 6:lettreSeq = "F";
		break;
		case 7:lettreSeq = "G";
		break;
		case 8:lettreSeq = "H";
		break;
		case 9:lettreSeq = "I";
		break;
		case 10:lettreSeq = "J";
		break;
		case 11:lettreSeq = "K";
		break;
		case 12:lettreSeq = "L";
		break;
		case 13:lettreSeq = "M";
		break;
		case 14:lettreSeq = "N";
		break;
		case 15:lettreSeq = "O";
		break;
		case 16:lettreSeq = "P";
		break;
		case 17:lettreSeq = "Q";
		break;
		case 18:lettreSeq = "R";
		break;
		case 19:lettreSeq = "S";
		break;
		case 20:lettreSeq = "T";
		break;
		case 21:lettreSeq = "U";
		break;
		case 22:lettreSeq = "V";
		break;
		case 23:lettreSeq = "W";
		break;
		case 24:lettreSeq = "X";
		break;
		case 25:lettreSeq = "Y";
		break;

		default:
			break;
		}
		
		
		return lettreSeq;
	}
	
	/**retourne lettre cle modulo 10.
	 * @param codeSeq en parametre.
	 * @return la chaine finale*/
	public static String cleSequenceNumerique(int codeSeq){
		String lettreSeq="";
		
		lettreSeq = ""+(codeSeq % 10);
		
		return lettreSeq;
	}

	/**ecart en jours entre deux dates.
	 * @param deb debut
	 * @param fin fin
	 * @return le nombre de jours.**/	
	public static int ecartEnjours(Date deb,Date fin){	  	  
		  Long nmillis=fin.getTime()-deb.getTime();
		  return Integer.parseInt(Math.round(nmillis*1d/(1000*60*60*24))+"");
	}
		
	/***retourne le nombre jour maximum d'un mois donné.**/
	public static int getNbreJoursDuMois(Date dateRef){		
		return ecartEnjours(getDateFinMoisPrecedant(dateRef),finDuMois(dateRef));
	}
	
	public static String getStrMoisAnnee(int mois, int annee){
		return annee+completeStr(mois+"", '0',2,true);
	}
	/**ajoute x mois à une date donnée.
	 * 
	 * **/
	public static Date addNmois(Date datDeb,int n){		  		  
		  Calendar deb = createCalendarFromDate(datDeb);
		  boolean ok = finDuMois(datDeb).equals(datDeb);
		  deb.add(Calendar.DAY_OF_MONTH, ok ? 1 : 0);//permet de se positionner sur les fins du mois 
		  deb.add(Calendar.MONTH,n);
		  deb.add(Calendar.DAY_OF_MONTH, ok ? -1 : 0);//
		  return deb.getTime();
	}
	
	/**ajoute une période à une date donnée.
	 * cette période peut etre un multiple du mois ou du jour
	 * 
	 * **/
	public static Date addPeriode(Date dateRef ,int coeff,boolean coeffBasedInDays){
		   if(coeffBasedInDays)
			    return ChakaUtils.addNdays(dateRef,coeff);
		   else return ChakaUtils.addNmois(dateRef,coeff);
	}
	
	/**retourne le nombre de périodes entre 2 dates données.
	 * 
	 * **/
	public static int getNbrePeriodEntre2Dates(Date debut,Date fin,int rapport,boolean coeffBasedInDays){
		 int xperiodes = 0;
		 
	     for(int i = 1 ; true ; i++){      
	       debut = ChakaUtils.addPeriode(debut, rapport,coeffBasedInDays);
	       if(debut.equals(fin)){
	    	     xperiodes = i;			     
			     break;
	       }
	       else if(debut.before(fin))
			     continue;
		   else {
			    xperiodes = i - 1;	
			    break ;	 
		   }	  
	   } 
	   return xperiodes;
	 }
	
	/****
	 * teste si la méthode est de type double
	 * @param obj
	 * @return
	 */
	public static boolean isDouble(String obj){
		try{
		Double.parseDouble(obj);
		return true;
		}
		catch(Exception e){return false;}
	}
   /****
    * retourne une chaine en double
    * @param value
    * @return
    */
    public static Double toDouble(String value){
		try{
			return new Double(value);		
		}
		catch(Exception e){return 0d;}
	}
    /*****
     * retourne la valeur formatée
     * @param arg2
     * @return
     */
    public static String getValueFormatted(Object arg2){	
		Double value;
		if(arg2 == null) return null;
		if(ChakaUtils.isDouble(arg2.toString()))
			 value=Double.parseDouble(arg2.toString().trim());
		else value=((Long)arg2).doubleValue();
		String strValue = Math.round(value)+"";		
		String valeur = "";
		for(int i=strValue.length()-1;i >=0;i=i-3){
		String c="";
		for(int j=0;j<3;j++){			
			if(i>=j)								
				c=c+strValue.charAt(i-j);
		}
		valeur=valeur+" "+c;
		}
		String result="";
		for(int i=valeur.length()-1;i>=1;i--){
			result=result+valeur.charAt(i);
		}
		return result;
	}
   
    
   
    public static boolean date1AfterDate2(Date date1, Date date2){
    	Calendar cal1 = createCalendarFromDate(date1);
    	Calendar cal2 = createCalendarFromDate(date2);    	
    	System.out.println(cal1.getTime());
    	System.out.println(cal2.getTime());
    	return cal1.after(cal2);
    }
    
    //renseigne automatiquement le paramétre nommé "logo" ainsi que le paramétre nomé "nomSociete"
    /**
     * 
     * @return
     */
    private Map<String, Object> fillReportParameter() {

		Map<String, Object> parameters = new HashMap<String, Object>();
		ExternalContext ec =	FacesContext.getCurrentInstance().getExternalContext();
		ServletContext sc = (ServletContext)ec.getContext();
		
		InputStream is = sc.getResourceAsStream("/css2/logogstock.jpg");
	
		if (is != null)
		{
			parameters.put("logo", is);
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			parameters.put("logo", null);	
		}

		
		parameters.put("nomSociete", "Caisse Nationale de Securité Sociale (C.N.S.S)");
		

		return parameters;
	}

    /***
     * 
     * @param fileName nom du fichier sans l'extension
     * @return   le fichier s'il est déjà compilé sinon 
     * compile et retourne le fichier
     * @throws JRException
     */
    private JasperReport getCompiledReport(String fileName) throws JRException {
		ExternalContext ec =	FacesContext.getCurrentInstance().getExternalContext();
		ServletContext sc = (ServletContext)ec.getContext();
		File reportFile = new File(sc.getRealPath(fileName + ".jasper"));

		// If compiled file is not found, then compile XML template
		if (!reportFile.exists()) {
			JasperCompileManager.compileReportToFile(sc.getRealPath(fileName + ".jrxml"));
		}

		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportFile);

		return jasperReport;
	}
    
    private JasperPrint getJasperPrint(String repertoire,String reportShortName, Map<String,Object> params, Collection  listeDonnees, String outReportName,Utilisateur utilisateur) throws JRException{
    	//String reportShortName = "cnss_imma";
  	   
  	        net.sf.jasperreports.engine.JasperReport jasperReport = null;
        	String reportFileName = "report/" + repertoire + "/" + reportShortName;
  			
  			jasperReport = getCompiledReport(reportFileName);
  			Map<String, Object> parameters = fillReportParameter();
  			parameters.put("adresse" ,listeParamStock.getInstitution().getAdresseSociete());
  			parameters.put("tel" ,listeParamStock.getInstitution().getTelSociete());
  			parameters.put("email" ,listeParamStock.getInstitution().getEmailSociete());
  			parameters.put("nom" ,listeParamStock.getInstitution().getLibelle());
  			parameters.put("bp" ,listeParamStock.getInstitution().getBpSociete());
  			parameters.put("footer" ,listeParamStock.getInstitution().getSloganSociete());
  			
  			if(utilisateur != null && utilisateur.getIdUtilisateur() != null && utilisateur.getNom() != null && utilisateur.getPrenom() != null){
  				parameters.put("utilisateur",utilisateur.getPrenom()+" "+utilisateur.getNom());
  			}
  			ExternalContext ec =	FacesContext.getCurrentInstance().getExternalContext();
  			ServletContext sc = (ServletContext)ec.getContext();
  			
  			String reportFile = sc.getRealPath("/report" +"/"+repertoire);
  			
             parameters.put("SUBREPORT_DIR", reportFile+"/"); 
  			if(params != null)
  			   parameters.putAll(params);
  			  		
  			JRBeanCollectionDataSource collection = new JRBeanCollectionDataSource(listeDonnees);
  	    	return net.sf.jasperreports.engine.JasperFillManager.fillReport(jasperReport, parameters,collection);
  			
  		
    }
    
    /****
     * 
     * @param parameters  les paramétres de l'état autre que le logo et le nom de la société
     * @param jasperReport
     * @param listeDonnees la liste des données (doit être non null s'il sagit d'un etat multiligne)  
     * @return
     * @throws JRException
     * @throws SQLException
     * @throws IOException
     */
	private byte[] generatePDFOutput(JasperPrint jasperPrint) throws JRException, SQLException, IOException {

		JRPdfExporter exporter = new JRPdfExporter();

		ByteArrayOutputStream os = new ByteArrayOutputStream();

		//exporter.setParameter(JRPdfExporterParameter.IS_ENCRYPTED, Boolean.TRUE);
		exporter.setParameter(JRPdfExporterParameter.METADATA_SUBJECT, "Impression");
		exporter.setParameter(JRPdfExporterParameter.METADATA_TITLE, "Immatriculations");
		//exporter.setParameter(JRPdfExporterParameter.USER_PASSWORD, "Progis@p");
		//exporter.setParameter(JRPdfExporterParameter.PROPERTY_USER_PASSWORD, "Progis@p");
		exporter.setParameter(JRPdfExporterParameter.METADATA_CREATOR, "Chaka");
		exporter.setParameter(JRPdfExporterParameter.METADATA_AUTHOR, "Chaka");
		exporter.setParameter(JRPdfExporterParameter.IS_COMPRESSED, Boolean.TRUE);
		exporter.setParameter(JRPdfExporterParameter.PERMISSIONS, new Integer(PdfWriter.HideMenubar | PdfWriter.HideToolbar | PdfWriter.ALLOW_PRINTING));
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF-8");
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, os);

		exporter.exportReport();

		byte[] bytes = os.toByteArray();
		os.close();

		return bytes;

	}


    private static void exportReport(JRAbstractExporter exporter, JasperPrint jasperPrint, PrintWriter out) throws JRException {
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
        
        exporter.exportReport();
      }

      public static void generateReportAsHtml(JasperPrint jasperPrint, PrintWriter out) throws JRException {
        JRHtmlExporter exporter = new JRHtmlExporter();  
        exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.FALSE);
        exporter.setParameter(JRHtmlExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
        exporter.setParameter(JRHtmlExporterParameter.CHARACTER_ENCODING, "ISO-8859-9");
        
        exportReport(exporter, jasperPrint, out);
      }    
      
      
    /****
     * @param repertoire le repertoire créé dans le dossier report du projet
     * @param reportShortName  le nom de l'état sans l'extension
     * @param les paramétres personnels autres que le logo et le nom de la société si vide null
     * @param listeDonnees   la liste s'il sagit d'un état multi-ligne sinon null
     * @param outReportName le nom du fichier généré
     * @return
     */
      /*    imprimer version 1 */
    public String imprimerPDF(String repertoire,String reportShortName, Map<String,Object> params, Collection  listeDonnees, String outReportName,Utilisateur utilisateur)
    { 	   
 	   //String reportShortName = "cnss_imma";
 	   
 	   net.sf.jasperreports.engine.JasperReport jasperReport = null;
        try{
 		try {
 			JasperPrint jasperPrint = getJasperPrint(repertoire, reportShortName, params, listeDonnees, outReportName, utilisateur);
 			byte[] bytes = generatePDFOutput(jasperPrint);
 			
 			ExternalContext ec =	FacesContext.getCurrentInstance().getExternalContext();
 			ServletContext sc = (ServletContext)ec.getContext();
 			FacesContext context = FacesContext.getCurrentInstance();
 			HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
 			if(outReportName.indexOf("pdf") == -1) outReportName += ".pdf";
 			response.addHeader("Content-disposition", "attachment;filename=\"" + outReportName + "\"");
 			response.setContentLength(bytes.length);
 			try {
 				response.getOutputStream().write(bytes);
 			} catch (IOException e) {
 				//e.printStackTrace();
 			}
 			response.setContentType("application/pdf");
 			context.responseComplete();

 			//return bytes;

 		} catch (JRException e) {
 			e.printStackTrace();
 			FacesMessages.instance().addToControl("erreurGenerique", "Une erreur est survenue lors de l'impression");
 		} catch (SQLException e) {
 			e.printStackTrace();
 			FacesMessages.instance().addToControl("erreurGenerique", "Une erreur est survenue lors de l'impression");
 		} catch (IOException e) {
 			e.printStackTrace();
 			FacesMessages.instance().addToControl("erreurGenerique", "Une erreur est survenue lors de l'impression");
 		}
        }catch(Exception ex){
        	System.out.println("erreur rapport");
        	ex.printStackTrace();
        }
 	   
 	   
 	   return "";
    }
      
      /****
       * @param repertoire le repertoire créé dans le dossier report du projet
       * @param reportShortName  le nom de l'état sans l'extension
       * @param listeDonnees   la liste s'il sagit d'un état multi-ligne sinon null
       * @param outReportName le nom du fichier généré
       * @param utilisateur le nom de l'utilisateur 
       * @return  .
       */
    public byte[] getContentBytesPdf(String repertoire,String reportShortName, Map<String,Object> params, Collection  listeDonnees, String outReportName,Utilisateur utilisateur)
    { 	  
 	   //String reportShortName = "cnss_imma";
    	byte[] bytes = null;
        try{
 		try {
 			
 			JasperPrint jasperPrint = getJasperPrint(repertoire, reportShortName, params, listeDonnees, outReportName, utilisateur);
 		     bytes = generatePDFOutput(jasperPrint);

 		} catch (JRException e) {
 			e.printStackTrace();
 			FacesMessages.instance().addToControl("erreurGenerique", "Une erreur est survenue lors de l'impression");
 		} catch (SQLException e) {
 			e.printStackTrace();
 			FacesMessages.instance().addToControl("erreurGenerique", "Une erreur est survenue lors de l'impression");
 		} catch (IOException e) {
 			e.printStackTrace();
 			FacesMessages.instance().addToControl("erreurGenerique", "Une erreur est survenue lors de l'impression");
 		}
        }catch(Exception ex){
        	System.out.println("erreur rapport");
        	ex.printStackTrace();
        }
 	   
 	   
 	   return bytes;
    }


    public void generateOtherReport(String repertoire,String reportShortName, Map<String,Object> params, Collection  listeDonnees, String outReportName,Utilisateur utilisateur,ExportOption exportOption)
    { 	  
 	   //String reportShortName = "cnss_imma";
    	byte[] bytes = null;
        try{
 		try {
 			JasperPrint jasperPrint = getJasperPrint(repertoire, reportShortName, params, listeDonnees, outReportName, utilisateur);
 		    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
 		    HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
 		    HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
 		    if(exportOption.equals(ExportOption.HTML))
 				generateReportAsHtml(jasperPrint, response.getWriter());
 			else {
// 				  JRXlsExporter exporter = new JRXlsExporter(); 
// 			  	  exportReport(exporter, jasperPrint, response.getWriter());
 			      request.getSession().setAttribute(BaseHttpServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint);    
 			      response.sendRedirect(request.getContextPath()+"/servlets/report/"+exportOption); 				
 			}
 		    FacesContext.getCurrentInstance().responseComplete();

 		} catch (JRException e) {
 			e.printStackTrace();
 			FacesMessages.instance().addToControl("erreurGenerique", "Une erreur est survenue lors de l'impression");
 		}catch (IOException e) {
 			e.printStackTrace();
 			FacesMessages.instance().addToControl("erreurGenerique", "Une erreur est survenue lors de l'impression");
 		}
        }catch(Exception ex){
        	System.out.println("erreur rapport");
        	ex.printStackTrace();
        }
    }


	
	public static void main(String arg[]){
//		System.out.println(date1AfterDate2(new Date(), constituerDate(2013, 11, 1)));
		StringBuffer requeteBuffer = new StringBuffer("from Personne p ");
		System.out.println("Test1:"+requeteBuffer);
		requeteBuffer.append(" champ1 ");
		System.out.println("Test2:"+requeteBuffer);
		requeteBuffer.append(" champ2 ");
		requeteBuffer.append(" champ3 ");
		requeteBuffer.append(" champ4");
		requeteBuffer.append(55);
		System.out.println("Test3:"+requeteBuffer);

	}
	  public String getNumberWithMD5(String input){
			 try {
		            //String vpc_SecureHash="5DB0F8628C4801B2ds10FD52056af367";                  
		            MessageDigest m=MessageDigest.getInstance("MD5");  
		            m.update(input.getBytes(),0,input.length());  
		            String securedHash = new BigInteger(1,m.digest()).toString(16);  
		            return securedHash;
		        } catch (NoSuchAlgorithmException ex) {
		            Logger.getLogger(ChakaUtils.class.getName()).log(Level.SEVERE, null, ex);
		            return null;
		        }

	   }
	    public static String getViewParameter(String name) {
	        FacesContext fc = FacesContext.getCurrentInstance();
	        String param = (String) fc.getExternalContext().getRequestParameterMap().get(name);
	        if (param != null && param.trim().length() > 0) {
	            return param;
	        } else {
	            return null;
	        }
	    }
		
		 /*
	     * recupere un objet depuis la session
	     * 
	     */
	    public Object getAttribute(String idAttribut){
	    	  return((HttpSession ) FacesContext.getCurrentInstance().getExternalContext()
	    	   .getSession(false)).getAttribute(idAttribut);
	    }
	    
	    /*
	     * sauvegarde un objet ds la session
	     */
	    
	    public void setAttribute(String idAttribut,Object obj){
	    	  ((HttpSession ) FacesContext.getCurrentInstance().getExternalContext()
	    	   .getSession(false)).setAttribute(idAttribut,obj);
	    }
	    
	    public static void bindeule(String wax) {
	    	System.out.println("************************************** / "+wax+" / **************************************** ");
	    }
}
