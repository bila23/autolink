/*---------------------------------------------------------
* FILE: MarcaService.java
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
import com.terzus.autolink.dao.MarcaDao;
import com.terzus.autolink.model.Marca;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * @author Terzus
 * <b>Created by: </b>will
 * <b>For: </b>autolink
 * <b>On: </b>Aug 3, 2020 8:42:03 PM
 * <b>Purpose</b> 
 * <p>
 *      
 * </p>
 */
@Stateless
public class MarcaService extends Service<Marca, Integer>{

    @Inject private MarcaDao dao;

    @Override
    public Dao<Marca, Integer> getDao() {
        return dao;
    }

}
