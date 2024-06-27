package com.example.buensaborback.business.service.Imp;

import com.example.buensaborback.business.service.CloudinaryService;
import com.example.buensaborback.business.service.ImageService;
import com.example.buensaborback.domain.entities.Image;
import com.example.buensaborback.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private ImageRepository imageRepository;

    // Método para obtener todas las imágenes
    @Override
    public ResponseEntity<List<Map<String, Object>>> getAllImages() {
        try {
            // Obtener todas las imágenes de la base de datos
            List<Image> images = imageRepository.findAll();
            List<Map<String, Object>> imageList = new ArrayList<>();

            // Iterar sobre las imágenes y crear un mapa para cada una
            for (Image image : images) {
                Map<String, Object> imageMap = new HashMap<>();
                imageMap.put("id", image.getId());
                imageMap.put("name", image.getName());
                imageMap.put("url", image.getUrl());
                imageList.add(imageMap);
            }

            // Devolver la lista de imágenes como ResponseEntity OK
            return ResponseEntity.ok(imageList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Método para subir imágenes
    @Override
    public ResponseEntity<String> uploadImages(MultipartFile[] files) {
        List<String> urls = new ArrayList<>();

        try {
            // Iterar sobre los archivos recibidos
            for (MultipartFile file : files) {
                // Verificar si el nombre del archivo está vacío
                if (file.getName().isEmpty()) {
                    return ResponseEntity.badRequest().build();
                }

                // Crear una nueva entidad de imagen y configurar el nombre y la URL
                Image image = new Image();
                image.setName(file.getName());
                image.setUrl(cloudinaryService.uploadFile(file));

                // Verificar si la carga a Cloudinary fue exitosa
                if (image.getUrl() == null) {
                    return ResponseEntity.badRequest().build();
                }

                // Guardar la imagen en la base de datos
                imageRepository.save(image);

                // Agregar la URL a la lista de URLs
                urls.add(image.getUrl());
            }

            // Convertir la lista de URLs a un JSON y devolver como ResponseEntity OK
            return new ResponseEntity<>("{\"status\":\"OK\", \"urls\":" + urls + "}", HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("{\"status\":\"ERROR\", \"message\":\"" + e.getMessage() + "\"}", HttpStatus.BAD_REQUEST);
        }
    }

    // Método para eliminar una imagen
    @Override
    public ResponseEntity<String> deleteImage(String publicId, UUID idBd) {
        try {
            // Eliminar la imagen de la base de datos por su ID
            imageRepository.deleteById(idBd);

            // Llamar al servicio de Cloudinary para eliminar la imagen de Cloudinary
            return cloudinaryService.deleteImage(publicId, idBd);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("{\"status\":\"ERROR\", \"message\":\"" + e.getMessage() + "\"}", HttpStatus.BAD_REQUEST);
        }
    }
}
