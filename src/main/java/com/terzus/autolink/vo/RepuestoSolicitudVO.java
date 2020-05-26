/*---------------------------------------------------------
* FILE: RepuestoSolicitudVO.java
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
import lombok.Data;

/**
 * @author CEL
 * <b>Created by: </b>will
 * <b>For: </b>autolink
 * <b>On: </b>May 25, 2020 11:42:27 PM
 * <b>Purpose</b> 
 * <p>
 *      
 * </p>
 */
@Data
public class RepuestoSolicitudVO implements Serializable{

    private Integer id;
    private Integer idrepuesto;
    private Integer idsolicitud;
    private String estado;
    private String repuesto;
}
