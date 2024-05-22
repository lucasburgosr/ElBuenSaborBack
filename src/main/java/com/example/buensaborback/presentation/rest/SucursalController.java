package com.example.buensaborback.presentation.rest;

import com.example.buensaborback.business.facade.SucursalFacade;
import com.example.buensaborback.domain.dtos.SucursalDto;
import com.example.buensaborback.presentation.rest.Base.BaseController;
import com.example.buensaborback.presentation.rest.Base.BaseControllerImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "api/sucursales")
public class SucursalController extends BaseControllerImpl<SucursalDto, Long, SucursalFacade> implements BaseController<SucursalDto, Long> {

    protected SucursalFacade sucursalFacade;

    public SucursalController(SucursalFacade sucursalFacade) {
        super(sucursalFacade);
        this.sucursalFacade = sucursalFacade;
    }
}

