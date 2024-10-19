package com.monedero.controller;

import com.monedero.dao.CuentaDAO;
import com.monedero.dao.TransferenciaDAO;
import com.monedero.model.Cuenta;
import com.monedero.model.Transferencia;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebServlet("/transferencia")
public class TransferenciaServlet extends HttpServlet {
    private TransferenciaDAO transferenciaDAO;
    private CuentaDAO cuentaDAO;

    @Override
    public void init() {
        transferenciaDAO = new TransferenciaDAO();
        cuentaDAO = new CuentaDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("create".equals(action)) {
            int cuentaOrigenId = Integer.parseInt(request.getParameter("cuentaOrigenId"));
            int cuentaDestinoId = Integer.parseInt(request.getParameter("cuentaDestinoId"));
            double valor = Double.parseDouble(request.getParameter("valor"));
            String concepto = request.getParameter("concepto");

            Cuenta cuentaOrigen = cuentaDAO.findById(cuentaOrigenId);
            Cuenta cuentaDestino = cuentaDAO.findById(cuentaDestinoId);

            if (cuentaOrigen.getBalance() >= valor) {
                Transferencia transferencia = new Transferencia();
                transferencia.setCuentaOrigen(cuentaOrigen);
                transferencia.setCuentaDestino(cuentaDestino);
                transferencia.setValor(valor);
                transferencia.setConcepto(concepto);

                transferenciaDAO.save(transferencia);

                // Actualizar los balances de las cuentas
                cuentaOrigen.setBalance(cuentaOrigen.getBalance() - valor);
                cuentaDestino.setBalance(cuentaDestino.getBalance() + valor);
                cuentaDAO.update(cuentaOrigen);
                cuentaDAO.update(cuentaDestino);

                response.sendRedirect("tablero.jsp");
            } else {
                response.sendRedirect("tablero.jsp?error=SaldoInsuficiente");
            }
        }
    }
}
