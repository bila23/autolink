/*---------------------------------------------------------
* FILE: SolicitudService.java
* PRODUCT: autolink
*----------------------------------------------------------
* IMPORTANT NOTICE
* This program is property of CEL
* Its unauthorized use, as any code alteration without authorization 
* is prohibited
*----------------------------------------------------------
 */
package com.terzus.autolink.service;

import com.bila.framework.commons.GeneralFunction;
import com.bila.framework.dao.Dao;
import com.bila.framework.service.Service;
import com.terzus.autolink.dao.AseguradoraDao;
import com.terzus.autolink.dao.HorariosDao;
import com.terzus.autolink.dao.MarcaDao;
import com.terzus.autolink.dao.ModeloDao;
import com.terzus.autolink.dao.RepuestoDao;
import com.terzus.autolink.dao.SolicitudDao;
import com.terzus.autolink.dao.TallerDao;
import com.terzus.autolink.dao.TipoRepuestoDao;
import com.terzus.autolink.model.Aseguradora;
import com.terzus.autolink.model.Horarios;
import com.terzus.autolink.model.Marca;
import com.terzus.autolink.model.Modelo;
import com.terzus.autolink.model.Proveedor;
import com.terzus.autolink.model.Repuesto;
import com.terzus.autolink.model.Solicitud;
import com.terzus.autolink.model.Taller;
import com.terzus.autolink.model.TipoRepuesto;
import com.terzus.autolink.vo.FotoXSolicitudVO;
import com.terzus.autolink.vo.HorasVO;
import com.terzus.autolink.vo.OfertaProveedorVO;
import com.terzus.autolink.vo.RepuestoSolicitudVO;
import com.terzus.autolink.vo.RepuestosAllSolVO;
import com.terzus.autolink.vo.SolicitudVO;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.apache.commons.beanutils.PropertyUtils;

/**
 * @author CEL
 * <b>Created by: </b>will
 * <b>For: </b>autolink
 * <b>On: </b>May 24, 2020 7:53:20 PM
 * <b>Purpose</b>
 * <p>
 *
 * </p>
 */
@Stateless
public class SolicitudService extends Service<Solicitud, Integer> {

    @Inject
    private SolicitudDao dao;
    @Inject
    private TallerDao tallerDao;
    @Inject
    private AseguradoraDao asegDao;
    @Inject
    private MarcaDao marcaDao;
    @Inject
    private ModeloDao modeloDao;
    @Inject
    private RepuestoDao repDao;
    @Inject
    private OfertaProvService opService;
    @Inject
    private RepuestoSolicitudService rsService;
    @Inject
    private OfertaSolicitudVistaService ofvService;
    @Inject
    private FotoSolService fotoService;
    @Inject
    private TipoRepuestoDao tipoRepDao;
    @Inject
    private HorariosDao horariosDao;

    @Override
    public Dao<Solicitud, Integer> getDao() {
        return dao;
    }
    
    public List<HorasVO> prepareHourList(){
        List<HorasVO> list = new ArrayList();
        HorasVO vo = null;
        for(int i = 1; i<24; i++){
            vo = new HorasVO();
            vo.setHora(i);
            if(i<=9)
                vo.setText("0" + i);
            else
                vo.setText(String.valueOf(i));
            list.add(vo);
        }
        vo = new HorasVO();
        vo.setHora(0);
        vo.setText("00");
        list.add(vo);
        return list;
    }

    public void changeCoaToPea() throws Exception {
        Calendar today = Calendar.getInstance();
        today.setTime(new Date());
        int hours = today.get(Calendar.HOUR_OF_DAY);
        dao.changeCoaToPea(hours);
    }
    
    public void updateDesiertas() throws Exception{
        Calendar today = Calendar.getInstance();
        today.setTime(new Date());
        int hours  = today.get(Calendar.HOUR_OF_DAY);
        dao.updateDesiertas(hours);        
    }
    
    
     public List<Repuesto> findRepuestoByTipo(TipoRepuesto tipo) throws Exception {
        return repDao.findByTipoRep(tipo!=null? tipo.getId():0);
    }

    private SolicitudVO injectFK(Solicitud model, SolicitudVO vo) throws Exception {
        if (model.getIdaseguradora() != null && model.getIdaseguradora() > 0) {
            Aseguradora aseg = asegDao.findByKey(model.getIdaseguradora());
            if (aseg != null) {
                vo.setAseguradora(aseg.getNombreaseguradora());
            }
        }
        if (model.getIdmarca() != null && model.getIdmarca() > 0) {
            Marca marca = marcaDao.findByKey(model.getIdmarca());
            if (marca != null) {
                vo.setMarca(marca.getNombremarca());
            }
        }
        if (model.getIdtaller() != null && model.getIdtaller() > 0) {
            Taller taller = tallerDao.findByKey(model.getIdtaller());
            if (taller != null) {
                vo.setTaller(taller.getNombreTaller());
            }
        }
        if (model.getIdmodelo() != null && model.getIdmodelo() > 0) {
            Modelo modelo = modeloDao.findByKey(model.getIdmodelo());
            vo.setModelo(modelo.getNombremodelo());
        }
        return vo;
    }

    /**
     * Ver la informacion completa de la solicitud
     *
     * @param idSol codigo de la solicitud - int
     * @return objeto de tipo SolicitudVO
     * @throws Exception
     */
    public SolicitudVO seeInfoSol(int idSol) throws Exception {
        if (idSol == 0) {
            return null;
        }
        Solicitud model = dao.findByKey(idSol);
        if (model == null) {
            return null;
        }
        SolicitudVO vo = modelToVO(model, 0);
        //RECUPERO TODOS LOS REPUESTOS
        List<RepuestosAllSolVO> list = rsService.findAllRepAndOfertas(idSol);
        if (list != null && !list.isEmpty()) {
            vo.setRepAllList(list);
        }
        return vo;
    }

    /**
     * Pone la hora de vigencia de la solicitud para que los proveedores puedan
     * ofertar
     *
     * @param idSol codigo de la solicitud - int
     * @param hour objeto de tipo Date con la hora de finalizacion
     * @throws Exception
     */
    public void updateHorasVigencia(int idSol, int hour, Date fecha) throws Exception{
        Solicitud model = dao.findByKey(idSol);
        if(model != null){
            Date ini = new Date();
            Date end = defineEndDate(fecha, hour);
            
            model.setFechainicio(ini);
            model.setFechafin(end);
            model.setHoraFinal(hour);
            dao.update(model);
        }
    }
    
    private Date defineEndDate(Date fecha, int hours) {
        Calendar newDay = Calendar.getInstance();
        newDay.setTime(fecha);
        newDay.set(Calendar.HOUR_OF_DAY, hours);
        newDay.set(Calendar.MINUTE, 0);
        return newDay.getTime();
    }

    public List<Repuesto> findRepuestoActive() throws Exception {
        return repDao.findActive();
    }

    public Solicitud save(Solicitud model, String user, int idTaller) throws Exception {
        model.setEstado("ING");
        model.setFechacreacion(new Date());
        model.setIdtaller(idTaller);
        model.setUsuariocrea(user);
        dao.save(model);
        model.setCodigosolicitud("SOL-" + GeneralFunction.getYear() + "-" + model.getId());
        dao.update(model);
        return model;
    }

    public int saveSol(Solicitud model, String user, String tipo) throws Exception {
        model.setEstado("INI");
        model.setFechacreacion(new Date());
        model.setUsuariocrea(user);
        model.setTipovehiculo(tipo);
        dao.save(model);
        model.setCodigosolicitud("SOL-" + GeneralFunction.getYear() + "-" + model.getId());
        dao.update(model);
        return model.getId();
    }

    public List<Aseguradora> findAsegActive() throws Exception {
        return asegDao.findActive();
    }

    public List<Marca> findMarcaActive() throws Exception {
        return marcaDao.findActive();
    }

    public List<Modelo> findActiveByMarca(Integer idMarca) throws Exception {
        return modeloDao.findActiveByMarca(idMarca);
    }

    private SolicitudVO prepareModel(Solicitud model) throws Exception {
        SolicitudVO vo = new SolicitudVO();
        PropertyUtils.copyProperties(vo, model);
        if (model.getIdaseguradora() != null && model.getIdaseguradora() > 0) {
            Aseguradora aseg = asegDao.findByKey(model.getIdaseguradora());
            if (aseg != null) {
                vo.setAseguradora(aseg.getNombreaseguradora());
            }
        }
        if (model.getIdmarca() != null && model.getIdmarca() > 0) {
            Marca marca = marcaDao.findByKey(model.getIdmarca());
            if (marca != null) {
                vo.setMarca(marca.getNombremarca());
            }
        }
        if (model.getIdtaller() != null && model.getIdtaller() > 0) {
            Taller taller = tallerDao.findByKey(model.getIdtaller());
            if (taller != null) {
                vo.setTaller(taller.getNombreTaller());
            }
        }
        if (model.getIdmodelo() != null && model.getIdmodelo() > 0) {
            Modelo modelo = modeloDao.findByKey(model.getIdmodelo());
            vo.setModelo(modelo.getNombremodelo());
        }
        return vo;
    }

    private SolicitudVO modelToVO(Solicitud model, int codprv) throws Exception {
        if (model == null) {
            return null;
        }
        SolicitudVO vo = prepareModel(model);
        List<RepuestoSolicitudVO> repList = rsService.findAplicaBySolicitud(vo.getId(), codprv);
        vo.setRepAplicaList(repList);

        //TIPO VEHICULO
        if (model.getIdTipoVehiculo() != null) {
            vo.setIdTipoVeh(model.getIdTipoVehiculo().getId());
            vo.setTipovehiculo(model.getIdTipoVehiculo().getNombre());
        }

        //RECUPERO LAS IMAGENES
        List<FotoXSolicitudVO> fotoList = fotoService.findImageBySol(vo.getId());
        if (fotoList != null) {
            vo.setFotoList(fotoList);
        }

        //VERIFICO SI LA SOLICITUD ES DE REPUESTOS PARCIAL O NO
        boolean isParcial = rsService.isSolRepParcial(vo.getId());
        if (isParcial) {
            vo.setParcial("Parcial (Repuestos)");
        } else {
            vo.setParcial("Todos los repuestos");
        }

        //VERIFICO SI LA SOLICITUD TIENE UN GANADOR
        Proveedor prov = opService.findWinnerBySolicitud(vo.getId());
        if (prov != null) {
            vo.setIdProvWinner(prov.getId());
            vo.setProveedorWinner(prov.getNombreproveedor());
        }

        //HORA PENDIENTES
        if (vo.getFechafin() != null) {
            long diffInMillies = vo.getFechafin().getTime() - new Date().getTime();
            long diffHours = diffInMillies / (60 * 60 * 1000);
            long diffMinutes = diffInMillies / (60 * 1000) % 60;
            vo.setPendingHours(diffHours + " : " + diffMinutes);
        }
        return vo;
    }

    private List<SolicitudVO> listModelToVO(List<Solicitud> list, int codprv) throws Exception {
        if (list == null || list.isEmpty()) {
            return null;
        }
        List<SolicitudVO> lst = new ArrayList();
        int i = 0, size = list.size();
        for (i = 0; i < size; i++) {
            lst.add(modelToVO(list.get(i), codprv));
        }
        return lst;
    }

    public List<SolicitudVO> findByEstado(String estado, int codprv) throws Exception {
        if (estado == null || estado.equals("")) {
            return null;
        }
        List<Solicitud> list = dao.findByEstado(estado);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return listModelToVO(list, codprv);
    }

    public List<SolicitudVO> findByEstado(String estado) throws Exception {
        if (estado == null || estado.equals("")) {
            return null;
        }
        List<Solicitud> list = dao.findByEstado(estado);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return listModelToVO(list, 0);
    }

    public void updateEstado(int id, String state) throws Exception {
        if (id <= 0 || state == null || state.equals("") || state.length() != 3) {
            return;
        }
        dao.updateEstado(id, state.toUpperCase());
    }

    public List<SolicitudVO> findIngresadas() throws Exception {
        return findByEstado("ING");
    }

    public List<SolicitudVO> findAnuladas() throws Exception {
        return findByEstado("ANU");
    }

    public List<SolicitudVO> findAnuladasByAdmin() throws Exception {
        return findByEstado("ANA");
    }

    public List<SolicitudVO> findCotAbierta() throws Exception {
        return findByEstado("COA");
    }

    /* METODOS SOLO PARA PROVEEDOR QUE BUSCA SUS OFERTAS*/
    public List<SolicitudVO> findCotAbierta(int codprv) throws Exception {
        return findByEstadoForProveedor("COA", codprv);
    }

    public List<SolicitudVO> findByEstadoForProveedor(String estado, int codprv) throws Exception {
        if (estado == null || estado.equals("")) {
            return null;
        }
        List<Solicitud> list = dao.findByEstado(estado);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return listModelToVOForProveedor(list, codprv);
    }

    private List<SolicitudVO> listModelToVOForProveedor(List<Solicitud> list, int codprv) throws Exception {
        if (list == null || list.isEmpty()) {
            return null;
        }
        List<SolicitudVO> lst = new ArrayList();
        int i = 0, size = list.size();
        for (i = 0; i < size; i++) {
            lst.add(modelToVOForProveedor(list.get(i), codprv));
        }
        return lst;
    }

    public List<RepuestoSolicitudVO> findRepAplicaBySol(int idSolicitud, int idProveedor) throws Exception {
        return rsService.findAplicaBySolicitud(idSolicitud, idProveedor);
    }

    private SolicitudVO modelToVOForProveedor(Solicitud model, int codprv) throws Exception {
        if (model == null) {
            return null;
        }
        SolicitudVO vo = prepareModel(model);
        List<RepuestoSolicitudVO> repList = rsService.findAplicaBySolicitud(vo.getId(), codprv);
        vo.setRepAplicaList(repList);

        //VERIFICO SI LA OFERTA YA FUE ABIERTA
        vo.setViewed(ofvService.isViewed(vo.getId(), codprv));        
        
        //VERIFICO SI LA SOLICITUD POSEE OFERTAS POR EL PROVEEDOR
        List<OfertaProveedorVO> offerList = opService.findBySolAndProv(vo.getId(), codprv);
        if(offerList != null && !offerList.isEmpty())
            vo.setHasOffers(true);
        else
            vo.setHasOffers(false);
                
        //HORA PENDIENTES
        if (vo.getFechafin() != null) {
            long diffInMillies = vo.getFechafin().getTime() - new Date().getTime();
            long diffHours = diffInMillies / (60 * 60 * 1000);
            long diffMinutes = diffInMillies / (60 * 1000) % 60;
            vo.setPendingHours(diffHours + " : " + diffMinutes);
        }
        return vo;
    }

    /* FIN METODOS SOLO PARA PROVEEDOR QUE BUSCA SUS OFERTAS*/
    public List<SolicitudVO> findCerradaDesierta() throws Exception {
        return findByEstado("CPD");
    }

    public List<SolicitudVO> findCerradaAseg() throws Exception {
        return findByEstado("CEA");
    }

    public List<SolicitudVO> findCerradaParcialmente() throws Exception {
        return findByEstado("CPA");
    }

    public List<SolicitudVO> findPendAprobar() throws Exception {
        return findByEstado("PEA");
    }

    public List<SolicitudVO> findGenOrdCompra() throws Exception {
        return findByEstado("GOC");
    }

    public List<SolicitudVO> findDespProvByProveedorWinner(int codprv) throws Exception {
        if (codprv == 0) {
            return null;
        }
        List<Solicitud> list = dao.findDespProvByProveedorWinner(codprv);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return listModelToVO(list, codprv);
    }

    public List<SolicitudVO> findGenOrdCompraByProveedor(int codprv) throws Exception {
        if (codprv == 0) {
            return null;
        }
        List<Solicitud> list = dao.findByProveedorWinner(codprv);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return listModelToVO(list, codprv);
    }

    public List<SolicitudVO> findGenOrdCompra(int codprv) throws Exception {
        return findByEstado("GOC", codprv);
    }

    public SolicitudVO genOrdCompra(int idSol, int codprv) throws Exception {
        Solicitud model = dao.findByKey(idSol);
        if (model == null) {
            return null;
        }
        return modelToVO(model, codprv);
    }

    public SolicitudVO genOrdCompraByProv(int idSol, int codprv) throws Exception {
        Solicitud model = dao.findByKey(idSol);
        if (model == null) {
            return null;
        }
        return getOneOrMultipleGanadores(model, codprv);
    }

    private SolicitudVO getOneOrMultipleGanadores(Solicitud model, int idProv) throws Exception {
        if (model == null) {
            return null;
        }
        SolicitudVO vo = new SolicitudVO();
        PropertyUtils.copyProperties(vo, model);

        if (model.getIdmarca() != null && model.getIdmarca() > 0) {
            Marca marca = marcaDao.findByKey(model.getIdmarca());
            if (marca != null) {
                vo.setMarca(marca.getNombremarca());
            }
        }
        if (model.getIdmodelo() != null && model.getIdmodelo() > 0) {
            Modelo modelo = modeloDao.findByKey(model.getIdmodelo());
            vo.setModelo(modelo.getNombremodelo());
        }
        List<RepuestoSolicitudVO> repList = rsService.findRepuestosWithWinner(vo.getId(), idProv);
        vo.setRepAplicaList(repList);
        return vo;
    }

    public List<SolicitudVO> findDespProveedor() throws Exception {
        return findByEstado("DEP");
    }

    public List<SolicitudVO> findEntSatCliente() throws Exception {
        return findByEstado("ESC");
    }

    //***** CONSULTA DE SOLICITUDES POR TALLER *****//
    public List<SolicitudVO> findByEstadoAndTaller(String estado, int idTaller) throws Exception {
        if (estado == null || estado.equals("") || idTaller == 0) {
            return null;
        }
        List<Solicitud> list = dao.findByEstadoAndTaller(estado, idTaller);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return listModelToVO(list, 0);
    }

    public List<SolicitudVO> findIngresadasByTaller(int idTaller) throws Exception {
        return findByEstadoAndTaller("ING", idTaller);
    }

    public List<SolicitudVO> findAnuladasByTaller(int idTaller) throws Exception {
        return findByEstadoAndTaller("ANU", idTaller);
    }

    public List<SolicitudVO> findDespProveedorByTaller(int idTaller) throws Exception {
        return findByEstadoAndTaller("DEP", idTaller);
    }

    public List<SolicitudVO> findEntSatClienteByTaller(int idTaller) throws Exception {
        return findByEstadoAndTaller("ESC", idTaller);
    }

    public List<SolicitudVO> findCerradaDesiertaByTaller(int idTaller) throws Exception {
        return findByEstadoAndTaller("CPD", idTaller);
    }

    public List<SolicitudVO> findCerradaAsegByTaller(int idTaller) throws Exception {
        return findByEstadoAndTaller("CEA", idTaller);
    }
    //***** CONSULTA DE SOLICITUDES POR TALLER *****//

    //***** CONSULTA DE SOLICITUDES POR ASEGURADORA *****//
    public List<SolicitudVO> findByEstadoAndAseg(String estado, int idAseg) throws Exception {
        if (estado == null || estado.equals("") || idAseg == 0) {
            return null;
        }
        List<Solicitud> list = dao.findByEstadoAndAseg(estado, idAseg);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return listModelToVO(list, 0);
    }

    public List<SolicitudVO> findIngresadasByAseg(int idAseg) throws Exception {
        return findByEstadoAndAseg("ING", idAseg);
    }

    public List<SolicitudVO> findCotAbiertaByAseg(int idAseg) throws Exception {
        return findByEstadoAndAseg("COA", idAseg);
    }

    public List<SolicitudVO> findPendAprobarByAseg(int idAseg) throws Exception {
        return findByEstadoAndAseg("PEA", idAseg);
    }

    public List<SolicitudVO> findCerradaAsegByAseg(int idAseg) throws Exception {
        return findByEstadoAndAseg("CEA", idAseg);
    }

    public List<SolicitudVO> findGenOrdCompraByAseg(int idAseg) throws Exception {
        return findByEstadoAndAseg("GOC", idAseg);
    }
    //***** CONSULTA DE SOLICITUDES POR ASEGURADORA *****//

    //***** INICIO CONSULTA DE SOLICITUDES POR CLIENTE *****//
    public List<SolicitudVO> findByEstadoAndCliente(String estado, String user) throws Exception {
        if (estado == null || estado.equals("") || user == null) {
            return null;
        }
        List<Solicitud> list = dao.findByEstadoAndCliente(estado, user);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return listModelToVO(list, 0);
    }

    public List<Solicitud> findByEstadoAndClienteSol(String estado, String user) throws Exception {
        return dao.findByEstadoAndCliente(estado, user);
    }
    //***** FIN CONSULTA DE SOLICITUDES POR CLIENTE *****//

    //***** INICIO CONSULTA DE SOLICITUDES POR CLIENTE *****//
    public Solicitud findLastByEstadoAndCliente(String estado, String user) throws Exception {
        if (estado == null || estado.equals("") || user == null) {
            return null;
        }
        List<Solicitud> list = dao.findByEstadoAndCliente(estado, user);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.size() > 0 ? list.get(0) : null;
    }
    //***** FIN CONSULTA DE SOLICITUDES POR CLIENTE *****//

    public List<TipoRepuesto> findAllTipoRepuestos() throws Exception {
        return tipoRepDao.getTipoRepuestoActivo();
    }

    public boolean existeOfertaToCliente(int id) throws Exception {
        Integer codprv;
        if (id == 0) {
            return false;
        }
        codprv = opService.getIdProveedorWinnerBySolicitudCliente(id);
        return !GeneralFunction.isNullOrEmptyOrZero(codprv);
    }

    /**
     * Pone la hora de vigencia de la solicitud del cliente para que los
     * proveedores puedan ofertar
     *
     * @param idSol codigo de la solicitud - int
     * @param hour objeto de tipo Date con la hora de finalizacion
     * @throws Exception
     */
    public void updateHorasVigenciaForCliente(int idSol, int hour) throws Exception {
        Solicitud model = dao.findByKey(idSol);
        if (model != null) {

            Date ini = new Date();
            Date end = addDate(ini, hour, Calendar.HOUR_OF_DAY);
            end = getFechaFinForSol(end, hour);

            model.setFechainicio(ini);
            model.setFechafin(end);
            model.setHoraFinal(end.getHours());
            dao.update(model);
        }
    }

    private static Date addDate(Date fecha, int cantidad, int tipo) throws Exception {
        Calendar newDay = Calendar.getInstance();
        newDay.setTime(fecha);
        newDay.add(tipo, cantidad);
        return newDay.getTime();
    }

    private Date getFechaFinForSol(Date fechaFin, int cantidad) throws Exception {
        String letraD = "";
        int nD = -1;
        Horarios horario;
        Calendar DayCurrent = Calendar.getInstance();

        nD = DayCurrent.get(Calendar.DAY_OF_WEEK);

        letraD = getLetrasDia(nD);
        horario = horariosDao.getHorarioByDiaAndJornada(letraD, 1);

        if (horario != null) {
            Calendar dateleave = Calendar.getInstance();

            dateleave.setTime(new Date());
            dateleave.set(Calendar.HOUR_OF_DAY, horario.getHoraFin());
            dateleave.set(Calendar.MINUTE, 0);

            long var = dateleave.getTimeInMillis() - DayCurrent.getTimeInMillis();

            if (var >= 0) { //la solicitud es antes de la hora de salir.
                long currenTime = var - (cantidad * 1000 * 60 * 60);
                if (currenTime >= 0) { // La solicitud esta en tiempo para ser resuelta
                    return fechaFin;
                } else { //Queda tiempo restando para la proximo dia o jornada de trabajo.
                    DayCurrent = getNextDiaLaboral(DayCurrent);
                    DayCurrent.add(Calendar.MILLISECOND, Integer.parseInt(String.valueOf(Math.abs(currenTime))));
                    fechaFin = DayCurrent.getTime();
                    return fechaFin;
                }

            } else { //la solicitud es despues de la hora de salir.
                DayCurrent = getNextDiaLaboral(DayCurrent);
                DayCurrent.add(Calendar.HOUR_OF_DAY, cantidad);
                fechaFin = DayCurrent.getTime();
                return fechaFin;
            }
        } else {
            DayCurrent = getNextDiaLaboral(DayCurrent);
            DayCurrent.add(Calendar.HOUR_OF_DAY, cantidad);
            fechaFin = DayCurrent.getTime();
            return fechaFin;
        }
    }

    private Calendar getNextDiaLaboral(Calendar dayCurrent) throws Exception {
        int nd, count=0;
        String letraD;
        Horarios horarioCurrent = null;
        do {
            count++;
            dayCurrent.add(Calendar.DAY_OF_MONTH, 1);
            nd = dayCurrent.get(Calendar.DAY_OF_WEEK);
            letraD = getLetrasDia(nd);
            horarioCurrent = horariosDao.getHorarioByDiaAndJornada(letraD, 1);
        } while (horarioCurrent == null || count>7);

        dayCurrent.set(Calendar.HOUR_OF_DAY, horarioCurrent.getHoraInicio());
        /*  if(horarioCurrent.getHoraInicio()<=12){
          dayCurrent.set(Calendar.HOUR , horarioCurrent.getHoraInicio());
          }else{
              dayCurrent.set(Calendar.HOUR , horarioCurrent.getHoraInicio()-12);
          }*/
        dayCurrent.set(Calendar.MINUTE, 0);

        return dayCurrent;
    }

    private String getLetrasDia(int nD) throws Exception {
        String letraD = "";
        switch (nD) {
            case 1:
                letraD = "DO";
                break;
            case 2:
                letraD = "LU";
                break;
            case 3:
                letraD = "MA";
                break;
            case 4:
                letraD = "MI";
                break;
            case 5:
                letraD = "JU";
                break;
            case 6:
                letraD = "VI";
                break;
            case 7:
                letraD = "SA";
                break;
        }
        return letraD;
    }

}
