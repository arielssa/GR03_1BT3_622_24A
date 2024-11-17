import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.monedero.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

public class TransaccionTest {

    @Test
    public void testCalcularSaldoAntesDeTransaccion_Ingreso() {
        // Crear una instancia de Ingreso con un valor específico
        Ingreso ingreso = new Ingreso(50.0, "Depósito");

        // Saldo después de la transacción
        double saldoDespues = 100.0;

        // Calcular el saldo antes de la transacción
        double saldoAntes = ingreso.calcularBalanceAntesDeTransaccion(saldoDespues, 0);

        // Verificar que el saldo antes sea el saldo después menos el valor del ingreso
        assertEquals(50.0, saldoAntes, 0.01);
    }

    @Test
    public void testCamposIncompletosMuestraMensajeError_Ingreso() {
        // Crear una instancia de Ingreso sin completar todos los campos necesarios
        Ingreso ingreso = new Ingreso();
        ingreso.setValor(100.0); // No se establece el concepto ni la etiqueta

        // Intentar validar los campos de la transacción
        String mensajeError = ingreso.validarCampos();

        // Verificar que se muestra el mensaje de error esperado
        assertEquals("Todos los campos son obligatorios", mensajeError);
    }

    @Test
    public void testValorNegativo_Transaccion() {
        // Verificar que se lanza una excepción cuando se establece un valor negativo en Transaccion
        assertThrows(IllegalArgumentException.class, () -> {
            Transaccion transaccion = new Ingreso(-100.0, "Depósito");
            transaccion.validarValor();
        });
    }

    @Test
    public void testValorNegativo_Ingreso() {
        // Verificar que se lanza una excepción cuando se establece un valor negativo en Ingreso
        assertThrows(IllegalArgumentException.class, () -> {
            Ingreso ingreso = new Ingreso(-100.0, "Depósito");
            ingreso.validarValor();
        });
    }

    @Test
    public void testCalcularSaldoAntesDeTransaccion_Egreso() {
        // Crear una instancia de Egreso con un valor específico
        Egreso egreso = new Egreso(30.0, "Compra");

        // Saldo después de la transacción
        double saldoDespues = 70.0;

        // Calcular el saldo antes de la transacción
        double saldoAntes = egreso.calcularBalanceAntesDeTransaccion(saldoDespues, 0);

        // Verificar que el saldo antes sea el saldo después más el valor del egreso
        assertEquals(100.0, saldoAntes, 0.01);
    }

    @Test
    public void testExtraerInformacionTransaccion_Egreso() {
        // Crear una instancia de Egreso con un valor y concepto específicos
        Egreso egreso = new Egreso(30.0, "Compra");

        // Extraer la información de la transacción
        List<String> info = egreso.extraerInformacionTransaccion();

        // Verificar que los valores extraídos sean correctos
        assertEquals("Egreso", info.get(0));
    }

    @Test
    public void testCalcularSaldoAntesDeTransaccion_TransferenciaConMock() {
        // Crear una instancia de Transferencia usando un mock para la cuenta de origen
        Transferencia transferencia = new Transferencia(25.0, "Transferencia a amigo");

        // Mock de la cuenta de origen y configuración del ID
        Cuenta cuentaOrigenMock = mock(Cuenta.class);
        when(cuentaOrigenMock.getId()).thenReturn(1);
        transferencia.setCuentaOrigen(cuentaOrigenMock);

        double saldoDespues = 200.0;

        // Calcular el saldo antes de la transacción
        double saldoAntes = transferencia.calcularBalanceAntesDeTransaccion(saldoDespues, cuentaOrigenMock.getId());

        // Verificar que el saldo antes sea el saldo después más el valor de la transferencia (salida)
        assertEquals(225.0, saldoAntes, 0.01);
    }

    @ParameterizedTest
    @CsvSource({
            "Ingreso, 100.0, 150.0, 50.0",            // Caso de Ingreso: saldo después - valor = saldo antes
            "Egreso, 50.0, 150.0, 200.0",             // Caso de Egreso: saldo después + valor = saldo antes
            "TransferenciaSalida, 75.0, 200.0, 275.0", // Caso de Transferencia de salida
            "TransferenciaEntrada, 60.0, 180.0, 120.0" // Caso de Transferencia de entrada
    })
    public void testCalcularSaldoAntesDeTransaccion_ConParametros(String tipoTransaccion, double valor, double saldoDespues, double saldoEsperado) {
        // Crear la transacción específica según el tipo
        Transaccion transaccion;
        int cuentaId = 1;

        switch (tipoTransaccion) {
            case "Ingreso":
                transaccion = new Ingreso(valor, "Depósito");
                break;
            case "Egreso":
                transaccion = new Egreso(valor, "Pago");
                break;
            case "TransferenciaSalida":
                transaccion = new Transferencia(valor, "Transferencia a cuenta externa");
                Cuenta cuentaOrigenMock = mock(Cuenta.class);
                Cuenta cuentaDestinoMock = mock(Cuenta.class);
                when(cuentaOrigenMock.getId()).thenReturn(cuentaId);
                when(cuentaDestinoMock.getId()).thenReturn(2); // ID diferente para simular una cuenta de destino
                ((Transferencia) transaccion).setCuentaOrigen(cuentaOrigenMock);
                ((Transferencia) transaccion).setCuentaDestino(cuentaDestinoMock);
                break;
            case "TransferenciaEntrada":
                transaccion = new Transferencia(valor, "Transferencia desde cuenta externa");
                Cuenta cuentaOrigenMockEntrada = mock(Cuenta.class);
                Cuenta cuentaDestinoMockEntrada = mock(Cuenta.class);
                when(cuentaOrigenMockEntrada.getId()).thenReturn(2); // ID diferente para simular una cuenta externa como origen
                when(cuentaDestinoMockEntrada.getId()).thenReturn(cuentaId); // ID de cuenta actual para simular cuenta de destino
                ((Transferencia) transaccion).setCuentaOrigen(cuentaOrigenMockEntrada);
                ((Transferencia) transaccion).setCuentaDestino(cuentaDestinoMockEntrada);
                break;
            default:
                throw new IllegalArgumentException("Tipo de transacción desconocido: " + tipoTransaccion);
        }

        // Calcular el saldo antes de la transacción
        double saldoAntes = transaccion.calcularBalanceAntesDeTransaccion(saldoDespues, cuentaId);

        // Verificar que el saldo antes sea igual al esperado
        assertEquals(saldoEsperado, saldoAntes, 0.01);
    }

}
