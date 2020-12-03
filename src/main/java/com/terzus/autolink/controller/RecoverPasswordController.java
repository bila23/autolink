package com.terzus.autolink.controller;

import com.bila.framework.commons.FacesHelper;
import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.resource.Emailv31;
import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;

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
    private String API_KEY = "3e8e4b3133dca6613258b441ea9be238";
    private String API_KEY_PRIVATE = "b5c84504d510891527419f202558f779";
    
    public void send(){
        try{
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
        }
    }
}