package com.example.buensaborback.domain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
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
public class Pais extends Base{

    private String nombre;

    @OneToMany(mappedBy = "pais")
    @ToString.Exclude
    @Builder.Default
    @JsonBackReference(value = "pais_provincias")
// Declaración del atributo privado 'provincias' de tipo 'Set<Provincia>', inicializado como un nuevo HashSet
    private Set<Provincia> provincias = new HashSet<>();

}
