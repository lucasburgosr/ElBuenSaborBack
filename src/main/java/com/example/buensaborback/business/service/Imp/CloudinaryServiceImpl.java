package com.example.buensaborback.business.service.Imp;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.buensaborback.business.service.CloudinaryService;
import jakarta.annotation.Resource;
import org.cloudinary.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {

    @Resource
    private Cloudinary cloudinary;

    // Método para subir un archivo a Cloudinary
    @Override
    public String uploadFile(MultipartFile file) {
        try {
            // Configurar opciones de carga si es necesario
            HashMap<Object, Object> options = new HashMap<>();

            // Subir el archivo a Cloudinary
            Map uploadedFile = cloudinary.uploader().upload(file.getBytes(), options);

            // Obtener el ID público del archivo subido
            String publicId = (String) uploadedFile.get("public_id");

            // Generar URL segura para el archivo subido
            return cloudinary.url().secure(true).generate(publicId);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Método para eliminar una imagen de Cloudinary
    @Override
    public ResponseEntity<String> deleteImage(String publicId, UUID idBd) {
        try {
            // Eliminar la imagen en Cloudinary
            Map response = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            JSONObject json = new JSONObject(response);

            // Verificar si la eliminación fue exitosa
            if ("ok".equals(json.getString("result"))) {
                return new ResponseEntity<>("{\"status\":\"OK\", \"message\":\"Imagen eliminada exitosamente.\"}", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("{\"status\":\"ERROR\", \"message\":\"Error al eliminar la imagen.\"}", HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("{\"status\":\"ERROR\", \"message\":\"" + e.getMessage() + "\"}", HttpStatus.BAD_REQUEST);
        }
    }
}
