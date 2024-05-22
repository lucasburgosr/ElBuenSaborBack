package com.example.buensaborback.business.service;


import com.example.buensaborback.business.service.Base.BaseService;
import com.example.buensaborback.domain.entities.Sucursal;

public interface SucursalService extends BaseService<Sucursal, Long> {
    public Sucursal guardarSucursal(Sucursal sucursal);
    public Sucursal actualizarSucursal(Long id, Sucursal sucursal);
}
