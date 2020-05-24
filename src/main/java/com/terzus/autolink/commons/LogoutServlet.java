/*---------------------------------------------------------
* FILE: LogoutServlet.java
* PRODUCT: sisinv
*----------------------------------------------------------
* IMPORTANT NOTICE
* This program is property of Terzus
* Its unauthorized use, as any code alteration without authorization 
* is prohibited
*----------------------------------------------------------
*/
package com.terzus.autolink.commons;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Terzus
 * <b>Created by: </b>will
 * <b>For: </b>sisinv
 * <b>On: </b>Feb 23, 2020 7:00:49 PM
 * <b>Purpose</b> 
 * <p>
 *      Servlet para salir de la aplicacion
 * </p>
 */
@WebServlet(value="/logoutServlet")
public class LogoutServlet extends HttpServlet{

    private static final long serialVersionUID = 1L;
    private final static Logger log = LoggerFactory.getLogger(LogoutServlet.class);

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
        try{
            request.getSession().invalidate();
            request.logout();
            response.sendRedirect(request.getContextPath());
        }catch(IOException | ServletException e){
            log.error(e.getMessage(), e);
        }
    }
}