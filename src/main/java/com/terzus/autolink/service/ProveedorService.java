/*---------------------------------------------------------
* FILE: ProveedorService.java
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
import com.terzus.autolink.dao.ProveedorDao;
import com.terzus.autolink.model.Proveedor;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * @author CEL
 * <b>Created by: </b>will
 * <b>For: </b>autolink
 * <b>On: </b>May 28, 2020 6:54:07 PM
 * <b>Purpose</b> 
 * <p>
 *      
 * </p>
 */
@Stateless
public class ProveedorService extends Service<Proveedor, Integer>{
    
    @Inject ProveedorDao dao;

    @Override
    public Dao<Proveedor, Integer> getDao() {
        return dao;
    }
    
    public Proveedor findByUser(String user) throws Exception{
        if(user == null || user.equals("")) return null;
        return dao.findByUser(user);
    }
    
    public int findIdProvByUser(String user) throws Exception{
        Proveedor model = findByUser(user);
        if(model == null) return 0;
        return model.getId();
    }

}
