package com.terzus.autolink.controller;

import com.bila.framework.commons.FacesHelper;
import com.terzus.autolink.model.RecuperarClave;
import com.terzus.autolink.service.RecuperarClaveService;
import com.terzus.autolink.service.UsuarioService;
import java.io.Serializable;
import java.util.Date;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.Message;
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

    @Resource(name = "mail/cel")
    private Session mailSession;
    @Getter @Setter private String userMail;
    @Inject private RecuperarClaveService recuperarClaveService;
    @EJB private UsuarioService usuarioService;
    
    /*private String API_KEY = "3e8e4b3133dca6613258b441ea9be238";
    private String API_KEY_PRIVATE = "b5c84504d510891527419f202558f779";*/
    
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
            String from = "help.autolink@gmail.com";
            String subject = "Autolink - Recuperar contraseña";
            String text = "<html><body>Buen día<br/>Ha solicitado un cambio de contraseña, favor ingrese a este link para realizarlo.<br/><a href=''>[Cambiar contraseña]</a></body></html>";
            sendMail(from, userMail, subject, text);
        }catch(Exception e){
            log.error(e.getMessage(), e);
            System.out.println(e.getMessage());
            FacesHelper.errorMessage("Ha ocurrido un error al tratar de enviar la notificación");
        }
        /*try{
            MailjetClient client;
            MailjetRequest request;
            MailjetResponse response;
            client = new MailjetClient(API_KEY, API_KEY_PRIVATE, new ClientOptions("v3.1"));
            request = new MailjetRequest(Emailv31.resource)
                  .property(Emailv31.MESSAGES, new JSONArray()
                      .put(new JSONObject()
                          .put(Emailv31.Message.FROM, new JSONObject()
                              .put("Email", "help.autolink@gmail.com")
                              .put("Name", "Mailjet Pilot"))
                          .put(Emailv31.Message.TO, new JSONArray()
                              .put(new JSONObject()
                                  .put("Email", userMail)
                                  .put("Name", "passenger 1")))
                          .put(Emailv31.Message.SUBJECT, "Your email flight plan!")
                          .put(Emailv31.Message.TEXTPART, "Dear passenger 1, welcome to Mailjet! May the delivery force be with you!")
                          .put(Emailv31.Message.HTMLPART, "<h3>Dear passenger 1, welcome to <a href='https://www.mailjet.com/'>Mailjet</a>!</h3><br />May the delivery force be with you!")));
            response = client.post(request);
            System.out.println(response.getStatus());
            System.out.println(response.getData());
        }catch(Exception e){
            log.error(e.getMessage(), e);
            System.out.println(e.getMessage());
            FacesHelper.errorMessage("Ha ocurrido un error al tratar de enviar la notificación");
        }*/
    }
    
    private void sendMail(String from, String to, String subject, String body) throws Exception {
        MimeMessage message = new MimeMessage(mailSession);
        message.setFrom(new InternetAddress(from));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject(subject);
        message.setSentDate(new Date());
        message.setText(body, "utf-8", "html");
        Transport.send(message);
        message = null;
    }
    
    private String randomString(){
        return RandomStringUtils.randomAlphanumeric(20);
    }
}