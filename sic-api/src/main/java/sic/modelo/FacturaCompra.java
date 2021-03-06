package sic.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "facturacompra")
@NamedQueries({
    @NamedQuery(name = "FacturaCompra.buscarPorTipoSerieNum",
            query = "SELECT f FROM FacturaCompra f "
                    + "WHERE f.tipoFactura= :tipo AND f.numSerie= :serie AND f.numFactura= :num")
})
@Data
@EqualsAndHashCode(callSuper = true)
public class FacturaCompra extends Factura implements Serializable {

    @ManyToOne
    @JoinColumn(name = "id_Proveedor", referencedColumnName = "id_Proveedor")
    private Proveedor proveedor;
    
    public FacturaCompra() {}

    public FacturaCompra(long id_Factura, Date fecha, char tipoFactura, long numSerie,
            long numFactura, Date fechaVencimiento, Pedido pedido, Transportista transportista, 
            List<RenglonFactura> renglones, List<Pago> pagos, double subTotal,
            double recargo_porcentaje, double recargo_neto, double descuento_porcentaje,
            double descuento_neto, double subTotal_neto, double iva_105_neto, double iva_21_neto,
            double impuestoInterno_neto, double total, String observaciones, boolean pagada, Empresa empresa, boolean eliminada, Proveedor proveedor) {
        super(id_Factura, fecha, tipoFactura, numSerie, numFactura, fechaVencimiento, 
                pedido, transportista, renglones, pagos, subTotal, recargo_porcentaje,
                recargo_neto, descuento_porcentaje, descuento_neto, subTotal_neto, iva_105_neto,
                iva_21_neto, impuestoInterno_neto, total, observaciones, pagada, empresa, eliminada);
        this.proveedor = proveedor;
    } 
    
}
