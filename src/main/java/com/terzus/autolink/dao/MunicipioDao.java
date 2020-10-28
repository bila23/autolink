/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.terzus.autolink.dao;

import com.bila.framework.dao.Dao;
import com.terzus.autolink.model.Municipio;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author josemauricioherrera
 */
@Stateless
public class MunicipioDao extends Dao<Municipio, Integer>  {

    public MunicipioDao() {
        super(Municipio.class);
    }

    @PersistenceContext(unitName = "PU")
    private EntityManager em;
    
    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public List<Municipio> getListMunicipiosByDelpto(Integer idDepto) throws Exception{
        Query q =em.createNamedQuery("Municipio.findByDepto", Municipio.class);
        q.setParameter("idDepto", idDepto);
        return q.getResultList();
    }
   
}
