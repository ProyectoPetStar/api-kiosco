/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petstar.configurations;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ramiro
 */
public class Utils {

    public static java.sql.Date convertStringToSql(String fecha) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date parsed = format.parse(fecha);
        java.sql.Date sql = new java.sql.Date(parsed.getTime());
        return sql;
    }

    public static String convertSqlToDay(java.sql.Date fecha) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String sFecha = format.format(fecha);

        return sFecha;
    }

    public static java.sql.Date sumarFechasDias(java.sql.Date fch, int dias) {
        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(fch.getTime());
        cal.add(Calendar.DATE, dias);
        return new java.sql.Date(cal.getTimeInMillis());
    }

    /**
     * Metodo que codifica un archivo a base 64
     *
     * @param file
     * @return
     */
    public static String encodeFileToBase64(File file) throws IOException {
        String encodedfile = null;
        try {
            FileInputStream fileInputStreamReader = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            fileInputStreamReader.read(bytes);
            encodedfile = new String(Base64.getEncoder().encode(bytes), "UTF-8");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return encodedfile;
    }

    public static java.sql.Date getCurrentDate() {
        java.sql.Date fecha = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        return fecha;
    }

    public static java.sql.Date getCurrentDate(SimpleDateFormat formatoFecha) {
        java.util.Date fechaActual = new Date();

        java.sql.Date fecha = null;
        try {
            fecha = Utils.convertStringToSql(formatoFecha.format(fechaActual));
        } catch (ParseException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fecha;
    }
}
