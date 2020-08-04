/*---------------------------------------------------------
* FILE: ProvCrudController.java
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
import com.terzus.autolink.model.Proveedor;
import com.terzus.autolink.model.Usuario;
import com.terzus.autolink.service.ProveedorService;
import com.terzus.autolink.vo.ProveedorVO;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;

/**
 * @author Terzus
 * <b>Created by: </b>will
 * <b>For: </b>autolink
 * <b>On: </b>Aug 2, 2020 4:51:55 PM
 * <b>Purpose</b> 
 * <p>
 *      
 * </p>
 */
@Named(value = "provCrudController")
@ViewScoped
@Slf4j
public class ProvCrudController implements Serializable{

    @Inject private ProveedorService service;
    @Getter @Setter private ProveedorVO vo;
    @Getter @Setter private Proveedor modelView;
    @Getter private Proveedor mupdate;
    @Getter @Setter private ProveedorVO modelUpdate;
    @Getter @Setter private Proveedor modelDelete;
    @Getter @Setter private List<Proveedor> list;
    @Getter @Setter private List<Proveedor> filteredList;
    @Getter @Setter private List<Usuario> userList;
    
    @PostConstruct
    public void init(){
        try{
            find();
            userList = service.findUserList();
            vo = new ProveedorVO();
            modelUpdate = new ProveedorVO();
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.error("Ha ocurrido un error al tratar de mostrar los proveedores");
        }
    }
    
    public void find(){
        try{
            list = service.findAllWithNamedQuery();
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.error("Ha ocurrido un error al tratar de mostrar los proveedores");
        }
    }
    
    public void save(){
        try{
            vo.setUsuariocrea(FacesHelper.getUserLogin());
            service.saveVO(vo);
            vo = new ProveedorVO();
            FacesHelper.success("Se ha guardado el proveedor correctamente");
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.error("Ha ocurrido un error al tratar de guardar un proveedor");
        }
    }
    
    public void update(){
        try{
            if(modelUpdate != null){
                service.update(modelUpdate);
                FacesHelper.success("Se ha actualizado correctamente el proveedor");
                find();
            }
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.error("Ha ocurrido un error al tratar de actualizar el proveedor");
        }
    }
    
    public void delete(){
        try{
            if(modelDelete != null){
                modelDelete.setEstado("I");
                service.update(modelDelete);
                FacesHelper.success("Se ha inactivado correctamente el proveedor");
            }
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.error("Ha ocurrido un error al tratar de inactivar el proveedor");
        }
    }

    public void setMupdate(Proveedor mupdate) {
        this.mupdate = mupdate;
        try{
            modelUpdate = new ProveedorVO();
            PropertyUtils.copyProperties(modelUpdate, mupdate);
            modelUpdate.setIdUsuario(mupdate.getIdusuario().getId());
        }catch(Exception e){
            log.error(e.getMessage(), e);
        }
    }
    
}
