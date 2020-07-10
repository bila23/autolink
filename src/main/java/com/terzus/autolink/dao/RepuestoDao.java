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
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
public class RepuestoDao extends Dao<Repuesto, Integer>{
    
    public RepuestoDao(){
        super(Repuesto.class);
    }

    @PersistenceContext(unitName = "PU")
    private EntityManager em;

    @Override
    public EntityManager getEntityManager() {
        return em;
    }
    
    public List<Repuesto> findActive() throws Exception{
        return findWithNamedQuery("Repuesto.findActive");
    }
    
    public String findNameOfRepuesto(int idRep) throws Exception{
        if(idRep == 0) return null;
        Repuesto model = super.findByKey(idRep);
        if(model == null) return null;
        return model.getNombrerepuesto();
    }
}
