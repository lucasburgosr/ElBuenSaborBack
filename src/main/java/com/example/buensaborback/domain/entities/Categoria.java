package com.example.buensaborback.domain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class Categoria extends Base{

    private String denominacion;

    // Relación uno a muchos con la entidad Categoria para las subcategorías

    @OneToMany(mappedBy = "categoriaPadre", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Categoria> subCategorias = new HashSet<>();

    // Relación muchos a uno con la entidad Categoria para la categoría padre

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "categoria_padre_id")
    @JsonBackReference(value = "categoria_categoriaPadre")
    private Categoria categoriaPadre;

    // Relación muchos a muchos con la entidad Sucursal

    @ManyToMany(mappedBy = "categorias")
    @ToString.Exclude
    @Builder.Default
    @JsonIgnoreProperties({"nombre", "domicilio"})
    private Set<Sucursal> sucursales = new HashSet<>();

    // Relación uno a muchos con la entidad Articulo

    @OneToMany(mappedBy = "categoria")
    @ToString.Exclude
    @Builder.Default
    @JsonBackReference(value = "categoria_articulos")
    private Set<Articulo> articulos = new HashSet<>();
}

