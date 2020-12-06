/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.terzus.autolink.dao;

import com.bila.framework.dao.Dao;
import com.terzus.autolink.model.Horarios;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.omnifaces.util.Utils;

/**
 *
 * @author josemauricioherrera
 */
@Stateless
public class HorariosDao extends Dao<Horarios, Integer> {

    public HorariosDao() {
        super(Horarios.class);
    }

    @PersistenceContext(unitName = "PU")
    private EntityManager em;

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public Horarios getHorarioByDiaAndJornada(String dia, int jornada) throws Exception {
        Query q = em.createNamedQuery("Horarios.findByDiaAndJornada", Horarios.class);
        q.setParameter("dia", dia);
        q.setParameter("jornada", jornada);
        Object obj = q.getSingleResult();
        if (obj != null && !Utils.isEmpty(obj)) {
            return (Horarios) obj;
        }
        return null;
    }

}
