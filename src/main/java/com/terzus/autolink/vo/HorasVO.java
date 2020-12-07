/*---------------------------------------------------------
* FILE: HorasVO.java
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
 * <b>On: </b>Nov 15, 2020 9:23:17 AM
 * <b>Purpose</b> 
 * <p>
 *      
 * </p>
 */
@Data
public class HorasVO implements Serializable{

    private String text;
    private int hora;
}
