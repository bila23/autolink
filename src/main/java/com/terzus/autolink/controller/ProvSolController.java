/*---------------------------------------------------------
* FILE: ProvSolController.java
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
import com.terzus.autolink.model.Usuario;
import com.terzus.autolink.service.RepuestoSolicitudService;
import com.terzus.autolink.service.SolicitudService;
import com.terzus.autolink.service.UsuarioService;
import com.terzus.autolink.vo.RepuestoSolicitudVO;
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
 * <b>On: </b>May 27, 2020 2:33:42 PM
 * <b>Purpose</b> 
 * <p>
 *      
 * </p>
 */
@Named(value="provSolController")
@ViewScoped
@Slf4j
public class ProvSolController implements Serializable{
    
    @Inject private SolicitudService solService;
    @Inject private RepuestoSolicitudService repSolService;
    @Inject private UsuarioService userService;
    @Getter @Setter private List<SolicitudVO> solList;
    @Getter @Setter private List<RepuestoSolicitudVO> repSolList;
    @Getter @Setter private int codSol;
    @Getter @Setter private int codPrv;
    
    @PostConstruct
    public void init(){
        try{
            Usuario userModel = userService.findByUser(FacesHelper.getUserLogin());
            solList = solService.findCotAbierta(userModel.getId());
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.errorMessage(Constants.ERROR, "Ha ocurrido un error al tratar de recuperar las solicitudes");
        }
    }
    
    public void onTabChange(TabChangeEvent event) {
        try{
            String id = event.getTab().getId();
            if(id != null)
                if(id.equals("coa"))
                    solList = solService.findCotAbierta();
                else if(id.equals("goc"))
                    solList = solService.findGenOrdCompra();
                else if(id.equals("dpp"))
                    solList = solService.findDespProveedor();
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.errorMessage(Constants.ERROR, "Ha ocurrido un error al tratar de recuperar las solicitudes");
        }
    }

}
