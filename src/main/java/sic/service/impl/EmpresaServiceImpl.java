package sic.service.impl;

import sic.modelo.EmpresaActiva;
import java.util.List;
import java.util.ResourceBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sic.modelo.ConfiguracionDelSistema;
import sic.modelo.Empresa;
import sic.repository.IConfiguracionDelSistemaRepository;
import sic.repository.IEmpresaRepository;
import sic.service.IEmpresaService;
import sic.service.ServiceException;
import sic.service.TipoDeOperacion;
import sic.util.Validator;

@Service
public class EmpresaServiceImpl implements IEmpresaService {

    private final IEmpresaRepository empresaRepository;
    private final IConfiguracionDelSistemaRepository configuracionDelSistemaRepository;

    @Autowired
    public EmpresaServiceImpl(IEmpresaRepository empresaRepository,
            IConfiguracionDelSistemaRepository configuracionDelSistemaRepository) {

        this.empresaRepository = empresaRepository;
        this.configuracionDelSistemaRepository = configuracionDelSistemaRepository;
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

    @Override
    public EmpresaActiva getEmpresaActiva() {
        return EmpresaActiva.getInstance();
    }

    @Override
    public void setEmpresaActiva(Empresa empresa) {
        EmpresaActiva empresaActiva = EmpresaActiva.getInstance();
        empresaActiva.setEmpresa(empresa);
    }

    private void validarOperacion(TipoDeOperacion operacion, Empresa empresa) {
        //Entrada de Datos
        if (!Validator.esEmailValido(empresa.getEmail())) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_empresa_email_invalido"));
        }
        //Requeridos
        if (Validator.esVacio(empresa.getNombre())) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_empresa_vacio_nombre"));
        }
        if (Validator.esVacio(empresa.getDireccion())) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_empresa_vacio_direccion"));
        }
        if (empresa.getCondicionIVA() == null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_empresa_vacio_condicionIVA"));
        }
        if (empresa.getLocalidad() == null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_empresa_vacio_localidad"));
        }
        //Duplicados
        //Nombre
        Empresa empresaDuplicada = this.getEmpresaPorNombre(empresa.getNombre());
        if (operacion.equals(TipoDeOperacion.ALTA) && empresaDuplicada != null) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_empresa_duplicado_nombre"));
        }
        if (operacion.equals(TipoDeOperacion.ACTUALIZACION)) {
            if (empresaDuplicada != null && empresaDuplicada.getId_Empresa() != empresa.getId_Empresa()) {
                throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                        .getString("mensaje_empresa_duplicado_nombre"));
            }
        }
        //CUIP
        empresaDuplicada = this.getEmpresaPorCUIP(empresa.getCuip());
        if (operacion.equals(TipoDeOperacion.ALTA) && empresaDuplicada != null && empresa.getCuip() != 0) {
            throw new ServiceException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_empresa_duplicado_cuip"));
        }
        if (operacion.equals(TipoDeOperacion.ACTUALIZACION)) {
            if (empresaDuplicada != null && empresaDuplicada.getId_Empresa() != empresa.getId_Empresa() && empresa.getCuip() != 0) {
                throw new ServiceException(ResourceBundle.getBundle("Mensajes")
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
    public void guardar(Empresa empresa) {
        validarOperacion(TipoDeOperacion.ALTA, empresa);
        empresaRepository.guardar(empresa);
        crearConfiguracionDelSistema(empresa);
    }

    @Override
    @Transactional
    public void actualizar(Empresa empresa) {
        validarOperacion(TipoDeOperacion.ACTUALIZACION, empresa);
        empresaRepository.actualizar(empresa);
    }

    @Override
    @Transactional
    public void eliminar(Empresa empresa) {
        empresa.setEliminada(true);
        empresaRepository.actualizar(empresa);
    }
}
