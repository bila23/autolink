/*---------------------------------------------------------
* FILE: EntregaClienteVO.java
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
 * <b>On: </b>Nov 21, 2020 8:30:46 PM
 * <b>Purpose</b> 
 * <p>
 *      
 * </p>
 */
@Data
public class EntregaClienteVO implements Serializable{

    private Integer id;
    private Integer solicitud;
    private Integer proveedor;
    private Integer repuesto;
    private String entregado;
    private String userCreate;
    private Date dateCreate;
    private String nameRepuesto;
    private String nameProveedor;
    private boolean seEntrego;

}
