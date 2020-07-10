/*---------------------------------------------------------
* FILE: AdminSolController.java
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
import com.terzus.autolink.service.SolicitudService;
import com.terzus.autolink.vo.OfertaProveedorVO;
import com.terzus.autolink.vo.SolicitudVO;
import java.io.Serializable;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author CEL
 * <b>Created by: </b>will
 * <b>For: </b>autolink
 * <b>On: </b>May 30, 2020 11:40:34 AM
 * <b>Purpose</b> 
 * <p>
 *      
 * </p>
 */
@Named(value="adminSolController")
@ViewScoped
@Slf4j
public class AdminSolController implements Serializable {

    @Inject private SolicitudService solService;
    @Getter @Setter private List<SolicitudVO> solList;
    @Getter @Setter private String state;
    @Getter @Setter private SolicitudVO vo;
    @Getter @Setter private List<OfertaProveedorVO> ofertaList;
    
    public void findByState(){
        try{
            if(state == null || state.equals("N")) solList = null;
            solList = solService.findByEstado(state);
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.errorMessage(Constants.ERROR, "Ha ocurrido un error al tratar de mostrar las solicitudes");
        }
    }
    
    public void seeSol(int idSol){
        try{
            if(idSol > 0)
                vo = solService.seeInfoSol(idSol);
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.errorMessage(Constants.ERROR, "Ha ocurrido un error al tratar de mostrar la solicitudes");
        }
    }

    
}//class
