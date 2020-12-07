/*---------------------------------------------------------
* FILE: SolicitudPPASchedule.java
* PRODUCT: autolink
*----------------------------------------------------------
* IMPORTANT NOTICE
* This program is property of Terzus
* Its unauthorized use, as any code alteration without
* authorization is prohibited
*----------------------------------------------------------
*/
package com.terzus.autolink.commons;

import com.terzus.autolink.service.SolicitudService;
import javax.ejb.Schedule;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Terzus
 * <b>Created by: </b>will
 * <b>For: </b>autolink
 * <b>On: </b>Aug 5, 2020 7:06:59 PM
 * <b>Purpose</b> 
 * <p>
 *      
 * </p>
 */
@Stateless
@Slf4j
@Startup
public class SolicitudPPASchedule {
    
    @Inject private SolicitudService solService;

    //@Schedule(minute="0", hour="*/1", persistent = false, info = "Cada Hora")
    @Schedule(minute="*/5", hour="*", persistent = false, info = "Cada 5 minutos")
    public void changeToPPA(){
        try{
            solService.updateDesiertas();
            solService.changeCoaToPea();
        }catch(Exception e){
            log.error(e.getMessage(), e);
        }
    }
    
}
