package sic.service.impl;

import java.util.List;
import java.util.ResourceBundle;
import javax.persistence.EntityNotFoundException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sic.modelo.ConfiguracionDelSistema;
import sic.modelo.Empresa;
import sic.repository.IConfiguracionDelSistemaRepository;
import sic.repository.IEmpresaRepository;
import sic.service.IEmpresaService;
import sic.service.BusinessServiceException;
import sic.modelo.TipoDeOperacion;
import sic.util.Validator;

@Service
public class EmpresaServiceImpl implements IEmpresaService {

    private final IEmpresaRepository empresaRepository;
    private final IConfiguracionDelSistemaRepository configuracionDelSistemaRepository;    
    private static final Logger LOGGER = Logger.getLogger(EmpresaServiceImpl.class.getPackage().getName());

    @Autowired
    public EmpresaServiceImpl(IEmpresaRepository empresaRepository,
            IConfiguracionDelSistemaRepository configuracionDelSistemaRepository) {

        this.empresaRepository = empresaRepository;
        this.configuracionDelSistemaRepository = configuracionDelSistemaRepository;
    }
    
    @Override
    public Empresa getEmpresaPorId(Long idEmpresa){
        Empresa empresa = empresaRepository.getEmpresaPorId(idEmpresa);
        if (empresa == null) {
            throw new EntityNotFoundException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_empresa_no_existente"));
        }
        return empresa;
    }

    @Override
    public List<Empresa> getEmpresas() {
        return empresaRepository.getEmpresas();
    }

    @Override
    public Empresa getEmpresaPorNombre(String nombre) {
        return empresaRepository.getEmpresaPorNombre(nombre);
    }

    @Override
    public Empresa getEmpresaPorCUIP(long cuip) {
        return empresaRepository.getEmpresaPorCUIP(cuip);
    }

    private void validarOperacion(TipoDeOperacion operacion, Empresa empresa) {
        //Entrada de Datos
        if (!Validator.esEmailValido(empresa.getEmail())) {
            throw new BusinessServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_empresa_email_invalido"));
        }
        //Requeridos
        if (Validator.esVacio(empresa.getNombre())) {
            throw new BusinessServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_empresa_vacio_nombre"));
        }
        if (Validator.esVacio(empresa.getDireccion())) {
            throw new BusinessServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_empresa_vacio_direccion"));
        }
        if (empresa.getCondicionIVA() == null) {
            throw new BusinessServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_empresa_vacio_condicionIVA"));
        }
        if (empresa.getLocalidad() == null) {
            throw new BusinessServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_empresa_vacio_localidad"));
        }
        //Duplicados
        //Nombre
        Empresa empresaDuplicada = this.getEmpresaPorNombre(empresa.getNombre());
        if (operacion.equals(TipoDeOperacion.ALTA) && empresaDuplicada != null) {
            throw new BusinessServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_empresa_duplicado_nombre"));
        }
        if (operacion.equals(TipoDeOperacion.ACTUALIZACION)) {
            if (empresaDuplicada != null && empresaDuplicada.getId_Empresa() != empresa.getId_Empresa()) {
                throw new BusinessServiceException(ResourceBundle.getBundle("Mensajes")
                        .getString("mensaje_empresa_duplicado_nombre"));
            }
        }
        //CUIP
        empresaDuplicada = this.getEmpresaPorCUIP(empresa.getCuip());
        if (operacion.equals(TipoDeOperacion.ALTA) && empresaDuplicada != null && empresa.getCuip() != 0) {
            throw new BusinessServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_empresa_duplicado_cuip"));
        }
        if (operacion.equals(TipoDeOperacion.ACTUALIZACION)) {
            if (empresaDuplicada != null && empresaDuplicada.getId_Empresa() != empresa.getId_Empresa() && empresa.getCuip() != 0) {
                throw new BusinessServiceException(ResourceBundle.getBundle("Mensajes")
                        .getString("mensaje_empresa_duplicado_cuip"));
            }
        }
    }

    private void crearConfiguracionDelSistema(Empresa empresa) {
        ConfiguracionDelSistema cds = new ConfiguracionDelSistema();
        cds.setCantidadMaximaDeRenglonesEnFactura(28);
        cds.setUsarFacturaVentaPreImpresa(false);
        cds.setEmpresa(getEmpresaPorNombre(empresa.getNombre()));
        configuracionDelSistemaRepository.guardar(cds);
    }

    @Override
    @Transactional
    public Empresa guardar(Empresa empresa) {
        validarOperacion(TipoDeOperacion.ALTA, empresa);
        empresa = empresaRepository.guardar(empresa);
        crearConfiguracionDelSistema(empresa);
        LOGGER.warn("La Empresa " + empresa + " se guardó correctamente." );
        return empresa;
    }

    @Override
    @Transactional
    public void actualizar(Empresa empresa) {
        validarOperacion(TipoDeOperacion.ACTUALIZACION, empresa);
        empresaRepository.actualizar(empresa);
    }

    @Override
    @Transactional
    public void eliminar(Long idEmpresa) {
        Empresa empresa = this.getEmpresaPorId(idEmpresa);
        if (empresa == null) {
            throw new EntityNotFoundException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_empresa_no_existente"));
        }
        empresa.setEliminada(true);
        empresaRepository.actualizar(empresa);
    }
}
