package com.example.buensaborback.business.facade.Imp;

import com.example.buensaborback.business.facade.Base.BaseFacadeImpl;
import com.example.buensaborback.business.facade.EmpleadoFacade;
import com.example.buensaborback.business.mapper.BaseMapper;
import com.example.buensaborback.business.mapper.EmpleadoMapper;
import com.example.buensaborback.business.service.Base.BaseService;
import com.example.buensaborback.business.service.Imp.EmpleadoServiceImpl;
import com.example.buensaborback.domain.dtos.EmpleadoDto;
import com.example.buensaborback.domain.entities.Empleado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class EmpleadoFacadeImpl extends BaseFacadeImpl<Empleado, EmpleadoDto, Long> implements EmpleadoFacade {

    @Autowired
    private EmpleadoServiceImpl empleadoService;

    @Autowired
    private EmpleadoMapper empleadoMapper;

    @Autowired
    public EmpleadoFacadeImpl(@Qualifier("empleadoServiceImpl") BaseService<Empleado, Long> baseService, BaseMapper<Empleado, EmpleadoDto> baseMapper) {
        super(baseService, baseMapper);
    }

    public EmpleadoDto findByEmail(String email) throws Exception {
        // Busca una entidad por id
        var entity = empleadoService.findByEmail(email);
        // convierte la entidad a DTO
        return empleadoMapper.toDTO(entity);
    }
}