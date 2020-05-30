/*---------------------------------------------------------
* FILE: FotoSolDao.java
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
import com.terzus.autolink.model.Fotoxsolicitud;
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
 * <b>On: </b>May 30, 2020 11:26:04 AM
 * <b>Purpose</b> 
 * <p>
 *      DAO para manejo de fotos por solicitudes
 * </p>
 */
@Stateless
public class FotoSolDao extends Dao<Fotoxsolicitud, Integer>{
    
    public FotoSolDao(){
        super(Fotoxsolicitud.class);
    }

    @PersistenceContext(unitName = "PU")
    private EntityManager em;

    @Override
    public EntityManager getEntityManager() {
        return em;
    }
    
    public List<Fotoxsolicitud> findBySolicitud(int idSol) throws Exception{
        Map<String, Object> param = new HashMap();
        param.put("idSol", idSol);
        return findWithNamedQuery("Fotoxsolicitud.findBySolicitud", param);
    }
}
