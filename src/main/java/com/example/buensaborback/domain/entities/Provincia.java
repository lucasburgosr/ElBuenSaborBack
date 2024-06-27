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
public class Provincia extends Base{

    private String nombre;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pais_id")
// Declaración del atributo privado 'pais' de tipo 'Pais'
    private Pais pais;

    @OneToMany(mappedBy = "provincia")
    @ToString.Exclude
    @Builder.Default
    @JsonBackReference(value = "provincia_localidades")
// Declaración del atributo privado 'localidades' de tipo 'Set<Localidad>', inicializado como un nuevo HashSet
    private Set<Localidad> localidades = new HashSet<>();

}
