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
import com.terzus.autolink.model.OfertaSolicitudVista;
import com.terzus.autolink.model.Ofertaproveedor;
import com.terzus.autolink.model.SolicitudDespachada;
import com.terzus.autolink.service.OfertaProvService;
import com.terzus.autolink.service.OfertaSolicitudVistaService;
import com.terzus.autolink.service.ProveedorService;
import com.terzus.autolink.service.SolicitudDespService;
import com.terzus.autolink.vo.OfertaVO;
import com.terzus.autolink.vo.RepuestoSolicitudVO;
import com.terzus.autolink.vo.SolicitudVO;
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
    
    @Inject private ProveedorService provService;
    @Inject private OfertaProvService opService;
    @Inject private SolicitudDespService solDespService;
    @Inject private OfertaSolicitudVistaService osvService;
    @Getter @Setter private SolicitudVO voOrdenCompra;
    @Getter @Setter private List<SolicitudVO> solList;
    @Getter @Setter private List<RepuestoSolicitudVO> repSolList;
    @Getter @Setter private int codSol;
    @Getter @Setter private int codRep;
    @Getter @Setter private int cantidad;
    @Getter @Setter private int codPrv;
    @Getter @Setter private int totalSolCOA;
    @Getter @Setter private OfertaVO vo;
    @Getter @Setter private boolean despFlag;
    
    @PostConstruct
    public void init(){
        try{
            vo = new OfertaVO();
            this.codPrv = provService.findIdProvByUser(FacesHelper.getUserLogin());
            findCotAbierta();
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.errorMessage(Constants.ERROR, "Ha ocurrido un error al tratar de recuperar las solicitudes");
        }
    }
    
    public String goToOferta(int idSolicitud){
        try{
            boolean flag = osvService.isViewed(idSolicitud, codPrv);
            if(!flag){
                OfertaSolicitudVista model = new OfertaSolicitudVista();
                model.setEstado("S");
                model.setFeccrea(new Date());
                model.setIdProveedor(codPrv);
                model.setIdSolicitud(idSolicitud);
                model.setUserCrea(FacesHelper.getUserLogin());
                osvService.save(model);
            }
            return "/html/private/proveedor/definirOferta.xhtml?faces-redirect=true&idSolicitud=" + idSolicitud + "&idProveedor=" + this.codPrv;
        }catch(Exception e){
            log.error(e.getMessage(), e);
            return "";
        }
    }
    
    public void findCotAbierta(){
        try{
            solList = provService.findCotAbierta(this.codPrv);
            if(solList == null || solList.isEmpty()) 
                totalSolCOA = 0;
            else
                totalSolCOA = solList.size();
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.errorMessage(Constants.ERROR, "Ha ocurrido un error al tratar de recuperar las solicitudes");            
        }
    }
    
    public void generateOrdenCompraByProv(int idSol){
        try{
            this.voOrdenCompra = provService.genOrdCompraByProv(idSol, codPrv);
            //VERIFICO SI YA DESPACHO LA ORDEN
            SolicitudDespachada solDespModel = solDespService.findBySolAndProv(idSol, codPrv);
            if(solDespModel == null) 
                despFlag = false;
            else if(solDespModel.getEstado().equals("A"))
                despFlag = false;
            else if(solDespModel.getEstado().equals("N"))
                despFlag = true;
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.errorMessage(Constants.ERROR, "Ha ocurrido un error al tratar de generar la orden de compra");
        }
    }

    
    public void onTabChange(TabChangeEvent event) {
        try{
            String id = event.getTab().getId();
            if(id != null)
                if(id.equals("coa"))
                    findCotAbierta();
                else if(id.equals("goc"))
                    solList = provService.findGenOrdCompraByProveedor(this.codPrv);
                else if(id.equals("dpp"))
                    solList = provService.findDespProvByProveedorWinner(this.codPrv);
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.errorMessage(Constants.ERROR, "Ha ocurrido un error al tratar de recuperar las solicitudes");
        }
    }
    
    public void prepareOferta(int codSol, int codRep, int cantidad){
        this.codSol = codSol;
        this.codRep = codRep;
        this.cantidad = cantidad;
        try{
            Ofertaproveedor op = opService.findBySolicitudAndProveedorAndRepuesto(codSol, codPrv, codRep);
            if(op != null){
                vo.setEstado(op.getEstado());
                vo.setPrecio(op.getPrecio());
                vo.setTiempo(op.getTiempo());
                vo.setCantidad(op.getCantidad());
            }else
                vo = new OfertaVO();
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.errorMessage(Constants.ERROR, "Ha ocurrido un error al tratar de recuperar la oferta del proveedor");
        }
    }
    
    public void saveOferta(){
        try{
            //VALIDO EL FORMULARIO
            if(vo.getPrecio() == null || vo.getPrecio() == 0){
                FacesHelper.warningMessage(Constants.WARNING, "Debe ingresar el precio unitario del repuesto");
                return;
            }else if(vo.getCantidad() == null || vo.getCantidad() == 0){
                FacesHelper.warningMessage(Constants.WARNING, "Debe ingresar la cantidad de items");
                return;
            }else if(vo.getTiempo() == null || vo.getCantidad() == 0){
                FacesHelper.warningMessage(Constants.WARNING, "Debe ingresar el tiempo de entrega");
                return;
            }
            
            //VALIDO EL NUMERO DE CANTIDAD INGRESADO
            if(this.cantidad < vo.getCantidad()){
                FacesHelper.warningMessage(Constants.WARNING, "Ha ingresado una cantidad mayor a la solicitada de items");
                return;
            }
            Ofertaproveedor op = opService.findBySolicitudAndProveedorAndRepuesto(codSol, codPrv, codRep);
            if(op != null){
                op.setEstado(vo.getEstado());
                op.setPrecio(vo.getPrecio());
                op.setTiempo(vo.getTiempo());
                op.setCantidad((vo.getCantidad()));
                opService.update(op);
            }else if(op == null){
                Ofertaproveedor model = new Ofertaproveedor();
                model.setEstado(vo.getEstado());
                model.setIdproveedor(codPrv);
                model.setIdrepuesto(codRep);
                model.setIdsolicitud(codSol);
                model.setPrecio(vo.getPrecio());
                model.setTiempo(vo.getTiempo());
                model.setCantidad(vo.getCantidad());
                opService.save(model);
            }
            vo = new OfertaVO();
            FacesHelper.successMessage(Constants.EXITO, "Se ha guardado su oferta correctamente");
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.errorMessage(Constants.ERROR, "Ha ocurrido un error al tratar de guardar la oferta del proveedor");
        }
    }

    public void updateDespProv(int idSol){
        try{
            provService.updateSolDEP(idSol, this.codPrv);
            solList = provService.findGenOrdCompraByProveedor(this.codPrv);
            FacesHelper.successMessage(Constants.EXITO, "Se ha cambiado el estado de la solicitud correctamente");
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.errorMessage(Constants.ERROR, "Ha ocurrido un error al tratar de cambiar el estado de la solicitud");
        }
    }
}
