/*---------------------------------------------------------
* FILE: OfertaProveedorVO.java
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
 * <b>On: </b>May 28, 2020 5:59:10 PM
 * <b>Purpose</b> 
 * <p>
 *      
 * </p>
 */
@Data
public class OfertaProveedorVO implements Serializable{

    private Integer id;
    private Integer idsolicitud;
    private Integer idrepuesto;
    private Integer idproveedor;
    private Integer cantidad;
    private String estado;
    private String ganador;
    private Integer tiempo;
    private Double precio;
    private String proveedor;
    private String repuesto;
    private Integer numero;
    private String estadoLeyenda;
    private Long cantidadSolOriginal;
}
