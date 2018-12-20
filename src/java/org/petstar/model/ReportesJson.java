/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petstar.model;

import java.util.List;
import org.petstar.dto.AplicacionDTO;
import org.petstar.dto.CatalogoPlantaDTO;
import org.petstar.dto.KioscoDTO;
import org.petstar.dto.PlantaDTO;
import org.petstar.dto.ReportesDTO;

/**
 *
 * @author Ramiro
 */
public class ReportesJson {
    private ReportesDTO reportes;
    private List<ReportesDTO> listReportes;
    private List<CatalogoPlantaDTO> listPlanta;
    private List<KioscoDTO> listKiosco;
    private AplicacionDTO aplicacion;
    private PlantaDTO planta;

    public ReportesDTO getReportes() {
        return reportes;
    }

    public void setReportes(ReportesDTO reportes) {
        this.reportes = reportes;
    }

    public List<ReportesDTO> getListReportes() {
        return listReportes;
    }

    public void setListReportes(List<ReportesDTO> listReportes) {
        this.listReportes = listReportes;
    }

    public List<CatalogoPlantaDTO> getListPlanta() {
        return listPlanta;
    }

    public void setListPlanta(List<CatalogoPlantaDTO> listPlanta) {
        this.listPlanta = listPlanta;
    }

    public List<KioscoDTO> getListKiosco() {
        return listKiosco;
    }

    public void setListKiosco(List<KioscoDTO> listKiosco) {
        this.listKiosco = listKiosco;
    }   

    public AplicacionDTO getAplicacion() {
        return aplicacion;
    }

    public void setAplicacion(AplicacionDTO aplicacion) {
        this.aplicacion = aplicacion;
    }    

    public PlantaDTO getPlanta() {
        return planta;
    }

    public void setPlanta(PlantaDTO planta) {
        this.planta = planta;
    }    
}
