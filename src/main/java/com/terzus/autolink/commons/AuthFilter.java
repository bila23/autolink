/*----------------------------------------------------------
* FILE: AuthFilter.java
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
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Terzus
 * <b>Created by </b>WJuarez
 * <b>for </b>sisinv
 * <b>package </b>com.terzus.sisinv.commons
 * <b>on </b> 02-24-2020 08:33:20 AM
 * <b>Purpose</b> 
 * <p>
 *      Clase que sirve como filtro de peticiones HTTP para verificar
 *      que el usuario esta logueado. En caso contrario se redirecciona
 *      a la pantalla de login
 * </p>
 */
public class AuthFilter implements Filter{

    private String init;
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        if (filterConfig != null)
            init = filterConfig.getInitParameter("init_page");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        boolean flag = true;

        if(!req.isRequestedSessionIdValid())
            flag = false;
        else if (req.getUserPrincipal() == null)
            flag = false;
        else if (req.getUserPrincipal().getName() == null)
            flag = false;
        
        if(flag)
            chain.doFilter(request, response);
        else{
            HttpServletResponse resp = (HttpServletResponse) response;
            resp.sendRedirect(init);
        }
    }

    @Override
    public void destroy() {
        if(init != null)
            init = null;
    }

}