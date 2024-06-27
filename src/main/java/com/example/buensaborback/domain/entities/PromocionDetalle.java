package com.example.buensaborback.domain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class PromocionDetalle extends Base {

    private Integer cantidad;

    @ManyToOne
    @JoinColumn(name = "articulo_id")
    @JsonIgnoreProperties({"imagenes", "categoria", "articuloManufacturadoDetalles"})
// Declaración del atributo privado 'articulo' de tipo 'Articulo'
    private Articulo articulo;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "promocion_id")
    @JsonBackReference(value = "detallepedido_pedido")
// Declaración del atributo privado 'promocion' de tipo 'Promocion'
    private Promocion promocion;

}
