package com.example.buensaborback.repositories;

import com.example.buensaborback.domain.entities.Empresa;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmpresaRepository extends BaseRepository<Empresa,Long> {

}
