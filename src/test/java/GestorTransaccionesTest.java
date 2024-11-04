import com.monedero.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
