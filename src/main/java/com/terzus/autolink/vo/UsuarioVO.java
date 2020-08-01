/*---------------------------------------------------------
* FILE: UsuarioVO.java
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
 * <b>On: </b>Jul 29, 2020 3:17:13 PM
 * <b>Purpose</b> 
 * <p>
 *      
 * </p>
 */
@Data
public class UsuarioVO implements Serializable{

    private Integer id;
    private String user;
    private String pass;
    private String nombre;
    private Integer idtipo;
    private String estado;
    private String usuariocrea;
    private Date fechacreacion;
    
}
