/*---------------------------------------------------------
* FILE: RepuestosAllSolVO.java
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
 * <b>On: </b>May 30, 2020 3:53:50 PM
 * <b>Purpose</b> 
 * <p>
 *      
 * </p>
 */
@Data
public class RepuestosAllSolVO implements Serializable{

    private Integer id;
    private Integer idrepuesto;
    private Integer idsolicitud;
    private String estado;
    private String aplica;
    private String repuesto;
    private List<OfertaProveedorVO> opList;
    
}
