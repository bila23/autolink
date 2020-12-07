/*---------------------------------------------------------
* FILE: NewPasswordController.java
* PRODUCT: autolink
*----------------------------------------------------------
* IMPORTANT NOTICE
* This program is property of Terzus
* Its unauthorized use, as any code alteration without
* authorization is prohibited
*----------------------------------------------------------
*/
package com.terzus.autolink.controller;

import com.bila.framework.commons.FacesHelper;
import com.terzus.autolink.model.RecuperarClave;
import com.terzus.autolink.service.RecuperarClaveService;
import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Terzus
 * <b>Created by: </b>will
 * <b>For: </b>autolink
 * <b>On: </b>Dec 5, 2020 12:03:01 PM
 * <b>Purpose</b> 
 * <p>
 *      
 * </p>
 */
@Named(value="newPasswordController")
@ViewScoped
@Slf4j
public class NewPasswordController implements Serializable {
    
    @Inject private RecuperarClaveService recuperarClaveService;
    @Getter @Setter private String clave;
    @Getter @Setter private String newPassword;
    
    public void changePassword(){
        try{
            if(clave != null){
                RecuperarClave model = recuperarClaveService.findByClaveAndState(clave, "P");
                if(model == null){
                    FacesHelper.warning("Ha ocurrido un error inesperado. Intente nuevamente acceder al link, si este ya fue utilizado debera solicitar una nueva contraseña");
                    return;
                }
                recuperarClaveService.changePassword(model, newPassword);
                FacesHelper.success("Ha cambiado su contraseña correctamente");
            }
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.errorMessage("Ha ocurrido un error al cambiar la contraseña");
        }
    }

}
