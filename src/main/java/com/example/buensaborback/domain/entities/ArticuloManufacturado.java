package com.example.buensaborback.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@SuperBuilder
@Audited
public class ArticuloManufacturado extends Articulo {

    private String descripcion;
    private Integer tiempoEstimadoMinutos;
    @Column(columnDefinition = "TEXT")
    private String preparacion;
    @Transient
    private Integer stock;
    @Transient
    private Double precioCosto;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "imagenes_id")
    @NotAudited
    protected List<ImagenArticulo> imagenesManufacturado;

    // Relación uno a muchos con la entidad ArticuloManufacturadoDetalle
    @OneToMany(mappedBy = "articuloManufacturado", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Builder.Default
    private Set<ArticuloManufacturadoDetalle> articuloManufacturadoDetalles = new HashSet<>();



    // Método que redondea un valor doble a dos decimales usando RoundingMode.HALF_UP
    public static double redondear(double value) {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }


    // Se calculan los stocks disponibles de cada ingrediente por la cantidad que se vaya a usar de cada uno
    public Integer stockCalculado() throws RuntimeException, InterruptedException {


        // Se imprime información sobre cada detalle manufacturado y su stock actual
        List<Integer> stocksPorCantidad = articuloManufacturadoDetalles.stream().map(
                detalle -> (int) (detalle.getArticuloInsumo().getStockActual() / detalle.getCantidad())
        ).toList();

        // Se busca el mínimo stock disponible
        articuloManufacturadoDetalles.stream().forEach(
                detalle -> System.out.println("Detalle: " + detalle.getCantidad() + " unidades de " + detalle.getArticuloInsumo().getDenominacion() + ", con stock " + detalle.getArticuloInsumo().getStockActual())
        );

        // Si no hay stock, se lanza una excepción
        Integer stockMinimo = stocksPorCantidad.stream().reduce(Integer.MAX_VALUE, (a, b) -> a < b ? a : b);


        // Método que calcula el stock mínimo disponible basado en los detalles manufacturados
        if (stockMinimo < 0) {
            return 0;
        } else {
            return stockMinimo;
        }
    }




    public Double precioCostoCalculado() {

        // Se calcula el costo de cada ingrediente por la cantidad que se vaya a usar de cada uno
        List<Double> costos = articuloManufacturadoDetalles.stream().map(
                detalle -> (detalle.getArticuloInsumo().getPrecioCompra() * detalle.getCantidad())
        ).toList();

        // Se imprime información sobre cada detalle manufacturado y su costo
        articuloManufacturadoDetalles.stream().forEach(
                detalle -> System.out.println(detalle.getCantidad() + " unidades de " + detalle.getArticuloInsumo().getDenominacion() + " con costo unitario de $" + detalle.getArticuloInsumo().getPrecioCompra() + " = " + detalle.getArticuloInsumo().getPrecioCompra() * detalle.getCantidad())
        );

        // Se suman los costos
        Double costo = redondear(costos.stream().reduce(0d, Double::sum));

        return costo;
    }

// Método que calcula el costo total basado en los detalles manufacturados
// Redondea el costo total a dos decimales y lo retorna

}
}
