package com.example.buensaborback.business.service.Imp;

import com.example.buensaborback.business.service.Base.BaseServiceImpl;
import com.example.buensaborback.business.service.SucursalService;
import com.example.buensaborback.domain.entities.Categoria;
import com.example.buensaborback.domain.entities.Empresa;
import com.example.buensaborback.domain.entities.Sucursal;
import com.example.buensaborback.repositories.SucursalRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("sucursalServiceImpl")
public class SucursalServiceImpl extends BaseServiceImpl<Sucursal, Long> implements SucursalService {

    private SucursalRepository sucursalRepository;

    // Constructor para inyectar SucursalRepository y llamar al constructor de la clase base
    public SucursalServiceImpl(SucursalRepository sucursalRepository) {
        super(sucursalRepository);
        this.sucursalRepository = sucursalRepository;
    }

    // Método para guardar una sucursal
    @Override
    public Sucursal guardarSucursal(Sucursal sucursal) {
        return sucursalRepository.save(sucursal);
    }

    // Método para actualizar una sucursal
    @Override
    public Sucursal actualizarSucursal(Long id, Sucursal sucursal) {
        return sucursalRepository.save(sucursal);
    }

    // Método para obtener todas las sucursales de una empresa por su ID
    @Override
    public List<Sucursal> obtenerSucursalesPorIdEmpresa(Long idEmpresa) {
        return sucursalRepository.findAllByEmpresaId(idEmpresa);
    }

    // Método para encontrar categorías por el ID de una sucursal
    @Override
    public List<Categoria> findCategoriasBySucursalId(Long sucursalId) {
        // Verificar si la sucursal existe
        var sucursalExiste = sucursalRepository.findById(sucursalId);
        if (sucursalExiste.isEmpty()) {
            throw new RuntimeException("Sucursal no encontrada: { id: " + sucursalId + " }");
        }

        // Obtener categorías y filtrar subcategorías recursivamente
        List<Categoria> categorias = sucursalRepository.findCategoriasBySucursalId(sucursalId);
        Set<Categoria> filteredCategorias = new HashSet<>();
        for (Categoria categoria : categorias) {
            if (categoria.getCategoriaPadre() == null) {
                filteredCategorias.add(categoria);
            }
        }
        filterSubcategorias(filteredCategorias, categorias);

        return new ArrayList<>(filteredCategorias);
    }

    // Método recursivo para filtrar subcategorías
    public void filterSubcategorias(Set<Categoria> categorias, List<Categoria> categoriasBySucursal) {
        for (Categoria categoria : categorias) {
            Set<Categoria> subcategorias = categoria.getSubCategorias();
            if (!subcategorias.isEmpty()) {
                Set<Categoria> filteredSubcategorias = new HashSet<>();
                for (Categoria subcategoria : subcategorias) {
                    if (categoriasBySucursal.contains(subcategoria)) {
                        filteredSubcategorias.add(subcategoria);
                    }
                }
                categoria.setSubCategorias(filteredSubcategorias);
                filterSubcategorias(filteredSubcategorias, categoriasBySucursal);
            }
        }
    }

    // Método para obtener la empresa asociada a una sucursal por su ID
    @Override
    public Empresa obtenerEmpresaPorSucursalId(Long idSucursal) {
        Sucursal sucursal = sucursalRepository.findById(idSucursal).orElseThrow();
        return sucursal.getEmpresa();
    }
}
