/*---------------------------------------------------------
* FILE: ProveedorDao.java
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
import com.terzus.autolink.model.Proveedor;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * @author CEL
 * <b>Created by: </b>will
 * <b>For: </b>autolink
 * <b>On: </b>May 28, 2020 6:01:24 PM
 * <b>Purpose</b> 
 * <p>
 *      
 * </p>
 */
@Stateless
public class ProveedorDao extends Dao<Proveedor, Integer>{
    
    public ProveedorDao(){
        super(Proveedor.class);
    }

    @PersistenceContext(unitName = "PU")
    private EntityManager em;

    @Override
    public EntityManager getEntityManager() {
        return em;
    }
    
    public Proveedor findByUser(String user) throws Exception{
        Query q = em.createNamedQuery("Proveedor.findByUser");
        q.setParameter("idusuario", user.toUpperCase());
        List<Proveedor> list = q.getResultList();
        if(list == null || list.isEmpty()) return null;
        return list.get(0);
    }
}
