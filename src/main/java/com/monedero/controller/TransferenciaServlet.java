package com.monedero.controller;

import com.monedero.dao.CuentaDAO;
import com.monedero.dao.EtiquetaDAO;
import com.monedero.dao.TransferenciaDAO;
import com.monedero.model.Cuenta;
import com.monedero.model.Etiqueta;
import com.monedero.model.Transferencia;
import com.monedero.model.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/transferencia")
public class TransferenciaServlet extends HttpServlet {
    private TransferenciaDAO transferenciaDAO;
    private CuentaDAO cuentaDAO;
    private EtiquetaDAO etiquetaDAO;

    @Override
    public void init() {
        transferenciaDAO = new TransferenciaDAO();
        cuentaDAO = new CuentaDAO();
        etiquetaDAO = new EtiquetaDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("create".equals(action)) {
            int cuentaOrigenId = Integer.parseInt(request.getParameter("cuentaOrigenId"));
            int cuentaDestinoId = Integer.parseInt(request.getParameter("cuentaDestinoId"));
            double valor = Double.parseDouble(request.getParameter("valor"));
            String concepto = request.getParameter("concepto");
            Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
            List<Etiqueta> etiquetas = etiquetaDAO.findByUsuario(usuario);
            Etiqueta etiqueta = Etiqueta.buscarEtiquetaPorNombre(etiquetas, request.getParameter("etiqueta")).orElse(null);

            Cuenta cuentaOrigen = cuentaDAO.findById(cuentaOrigenId);
            Cuenta cuentaDestino = cuentaDAO.findById(cuentaDestinoId);

            if (cuentaOrigen.getBalance() >= valor) {
                Transferencia transferencia = new Transferencia(cuentaOrigen, cuentaDestino, valor, concepto);
                transferencia.setEtiqueta(etiqueta);
                transferencia.setFecha(LocalDateTime.now());

                try {
                    transferencia.realizarTransaccion();
                    transferenciaDAO.save(transferencia);
                    cuentaDAO.update(cuentaOrigen);
                    cuentaDAO.update(cuentaDestino);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                response.sendRedirect("cuenta");
            } else {
                response.sendRedirect("cuenta?error=SaldoInsuficiente");
            }
        }
    }
}
