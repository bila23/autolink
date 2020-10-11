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
import com.terzus.autolink.model.Proveedor;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
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
    
    @Inject private ProveedorDao provDao;
    
    public OfertaProvDao(){
        super(Ofertaproveedor.class);
    }

    @PersistenceContext(unitName = "PU")
    private EntityManager em;

    @Override
    public EntityManager getEntityManager() {
        return em;
    }
    
    public Ofertaproveedor findBySolAndRepOrderMinPrice(int idSol, int idRep) throws Exception{
        Query q = em.createNamedQuery("Ofertaproveedor.findBySolAndRepOrderMinPrice");
        q.setParameter("idsolicitud", idSol);
        q.setParameter("idrepuesto", idRep);
        List<Ofertaproveedor> list = q.getResultList();
        if(list == null || list.isEmpty()) return null;
        return list.get(0);
    }
    
    public List<Ofertaproveedor> findBySolAndRep(int idSol, int idRep) throws Exception{
        Query q = em.createNamedQuery("Ofertaproveedor.findBySolAndRep");
        q.setParameter("idsolicitud", idSol);
        q.setParameter("idrepuesto", idRep);
        return q.getResultList();
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
    
    public List<Ofertaproveedor> findBySolAndProv(int idSol, int idProv) throws Exception{
        Query q = em.createNamedQuery("Ofertaproveedor.findBySolAndProv");
        q.setParameter("idsolicitud", idSol);
        q.setParameter("idproveedor", idProv);
        return q.getResultList();
    }
    
    public List<Ofertaproveedor> findBySolicitud(int idSol) throws Exception{
        Query q = em.createNamedQuery("Ofertaproveedor.findBySolicitud");
        q.setParameter("idsolicitud", idSol);
        return q.getResultList();
    }
    
    public void updateGanador(int idSol, int idProv) throws Exception{
        Query q = em.createNamedQuery("Ofertaproveedor.updateGanador");
        q.setParameter("idsolicitud", idSol);
        q.setParameter("idproveedor", idProv);
        q.executeUpdate();
    }
    
    public void updateGanadorBySolAndProvAndRep(int idSol, int idProv, int idRep) throws Exception{
        Query q = em.createNamedQuery("Ofertaproveedor.updateGanadorBySolAndProvAndRep");
        q.setParameter("idsolicitud", idSol);
        q.setParameter("idproveedor", idProv);
        q.setParameter("idrepuesto", idRep);
        q.executeUpdate();        
    }
    
    public Proveedor findWinnerBySolicitud(int idSol) throws Exception{
        Query q = em.createNamedQuery("Ofertaproveedor.findWinnerBySolicitud");
        q.setParameter("idsolicitud", idSol);
        List<Ofertaproveedor> list = q.getResultList();
        if(list == null || list.isEmpty()) return null;
        Ofertaproveedor model = list.get(0);
        if(model.getIdproveedor() == null || model.getIdproveedor() == 0) return null;
        Proveedor proveedor = provDao.findByKey(model.getIdproveedor());
        if(proveedor == null) return null;
        return proveedor;
    }
    
    public Integer getIdProveedorWinnerBySolicitudCliente(int idSol) throws Exception{
        Query q = em.createNamedQuery("Ofertaproveedor.findWinnerBySolicitudCliente");
        q.setParameter("idsolicitud", idSol);    
        List<Object> list = q.getResultList();
        if(list == null || list.isEmpty() ) return null;
       
        Object[] obj = (Object[]) list.get(0);
        if (obj ==null) return null;
      return  Integer.valueOf(String.valueOf(obj[0]));
    }
    
    public List<Object[]> findProvTotalBySolicitud(int idSol) throws Exception{
        Query q = em.createNamedQuery("Ofertaproveedor.findProvTotalBySolicitud");
        q.setParameter("idsolicitud", idSol);
        return q.getResultList();
    }
    
    public Long findTotalRepuestosBySolAndProv(int idSol, int idProv) throws Exception{
        Query q = em.createNamedQuery("Ofertaproveedor.findTotalRepuestosBySolAndProv");
        q.setParameter("idsolicitud", idSol);
        q.setParameter("idproveedor", idProv);
        try{
            return (Long) q.getSingleResult();
        }catch(Exception e){
            return Long.valueOf(0);
        }
    }
}
