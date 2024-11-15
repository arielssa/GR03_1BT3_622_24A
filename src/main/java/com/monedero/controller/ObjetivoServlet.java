package com.monedero.controller;

import com.monedero.dao.ObjetivoAhorroDAO;
import com.monedero.model.ObjetivoAhorro;
import com.monedero.model.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/objetivos")
public class ObjetivoServlet extends HttpServlet {
    private ObjetivoAhorroDAO objetivoAhorroDAO;

    @Override
    public void init() {
        objetivoAhorroDAO = new ObjetivoAhorroDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        int objetivoId;
        double monto;
        ObjetivoAhorro objetivo;

        switch (action) {
            case "deposit":
                objetivoId = Integer.parseInt(request.getParameter("objetivoId"));
                monto = Double.parseDouble(request.getParameter("monto"));
                objetivo = objetivoAhorroDAO.findById(objetivoId);
                objetivo.ingresarDinero(monto);
                objetivoAhorroDAO.update(objetivo);
                response.sendRedirect("objetivos?objetivoId=" + objetivoId + "&action=deposit");
                break;

            case "withdraw":
                objetivoId = Integer.parseInt(request.getParameter("objetivoId"));
                monto = Double.parseDouble(request.getParameter("monto"));
                objetivo = objetivoAhorroDAO.findById(objetivoId);
                if (objetivo.retirarDinero(monto)) {
                    objetivoAhorroDAO.update(objetivo);
                    response.sendRedirect("objetivos?objetivoId=" + objetivoId + "&action=withdraw");
                } else {
                    response.sendRedirect("objetivos?error=SaldoInsuficiente");
                }
                break;

            case "createGoal":
                Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
                String descripcion = request.getParameter("descripcion");
                double montoObjetivo = Double.parseDouble(request.getParameter("montoObjetivo"));
                double montoActual = Double.parseDouble(request.getParameter("montoActual"));

                ObjetivoAhorro nuevoObjetivo = new ObjetivoAhorro();
                nuevoObjetivo.setDescripcion(descripcion);
                nuevoObjetivo.setMontoObjetivo(montoObjetivo);
                nuevoObjetivo.setMontoActual(montoActual);
                nuevoObjetivo.setUsuario(usuario);

                objetivoAhorroDAO.save(nuevoObjetivo);
                response.sendRedirect("objetivos");
                break;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
        List<ObjetivoAhorro> objetivos = objetivoAhorroDAO.findByUsuario(usuario);

        // Verificar si hay mensajes en la URL
        String mensaje = null;
        String objetivoIdParam = request.getParameter("objetivoId");
        String action = request.getParameter("action");

        if (objetivoIdParam != null) {
            int objetivoId = Integer.parseInt(objetivoIdParam);
            ObjetivoAhorro objetivo = objetivoAhorroDAO.findById(objetivoId);

            if ("deposit".equals(action)) {
                double progreso = (objetivo.getMontoActual() / objetivo.getMontoObjetivo()) * 100;
                if (progreso >= 100) {
                    mensaje = "¡Enhorabuena! Has alcanzado el 100% de tu objetivo de ahorro para " + objetivo.getDescripcion();
                } else if (progreso >= 50) {
                    mensaje = "¡Felicidades! Has alcanzado el 50% de tu objetivo de ahorro para " + objetivo.getDescripcion();
                }
            }
        }

        // Verificar si hay un error de saldo insuficiente
        if ("SaldoInsuficiente".equals(request.getParameter("error"))) {
            mensaje = "Error: Saldo insuficiente para realizar el retiro.";
        }

        request.setAttribute("mensaje", mensaje);
        request.setAttribute("objetivos", objetivos);
        request.getRequestDispatcher("objetivo.jsp").forward(request, response);
    }
}
