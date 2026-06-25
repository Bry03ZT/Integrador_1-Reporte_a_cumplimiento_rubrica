/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import config.Conexion;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.time.LocalDateTime;
/**
 *
 * @author bryan
 */
@WebServlet("/HealthServlet")
public class HealthServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String dbStatus = "OK";
        String appStatus = "UP";
        try (Connection conn = Conexion.getConexion()) {
            if (conn == null || conn.isClosed()) {
                dbStatus = "DOWN";
                appStatus = "DEGRADED";
            }
        } catch (Exception e) {
            dbStatus = "DOWN - " + e.getMessage();
            appStatus = "DEGRADED";
        }
        PrintWriter out = response.getWriter();
        out.print("{\"status\": \"" + appStatus + "\",\"database\": \"" + dbStatus + "\",\"timestamp\": \"" + LocalDateTime.now() + "\"}");
    }
}