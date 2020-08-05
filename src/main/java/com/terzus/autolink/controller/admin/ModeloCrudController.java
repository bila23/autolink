/*---------------------------------------------------------
* FILE: ModeloCrudController.java
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
import com.terzus.autolink.model.Modelo;
import com.terzus.autolink.service.MarcaService;
import com.terzus.autolink.service.ModeloService;
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
 * <b>On: </b>Aug 4, 2020 11:56:47 AM
 * <b>Purpose</b> 
 * <p>
 *      
 * </p>
 */
@Named(value = "modeloCrudController")
@ViewScoped
@Slf4j
public class ModeloCrudController implements Serializable{
    
    @Inject private ModeloService service;
    @Inject private MarcaService marcaService;
    @Getter @Setter private Modelo model;
    @Getter @Setter private Modelo modelUpdate;
    @Getter @Setter private Modelo modelDelete;
    @Getter @Setter private Modelo modelView;
    @Getter @Setter private List<Marca> marcaList;
    @Getter @Setter private List<Modelo> list;
    @Getter @Setter private List<Modelo> filteredList;
    
    @PostConstruct
    public void init(){
        try{
            find();
            model = new Modelo();
            modelUpdate = new Modelo();
            marcaList = marcaService.findActive();
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.error("Ha ocurrido un error al tratar de iniciar la pantalla");
        }
    }
    
    public void find(){
        try{
            list = service.findAllWithNamedQuery();
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.error("");
        }
    }
    
    public void save(){
        try{
            model.setEstado("A");
            model.setFechacreacion(new Date());
            model.setUsuariocrea(FacesHelper.getUserLogin());
            service.save(model);
            model = new Modelo();
            FacesHelper.success("Se ha guardado correctamente el modelo");
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.error("Ha ocurrido un error al tratar de guardar el modelo");
        }
    }
    
    public void update(){
        try{
            if(modelUpdate != null){
                service.update(modelUpdate);
                FacesHelper.success("Se ha actualizado correctamente el modelo");
                find();
            }
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.error("Ha ocurrido un error al tratar de actualizar el modelo");
        }
    }
    
    public void delete(){
        try{
            if(modelDelete != null){
                modelDelete.setEstado("I");
                service.update(modelDelete);
                FacesHelper.success("Se ha inactivado el modelo correctamente");
            }
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.error("Ha ocurrido un error al tratar de inactivar el modelo");
        }
    }
    
    public String getMarca(int idMarca){
        try{
            return marcaService.findName(idMarca);
        }catch(Exception e){
            log.error(e.getMessage(), e);
        }
        return "";
    }

}
