package com.example.buensaborback.utils;

import com.example.buensaborback.domain.entities.Localidad;
import com.example.buensaborback.domain.entities.Pais;
import com.example.buensaborback.domain.entities.Provincia;
import com.example.buensaborback.repositories.LocalidadRepository;
import com.example.buensaborback.repositories.PaisRepository;
import com.example.buensaborback.repositories.ProvinciaRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
class LocalidadesDownloader implements CommandLineRunner {

    @Autowired
    private LocalidadRepository localidadRepository;

    @Autowired
    private ProvinciaRepository provinciaRepository;

    @Autowired
    private PaisRepository paisRepository;

    @Override
    public void run(String... args) {
        // Crear una instancia de RestTemplate para hacer solicitudes HTTP
        RestTemplate restTemplate = new RestTemplate();

        // Realizar una solicitud GET a la URL especificada y obtener la respuesta como String
        String jsonResponse = restTemplate.getForObject("https://infra.datos.gob.ar/georef/departamentos.json", String.class);

        // Convertir la respuesta JSON en un objeto JSONObject
        JSONObject jsonObject = new JSONObject(jsonResponse);

        // Obtener el array de departamentos del objeto JSON
        JSONArray departamentosArray = jsonObject.getJSONArray("departamentos");

        // Obtener el país de la base de datos o crearlo si no existe
        Pais pais = paisRepository.findById(1L).orElseGet(() -> {
            Pais newPais = new Pais();
            newPais.setId(1L);
            newPais.setNombre("Argentina");
            return paisRepository.save(newPais);
        });

        // Iterar sobre cada departamento en el array
        departamentosArray.forEach(obj -> {
            JSONObject departamentoJson = (JSONObject) obj;

            // Obtener y convertir los datos de cada departamento
            Long localidadId = Long.parseLong(departamentoJson.getString("id"));
            String localidadNombre = departamentoJson.getString("nombre");

            // Obtener y convertir los datos de la provincia correspondiente
            JSONObject provinciaJson = departamentoJson.getJSONObject("provincia");
            Long provinciaId = Long.parseLong(provinciaJson.getString("id"));
            String provinciaNombre = provinciaJson.getString("nombre");

            // Verificar si la provincia ya existe por nombre, si no, crearla y guardarla
            Provincia provincia = provinciaRepository.findByNombre(provinciaNombre);
            if (provincia == null) {
                provincia = new Provincia();
                provincia.setId(provinciaId);
                provincia.setNombre(provinciaNombre);
                provincia.setPais(pais);
                provincia = provinciaRepository.save(provincia);
            }

            // Crear y guardar la localidad con los datos obtenidos
            Localidad localidad = new Localidad();
            localidad.setId(localidadId);
            localidad.setNombre(localidadNombre);
            localidad.setProvincia(provincia);
            localidadRepository.save(localidad);
        });
    }
}
