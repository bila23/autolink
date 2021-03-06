/*---------------------------------------------------------
* FILE: SolicitudVO.java
* PRODUCT: autolink
*----------------------------------------------------------
* IMPORTANT NOTICE
* This program is property of CEL
* Its unauthorized use, as any code alteration without authorization 
* is prohibited
*----------------------------------------------------------
*/
package com.terzus.autolink.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.Data;

/**
 * @author CEL
 * <b>Created by: </b>will
 * <b>For: </b>autolink
 * <b>On: </b>May 24, 2020 8:50:15 PM
 * <b>Purpose</b> 
 * <p>
 *      
 * </p>
 */
@Data
public class SolicitudVO implements Serializable{

    private Integer id;
    private Integer idtaller;
    private String taller;
    private Integer idaseguradora;
    private String aseguradora;
    private Integer idmarca;
    private String marca;
    private Integer idusuario;
    private Integer aniocarro;
    private String tipovehiculo;
    private String placa;
    private String chasis;
    private String motor;
    private String poliza;
    private String siniestro;
    private String nombreasegurado;
    private String codigosolicitud;
    private String estado;
    private String comentariostaller;
    private String comentariosaseguradora;
    private String comentariosproveedores;
    private Date fechainicio;
    private Date fechafin;
    private Integer idmodelo;
    private String modelo;
    private Date fechacreacion;
    private String usuariocrea;
    private List<RepuestoSolicitudVO> repAplicaList;
    private int idProvWinner;
    private String proveedorWinner;
    private String pendingHours;
    private List<RepuestosAllSolVO> repAllList;
    private int horaFinal;
    private String parcial;
    private boolean viewed;
    private int idTipoVeh;
    private String tipoVehiculo;
    private List<FotoXSolicitudVO> fotoList;
    private boolean hasOffers;
    private String styleClass;
    
}
