package com.example.buensaborback.business.facade;

import com.example.buensaborback.business.facade.Base.BaseFacade;
import com.example.buensaborback.domain.dtos.EmpleadoDto;


public interface EmpleadoFacade extends BaseFacade<EmpleadoDto, Long> {
    //Busca un empleado por su dirección de correo electrónico.
    public EmpleadoDto findByEmail(String email) throws Exception;
}
