import com.monedero.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class GestorTransaccionesTest {

    private GestorTransacciones gestorTransacciones;

    @BeforeEach
    public void setUp() {
        // Crear objetos simulados para pruebas
        Cuenta cuenta = new Cuenta("Mi cuenta", "1", new Usuario(), 1000, 100);
        Cuenta cuenta2 = new Cuenta("Mi cuenta 2", "1", new Usuario(), 1000, 100);

        // Crear etiquetas y transacciones simuladas
        Etiqueta etiqueta1 = new Etiqueta("Alimentación");
        Etiqueta etiqueta2 = new Etiqueta("Transporte");

        Transaccion transaccion1 = new Ingreso(cuenta, 100, "Pago", etiqueta1);
        transaccion1.setFecha(LocalDateTime.now().minusDays(1));

        Transaccion transaccion2 = new Egreso(cuenta, 100, "Pago", etiqueta1);
        transaccion2.setFecha(LocalDateTime.now().minusDays(2));

        Transaccion transaccion3 = new Transferencia(cuenta, cuenta2, 100, "Pago", etiqueta2);
        transaccion3.setFecha(LocalDateTime.now().minusDays(3));

        // Lista de transacciones simuladas
        List<Transaccion> transaccionesSimuladas = new ArrayList<>();
        transaccionesSimuladas.add(transaccion1);
        transaccionesSimuladas.add(transaccion2);
        transaccionesSimuladas.add(transaccion3);

        // Usar el constructor alternativo de GestorTransacciones para pruebas
        gestorTransacciones = new GestorTransacciones(cuenta, transaccionesSimuladas);
    }

    // Prueba de filtrado por etiqueta
    static Stream<Arguments> provideArgumentsForFiltrarTransaccionesPorEtiqueta() {
        return Stream.of(
                Arguments.of(new Etiqueta("Alimentación"), 2),
                Arguments.of(new Etiqueta("Transporte"), 1),
                Arguments.of(new Etiqueta("Otros"), 0)
        );
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForFiltrarTransaccionesPorEtiqueta")
    public void testFiltrarTransaccionesPorEtiqueta(Etiqueta etiqueta, int expectedCount) {
        List<Transaccion> filteredTransacciones = gestorTransacciones.filtrarTransaccionesPorEtiqueta(etiqueta);
        assertEquals(expectedCount, filteredTransacciones.size());
    }

    // Prueba de filtrar transacciones por etiqueta sin movimientos
    @Test
    public void testFiltrarTransaccionesPorEtiquetaSinMovimientos() {
        // Crear una etiqueta que no está asociada a ninguna transacción
        Etiqueta etiquetaSinMovimientos = new Etiqueta("SinMovimientos");

        // Filtrar transacciones por la etiqueta sin movimientos
        List<Transaccion> transaccionesFiltradas = gestorTransacciones.filtrarTransaccionesPorEtiqueta(etiquetaSinMovimientos);

        // Verificar que la lista de transacciones filtradas está vacía
        assertTrue(transaccionesFiltradas.isEmpty(), "La lista de transacciones debería estar vacía.");
    }

    // Prueba de limpiar filtro de etiquetas
    @Test
    public void testLimpiarFiltroDeEtiquetas() {
        // Filtrar transacciones por la etiqueta "Alimentación"
        Etiqueta etiquetaAlimentacion = new Etiqueta("Alimentación");
        List<Transaccion> transaccionesFiltradas = gestorTransacciones.filtrarTransaccionesPorEtiqueta(etiquetaAlimentacion);
        assertEquals(2, transaccionesFiltradas.size(), "Debería haber 2 transacciones con la etiqueta 'Alimentación'.");

        // Limpiar el filtro de etiquetas (obtener todas las transacciones)
        List<Transaccion> todasLasTransacciones = gestorTransacciones.getTransacciones();
        assertEquals(3, todasLasTransacciones.size(), "Debería haber 3 transacciones en total después de limpiar el filtro.");
    }

    // Prueba de filtrado por tipo de transacción
    static Stream<Arguments> provideArgumentsForFiltrarTransaccionesPorTipo() {
        return Stream.of(
                Arguments.of(Ingreso.class, 1),
                Arguments.of(Egreso.class, 1),
                Arguments.of(Transferencia.class, 1),
                Arguments.of(Transaccion.class, 3) // Cuenta todos los tipos
        );
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForFiltrarTransaccionesPorTipo")
    public void testFiltrarTransaccionesPorTipo(Class<? extends Transaccion> tipo, int expectedCount) {
        List<?> filteredTransacciones = gestorTransacciones.filtrarTransaccionesPorTipo(gestorTransacciones.getTransacciones(), tipo);
        assertEquals(expectedCount, filteredTransacciones.size());
    }

    // Prueba de filtrar transacciones por tipo sin movimientos
    @Test
    public void testFiltrarTransaccionesPorTipoSinMovimientos() {
        // Crear cuentas y transacciones simuladas
        Cuenta cuenta = new Cuenta("Mi cuenta", "1", new Usuario(), 1000, 100);

        // Lista de transacciones simuladas sin transacciones del tipo Egreso
        List<Transaccion> transaccionesSimuladas = new ArrayList<>();
        transaccionesSimuladas.add(new Ingreso(cuenta, 100, "Ingreso válido", new Etiqueta("Salario")));
        transaccionesSimuladas.add(new Transferencia(cuenta, new Cuenta("Mi cuenta 2", "2", new Usuario(), 1000, 100), 50, "Transferencia válida", new Etiqueta("Transferencia")));

        // Usar el constructor alternativo de GestorTransacciones para pruebas
        gestorTransacciones = new GestorTransacciones(cuenta, transaccionesSimuladas);

        // Filtrar transacciones por tipo Egreso
        List<Egreso> egresos = gestorTransacciones.filtrarTransaccionesPorTipo(transaccionesSimuladas, Egreso.class);

        // Verificar que la lista de egresos está vacía
        assertTrue(egresos.isEmpty(), "La lista de egresos debería estar vacía.");
    }

    // Prueba de cambio de filtros de movimientos
    @Test
    public void testCambiarFiltroCambiaMovimientosFiltrados() {
        // Filtrar transacciones por tipo Ingreso
        List<Ingreso> ingresos = gestorTransacciones.filtrarTransaccionesPorTipo(gestorTransacciones.getTransacciones(), Ingreso.class);
        assertEquals(1, ingresos.size(), "Debería haber 1 transacción de tipo Ingreso.");

        // Cambiar filtro a tipo Egreso
        List<Egreso> egresos = gestorTransacciones.filtrarTransaccionesPorTipo(gestorTransacciones.getTransacciones(), Egreso.class);
        assertEquals(1, egresos.size(), "Debería haber 1 transacción de tipo Egreso.");

        // Cambiar filtro a tipo Transferencia
        List<Transferencia> transferencias = gestorTransacciones.filtrarTransaccionesPorTipo(gestorTransacciones.getTransacciones(), Transferencia.class);
        assertEquals(1, transferencias.size(), "Debería haber 1 transacción de tipo Transferencia.");
    }

    // Prueba de filtrar transacciones por fecha
    @Test
    public void testFiltrarTransaccionesPorFecha() {
        // Definir el rango de fechas
        LocalDate fechaInicio = LocalDate.now().minusDays(3);
        LocalDate fechaFin = LocalDate.now().minusDays(1);

        // Filtrar transacciones por rango de fechas
        List<Transaccion> transaccionesFiltradas = gestorTransacciones.filtrarTransaccionesPorFecha(fechaInicio, fechaFin);

        // Verificar que la lista de transacciones filtradas contiene las transacciones esperadas
        assertEquals(3, transaccionesFiltradas.size(), "El número de transacciones filtradas no es el esperado.");
        assertTrue(transaccionesFiltradas.stream().anyMatch(t -> t.getFecha().toLocalDate().isEqual(LocalDate.now().minusDays(1))), "La transacción 1 debería estar en la lista filtrada.");
        assertTrue(transaccionesFiltradas.stream().anyMatch(t -> t.getFecha().toLocalDate().isEqual(LocalDate.now().minusDays(2))), "La transacción 2 debería estar en la lista filtrada.");
        assertTrue(transaccionesFiltradas.stream().anyMatch(t -> t.getFecha().toLocalDate().isEqual(LocalDate.now().minusDays(3))), "La transacción 3 debería estar en la lista filtrada.");
    }

    // Prueba de filtrar transacciones por fecha sin resultados
    @Test
    public void testFiltrarTransaccionesPorFechaSinMovimientos() {
        // Definir el rango de fechas en el que no hay movimientos
        LocalDate fechaInicio = LocalDate.now().minusDays(10);
        LocalDate fechaFin = LocalDate.now().minusDays(8);

        // Filtrar transacciones por rango de fechas
        List<Transaccion> transaccionesFiltradas = gestorTransacciones.filtrarTransaccionesPorFecha(fechaInicio, fechaFin);

        // Verificar que la lista de transacciones filtradas está vacía
        assertTrue(transaccionesFiltradas.isEmpty(), "La lista de transacciones debería estar vacía.");
    }

    // Pruena de filtrar transacciones por fecha con rango inválido
    @Test
    public void testFiltrarTransaccionesPorFechaRangoInvalido() {
        // Definir un rango de fechas inválido (fechaInicio después de fechaFin)
        LocalDate fechaInicio = LocalDate.now().minusDays(1);
        LocalDate fechaFin = LocalDate.now().minusDays(3);

        // Filtrar transacciones por rango de fechas
        List<Transaccion> transaccionesFiltradas = gestorTransacciones.filtrarTransaccionesPorFecha(fechaInicio, fechaFin);

        // Verificar que la lista de transacciones filtradas está vacía
        assertTrue(transaccionesFiltradas.isEmpty(), "La lista de transacciones debería estar vacía.");

        // Verificar que se muestra un mensaje indicando que el rango de fechas es inválido
        String mensaje = gestorTransacciones.getMensajeRangoInvalido();
        assertEquals("El rango de fechas es inválido. Por favor, cambie el rango.", mensaje, "El mensaje de rango inválido no es el esperado.");
    }

    // Prueba de asignar etiqueta a movimiento
    @Test
    public void testAsignarEtiquetaAMovimiento() {
        // Crear cuentas, usuario, etiqueta y transacción
        Cuenta cuenta = new Cuenta("Mi cuenta", "1", new Usuario(), 1000, 100);
        Etiqueta etiqueta = new Etiqueta("Compras");
        Transaccion transaccion = new Ingreso(cuenta, 100, "Compra de productos", etiqueta);

        // Verificar que la etiqueta asignada a la transacción es la misma que la creada
        assertEquals(etiqueta, transaccion.getEtiqueta(), "La etiqueta asignada a la transacción no es la esperada.");
    }

    // Prueba de obtener etiquetas disponibles
    @Test
    public void testGetEtiquetasDisponibles() {
        // Get the list of available label names from the transactions
        List<String> etiquetasDisponibles = gestorTransacciones.getTransacciones().stream()
                .map(t -> t.getEtiqueta().getNombre())
                .distinct()
                .collect(Collectors.toList());

        // Verify that the list contains the expected labels
        assertEquals(2, etiquetasDisponibles.size(), "The number of available labels is not as expected.");
        assertTrue(etiquetasDisponibles.contains("Alimentación"), "The label 'Alimentación' should be in the list.");
        assertTrue(etiquetasDisponibles.contains("Transporte"), "The label 'Transporte' should be in the list.");
    }

    // Prueba de modificar etiqueta
    @Test
    public void testModificarEtiqueta() {
        // Modify the label of the first transaction
        Transaccion transaccion = gestorTransacciones.getTransacciones().get(0);
        Etiqueta nuevaEtiqueta = new Etiqueta("Compras");
        transaccion.setEtiqueta(nuevaEtiqueta);

        // Verify that the label has been modified
        assertEquals("Compras", transaccion.getEtiqueta().getNombre(), "The label of the transaction should be 'Compras'.");
    }

    // Prueba de eliminar etiqueta
    @Test
    public void testEliminarEtiqueta() {
        // Remove the label of the first transaction
        Transaccion transaccion = gestorTransacciones.getTransacciones().get(0);
        transaccion.setEtiqueta(null);

        // Verify that the label has been removed
        assertNull(transaccion.getEtiqueta(), "The label of the transaction should be null.");
    }

    // Prueba de listar movimientos
    @Test
    public void testListarMovimientos() {
        // Obtener la lista de transacciones del gestor
        List<Transaccion> transacciones = gestorTransacciones.getTransacciones();

        // Verificar que la lista contiene las transacciones esperadas
        assertEquals(3, transacciones.size(), "El número de transacciones no es el esperado.");

        // Verificar que las transacciones son las esperadas
        assertTrue(transacciones.stream().anyMatch(t -> t instanceof Ingreso), "No se encontró la transacción de ingreso esperada.");
        assertTrue(transacciones.stream().anyMatch(t -> t instanceof Egreso), "No se encontró la transacción de egreso esperada.");
        assertTrue(transacciones.stream().anyMatch(t -> t instanceof Transferencia), "No se encontró la transacción de transferencia esperada.");
    }

    // Prueba de listar movimientos sin registros
    @Test
    public void testListarMovimientosSinRegistros() {
        // Crear un gestor de transacciones con una lista vacía
        Cuenta cuenta = new Cuenta("Mi cuenta", "1", new Usuario(), 1000, 100);
        gestorTransacciones = new GestorTransacciones(cuenta, new ArrayList<>());

        // Obtener la lista de transacciones del gestor
        List<Transaccion> transacciones = gestorTransacciones.getTransacciones();

        // Verificar que la lista está vacía
        assertTrue(transacciones.isEmpty(), "La lista de transacciones debería estar vacía.");
    }

    // Prueba de listar movimientos sin movimientos inválidos
    @Test
    public void testListarMovimientosSinMovimientosInvalidos() {
        // Crear cuentas y transacciones simuladas
        Cuenta cuenta = new Cuenta("Mi cuenta", "1", new Usuario(), 1000, 100);
        Etiqueta etiqueta = new Etiqueta("Compras");

        // Crear transacciones válidas e inválidas
        Transaccion transaccionValida = new Ingreso(cuenta, 100, "Compra válida", etiqueta);
        Transaccion transaccionInvalida = null;
        try {
            transaccionInvalida = new Ingreso(cuenta, -50, "Compra inválida", etiqueta);
        } catch (IllegalArgumentException e) {
            // Expected exception for invalid transaction
        }

        // Lista de transacciones simuladas, solo debe incluir la transacción válida
        List<Transaccion> transaccionesSimuladas = new ArrayList<>();
        transaccionesSimuladas.add(transaccionValida);
        if (transaccionInvalida != null) {
            transaccionesSimuladas.add(transaccionInvalida);
        }

        // Usar el constructor alternativo de GestorTransacciones para pruebas
        gestorTransacciones = new GestorTransacciones(cuenta, transaccionesSimuladas);

        // Obtener la lista de transacciones del gestor
        List<Transaccion> transacciones = gestorTransacciones.getTransacciones();

        // Verificar que la lista no contiene transacciones con valor negativo
        assertTrue(transacciones.stream().noneMatch(t -> t.getValor() < 0), "La lista de transacciones contiene movimientos inválidos.");
    }
}
