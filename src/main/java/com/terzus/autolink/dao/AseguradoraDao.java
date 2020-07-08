/*---------------------------------------------------------
* FILE: AseguradoraDao.java
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
import com.terzus.autolink.model.Aseguradora;
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
 * <b>On: </b>May 24, 2020 8:53:47 PM
 * <b>Purpose</b> 
 * <p>
 *      
 * </p>
 */
@Stateless
public class AseguradoraDao extends Dao<Aseguradora, Integer>{
    
    public AseguradoraDao(){
        super(Aseguradora.class);
    }

    @PersistenceContext(unitName = "PU")
    private EntityManager em;

    @Override
    public EntityManager getEntityManager() {
        return em;
    }
    
    public List<Aseguradora> findActive() throws Exception{
        return findWithNamedQuery("Aseguradora.findActive");
    }
    
    public Aseguradora findByUser(String user) throws Exception{
        Map<String, Object> param = new HashMap();
        param.put("user", user.toUpperCase());
        try{
            return findNamedQuerySingleResult("Aseguradora.findByUser", param);
        }catch(Exception e){}
        return null;
    }
}
