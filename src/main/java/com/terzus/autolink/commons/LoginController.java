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
                
                /*Usuario admin = new Usuario();
                Usuario taller = new Usuario();
                Usuario prov = new Usuario();
                Usuario aseg = new Usuario();
                
                admin.setFechacreacion(new Date());
                admin.setEstado(1);
                admin.setIdtipo(1);
                admin.setNombre("Usuario administrador");
                admin.setPass("admin");
                admin.setUser("admin@gmail.com");
                admin.setUsuariocrea("WJUAREZ");
                
                taller.setFechacreacion(new Date());
                taller.setEstado(1);
                taller.setIdtipo(3);
                taller.setNombre("Usuario taller");
                taller.setPass("taller");
                taller.setUser("taller@gmail.com");
                taller.setUsuariocrea("WJUAREZ");
                
                prov.setFechacreacion(new Date());
                prov.setEstado(1);
                prov.setIdtipo(4);
                prov.setNombre("Usuario prov");
                prov.setPass("prov");
                prov.setUser("prov@gmail.com");
                prov.setUsuariocrea("WJUAREZ");
                
                
                aseg.setFechacreacion(new Date());
                aseg.setEstado(1);
                aseg.setIdtipo(2);
                aseg.setNombre("Usuario aseg");
                aseg.setPass("aseg");
                aseg.setUser("aseg@gmail.com");
                aseg.setUsuariocrea("WJUAREZ");
                
                userService.save(admin);
                userService.save(taller);
                userService.save(prov);
                userService.save(aseg);*/
                
                //VERIFICO SI EL USUARIO EXISTE
                Usuario model = userService.findByUser(user);
                if(model == null){
                    FacesHelper.errorMessage("El usuario que ha ingresado no existe");
                    return "init";
                }else if(model.getEstado() == 0){
                    FacesHelper.errorMessage("Su usuario se encuentra inactivo, no tiene permisos para ingresar al sistema");
                    return "init";                    
                }else{
                    HttpServletRequest request = FacesHelper.getRequest();
                    request.login(user, vo.getPassword());
                    return "/html/private/index.xhtml?faces-redirect=true";
                }
            }catch(Exception e){
                FacesHelper.errorMessage("Su usuario y/o contrasena son incorrectos");
                return "init";
            }
        }
        return "init";
    }
    
}