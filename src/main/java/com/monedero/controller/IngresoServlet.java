package com.monedero.controller;

import com.monedero.dao.CuentaDAO;
import com.monedero.dao.EtiquetaDAO;
import com.monedero.dao.IngresoDAO;
import com.monedero.model.Cuenta;
import com.monedero.model.Etiqueta;
import com.monedero.model.Ingreso;
import com.monedero.model.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/ingreso")
public class IngresoServlet extends HttpServlet {
    private IngresoDAO ingresoDAO;
    private CuentaDAO cuentaDAO;
    private EtiquetaDAO etiquetaDAO;

    @Override
    public void init() {
        ingresoDAO = new IngresoDAO();
        cuentaDAO = new CuentaDAO();
        etiquetaDAO = new EtiquetaDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("create".equals(action)) {
            int cuentaId = Integer.parseInt(request.getParameter("cuentaId"));
            double valor = Double.parseDouble(request.getParameter("valor"));
            String concepto = request.getParameter("concepto");
            Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
            List<Etiqueta> etiquetas = etiquetaDAO.findByUsuario(usuario);
            Etiqueta etiqueta = Etiqueta.buscarEtiquetaPorNombre(etiquetas, request.getParameter("etiqueta")).orElse(null);


            Cuenta cuentaDestino = cuentaDAO.findById(cuentaId);
            Ingreso ingreso = new Ingreso(cuentaDestino, valor, concepto, etiqueta);
            ingreso.setFecha(LocalDateTime.now());

            try {
                ingreso.realizarTransaccion();
                ingresoDAO.save(ingreso);
                cuentaDAO.update(cuentaDestino);
            } catch (Exception e){
                e.printStackTrace();
            }

            response.sendRedirect("cuenta");
        }
    }
}
