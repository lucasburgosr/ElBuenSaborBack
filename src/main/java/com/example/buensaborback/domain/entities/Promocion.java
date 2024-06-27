package com.example.buensaborback.domain.entities;

import com.example.buensaborback.domain.enums.TipoPromocion;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Builder
@Audited
public class Promocion extends Base {

    private String denominacion;
    private LocalDate fechaDesde;
    private LocalDate fechaHasta;
    private LocalTime horaDesde;
    private LocalTime horaHasta;
    private String descripcionDescuento;
    private Double precioPromocional;
    private TipoPromocion tipoPromocion;

    @OneToMany(mappedBy = "promocion", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    @NotAudited
// Declaración del atributo privado 'imagenes' de tipo 'Set<ImagenPromocion>', inicializado como un nuevo HashSet
    private Set<ImagenPromocion> imagenes = new HashSet<>();

    @ManyToMany(mappedBy = "promociones")
    @ToString.Exclude
    @JsonIgnoreProperties({"nombre", "domicilio", "promociones"})
    @Builder.Default
// Declaración del atributo privado 'sucursales' de tipo 'Set<Sucursal>', inicializado como un nuevo HashSet
    private Set<Sucursal> sucursales = new HashSet<>();

    @OneToMany(mappedBy = "promocion", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Builder.Default
// Declaración del atributo privado 'promocionDetalles' de tipo 'Set<PromocionDetalle>', inicializado como un nuevo HashSet
    private Set<PromocionDetalle> promocionDetalles = new HashSet<>();

}
