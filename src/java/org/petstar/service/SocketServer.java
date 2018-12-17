/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petstar.service;


import java.util.ArrayList;
import java.util.Iterator;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.OnOpen;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;



/**
 *
 * @author Tech-Pro
 */
@ServerEndpoint("/P3t5t4rWs/{role}")
public class SocketServer {

    @OnOpen
    public void onOpen(@PathParam("role") String role, Session session) {

        System.out.println("");
    }

    @OnClose
    public void onClose(Session session) {
        this.onMessage("close session",session);
    }

    @OnMessage
    public void onMessage(String mensaje, Session session) {
        ArrayList<Object> response = new ArrayList<>();
        ArrayList<String> kioscos_online = new ArrayList<>();
        int kioscos_using_now = 0;

        // CUENTA LOS KIOSCOS USADOS EN ESTE MOMENTO
        Iterator<Session> it = session.getOpenSessions().iterator();
        while (it.hasNext()) {

            Session session_index = it.next();
            String rol = session_index.getPathParameters().get("role");

            if ("KIOSCO_USING_NOW".equals(rol)) {
                kioscos_using_now++;
            }
        }
        
        response.add(kioscos_using_now);

       // CUENTA KIOSCOS ONLINE
        Iterator<Session> sesiones = session.getOpenSessions().iterator();

        while (sesiones.hasNext()) {
            String cadena_template = "{ip_publica:PUBLICA,ip_privada:PRIVADA}";
            Session session_index = sesiones.next();
            String rol = session_index.getPathParameters().get("role");

            if ("KIOSCO".equals(rol)) {
                String kiosco_online = cadena_template.replaceAll("PUBLICA", session_index.getPathParameters().get("publicIp")).replaceAll("PRIVADA", session_index.getPathParameters().get("privateIp"));
                kioscos_online.add(kiosco_online);
            }
        }
        
        response.add(kioscos_online);

        //ENVIA MENSAJE DE ACTUALIZACIÓN A LOS ADMINISTRADORES
        Iterator<Session> admin_session = session.getOpenSessions().iterator();
        while (admin_session.hasNext()) {
            Session session_index = admin_session.next();
            String rol = session_index.getPathParameters().get("role");

            if ("ADMIN".equals(rol)) {
                session_index.getAsyncRemote().sendText(response.toString());
            }
        }

    }

    @OnError
    public void onError(Throwable t) {
        System.out.println("onError::" + t.getMessage());
    }

}
