package com.monedero.controller;

import com.monedero.dao.UsuarioDAO;
import com.monedero.model.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/usuario")
public class UsuarioServlet extends HttpServlet {
    private UsuarioDAO usuarioDAO;

    @Override
    public void init() {
        usuarioDAO = new UsuarioDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("register".equals(action)) {
            String nombre = request.getParameter("nombre");
            String nombreUsuario = request.getParameter("nombreUsuario");
            String contrasena = request.getParameter("contrasena");

            Usuario usuario = new Usuario();
            usuario.setNombre(nombre);
            usuario.setNombreUsuario(nombreUsuario);
            usuario.setContrasena(contrasena);

            usuarioDAO.save(usuario);

            response.sendRedirect("login.jsp"); // Redirige a la página de login después del registro
        } else if ("login".equals(action)) {
            String nombreUsuario = request.getParameter("nombreUsuario");
            String contrasena = request.getParameter("contrasena");

            Usuario usuario = usuarioDAO.findByNombreUsuario(nombreUsuario);
            if (usuario != null && usuario.getContrasena().equals(contrasena)) {
                request.getSession().setAttribute("usuario", usuario);
                response.sendRedirect("tablero.jsp");
            } else {
                response.sendRedirect("login.jsp?error=true");
            }
        }
    }
}
