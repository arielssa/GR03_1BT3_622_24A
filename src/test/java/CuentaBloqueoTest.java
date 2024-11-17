import com.monedero.dao.CuentaDAO;
import com.monedero.model.Cuenta;
import com.monedero.model.Egreso;
import com.monedero.model.Transferencia;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CuentaBloqueoTest {

    // Prueba unitaria simple para la clase Cuenta
    @Test
    void testBloquearCuenta() {
        Cuenta cuenta = new Cuenta();
        cuenta.setBloqueada(true);
        assertTrue(cuenta.isBloqueada(), "La cuenta debería estar bloqueada después de llamar a setBloqueada(true)");
    }

    // Prueba con mocks para CuentaDAO
    @Test
    void testBloquearCuentaConMock() {
        // Crear un mock de CuentaDAO
        CuentaDAO cuentaDAO = Mockito.mock(CuentaDAO.class);

        // Crear una instancia de Cuenta
        Cuenta cuenta = new Cuenta();
        cuenta.setId(1);

        // Simular el comportamiento de findById para devolver nuestra cuenta
        when(cuentaDAO.findById(1)).thenReturn(cuenta);

        // Bloquear la cuenta usando el mock
        cuenta.setBloqueada(true);
        cuentaDAO.update(cuenta);

        // Verificar que el metodo setBloqueada cambió el estado de la cuenta
        assertTrue(cuenta.isBloqueada(), "La cuenta debería estar bloqueada después de la actualización");
        verify(cuentaDAO, times(1)).update(cuenta);
    }

    @Test
    void testDesbloquearCuentaConMock() {
        // Crear un mock de CuentaDAO
        CuentaDAO cuentaDAO = Mockito.mock(CuentaDAO.class);

        // Crear una instancia de Cuenta
        Cuenta cuenta = new Cuenta();
        cuenta.setBloqueada(true);
        cuenta.setId(1);

        // Simular el comportamiento de findById para devolver nuestra cuenta
        when(cuentaDAO.findById(1)).thenReturn(cuenta);

        // Desbloquear la cuenta usando el mock
        cuenta.setBloqueada(false);
        cuentaDAO.update(cuenta);

        // Verificar que el metodo setBloqueada cambió el estado de la cuenta
        assertFalse(cuenta.isBloqueada(), "La cuenta debería estar desbloqueada después de la actualización");
        verify(cuentaDAO, times(1)).update(cuenta);
    }

    // Prueba para verificar que no se puede realizar un egreso en una cuenta bloqueada
    @Test
    void testEgresoNoPermiteCuandoCuentaBloqueada() {
        // Crear una cuenta con un balance inicial y bloquearla
        Cuenta cuenta = new Cuenta();
        cuenta.depositarDinero(1000.0); // Balance inicial
        cuenta.setBloqueada(true);

        // Crear un egreso e intentar realizarlo
        Egreso egreso = new Egreso(cuenta, 100.0, "Compra");
        double balanceInicial = cuenta.getBalance();
        egreso.realizarTransaccion();

        // Verificar que el balance no ha cambiado
        assertEquals(balanceInicial, cuenta.getBalance(),
                "El balance debería mantenerse sin cambios al intentar un egreso en una cuenta bloqueada");
    }

    // Prueba para verificar que no se puede realizar una transferencia desde una cuenta bloqueada
    @Test
    void testTransferenciaNoPermiteCuandoCuentaBloqueada() {
        // Crear cuentas de origen (bloqueada) y destino con balance inicial
        Cuenta cuentaOrigen = new Cuenta();
        cuentaOrigen.depositarDinero(1000.0); // Balance inicial en cuenta de origen
        cuentaOrigen.setBloqueada(true);

        Cuenta cuentaDestino = new Cuenta();
        cuentaDestino.depositarDinero(500.0); // Balance inicial en cuenta de destino

        // Crear una transferencia e intentar realizarla
        Transferencia transferencia = new Transferencia(cuentaOrigen, cuentaDestino, 100.0, "Pago");
        double balanceInicialOrigen = cuentaOrigen.getBalance();
        double balanceInicialDestino = cuentaDestino.getBalance();
        transferencia.realizarTransaccion();

        // Verificar que los balances no han cambiado
        assertEquals(balanceInicialOrigen, cuentaOrigen.getBalance(),
                "El balance de la cuenta de origen debería mantenerse sin cambios al intentar una transferencia desde una cuenta bloqueada");
        assertEquals(balanceInicialDestino, cuentaDestino.getBalance(),
                "El balance de la cuenta de destino debería mantenerse sin cambios cuando la cuenta de origen está bloqueada");
    }
}

