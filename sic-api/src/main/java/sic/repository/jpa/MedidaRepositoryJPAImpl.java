package sic.repository.jpa;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import sic.modelo.Empresa;
import sic.modelo.Medida;
import sic.repository.IMedidaRepository;

@Repository
public class MedidaRepositoryJPAImpl implements IMedidaRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Medida> getUnidadMedidas(Empresa empresa) {
        TypedQuery<Medida> typedQuery = em.createNamedQuery("Medida.buscarTodas", Medida.class);
        typedQuery.setParameter("empresa", empresa);
        List<Medida> medidas = typedQuery.getResultList();
        return medidas;
    }

    @Override
    public Medida getMedidaPorId(Long id_Medida) {
        TypedQuery<Medida> typedQuery = em.createNamedQuery("Medida.buscarPorId", Medida.class);
        typedQuery.setParameter("id", id_Medida);
        List<Medida> medidas = typedQuery.getResultList();
        if (medidas.isEmpty()) {
            return null;
        } else {
            return medidas.get(0);
        }
    }
    
    @Override
    public Medida getMedidaPorNombre(String nombreMedida, Empresa empresa) {
        TypedQuery<Medida> typedQuery = em.createNamedQuery("Medida.buscarPorNombre", Medida.class);
        typedQuery.setParameter("nombre", nombreMedida);
        typedQuery.setParameter("empresa", empresa);
        List<Medida> medidas = typedQuery.getResultList();
        if (medidas.isEmpty()) {
            return null;
        } else {
            return medidas.get(0);
        }
    }

    @Override
    public Medida guardar(Medida medida) {
        medida = em.merge(medida);
        em.persist(medida);
        return medida;
    }

    @Override
    public void actualizar(Medida medida) {
        em.merge(medida);
    }
}
