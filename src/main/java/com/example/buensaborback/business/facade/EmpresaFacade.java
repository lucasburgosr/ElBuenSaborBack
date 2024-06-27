package com.example.buensaborback.business.facade;

import com.example.buensaborback.business.facade.Base.BaseFacade;
import com.example.buensaborback.domain.dtos.EmpresaLargeDto;

public interface EmpresaFacade extends BaseFacade<EmpresaLargeDto, Long> {
    // Agrega una sucursal a una empresa existente.
    EmpresaLargeDto addSucursal(Long idEmpresa, Long idSucursal);
}
