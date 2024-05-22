package com.example.buensaborback.domain.dtos;

import com.example.buensaborback.domain.entities.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class ArticuloDto extends BaseDto{

    protected String denominacion;
    protected Double precioVenta;
    protected Set<ImagenArticulo> imagenes = new HashSet<>();
    protected UnidadMedida unidadMedida;
    protected Categoria categoria;
    protected Set<DetallePedido> detallePedidos = new HashSet<>();
    protected Set<PromocionDetalle> promocionDetalles = new HashSet<>();

}
