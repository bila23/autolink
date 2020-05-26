/*---------------------------------------------------------
* FILE: ModeloDao.java
* PRODUCT: autolink
*----------------------------------------------------------
* IMPORTANT NOTICE
* This program is property of CEL
* Its unauthorized use, as any code alteration without authorization 
* is prohibited
*----------------------------------------------------------
*/
package com.terzus.autolink.dao;

import com.bila.framework.dao.Dao;
import com.terzus.autolink.model.Modelo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author CEL
 * <b>Created by: </b>will
 * <b>For: </b>autolink
 * <b>On: </b>May 25, 2020 7:37:03 PM
 * <b>Purpose</b> 
 * <p>
 *      
 * </p>
 */
@Stateless
public class ModeloDao extends Dao<Modelo, Integer>{
    
    public ModeloDao(){
        super(Modelo.class);
    }

    @PersistenceContext(unitName = "PU")
    private EntityManager em;

    @Override
    public EntityManager getEntityManager() {
        return em;
    }
    
    public List<Modelo> findActiveByMarca(Integer marca) throws Exception{
        if(marca == null || marca == 0) return null;
        String idMarca = marca.toString();
        Map<String, Object> param = new HashMap();
        param.put("idmarca", idMarca);
        return findWithNamedQuery("Modelo.findActiveByMarca", param);
    }
}
