package com.monedero.controller;

import com.monedero.dao.CuentaDAO;
import com.monedero.dao.EgresoDAO;
import com.monedero.model.Cuenta;
import com.monedero.model.Egreso;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebServlet("/egreso")
public class EgresoServlet extends HttpServlet {
    private EgresoDAO egresoDAO;
    private CuentaDAO cuentaDAO;

    @Override
    public void init() {
        egresoDAO = new EgresoDAO();
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

            Cuenta cuentaOrigen = cuentaDAO.findById(cuentaId);
            if (cuentaOrigen.getBalance() >= valor) {
                Egreso egreso = new Egreso();
                egreso.setCuentaOrigen(cuentaOrigen);
                egreso.setValor(valor);
                egreso.setConcepto(concepto);
                egreso.setCategoria(categoria);

                egresoDAO.save(egreso);

                // Actualizar el balance de la cuenta
                cuentaOrigen.setBalance(cuentaOrigen.getBalance() - valor);
                cuentaDAO.update(cuentaOrigen);

                response.sendRedirect("cuenta.jsp");
            } else {
                response.sendRedirect("cuenta.jsp?error=SaldoInsuficiente");
            }
        }
    }
}
