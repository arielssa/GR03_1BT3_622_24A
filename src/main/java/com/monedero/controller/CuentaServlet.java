package com.monedero.controller;

import com.monedero.dao.CuentaDAO;
import com.monedero.dao.EtiquetaDAO;
import com.monedero.dao.UsuarioDAO;
import com.monedero.model.Cuenta;
import com.monedero.model.Etiqueta;
import com.monedero.model.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cuenta")
public class CuentaServlet extends HttpServlet {
    private CuentaDAO cuentaDAO;
    private EtiquetaDAO etiquetaDAO;

    @Override
    public void init() {
        cuentaDAO = new CuentaDAO();
        etiquetaDAO = new EtiquetaDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("createAccount".equals(action)) {
            Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
            String nombre = request.getParameter("nombre");
            String numeroCuenta = request.getParameter("numeroCuenta");
            double balance = Double.parseDouble(request.getParameter("balance"));
            double balanceLimite = Double.parseDouble(request.getParameter("balanceLimite"));

            Cuenta cuenta = new Cuenta(nombre, numeroCuenta, usuario, balance, balanceLimite);

            cuentaDAO.save(cuenta);

            response.sendRedirect("cuenta");
        }

        if("actualizarBalanceLimite".equals(action)) {

            int cuentaId = Integer.parseInt(request.getParameter("cuentaId"));
            double balanceLimite = Double.parseDouble(request.getParameter("balanceLimite"));
            Cuenta cuenta = cuentaDAO.findById(cuentaId);
            cuenta.setBalanceLimite(balanceLimite);
            cuentaDAO.update(cuenta);
            response.sendRedirect("cuenta");
        }

        if ("createTag".equals(action)) {
            Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
            String nombre = request.getParameter("nombre");

            Etiqueta etiqueta = new Etiqueta(nombre, usuario);

            etiquetaDAO.save(etiqueta);

            response.sendRedirect("cuenta");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
        request.setAttribute("cuentas", cuentaDAO.findByUsuario(usuario));
        request.setAttribute("etiquetas", etiquetaDAO.findByUsuario(usuario));
        request.getRequestDispatcher("cuenta.jsp").forward(request, response);
    }
}
