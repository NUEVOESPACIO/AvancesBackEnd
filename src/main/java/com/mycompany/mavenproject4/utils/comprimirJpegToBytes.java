package com.mycompany.mavenproject4.utils;

import javax.imageio.ImageIO;
import javax.imageio.IIOImage;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.io.*;
import java.awt.image.BufferedImage;
import java.util.Iterator;

public class comprimirJpegToBytes {

    public static byte[] comprimirJpeg(byte[] imagenBytes, float calidad) throws IOException {

        // 1. byte[] → BufferedImage
        ByteArrayInputStream bais = new ByteArrayInputStream(imagenBytes);
        BufferedImage imagen = ImageIO.read(bais);

        if (imagen == null) {
            throw new IOException("Formato de imagen no soportado o datos corruptos");
        }

        // 2. Preparar salida
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
        if (!writers.hasNext()) {
            throw new IllegalStateException("No hay writers para JPG disponibles");
        }

        ImageWriter writer = writers.next();

        ImageOutputStream ios = ImageIO.createImageOutputStream(baos);
        writer.setOutput(ios);

        // 3. Configurar compresión
        ImageWriteParam param = writer.getDefaultWriteParam();
        if (param.canWriteCompressed()) {
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(calidad);
        }

        // 4. Escribir imagen comprimida
        writer.write(null, new IIOImage(imagen, null, null), param);

        ios.close();
        writer.dispose();

        // 5. BufferedImage → byte[]
        return baos.toByteArray();
    }
}