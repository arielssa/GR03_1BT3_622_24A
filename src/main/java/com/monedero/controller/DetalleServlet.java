package com.monedero.controller;

import com.monedero.dao.CuentaDAO;
import com.monedero.dao.EtiquetaDAO;
import com.monedero.model.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/detalleCuenta")
public class DetalleServlet extends HttpServlet {
    private CuentaDAO cuentaDAO;
    private EtiquetaDAO etiquetaDAO;

    @Override
    public void init() {
        cuentaDAO = new CuentaDAO();
        etiquetaDAO = new EtiquetaDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the cuentaId from the request
        String cuentaIdParam = request.getParameter("cuentaId");
        if (cuentaIdParam != null) {
            try {
                int cuentaId = Integer.parseInt(cuentaIdParam);
                // Find the account using CuentaDAO
                Cuenta cuenta = cuentaDAO.findById(cuentaId);
                if (cuenta != null) {
                    // Set the account as blocked
                    cuenta.setBloqueada(!cuenta.isBloqueada());
                    boolean nuevoEstado = cuenta.isBloqueada();
                    String mensaje = nuevoEstado ? "La cuenta ha sido bloqueada." : "La cuenta ha sido desbloqueada.";
                    request.getSession().setAttribute("mensaje", mensaje);
                    // Update the account in the database
                    cuentaDAO.update(cuenta);
                    // Redirect to the detalleCuenta.jsp page with the cuentaId parameter
                    response.sendRedirect("detalleCuenta?cuentaId=" + cuentaId);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Cuenta no encontrada.");
                }
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de cuenta inválido.");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de cuenta no proporcionado.");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener parámetros desde el formulario
        String cuentaIdParam = request.getParameter("cuentaId");

        if (cuentaIdParam != null) {
            int cuentaId = Integer.parseInt(cuentaIdParam);
            Cuenta cuenta = cuentaDAO.findById(cuentaId); // Método para obtener la cuenta

            if (cuenta != null) {
                request.setAttribute("cuenta", cuenta);
                request.setAttribute("cuentaId", cuentaId);
                Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
                List<Etiqueta> etiquetas = etiquetaDAO.findByUsuario(usuario);
                request.setAttribute("etiquetas", etiquetas);
                request.getRequestDispatcher("detalleCuenta.jsp").forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Cuenta no encontrada.");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de cuenta no proporcionado.");
        }
    }

}
