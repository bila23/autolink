/*---------------------------------------------------------
* FILE: ProveedorVO.java
* PRODUCT: autolink
*----------------------------------------------------------
* IMPORTANT NOTICE
* This program is property of Terzus
* Its unauthorized use, as any code alteration without
* authorization is prohibited
*----------------------------------------------------------
*/
package com.terzus.autolink.vo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author Terzus
 * <b>Created by: </b>will
 * <b>For: </b>autolink
 * <b>On: </b>Aug 2, 2020 4:45:36 PM
 * <b>Purpose</b> 
 * <p>
 *      
 * </p>
 */
@Data
public class ProveedorVO implements Serializable{

    private Integer id;
    private String nombreproveedor;
    private String direccion;
    private String telefono;
    private String cargo;
    private String razonsocial;
    private String cuentabancaria;
    private String nit;
    private String estado;
    private String usuariocrea;
    private Date fechacreacion;
    private int idUsuario;

}
