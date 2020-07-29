/*---------------------------------------------------------
* FILE: FotoSolService.java
* PRODUCT: autolink
*----------------------------------------------------------
* IMPORTANT NOTICE
* This program is property of CEL
* Its unauthorized use, as any code alteration without authorization 
* is prohibited
*----------------------------------------------------------
*/
package com.terzus.autolink.service;

import com.bila.framework.commons.FileHelper;
import com.bila.framework.commons.GeneralFunction;
import com.bila.framework.dao.Dao;
import com.bila.framework.service.Service;
import com.terzus.autolink.commons.Constants;
import com.terzus.autolink.dao.FotoSolDao;
import com.terzus.autolink.model.Fotoxsolicitud;
import com.terzus.autolink.model.Solicitud;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 * @author CEL
 * <b>Created by: </b>will
 * <b>For: </b>autolink
 * <b>On: </b>May 30, 2020 11:28:21 AM
 * <b>Purpose</b> 
 * <p>
 *      
 * </p>
 */
@Stateless
public class FotoSolService extends Service<Fotoxsolicitud, Integer>{
    
    @Inject private FotoSolDao dao;
    @Inject private SolicitudService solService;

    @Override
    public Dao<Fotoxsolicitud, Integer> getDao() {
        return dao;
    }
    
    public List<Fotoxsolicitud> findBySolicitud(int idSol) throws Exception{
        if(idSol == 0) return null;
        return dao.findBySolicitud(idSol);
    }
    
    public void save(int idSol, InputStream in, String file) throws Exception{
        if(idSol == 0 || in == null) return;
        Solicitud solModel = solService.findByKey(idSol);
        if(solModel == null) return;
        Fotoxsolicitud model = new Fotoxsolicitud();
        model.setIdSolicitud(solModel);
        dao.save(model);
        //UNA VEZ HAYA GUARDADO EL REGISTRO GUARDO EL ARCHIVO
        String fileName = idSol + "_" + model.getId() + "." + GeneralFunction.getExtension(file);
        FileHelper.copyFile(in, Constants.PATH_IMAGE, fileName);
        model.setFoto(fileName);
        dao.update(model);
    }
    
    public List<StreamedContent> findImageBySol(int idSol) throws Exception{
        if(idSol == 0) return null;
        List<Fotoxsolicitud> list = dao.findBySolicitud(idSol);
        if(list == null || list.isEmpty()) return null;
        List<StreamedContent> scList = new ArrayList();
        return scList;
    }

}
