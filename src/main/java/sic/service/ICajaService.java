package sic.service;

import java.util.Date;
import java.util.List;
import sic.modelo.BusquedaCajaCriteria;
import sic.modelo.Caja;

public interface ICajaService {

    void actualizar(Caja caja);

    double calcularTotalPorMovimiento(List<Object> movimientos);

    Caja getCajaPorId(long id_Caja, long id_Empresa);

    List<Caja> getCajas(long id_Empresa, Date desde, Date hasta);

    List<Caja> getCajasCriteria(BusquedaCajaCriteria criteria);

    Caja getUltimaCaja(long id_Empresa);

    int getUltimoNumeroDeCaja(long id_Empresa);

    void guardar(Caja caja);

    void validarCaja(Caja caja);

}
