/*---------------------------------------------------------
* FILE: AsegSolController.java
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
import com.terzus.autolink.model.Proveedor;
import com.terzus.autolink.model.Respuestoxsolicitud;
import com.terzus.autolink.model.Solicitud;
import com.terzus.autolink.service.ProveedorService;
import com.terzus.autolink.service.AseguradoraService;
import com.terzus.autolink.service.OfertaProvService;
import com.terzus.autolink.service.RepuestoSolicitudService;
import com.terzus.autolink.service.SolicitudService;
import com.terzus.autolink.vo.OfertaPrecioVO;
import com.terzus.autolink.vo.RepuestoSolicitudVO;
import com.terzus.autolink.vo.SolicitudVO;
import java.io.Serializable;
import java.util.ArrayList;
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
 * <b>On: </b>May 26, 2020 8:34:07 PM
 * <b>Purpose</b> 
 * <p>
 *      
 * </p>
 */
@Named(value="asegSolController")
@ViewScoped
@Slf4j
public class AsegSolController implements Serializable{

    @Inject private SolicitudService solService;
    @Inject private RepuestoSolicitudService repSolService;
    @Inject private OfertaProvService opService;
    @Inject private AseguradoraService asegService;
    @Inject private ProveedorService provService;
    @Getter @Setter private List<SolicitudVO> solList;
    @Getter @Setter private SolicitudVO voOrdenCompra;
    @Getter @Setter private List<OfertaPrecioVO> opList;
    @Getter @Setter private List<RepuestoSolicitudVO> repSolList;
    @Getter @Setter private int codSol;
    @Getter @Setter private int idProv;
    @Getter @Setter private int idSol;
    @Getter @Setter private int horas;
    @Getter @Setter private int idAseguradora;
    @Getter @Setter private String com;
    @Getter @Setter private String totalRepuestosLabel;
    
    @PostConstruct
    public void init(){
        try{
            solList = solService.findIngresadas();
            idAseguradora = asegService.findIdByUser(FacesHelper.getUserLogin());
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.errorMessage(Constants.ERROR, "Ha ocurrido un error al tratar de recuperar las solicitudes");
        }
    }
    
    private void messageIdAsegZero() throws Exception{
        FacesHelper.warningMessage(Constants.WARNING, "Ha ocurrido un error al tratar de recupera el ID de la aseguradora. Favor reportarlo al administrador");
    }
    
    public void onTabChange(TabChangeEvent event) {
        try{
            if(idAseguradora == 0){
                messageIdAsegZero();
                return;
            }
            String id = event.getTab().getId();
            if(id != null)
                if(id.equals("ing"))
                    solList = solService.findIngresadasByAseg(idAseguradora);
                else if(id.equals("coa"))
                    solList = solService.findCotAbiertaByAseg(idAseguradora);
                else if(id.equals("ppa")){
                    //AQUI SE AGREGA LA COTIZACION OPTIMA
                    solList = solService.findPendAprobarByAseg(idAseguradora);
                }else if(id.equals("cpa"))
                    solList = solService.findCerradaAsegByAseg(idAseguradora);
                else if(id.equals("goc"))
                    solList = solService.findGenOrdCompraByAseg(idAseguradora);
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.errorMessage(Constants.ERROR, "Ha ocurrido un error al tratar de recuperar las solicitudes");
        }
    }
    
    public void showRep(int idSol){
        try{
            repSolList = repSolService.findBySolicitud(idSol);
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.errorMessage(Constants.ERROR, "Ha ocurrido un error al tratar de ver los repuestos de la solicitud");
        }
    }
    
    public void showRepAplica(int idSol){
        try{
            repSolList = repSolService.findAplicaBySolicitud(idSol);
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.errorMessage(Constants.ERROR, "Ha ocurrido un error al tratar de ver los repuestos de la solicitud");
        }
    }
    
    public void aplicaRep(int idSol, int idRep){
        try{
            Respuestoxsolicitud model = repSolService.findByKey(idRep);
            if(model.getAplica() == null || model.getAplica().equals("N"))
                model.setAplica("S");
            else
                model.setAplica("N");
            repSolService.update(model);
            repSolList = repSolService.findBySolicitud(idSol);
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.errorMessage(Constants.ERROR, "Ha ocurrido un error al tratar de actualizar el repuesto");
        }
    }
    
    public void changeCotAbierta(){
        try{
            if(horas == 0)
                FacesHelper.warningMessage(Constants.WARNING, "Debe definir el tiempo de vigencia de la solicitud");
            else if(horas > 36)
                FacesHelper.warningMessage(Constants.WARNING, "El tiempo de vigencia debe ser menor o igual a 36 horas");
            else if(codSol > 0){
                solService.updateHorasVigencia(codSol, horas);
                solService.updateEstado(codSol, "COA");
                solList = solService.findIngresadasByAseg(idAseguradora);
                FacesHelper.successMessage(Constants.EXITO, "Se ha actualizado la solicitud correctamente");
            }
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.errorMessage(Constants.ERROR, "Ha ocurrido un error al tratar de actualizar la solicitud");
        }
    }
    
    public void recoverSol(int codSol){
        try{
            this.codSol = codSol;
            if(codSol > 0){
                Solicitud sol = solService.findByKey(codSol);
                if(sol != null)
                    this.com = sol.getComentariosaseguradora();
            }
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.errorMessage(Constants.ERROR, "Ha ocurrido un error al tratar de recuperar el comentario");
        }
    }
    
    public void updateComentario(){
        try{
            if(codSol > 0){
                if(com == null || com.equals(""))
                    FacesHelper.warningMessage(Constants.WARNING, "El comentario no puede ir vacio");
                else{
                    Solicitud sol = solService.findByKey(codSol);
                    if(sol != null){
                        sol.setComentariosaseguradora(com);
                        solService.update(sol);
                        FacesHelper.successMessage(Constants.EXITO, "Se ha guardado correctamente el comentario");                        
                    }
                }
            }
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.errorMessage(Constants.ERROR, "Ha ocurrido un error al tratar de actualizar la solicitud");
        }
    }
    
    public void changeStateSol(String state){
        try{
            solService.updateEstado(codSol, state);
            solList = solService.findIngresadasByAseg(idAseguradora);
            FacesHelper.successMessage(Constants.EXITO, "Se ha actualizado el estado de la solicitud correctamente");  
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.errorMessage(Constants.ERROR, "Ha ocurrido un error al tratar de actualizar la solicitud");
        }
    }
    
    public void showOfertasBySolicitud(int idSol){
        try{
            OfertaPrecioVO optima = opService.findOfertaOptima(idSol);
            opList = new ArrayList();
            //GUARDO LA COTIZACION OPTIMA
            if(optima != null)
                opList.add(optima);
            //GUARDO EL RESTO DE COTIZACIONES
            opList.addAll(opService.findOfertaTotalBySol(idSol));
            //CREO EL LABEL DE TOTAL DE REPUESTOS POR SOLICITUD
            Long totalRepuestos = repSolService.findCantidadTotalRepuestosBySol(idSol);
            if(totalRepuestos > 0)
                totalRepuestosLabel = "Total de repuestos solicitados: ".concat(String.valueOf(totalRepuestos));
            else
                totalRepuestosLabel = "";
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.errorMessage(Constants.ERROR, "Ha ocurrido un error al tratar de mostrar las ofertas de la solicitud");
        }
    }
    
    public void defineWinner(){
        try{
            if(idProv != -17)
                opService.updateGanador(idSol, idProv);
            else
                opService.updateGanadorCotOptima(idSol);
            solService.updateEstado(idSol, "GOC");
            solList = solService.findPendAprobarByAseg(idAseguradora);
            FacesHelper.successMessage(Constants.EXITO, "Se ha definido el ganador correctamente");
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.errorMessage(Constants.ERROR, "Ha ocurrido un error al tratar de definir el ganador");
        }
    }
    
    public void prepareWinner(int idSol, int idProv){
        this.idSol = idSol;
        this.idProv = idProv;
    }

    public void generateOrdenCompra(int idSol, int idProv){
        try{
            this.voOrdenCompra = solService.genOrdCompra(idSol, idProv);
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.errorMessage(Constants.ERROR, "Ha ocurrido un error al tratar de generar la orden de compra");
        }
    }
    
    public double iva(List<RepuestoSolicitudVO> list){
        try{
            double total = getTotal(list);
            if(total == 0.0) return total; 
            return total * 0.13;
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.errorMessage(Constants.ERROR, "Ha ocurrido un error al tratar de generar el total de la orden de compra");
        }
        return 0.0;
    }
    
    public double porcentajeProveedor(int idProv, List<RepuestoSolicitudVO> list){
        try{
            double total = getTotal(list);
            if(total == 0.0) return total; 
            Proveedor prov = provService.findByKey(idProv);
            if(prov == null) return 0.0;
            if(prov.getPorcentaje() > 0)
                return total * prov.getPorcentaje();
            return 0.0;
        }catch(Exception e){
            log.error(e.getMessage(), e);
        }
        return 0.0;
    }
    
    public double totalOrden(int idProv, List<RepuestoSolicitudVO> list){
        try{
            double total = getTotal(list);
            if(total == 0.0) return total;
            double ganancia = porcentajeProveedor(idProv, list);
            if(ganancia > 0)
                return total + (total * 0.13) + ganancia;
            else
                return total + (total * 0.13);
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.errorMessage(Constants.ERROR, "Ha ocurrido un error al tratar de generar el total de la orden de compra");
        }
        return 0.0;
    }
    
    public double subTotal(List<RepuestoSolicitudVO> list){
        try{
            return getTotal(list);
        }catch(Exception e){
            log.error(e.getMessage(), e);
        }
        return 0.0;
    }
    
    private double getTotal(List<RepuestoSolicitudVO> list){
            double total = 0.0;
            if(list == null || list.isEmpty()) return 0.0;
            for(RepuestoSolicitudVO vo : list){
                if(vo.getPrecio() != null && vo.getCantidad() != null && vo.getCantidad() > 0)
                    total += (vo.getPrecio() * vo.getCantidad());
            }
            return total;
    }

}
