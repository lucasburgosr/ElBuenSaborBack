package com.example.buensaborback.business.facade;

import com.example.buensaborback.business.facade.Base.BaseFacade;
import com.example.buensaborback.domain.dtos.PedidoDto;
// Interfaz de facade que extiende BaseFacade para operaciones CRUD.
public interface PedidoFacade extends BaseFacade<PedidoDto, Long> {
}
