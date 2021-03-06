package sic.repository.jpa;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import sic.modelo.Localidad;
import sic.modelo.Provincia;
import sic.repository.ILocalidadRepository;

@Repository
public class LocalidadRepositoryJPAImpl implements ILocalidadRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Localidad> getLocalidadesDeLaProvincia(Provincia provincia) {
        TypedQuery<Localidad> typedQuery = em.createNamedQuery("Localidad.buscarLocalidadesDeLaProvincia", Localidad.class);
        typedQuery.setParameter("provincia", provincia);
        List<Localidad> localidades = typedQuery.getResultList();
        return localidades;
    }

    @Override
    public Localidad getLocalidadPorId(Long id_Localidad) {
        TypedQuery<Localidad> typedQuery = em.createNamedQuery("Localidad.buscarPorId", Localidad.class);
        typedQuery.setParameter("id", id_Localidad);
        List<Localidad> localidades = typedQuery.getResultList();
        if (localidades.isEmpty()) {
            return null;
        } else {
            return localidades.get(0);
        }
    }
    
    @Override
    public Localidad getLocalidadPorNombre(String nombre, Provincia provincia) {
        TypedQuery<Localidad> typedQuery = em.createNamedQuery("Localidad.buscarPorNombre", Localidad.class);
        typedQuery.setParameter("nombre", nombre);
        typedQuery.setParameter("provincia", provincia);
        List<Localidad> localidades = typedQuery.getResultList();
        if (localidades.isEmpty()) {
            return null;
        } else {
            return localidades.get(0);
        }
    }

    @Override
    public void actualizar(Localidad localidad) {
        em.merge(localidad);
    }

    @Override
    public Localidad guardar(Localidad localidad) {
        localidad = em.merge(localidad);
        em.persist(localidad);
        return localidad;
    }
}
