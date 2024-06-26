package com.example.buensaborback.domain.entities;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import java.util.HashSet;
import java.util.Set;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ArticuloInsumo.class, name = "insumo"),
        @JsonSubTypes.Type(value = ArticuloManufacturado.class, name = "manufacturado")
})
@Audited
public abstract class Articulo extends Base {

    protected String denominacion;
    protected Double precioVenta;

    // Relación uno a muchos con la entidad ImagenArticulo
    @OneToMany(mappedBy = "articulo", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Builder.Default
    @NotAudited
    protected Set<ImagenArticulo> imagenes = new HashSet<>();

    // Relación muchos a uno con la entidad UnidadMedida
    @ManyToOne
    @JoinColumn(name = "UnidadMedida_ID")
    protected UnidadMedida unidadMedida;

    // Relación muchos a uno con la entidad Categoria
    @ManyToOne
    @JoinColumn(name = "Categoria_ID")
    @JsonIgnoreProperties({"subCategorias", "categoriaPadre", "sucursales", "articulos"})
    protected Categoria categoria;

    // Relación uno a muchos con la entidad DetallePedido
    @OneToMany(mappedBy = "articulo")
    @ToString.Exclude
    @Builder.Default
    @JsonBackReference(value = "articulo_detallepedidos")
    protected Set<DetallePedido> detallePedidos = new HashSet<>();

    // Relación uno a muchos con la entidad PromocionDetalle
    @OneToMany(mappedBy = "articulo")
    @ToString.Exclude
    @Builder.Default
    @JsonBackReference(value = "articulo_promociondetalles")
    protected Set<PromocionDetalle> promocionDetalles = new HashSet<>();

}
