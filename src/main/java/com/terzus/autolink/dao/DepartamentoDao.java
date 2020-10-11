/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.terzus.autolink.dao;

import com.bila.framework.dao.Dao;
import com.terzus.autolink.model.Departamento;
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
public class DepartamentoDao extends Dao<Departamento, Integer>  {

    public DepartamentoDao() {
        super(Departamento.class);
    }
    
@PersistenceContext(unitName = "PU")
    private EntityManager em;

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public List<Departamento> getAllDeptos()throws Exception{
          Query q =em.createNamedQuery("Departamento.findAll", Departamento.class);
          List<Departamento> list = q.getResultList();
        return list;
    }

  
}
