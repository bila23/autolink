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
import com.terzus.autolink.model.Ofertaproveedor;
import com.terzus.autolink.model.Usuario;
import com.terzus.autolink.service.OfertaProvService;
import com.terzus.autolink.service.RepuestoSolicitudService;
import com.terzus.autolink.service.SolicitudService;
import com.terzus.autolink.service.UsuarioService;
import com.terzus.autolink.vo.OfertaVO;
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
    @Inject private OfertaProvService opService;
    @Getter @Setter private List<SolicitudVO> solList;
    @Getter @Setter private List<RepuestoSolicitudVO> repSolList;
    @Getter @Setter private int codSol;
    @Getter @Setter private int codRep;
    @Getter @Setter private int codPrv;
    @Getter @Setter private OfertaVO vo;
    
    @PostConstruct
    public void init(){
        try{
            vo = new OfertaVO();
            Usuario userModel = userService.findByUser(FacesHelper.getUserLogin());
            this.codPrv = userModel.getId();
            solList = solService.findCotAbierta(this.codPrv);
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
                    solList = solService.findCotAbierta(this.codPrv);
                else if(id.equals("goc"))
                    solList = solService.findGenOrdCompra();
                else if(id.equals("dpp"))
                    solList = solService.findDespProveedor();
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.errorMessage(Constants.ERROR, "Ha ocurrido un error al tratar de recuperar las solicitudes");
        }
    }
    
    public void prepareOferta(int codSol, int codRep){
        this.codSol = codSol;
        this.codRep = codRep;
        try{
            Ofertaproveedor op = opService.findBySolicitudAndProveedorAndRepuesto(codSol, codPrv, codRep);
            if(op != null){
                vo.setEstado(op.getEstado());
                vo.setPrecio(op.getPrecio());
                vo.setTiempo(op.getTiempo());
            }else
                vo = new OfertaVO();
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.errorMessage(Constants.ERROR, "Ha ocurrido un error al tratar de recuperar la oferta del proveedor");
        }
    }
    
    public void saveOferta(){
        try{
            Ofertaproveedor op = opService.findBySolicitudAndProveedorAndRepuesto(codSol, codPrv, codRep);
            if(op != null){
                op.setEstado(vo.getEstado());
                op.setPrecio(vo.getPrecio());
                op.setTiempo(vo.getTiempo());
                opService.update(op);
            }else if(op == null){
                Ofertaproveedor model = new Ofertaproveedor();
                model.setEstado(vo.getEstado());
                model.setIdproveedor(codPrv);
                model.setIdrepuesto(codRep);
                model.setIdsolicitud(codSol);
                model.setPrecio(vo.getPrecio());
                model.setTiempo(vo.getTiempo());
                opService.save(model);
            }
            vo = new OfertaVO();
            solList = solService.findCotAbierta(this.codPrv);
            FacesHelper.successMessage(Constants.EXITO, "Se ha guardado su oferta correctamente");
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.errorMessage(Constants.ERROR, "Ha ocurrido un error al tratar de guardar la oferta del proveedor");
        }
    }

}
