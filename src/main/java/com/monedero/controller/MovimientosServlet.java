package com.monedero.controller;

import com.monedero.dao.CuentaDAO;
import com.monedero.dao.EtiquetaDAO;
import com.monedero.model.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/movimientos")
public class MovimientosServlet extends HttpServlet {
    private CuentaDAO cuentaDAO;
    private EtiquetaDAO etiquetaDAO;
    GestorTransacciones gestorTransacciones;

    @Override
    public void init() {
        cuentaDAO = new CuentaDAO();
        etiquetaDAO = new EtiquetaDAO();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int cuentaId = Integer.parseInt(request.getParameter("cuentaId"));
        String action = request.getParameter("action");

        gestorTransacciones = new GestorTransacciones(cuentaDAO.findById(cuentaId));
        request.setAttribute("cuentaId", cuentaId);

        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
        List<Etiqueta> etiquetas = etiquetaDAO.findByUsuario(usuario);

        List<Map<String, Object>> movimientosConTipo = new ArrayList<>();

        if ("ingresosFecha".equals(action)) {
            LocalDate fechaInicio = LocalDate.parse(request.getParameter("fechaInicio"));
            LocalDate fechaFin = LocalDate.parse(request.getParameter("fechaFin"));

            List<Transaccion> transacciones = gestorTransacciones.filtrarTransaccionesPorFecha(fechaInicio, fechaFin);
            List<Ingreso> ingresos = gestorTransacciones.filtrarTransaccionesPorTipo(transacciones, Ingreso.class);

            movimientosConTipo = ingresos.stream()
                    .map(transaccion -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("transaccion", transaccion);
                        map.put("tipo", "Ingreso");
                        return map;
                    }).collect(Collectors.toList());

            request.setAttribute("tipo", "Ingresos");

        } else if ("egresosFecha".equals(action)) {
            LocalDate fechaInicio = LocalDate.parse(request.getParameter("fechaInicio"));
            LocalDate fechaFin = LocalDate.parse(request.getParameter("fechaFin"));

            List<Transaccion> transacciones = gestorTransacciones.filtrarTransaccionesPorFecha(fechaInicio, fechaFin);
            List<Egreso> egresos = gestorTransacciones.filtrarTransaccionesPorTipo(transacciones, Egreso.class);

            movimientosConTipo = egresos.stream()
                    .map(transaccion -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("transaccion", transaccion);
                        map.put("tipo", "Egreso");
                        return map;
                    }).collect(Collectors.toList());

            request.setAttribute("tipo", "Egresos");

        } else if ("transferenciasFecha".equals(action)) {
            LocalDate fechaInicio = LocalDate.parse(request.getParameter("fechaInicio"));
            LocalDate fechaFin = LocalDate.parse(request.getParameter("fechaFin"));

            List<Transaccion> transacciones = gestorTransacciones.filtrarTransaccionesPorFecha(fechaInicio, fechaFin);
            List<Transferencia> transferencias = gestorTransacciones.filtrarTransaccionesPorTipo(transacciones, Transferencia.class);

            movimientosConTipo = transferencias.stream()
                    .map(transaccion -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("transaccion", transaccion);
                        map.put("tipo", "Transferencia");
                        return map;
                    }).collect(Collectors.toList());

            request.setAttribute("tipo", "Transferencias");

        } else if ("ingresosEtiqueta".equals(action)) {
            Etiqueta etiqueta = Etiqueta.buscarEtiquetaPorNombre(etiquetas, request.getParameter("etiqueta")).orElse(null);

            List<Transaccion> transacciones = gestorTransacciones.filtrarTransaccionesPorEtiqueta(etiqueta);
            List<Ingreso> ingresos = gestorTransacciones.filtrarTransaccionesPorTipo(transacciones, Ingreso.class);

            movimientosConTipo = ingresos.stream()
                    .map(transaccion -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("transaccion", transaccion);
                        map.put("tipo", "Ingreso");
                        return map;
                    }).collect(Collectors.toList());

            request.setAttribute("tipo", "Ingresos");

        } else if ("egresosEtiqueta".equals(action)) {
            Etiqueta etiqueta = Etiqueta.buscarEtiquetaPorNombre(etiquetas, request.getParameter("etiqueta")).orElse(null);

            List<Transaccion> transacciones = gestorTransacciones.filtrarTransaccionesPorEtiqueta(etiqueta);
            List<Egreso> egresos = gestorTransacciones.filtrarTransaccionesPorTipo(transacciones, Egreso.class);

            movimientosConTipo = egresos.stream()
                    .map(transaccion -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("transaccion", transaccion);
                        map.put("tipo", "Egreso");
                        return map;
                    }).collect(Collectors.toList());

            request.setAttribute("tipo", "Egresos");

        } else if ("todas".equals(action)) {
            List<Transaccion> transacciones = gestorTransacciones.getTransacciones();

            movimientosConTipo = transacciones.stream()
                    .map(transaccion -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("transaccion", transaccion);
                        map.put("tipo", transaccion.getClass().getSimpleName());
                        return map;
                    }).collect(Collectors.toList());

            request.setAttribute("tipo", "Transacciones");
        } else {
            response.sendRedirect("detalleCuenta?cuentaId=" + cuentaId);
            return;
        }

        request.setAttribute("movimientosConTipo", movimientosConTipo);
        request.getRequestDispatcher("mostrarMovimientos.jsp").forward(request, response);
    }


}
