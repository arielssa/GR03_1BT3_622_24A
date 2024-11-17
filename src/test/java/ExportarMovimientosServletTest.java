import com.monedero.controller.ExportarMovimientosServlet;
import com.monedero.dao.CuentaDAO;
import com.monedero.model.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class ExportarMovimientosServletTest {

    private ExportarMovimientosServlet servlet;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private CuentaDAO cuentaDAO;

    private Cuenta cuenta;
    private Usuario usuario;
    private List<Transaccion> transacciones;
    private ByteArrayOutputStream outputStream;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        servlet = new ExportarMovimientosServlet();

        // Configurar cuenta y usuario de prueba
        usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("Usuario Test");

        cuenta = new Cuenta("Cuenta Test", "123456", usuario, 1000.0, 5000.0);
        cuenta.setId(1);

        // Crear transacciones de prueba
        transacciones = new ArrayList<>();
        transacciones.add(new Ingreso(cuenta, 100.0, "Ingreso test 1"));
        transacciones.add(new Egreso(cuenta, 50.0, "Egreso test 1"));

        // Configurar fechas en las transacciones
        for (Transaccion t : transacciones) {
            t.setFecha(LocalDateTime.now());
        }

        outputStream = new ByteArrayOutputStream();

        // Configurar el mock de CuentaDAO
        servlet.cuentaDAO = cuentaDAO;
        when(cuentaDAO.findById(1)).thenReturn(cuenta);
    }

    @Test
    public void testExportarMovimientosSinFiltroFecha() throws ServletException, IOException {
        // Configurar request params
        when(request.getParameter("cuentaId")).thenReturn("1");
        when(request.getParameter("fechaInicio")).thenReturn("");
        when(request.getParameter("fechaFin")).thenReturn("");

        // Configurar response
        when(response.getOutputStream()).thenReturn(new ServletOutputStreamMock(outputStream));

        // Ejecutar servlet
        servlet.doPost(request, response);

        // Verificar que se configuró correctamente la respuesta
        verify(response).setContentType("application/pdf");
        verify(response).setHeader("Content-Disposition", "attachment; filename=movimientos_1.pdf");

        // Verificar que se generó contenido en el PDF
        assertTrue(outputStream.size() > 0);
    }

    @Test
    public void testExportarMovimientosConFiltroFecha() throws ServletException, IOException {
        // Configurar request params con fechas
        LocalDate fechaInicio = LocalDate.now().minusDays(7);
        LocalDate fechaFin = LocalDate.now();

        when(request.getParameter("cuentaId")).thenReturn("1");
        when(request.getParameter("fechaInicio")).thenReturn(fechaInicio.toString());
        when(request.getParameter("fechaFin")).thenReturn(fechaFin.toString());

        // Configurar response
        when(response.getOutputStream()).thenReturn(new ServletOutputStreamMock(outputStream));

        // Ejecutar servlet
        servlet.doPost(request, response);

        // Verificar que se configuró correctamente la respuesta
        verify(response).setContentType("application/pdf");
        verify(response).setHeader("Content-Disposition", "attachment; filename=movimientos_1.pdf");

        // Verificar que se generó contenido en el PDF
        assertTrue(outputStream.size() > 0);
    }

    @Test(expected = NumberFormatException.class)
    public void testExportarMovimientosConCuentaIdInvalido() throws ServletException, IOException {
        // Configurar request params con ID inválido
        when(request.getParameter("cuentaId")).thenReturn("invalid");

        // Ejecutar servlet - debería lanzar NumberFormatException
        servlet.doPost(request, response);
    }

    // Clase auxiliar para simular ServletOutputStream
    private static class ServletOutputStreamMock extends javax.servlet.ServletOutputStream {
        private final ByteArrayOutputStream baos;

        public ServletOutputStreamMock(ByteArrayOutputStream baos) {
            this.baos = baos;
        }

        @Override
        public void write(int b) throws IOException {
            baos.write(b);
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setWriteListener(javax.servlet.WriteListener writeListener) {
        }
    }
}