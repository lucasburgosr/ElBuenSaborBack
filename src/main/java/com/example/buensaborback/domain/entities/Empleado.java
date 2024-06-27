package com.example.buensaborback.domain.entities;

import com.example.buensaborback.domain.enums.Rol;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class Empleado extends Base {

    // Campo que representa la relación uno a muchos con la entidad "Pedido"
    @OneToMany(mappedBy = "empleado", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @ToString.Exclude
    @Builder.Default
    @JsonBackReference(value = "empleado_pedidos")
    private Set<Pedido> pedidos = new HashSet<>();

    // Campo para almacenar el nombre del empleado
    private String nombre;

    // Campo para almacenar el apellido del empleado
    private String apellido;

    // Campo para almacenar el teléfono del empleado
    private String telefono;

    // Campo para almacenar el email del empleado
    private String email;

    // Campo para almacenar la fecha de nacimiento del empleado
    private LocalDate fechaNacimiento;

    // Campo para almacenar el rol del empleado
    private Rol rol;

    // Campo que representa la relación uno a uno con la entidad "UsuarioEmpleado"
    @OneToOne
    @ToString.Exclude
    @JoinColumn(name = "usuario_id")
    @JsonBackReference(value = "empleado_usuario")
    private UsuarioEmpleado usuario;

    // Campo que representa la relación uno a uno con la entidad "ImagenEmpleado"
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "imagen_id")
    @NotAudited
    private ImagenEmpleado imagen;

    // Campo que representa la relación muchos a uno con la entidad "Sucursal"
    @ManyToOne
    @JoinColumn(name = "sucursal_id")
    private Sucursal sucursal;
}
