package com.example.buensaborback.presentation.rest;

import com.example.buensaborback.business.facade.EmpleadoFacade;
import com.example.buensaborback.domain.dtos.EmpleadoDto;
import com.example.buensaborback.business.service.Imp.EmpleadoServiceImpl;
import com.example.buensaborback.presentation.rest.Base.BaseController;
import com.example.buensaborback.presentation.rest.Base.BaseControllerImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "api/empleados")
public class EmpleadoController extends BaseControllerImpl<EmpleadoDto, Long, EmpleadoFacade> implements BaseController<EmpleadoDto, Long> {

    protected EmpleadoFacade empleadoFacade;

    public EmpleadoController(EmpleadoFacade empleadoFacade) {
        super(empleadoFacade);
        this.empleadoFacade = empleadoFacade;
    }

    @GetMapping("/filter")
    @PreAuthorize("hasAnyRole('CAJERO', 'COCINERO', 'ADMIN', 'SUPERADMIN', 'DELIVERY')")
    public ResponseEntity<?> findByEmail(@RequestParam String email) {
        try {
            EmpleadoDto empleado = empleadoFacade.findByEmail(email);
            return ResponseEntity.ok(empleado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}

