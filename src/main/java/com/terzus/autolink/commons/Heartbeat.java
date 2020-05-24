/*---------------------------------------------------------
* FILE: Heartbeat.java
* PRODUCT: sisinv
*----------------------------------------------------------
* IMPORTANT NOTICE
* This program is property of Terzus
* Its unauthorized use, as any code alteration without authorization 
* is prohibited
*----------------------------------------------------------
*/
package com.terzus.autolink.commons;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Terzus
 * <b>Created by: </b>will
 * <b>For: </b>sisinv
 * <b>On: </b>Feb 23, 2020 7:22:49 PM
 * <b>Purpose</b> 
 * <p>
  *      Controlador que reactiva la sesion del usuario
 * </p>
 */
@Named
@RequestScoped
public class Heartbeat implements Serializable{

    public void keepUserSessionAlive() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        request.getSession().setMaxInactiveInterval(10 * 60);
    }
}