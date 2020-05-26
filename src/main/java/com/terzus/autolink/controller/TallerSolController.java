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
    
    @PostConstruct
    public void init(){
        try{
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

}
