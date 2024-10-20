package com.monedero.controller;

import com.monedero.dao.CuentaDAO;
import com.monedero.model.Egreso;
import com.monedero.model.GestorTransacciones;
import com.monedero.model.Ingreso;
import com.monedero.model.Transferencia;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/movimientos")
public class MovimientosServlet extends HttpServlet {
    private CuentaDAO cuentaDAO;
    GestorTransacciones gestorTransacciones;

    @Override
    public void init() {
        cuentaDAO = new CuentaDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener parámetros desde el formulario
        int cuentaId = Integer.parseInt(request.getParameter("cuentaId"));
        String action = request.getParameter("action");

        LocalDate fechaInicio = LocalDate.parse(request.getParameter("fechaInicio"));
        LocalDate fechaFin = LocalDate.parse(request.getParameter("fechaFin"));

        gestorTransacciones = new GestorTransacciones(cuentaDAO.findById(cuentaId));

        request.setAttribute("cuentaId", cuentaId);


        if ("ingresos".equals(action)) {
            // Obtener los ingresos en el rango de fechas
            List<Ingreso> ingresos = gestorTransacciones.obtenerIngresosPorFecha(fechaInicio, fechaFin);
            request.setAttribute("movimientos", ingresos);
            request.setAttribute("tipo", "Ingresos");
            request.getRequestDispatcher("mostrarMovimientos.jsp").forward(request, response);
        } else if ("egresos".equals(action)) {
            // Obtener los egresos en el rango de fechas
            List<Egreso> egresos = gestorTransacciones.obtenerEgresosPorFecha(fechaInicio, fechaFin);
            request.setAttribute("movimientos", egresos);
            request.setAttribute("tipo", "Egresos");
            request.getRequestDispatcher("mostrarMovimientos.jsp").forward(request, response);
        } else if ("transferencias".equals(action)) {
            // Obtener las transferencias en el rango de fechas
            List<Transferencia> transferencias = gestorTransacciones.obtenerTransferenciasPorFecha(fechaInicio, fechaFin);
            request.setAttribute("movimientos", transferencias);
            request.setAttribute("tipo", "Transferencias");
            request.getRequestDispatcher("mostrarMovimientos.jsp").forward(request, response);
        } else {
            response.sendRedirect("detalleCuenta?cuentaId=" + cuentaId);
        }
    }

}
