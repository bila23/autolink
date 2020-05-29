/*---------------------------------------------------------
* FILE: OfertaProvDao.java
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
import com.terzus.autolink.model.Ofertaproveedor;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * @author CEL
 * <b>Created by: </b>will
 * <b>For: </b>autolink
 * <b>On: </b>May 27, 2020 7:12:54 PM
 * <b>Purpose</b> 
 * <p>
 *      
 * </p>
 */
@Stateless
public class OfertaProvDao extends Dao<Ofertaproveedor, Integer> {
    
    public OfertaProvDao(){
        super(Ofertaproveedor.class);
    }

    @PersistenceContext(unitName = "PU")
    private EntityManager em;

    @Override
    public EntityManager getEntityManager() {
        return em;
    }
    
    public Ofertaproveedor findBySolicitudAndProveedorAndRepuesto(int idSol, int idProv, int idRep) throws Exception{
        Query q = em.createNamedQuery("Ofertaproveedor.findBySolicitudAndProveedorAndRepuesto");
        q.setParameter("idsolicitud", idSol);
        q.setParameter("idproveedor", idProv);
        q.setParameter("idrepuesto", idRep);
        List<Ofertaproveedor> list = q.getResultList();
        if(list == null || list.isEmpty()) return null;
        return list.get(0);
    }
    
    public List<Ofertaproveedor> findBySolicitud(int idSol) throws Exception{
        Query q = em.createNamedQuery("Ofertaproveedor.findBySolicitud");
        q.setParameter("idsolicitud", idSol);
        return q.getResultList();
    }
}
