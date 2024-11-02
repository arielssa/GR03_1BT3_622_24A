package com.monedero.controller;

import com.monedero.dao.CuentaDAO;
import com.monedero.dao.IngresoDAO;
import com.monedero.model.Cuenta;
import com.monedero.model.Ingreso;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/ingreso")
public class IngresoServlet extends HttpServlet {
    private IngresoDAO ingresoDAO;
    private CuentaDAO cuentaDAO;

    @Override
    public void init() {
        ingresoDAO = new IngresoDAO();
        cuentaDAO = new CuentaDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("create".equals(action)) {
            int cuentaId = Integer.parseInt(request.getParameter("cuentaId"));
            double valor = Double.parseDouble(request.getParameter("valor"));
            String concepto = request.getParameter("concepto");
            String categoria = request.getParameter("categoria");

            Cuenta cuentaDestino = cuentaDAO.findById(cuentaId);
            Ingreso ingreso = new Ingreso();
            ingreso.setCuentaDestino(cuentaDestino);
            ingreso.setValor(valor);
            ingreso.setConcepto(concepto);
            ingreso.setCategoria(categoria);
            ingreso.setFecha(LocalDateTime.now());


            ingreso.realizarTransaccion();

            ingresoDAO.save(ingreso);
            cuentaDAO.update(cuentaDestino);


            response.sendRedirect("cuenta");
        }
    }
}
