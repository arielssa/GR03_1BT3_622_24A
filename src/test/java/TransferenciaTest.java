import com.monedero.model.Transferencia;
import com.monedero.model.Cuenta;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            transferencia.realizarTransaccion();
        });
    }
}
