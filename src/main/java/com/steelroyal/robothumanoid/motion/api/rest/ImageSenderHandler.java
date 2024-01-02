package com.steelroyal.robothumanoid.motion.api.rest;

import com.steelroyal.robothumanoid.motion.domain.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class ImageSenderHandler extends BinaryWebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(ImageSenderHandler.class);
    private final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    // Constructor generado automáticamente por Lombok
    public ImageSenderHandler(ImageService imageService) {
        imageService.setOnImageProcessed(this::sendImageToClients);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
        //logger.warn("Nueva conexión WebSocket establecida: {}", session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
        //logger.warn("Conexión WebSocket cerrada: {}", session.getId());
    }
    private void sendImageToClients(BufferedImage image) {
        //logger.warn("Enviando imagen a {} clientes", sessions.size());
        try {
            byte[] imageBytes = convertBufferedImageToBytes(image);

            for (WebSocketSession session : sessions) {
                if (session.isOpen()) {
                    //logger.warn("Enviando imagen al cliente: {}", session.getId());
                    session.sendMessage(new BinaryMessage(imageBytes));
                }
            }
        } catch (IOException e) {
            logger.error("Error en la conversion o envio de la imagen", e);
        }
    }
    private byte[] convertBufferedImageToBytes(BufferedImage image) throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(image, "JPEG", baos);
            return baos.toByteArray();
        }
    }
}
