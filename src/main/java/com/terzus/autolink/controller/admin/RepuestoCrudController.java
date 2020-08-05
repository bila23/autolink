/*---------------------------------------------------------
* FILE: RepuestoCrudController.java
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
import com.terzus.autolink.model.Repuesto;
import com.terzus.autolink.service.RepuestoService;
import java.io.Serializable;
import java.util.Date;
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
 * <b>On: </b>Aug 4, 2020 7:55:59 PM
 * <b>Purpose</b> 
 * <p>
 *      
 * </p>
 */
@Named(value = "repCrudController")
@ViewScoped
@Slf4j
public class RepuestoCrudController implements Serializable{

    @Inject private RepuestoService service;
    @Getter @Setter private Repuesto model;
    @Getter @Setter private Repuesto modelUpdate;
    @Getter @Setter private Repuesto modelDelete;
    @Getter @Setter private List<Repuesto> list;
    @Getter @Setter private List<Repuesto> filteredList;
    
    @PostConstruct
    public void init(){
        try{
            model = new Repuesto();
            modelUpdate = new Repuesto();
            find();
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.error("Ha ocurrido un error al iniciar la pantalla");
        }
    }
    
    public void find(){
        try{
            list = service.findAllWithNamedQuery();
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.error("Ha ocurrido un error al recuperar los repuestos");
        }
    }

    public void save(){
        try{
            model.setEstado("A");
            model.setFechacreacion(new Date());
            model.setUsuariocrea(FacesHelper.getUserLogin());
            service.save(model);
            FacesHelper.success("Se ha guardado correctamente el repuesto");
            model = new Repuesto();
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.error("Ha ocurrido un error al guardar el repuesto");
        }
    }
    
    public void update(){
        try{
            if(modelUpdate != null){
                service.update(modelUpdate);
                FacesHelper.success("Se ha actualizado correctamente el repuesto");
                find();
            }
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.error("Ha ocurrido un error al tratar de actualizar el repuesto");
        }
    }
    
    public void delete(){
        try{
            if(modelDelete != null){
                modelDelete.setEstado("I");
                FacesHelper.success("Se ha inactivado el repuesto correctamente");
            }
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.error("Ha ocurrido un error al tratar de inactivar el repuesto");
        }
    }
    
}
