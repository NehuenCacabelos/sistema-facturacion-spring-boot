package com.facturacion.api;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.facturacion.api.entities.Cliente;
import com.facturacion.api.entities.Factura;
import com.facturacion.api.entities.ItemFactura;
import com.facturacion.api.entities.Producto;
import com.facturacion.api.services.IClienteService;
import com.facturacion.api.services.IFacturaService;
import com.facturacion.api.services.IProductoService;

@Component
public class DataLoader implements CommandLineRunner {

    private final IClienteService clienteService;
    private final IProductoService productoService;
    private final IFacturaService facturaService;

    public DataLoader(IClienteService clienteService, IProductoService productoService, IFacturaService facturaService) {
        this.clienteService = clienteService;
        this.productoService = productoService;
        this.facturaService = facturaService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (clienteService.findAll().isEmpty()) {
            Cliente c1 = new Cliente(null, "Veschi", "Fabian", "fveschi@gmail.com", LocalDate.of(2025, 9, 26), "");
            Cliente c2 = new Cliente(null, "Guzmán", "Andrés", "profesor@bolsadeideas.com", LocalDate.of(2025, 9, 20), "");
            Cliente c3 = new Cliente(null, "Doe", "John", "john.doe@gmail.com", LocalDate.of(2025, 9, 15), "");

            c1 = clienteService.save(c1);
            c2 = clienteService.save(c2);
            c3 = clienteService.save(c3);

            if (productoService.findAll().isEmpty()) {
                Producto p1 = productoService.save(new Producto(null, "PC", 142565.00));
                Producto p2 = productoService.save(new Producto(null, "Monitor", 628.00));
                Producto p3 = productoService.save(new Producto(null, "Teclado", 547.00));
                Producto p4 = productoService.save(new Producto(null, "Mouse", 358.00));
                Producto p5 = productoService.save(new Producto(null, "Impresora", 85473.00));
                Producto p6 = productoService.save(new Producto(null, "Parlantes", 1478.00));
                Producto p7 = productoService.save(new Producto(null, "Auriculares", 741.00));
                Producto p8 = productoService.save(new Producto(null, "WebCam", 965.00));

                // Factura de prueba inicial para Fabian Veschi
                Factura f1 = new Factura();
                f1.setCliente(c1);
                f1.setDescripcion("Gastos varios");
                f1.setObservacion("Configuración de oficina y accesorios");
                f1.setCreateAt(LocalDate.of(2025, 9, 19));

                ItemFactura it1 = new ItemFactura(null, 3, p2, f1); // 3 Monitores: 3 * 628 = 1884
                ItemFactura it2 = new ItemFactura(null, 1, p4, f1); // 1 Mouse: 1 * 358 = 358 (Total 2242)

                f1.getLista().add(it1);
                f1.getLista().add(it2);

                facturaService.save(f1);
            }
        } else if (productoService.findAll().isEmpty()) {
            productoService.save(new Producto(null, "PC", 142565.00));
            productoService.save(new Producto(null, "Monitor", 628.00));
            productoService.save(new Producto(null, "Teclado", 547.00));
            productoService.save(new Producto(null, "Mouse", 358.00));
            productoService.save(new Producto(null, "Impresora", 85473.00));
            productoService.save(new Producto(null, "Parlantes", 1478.00));
            productoService.save(new Producto(null, "Auriculares", 741.00));
            productoService.save(new Producto(null, "WebCam", 965.00));
        }
    }
}
