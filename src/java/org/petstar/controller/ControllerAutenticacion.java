/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petstar.controller;

import java.security.Key;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.TextCodec;
import io.jsonwebtoken.impl.crypto.MacProvider;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.petstar.configurations.Configuration;
import org.petstar.dao.AutenticacionDAO;
import org.petstar.dto.UserDTO;


/**
 * Controllador de Autenticación
 * @author Tech-Pro
 */
public class ControllerAutenticacion {

    /**
     * Creación de token
     * Metodo que se encarga de la construcción del token y almacenamiento del key
     * @param usuario
     * @return
     * @throws UnsupportedEncodingException
     * @throws Exception 
     */
    public String createJWT(UserDTO usuario) throws UnsupportedEncodingException, Exception {
        Key key = MacProvider.generateKey();
        byte[] keyBytes = key.getEncoded();
        String key_base64 = TextCodec.BASE64.encode(keyBytes);
        AutenticacionDAO dao = new AutenticacionDAO();

        String jwt = Jwts.builder()
                .setSubject(String.valueOf(usuario.getId_usuario_kiosko()))
                .setExpiration(new Date(System.currentTimeMillis() + 32400000L))
                .claim("id_usuario", usuario.getId_usuario_kiosko())
                .claim("nombre_usuario", usuario.getNombre_usuario())
                .claim("usuario",usuario.getUsuario())
                .claim("id_pefil", usuario.getId_perfil())
                .claim("imagen", usuario.getImagen_administrador())
                .signWith(
                        SignatureAlgorithm.HS256,
                        key_base64
                )
                .compact();
        dao.updateToken_Key(usuario.getId_usuario_kiosko(), key_base64);
        return jwt;
    }

    /**
     * Validar token
     * Metodo que se encarga de la validación del token, que no haya sido alterado
     * @param request
     * @return
     */
    public UserDTO isValidToken(HttpServletRequest request) {
        UserDTO usuario = new UserDTO();
        String token = request.getHeader(Configuration.HEADER_STRING);
        int idAcceso = Integer.parseInt(request.getParameter("id_usuario"));
        if (token != null) {
            try {
                AutenticacionDAO dao = new AutenticacionDAO();
                String token_key = dao.getToken_Key(idAcceso);
                Claims claims = Jwts.parser().setSigningKey(token_key).parseClaimsJws(token).getBody();
                
                usuario.setId_usuario_kiosko(claims.get("id_usuario", Integer.class));
                usuario.setId_perfil(claims.get("id_pefil", Integer.class));
                usuario.setImagen_administrador(claims.get("imagen", String.class));
                usuario.setNombre_usuario(claims.get("nombre_usuario", String.class));
                usuario.setUsuario(claims.get("usuario", String.class));
            } catch (SignatureException | ClaimJwtException | MalformedJwtException | UnsupportedJwtException e) {
                usuario = null;
            } catch (Exception ex) {
                usuario = null;
            }
        } else {
            usuario = null;
        }
        return usuario;
    }
}
