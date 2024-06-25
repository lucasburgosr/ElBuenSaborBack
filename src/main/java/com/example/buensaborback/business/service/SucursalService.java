package com.example.buensaborback.business.service;


import com.example.buensaborback.business.service.Base.BaseService;
import com.example.buensaborback.domain.entities.Categoria;
import com.example.buensaborback.domain.entities.Empresa;
import com.example.buensaborback.domain.entities.Sucursal;

import java.util.List;

public interface SucursalService extends BaseService<Sucursal, Long> {
    public Sucursal guardarSucursal(Sucursal sucursal);
    public Sucursal actualizarSucursal(Long id, Sucursal sucursal);
    public List<Sucursal> obtenerSucursalesPorIdEmpresa(Long idEmpresa);
    public Empresa obtenerEmpresaPorSucursalId(Long idSucursal);
    List<Categoria> findCategoriasBySucursalId(Long sucursalId);
}
