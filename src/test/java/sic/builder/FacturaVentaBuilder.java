package sic.builder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import sic.modelo.Cliente;
import sic.modelo.Empresa;
import sic.modelo.FacturaVenta;
import sic.modelo.Pago;
import sic.modelo.Pedido;
import sic.modelo.RenglonFactura;
import sic.modelo.Transportista;
import sic.modelo.Usuario;

public class FacturaVentaBuilder {
    
    private long id_Factura = 0L;
    private Date fecha = new Date();
    private char tipoFactura = 'A';
    private long numSerie = 0;
    private long numFactura = 1;
    private Date fechaVencimiento = new Date();    
    private Pedido pedido =  null;
    private Transportista transportista = new TransportistaBuilder().build();
    private List<RenglonFactura> renglones = new ArrayList<>();
    private List<Pago> pagos = new ArrayList<>();
    private Cliente cliente = new ClienteBuilder().build();
    private Usuario usuario = new UsuarioBuilder().build();
    private double subTotal = 7885;
    private double recargo_porcentaje = 0.0;
    private double recargo_neto = 0.0;
    private double descuento_porcentaje = 0.0;
    private double descuento_neto = 0.0;
    private double subTotal_neto = 7885;
    private double iva_105_neto = 0.0;
    private double iva_21_neto = 1655.85;
    private double impuestoInterno_neto = 0.0;
    private double total = 7885;
    private String observaciones = "Factura por Default";
    private boolean pagada = false;
    private Empresa empresa = new EmpresaBuilder().build();
    private boolean eliminada = false;
      
    public FacturaVenta build() {
        FacturaVenta factura = new FacturaVenta(cliente, usuario);
        factura.setId_Factura(id_Factura);
        factura.setFecha(fecha);
        factura.setTipoFactura(tipoFactura);
        factura.setNumSerie(numSerie);
        factura.setNumFactura(numFactura);
        factura.setFechaVencimiento(fechaVencimiento);
        factura.setPedido(pedido);
        factura.setTransportista(transportista);
        //Renglones
        if (this.renglones.isEmpty()) {
            RenglonFactura rf1 = new RenglonFacturaBuilder().build();
            RenglonFactura rf2 = new RenglonFacturaBuilder().withCantidad(4)
                    .withId_ProductoItem(890L)
                    .withCodigoItem("mate.0923")
                    .withIVAneto(1092)
                    .withImporte(6292)
                    .build();
            List<RenglonFactura> renglonesFactura = new ArrayList<>();
            renglonesFactura.add(rf1);
            renglonesFactura.add(rf2);
            this.renglones = renglonesFactura;
            factura.setRenglones(renglones);
        } else {
            factura.setRenglones(renglones);
        }
        factura.setCliente(cliente);
        factura.setUsuario(usuario);
        factura.setSubTotal(subTotal);
        factura.setRecargo_porcentaje(recargo_porcentaje);
        factura.setRecargo_neto(recargo_neto);
        factura.setDescuento_porcentaje(descuento_porcentaje);
        factura.setDescuento_neto(descuento_neto);
        factura.setSubTotal_neto(subTotal_neto);
        factura.setIva_105_neto(iva_105_neto);
        factura.setIva_21_neto(iva_21_neto);
        factura.setImpuestoInterno_neto(impuestoInterno_neto);
        factura.setTotal(total);
        factura.setObservaciones(observaciones);
        factura.setPagada(pagada);
        factura.setEmpresa(empresa);
        factura.setEliminada(eliminada);
        return factura;
    }
    
    public FacturaVentaBuilder withId_Factura(long idFactura) {
        this.id_Factura = idFactura;
        return this;
    }
    
    public FacturaVentaBuilder withFecha(Date fecha) {
        this.fecha = fecha;
        return this;
    }
    
    public FacturaVentaBuilder withTipoFactura(char tipoFactura) {
        this.tipoFactura = tipoFactura;
        return this;
    }
    
    public FacturaVentaBuilder withTransportista(Transportista transportista) {
        this.transportista = transportista;
        return this;
    }

    public FacturaVentaBuilder withPedido(Pedido pedido) {
        this.pedido = pedido;
        return this;
    }

    public FacturaVentaBuilder withPagos(List<Pago> pagos) {
        this.pagos = pagos;
        return this;
    }

    public FacturaVentaBuilder withRenglones(List<RenglonFactura> renglones) {
        this.renglones = renglones;
        return this;
    }
    
    public FacturaVentaBuilder withNumSerie(long numeroDeSerie) {
        this.numSerie = numeroDeSerie;
        return this;
    }
    
    public FacturaVentaBuilder withNumFactura(long numeroFactura) {
        this.numFactura = numeroFactura;
        return this;
    }
    
    public FacturaVentaBuilder withFechaVencimiento(Date fechaDeVencimiento) {
        this.fechaVencimiento = fechaDeVencimiento;
        return this;
    }  
    
    public FacturaVentaBuilder withCliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public FacturaVentaBuilder withUsuario(Usuario usuario) {
        this.usuario = usuario;
        return this;
    }

    public FacturaVentaBuilder withSubTotal(double subTotal) {
        this.subTotal = subTotal;
        return this;
    }

    public FacturaVentaBuilder withRecargo_porcentaje(double recargoPorcentaje) {
        this.recargo_porcentaje = recargoPorcentaje;
        return this;
    }

    public FacturaVentaBuilder withRecargo_neto(double recargoNeto) {
        this.recargo_neto = recargoNeto;
        return this;
    }

    public FacturaVentaBuilder withDescuento_porcentaje(double descuentoPorcentaje) {
        this.descuento_porcentaje = descuentoPorcentaje;
        return this;
    }

    public FacturaVentaBuilder withDescuento_neto(double descuentoNeto) {
        this.descuento_neto = descuentoNeto;
        return this;
    }

    public FacturaVentaBuilder withSubTotal_neto(double subTotalNeto) {
        this.subTotal_neto = subTotalNeto;
        return this;
    }

    public FacturaVentaBuilder withIva_105_neto(double iva105Neto) {
        this.iva_105_neto = iva105Neto;
        return this;
    }

    public FacturaVentaBuilder withIva_21_neto(double iva21Neto) {
        this.iva_21_neto = iva21Neto;
        return this;
    }

    public FacturaVentaBuilder withImpuestoInterno_neto(double impuestoInternoNeto) {
        this.impuestoInterno_neto = impuestoInternoNeto;
        return this;
    }

    public FacturaVentaBuilder withTotal(double total) {
        this.total = total;
        return this;
    }

    public FacturaVentaBuilder withObservaciones(String observarciones) {
        this.observaciones = observarciones;
        return this;
    }

    public FacturaVentaBuilder withPagada(boolean pagada) {
        this.pagada = pagada;
        return this;
    }

    public FacturaVentaBuilder withEmpresa(Empresa empresa) {
        this.empresa = empresa;
        return this;
    }

    public FacturaVentaBuilder withEliminada(boolean eliminada) {
        this.eliminada = eliminada;
        return this;
    }
    
}
