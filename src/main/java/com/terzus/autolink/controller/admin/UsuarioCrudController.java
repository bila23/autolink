/*---------------------------------------------------------
* FILE: UsuarioCrudController.java
* PRODUCT: autolink
*----------------------------------------------------------
* IMPORTANT NOTICE
* This program is property of Terzus
* Its unauthorized use, as any code alteration without
* authorization is prohibited
*----------------------------------------------------------
*/
package com.terzus.autolink.controller.admin;

import com.bila.framework.commons.FacesHelper;
import com.terzus.autolink.model.Usuario;
import com.terzus.autolink.service.UsuarioService;
import com.terzus.autolink.vo.UsuarioVO;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
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
 * <b>On: </b>Jul 29, 2020 3:20:41 PM
 * <b>Purpose</b> 
 * <p>
 *      
 * </p>
 */
@Named(value = "usuarioCrudController")
@ViewScoped
@Slf4j
public class UsuarioCrudController implements Serializable{

    @Inject private UsuarioService service;
    @Getter @Setter private UsuarioVO vo;
    @Getter @Setter private Usuario userView;
    @Getter @Setter private Usuario userUpdate;
    @Getter @Setter private Usuario userDelete;
    @Getter @Setter private List<Usuario> list;
    @Getter @Setter private List<Usuario> filteredList;
    
    @PostConstruct
    public void init(){
        vo = new UsuarioVO();
        find();
    }
    
    public void find(){
        try{
            list = service.findAllWithNamedQuery();
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.error("Ha ocurrido un error al tratar de mostrar los usuarios");
        }        
    }
    
    public void save(){
        try{
            boolean existUser = service.existUser(vo.getUser());
            if(existUser)
                FacesHelper.warning("Ya existe un registro que posee ese usuario");
            else{
                vo.setUsuariocrea(FacesHelper.getUserLogin());
                service.saveVO(vo);
                vo = new UsuarioVO();
                FacesHelper.success("Se ha guardado correctamente el usuario");
            }
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.error("Ha ocurrido un error al tratar de mostrar los usuarios");
        }        
    }
    
    public void update(){
        try{
            if(userUpdate != null){
                service.update(userUpdate);
                FacesHelper.success("Se ha actualizado el usuario correctamente");
            }
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.error("Ha ocurrido un error al tratar de actualizar el usuario");
        }        
    }
    
    public void delete(){
        try{
            if(userDelete != null){
                userDelete.setEstado("I");
                service.update(userDelete);
                FacesHelper.success("Se ha inactivado el usuario correctamente");
                find();
            }
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.error("Ha ocurrido un error al tratar de inactivar el usuario");
        }        
    }
}
