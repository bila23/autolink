/*----------------------------------------------------------
* FILE: LoginController.java
* PRODUCT: sisinv
*----------------------------------------------------------
* IMPORTANT NOTICE
* This program is property of Terzus
* Its unauthorized use, as any code alteration without authorization 
* is prohibited
*----------------------------------------------------------
*/
package com.terzus.autolink.commons;

import com.bila.framework.commons.FacesHelper;
import com.terzus.autolink.model.Usuario;
import com.terzus.autolink.service.UsuarioService;
import com.terzus.autolink.vo.LoginVO;
import javax.inject.Named;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Terzus
 * <b>Created by: </b>will
 * <b>For: </b>sisinv
 * <b>On: </b>Feb 22, 2020 2:52:39 PM
 * <b>Purpose</b> 
 * <p>
 *      Controlador para login del sistema
 * </p>
 */
@Named(value="loginController")
@RequestScoped
@Slf4j
public class LoginController implements Serializable{

    @Getter @Setter private LoginVO vo;
    @Inject private UsuarioService userService;
    
    public LoginController(){
        vo = new LoginVO();
    }
    
    public String login(){
        if(vo.getUsername() == null || vo.getUsername().equals(""))
            FacesHelper.warningMessage("Debe ingresar su usuario para poder iniciar sesion");
        else if(vo.getPassword() == null || vo.getPassword().equals(""))
            FacesHelper.warningMessage("Debe ingresar su contrasena para poder iniciar sesion");
        else{
            try{
                String user = vo.getUsername().toLowerCase();
                //VERIFICO SI EL USUARIO EXISTE
                Usuario model = userService.findByUser(user);
                if(model == null){
                    FacesHelper.errorMessage("El usuario que ha ingresado no existe");
                    return "index";
                }else if(model.getEstado().equals("I")){
                    FacesHelper.errorMessage("Su usuario se encuentra inactivo, no tiene permisos para ingresar al sistema");
                    return "index";      
                }else if(model.getIdtipo() == null || model.getIdtipo() == 0){
                    FacesHelper.errorMessage("Su usuario no posee ningun rol, favor reportarlo al administrador del sistema");
                    return "index";
                }else{
                    HttpServletRequest request = FacesHelper.getRequest();
                    request.login(user, vo.getPassword());
                    
                    //VERIFICO EL ROL DEL USUARIO, ASI LO REDIRECCIONO A UNA U OTRA PAGINA
                    if(model.getIdtipo() == 1)
                        return "/html/private/admin/index.xhtml?faces-redirect=true";
                    else if(model.getIdtipo() == 2)
                        return "/html/private/aseguradora/index.xhtml?faces-redirect=true";
                    else if(model.getIdtipo() == 3)
                        return "/html/private/taller/index.xhtml?faces-redirect=true";
                    else if(model.getIdtipo() == 4)
                        return "/html/private/proveedor/index.xhtml?faces-redirect=true";
                    else if(model.getIdtipo() == 5)
                        return "/html/private/cliente/index.xhtml?faces-redirect=true";
                }
            }catch(Exception e){
                FacesHelper.errorMessage("Su usuario y/o contrasena son incorrectos");
                return "index";
            }
        }
        return "index";
    }
    
}