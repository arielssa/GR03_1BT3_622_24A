package com.monedero.test;

import com.monedero.model.Cuenta;
import com.monedero.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CuentaRendimientoTest {

    private Cuenta cuenta;

    @BeforeEach
    public void setUp() {
        Usuario usuario = new Usuario();
        cuenta = new Cuenta("Cuenta Ahorro", "123456789", usuario, 1000.00);
    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.SECONDS) // Limita a 1 segundo la ejecución de la prueba
    public void testDepositarDineroRendimiento() {
        for (int i = 0; i < 1000; i++) {
            cuenta.depositarDinero(1); // Realiza 1000 depósitos
        }
        assertEquals(2000.00, cuenta.getBalance());
    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.SECONDS) // Limita a 1 segundo la ejecución de la prueba
    public void testRetirarDineroRendimiento() {
        for (int i = 0; i < 1000; i++) {
            cuenta.retirarDinero(1); // Realiza 1000 retiros
        }
        assertEquals(0.00, cuenta.getBalance());
    }
}
