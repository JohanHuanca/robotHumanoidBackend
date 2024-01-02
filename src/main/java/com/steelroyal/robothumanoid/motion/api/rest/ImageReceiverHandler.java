package com.steelroyal.robothumanoid.motion.api.rest;

import com.steelroyal.robothumanoid.motion.domain.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ImageReceiverHandler extends BinaryWebSocketHandler {
    private static final Logger logger = LoggerFactory.getLogger(ImageReceiverHandler.class);
    private final ImageService imageService;
    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
        //logger.warn("Mensaje binario recibido de tamaño: {}", message.getPayloadLength());

        byte[] imageBytes = message.getPayload().array();
        BufferedImage image = convertToBufferedImage(imageBytes);

        if (image != null) {
            //logger.warn("Imagen convertida exitosamente, procesando imagen...");
            imageService.processImage(image);
        } else {
            logger.error("La conversión de la imagen ha fallado.");
        }
    }

    private BufferedImage convertToBufferedImage(byte[] imageBytes) {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes)) {
            BufferedImage image = ImageIO.read(bais);
            if (image == null) {
                logger.error("No se pudo convertir los bytes a una imagen. Posible formato no soportado o datos corruptos.");
            }
            return image;
        } catch (IOException e) {
            logger.error("Error al leer la imagen", e);
            return null;
        }
    }
}
