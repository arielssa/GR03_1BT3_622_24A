import com.monedero.model.Transferencia;
import com.monedero.model.Cuenta;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TransferenciaTest {

    @Test
    public void testRealizarTransferencia_LanzaExcepcionPorSaldoInsuficiente() {
        // Configuración
        Cuenta cuentaOrigen = new Cuenta();
        cuentaOrigen.depositarDinero(100); // Saldo en la cuenta origen

        Cuenta cuentaDestino = new Cuenta();
        double valorTransferencia = 200; // Valor mayor que el saldo disponible

        Transferencia transferencia = new Transferencia(cuentaOrigen, cuentaDestino, valorTransferencia, "Pago de servicios");

        // Verificación
        Assertions.assertThrows(RuntimeException.class, () -> {
            transferencia.realizarTransaccion();
        });
    }

    @Test
    public void testRealizarTransferencia_LanzaExcepcionPorValorNegativo() {
        // Configuración
        Cuenta cuentaOrigen = new Cuenta();
        cuentaOrigen.depositarDinero(100); // Saldo en la cuenta origen

        Cuenta cuentaDestino = new Cuenta();
        double valorTransferencia = -50; // Valor negativo para la transferencia

        Transferencia transferencia = new Transferencia(cuentaOrigen, cuentaDestino, valorTransferencia, "Pago erróneo");

        // Verificación
        Assertions.assertThrows(RuntimeException.class, () -> {
            transferencia.realizarTransaccion();
        });
    }

    @Test
    public void testRealizarTransferencia_LanzaExcepcionPorCuentaDestinoInexistente() {
        // Configuración
        Cuenta cuentaOrigen = new Cuenta();
        cuentaOrigen.depositarDinero(100); // Saldo en la cuenta origen

        // Mock de la cuenta destino para simular que no existe
        Cuenta cuentaDestinoMock = mock(Cuenta.class);
        when(cuentaDestinoMock.getId()).thenReturn(-1); // ID nulo para simular cuenta inexistente

        double valorTransferencia = 50; // Valor de la transferencia

        Transferencia transferencia = new Transferencia(cuentaOrigen, cuentaDestinoMock, valorTransferencia, "Pago de servicios");

        // Verificación
        Assertions.assertThrows(RuntimeException.class, () -> {
            transferencia.realizarTransaccion();
        });
    }
}
