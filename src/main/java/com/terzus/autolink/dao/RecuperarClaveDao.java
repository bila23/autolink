/*----------------------------------------------------------
* FILE: RecuperarClaveDao.java
* PRODUCT: autolink
*----------------------------------------------------------
* CEL - UNIDAD DE INFORMÁTICA
* ÁREA DE DESARROLLO DE SISTEMAS
*----------------------------------------------------------
*/
package com.terzus.autolink.dao;

import com.bila.framework.dao.Dao;
import com.terzus.autolink.model.RecuperarClave;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author CEL
 * <b>Created by </b>WJuarez
 * <b>for </b>autolink
 * <b>package </b>com.terzus.autolink.dao
 * <b>on </b>12-04-2020 09:27:00 AM
 * <b>Purpose</b> 
 * <p>
 *  
 * </p>
 */
@Stateless
@LocalBean
public class RecuperarClaveDao extends Dao<RecuperarClave, Integer>{
    
    public RecuperarClaveDao(){
        super(RecuperarClave.class);
    }
    
    @PersistenceContext(unitName = "PU")
    private EntityManager em;

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

}