/*---------------------------------------------------------
* FILE: OfertaVO.java
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
 * <b>On: </b>May 27, 2020 8:41:49 PM
 * <b>Purpose</b> 
 * <p>
 *      
 * </p>
 */
@Data
public class OfertaVO implements Serializable{
    
    private Integer cantidad;
    private Integer tiempo;
    private Double precio;
    private String estado;
    private Integer idrepuesto;
    private Integer idsolicitud;
    private Integer idproveedor;

}
