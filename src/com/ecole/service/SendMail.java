package com.ecole.service;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
public class SendMail 
{
   public void sendMail(String mailDest , String mes,String subject){
	   final String username = "antoumanediouf@gmail.com";
       final String password = "senegal12@@";

       Properties props = new Properties();
       props.put("mail.smtp.auth", true);
       props.put("mail.smtp.starttls.enable", true);
       props.put("mail.smtp.host", "smtp.gmail.com");
       props.put("mail.smtp.port", "587");

       Session session = Session.getInstance(props,
               new javax.mail.Authenticator() {
                   protected PasswordAuthentication getPasswordAuthentication() {
                       return new PasswordAuthentication(username, password);
                   }
               });

       try {
           Message message = new MimeMessage(session);
           message.setFrom(new InternetAddress("antoumanediouf@gmail.com"));
           message.setRecipients(Message.RecipientType.TO,
                   InternetAddress.parse(mailDest));
           message.setSubject(subject);
           message.setText(mes);
           Transport.send(message);
       } catch (MessagingException e) {
           throw new RuntimeException(e);
       }
   }
}