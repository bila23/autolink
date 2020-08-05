/*---------------------------------------------------------
* FILE: ModeloService.java
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
import com.terzus.autolink.dao.ModeloDao;
import com.terzus.autolink.model.Modelo;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * @author Terzus
 * <b>Created by: </b>will
 * <b>For: </b>autolink
 * <b>On: </b>Aug 4, 2020 11:55:05 AM
 * <b>Purpose</b> 
 * <p>
 *      
 * </p>
 */
@Stateless
public class ModeloService extends Service<Modelo, Integer>{

    @Inject private ModeloDao dao;

    @Override
    public Dao<Modelo, Integer> getDao() {
        return dao;
    }
    
}
