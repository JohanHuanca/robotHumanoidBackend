package com.steelroyal.robothumanoid.motion.api.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.steelroyal.robothumanoid.motion.domain.service.ServoService;
import com.steelroyal.robothumanoid.motion.mapping.PageableMapper;
import com.steelroyal.robothumanoid.motion.mapping.ServoMapper;
import com.steelroyal.robothumanoid.motion.resource.CreateServoResource;
import com.steelroyal.robothumanoid.motion.resource.PageableAllRequest;
import com.steelroyal.robothumanoid.motion.resource.ServoResource;
import com.steelroyal.robothumanoid.motion.resource.UpdateServoResource;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
@RequiredArgsConstructor
public class ServoHandler extends TextWebSocketHandler {
    private final ServoService servoService;
    private final ServoMapper servoMapper;
    private final PageableMapper pageableMapper;
    private final ObjectMapper objectMapper;
    private final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
    }
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
    }
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            JSONObject jsonMessage = new JSONObject(message.getPayload());
            String requestType = jsonMessage.getString("requestType");
            JSONObject content = jsonMessage.getJSONObject("content");

            switch (requestType) {
                case "getAll":
                    handleGetAll(session, content);
                    break;
                case "create":
                    handleCreate(session, content);
                    break;
                case "update":
                    handleUpdate(session, content);
                    break;
                case "delete":
                    handleDelete(session, content);
                    break;
                default:
                    sendErrorMessage(session, "Acción no reconocida");
                    break;
            }
        } catch (Exception  e){
            sendErrorMessage(session, "Ocurrió un error en el servidor");
        }
    }
    private void handleDelete(WebSocketSession session, JSONObject content) throws Exception {
        // Validación de datos de entrada
        if (!content.has("id")) {
            sendErrorMessage(session, "Falta el campo id");
            return;
        }

        if (servoService.delete(content.getInt("id"))) {
            // Construir y enviar respuesta de éxito
            JSONObject response = new JSONObject();
            response.put("message", "Botón eliminado exitosamente");
            session.sendMessage(new TextMessage(response.toString()));
        } else {
            sendErrorMessage(session, "Botón no encontrado");
        }
    }
    private void handleCreate(WebSocketSession session, JSONObject content) throws Exception {
        // Validación de datos de entrada
        if (!content.has("angle")) {
            sendErrorMessage(session, "Falta el campo state");
            return;
        }

        // Lógica de creación
        ServoResource createdResource  = servoMapper.toResource(servoService.create(servoMapper.toModel(CreateServoResource.builder()
                .angle(content.getInt("angle"))
                .build())));

        // Construir y enviar respuesta
        session.sendMessage(new TextMessage(objectMapper.writeValueAsString(createdResource)));
    }
    private void handleUpdate(WebSocketSession session, JSONObject content) throws Exception {
        // Validación de datos de entrada
        if (!content.has("id") || !content.has("angle")) {
            sendErrorMessage(session, "Falta el campo id o state");
            return;
        }

        // Lógica de actualizacion
        ServoResource updatedResource = servoMapper.toResource(servoService.update(servoMapper.toModel(UpdateServoResource.builder()
                .id(content.getInt("id"))
                .angle(content.getInt("angle"))
                .build())));

        // Construir y enviar respuesta
        session.sendMessage(new TextMessage(objectMapper.writeValueAsString(updatedResource)));
    }
    private void handleGetAll(WebSocketSession session, JSONObject content) throws Exception {
        // Validación de datos de entrada
        if (!content.has("page") || !content.has("size") || !content.has("sortBy") || !content.has("sortDirection")) {
            sendErrorMessage(session, "Falta el campo page, size, sortBy o sortDirection");
            return;
        }

        //logica de retorno
        Page<ServoResource> allResources = servoMapper.toResourcePage(servoService.getAll(pageableMapper.toModel(PageableAllRequest.builder()
                .page(content.getInt("page"))
                .size(content.getInt("size"))
                .sortBy(content.getString("sortBy"))
                .sortDirection(content.getString("sortDirection"))
                .build())));

        // Construir y enviar respuesta
        session.sendMessage(new TextMessage(objectMapper.writeValueAsString(allResources)));
    }
    private void sendErrorMessage(WebSocketSession session, String message) throws IOException {
        JSONObject errorResponse = new JSONObject();
        errorResponse.put("error", message);
        session.sendMessage(new TextMessage(errorResponse.toString()));
    }
}