/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petstar.model;

import java.util.List;
import org.petstar.dto.imagenDTO;

/**
 *
 * @author Ramiro
 */
public class ProtectorPantallaJson {
    private imagenDTO imagen;
    private List<imagenDTO> listImagen;

    public imagenDTO getImagen() {
        return imagen;
    }

    public void setImagen(imagenDTO imagen) {
        this.imagen = imagen;
    }

    public List<imagenDTO> getListImagen() {
        return listImagen;
    }

    public void setListImagen(List<imagenDTO> listImagen) {
        this.listImagen = listImagen;
    }        
}
