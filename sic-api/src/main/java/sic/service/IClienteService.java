package sic.service;

import sic.modelo.TipoDeOperacion;
import java.util.List;
import sic.modelo.BusquedaClienteCriteria;
import sic.modelo.Cliente;
import sic.modelo.Empresa;

public interface IClienteService {

    void actualizar(Cliente cliente);
        
    List<Cliente> buscarClientes(BusquedaClienteCriteria criteria);

    void eliminar(Long idCliente);

    Cliente getClientePorId(Long id_Cliente);

    Cliente getClientePorIdFiscal(String idFiscal, Empresa empresa);

    Cliente getClientePorRazonSocial(String razonSocial, Empresa empresa);

    Cliente getClientePredeterminado(Empresa empresa);

    List<Cliente> getClientes(Empresa empresa);

    List<Cliente> getClientesQueContengaRazonSocialNombreFantasiaIdFiscal(String criteria, Empresa empresa);

    Cliente guardar(Cliente cliente);

    void setClientePredeterminado(Cliente cliente);

    void validarOperacion(TipoDeOperacion operacion, Cliente cliente);

}
