/*---------------------------------------------------------
* FILE: SolicitudDespDao.java
* PRODUCT: autolink
*----------------------------------------------------------
* IMPORTANT NOTICE
* This program is property of Terzus
* Its unauthorized use, as any code alteration without
* authorization is prohibited
*----------------------------------------------------------
*/
package com.terzus.autolink.dao;

import com.bila.framework.dao.Dao;
import com.terzus.autolink.model.SolicitudDespachada;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * @author Terzus
 * <b>Created by: </b>will
 * <b>For: </b>autolink
 * <b>On: </b>Aug 5, 2020 10:44:01 PM
 * <b>Purpose</b> 
 * <p>
 *      
 * </p>
 */
@Stateless
public class SolicitudDespDao extends Dao<SolicitudDespachada, Integer>{
    
    public SolicitudDespDao(){
        super(SolicitudDespachada.class);
    }

    @PersistenceContext(unitName = "PU")
    private EntityManager em;

    @Override
    public EntityManager getEntityManager() {
        return em;
    }
    
    public void updateEstado(int idSol, int idProv, String state) throws Exception{
        Map<String, Object> param = new HashMap();
        param.put("idSolicitud", idSol);
        param.put("idProveedor", idProv);
        param.put("estado", state);
        executeUpdateOrDelete("SolicitudDespachada.updateEstado", param);
    }
    
    public List<SolicitudDespachada> findBySolAndState(int idSol, String state) throws Exception{
        Map<String, Object> param = new HashMap();
        param.put("idSolicitud", idSol);
        param.put("estado", state);
        return findWithNamedQuery("SolicitudDespachada.findBySolAndState", param);
    }
    
    public SolicitudDespachada findBySolAndProv(int idSol, int idProv) throws Exception{
        Query q = em.createNamedQuery("SolicitudDespachada.findBySolAndProv");
        q.setParameter("idSolicitud", idSol);
        q.setParameter("idProveedor", idProv);
        try{
            SolicitudDespachada model = (SolicitudDespachada) q.getSingleResult();
            return model;
        }catch(Exception e){}
        return null;
    }
    
    public List<SolicitudDespachada> findBySolicitud(int idSol) throws Exception{
        Map<String, Object> param = new HashMap();
        param.put("idSolicitud", idSol);
        return findWithNamedQuery("SolicitudDespachada.findBySol", param);
    }
    
}