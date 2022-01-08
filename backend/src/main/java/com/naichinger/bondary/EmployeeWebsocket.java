package com.naichinger.bondary;

import com.naichinger.control.EmployeeRepository;
import io.vertx.core.json.JsonObject;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.bind.JsonbBuilder;
import javax.websocket.OnOpen;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.List;

@ServerEndpoint(value = "/employees")
@ApplicationScoped
public class EmployeeWebsocket {
    @Inject
    EmployeeRepository employeeRepository;

    List<Session> sessions = new ArrayList<>();

    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
        new Thread(() -> {
            String employeeJson = JsonbBuilder.create().toJson(employeeRepository.findAll());
            sendEmployeeToSession(session, employeeJson);
        }).start();
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
    }

    @OnMessage
    public void onMessage(String message) {
        JsonObject jsonObject = new JsonObject(message);
        String action = jsonObject.getString("action");
        switch (action) {
            case "remove": {
                Long id = jsonObject.getLong("id");
                new Thread(() -> {
                    employeeRepository.removeEmployee(id);
                    sendAllEmployees();
                }).start();
                break;
            }
        }
        System.out.println(message);
    }

    public void sendAllEmployees() {
        String employeeJson = JsonbBuilder.create().toJson(employeeRepository.findAll());
        System.out.println(employeeJson);
        sessions.forEach(s -> sendEmployeeToSession(s, employeeJson));
    }

    private void sendEmployeeToSession(Session session, String employeeJsonArray) {
        session.getAsyncRemote().sendText(employeeJsonArray, result -> {
            if (result.getException() != null) {
                System.out.println("Unable to send message: " + result.getException());
            }
        });
    }
}
