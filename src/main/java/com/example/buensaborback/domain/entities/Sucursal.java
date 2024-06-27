package com.example.buensaborback.domain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;

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
public class Sucursal extends Base{

    private String nombre;
    private LocalTime horarioApertura;
    private LocalTime horarioCierre;
    private Boolean casaMatriz;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "domicilio_id")
// Declaración del atributo privado 'domicilio' de tipo 'Domicilio'
    private Domicilio domicilio;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    @JoinTable(name = "sucursal_categoria",
            joinColumns = @JoinColumn(name = "sucursal_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id"))
    @Builder.Default
    @JsonBackReference(value = "sucursal_categorias")
// Declaración del atributo privado 'categorias' de tipo 'Set<Categoria>', inicializado como un nuevo HashSet
    private Set<Categoria> categorias = new HashSet<>();

    @OneToMany(mappedBy = "sucursal", fetch = FetchType.LAZY)
    @ToString.Exclude
    @Builder.Default
    @JsonBackReference(value = "sucursal_pedidos")
// Declaración del atributo privado 'pedidos' de tipo 'Set<Pedido>', inicializado como un nuevo HashSet
    private Set<Pedido> pedidos = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "sucursal_promocion",
            joinColumns = @JoinColumn(name = "sucursal_id"),
            inverseJoinColumns = @JoinColumn(name = "promocion_id"))
    @JsonIgnoreProperties({"denominacion", "fechaDesde", "fechaHasta", "sucursales"})
    @Builder.Default
// Declaración del atributo privado 'promociones' de tipo 'Set<Promocion>', inicializado como un nuevo HashSet
    private Set<Promocion> promociones = new HashSet<>();

    @OneToMany(mappedBy = "sucursal", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @ToString.Exclude
    @Builder.Default
    @JsonBackReference(value = "sucursal_empleados")
// Declaración del atributo privado 'empleados' de tipo 'Set<Empleado>', inicializado como un nuevo HashSet
    private Set<Empleado> empleados = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    @JsonIgnoreProperties("sucursales")
// Declaración del atributo privado 'empresa' de tipo 'Empresa'
    private Empresa empresa;

}
