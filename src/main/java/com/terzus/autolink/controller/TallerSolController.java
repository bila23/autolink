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
import com.terzus.autolink.model.Repuesto;
import com.terzus.autolink.model.Solicitud;
import com.terzus.autolink.model.TipoVehiculo;
import com.terzus.autolink.service.FotoSolService;
import com.terzus.autolink.service.RepuestoSolicitudService;
import com.terzus.autolink.service.SolicitudDespService;
import com.terzus.autolink.service.SolicitudService;
import com.terzus.autolink.service.TallerService;
import com.terzus.autolink.service.TipoVehiculoService;
import com.terzus.autolink.vo.FotoXSolicitudVO;
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
import org.primefaces.model.UploadedFile;

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
    @Inject private SolicitudDespService solDespService;
    @Inject private FotoSolService fotoSolService;
    @Inject private RepuestoSolicitudService repSolService;
    @Inject private TallerService tallerService;
    @Inject private TipoVehiculoService tipoVehService;
    @Getter @Setter private List<TipoVehiculo> tipoVehList;
    @Getter @Setter private List<SolicitudVO> solList;
    @Getter @Setter private int codeChange;
    @Getter @Setter private int idTipoVehiculo;
    @Getter @Setter private boolean flagEsc;
    @Getter @Setter private List<Aseguradora> asegList;
    @Getter @Setter private List<Marca> marcaList;
    @Getter @Setter private List<Modelo> modeloList;
    @Getter @Setter private Solicitud model;
    @Getter @Setter private boolean showSaveBtn;
    @Getter @Setter private List<Repuesto> repList;
    @Getter @Setter private int repuesto;
    @Getter @Setter private int idTaller;
    @Getter @Setter private String user;
    @Getter @Setter private String comentario;
    @Getter @Setter private Integer cantidad;
    @Getter @Setter private List<RepuestoSolicitudVO> repVOList;
    @Getter @Setter private List<RepuestoSolicitudVO> repSolList;
    @Getter @Setter private UploadedFile imageFile;
    @Getter @Setter private List<FotoXSolicitudVO> imageList;
    private int idSol;
    
    public void deleteImage(int id){
        try{
            if(id > 0){
                fotoSolService.delete(id);
                FacesHelper.successMessage(Constants.EXITO, "Se ha eliminado correctamente la imagen");
                imageList = fotoSolService.findImageBySol(idSol);
            }
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.errorMessage(Constants.ERROR, "Ha ocurrido un error al tratar de eliminar la imagen");            
        }
    }
    
    public void findImage(int idSol){
        try{
            imageList = fotoSolService.findImageBySol(idSol);
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.errorMessage(Constants.ERROR, "Ha ocurrido un error al tratar de mostrar las imagenes");
        }
    }
    
    public void saveImage(){
        try{
            if(idSol >0){
                if(imageFile != null){
                    fotoSolService.save(idSol, imageFile.getInputstream(), imageFile.getFileName());
                    FacesHelper.successMessage(Constants.EXITO, "Se ha guardado correctamente la imagen");
                    imageList = fotoSolService.findImageBySol(idSol);
                }
            }else
                FacesHelper.warningMessage(Constants.WARNING, "Ha ocurrido un problema al tratar de asociar la imagen a la solicitud");
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.errorMessage(Constants.ERROR, "Ha ocurrido un error al tratar de guardar la imagen");
        }
    }
    
    @PostConstruct
    public void init(){
        try{
            showSaveBtn = true;
            model = new Solicitud();
            user = FacesHelper.getUserLogin();
            asegList = solService.findAsegActive();
            marcaList = solService.findMarcaActive();
            solList = solService.findIngresadas();
            repList = solService.findRepuestoActive();
            idTaller = tallerService.findCodeByUser(user);
            tipoVehList = tipoVehService.findAllWithNamedQuery();
            if(idTaller == 0)
                showMessageIdTallerZero();
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.errorMessage(Constants.ERROR, "Ha ocurrido un error al recuperar las solicitudes. Favor intente nuevamente");
        }
    }
    
    private void showMessageIdTallerZero() throws Exception{
        FacesHelper.warningMessage(Constants.WARNING, "Ha ocurrido un error al tratar de recuperar el codigo del taller. Favor reportarlo al administrador");
    }
    
    public void onTabChange(TabChangeEvent event) {
        try{
            if(idTaller > 0){
                String id = event.getTab().getId();
                if(id != null)
                    if(id.equals("ing"))
                        solList = solService.findIngresadasByTaller(idTaller);
                    else if(id.equals("anu"))
                        solList = solService.findAnuladasByTaller(idTaller);
                    else if(id.equals("dpp"))
                        solList = solService.findDespProveedorByTaller(idTaller);
                    else if(id.equals("eas"))
                        solList = solService.findEntSatClienteByTaller(idTaller);
                    else if(id.equals("cpd"))
                        solList = solService.findCerradaDesiertaByTaller(idTaller);
                    else if(id.equals("cpa"))
                        solList = solService.findCerradaAsegByTaller(idTaller);
                    else if(id.equals("for")){
                        showSaveBtn = true;
                        model = new Solicitud();
                    }
            }else
                showMessageIdTallerZero();
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.errorMessage(Constants.ERROR, "Ha ocurrido un error al tratar de recuperar las solicitudes");
        }
    }
    
    public void despProvToEntSatis(){
        try{
            if(codeChange > 0){
                solService.updateEstado(codeChange, "ESC");
                solList = solService.findDespProveedorByTaller(idTaller);
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
            if(idTaller == 0){
                showMessageIdTallerZero();
                return;
            }
            if(validateForm()){
                TipoVehiculo tipoVehiculo = tipoVehService.findByKey(idTipoVehiculo);
                model.setIdTipoVehiculo(tipoVehiculo);
                Solicitud newSolicitud = solService.save(model, user, idTaller);
                idSol = newSolicitud.getId();
                showSaveBtn = false;
                FacesHelper.successMessage(Constants.EXITO, "Se ha guardado la solicitud correctamente, el código es ".concat(model.getCodigosolicitud()));
            }
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.errorMessage(Constants.ERROR, "Ha ocurrido un error al ingresar la solicitud");
        }
    }
    
    private boolean validateForm(){
        boolean flag = false;
        if(model.getNombreasegurado() == null || model.getNombreasegurado().equals(""))
            FacesHelper.warningMessage("Debe ingresar el nombre del asegurado");
        else if(idTipoVehiculo == 0)
            FacesHelper.warningMessage("Debe ingresar el tipo de vehiculo");
        else if(model.getIdmarca() == null || model.getIdmarca() == 0)
            FacesHelper.warningMessage("Debe ingresar la marca del vehiculo");
        else if(model.getIdmodelo() == null || model.getIdmodelo() == 0)
            FacesHelper.warningMessage("Debe ingresar el modelo del vehiculo");
        else if(model.getAniocarro()== null || model.getAniocarro() == 0)
            FacesHelper.warningMessage("Debe ingresar el anio del vehiculo");
        else if(model.getPlaca() == null || model.getPlaca().equals(""))
            FacesHelper.warningMessage("Debe ingresar la placa del vehiculo");
        else if(model.getChasis()== null || model.getChasis().equals(""))
            FacesHelper.warningMessage("Debe ingresar el chasis del vehiculo");
        else if(model.getMotor()== null || model.getMotor().equals(""))
            FacesHelper.warningMessage("Debe ingresar el motor del vehiculo");
        else if(model.getPoliza()== null || model.getPoliza().equals(""))
            FacesHelper.warningMessage("Debe ingresar la poliza del vehiculo");
        else if(model.getChasis()== null || model.getChasis().equals(""))
            FacesHelper.warningMessage("Debe ingresar el chasis del vehiculo");
        else if(model.getSiniestro()== null || model.getSiniestro().equals(""))
            FacesHelper.warningMessage("Debe ingresar el siniestro");
        else if(model.getChasis().length() != 17)
            FacesHelper.warningMessage("El chasis debe tener 17 caracteres");
        else
            flag = true;
        return flag;
    }
    
    public void saveRepSol(){
        try{
            if(cantidad == null || cantidad == 0)
                FacesHelper.warningMessage(Constants.WARNING, "Debe ingresar la cantidad de repuestos");
            else if(repuesto == 0)
                FacesHelper.warningMessage(Constants.WARNING, "Debe seleccionar un repuesto");
            else if(repuesto > 0){
                repSolService.save(idSol, repuesto, cantidad);
                repVOList = repSolService.findBySolicitud(idSol);
                FacesHelper.successMessage(Constants.EXITO, "Se ha guardado el repuesto");
            }
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.errorMessage(Constants.ERROR, "Ha ocurrido un error al asociar un repuesto a la solicitud");
        }
    }
    
    public void deleteRepSol(int id){
        try{
            if(id > 0){
                repSolService.deleteById(id);
                repVOList = repSolService.findBySolicitud(idSol);
                FacesHelper.successMessage(Constants.EXITO, "Se ha eliminado el repuesto");
            }
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.errorMessage(Constants.ERROR, "Ha ocurrido un error al eliminar un repuesto");
        }
    }
    
    public void saveComentario(){
        try{
            if(model != null){
                if(model.getId() != null && model.getId() > 0){
                    model.setComentariostaller(comentario);
                    solService.update(model);
                    FacesHelper.success("Se ha guardado el comentario correctamente");
                }
            }
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.errorMessage("Ha ocurrido un error al guardar el comentario");
        }
    }
    
    public void finishSolicitud(){
        try{
            idSol = 0;
            model = new Solicitud();
            showSaveBtn = true;
            FacesHelper.successMessage(Constants.EXITO, "Se ha finalizado la solicitud");
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.errorMessage(Constants.ERROR, "Ha ocurrido un error al finalizar la solicitud");
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

    public void verifySatCliente(int idSol){
        try{
            flagEsc = solDespService.existBySolWithStateN(idSol);
            if(!flagEsc)
                this.codeChange = idSol;
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.error("Ha ocurrido un error inesperado al tratar de verificar la solicitud");
        }
    }
}
