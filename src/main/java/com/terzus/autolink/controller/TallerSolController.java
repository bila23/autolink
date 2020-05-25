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
    
    @PostConstruct
    public void init(){
        try{
            solList = solService.findIngresadas();
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.error("Ha ocurrido un error al recuperar las solicitudes. Favor intente nuevamente");
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
            FacesHelper.error("Ha ocurrido un error al tratar de recuperar las solicitudes");
        }
    }

}
