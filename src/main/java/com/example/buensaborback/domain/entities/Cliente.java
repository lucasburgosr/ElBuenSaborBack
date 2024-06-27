package com.example.buensaborback.domain.entities;

import com.example.buensaborback.domain.enums.Rol;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@ToString
@SuperBuilder
@Audited
public class Cliente extends Base {

    private Rol rol;
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
    private LocalDate fechaNacimiento;

    // Relación uno a muchos con la entidad Pedido

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @Builder.Default
    @JsonIgnoreProperties("cliente")
    private Set<Pedido> pedidos = new HashSet<>();

    // Relación muchos a muchos con la entidad Domicilio

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "Cliente_domicilio",
            joinColumns = @JoinColumn(name = "Cliente_id"),
            inverseJoinColumns = @JoinColumn(name = "domicilio_id"))
    @Builder.Default
    private Set<Domicilio> domicilios = new HashSet<>();

    // Relación uno a uno con la entidad UsuarioCliente

    @OneToOne
    @ToString.Exclude
    @JoinColumn(name = "usuario_id")
    @JsonBackReference(value = "cliente_usuario")
    private UsuarioCliente usuario;

    // Relación uno a uno con la entidad ImagenCliente

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "imagen_id")
    @NotAudited
    private ImagenCliente imagen;
}

