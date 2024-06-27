package com.example.buensaborback.business.facade;

import com.example.buensaborback.business.facade.Base.BaseFacade;
import com.example.buensaborback.domain.dtos.CategoriaDto;
import com.example.buensaborback.domain.dtos.EmpresaLargeDto;
import com.example.buensaborback.domain.dtos.SucursalDto;

import java.util.List;

public interface SucursalFacade extends BaseFacade<SucursalDto, Long> {
    //Obtiene una lista de todas las sucursales asociadas a una empresa específica.
    public List<SucursalDto> obtenerSucursalesPorIdEmpresa(Long idEmpresa);
    //Obtiene los detalles de la empresa asociada a una sucursal específica.
    public EmpresaLargeDto obtenerEmpresaPorSucursalId(Long id);
    //Encuentra todas las categorías asociadas a una sucursal específica.
    public List<CategoriaDto> findCategoriasBySucursalId(Long id);
}
