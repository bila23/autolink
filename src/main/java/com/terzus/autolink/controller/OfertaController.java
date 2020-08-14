/*---------------------------------------------------------
* FILE: OfertaController.java
* PRODUCT: autolink
*----------------------------------------------------------
* IMPORTANT NOTICE
* This program is property of Terzus
* Its unauthorized use, as any code alteration without
* authorization is prohibited
*----------------------------------------------------------
*/
package com.terzus.autolink.controller;

import com.bila.framework.commons.FacesHelper;
import com.terzus.autolink.service.SolicitudService;
import com.terzus.autolink.vo.RepuestoSolicitudVO;
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
 * <b>On: </b>Aug 14, 2020 10:18:09 AM
 * <b>Purpose</b> 
 * <p>
 *      
 * </p>
 */
@Named(value="ofertaController")
@ViewScoped
@Slf4j
public class OfertaController implements Serializable{

    @Inject private SolicitudService solService;
    @Getter @Setter private List<RepuestoSolicitudVO> repSolList;
    @Getter @Setter private int idSolicitud;
    @Getter @Setter private int idProveedor;
    
    @PostConstruct
    public void init(){
        try{
            findRepuestos();
        }catch(Exception e){
            log.error(e.getMessage(), e);
        }
    }
    
    public String goBack(){
        return "/html/private/proveedor/index.html?faces-redirect=true";
    }
    
    public void findRepuestos(){
        try{
            repSolList = solService.findRepAplicaBySol(idSolicitud, idProveedor);
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.error("Ha ocurrido un error al tratar de mostrar los repuestos");            
        }
    }

}
