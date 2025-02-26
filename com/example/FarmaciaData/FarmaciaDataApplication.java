package com.example.FarmaciaData;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.FarmaciaData.models.Cliente;
import com.example.FarmaciaData.models.Factura;
import com.example.FarmaciaData.models.Farmacia;
import com.example.FarmaciaData.models.Producto;
import com.example.FarmaciaData.repository.ClienteRepository;
import com.example.FarmaciaData.repository.FacturaRepository;
import com.example.FarmaciaData.repository.FarmaciaRepository;
import com.example.FarmaciaData.repository.ProductoRepository;


@SpringBootApplication
public class FarmaciaDataApplication implements CommandLineRunner {

	
	@Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private FarmaciaRepository farmaciaRepository;
    @Autowired
    private ClienteRepository   clienteRepository;
    @Autowired
    private FacturaRepository facturaRepository;


	public static void main(String[] args) {
		SpringApplication.run(FarmaciaDataApplication.class, args);
	}


    @Override
    public void run(String... args) throws Exception {
        Farmacia farmacity = new Farmacia("Farmacity");
        Farmacia sol = new Farmacia("Farmacia Sol");
        Farmacia luna = new Farmacia("Farmacia Luna");
        Farmacia marte = new Farmacia("Farmacia Marte");

        farmaciaRepository.saveAll(List.of(farmacity, sol, luna , marte));

        Producto producto1 = Producto.builder()
                .nombre("Jabón en Polvo")
                .fechaCreacion(LocalDate.of(2021, 7, 22))
                .precio(125.00)
                .farmacias(List.of(farmacity, luna))
                .build();

        Producto producto2 = Producto.builder()
                .nombre("Ibuprofeno")
                .fechaCreacion(LocalDate.of(2020, 3, 2))
                .precio(500.00)
                .farmacias(List.of(marte, sol))
                .build();
        Producto producto3 = Producto.builder()
                .nombre("Colgate")
                .fechaCreacion(LocalDate.of(2017, 5, 7))
                .precio(700.00)
                .farmacias(List.of(farmacity, luna, marte))
                .build();
        Producto producto4 = Producto.builder()
                .nombre("Pastilla de la garganta")
                .fechaCreacion(LocalDate.of(2025, 3, 31))
                .precio(850.00)
                .farmacias(List.of(marte, luna, sol))
                .build();

		productoRepository.saveAll(List.of(producto1, producto2, producto3, producto4));

        Cliente cliente1 = Cliente.builder()
                .dni("12345678A")
                .nombre("Pedro")
                .apellido("Perez")                
                .productos(List.of(producto1, producto2))
                .farmacias(List.of(farmacity, luna))
                .build();

        Cliente cliente2 = Cliente.builder()
                .dni("87654321B")
                .nombre("Maria")
                .apellido("Gomez")                
                .productos(List.of(producto3, producto4))
                .farmacias(List.of(marte, sol))
                .build();                

        Cliente cliente3 = Cliente.builder()                
                .dni("34567890C")  
                .nombre("Angeles") 
                .apellido("Feuillade")             
                .productos(List.of(producto1, producto2, producto3, producto4))
                .farmacias(List.of(farmacity, luna, marte, sol))
                .build();
        clienteRepository.saveAll(List.of(cliente1, cliente2, cliente3));
	

    Factura factura1 = Factura.builder()    
            .numeroFactura("AA-78459")
            .fecha(LocalDate.of(2022, 3, 2))
            .productos(List.of(producto1, producto2))
            .farmacias(List.of(farmacity, luna))
            .cliente(cliente1)
            .build();

    facturaRepository.saveAll(List.of(factura1));
    }
}
