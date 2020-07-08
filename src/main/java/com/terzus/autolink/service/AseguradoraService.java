/*---------------------------------------------------------
* FILE: AseguradoraService.java
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
import com.terzus.autolink.dao.AseguradoraDao;
import com.terzus.autolink.model.Aseguradora;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * @author Terzus
 * <b>Created by: </b>will
 * <b>For: </b>autolink
 * <b>On: </b>Jul 8, 2020 11:00:58 AM
 * <b>Purpose</b> 
 * <p>
 *      Servicio para aseguradora
 * </p>
 */
@Stateless
public class AseguradoraService extends Service<Aseguradora, Integer>{

    @Inject private AseguradoraDao dao;

    @Override
    public Dao<Aseguradora, Integer> getDao() {
        return dao;
    }
    
    public int findIdByUser(String user) throws Exception{
        if(user == null || user.equals("")) return 0;
        Aseguradora model = dao.findByUser(user);
        if(model == null) return 0;
        return model.getId();
    }
    
}
