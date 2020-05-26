/*---------------------------------------------------------
* FILE: TallerSolController.java
* PRODUCT: autolink
*----------------------------------------------------------
* IMPORTANT NOTICE
* This program is property of CEL
* Its unauthorized use, as any code alteration without authorization 
* is prohibited
*----------------------------------------------------------
*/
package com.terzus.autolink.controller;

import com.bila.framework.commons.FacesHelper;
import com.terzus.autolink.commons.Constants;
import com.terzus.autolink.model.Aseguradora;
import com.terzus.autolink.model.Marca;
import com.terzus.autolink.model.Modelo;
import com.terzus.autolink.model.Solicitud;
import com.terzus.autolink.service.SolicitudService;
import com.terzus.autolink.vo.SolicitudVO;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.primefaces.event.TabChangeEvent;

/**
 * @author CEL
 * <b>Created by: </b>will
 * <b>For: </b>autolink
 * <b>On: </b>May 24, 2020 8:07:37 PM
 * <b>Purpose</b> 
 * <p>
 *      
 * </p>
 */
@Named(value="tallerSolController")
@ViewScoped
@Slf4j
public class TallerSolController implements Serializable{

    @Inject private SolicitudService solService;
    @Getter @Setter private List<SolicitudVO> solList;
    @Getter @Setter private int codeChange;
    @Getter @Setter private List<Aseguradora> asegList;
    @Getter @Setter private List<Marca> marcaList;
    @Getter @Setter private List<Modelo> modeloList;
    @Getter @Setter private Solicitud model;
    @Getter @Setter private boolean showSaveBtn;
    
    @PostConstruct
    public void init(){
        try{
            showSaveBtn = true;
            model = new Solicitud();
            asegList = solService.findAsegActive();
            marcaList = solService.findMarcaActive();
            solList = solService.findIngresadas();
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.errorMessage(Constants.ERROR, "Ha ocurrido un error al recuperar las solicitudes. Favor intente nuevamente");
        }
    }
    
    public void onTabChange(TabChangeEvent event) {
        try{
            String id = event.getTab().getId();
            if(id != null)
                if(id.equals("ing"))
                    solList = solService.findIngresadas();
                else if(id.equals("anu"))
                    solList = solService.findAnuladas();
                else if(id.equals("dpp"))
                    solList = solService.findDespProveedor();
                else if(id.equals("eas"))
                    solList = solService.findEntSatCliente();
                else if(id.equals("cpd"))
                    solList = solService.findCerradaDesierta();
                else if(id.equals("cpa"))
                    solList = solService.findCerradaAseg();
                else if(id.equals("for")){
                    showSaveBtn = true;
                    model = new Solicitud();
                }
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.errorMessage(Constants.ERROR, "Ha ocurrido un error al tratar de recuperar las solicitudes");
        }
    }
    
    public void despProvToEntSatis(){
        try{
            if(codeChange > 0){
                solService.updateEstado(codeChange, "ESC");
                solList = solService.findDespProveedor();
                FacesHelper.successMessage(Constants.EXITO, "Se ha actualizado la solicitud correctamente");
            }
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.errorMessage(Constants.ERROR, "Ha ocurrido un error al tratar de cambiar el estado de la solicitud");
        }
    }
    
    public void chargeModelos(){
        try{
            if(model != null)
                if(model.getIdmarca() != null && model.getIdmarca() > 0)
                    modeloList = solService.findActiveByMarca(model.getIdmarca());
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.errorMessage(Constants.ERROR, "Ha ocurrido un error al recuperar los modelos de carro");
        }
    }
    
    public void saveSolicitud(){
        try{
            if(model.getNombreasegurado() == null || model.getNombreasegurado().equals(""))
                FacesHelper.warningMessage(Constants.WARNING, "Debe ingresar el nombre del asegurado");
            else if(model.getTipovehiculo()== null || model.getTipovehiculo().equals(""))
                FacesHelper.warningMessage(Constants.WARNING, "Debe ingresar el tipo de vehiculo");
            else if(model.getIdmarca() == null || model.getIdmarca() == 0)
                FacesHelper.warningMessage(Constants.WARNING, "Debe ingresar la marca del vehiculo");
            else if(model.getIdmodelo() == null || model.getIdmodelo() == 0)
                FacesHelper.warningMessage(Constants.WARNING, "Debe ingresar el modelo del vehiculo");
            else if(model.getAniocarro()== null || model.getAniocarro() == 0)
                FacesHelper.warningMessage(Constants.WARNING, "Debe ingresar el anio del vehiculo");
            else if(model.getPlaca() == null || model.getPlaca().equals(""))
                FacesHelper.warningMessage(Constants.WARNING, "Debe ingresar la placa del vehiculo");
            else if(model.getChasis()== null || model.getChasis().equals(""))
                FacesHelper.warningMessage(Constants.WARNING, "Debe ingresar el chasis del vehiculo");
            else if(model.getMotor()== null || model.getMotor().equals(""))
                FacesHelper.warningMessage(Constants.WARNING, "Debe ingresar el motor del vehiculo");
            else if(model.getPoliza()== null || model.getPoliza().equals(""))
                FacesHelper.warningMessage(Constants.WARNING, "Debe ingresar la poliza del vehiculo");
            else if(model.getChasis()== null || model.getChasis().equals(""))
                FacesHelper.warningMessage(Constants.WARNING, "Debe ingresar el chasis del vehiculo");
            else if(model.getSiniestro()== null || model.getSiniestro().equals(""))
                FacesHelper.warningMessage(Constants.WARNING, "Debe ingresar el siniestro");
            else{
                solService.save(model, FacesHelper.getUserLogin());
                showSaveBtn = false;
                FacesHelper.successMessage(Constants.EXITO, "Se ha guardado la solicitud correctamente");
            }
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.errorMessage(Constants.ERROR, "Ha ocurrido un error al ingresar la solicitud");
        }
    }

}
