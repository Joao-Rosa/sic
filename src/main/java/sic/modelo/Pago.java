package sic.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pago")
@NamedQueries({
    @NamedQuery(name = "Pago.buscarPorId",
            query = "SELECT p FROM Pago p "
                    + "WHERE p.eliminado = false AND p.id_Pago= :id"),
    @NamedQuery(name = "Pago.buscarPagosEntreFechasYFormaDePago",
            query = "SELECT p FROM Pago p "
            + "WHERE p.empresa.id_Empresa = :id_Empresa "
            + "AND p.formaDePago.id_FormaDePago = :id_FormaDePago "
            + "AND p.eliminado = false AND p.fecha BETWEEN :desde AND :hasta"),
    @NamedQuery(name = "Pago.buscarPorFactura",
            query = "SELECT p FROM Pago p "
            + "WHERE p.factura = :factura AND p.eliminado = false "
            + "ORDER BY p.fecha ASC")
})
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Pago implements Serializable {

    @Id
    @GeneratedValue
    private long id_Pago;

    @ManyToOne
    @JoinColumn(name = "id_FormaDePago", referencedColumnName = "id_FormaDePago")
    private FormaDePago formaDePago;

    @ManyToOne
    @JoinColumn(name = "id_Factura", referencedColumnName = "id_Factura")
    private Factura factura;

    @Column(nullable = false)
    private double monto;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    @Column(nullable = false)
    private String nota;

    @ManyToOne
    @JoinColumn(name = "id_Empresa", referencedColumnName = "id_Empresa")
    private Empresa empresa;

    private boolean eliminado;

}
