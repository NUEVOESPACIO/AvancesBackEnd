package com.mycompany.mavenproject4.servicios;

import com.mycompany.mavenproject4.entidades.Foto;
import com.mycompany.mavenproject4.repository.FotoRepository;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Isolation;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FotoService {

    @Autowired
    private FotoRepository fotoRepository;

    public Foto guardarFoto(
            MultipartFile file,
            String descripcion,
            String clasificacion,
            String tablaasociada,
            Long idasociado
    ) throws IOException {

        if (file == null || file.isEmpty()) {
            throw new RuntimeException("Archivo vacío");
        }

        String contentType = file.getContentType();

        if (contentType == null
                || (!contentType.equals("image/png")
                && !contentType.equals("image/jpeg"))) {
            throw new RuntimeException("Solo se permiten imágenes PNG o JPG");
        }

        byte[] imagenFinal;

        // 🔥 Comprimir solo JPEG
        if (contentType.equals("image/jpeg")) {
            imagenFinal = comprimirJpeg(file, 0.7f);
        } else {
            // PNG no se recomprime (mantiene calidad y transparencia)
            imagenFinal = file.getBytes();
        }

        Foto foto = new Foto();
        foto.setArchivo(imagenFinal);
        foto.setMimeType(contentType);   // 👈 CLAVE
        foto.setDescripcion(descripcion);
        foto.setClasificacion(clasificacion);
        foto.setTablaasociada(tablaasociada);
        foto.setIdasociado(idasociado);

        return fotoRepository.save(foto);
    }

    // 🔥 Compresión JPEG optimizada
    private byte[] comprimirJpeg(MultipartFile file, float calidad) throws IOException {

        BufferedImage imagen = ImageIO.read(file.getInputStream());

        if (imagen == null) {
            throw new RuntimeException("Imagen inválida");
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        ImageWriter writer = ImageIO
                .getImageWritersByFormatName("jpg")
                .next();

        ImageWriteParam param = writer.getDefaultWriteParam();

        if (param.canWriteCompressed()) {
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(calidad);
        }

        ImageOutputStream ios = ImageIO.createImageOutputStream(baos);
        writer.setOutput(ios);

        writer.write(null, new IIOImage(imagen, null, null), param);

        writer.dispose();
        ios.close();

        return baos.toByteArray();
    }

    // 🔥 Buscar por ID (lo usa el controller /ver/{id})
    public Foto buscarPorId(Long id) {
        return fotoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Foto no encontrada"));
    }

    public List<Foto> buscarFotos(
            String tablaasociada,
            Long idasociado,
            String clasificacion
    ) {
        return fotoRepository
                .findByTablaasociadaAndIdasociadoAndClasificacion(
                        tablaasociada,
                        idasociado,
                        clasificacion
                );
    }

    public void eliminarFoto(Long id) {

        if (!fotoRepository.existsById(id)) {
            throw new RuntimeException("Foto no encontrada");
        }

        fotoRepository.deleteById(id);
    }

    public Foto actualizarDescripcion(Long id, String nuevaDescripcion) {

        Foto foto = fotoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Foto no encontrada"));

        foto.setDescripcion(nuevaDescripcion);

        return fotoRepository.save(foto);
    }

    public List<Foto> obtenerTodasLasFotos() {
        return fotoRepository.findAll();
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Foto reemplazarFoto(
            MultipartFile file,
            String descripcion,
            String clasificacion,
            String tablaasociada,
            Long idasociado
    ) throws IOException {

        // 1️⃣ Delete previous ones
        fotoRepository
                .deleteByTablaasociadaAndIdasociadoAndClasificacion(
                        tablaasociada,
                        idasociado,
                        clasificacion
                );

        // 2️⃣ Save new one
        return guardarFoto(
                file,
                descripcion,
                clasificacion,
                tablaasociada,
                idasociado
        );
    }
}
