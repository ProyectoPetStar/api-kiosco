/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petstar.configurations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Ramiro
 */
public class Utils {
    public static java.sql.Date convertStringToSql(String fecha) throws ParseException{
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date parsed = format.parse(fecha);
        java.sql.Date sql = new java.sql.Date(parsed.getTime());
        return sql;
    }
}
