
package com.monedero.controller;
import com.monedero.dao.CuentaDAO;
import com.monedero.model.Cuenta;
import com.monedero.model.GestorTransacciones;
import com.monedero.model.Transaccion;
import com.monedero.util.PDFGenerator;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
@WebServlet("/exportarMovimientos")
public class ExportarMovimientosServlet extends HttpServlet {
    private GestorTransacciones gestorTransacciones;
    private CuentaDAO cuentaDAO;

    @Override
    public void init() {
        cuentaDAO = new CuentaDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fechaInicioParam = request.getParameter("fechaInicio");
        String fechaFinParam = request.getParameter("fechaFin");
        String cuentaIdParam = request.getParameter("cuentaId");
        // Obtener la cuenta y configurar el gestor de transacciones
        Cuenta cuenta = cuentaDAO.findById(Integer.parseInt(cuentaIdParam));
        gestorTransacciones = new GestorTransacciones(cuenta);
        // Parsear las fechas opcionales
        LocalDate fechaInicio = fechaInicioParam != null && !fechaInicioParam.isEmpty() ? LocalDate.parse(fechaInicioParam) : null;
        LocalDate fechaFin = fechaFinParam != null && !fechaFinParam.isEmpty() ? LocalDate.parse(fechaFinParam) : null;
        // Filtrar los movimientos
        List<Transaccion> movimientos;
        if (fechaInicio != null && fechaFin != null) {
            movimientos = gestorTransacciones.filtrarTransaccionesPorFecha(fechaInicio, fechaFin);
        } else {
            movimientos = gestorTransacciones.getTransacciones();
        }
        // Configurar la respuesta para la descarga de PDF
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=movimientos_" + cuentaIdParam + ".pdf");
        PDFGenerator.generarPDF(movimientos, response.getOutputStream(), cuenta);
    }
}
