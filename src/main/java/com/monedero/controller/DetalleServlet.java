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
