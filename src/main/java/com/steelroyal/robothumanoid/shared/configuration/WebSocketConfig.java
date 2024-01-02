package com.steelroyal.robothumanoid.shared.configuration;

import com.steelroyal.robothumanoid.motion.api.rest.ButtonHandler;
import com.steelroyal.robothumanoid.motion.api.rest.ImageReceiverHandler;
import com.steelroyal.robothumanoid.motion.api.rest.ImageSenderHandler;
import com.steelroyal.robothumanoid.motion.api.rest.ServoHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private final ButtonHandler buttonHandler;
    private final ServoHandler servoHandler;
    private final ImageReceiverHandler imageReceiverHandler;
    private final ImageSenderHandler imageSenderHandler;
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
                .addHandler(buttonHandler, "/api/v1/buttons")
                .addHandler(servoHandler, "/api/v1/servos")
                .addHandler(imageReceiverHandler, "/api/v1/images/receive")
                .addHandler(imageSenderHandler, "/api/v1/images/send")
                .setAllowedOriginPatterns("*");
    }

    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(8000); // Tamaño máximo para mensajes de texto
        container.setMaxBinaryMessageBufferSize(16000); // Tamaño máximo para mensajes binarios
        return container;
    }
}
