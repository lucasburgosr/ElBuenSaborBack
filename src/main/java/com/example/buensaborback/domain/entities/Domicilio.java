package com.example.buensaborback.domain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;

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
public class Domicilio extends Base {

    private String calle;
    private Integer numero;
    private Integer cp;

    // Campo que representa la relación con la entidad "Localidad"
    @ManyToOne
    @JoinColumn(name = "localidad_id")
    private Localidad localidad;

    // Campo que representa la relación muchos a muchos con la entidad "Cliente"
    @ManyToMany(mappedBy = "domicilios", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @ToString.Exclude
    @Builder.Default
    @JsonBackReference(value = "domicilio_clientes")
    private Set<Cliente> clientes = new HashSet<>();

    // Campo que representa la relación uno a uno con la entidad "Sucursal"
    @OneToOne(mappedBy = "domicilio")
    @ToString.Exclude
    @JsonBackReference(value = "domicilio_sucursal")
    private Sucursal sucursal;

    // Campo que representa la relación uno a muchos con la entidad "Pedido"
    @OneToMany(mappedBy = "domicilio", fetch = FetchType.LAZY)
    @ToString.Exclude
    @Builder.Default
    @JsonBackReference(value = "domicilio_pedidos")
    private Set<Pedido> pedidos = new HashSet<>();
}
