package sic.repository;

import java.util.List;
import sic.modelo.Pais;
import sic.modelo.Provincia;

public interface IProvinciaRepository {

    void actualizar(Provincia provincia);

    Provincia getProvinciaPorNombre(String nombreProvincia, Pais paisRelacionado);

    List<Provincia> getProvinciasDelPais(Pais pais);

    void guardar(Provincia provincia);
    
}