package com.example.buensaborback.business.facade;

import com.example.buensaborback.business.facade.Base.BaseFacade;
import com.example.buensaborback.domain.dtos.UsuarioClienteDto;
import com.example.buensaborback.domain.entities.UsuarioCliente;
// Interfaz de facade que extiende BaseFacade para operaciones CRUD.
public interface UsuarioClienteFacade extends BaseFacade<UsuarioClienteDto, Long> {
}
