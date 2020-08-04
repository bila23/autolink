/*---------------------------------------------------------
* FILE: MarcaCrudController.java
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
import com.terzus.autolink.model.Marca;
import com.terzus.autolink.service.MarcaService;
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
 * <b>On: </b>Aug 3, 2020 8:40:44 PM
 * <b>Purpose</b> 
 * <p>
 *      
 * </p>
 */
@Named(value = "marcaCrudController")
@ViewScoped
@Slf4j
public class MarcaCrudController implements Serializable{
    
    @Inject private MarcaService service;
    @Getter @Setter private Marca model;
    @Getter @Setter private Marca modelUpdate;
    @Getter @Setter private Marca modelDelete;
    @Getter @Setter private List<Marca> list;
    @Getter @Setter private List<Marca> filteredList;
    
    @PostConstruct
    public void init(){
        try{
            find();
            model = new Marca();
            modelUpdate = new Marca();
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.error("Ha ocurrido un error al tratar de mostrar las marcas");
        }
    }
    
    public void find(){
        try{
            list = service.findAllWithNamedQuery();
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.error("Ha ocurrido un error al tratar de mostrar las marcas");
        }
    }
    
    public void save(){
        try{
           model.setEstado("A");
           model.setFechacreacion(new Date());
           model.setUsuariocrea(FacesHelper.getUserLogin());
           service.save(model);
           FacesHelper.success("Se ha guardado satisfactoriamente la marca de carro");
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.error("Ha ocurrido un error al tratar de guardar la marca");
        }
    }
    
    public void update(){
        try{
            if(modelUpdate != null){
                service.update(modelUpdate);
                FacesHelper.success("Se ha actualizado correctamente la marca de carro");
                find();
            }
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.error("Ha ocurrido un error al tratar de actualizar la marca");
        }
    }
    
    public void delete(){
        try{
            if(modelDelete != null){
                modelDelete.setEstado("I");
                service.update(modelDelete);
                FacesHelper.success("Se ha inactivado la marca correctamente");
            }
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.error("Ha ocurrido un error al tratar de inactivar la marca");
        }
    }

}
