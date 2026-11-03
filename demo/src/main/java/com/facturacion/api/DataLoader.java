package com.facturacion.api;

import java.time.LocalDate;
import java.util.List;

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
        // 1. Cargar productos si faltan
        List<Producto> productosExistentes = productoService.findAll();
        Producto p1 = buscarOCrearProducto(productosExistentes, "PC", 142565.00);
        Producto p2 = buscarOCrearProducto(productosExistentes, "Monitor", 628.00);
        buscarOCrearProducto(productosExistentes, "Teclado", 547.00);
        Producto p4 = buscarOCrearProducto(productosExistentes, "Mouse", 358.00);
        buscarOCrearProducto(productosExistentes, "Impresora", 85473.00);
        buscarOCrearProducto(productosExistentes, "Parlantes", 1478.00);
        buscarOCrearProducto(productosExistentes, "Auriculares", 741.00);
        buscarOCrearProducto(productosExistentes, "WebCam", 965.00);

        // 2. Cargar clientes del apunte si no existen
        List<Cliente> clientesExistentes = clienteService.findAll();

        Cliente c1 = clientesExistentes.stream()
                .filter(c -> "fveschi@gmail.com".equalsIgnoreCase(c.getEmail()))
                .findFirst()
                .orElse(null);

        if (c1 == null) {
            c1 = clienteService.save(new Cliente(null, "Veschi", "Fabian", "fveschi@gmail.com", LocalDate.of(2025, 9, 26), ""));
        }

        boolean existeAndres = clientesExistentes.stream()
                .anyMatch(c -> "profesor@bolsadeideas.com".equalsIgnoreCase(c.getEmail()));
        if (!existeAndres) {
            clienteService.save(new Cliente(null, "Guzmán", "Andrés", "profesor@bolsadeideas.com", LocalDate.of(2025, 9, 20), ""));
        }

        boolean existeJohn = clientesExistentes.stream()
                .anyMatch(c -> "john.doe@gmail.com".equalsIgnoreCase(c.getEmail()));
        if (!existeJohn) {
            clienteService.save(new Cliente(null, "Doe", "John", "john.doe@gmail.com", LocalDate.of(2025, 9, 15), ""));
        }

        // 3. Cargar factura de prueba para Fabian Veschi si no tiene ninguna
        if (c1 != null && c1.getId() != null) {
            List<Factura> facturasVeschi = facturaService.findAllByCliente(c1.getId());
            if (facturasVeschi.isEmpty() && p2 != null && p4 != null) {
                Factura f1 = new Factura();
                f1.setCliente(c1);
                f1.setDescripcion("Gastos varios");
                f1.setObservacion("Configuración de oficina y accesorios");
                f1.setCreateAt(LocalDate.of(2025, 9, 19));

                ItemFactura it1 = new ItemFactura(null, 3, p2, f1); // 3 Monitores
                ItemFactura it2 = new ItemFactura(null, 1, p4, f1); // 1 Mouse

                f1.getLista().add(it1);
                f1.getLista().add(it2);

                facturaService.save(f1);
            }
        }
    }

    private Producto buscarOCrearProducto(List<Producto> lista, String nombre, Double precio) {
        return lista.stream()
                .filter(p -> p.getNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .orElseGet(() -> productoService.save(new Producto(null, nombre, precio)));
    }
}
