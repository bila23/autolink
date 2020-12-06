/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.terzus.autolink.dao;

import com.bila.framework.commons.GeneralFunction;
import com.bila.framework.dao.Dao;
import com.google.protobuf.Empty;
import com.terzus.autolink.model.Registro;
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
public class RegistroDao extends Dao<Registro, Integer> {

    public RegistroDao() {
        super(Registro.class);
    }

    @PersistenceContext(unitName = "PU")
    private EntityManager em;

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public Registro getRegistroByUser(String user) throws Exception {
        Query q = em.createNamedQuery("Registro.findByEmail", Registro.class);
        q.setParameter("email", user != null ? user.toLowerCase() : "");
        List<Registro> list = q.getResultList();
        if (!GeneralFunction.isNullOrEmpty(list)) {
            if (list.size() > 0) {
                return list.get(0);
            } else {
                return null;
            }
        }
        return null;
    }

}
