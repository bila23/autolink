/*---------------------------------------------------------
* FILE: RepuestoService.java
* PRODUCT: autolink
*----------------------------------------------------------
* IMPORTANT NOTICE
* This program is property of Terzus
* Its unauthorized use, as any code alteration without
* authorization is prohibited
*----------------------------------------------------------
*/
package com.terzus.autolink.service;

import com.bila.framework.dao.Dao;
import com.bila.framework.service.Service;
import com.terzus.autolink.dao.RepuestoDao;
import com.terzus.autolink.model.Repuesto;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * @author Terzus
 * <b>Created by: </b>will
 * <b>For: </b>autolink
 * <b>On: </b>Aug 4, 2020 7:54:59 PM
 * <b>Purpose</b> 
 * <p>
 *      
 * </p>
 */
@Stateless
public class RepuestoService extends Service<Repuesto, Integer>{
    
    @Inject private RepuestoDao dao;

    @Override
    public Dao<Repuesto, Integer> getDao() {
        return dao;
    }

    public String findName(int idRep) throws Exception{
        if(idRep == 0) return null;
        Repuesto model = dao.findByKey(idRep);
        if(model == null) return null;
        return model.getNombrerepuesto();
    }
}
