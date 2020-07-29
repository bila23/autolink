package com.terzus.autolink.controller.admin;

import com.bila.framework.commons.FacesHelper;
import com.terzus.autolink.model.Taller;
import com.terzus.autolink.model.Usuario;
import com.terzus.autolink.service.TallerService;
import com.terzus.autolink.service.UsuarioService;
import com.terzus.autolink.vo.TallerVO;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author will
 */
@Named(value = "tallerCrudController")
@ViewScoped
@Slf4j
public class TallerCrudController implements Serializable{

    @Inject private TallerService service;
    @Inject private UsuarioService userService;
    @Getter @Setter private TallerVO taller;
    @Getter @Setter private Taller tallerDelete;
    @Getter @Setter private Taller tallerUpdate;
    @Getter @Setter private Taller tallerView;
    @Getter @Setter private List<Taller> list;
    @Getter @Setter private List<Taller> filteredList;
    @Getter private List<Usuario> usuarioList;
    
    public TallerCrudController(){
        taller = new TallerVO();
    }
    
    @PostConstruct
    public void init(){
        try{
            usuarioList = userService.findUserTaller();
            find();
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.error("Ha ocurrido un error al tratar de cargar la informacion de los talleres");
        }
    }
    
    public void find(){
        try{
            list = service.findAllWithNamedQuery();
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.error("Ha ocurrido un error al tratar de mostrar la informacion de los talleres");
        }
    }
    
    public void save(){
        try{
            taller.setUsuariocrea(FacesHelper.getUserLogin());
            service.save(taller);
            taller = new TallerVO();
            FacesHelper.success("Se ha guardado correctamente el taller");
        }catch(Exception e){
            log.error(e.getMessage(), e);
            FacesHelper.error("Ha ocurrido un error al tratar de guardar el taller");
        }
    }
    
    public void delete(){
        if(tallerDelete != null){
            try{
                tallerDelete.setEstado("I");
                service.update(tallerDelete);
                FacesHelper.success("Se ha inactivado correctamente el taller");
                find();
            }catch(Exception e){
                log.error(e.getMessage(), e);
                FacesHelper.error("Ha ocurrido un error al inactivar el taller");
            }
        }
    }
    
    public void update(){
        if(tallerUpdate != null){
            try{
                service.update(tallerUpdate);
                FacesHelper.success("Se ha actualizado correctamente el taller");
                find();
            }catch(Exception e){
                log.error(e.getMessage(), e);
                FacesHelper.error("Ha ocurrido un error al actualizar el taller");                
            }
        }
    }
    
}
