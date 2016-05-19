package sic.service;

import java.util.List;
import sic.modelo.Pais;

public interface IPaisService {

    void actualizar(Pais pais);

    void eliminar(Pais pais);

    Pais getPaisPorNombre(String nombre);

    List<Pais> getPaises();

    void guardar(Pais pais);
    
}