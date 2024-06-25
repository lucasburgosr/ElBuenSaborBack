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
    public SucursalServiceImpl(SucursalRepository sucursalRepository) {
        super(sucursalRepository);
        this.sucursalRepository = sucursalRepository;
    }

    @Override
    public Sucursal guardarSucursal(Sucursal sucursal) {
        return sucursalRepository.save(sucursal);
    }

    @Override
    public Sucursal actualizarSucursal(Long id, Sucursal sucursal) {
        return sucursalRepository.save(sucursal);
    }

    @Override
    public List<Sucursal> obtenerSucursalesPorIdEmpresa(Long idEmpresa) {
        return sucursalRepository.findAllByEmpresaId(idEmpresa);
    }

    @Override
    public List<Categoria> findCategoriasBySucursalId(Long sucursalId) {
        var sucursalExiste = sucursalRepository.findById(sucursalId);

        if(sucursalExiste.isEmpty()){
            throw new RuntimeException("Sucursal no encontrada: { id: " + sucursalId + " }");
        }

        List<Categoria> categorias = sucursalRepository.findCategoriasBySucursalId(sucursalId);
        Set<Categoria> filteredCategorias = new HashSet<>();
        for (Categoria categoria: categorias){
            if (categoria.getCategoriaPadre() == null) {
                filteredCategorias.add(categoria);
            }
        }
        filterSubcategorias(filteredCategorias, categorias);

        return new ArrayList<>(filteredCategorias);
    }

    public void filterSubcategorias(Set<Categoria> categorias, List<Categoria> categoriasBySucursal) {
        for (Categoria categoria : categorias) {
            System.out.println(categoria.getDenominacion());
            Set<Categoria> subcategorias = categoria.getSubCategorias();
            if (!subcategorias.isEmpty()) {
                Set<Categoria> filteredSubcategorias = new HashSet<>();
                for (Categoria subcategoria: subcategorias){
                    System.out.println(subcategoria.getDenominacion());
                    if (categoriasBySucursal.contains(subcategoria)) {
                        System.out.println("está en sucursal");
                        filteredSubcategorias.add(subcategoria);
                    }
                }
                categoria.setSubCategorias(filteredSubcategorias);
                filterSubcategorias(filteredSubcategorias, categoriasBySucursal);
            }

        }
    }

    @Override
    public Empresa obtenerEmpresaPorSucursalId(Long idSucursal) {
        Sucursal sucursal = sucursalRepository.findById(idSucursal).orElseThrow();
        return sucursal.getEmpresa();
    }


}
