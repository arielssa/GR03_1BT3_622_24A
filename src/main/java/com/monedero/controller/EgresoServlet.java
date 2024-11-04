package com.monedero.controller;

import com.monedero.dao.CuentaDAO;
import com.monedero.dao.EgresoDAO;
import com.monedero.dao.EtiquetaDAO;
import com.monedero.model.Cuenta;
import com.monedero.model.Egreso;
import com.monedero.model.Etiqueta;
import com.monedero.model.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.OpenOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@WebServlet("/egreso")
public class EgresoServlet extends HttpServlet {
    private EgresoDAO egresoDAO;
    private CuentaDAO cuentaDAO;
    private EtiquetaDAO etiquetaDAO;

    @Override
    public void init() {
        egresoDAO = new EgresoDAO();
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
            Cuenta cuentaOrigen = cuentaDAO.findById(cuentaId);
            Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
            List<Etiqueta> etiquetas = etiquetaDAO.findByUsuario(usuario);
            Etiqueta etiqueta = Etiqueta.buscarEtiquetaPorNombre(etiquetas, request.getParameter("etiqueta")).orElse(null);


            if (cuentaOrigen.getBalance() >= valor) {
                Egreso egreso = new Egreso(cuentaOrigen, valor, concepto, etiqueta);
                double balanceTemp = cuentaOrigen.getBalance();
                egreso.setFecha(LocalDateTime.now());

                try {
                    egreso.realizarTransaccion();
                    egresoDAO.save(egreso);
                    cuentaDAO.update(cuentaOrigen);
                } catch (Exception e){
                    e.printStackTrace();
                }

                if((balanceTemp - valor) < cuentaOrigen.getBalanceLimite()){
                    response.sendRedirect("cuenta?error=SobrepasoLimite");
                } else if ((balanceTemp - valor) == cuentaOrigen.getBalanceLimite()) {
                    response.sendRedirect("cuenta?error=EstaEnLimite");
                } else if (((balanceTemp - valor) - cuentaOrigen.getBalanceLimite()) > 0 &&
                        ((balanceTemp - valor) - cuentaOrigen.getBalanceLimite()) < 11) {
                    response.sendRedirect("cuenta?error=EstaCercaLimite");
                }else{
                    response.sendRedirect("cuenta");
                }


            } else {
                response.sendRedirect("cuenta?error=SaldoInsuficiente");
            }
        }
    }
}
