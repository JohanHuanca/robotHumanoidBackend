package com.steelroyal.robothumanoid.motion.service;

import com.steelroyal.robothumanoid.motion.domain.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.function.Consumer;

@Service
public class ImageServiceImpl implements ImageService {

    private static final Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);
    private Consumer<BufferedImage> onImageProcessed;

    @Override
    public void processImage(BufferedImage image) {
        logger.warn("Procesando imagen recibida");
        if (onImageProcessed != null) {
            onImageProcessed.accept(image);
            logger.warn("Imagen procesada y aceptada por el consumidor");
        } else {
            logger.error("No hay consumidor configurado para procesar la imagen");
        }
    }


    @Override
    public void setOnImageProcessed(Consumer<BufferedImage> onImageProcessed) {
        this.onImageProcessed = onImageProcessed;
        logger.warn("Consumidor de imágenes procesadas configurado");
    }
}