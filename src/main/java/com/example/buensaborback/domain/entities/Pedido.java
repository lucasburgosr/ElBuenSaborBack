package com.example.buensaborback.domain.entities;

import com.example.buensaborback.domain.enums.Estado;
import com.example.buensaborback.domain.enums.FormaPago;
import com.example.buensaborback.domain.enums.TipoEnvio;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;

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
public class Pedido extends Base{

    private LocalTime horaEstimadaFinalizacion;
    private Double total;
    private Double totalCosto;
    private Estado estado;
    private TipoEnvio tipoEnvio;
    private FormaPago formaPago;
    private LocalDate fechaPedido;

    @ManyToOne
    @JoinColumn(name = "domicilio_id")
// Declaración del atributo privado 'domicilio' de tipo 'Domicilio'
    private Domicilio domicilio;

    @ManyToOne
    @JoinColumn(name = "sucursal_id")
// Declaración del atributo privado 'sucursal' de tipo 'Sucursal'
    private Sucursal sucursal;

    @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonBackReference(value = "pedido_factura")
// Declaración del atributo privado 'factura' de tipo 'Factura'
    private Factura factura;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    @JsonIgnoreProperties({"pedidos", "domicilios", "usuario", "imagen"})
// Declaración del atributo privado 'cliente' de tipo 'Cliente'
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "empleado_id")
    @JsonIgnoreProperties({"pedidos", "domicilios", "usuario", "imagen", "rol"})
// Declaración del atributo privado 'empleado' de tipo 'Empleado'
    private Empleado empleado;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
// Declaración del atributo privado 'detallePedidos' de tipo 'Set<DetallePedido>', inicializado como un nuevo HashSet
    private Set<DetallePedido> detallePedidos = new HashSet<>();

}
