package com.example.buensaborback.business.facade;

import com.example.buensaborback.business.facade.Base.BaseFacade;
import com.example.buensaborback.domain.dtos.FacturaDto;
// Interfaz de facade que extiende BaseFacade para operaciones CRUD.
public interface FacturaFacade extends BaseFacade<FacturaDto, Long> {
}
