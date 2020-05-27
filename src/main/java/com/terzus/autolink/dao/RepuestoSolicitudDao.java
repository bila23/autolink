/*---------------------------------------------------------
* FILE: RepuestoSolicitudDao.java
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
import com.terzus.autolink.model.Respuestoxsolicitud;
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
 * <b>On: </b>May 25, 2020 11:33:12 PM
 * <b>Purpose</b> 
 * <p>
 *      
 * </p>
 */
@Stateless
public class RepuestoSolicitudDao extends Dao<Respuestoxsolicitud, Integer>{
    
    public RepuestoSolicitudDao(){
        super(Respuestoxsolicitud.class);
    }

    @PersistenceContext(unitName = "PU")
    private EntityManager em;

    @Override
    public EntityManager getEntityManager() {
        return em;
    }
    
    public List<Respuestoxsolicitud> findBySolicitud(int id) throws Exception{
        Map<String, Object> param = new HashMap();
        param.put("idsolicitud", id);
        return findWithNamedQuery("Respuestoxsolicitud.findBySolicitud", param);
    }
    
    public void updateAplica(String aplica, int id) throws Exception{
        Map<String, Object> param = new HashMap();
        param.put("aplica", aplica);
        param.put("id", id);
        executeUpdateOrDelete("Respuestoxsolicitud.updateAplica", param);
    }
}
