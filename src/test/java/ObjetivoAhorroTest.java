import static org.junit.jupiter.api.Assertions.*;

import com.monedero.model.ObjetivoAhorro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ObjetivoAhorroTest {

    private ObjetivoAhorro objetivo;

    @BeforeEach
    void setUp() {
        objetivo = new ObjetivoAhorro();
        objetivo.setDescripcion("Vacaciones");
        objetivo.setMontoObjetivo(1000.0);
        objetivo.setMontoActual(500.0);
    }

    // Prueba: Verificar que ingresar dinero incrementa el monto actual
    @Test
    void testIngresarDinero() {
        objetivo.ingresarDinero(200.0);
        assertEquals(700.0, objetivo.getMontoActual(), "El monto actual debería ser 700 después de ingresar 200");
    }

    // Prueba: Verificar que retirar dinero decrementa el monto actual si hay suficiente saldo
    @Test
    void testRetirarDineroConSaldoSuficiente() {
        boolean resultado = objetivo.retirarDinero(300.0);
        assertEquals(200.0, objetivo.getMontoActual(), "El monto actual debería ser 200 después de retirar 300");
    }

    // Prueba: Verificar que retirar dinero falla si el saldo es insuficiente
    @Test
    void testRetirarDineroConSaldoInsuficiente() {
        boolean resultado = objetivo.retirarDinero(600.0);
        assertFalse(resultado, "No debería ser posible retirar cuando el saldo es insuficiente");
    }

    // Prueba: Verificar el cálculo del porcentaje de progreso
    @Test
    void testCalcularProgreso() {
        double progreso = objetivo.obtenerPorcentajeProgreso();
        assertEquals(50.0, progreso, "El porcentaje de progreso debería ser 50% con un monto actual de 500 y un objetivo de 1000");
    }

    // Prueba: Verificar el cálculo del saldo restante
    @Test
    void testCalcularSaldoRestante() {
        double montoRestante = objetivo.obtenerMontoRestante();
        assertEquals(500.0, montoRestante, "El monto restante debería ser 500 cuando el monto objetivo es 1000 y el monto actual es 500");
    }

    // Prueba (Parametrizada): Verificar que ingresar diferentes cantidades actualiza el monto actual correctamente
    @ParameterizedTest
    @CsvSource({
            "200.0, 700.0",
            "300.0, 800.0",
            "500.0, 1000.0"
    })
    void testIngresarDineroConParametros(double montoIngresado, double montoEsperado) {
        objetivo.ingresarDinero(montoIngresado);
        assertEquals(montoEsperado, objetivo.getMontoActual(),
                "El monto actual debería ser " + montoEsperado + " después de ingresar " + montoIngresado);
    }
}
