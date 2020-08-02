/*---------------------------------------------------------
* FILE: AsegCrudController.java
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
import com.terzus.autolink.model.Aseguradora;
import com.terzus.autolink.model.Usuario;
import com.terzus.autolink.service.AseguradoraService;
import com.terzus.autolink.service.UsuarioService;
import com.terzus.autolink.vo.AseguradoraVO;
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
 * <b>On: </b>Aug 1, 2020 5:16:00 PM
 * <b>Purpose</b> 
 * <p>
 *      
 * </p>
 */
@Named(value = "asegCrudController")
@ViewScoped
@Slf4j
public class AsegCrudController implements Serializable{
    
    @Inject private AseguradoraService service;
    @Inject private UsuarioService userService;
    @Getter @Setter private AseguradoraVO vo;
    @Getter @Setter private Aseguradora modelView;
    @Getter @Setter private Aseguradora modelUpdate;
    @Getter @Setter private Aseguradora modelDelete;
    @Getter @Setter private List<Usuario> userList;
    @Getter @Setter private List<Aseguradora> list;
    @Getter @Setter private List<Aseguradora> filteredList;
    
    @PostConstruct
    public void init(){
        try{
            vo = new AseguradoraVO();
            modelUpdate = new Aseguradora();
            userList = userService.findUserAseguradora();
            find();
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.error("Ha ocurrido un error al tratar de mostrar las aseguradoras");
        }
    }
    
    public void find(){
        try{
            list = service.findAllWithNamedQuery();
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.error("Ha ocurrido un error al tratar de mostrar las aseguradoras");
        }
    }
    
    public void save(){
        try{
            vo.setUsuariocrea(FacesHelper.getUserLogin());
            service.saveVO(vo);
            vo = new AseguradoraVO();
            FacesHelper.success("Se ha guardado correctamente la aseguradora");
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.error("Ha ocurrido un error al tratar de guardar la aseguradora");
        }
    }
    
    public void update(){
        try{
            if(modelUpdate != null){
                service.update(modelUpdate);
                FacesHelper.success("Se ha actualizado correctamente la aseguradora");
                find();
            }
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.error("Ha ocurrido un error al tratar de actualizar la aseguradora");
        }
    }
    
    public void delete(){
        try{
            if(modelDelete != null){
                modelDelete.setEstado("I");
                FacesHelper.success("Se ha inactivado correctamente la aseguradora");
            }
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.error("Ha ocurrido un error al tratar de inactivar la aseguradora");
        }
    }
    
    public String getUser(int user){
        try{
            return userService.findNameUser(user);
        }catch(Exception e){
            log.error(e.getMessage(), e);
        }
        return "";
    }

}