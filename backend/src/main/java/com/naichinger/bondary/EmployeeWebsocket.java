package com.naichinger.bondary;

import com.naichinger.control.EmployeeRepository;
import com.naichinger.entity.Employee;
import io.vertx.core.json.JsonObject;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.websocket.OnOpen;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

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
            JsonArray arr = convertEmployeeListToJson(employeeRepository.findAll());
            sendEmployeeToSession(session, arr.toString());
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

    private JsonArray convertEmployeeListToJson(List<Employee> employees) {
        JsonArrayBuilder jsonArray = Json.createArrayBuilder();

        employees.forEach(e -> {
            JsonObjectBuilder jsonObj = Json.createObjectBuilder();
            jsonObj.add("id", e.getId());
            jsonObj.add("firstname", e.getFirstname());
            jsonObj.add("lastname", e.getLastname());
            jsonArray.add(jsonObj);
        });
        return jsonArray.build();
    }

    public void sendAllEmployees() {
        JsonArray arr = convertEmployeeListToJson(employeeRepository.findAll());
        System.out.println(arr.toString());
        sessions.forEach(s -> sendEmployeeToSession(s, arr.toString()));
    }

    private void sendEmployeeToSession(Session session, String employeeJsonArray) {
        session.getAsyncRemote().sendText(employeeJsonArray, result -> {
            if (result.getException() != null) {
                System.out.println("Unable to send message: " + result.getException());
            }
        });
    }
}
