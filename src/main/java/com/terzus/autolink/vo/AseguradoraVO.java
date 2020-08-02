/*---------------------------------------------------------
* FILE: AseguradoraVO.java
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
 * <b>On: </b>Aug 1, 2020 4:53:31 PM
 * <b>Purpose</b> 
 * <p>
 *      
 * </p>
 */
@Data
public class AseguradoraVO implements Serializable{

    private Integer id;
    private String nombreaseguradora;
    private String cargo;
    private String razonsocial;
    private String nit;
    private String iva;
    private String estado;
    private String usuariocrea;
    private Date fechacreacion;
    private int idusuario;

}
