package sic.repository;

import java.util.List;
import sic.modelo.Empresa;
import sic.modelo.Medida;

public interface IMedidaRepository {
    
    Medida getMedidaPorId(long id_Medida);

    void actualizar(Medida medida);

    Medida getMedidaPorNombre(String nombreMedida, Empresa empresa);

    List<Medida> getUnidadMedidas(Empresa empresa);

    void guardar(Medida medida);
    
}
