/*---------------------------------------------------------
* FILE: TallerDao.java
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
import com.terzus.autolink.model.Taller;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * @author CEL
 * <b>Created by: </b>will
 * <b>For: </b>autolink
 * <b>On: </b>May 24, 2020 8:52:23 PM
 * <b>Purpose</b> 
 * <p>
 *      
 * </p>
 */
@Stateless
public class TallerDao extends Dao<Taller, Integer>{
    
    public TallerDao(){
        super(Taller.class);
    }

    @PersistenceContext(unitName = "PU")
    private EntityManager em;

    @Override
    public EntityManager getEntityManager() {
        return em;
    }
    
    public List<Taller> findActive() throws Exception{
        return findWithNamedQuery("Taller.findActive");
    }
    
    public Taller findByUser(String user) throws Exception{
        if(user == null || user.equals("")) return null;
        Query q = em.createNamedQuery("Taller.findByUser");
        q.setParameter("user", user.toUpperCase());
        List<Taller> list = q.getResultList();
        if(list == null || list.isEmpty()) return null;
        return list.get(0);
    }
}
