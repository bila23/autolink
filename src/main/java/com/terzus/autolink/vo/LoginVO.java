/*---------------------------------------------------------
* FILE: LoginVO.java
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
 * <b>On: </b>May 24, 2020 11:02:23 AM
 * <b>Purpose</b> 
 * <p>
 *      
 * </p>
 */
@Data
public class LoginVO implements Serializable{

    private String username;
    private String password;
}
