/*---------------------------------------------------------
* FILE: UsuarioDao.java
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
import com.terzus.autolink.model.Usuario;
import java.security.MessageDigest;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.xml.bind.DatatypeConverter;

/**
 * @author CEL
 * <b>Created by: </b>will
 * <b>For: </b>autolink
 * <b>On: </b>May 24, 2020 3:04:46 PM
 * <b>Purpose</b> 
 * <p>
 *      
 * </p>
 */
@Stateless
public class UsuarioDao extends Dao<Usuario, Integer>{

    public UsuarioDao(){
        super(Usuario.class);
    }
    
    @PersistenceContext(unitName = "PU")
    private EntityManager em;

    @Override
    public EntityManager getEntityManager() {
        return em;
    }
    
    @Override
    public void save(Usuario model) throws Exception{
        model.setPass(encodeSHA256(model.getPass()));
        em.persist(model);
    }
    
     private String encodeSHA256(String password) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes("UTF-8"));
        byte[] digest = md.digest();
        return DatatypeConverter.printBase64Binary(digest);
    }
     
     public Usuario findByUser(String user) throws Exception{
         Query q = em.createNamedQuery("Usuario.findByUser");
         q.setParameter("user", user.toUpperCase());
         List<Usuario> list = q.getResultList();
         if(list == null || list.isEmpty()) return null;
         return list.get(0);
     }
}
