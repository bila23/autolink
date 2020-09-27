/*---------------------------------------------------------
* FILE: TipoVehiculoService.java
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
import com.terzus.autolink.dao.TipoVehiculoDao;
import com.terzus.autolink.model.TipoVehiculo;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * @author Terzus
 * <b>Created by: </b>will
 * <b>For: </b>autolink
 * <b>On: </b>Sep 27, 2020 10:30:03 AM
 * <b>Purpose</b> 
 * <p>
 *      
 * </p>
 */
@Stateless
public class TipoVehiculoService extends Service<TipoVehiculo, Integer>{

    @Inject private TipoVehiculoDao dao;

    @Override
    public Dao<TipoVehiculo, Integer> getDao() {
        return dao;
    }
}
