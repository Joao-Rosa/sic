package sic.service.impl;

import java.util.List;
import java.util.ResourceBundle;
import javax.persistence.EntityNotFoundException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sic.modelo.CondicionIVA;
import sic.repository.ICondicionIVARepository;
import sic.service.ICondicionIVAService;
import sic.service.BusinessServiceException;
import sic.modelo.TipoDeOperacion;
import sic.util.Validator;

@Service
public class CondicionDeIVAServiceImpl implements ICondicionIVAService {

    private final ICondicionIVARepository condicionIVARepository;    
    private static final Logger LOGGER = Logger.getLogger(CondicionDeIVAServiceImpl.class.getPackage().getName());

    @Autowired
    public CondicionDeIVAServiceImpl(ICondicionIVARepository condicionIVARepository) {        
        this.condicionIVARepository = condicionIVARepository;        
    }
    
    @Override
    public CondicionIVA getCondicionIVAPorId(long idCondicionIVA) {        
        return condicionIVARepository.getCondicionIVAPorId(idCondicionIVA);
    }

    @Override
    public List<CondicionIVA> getCondicionesIVA() {        
        return condicionIVARepository.getCondicionesIVA();        
    }

    @Override
    @Transactional
    public void actualizar(CondicionIVA condicionIVA) {
        this.validarOperacion(TipoDeOperacion.ACTUALIZACION, condicionIVA);        
        condicionIVARepository.actualizar(condicionIVA);       
    }

    @Override
    @Transactional
    public CondicionIVA guardar(CondicionIVA condicionIVA) {
        this.validarOperacion(TipoDeOperacion.ALTA, condicionIVA);        
        condicionIVA = condicionIVARepository.guardar(condicionIVA);       
        LOGGER.warn("La Condicion IVA " + condicionIVA + " se guardó correctamente." );
        return condicionIVA;
    }

    @Override
    @Transactional
    public void eliminar(Long idCondicionIVA) {
        CondicionIVA condicionIVA = this.getCondicionIVAPorId(idCondicionIVA);
        if (condicionIVA == null) {
            throw new EntityNotFoundException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_CondicionIVA_no_existente"));
        }
        condicionIVA.setEliminada(true);        
        condicionIVARepository.actualizar(condicionIVA);
    }

    @Override
    public CondicionIVA getCondicionIVAPorNombre(String nombre) {        
        return condicionIVARepository.getCondicionIVAPorNombre(nombre);        
    }

    @Override
    public void validarOperacion(TipoDeOperacion operacion, CondicionIVA condicionIVA) {
        //Requeridos
        if (Validator.esVacio(condicionIVA.getNombre())) {
            throw new BusinessServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_condicionIVA_nombre_requerido"));
        }
        //Duplicados
        CondicionIVA condicionIVADuplicada = this.getCondicionIVAPorNombre(condicionIVA.getNombre());
        if (operacion.equals(TipoDeOperacion.ALTA) && condicionIVADuplicada != null) {
            throw new BusinessServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_condicionIVA_nombre_duplicado"));
        }
        if (operacion.equals(TipoDeOperacion.ACTUALIZACION)) {
            if (condicionIVADuplicada != null && condicionIVADuplicada.getId_CondicionIVA() != condicionIVA.getId_CondicionIVA()) {
                throw new BusinessServiceException(ResourceBundle.getBundle("Mensajes")
                        .getString("mensaje_condicionIVA_nombre_duplicado"));
            }
        }
    }
}
