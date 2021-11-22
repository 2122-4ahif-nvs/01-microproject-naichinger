package com.naichinger.bondary;

import com.naichinger.control.EmployeeRepository;
import io.quarkus.narayana.jta.runtime.interceptor.TransactionalInterceptorBase;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.OnOpen;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import javax.ws.rs.Path;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/employees")
@ApplicationScoped
public class EmployeeWebsocket {
    @Inject
    EmployeeRepository employeeRepository;

    List<Session> sessions = new ArrayList<>();

    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
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
                    notifyEmployeeChange();
                }).start();
                break;
            }
        }
        System.out.println(message);
    }

    public void notifyEmployeeChange() {
        broadcast("updated");
    }

    public void broadcast(String action) {
        sessions.forEach(s -> {
            s.getAsyncRemote().sendText(action, result -> {
                if (result.getException() != null) {
                    System.out.println("Unable to send message: " + result.getException());
                }
            });
        });
    }
}
