/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.terzus.autolink.service;

import com.bila.framework.dao.Dao;
import com.bila.framework.service.Service;
import com.terzus.autolink.dao.DepartamentoDao;
import com.terzus.autolink.dao.MunicipioDao;
import com.terzus.autolink.model.Departamento;
import com.terzus.autolink.model.Municipio;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author josemauricioherrera
 */
@Stateless
public class MunicipioService extends Service<Municipio, Integer> {

    @Inject private MunicipioDao muniDao;
    @Inject private DepartamentoDao deptoDao;
    
    @Override
    public Dao<Municipio, Integer> getDao() {
        return muniDao;
    }

   public List<Departamento> getListDepartamentos() throws Exception{
       return deptoDao.getAllDeptos();
   }
   
   public List<Municipio> getListMunicipiosByDepto(Integer idDepto) throws Exception{
       return muniDao.getListMunicipiosByDelpto(idDepto);
   }
}
