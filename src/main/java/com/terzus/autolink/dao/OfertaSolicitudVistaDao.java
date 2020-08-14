/*---------------------------------------------------------
* FILE: OfertaSolicitudVistaDao.java
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
import com.terzus.autolink.model.OfertaSolicitudVista;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Terzus
 * <b>Created by: </b>will
 * <b>For: </b>autolink
 * <b>On: </b>Aug 11, 2020 8:50:36 AM
 * <b>Purpose</b> 
 * <p>
 *      
 * </p>
 */
@Stateless
public class OfertaSolicitudVistaDao extends Dao<OfertaSolicitudVista, Integer>{
    
    public OfertaSolicitudVistaDao(){
        super(OfertaSolicitudVista.class);
    }
    
    @PersistenceContext(unitName = "PU")
    private EntityManager em;

    @Override
    public EntityManager getEntityManager() {
        return em;
    }
    
    public OfertaSolicitudVista findBySolAndProv(int idSolicitud, int idProveedor) throws Exception{
        Map<String, Object> param = new HashMap();
        param.put("idSolicitud", idSolicitud);
        param.put("idProveedor", idProveedor);
        try{
            return findNamedQuerySingleResult("OfertaSolicitudVista.findBySolicitudAndProveedor", param);
        }catch(Exception e){
            return null;
        }
    }

}
