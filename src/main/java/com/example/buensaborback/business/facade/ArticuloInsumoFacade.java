package com.example.buensaborback.business.facade;

import com.example.buensaborback.business.facade.Base.BaseFacade;
import com.example.buensaborback.domain.dtos.ArticuloInsumoDto;

// Interfaz de facade que extiende BaseFacade para operaciones CRUD.
public interface ArticuloInsumoFacade extends BaseFacade<ArticuloInsumoDto, Long> {
}
