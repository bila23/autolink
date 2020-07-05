/*---------------------------------------------------------
* FILE: OfertaPrecioVO.java
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
import java.util.List;
import lombok.Data;

/**
 * @author CEL
 * <b>Created by: </b>will
 * <b>For: </b>autolink
 * <b>On: </b>Jun 6, 2020 10:13:56 PM
 * <b>Purpose</b> 
 * <p>
 *      
 * </p>
 */
@Data
public class OfertaPrecioVO implements Serializable{

    private int idProveedor;
    private double precioTotal;
    private int idSol;
    private int numero;
    private List<OfertaProveedorVO> list;
    private Long totalRepuestosAplica;
    
}
