package sic.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import sic.modelo.Cliente;
import sic.modelo.CondicionIVA;
import sic.modelo.Empresa;
import sic.modelo.FacturaVenta;
import sic.modelo.Medida;
import sic.modelo.Producto;
import sic.modelo.Proveedor;
import sic.modelo.RenglonFactura;
import sic.repository.IFacturaRepository;
import sic.service.IFacturaService;
import sic.service.IProductoService;
import sic.service.Movimiento;

public class FacturaServiceImplTest {

    private IFacturaService facturaService;
    private IProductoService productoService;

    @Before
    public void before() {
        productoService = Mockito.mock(ProductoServiceImpl.class);
        IFacturaRepository facturaRepository = Mockito.mock(IFacturaRepository.class);
        when(facturaRepository.getMayorNumFacturaSegunTipo("", (long) 1)).thenReturn((long) 1);
        facturaService = new FacturaServiceImpl(facturaRepository, productoService, null, null, null);
    }

    @Test
    public void shouldGetTipoFacturaCompraWhenEmpresaYProveedorDiscriminanIVA() {
        Empresa empresa = Mockito.mock(Empresa.class);
        CondicionIVA condicionIVAqueDiscrimina = Mockito.mock(CondicionIVA.class);
        when(condicionIVAqueDiscrimina.isDiscriminaIVA()).thenReturn(Boolean.TRUE);
        when(empresa.getCondicionIVA()).thenReturn(condicionIVAqueDiscrimina);
        empresa.setCondicionIVA(condicionIVAqueDiscrimina);
        Proveedor proveedor = Mockito.mock(Proveedor.class);
        when(proveedor.getCondicionIVA()).thenReturn(condicionIVAqueDiscrimina);
        String[] expResult = new String[3];
        expResult[0] = "Factura A";
        expResult[1] = "Factura B";
        expResult[2] = "Factura X";
        String[] result = facturaService.getTipoFacturaCompra(empresa, proveedor);
        assertArrayEquals(expResult, result);
    }

    @Test
    public void shouldGetTipoFacturaCompraWhenEmpresaDiscriminaIVAYProveedorNO() {
        Empresa empresa = Mockito.mock(Empresa.class);
        Proveedor proveedor = Mockito.mock(Proveedor.class);
        CondicionIVA condicionIVAqueDiscrimina = Mockito.mock(CondicionIVA.class);
        when(condicionIVAqueDiscrimina.isDiscriminaIVA()).thenReturn(Boolean.TRUE);
        CondicionIVA condicionIVAqueNoDiscrimina = Mockito.mock(CondicionIVA.class);
        when(condicionIVAqueNoDiscrimina.isDiscriminaIVA()).thenReturn(Boolean.FALSE);
        when(empresa.getCondicionIVA()).thenReturn(condicionIVAqueDiscrimina);
        when(proveedor.getCondicionIVA()).thenReturn(condicionIVAqueNoDiscrimina);
        String[] expResult = new String[2];
        expResult[0] = "Factura C";
        expResult[1] = "Factura X";
        String[] result = facturaService.getTipoFacturaCompra(empresa, proveedor);
        assertArrayEquals(expResult, result);
    }

    @Test
    public void shouldGetTipoFacturaCompraWhenEmpresaNoDiscriminaIVAYProveedorSI() {
        Empresa empresa = Mockito.mock(Empresa.class);
        Proveedor proveedor = Mockito.mock(Proveedor.class);
        CondicionIVA condicionIVAqueDiscrimina = Mockito.mock(CondicionIVA.class);
        when(condicionIVAqueDiscrimina.isDiscriminaIVA()).thenReturn(Boolean.TRUE);
        CondicionIVA condicionIVAqueNoDiscrimina = Mockito.mock(CondicionIVA.class);
        when(condicionIVAqueNoDiscrimina.isDiscriminaIVA()).thenReturn(Boolean.FALSE);
        when(empresa.getCondicionIVA()).thenReturn(condicionIVAqueNoDiscrimina);
        when(proveedor.getCondicionIVA()).thenReturn(condicionIVAqueDiscrimina);
        String[] expResult = new String[2];
        expResult[0] = "Factura B";
        expResult[1] = "Factura X";
        empresa.getCondicionIVA().isDiscriminaIVA();
        String[] result = facturaService.getTipoFacturaCompra(empresa, proveedor);
        assertArrayEquals(expResult, result);
    }

    @Test
    public void shouldGetTipoFacturaCompraWhenEmpresaNoDiscriminaYProveedorTampoco() {
        Empresa empresa = Mockito.mock(Empresa.class);
        Proveedor proveedor = Mockito.mock(Proveedor.class);
        CondicionIVA condicionIVAqueNoDiscrimina = Mockito.mock(CondicionIVA.class);
        when(condicionIVAqueNoDiscrimina.isDiscriminaIVA()).thenReturn(Boolean.FALSE);
        when(empresa.getCondicionIVA()).thenReturn(condicionIVAqueNoDiscrimina);
        when(proveedor.getCondicionIVA()).thenReturn(condicionIVAqueNoDiscrimina);
        String[] expResult = new String[2];
        expResult[0] = "Factura C";
        expResult[1] = "Factura X";
        empresa.getCondicionIVA().isDiscriminaIVA();
        String[] result = facturaService.getTipoFacturaCompra(empresa, proveedor);
        assertArrayEquals(expResult, result);
    }

    @Test
    public void shouldGetTipoFacturaVentaWhenEmpresaDiscriminaYClienteTambien() {
        Empresa empresa = Mockito.mock(Empresa.class);
        Cliente cliente = Mockito.mock(Cliente.class);
        CondicionIVA condicionIVAqueDiscrimina = Mockito.mock(CondicionIVA.class);
        when(condicionIVAqueDiscrimina.isDiscriminaIVA()).thenReturn(Boolean.TRUE);
        when(empresa.getCondicionIVA()).thenReturn(condicionIVAqueDiscrimina);
        when(cliente.getCondicionIVA()).thenReturn(condicionIVAqueDiscrimina);
        String[] expResult = {"Factura A", "Factura B", "Factura X", "Factura Y", "Pedido"};
        String[] result = facturaService.getTipoFacturaVenta(empresa, cliente);
        assertArrayEquals(expResult, result);
    }

    @Test
    public void shouldGetTipoFacturaVentaWhenEmpresaDiscriminaYClienteNo() {
        Empresa empresa = Mockito.mock(Empresa.class);
        Cliente cliente = Mockito.mock(Cliente.class);
        CondicionIVA condicionIVAqueDiscrimina = Mockito.mock(CondicionIVA.class);
        CondicionIVA condicionIVAqueNoDiscrimina = Mockito.mock(CondicionIVA.class);
        when(condicionIVAqueDiscrimina.isDiscriminaIVA()).thenReturn(Boolean.TRUE);
        when(condicionIVAqueNoDiscrimina.isDiscriminaIVA()).thenReturn(Boolean.FALSE);
        when(empresa.getCondicionIVA()).thenReturn(condicionIVAqueDiscrimina);
        when(cliente.getCondicionIVA()).thenReturn(condicionIVAqueNoDiscrimina);
        String[] expResult = {"Factura B", "Factura X", "Factura Y", "Pedido"};
        String[] result = facturaService.getTipoFacturaVenta(empresa, cliente);
        assertArrayEquals(expResult, result);
    }

    @Test
    public void shouldGetTipoFacturaVentaWhenEmpresaNoDiscriminaYClienteSi() {
        Empresa empresa = Mockito.mock(Empresa.class);
        Cliente cliente = Mockito.mock(Cliente.class);
        CondicionIVA condicionIVAqueDiscrimina = Mockito.mock(CondicionIVA.class);
        CondicionIVA condicionIVAqueNoDiscrimina = Mockito.mock(CondicionIVA.class);
        when(condicionIVAqueDiscrimina.isDiscriminaIVA()).thenReturn(Boolean.TRUE);
        when(condicionIVAqueNoDiscrimina.isDiscriminaIVA()).thenReturn(Boolean.FALSE);
        when(empresa.getCondicionIVA()).thenReturn(condicionIVAqueNoDiscrimina);
        when(cliente.getCondicionIVA()).thenReturn(condicionIVAqueDiscrimina);
        String[] expResult = {"Factura C", "Factura X", "Factura Y", "Pedido"};
        String[] result = facturaService.getTipoFacturaVenta(empresa, cliente);
        assertArrayEquals(expResult, result);
    }

    @Test
    public void shouldGetTipoFacturaVentaWhenEmpresaNoDiscriminaIVAYClienteNO() {
        Empresa empresa = Mockito.mock(Empresa.class);
        Cliente cliente = Mockito.mock(Cliente.class);
        CondicionIVA condicionIVAqueNoDiscrimina = Mockito.mock(CondicionIVA.class);
        when(condicionIVAqueNoDiscrimina.isDiscriminaIVA()).thenReturn(Boolean.TRUE);
        when(condicionIVAqueNoDiscrimina.isDiscriminaIVA()).thenReturn(Boolean.FALSE);
        when(empresa.getCondicionIVA()).thenReturn(condicionIVAqueNoDiscrimina);
        when(cliente.getCondicionIVA()).thenReturn(condicionIVAqueNoDiscrimina);
        String[] expResult = {"Factura C", "Factura X", "Factura Y", "Pedido"};
        String[] result = facturaService.getTipoFacturaVenta(empresa, cliente);
        assertArrayEquals(expResult, result);
    }

    @Test
    public void shouldGetTiposFacturaWhenEmpresaDiscriminaIVA() {
        Empresa empresa = Mockito.mock(Empresa.class);
        CondicionIVA condicionIVAqueDiscrimina = Mockito.mock(CondicionIVA.class);
        when(condicionIVAqueDiscrimina.isDiscriminaIVA()).thenReturn(Boolean.TRUE);
        when(empresa.getCondicionIVA()).thenReturn(condicionIVAqueDiscrimina);
        char[] expResult = {'A', 'B', 'X', 'Y'};
        char[] result = facturaService.getTiposFacturaSegunEmpresa(empresa);
        assertArrayEquals(expResult, result);
    }

    @Test
    public void shouldGetTiposFacturaWhenEmpresaNoDiscriminaIVA() {
        Empresa empresa = Mockito.mock(Empresa.class);
        CondicionIVA condicionIVAqueNoDiscrimina = Mockito.mock(CondicionIVA.class);
        when(condicionIVAqueNoDiscrimina.isDiscriminaIVA()).thenReturn(Boolean.FALSE);
        when(empresa.getCondicionIVA()).thenReturn(condicionIVAqueNoDiscrimina);
        char[] expResult = {'C', 'X', 'Y'};
        char[] result = facturaService.getTiposFacturaSegunEmpresa(empresa);
        assertArrayEquals(expResult, result);
    }

    @Test
    public void shouldDividirFactura() {
        FacturaVenta factura = Mockito.mock(FacturaVenta.class);
        RenglonFactura renglon1 = Mockito.mock(RenglonFactura.class);
        RenglonFactura renglon2 = Mockito.mock(RenglonFactura.class);
        Producto producto = Mockito.mock(Producto.class);
        when(producto.getId_Producto()).thenReturn((long) 1);
        when(producto.getCodigo()).thenReturn("1");
        when(producto.getDescripcion()).thenReturn("producto test");
        Medida medida = Mockito.mock(Medida.class);
        when(producto.getMedida()).thenReturn(medida);
        when(producto.getPrecioVentaPublico()).thenReturn(1.0);
        when(producto.getIva_porcentaje()).thenReturn(21.00);
        when(producto.getImpuestoInterno_porcentaje()).thenReturn(0.0);
        when(producto.getPrecioLista()).thenReturn(1.0);
        when(productoService.getProductoPorId((long) 1)).thenReturn(producto);
        when(renglon1.getId_ProductoItem()).thenReturn((long) 1);
        when(renglon2.getId_ProductoItem()).thenReturn((long) 1);
        when(renglon1.getCantidad()).thenReturn(4.00);
        when(renglon2.getCantidad()).thenReturn(7.00);
        List<RenglonFactura> renglones = new ArrayList<>();
        renglones.add(renglon1);
        renglones.add(renglon2);
        when(factura.getRenglones()).thenReturn(renglones);
        when(factura.getTipoFactura()).thenReturn('A');
        int[] indices = {0, 1};
        int cantidadDeFacturasEsperadas = 2;
        List<FacturaVenta> result = facturaService.dividirFactura(factura, indices);
        double cantidadRenglon1PrimeraFactura = result.get(0).getRenglones().get(0).getCantidad();
        double cantidadRenglon2PrimeraFactura = result.get(0).getRenglones().get(1).getCantidad();
        double cantidadRenglon1SegundaFactura = result.get(1).getRenglones().get(0).getCantidad();
        double cantidadRenglon2SegundaFactura = result.get(1).getRenglones().get(1).getCantidad();
        assertEquals(cantidadDeFacturasEsperadas, result.size());
        assertEquals(2, cantidadRenglon1PrimeraFactura, 0);
        assertEquals(4, cantidadRenglon2PrimeraFactura, 0);
        assertEquals(2, cantidadRenglon1SegundaFactura, 0);
        assertEquals(3, cantidadRenglon2SegundaFactura, 0);
    }

    //Calculos
    @Test
    public void shouldCalcularSubTotal() {
        List<RenglonFactura> renglones = new ArrayList<>();
        RenglonFactura renglon = new RenglonFactura();
        renglon.setImporte(15.605);
        for (int i = 0; i < 5; i++) {
            renglones.add(renglon);
        }
        double resultadoEsperado = 78.025;
        double resultadoObtenido = facturaService.calcularSubTotal(renglones);
        assertEquals(resultadoEsperado, resultadoObtenido, 0);
    }

    @Test
    public void shouldCalcularDescuentoNeto() {
        double resultadoEsperado = 11.773;
        double resultadoObtenido = facturaService.calcularDescuento_neto(78.255, 15.045);
        assertEquals(resultadoEsperado, resultadoObtenido, 0);
    }

    @Test
    public void shouldCalcularRecargoNeto() {
        double resultadoEsperado = 12.110;
        double resultadoObtenido = facturaService.calcularRecargo_neto(78.122, 15.502);
        assertEquals(resultadoEsperado, resultadoObtenido, 0);
    }

    @Test
    public void shouldCalcularSubTotal_neto() {
        double resultadoEsperado = 220.477;
        double resultadoObtenido = facturaService.calcularSubTotal_neto(225.025, 10.454, 15.002);
        assertEquals(resultadoEsperado, resultadoObtenido, 0);
    }

    @Test
    public void shouldCalcularIva_netoWhenLaFacturaEsA() {
        RenglonFactura renglon1 = new RenglonFactura();
        renglon1.setImporte(5.601);
        renglon1.setIva_porcentaje(21);
        RenglonFactura renglon2 = new RenglonFactura();
        renglon2.setImporte(18.052);
        renglon2.setIva_porcentaje(21);        
        RenglonFactura renglon3 = new RenglonFactura();
        renglon3.setImporte(10.011);
        renglon3.setIva_porcentaje(10.5);
        List<RenglonFactura> renglones = new ArrayList<>();
        renglones.add(renglon1);
        renglones.add(renglon2);
        renglones.add(renglon3);
        //El renglon3 no lo deberia tener en cuenta para el calculo ya que NO es 21% de IVA
        double resultadoEsperado = 5.702;
        double resultadoObtenido = facturaService.calcularIva_neto("Factura A", 10.201, 25.009, renglones, 21);
        assertEquals(resultadoEsperado, resultadoObtenido, 0);
    }

    @Test
    public void shouldCalcularIva_netoWhenLaFacturaEsX() {
        RenglonFactura renglon1 = new RenglonFactura();
        renglon1.setImporte(5.601);
        renglon1.setIva_porcentaje(21);
        RenglonFactura renglon2 = new RenglonFactura();
        renglon2.setImporte(18.052);
        renglon2.setIva_porcentaje(21);        
        RenglonFactura renglon3 = new RenglonFactura();
        renglon3.setImporte(10.011);
        renglon3.setIva_porcentaje(10.5);
        List<RenglonFactura> renglones = new ArrayList<>();
        renglones.add(renglon1);
        renglones.add(renglon2);
        renglones.add(renglon3);
        double resultadoEsperado = 0;
        double resultadoObtenido = facturaService.calcularIva_neto("Factura X", 10, 25, renglones, 21);
        assertEquals(resultadoEsperado, resultadoObtenido, 0);
    }

    @Test
    public void shouldCalcularImpInterno_neto() {
        RenglonFactura renglon1 = new RenglonFactura();
        renglon1.setImporte(5.601);
        renglon1.setImpuesto_porcentaje(15.304);
        RenglonFactura renglon2 = new RenglonFactura();
        renglon2.setImporte(18.052);
        renglon2.setImpuesto_porcentaje(9.043);
        RenglonFactura renglon3 = new RenglonFactura();
        renglon3.setImporte(10.011);
        renglon3.setImpuesto_porcentaje(4.502);
        List<RenglonFactura> renglones = new ArrayList<>();
        renglones.add(renglon1);
        renglones.add(renglon2);
        renglones.add(renglon3);
        double resultadoEsperado = 3.319;
        double resultadoObtenido = facturaService.calcularImpInterno_neto("Factura A", 9.104, 22.008, renglones);
        assertEquals(resultadoEsperado, resultadoObtenido, 0);
    }

    @Test
    public void shouldCalcularTotal() {
        double resultadoEsperado = 460.883;
        double resultadoObtenido = facturaService.calcularTotal(350.451, 10.753, 25.159, 1.451, 84.525, 10.050);
        assertEquals(resultadoEsperado, resultadoObtenido, 0);
    }

    @Test
    public void shouldCalcularTotalFacturado() {
        List<FacturaVenta> facturasDeVenta = new ArrayList<>();
        FacturaVenta factura = new FacturaVenta();
        factura.setTotal(412.30);
        for (int i = 0; i < 5; i++) {
            facturasDeVenta.add(factura);
        }
        double resultadoEsperado = 2061.5;
        double resultadoObtenido = facturaService.calcularTotalFacturado(facturasDeVenta);
        assertEquals(resultadoEsperado, resultadoObtenido, 0);
    }

    @Test
    public void shouldCalcularIvaVenta() {
        List<FacturaVenta> facturasDeVenta = new ArrayList<>();
        FacturaVenta facturaUno = new FacturaVenta();
        FacturaVenta facturaDos = new FacturaVenta();
        facturaUno.setIva_105_neto(0);
        facturaUno.setIva_21_neto(35);
        facturaDos.setIva_105_neto(0);
        facturaDos.setIva_21_neto(30);
        for (int i = 0; i < 4; i++) {
            facturasDeVenta.add(facturaUno);
            facturasDeVenta.add(facturaDos);
        }
        double resultadoEsperado = 260;
        double resultadoObtenido = facturaService.calcularIVA_Venta(facturasDeVenta);
        assertEquals(resultadoEsperado, resultadoObtenido, 0);
    }

    @Test
    public void shouldCalcularImporte() {
        double resultadoEsperado = 90;
        double cantidad = 10;
        double precioUnitario = 10;
        double descuento = 1;
        double resultadoObtenido = facturaService.calcularImporte(cantidad, precioUnitario, descuento);
        assertEquals(resultadoEsperado, resultadoObtenido, 0);
    }

    @Test
    public void shouldCalcularGananciaTotal() {
        List<RenglonFactura> renglones = new ArrayList<>();
        RenglonFactura renglon1 = new RenglonFactura();
        RenglonFactura renglon2 = new RenglonFactura();
        renglon1.setGanancia_neto(50);
        renglon1.setCantidad(2);
        renglon2.setGanancia_neto(25);
        renglon2.setCantidad(2);
        renglones.add(renglon1);
        renglones.add(renglon2);
        List<FacturaVenta> facturas = new ArrayList<>();
        FacturaVenta facturaUno = new FacturaVenta();
        facturaUno.setRenglones(renglones);
        FacturaVenta facturaDos = new FacturaVenta();
        facturaDos.setRenglones(renglones);
        facturas.add(facturaUno);
        facturas.add(facturaDos);
        when(facturaService.getRenglonesDeLaFactura(facturaUno)).thenReturn(renglones);
        when(facturaService.getRenglonesDeLaFactura(facturaDos)).thenReturn(renglones);
        double resultadoEsperado = 300;
        double resultadoObtenido = facturaService.calcularGananciaTotal(facturas);
        assertEquals(resultadoEsperado, resultadoObtenido, 0);
    }

    @Test
    public void shouldCalcularIVANetoWhenCompra() {
        Producto producto = new Producto();
        producto.setPrecioCosto(100.00);
        producto.setImpuestoInterno_neto(0.0);
        producto.setIva_porcentaje(21);
        double resultadoEsperado = 21;
        double resultadoObtenido = facturaService.calcularIVA_neto(Movimiento.COMPRA, producto, 0.0);
        assertEquals(resultadoEsperado, resultadoObtenido, 0);
    }

    @Test
    public void shouldCalcularIVANetoWhenVenta() {
        Producto producto = new Producto();
        producto.setPrecioVentaPublico(100.00);
        producto.setImpuestoInterno_neto(0.0);
        producto.setIva_porcentaje(21);
        double resultadoEsperado = 21;
        double resultadoObtenido = facturaService.calcularIVA_neto(Movimiento.VENTA, producto, 0.0);
        assertEquals(resultadoEsperado, resultadoObtenido, 0);
    }

    @Test
    public void shouldCalcularPrecioUnitarioWhenEsUnaVentaConFacturaA() {
        Producto producto = new Producto();
        producto.setPrecioCosto(100.00);
        producto.setPrecioVentaPublico(121.00);
        producto.setImpuestoInterno_neto(0.0);
        producto.setIva_porcentaje(21);
        double resultadoEsperado = 121;
        double resultadoObtenido = facturaService.calcularPrecioUnitario(Movimiento.VENTA, "Factura A", producto);
        assertEquals(resultadoEsperado, resultadoObtenido, 0);
    }

    @Test
    public void shouldCalcularPrecioUnitarioWhenEsUnaVentaConFacturaX() {
        Producto producto = new Producto();
        producto.setPrecioCosto(100.00);
        producto.setPrecioVentaPublico(121.00);
        producto.setImpuestoInterno_neto(0.0);
        producto.setIva_porcentaje(21);
        double resultadoEsperado = 121;
        double resultadoObtenido = facturaService.calcularPrecioUnitario(Movimiento.VENTA, "Factura X", producto);
        assertEquals(resultadoEsperado, resultadoObtenido, 0);
    }

    @Test
    public void shouldCalcularPrecioUnitarioWhenEsUnaCompraConFacturaA() {
        Producto producto = new Producto();
        producto.setPrecioCosto(100.00);
        producto.setPrecioVentaPublico(121.00);
        producto.setImpuestoInterno_neto(0.0);
        producto.setIva_porcentaje(21);
        double resultadoEsperado = 100;
        double resultadoObtenido = facturaService.calcularPrecioUnitario(Movimiento.COMPRA, "Factura A", producto);
        assertEquals(resultadoEsperado, resultadoObtenido, 0);
    }

    @Test
    public void shouldCalcularPrecioUnitarioWhenEsUnaCompraConFacturaX() {
        Producto producto = new Producto();
        producto.setPrecioCosto(100.00);
        producto.setPrecioVentaPublico(121.00);
        producto.setImpuestoInterno_neto(0.0);
        producto.setIva_porcentaje(21);
        double resultadoEsperado = 100;
        double resultadoObtenido = facturaService.calcularPrecioUnitario(Movimiento.COMPRA, "Factura X", producto);
        assertEquals(resultadoEsperado, resultadoObtenido, 0);
    }

    @Test
    public void shouldCalcularPrecioUnitarioWhenEsUnaCompraConFacturaB() {
        Producto producto = new Producto();
        producto.setPrecioCosto(100.00);
        producto.setGanancia_neto(100);
        producto.setIva_neto(42);
        producto.setPrecioVentaPublico(200);
        producto.setPrecioLista(242);
        producto.setImpuestoInterno_neto(0.0);
        producto.setIva_porcentaje(21);
        double resultadoEsperado = 121;
        double resultadoObtenido = facturaService.calcularPrecioUnitario(Movimiento.COMPRA, "Factura B", producto);
        assertEquals(resultadoEsperado, resultadoObtenido, 0);
    }

    @Test
    public void shouldCalcularPrecioUnitarioWhenEsUnaCompraConFacturaC() {
        Producto producto = new Producto();
        producto.setPrecioCosto(100.00);
        producto.setGanancia_neto(100);
        producto.setIva_neto(42);
        producto.setPrecioVentaPublico(200);
        producto.setPrecioLista(242);
        producto.setImpuestoInterno_neto(0.0);
        producto.setIva_porcentaje(21);
        double resultadoEsperado = 121;
        double resultadoObtenido = facturaService.calcularPrecioUnitario(Movimiento.COMPRA, "Factura C", producto);
        assertEquals(resultadoEsperado, resultadoObtenido, 0);
    }

    @Test
    public void shouldCalcularPrecioUnitarioWhenEsUnaCompraConFacturaY() {
        Producto producto = new Producto();
        producto.setPrecioCosto(100.00);
        producto.setGanancia_neto(100);
        producto.setIva_neto(42);
        producto.setPrecioVentaPublico(200);
        producto.setPrecioLista(242);
        producto.setImpuestoInterno_neto(0.0);
        producto.setIva_porcentaje(21);
        double resultadoEsperado = 121;
        double resultadoObtenido = facturaService.calcularPrecioUnitario(Movimiento.COMPRA, "Factura Y", producto);
        assertEquals(resultadoEsperado, resultadoObtenido, 0);
    }

    @Test
    public void shouldCalcularPrecioUnitarioWhenEsUnaVentaConFacturaB() {
        Producto producto = new Producto();
        producto.setPrecioCosto(100.00);
        producto.setGanancia_neto(100);
        producto.setIva_neto(42);
        producto.setPrecioVentaPublico(200);
        producto.setPrecioLista(242);
        producto.setImpuestoInterno_neto(0.0);
        producto.setIva_porcentaje(21);
        double resultadoEsperado = 242;
        double resultadoObtenido = facturaService.calcularPrecioUnitario(Movimiento.VENTA, "Factura B", producto);
        assertEquals(resultadoEsperado, resultadoObtenido, 0);
    }

    @Test
    public void shouldCalcularPrecioUnitarioWhenEsUnaVentaConFacturaC() {
        Producto producto = new Producto();
        producto.setPrecioCosto(100.00);
        producto.setGanancia_neto(100);
        producto.setIva_neto(42);
        producto.setPrecioVentaPublico(200);
        producto.setPrecioLista(242);
        producto.setImpuestoInterno_neto(0.0);
        producto.setIva_porcentaje(21);
        double resultadoEsperado = 242;
        double resultadoObtenido = facturaService.calcularPrecioUnitario(Movimiento.VENTA, "Factura C", producto);
        assertEquals(resultadoEsperado, resultadoObtenido, 0);
    }

    @Test
    public void shouldCalcularPrecioUnitarioWhenVentaYFacturaY() {
        Producto producto = new Producto();
        producto.setPrecioCosto(100.00);
        producto.setGanancia_neto(100);
        producto.setIva_neto(42);
        producto.setPrecioVentaPublico(200);
        producto.setPrecioLista(242);
        producto.setImpuestoInterno_neto(0.0);
        producto.setIva_porcentaje(21);
        double resultadoEsperado = 221;
        double resultadoObtenido = facturaService.calcularPrecioUnitario(Movimiento.VENTA, "Factura Y", producto);
        assertEquals(resultadoEsperado, resultadoObtenido, 0);
    }
}
