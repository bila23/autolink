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
import com.bila.framework.commons.GeneralFunction;
import com.terzus.autolink.commons.Constants;
import com.terzus.autolink.model.Aseguradora;
import com.terzus.autolink.model.Marca;
import com.terzus.autolink.model.Modelo;
import com.terzus.autolink.model.Registro;
import com.terzus.autolink.model.Repuesto;
import com.terzus.autolink.model.Solicitud;
import com.terzus.autolink.model.TipoRepuesto;
import com.terzus.autolink.model.TipoVehiculo;
import com.terzus.autolink.service.FotoSolService;
import com.terzus.autolink.service.RegistroService;
import com.terzus.autolink.service.RepuestoSolicitudService;
import com.terzus.autolink.service.SolicitudService;
import com.terzus.autolink.vo.FotoXSolicitudVO;
import com.terzus.autolink.vo.RepuestoSolicitudVO;
import com.terzus.autolink.vo.SolicitudVO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.UploadedFile;

/**
 * @author Autolink
 * <b>Created by: </b>Jose Mauricio Herrera
 * <b>For: </b>autolink
 * <b>On: </b>Sep 1, 2020 3:50:00 AM
 * <b>Purpose</b>
 * <p>
 *
 * </p>
 */
@Named(value = "clienteSolController")
@ViewScoped
@Slf4j
public class ClienteSolController implements Serializable {

    @Inject
    private SolicitudService solService;
    @Inject
    private FotoSolService fotoSolService;
    @Inject
    private RepuestoSolicitudService repSolService;
    @Inject
    private RegistroService regService;
    @Getter
    @Setter
    private List<SolicitudVO> solList;
    @Getter
    @Setter
    private int codeChange;
    @Getter
    @Setter
    private List<Aseguradora> asegList;
    @Getter
    @Setter
    private List<Marca> marcaList;
    @Getter
    @Setter
    private List<Modelo> modeloList;
    @Getter
    @Setter
    private Solicitud model;
    @Getter
    @Setter
    private boolean showSaveBtn;
        @Getter
    @Setter
    private boolean showPdatos;
    @Setter
    private List<Repuesto> repList;
    @Getter
    @Setter
    private List<TipoRepuesto> listTipoRep;
    private List<TipoRepuesto> listTipoRepTmp;
    @Getter
    @Setter
    private List<TipoVehiculo> listTipoVehiculo;
    @Getter
    @Setter
    private TipoRepuesto tipoRep;
    @Getter
    @Setter
    private TipoVehiculo tipoVehiculo;
    @Getter
    @Setter
    private String user;
    @Getter
    @Setter
    private Integer cantidad;
    @Getter
    @Setter
    private List<RepuestoSolicitudVO> repVOList;
    @Getter
    @Setter
    private List<RepuestoSolicitudVO> repSolList;
    @Getter
    @Setter
    private UploadedFile imageFile;
    @Getter @Setter private List<FotoXSolicitudVO> imageList;
    @Getter
    @Setter
    private Map<Integer, Integer> anios = new HashMap<Integer, Integer>();
    @Getter
    @Setter
    private String tipo;
    @Getter
    @Setter
    private Repuesto repuestoSelected;
    private int idSol;
    @Getter
    @Setter
    private Registro usuario;
    private Map<String, String> itemsTipos = new HashMap<>();
    @Getter
    @Setter
    private String textoBuscar;

    private List<Solicitud> listSolicitudes;
     @Getter
    @Setter
    private int resumenVehiculo;
    @Getter
    @Setter
    private int totalCotizado;

    public void saveImage() {
        try {
            if (idSol > 0) {
                if (imageFile != null) {
                    fotoSolService.save(idSol, imageFile.getInputstream(), imageFile.getFileName());
                    FacesHelper.successMessage(Constants.EXITO, "Se ha guardado correctamente la imagen");
                    imageList = fotoSolService.findImageBySol(idSol);
                }
            } else {
                FacesHelper.warningMessage(Constants.WARNING, "Ha ocurrido un problema al tratar de asociar la imagen a la solicitud");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            FacesHelper.errorMessage(Constants.ERROR, "Ha ocurrido un error al tratar de guardar la imagen");
        }
    }
    
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

    @PostConstruct
    public void init() {
        showPdatos=false;
        try {
            itemsTipos = new HashMap<>();
            itemsTipos.put("Sedan", "0");
            itemsTipos.put("Pick Up", "1");
            itemsTipos.put("Camión de Carga", "2");
            itemsTipos.put("Camión", "3");
            itemsTipos.put("Bus", "4");
            itemsTipos.put("Microbús", "5");
            itemsTipos.put("Trailer", "6");
            itemsTipos.put("Motoclicleta", "7");

            HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            tipo = req.getParameter("i");

            chargeAnios();
            user = FacesHelper.getUserLogin();
            usuario = regService.getRegistroByUser(user);
            model = solService.findLastByEstadoAndCliente("INI", user);
            if (model == null) {
                if (tipo == null) {
                    tipo = "0";
                    FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml?i=0");
                }
                model = new Solicitud();
                showSaveBtn = true;
            } else {
                if (tipo == null) {
                    tipo = getCodigoTipoCar(model.getTipovehiculo());
                    FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml?i=" + tipo);
                }
                showSaveBtn = false;
                showPdatos=true;
                idSol = model.getId();
                repVOList = repSolService.findBySolicitud(idSol);
                imageList = fotoSolService.findImageBySol(idSol);
                chargeModelos();
            }
            asegList = solService.findAsegActive();
            marcaList = solService.findMarcaActive();
            solList = solService.findByEstadoAndCliente("ini", user);
            listTipoRep = solService.findAllTipoRepuestos();
            listTipoRepTmp = new ArrayList<>();
            listTipoRepTmp = listTipoRep;
            repList = solService.findRepuestoByTipo(tipoRep);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            FacesHelper.errorMessage(Constants.ERROR, "Ha ocurrido un error al recuperar las solicitudes. Favor intente nuevamente");
        }
    }

    private void showMessageIdTallerZero() throws Exception {
        FacesHelper.warningMessage(Constants.WARNING, "Ha ocurrido un error al tratar de recuperar el codigo del taller. Favor reportarlo al administrador");
    }

    public void onTabChange(TabChangeEvent event) {
        try {
            if (user != null) {
                String id = event.getTab().getId();
                if (id != null) {
                    switch (id) {
                        case "ini":
                            solList = solService.findByEstadoAndCliente("ini", user);
                            showSaveBtn = true;
                            break;
                        case "coa":
                            solList = solService.findByEstadoAndCliente("coa", user);
                            break;
                        case "rev":
                            solList = solService.findByEstadoAndCliente("rev", user);
                            break;
                        case "pea":
                            solList = solService.findByEstadoAndCliente("pea", user);
                            break;
                        case "oka":
                            solList = solService.findByEstadoAndCliente("oka", user);
                            break;
                        case "fin":
                            solList = solService.findByEstadoAndCliente("fin", user);
                            break;
                        case "cpd":
                            solList = solService.findByEstadoAndCliente("cpd", user);
                            break;
                        case "pen":
                            showSaveBtn = true;
                            model = solService.findLastByEstadoAndCliente("ini", user);
                            break;
                        default:
                            break;
                    }
                }
            } else {
                showMessageIdTallerZero();
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            FacesHelper.errorMessage(Constants.ERROR, "Ha ocurrido un error al tratar de recuperar las solicitudes");
        }
    }

    public void chargeModelos() {
        try {
            if (model != null) {
                if (model.getIdmarca() != null && model.getIdmarca() > 0) {
                    modeloList = solService.findActiveByMarca(model.getIdmarca());
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            FacesHelper.errorMessage(Constants.ERROR, "Ha ocurrido un error al recuperar los modelos de carro");
        }
    }

    public void chargeAnios() {
        try {
            //Anios
            int lastAnio;
            lastAnio = GeneralFunction.getYear();
            anios = new HashMap<Integer, Integer>();
            for (int i = 1990; i < lastAnio; i++) {
                anios.put(i, i);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            FacesHelper.errorMessage(Constants.ERROR, "Ha ocurrido un error al recuperar listado de años");
        }

    }

    public void resetListRepuestos() {
        repList = null;
        repuestoSelected = null;
        cantidad = 1;
    }

    public List<Repuesto> getRepList() {
        if (repList == null) {
            try {
                repList = solService.findRepuestoByTipo(tipoRep);
            } catch (Exception ex) {
                Logger.getLogger(ClienteSolController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return repList;
    }

    public void empezar() {
        model.setTipovehiculo(getNombreTipoCar(tipo));
        if (model.getTipovehiculo() == null || model.getTipovehiculo().equals("")) {
            FacesHelper.warningMessage(Constants.WARNING, "Debe ingresar el tipo de vehiculo");
            showPdatos= false;
        } else if (model.getIdmarca() == null || model.getIdmarca() == 0) {
            FacesHelper.warningMessage(Constants.WARNING, "Debe ingresar la marca del vehiculo");
            showPdatos= false;
        } else if (model.getIdmodelo() == null || model.getIdmodelo() == 0) {
            FacesHelper.warningMessage(Constants.WARNING, "Debe ingresar el modelo del vehiculo");
            showPdatos= false;
        } else if (model.getAniocarro() == null || model.getAniocarro() == 0) {
            FacesHelper.warningMessage(Constants.WARNING, "Debe ingresar el año del vehiculo");
            showPdatos= false;

        } else {
            showSaveBtn = false;
            showPdatos= true;
            //resumenVehiculo= marcaList.
        }

    }

    public void saveSolicitud() {
        try {
            model.setTipovehiculo(getNombreTipoCar(tipo));
            if (model.getTipovehiculo() == null || model.getTipovehiculo().equals("")) {
                FacesHelper.warningMessage(Constants.WARNING, "Debe ingresar el tipo de vehiculo");
            } else if (model.getIdmarca() == null || model.getIdmarca() == 0) {
                FacesHelper.warningMessage(Constants.WARNING, "Debe ingresar la marca del vehiculo");
            } else if (model.getIdmodelo() == null || model.getIdmodelo() == 0) {
                FacesHelper.warningMessage(Constants.WARNING, "Debe ingresar el modelo del vehiculo");
            } else if (model.getAniocarro() == null || model.getAniocarro() == 0) {
                FacesHelper.warningMessage(Constants.WARNING, "Debe ingresar el año del vehiculo");
            } /* else if(model.getPlaca() == null || model.getPlaca().equals(""))
                FacesHelper.warningMessage(Constants.WARNING, "Debe ingresar la placa del vehiculo");
            else if(model.getChasis()== null || model.getChasis().equals(""))
                FacesHelper.warningMessage(Constants.WARNING, "Debe ingresar el chasis del vehiculo");
            else if(model.getMotor()== null || model.getMotor().equals(""))
                FacesHelper.warningMessage(Constants.WARNING, "Debe ingresar el motor del vehiculo");          
            else if(model.getChasis()== null || model.getChasis().equals(""))
                FacesHelper.warningMessage(Constants.WARNING, "Debe ingresar el chasis del vehiculo");*/ else {
                idSol = solService.saveSol(model, user, getNombreTipoCar(tipo));
                if (idSol > 0) {
                    showSaveBtn = true;
                } else {
                    showSaveBtn = false;
                }
                FacesHelper.successMessage(Constants.EXITO, "Se ha guardado la solicitud correctamente");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            FacesHelper.errorMessage(Constants.ERROR, "Ha ocurrido un error al ingresar la solicitud");
        }
    }

    public void saveRepSol() {
        try {
            if (repuestoSelected == null) {
                FacesHelper.warningMessage(Constants.WARNING, "Debe seleccionar un repuesto");
            } else if (cantidad == null || cantidad == 0) {
                FacesHelper.warningMessage(Constants.WARNING, "Debe ingresar la cantidad de repuestos");
            } else if (repuestoSelected.getId() > 0) {
                if (repVOList == null || repVOList.isEmpty() || repVOList.size() <= 0) {
                    saveSolicitud();
                }
                repSolService.edit(idSol, repuestoSelected.getId(), cantidad);
                repVOList = repSolService.findBySolicitud(idSol);
                if (repVOList != null) {
                    if (repVOList.size() > 0) {
                        showSaveBtn = true;
                    }
                }

                FacesHelper.successMessage(Constants.EXITO, "Se ha agregado el repuesto código:".concat(String.valueOf(repuestoSelected.getId())));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            FacesHelper.errorMessage(Constants.ERROR, "Ha ocurrido un error al asociar un repuesto a la solicitud");
        }
    }
   
    public void enviarSol() {
        try {
            if (model.getTipovehiculo() == null || model.getTipovehiculo().equals("")) {
                FacesHelper.warningMessage(Constants.WARNING, "Debe ingresar el tipo de vehiculo");
            } else if (model.getIdmarca() == null || model.getIdmarca() == 0) {
                FacesHelper.warningMessage(Constants.WARNING, "Debe ingresar la marca del vehiculo");
            } else if (model.getIdmodelo() == null || model.getIdmodelo() == 0) {
                FacesHelper.warningMessage(Constants.WARNING, "Debe ingresar el modelo del vehiculo");
            } else if (model.getAniocarro() == null || model.getAniocarro() == 0) {
                FacesHelper.warningMessage(Constants.WARNING, "Debe ingresar el año del vehiculo");
            } else if (model.getPlaca() == null || model.getPlaca().equals("")) {
                FacesHelper.warningMessage(Constants.WARNING, "Debe ingresar la placa del vehiculo");
            } else if (model.getChasis() == null || model.getChasis().equals("")) {
                FacesHelper.warningMessage(Constants.WARNING, "Debe ingresar el chasis del vehiculo");
            } else if (model.getMotor() == null || model.getMotor().equals("")) {
                FacesHelper.warningMessage(Constants.WARNING, "Debe ingresar el motor del vehiculo");
            } else {
                model.setEstado("COA");
                model.setTipovehiculo(getNombreTipoCar(tipo));
                model.setComentariosaseguradora(model.getComentariostaller());
                model.setFechacreacion(new Date());
                solService.update(model);
                solService.updateHorasVigenciaForCliente(model.getId(), 2);
                showSaveBtn = false;
                repVOList = new ArrayList<>();
                model = new Solicitud();
                FacesHelper.successMessage(Constants.EXITO, "Se ha enviado la solicitud correctamente, en un periodo de 2 horas habiles recibira una cotización de esta solicitud");
                showPdatos=false;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            FacesHelper.errorMessage(Constants.ERROR, "Ha ocurrido un error al ingresar la solicitud");
        }
    }

    public void procesarSolicitud() {

    }

    public void deleteRepSol(int id) {
        try {
            if (id > 0) {
                repSolService.deleteById(id);
                repVOList = repSolService.findBySolicitud(idSol);
                FacesHelper.successMessage(Constants.EXITO, "Se ha eliminado el repuesto");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            FacesHelper.errorMessage(Constants.ERROR, "Ha ocurrido un error al eliminar un repuesto");
        }
    }

    public void finishSolicitud() {
        try {
            idSol = 0;
            model = new Solicitud();
            showSaveBtn = true;
            FacesHelper.successMessage(Constants.EXITO, "Se ha finalizado la solicitud");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            FacesHelper.errorMessage(Constants.ERROR, "Ha ocurrido un error al finalizar la solicitud");
        }
    }

    public void showRep(int idSol) {
        try {
            repSolList = repSolService.findBySolicitud(idSol);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            FacesHelper.errorMessage(Constants.ERROR, "Ha ocurrido un error al tratar de ver los repuestos de la solicitud");
        }
    }

    public void showRepCotizados(int idSol) {
        try {
            repSolList = repSolService.findBySolicitudCotizadas(idSol);
            totalCotizado = calcularTotalCotizado(repSolList);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            FacesHelper.errorMessage(Constants.ERROR, "Ha ocurrido un error al tratar de ver los repuestos de la solicitud");
        }
    }

    //Saca Nombre del tipo de carro
    public String getNombreTipoCar(String valor) {
        String nombreTipo = "";
        for (Map.Entry e : itemsTipos.entrySet()) {
            if (!GeneralFunction.isNullOrEmpty(e.getValue())) {
                if (e.getValue().equals(valor)) {
                    nombreTipo = e.getKey().toString();
                    break;
                }
            }
        }
        return nombreTipo;
    }

    //Saca Codigo del tipo de carro
    public String getCodigoTipoCar(String valor) {
        String codTipo = "";
        for (Map.Entry e : itemsTipos.entrySet()) {
            if (!GeneralFunction.isNullOrEmpty(e.getKey())) {
                if (e.getKey().equals(valor)) {
                    codTipo = e.getValue().toString();
                    break;
                }
            }
        }
        return codTipo;
    }

    public void aceptarCotizacion() {
        try {
            if (codeChange > 0) {
                solService.updateEstado(codeChange, "OKA");
                solList = solService.findByEstadoAndCliente("PEA", user);
                FacesHelper.successMessage(Constants.EXITO, "Se ha actualizado la solicitud correctamente");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            FacesHelper.errorMessage(Constants.ERROR, "Ha ocurrido un error al tratar de cambiar el estado de la solicitud");
        }
    }

    public String getNombreUser() {
        if (usuario != null) {
            try {
                return usuario.getNombre().substring(0, usuario.getNombre().indexOf(" ")).concat(" ").concat(usuario.getApellido().substring(0, usuario.getApellido().indexOf(" ")));
            } catch (Exception e) {
                return usuario.getNombre() + " " + usuario.getApellido();
            }

        }
        return "";
    }

    public void filtrarTiposRepuestos() {
        listTipoRep = filterRepListByArg(listTipoRepTmp, textoBuscar);
    }

    public List<TipoRepuesto> filterRepListByArg(List<TipoRepuesto> tiprepList, String arg) {
        if (GeneralFunction.isNullOrEmpty(arg)) {
            return tiprepList;

        } else {
            arg = arg.toLowerCase();
        }

        if (tiprepList != null) {
            List<TipoRepuesto> lista = new ArrayList<>();
            for (TipoRepuesto tip : tiprepList) {
                if (!GeneralFunction.isNullOrEmpty(tip.getNombre())) {
                    if (tip.getNombre().toLowerCase().contains(arg)) {
                        lista.add(tip);
                    }
                }
            }
            return lista;
        }
        return null;
    }

    public List<Solicitud> getListSolicitudes(String estado) {
        if (listSolicitudes == null) {
            try {
                listSolicitudes = solService.findByEstadoAndClienteSol(estado, user);
            } catch (Exception ex) {
                Logger.getLogger(ClienteSolController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listSolicitudes;
    }

    private int calcularTotalCotizado(List<RepuestoSolicitudVO> repSolList) {
        int total = 0;
        for (RepuestoSolicitudVO rsVo : repSolList) {
            total = total + rsVo.getPrecio().intValue();
        }
        return total;
    }

}
