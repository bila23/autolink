/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.terzus.autolink.controller;

import com.bila.framework.commons.FacesHelper;
import com.bila.framework.commons.GeneralFunction;
import com.terzus.autolink.commons.Constants;
import com.terzus.autolink.model.Departamento;
import com.terzus.autolink.model.Municipio;
import com.terzus.autolink.model.Registro;
import com.terzus.autolink.model.Usuario;
import com.terzus.autolink.service.MunicipioService;
import com.terzus.autolink.service.RegistroService;
import com.terzus.autolink.service.SolicitudService;
import com.terzus.autolink.service.UsuarioService;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.primefaces.PrimeFaces;
import org.primefaces.util.Jsf22Helper;

/**
 *
 * @author josemauricioherrera
 */
@Named(value = "registroController")
@ViewScoped
@Slf4j
public class RegistroController implements Serializable {

    @Getter
    @Setter
    private Registro registro;
    @Inject
    private RegistroService regService;
    @Inject
    private UsuarioService userService;
    @Inject
    private MunicipioService muniService;
    @Getter
    @Setter
    private List<Departamento> listDeptos;
    private List<Municipio> listMunicipios;
    @Getter
    @Setter
    private List<Municipio> municipios;
    @Getter
    @Setter
    private String user;
    @Getter
    @Setter
    private String password;
    @Getter
    @Setter
    private String passreplay;
    private Usuario usu;
    @Getter
    @Setter
    private boolean cambiopass;

    public RegistroController() {
    }

    @PostConstruct
    public void init() {
        try {
            user = FacesHelper.getUserLogin();
            registro = getRegistroByUser();
            if (registro == null) {
                registro = new Registro();
            }
            listDeptos = muniService.getListDepartamentos();
            password = "";
            passreplay = "";
            // PrimeFaces.current().executeScript("$('.input-field label').removeClass('active'); ");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            FacesHelper.errorMessage(Constants.ERROR, "Ha ocurrido un error al recuperar departamentos. Favor intentelo nuevamente");
        }
    }

    public void resetMunicipio() {
        if (registro != null) {
            listMunicipios = null;
        }
    }

    public List<Municipio> getListMunicipio() {
        if (listMunicipios == null && registro.getIddepartamento() != null)
            try {
            listMunicipios = muniService.getListMunicipiosByDepto(registro.getIddepartamento().getId());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            FacesHelper.errorMessage(Constants.ERROR, "Ha ocurrido un error al recuperar los municipios. Favor intente nuevamente");
        }
        return listMunicipios;
    }

    public Registro getRegistroByUser() {
        try {
            registro = regService.getRegistroByUser(user);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            FacesHelper.error("Ha ocurrido un error al tratar de recuperar datos de registro del usuario");
        }
        return registro;
    }

    public void guardarRegistro() {
        try {
            if (registro != null) {
                if (registro.getEmail() == null || "".equals(registro.getEmail())) {
                    FacesHelper.warningMessage("Debe ingresar su usuario para poder registrarse");
                } else if (password == null || "".equals(password)) {
                    FacesHelper.warningMessage("Debe crear una contraseña para poder registrarse");
                } else if (!regService.isValidEmail(registro.getEmail())) {
                    FacesHelper.warningMessage("El correo electrónico ingresado no es válido");
                } else {
                    boolean existUser = userService.existUser(registro.getEmail());
                    if (existUser) {
                        FacesHelper.warning("Ya existe un registro con este usuario");
                    } else if (!password.equals(passreplay)) {
                        FacesHelper.warning("Las contraseñas no coinciden");
                    } else {
                        regService.save(registro, password);
                        registro = new Registro();
                        password = null;
                        passreplay = null;
                        FacesHelper.successMessage("Bienvenido", "Se ha registrado correctamente en nustra plataforma");
                    }
                }

            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            FacesHelper.errorMessage(Constants.ERROR, "Ha ocurrido un error al registrar el usuario. Favor intente nuevamente");
        }
    }

    public void updateRegistro() {
        try {
            if (registro != null) {
                if (regService.isValidEmail(registro.getEmail())) {
                    regService.update(registro);
                }
                if (cambiopass && !GeneralFunction.isNullOrEmpty(password) && password.equals(passreplay)) {
                    usu = registro.getIdusuario();
                    usu.setPass(password);
                    regService.updatePassword(usu);
                    FacesHelper.success("La clave de usuario se cambio con exito.");
                }
                FacesHelper.success("Se ha actualizado la informacion del usuario correctamente.");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            FacesHelper.error("Ha ocurrido un error al tratar de actualizar el registro de usuario");
        }
    }

}
