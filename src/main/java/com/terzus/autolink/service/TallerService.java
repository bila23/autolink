/*---------------------------------------------------------
* FILE: TallerService.java
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
import com.terzus.autolink.dao.TallerDao;
import com.terzus.autolink.model.Taller;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * @author Terzus
 * <b>Created by: </b>will
 * <b>For: </b>autolink
 * <b>On: </b>Jul 8, 2020 10:01:52 AM
 * <b>Purpose</b> 
 * <p>
 *    Servicio para CRUD de taller  
 * </p>
 */
@Stateless
public class TallerService extends Service<Taller, Integer>{

    @Inject private TallerDao dao;

    @Override
    public Dao<Taller, Integer> getDao() {
        return dao;
    }
    
    /**
     * Recupero el ID del taller por su usuario
     * @param user String con el usuario del taller
     * @return int con el codigo del taller
     * @throws Exception 
     */
    public int findCodeByUser(String user) throws Exception{
        if(user == null || user.equals("")) return 0;
        Taller model = dao.findByUser(user);
        if(model == null) return 0;
        return model.getId();
    }
}
