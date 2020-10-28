/*---------------------------------------------------------
* FILE: RepuestoDao.java
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
import com.terzus.autolink.model.Repuesto;
import com.terzus.autolink.model.TipoRepuesto;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * @author CEL
 * <b>Created by: </b>will
 * <b>For: </b>autolink
 * <b>On: </b>May 25, 2020 11:26:24 PM
 * <b>Purpose</b> 
 * <p>
 *      
 * </p>
 */
@Stateless
public class TipoRepuestoDao extends Dao<TipoRepuesto, Integer>{
    
    public TipoRepuestoDao(){
        super(TipoRepuesto.class);
    }

    @PersistenceContext(unitName = "PU")
    private EntityManager em;

    @Override
    public EntityManager getEntityManager() {
        return em;
    }
    
    public List<TipoRepuesto> getTipoRepuestoActivo() throws Exception {
        Query q = em.createNamedQuery("TipoRepuesto.findAllActive", TipoRepuesto.class);     
        return q.getResultList();      
    }

    
}
