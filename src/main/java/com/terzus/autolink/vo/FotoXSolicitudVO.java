/*---------------------------------------------------------
* FILE: FotoXSolicitudVO.java
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
import lombok.Data;

/**
 * @author Terzus
 * <b>Created by: </b>will
 * <b>For: </b>autolink
 * <b>On: </b>Oct 10, 2020 8:32:06 PM
 * <b>Purpose</b> 
 * <p>
 *      
 * </p>
 */
@Data
public class FotoXSolicitudVO implements Serializable{

    private int id;
    private String foto;
    private String url;
    private int idSol;
}
