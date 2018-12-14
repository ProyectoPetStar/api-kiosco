/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petstar.service;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.Iterator;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.OnOpen;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import org.petstar.dto.Message;

/**
 *
 * @author Tech-Pro
 */
@ServerEndpoint("/P3t5t4rWs/{role}")
public class SocketServer {

    @OnOpen
    public void onOpen(@PathParam("role") String role, Session session) {
    }

    @OnClose
    public void onClose(Session session) {
        //System.out.println("onClose::" +  session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session) {

        Gson g = new Gson();
        Message mensaje = g.fromJson(message, Message.class);
        if ("info_dashboard".equals(mensaje.getType())) {
            Iterator<Session> it = session.getOpenSessions().iterator();
            
            int kioscos_now = 0;
            while (it.hasNext()) {
                
                Session session_index = it.next();
                String rol = session_index.getPathParameters().get("role");
                
                if ("KIOSCO".equals(rol)) {
                    kioscos_now++;
                }
            }
            
            Iterator<Session> admin_session =  session.getOpenSessions().iterator();
            while (admin_session.hasNext()) {
                Session session_index = admin_session.next();
                String rol = session_index.getPathParameters().get("role");
                
                if ("ADMIN".equals(rol)) {
                    session_index.getAsyncRemote().sendText(String.valueOf(kioscos_now));
                }
            }
            
        } else if ("".equals(mensaje.getType())) {
            
        }
    }

    @OnError
    public void onError(Throwable t) {
        System.out.println("onError::" + t.getMessage());
    }

}
