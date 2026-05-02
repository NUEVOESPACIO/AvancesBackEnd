
package com.mycompany.mavenproject4.utils;
import javax.imageio.*;
import javax.imageio.stream.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;



public class comprimirPNGtoBytes {

    public static byte[] comprimirPNG (byte[] imagenBytes, float calidad) throws IOException {

        // 1. byte[] → BufferedImage
        ByteArrayInputStream bais = new ByteArrayInputStream(imagenBytes);
        BufferedImage imagen = ImageIO.read(bais);

        if (imagen == null) {
            throw new IOException("Formato de imagen no soportado o datos corruptos");
        }

        // 2. Preparar salida
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // Obtener escritor para PNG
        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("png");
        if (!writers.hasNext()) {
            throw new IllegalStateException("No hay writers para PNG disponibles");
        }

        ImageWriter writer = writers.next();
        ImageOutputStream ios = ImageIO.createImageOutputStream(baos);
        writer.setOutput(ios);

        // 3. Configurar compresión PNG
        ImageWriteParam param = writer.getDefaultWriteParam();
        if (param.canWriteCompressed()) {
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(calidad); // 0.0f a 1.0f (ajustar según sea necesario)
        }

        // 4. Escribir la imagen comprimida
        writer.write(null, new IIOImage(imagen, null, null), param);

        // Limpiar recursos
        ios.close();
        writer.dispose();

        // 5. BufferedImage → byte[]
        return baos.toByteArray();
    }
}