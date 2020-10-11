/*---------------------------------------------------------
* FILE: OfertaProvService.java
* PRODUCT: autolink
*----------------------------------------------------------
* IMPORTANT NOTICE
* This program is property of CEL
* Its unauthorized use, as any code alteration without authorization 
* is prohibited
*----------------------------------------------------------
*/
package com.terzus.autolink.service;

import com.bila.framework.dao.Dao;
import com.bila.framework.service.Service;
import com.terzus.autolink.dao.OfertaProvDao;
import com.terzus.autolink.dao.ProveedorDao;
import com.terzus.autolink.dao.RepuestoDao;
import com.terzus.autolink.model.Ofertaproveedor;
import com.terzus.autolink.model.Proveedor;
import com.terzus.autolink.model.Repuesto;
import com.terzus.autolink.model.Respuestoxsolicitud;
import com.terzus.autolink.vo.OfertaPrecioVO;
import com.terzus.autolink.vo.OfertaProveedorVO;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.apache.commons.beanutils.PropertyUtils;

/**
 * @author CEL
 * <b>Created by: </b>will
 * <b>For: </b>autolink
 * <b>On: </b>May 27, 2020 7:26:53 PM
 * <b>Purpose</b> 
 * <p>
 *      
 * </p>
 */
@Stateless
public class OfertaProvService extends Service<Ofertaproveedor, Integer> {

    @Inject private OfertaProvDao dao;
    @Inject private RepuestoDao repDao;
    @Inject private ProveedorDao provDao;
    @Inject private RepuestoSolicitudService repSolService;

    @Override
    public Dao<Ofertaproveedor, Integer> getDao() {
        return dao;
    }
    
    public Long findTotalRepuestosBySolAndProv(int idSol, int idProv) throws Exception{
        if(idSol == 0 || idProv == 0) return Long.valueOf(0);
        return dao.findTotalRepuestosBySolAndProv(idSol, idProv);
    }
    
    public List<OfertaPrecioVO> findOfertaTotalBySol(int idSol) throws Exception{
        if(idSol == 0) return null;
        List<Object[]> list = dao.findProvTotalBySolicitud(idSol);
        if(list == null || list.isEmpty()) return null;
        List<OfertaPrecioVO> lst = new ArrayList();
        OfertaPrecioVO vo = null;
        int i = 0, size = list.size();
        Object[] obj = null;
        for(i=0; i<size; i++){
            obj = list.get(i);
            vo = new OfertaPrecioVO();
            vo.setIdProveedor((int) obj[0]);
            vo.setIdSol(idSol);
            vo.setNumero(i + 1);
            vo.setPrecioTotal((double) obj[1]);
            vo.setTotalRepuestosAplica(this.findTotalRepuestosBySolAndProv(idSol, vo.getIdProveedor()));
            vo.setList(findBySolAndProv(idSol, vo.getIdProveedor()));
            lst.add(vo);
        }
        return lst;
    }
    
    /**
     * Recupero la oferta optima de una solicitud
     * @param idSol int: codigo de solicitud
     * @return OfertaPrecioVO
     * @throws Exception 
     */
    public OfertaPrecioVO findOfertaOptima(int idSol) throws Exception{
        if(idSol == 0) return null;
        
        //RECUPERO LOS REPUESTOS QUE ESTAN EN LA SOLICITUD
        List<Respuestoxsolicitud> repList = repSolService.findRepuestosBySolicitud(idSol);
        if(repList == null || repList.isEmpty()) return null;
        
        OfertaPrecioVO vo = new OfertaPrecioVO();
        vo.setIdProveedor(-17);
        
        double total = 0.0;
        
        List<OfertaProveedorVO> ofertaList = new ArrayList();
        OfertaProveedorVO opv = null;
        Ofertaproveedor ofertaLess = null;
        for(Respuestoxsolicitud model : repList){
            if(model.getAplica() != null && model.getAplica().equals("S")){
                ofertaLess = dao.findBySolAndRepOrderMinPrice(idSol, model.getIdrepuesto());
                if(ofertaLess != null){
                    opv = modelToVO(ofertaLess);
                    total += opv.getPrecio();
                    ofertaList.add(opv);
                }
            }
        }
        
        vo.setList(ofertaList);
        vo.setPrecioTotal(total);
        vo.setNumero(-17);
        vo.setIdSol(idSol);
        return vo;
    }
    
    public List<OfertaProveedorVO> findBySolAndRep(int idSol, int idRep) throws Exception{
        if(idSol == 0 || idRep == 0) return null;
        return listModelToVO(dao.findBySolAndRep(idSol, idRep));
    }
    
    public Ofertaproveedor findBySolicitudAndProveedorAndRepuesto(int idSol, int idProv, int idRep) throws Exception{
        if(idSol == 0 || idProv == 0 || idRep == 0) return null;
        return dao.findBySolicitudAndProveedorAndRepuesto(idSol, idProv, idRep);
    }
    
    public List<OfertaProveedorVO> findBySolAndProv(int idSol, int idProv) throws Exception{
        if(idSol == 0 || idProv == 0) return null;
        return listModelToVO(dao.findBySolAndProv(idSol, idProv));
    }
    
    public List<OfertaProveedorVO> findBySolicitud(int idSol) throws Exception{
        if(idSol == 0) return null;
        return listModelToVO(dao.findBySolicitud(idSol));
    }
    
    private OfertaProveedorVO modelToVO(Ofertaproveedor model) throws Exception{
        if(model == null) return null;
        OfertaProveedorVO vo = new OfertaProveedorVO();
        PropertyUtils.copyProperties(vo, model);
        if(vo.getIdproveedor() != null && vo.getIdproveedor() > 0){
            Proveedor prov = provDao.findByKey(vo.getIdproveedor());
            if(prov != null)
                vo.setProveedor(prov.getNombreproveedor());
        }
        if(vo.getIdrepuesto() != null && vo.getIdrepuesto() > 0){
            Repuesto rep = repDao.findByKey(vo.getIdrepuesto());
            if(rep != null)
                vo.setRepuesto(rep.getNombrerepuesto());
        }
        if(vo.getEstado() != null && !vo.getEstado().equals("")){
            switch (vo.getEstado()) {
                case "NO":
                    vo.setEstadoLeyenda("Nuevo Original");
                    break;
                case "NE":
                    vo.setEstadoLeyenda("Nuevo Equivalente");
                    break;
                case "US":
                    vo.setEstadoLeyenda("Usado");
                    break;
                default:
                    break;
            }
        }
        //RECUPERO EL TOTAL DE REPUESTOS ORIGINALES DESDE LA SOLICITUD
        vo.setCantidadSolOriginal(repSolService.findCantidadTotalRepuestosBySolAndRep(vo.getIdsolicitud(), vo.getIdrepuesto()));
        return vo;
    }
    
    private List<OfertaProveedorVO> listModelToVO(List<Ofertaproveedor> list) throws Exception{
        if(list == null || list.isEmpty()) return null;
        List<OfertaProveedorVO> lst = new ArrayList();
        for(Ofertaproveedor model : list)
            lst.add(modelToVO(model));
        return lst;
    }
    
    public void updateGanadorCotOptima(int idSol) throws Exception{
        if(idSol == 0) return;
        //RECUPERO LA OFERTA OPTIMA
        OfertaPrecioVO optima = this.findOfertaOptima(idSol);
        //RECUPERO LOS DIFERENTES PROVEEDORES Y LOS REPUESTOS QUE ESTOS GANAN
        for(OfertaProveedorVO vo : optima.getList())
            dao.updateGanadorBySolAndProvAndRep(idSol, vo.getIdproveedor(), vo.getIdrepuesto());
    }
    
    public void updateGanador(int idSol, int idProv) throws Exception{
        if(idSol == 0 || idProv == 0) return;
        dao.updateGanador(idSol, idProv);
    }
    
    public Proveedor findWinnerBySolicitud(int idSol) throws Exception{
        if(idSol == 0) return null;
        return dao.findWinnerBySolicitud(idSol);
    }
    
    public Integer getIdProveedorWinnerBySolicitudCliente(int idSol) throws Exception{
        return dao.getIdProveedorWinnerBySolicitudCliente(idSol);
    }
}
