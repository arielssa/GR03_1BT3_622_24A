package com.monedero.controller;

import com.monedero.controller.UsuarioServlet;
import com.monedero.dao.UsuarioDAO;
import com.monedero.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Mockito.*;

class UsuarioServletTest {

    private UsuarioServlet usuarioServlet;
    private UsuarioDAO usuarioDAO;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;

    @BeforeEach
    void setUp() {
        usuarioServlet = new UsuarioServlet();
        usuarioDAO = mock(UsuarioDAO.class);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);

        // Inyectar el mock de usuarioDAO en el servlet
        usuarioServlet.init(); // Esto inicializa el DAO
        usuarioServlet.usuarioDAO = usuarioDAO; // Sobrescribimos el DAO con el mock
    }

    @Test
    void testLoginSuccess() throws IOException, ServletException {
        when(request.getParameter("action")).thenReturn("login");
        when(request.getParameter("nombreUsuario")).thenReturn("testUser");
        when(request.getParameter("contrasena")).thenReturn("12345");

        // Configuramos un mock para el usuario que el DAO devolverá
        Usuario usuarioMock = new Usuario();
        usuarioMock.setNombreUsuario("testUser");
        usuarioMock.setContrasena("12345");
        when(usuarioDAO.findByNombreUsuario("testUser")).thenReturn(usuarioMock);

        // Configuramos que la solicitud devuelva nuestra sesión mockeada
        when(request.getSession()).thenReturn(session);

        // Llamamos al método doPost del servlet con el mock de request y response
        usuarioServlet.doPost(request, response);

        // Verificamos que la sesión tenga el atributo correcto
        verify(session).setAttribute("usuario", usuarioMock);

        // Verificamos que se redirija a la página correcta después del login exitoso
        verify(response).sendRedirect("cuenta.jsp");
    }

    @Test
    void testLoginFailure() throws IOException, ServletException {
        when(request.getParameter("action")).thenReturn("login");
        when(request.getParameter("nombreUsuario")).thenReturn("wrongUser");
        when(request.getParameter("contrasena")).thenReturn("wrongPass");

        // Configuramos que el DAO devuelva null, simulando que el usuario no fue encontrado
        when(usuarioDAO.findByNombreUsuario("wrongUser")).thenReturn(null);

        // Llamamos al método doPost del servlet con el mock de request y response
        usuarioServlet.doPost(request, response);

        // Verificamos que se redirija a la página de login con un error
        verify(response).sendRedirect("login.jsp?error=true");
    }
}
