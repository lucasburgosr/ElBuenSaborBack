package com.example.buensaborback.domain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Builder
@Audited
public class DetallePedido extends Base {

    private Integer cantidad;
    private Double subTotal;

    // Campo que representa la relación con la entidad "Articulo"
    @ManyToOne
    @JoinColumn(name = "articulo_id")
    @JsonIgnoreProperties({"imagenes", "unidadMedida", "categoria", "articuloManufacturadoDetalles"})
    private Articulo articulo;

    // Campo que representa la relación con la entidad "Pedido"
    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "pedido_id")
    @JsonBackReference(value = "detallepedido_pedido")
    private Pedido pedido;
}
