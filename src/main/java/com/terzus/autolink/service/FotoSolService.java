/*---------------------------------------------------------
* FILE: FotoSolService.java
* PRODUCT: autolink
*----------------------------------------------------------
* IMPORTANT NOTICE
* This program is property of CEL
* Its unauthorized use, as any code alteration without authorization 
* is prohibited
*----------------------------------------------------------
*/
package com.terzus.autolink.service;

import com.bila.framework.dao.Dao;
import com.bila.framework.service.Service;
import com.terzus.autolink.dao.FotoSolDao;
import com.terzus.autolink.model.Fotoxsolicitud;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * @author CEL
 * <b>Created by: </b>will
 * <b>For: </b>autolink
 * <b>On: </b>May 30, 2020 11:28:21 AM
 * <b>Purpose</b> 
 * <p>
 *      
 * </p>
 */
@Stateless
public class FotoSolService extends Service<Fotoxsolicitud, Integer>{
    
    @Inject private FotoSolDao dao;

    @Override
    public Dao<Fotoxsolicitud, Integer> getDao() {
        return dao;
    }
    
    public List<Fotoxsolicitud> findBySolicitud(int idSol) throws Exception{
        if(idSol == 0) return null;
        return dao.findBySolicitud(idSol);
    }

}
