package sic.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import sic.modelo.Localidad;
import sic.service.ILocalidadService;
import sic.service.IProvinciaService;

@RestController
@RequestMapping("/api/v1")
public class LocalidadController {
    
    private final ILocalidadService localidadService;
    private final IProvinciaService provinciaService;
    
    @Autowired
    public LocalidadController(ILocalidadService localidadService, IProvinciaService provinciaService) {    
        this.localidadService = localidadService;
        this.provinciaService = provinciaService;
    }
    
    @GetMapping("/localidades/{idLocalidad}")
    @ResponseStatus(HttpStatus.OK)
    public Localidad getLocalidadPorId(@PathVariable("idLocalidad") long idLocalidad) {
        return localidadService.getLocalidadPorId(idLocalidad);
    }
    
    @PutMapping("/localidades")
    @ResponseStatus(HttpStatus.OK)
    public Localidad actualizar(@RequestBody Localidad localidad) { 
        if(localidadService.getLocalidadPorId(localidad.getId_Localidad()) != null) {
            localidadService.actualizar(localidad);
        }
        return localidadService.getLocalidadPorId(localidad.getId_Localidad());
    }
    
    @DeleteMapping("/localidades/{idLocalidad}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable("idLocalidad") long idLocalidad) {
        localidadService.eliminar(idLocalidad);
    }
    
    @PostMapping("/localidades")
    @ResponseStatus(HttpStatus.OK)
    public Localidad guardar(@RequestBody Localidad localidad) {
        localidadService.guardar(localidad);
        return localidadService.getLocalidadPorId(localidad.getId_Localidad());
    }
    
    @GetMapping("/localidades/provincias/{idProvincia}")
    @ResponseStatus(HttpStatus.OK)
    List<Localidad> getLocalidadesDeLaProvincia(@PathVariable("idProvincia") long idProvincia) {
        return localidadService.getLocalidadesDeLaProvincia(provinciaService.getProvinciaPorId(idProvincia));
    }
}
