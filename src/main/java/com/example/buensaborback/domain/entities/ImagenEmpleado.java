package com.example.buensaborback.domain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Builder
public class ImagenEmpleado extends Base{

    private String url;

    // Campo que representa la relación uno a uno con la entidad "Empleado"
    @OneToOne(mappedBy = "imagen", fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonBackReference(value = "imagen_empleado")
    private Empleado empleado;


}
