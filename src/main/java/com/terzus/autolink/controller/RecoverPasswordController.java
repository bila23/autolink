package com.terzus.autolink.controller;

import com.bila.framework.commons.FacesHelper;
import com.terzus.autolink.model.RecuperarClave;
import com.terzus.autolink.service.RecuperarClaveService;
import com.terzus.autolink.service.UsuarioService;
import java.io.Serializable;
import java.util.Date;
import java.util.Properties;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * @author terzus
 * <b>Created by </b>WJuarez
 * <b>for </b>autolink
 * <b>package </b>com.terzus.autolink.controller
 * <b>on </b> 12-03-2020 09:00:53 AM
 * <b>Purpose</b> 
 * <p>
 *  
 * </p>
 */
@Named(value="recoverPasswordController")
@ViewScoped
@Slf4j
public class RecoverPasswordController implements Serializable{

    @Getter @Setter private String userMail;
    @Inject private RecuperarClaveService recuperarClaveService;
    @Inject private UsuarioService usuarioService;
    private final String FROM = "help.autolink@gmail.com";
    
    public void send(){
        try{
            //VERIFICO SI EL USUARIO ESTA EN EL SISTEMA
            boolean exist = usuarioService.existUser(userMail);
            if(!exist){
                FacesHelper.warningMessage("El usuario que ha ingresado no existe en el sistema");
                return;
            }

            //GUARDO LA CLAVE
            RecuperarClave model = new RecuperarClave();
            model.setCreacion(new Date());
            model.setEstado("P");
            model.setUsuario(userMail.toLowerCase());
            String randomString = randomString();
            model.setClave(randomString);
            recuperarClaveService.save(model);

            //MANDO EL CORREO
            String url = "http://localhost:8080/autolink/newPassword.xhtml?q=".concat(randomString);
            String subject = "Autolink - Recuperar contraseña";
            String text = "<html><body>Buen día<br/>Ha solicitado un cambio de contraseña, favor ingrese a este link para realizarlo.<br/><a href='".concat(url).concat("'>[Cambiar contraseña]</a></body></html>");
            sendMail(FROM, userMail, subject, text);
            FacesHelper.success("Se ha enviado un correo a tu cuenta para recuperar la contraseña");
        }catch(Exception e){
            log.error(e.getMessage(), e);
            System.out.println(e.getMessage());
            FacesHelper.errorMessage("Ha ocurrido un error al tratar de enviar la notificación");
        }
    }
    
    private void sendMail(String from, String to, String subject, String body) throws Exception {
        String host = "smtp.gmail.com";
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM, "autolinkhelp20");
            }
        });
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject(subject);
        message.setContent(body, "text/html; charset=utf-8");
        Transport.send(message);
    }
    
    private String randomString(){
        return RandomStringUtils.randomAlphanumeric(40);
    }
}