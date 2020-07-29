/*---------------------------------------------------------
* FILE: TallerVO.java
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
 * <b>On: </b>Jul 29, 2020 10:33:44 AM
 * <b>Purpose</b> 
 * <p>
 *      
 * </p>
 */
@Data
public class TallerVO implements Serializable{

    private Integer id;
    private String nombreTaller;
    private String telefono;
    private String cargo;
    private String razonsocial;
    private String direccion;
    private String estado;
    private String usuariocrea;
    private Date fechacreacion;
    private int idUsuario;

}
