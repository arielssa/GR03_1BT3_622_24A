package com.monedero.test;

import com.monedero.model.Cuenta;
import com.monedero.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CuentaBordeTest {

    private Cuenta cuenta;

    @BeforeEach
    public void setUp() {
        Usuario usuario = new Usuario();
        cuenta = new Cuenta("Cuenta Borde", "987654321", usuario, 100.00); // Balance inicial de 100
    }

    @Test
    public void testRetiroValorIgualAlBalance() {
        // Retirar una cantidad igual al balance actual (límite superior)
        boolean esValido = cuenta.validarRetiro(100.00);
        assertTrue(esValido); // La validación debe ser true

        cuenta.retirarDinero(100.00);
        assertEquals(0.00, cuenta.getBalance()); // El balance debe ser 0 después del retiro
    }

    @Test
    public void testRetiroValorMayorAlBalance() {
        // Retirar una cantidad mayor al balance actual (límite superior superado)
        boolean esValido = cuenta.validarRetiro(150.00);
        assertFalse(esValido); // La validación debe ser false

        // El balance debe permanecer igual, ya que no se realizó el retiro
        assertEquals(100.00, cuenta.getBalance());
    }

    @Test
    public void testRetiroValorCercanoACero() {
        // Retirar una cantidad muy pequeña, cercana a cero (límite inferior)
        boolean esValido = cuenta.validarRetiro(0.01);
        assertTrue(esValido); // La validación debe ser true

        cuenta.retirarDinero(0.01);
        assertEquals(99.99, cuenta.getBalance(), 0.001); // El balance debe reflejar correctamente el retiro
    }

    @Test
    public void testRetiroConBalanceCero() {
        // Simular cuenta con balance cero
        cuenta.setBalance(0.00);

        boolean esValido = cuenta.validarRetiro(1.00);
        assertFalse(esValido); // La validación debe ser false

        // El balance sigue siendo 0
        assertEquals(0.00, cuenta.getBalance());
    }
}
