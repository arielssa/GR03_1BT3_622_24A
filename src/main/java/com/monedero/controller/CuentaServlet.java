package com.monedero.controller;

import com.monedero.dao.CuentaDAO;
import com.monedero.model.Cuenta;
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

    @Override
    public void init() {
        cuentaDAO = new CuentaDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("create".equals(action)) {
            Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
            String nombre = request.getParameter("nombre");
            String numeroCuenta = request.getParameter("numeroCuenta");
            double balance = Double.parseDouble(request.getParameter("balance"));

            Cuenta cuenta = new Cuenta();
            cuenta.setNombre(nombre);
            cuenta.setNumeroCuenta(numeroCuenta);
            cuenta.setBalance(balance);
            cuenta.setUsuario(usuario);

            cuentaDAO.save(cuenta);

            response.sendRedirect("cuenta");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
        request.setAttribute("cuentas", cuentaDAO.findByUsuario(usuario));
        request.getRequestDispatcher("cuenta.jsp").forward(request, response);
    }
}
