package com.monedero.controller;

import com.monedero.model.Cuenta;
import com.monedero.model.Usuario;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class CuentaTest {

    @ParameterizedTest
    @CsvSource({
            "100.0, 50.0, true",  // Balance suficiente
            "100.0, 100.0, true", // Balance igual al retiro
            "100.0, 150.0, false" // Balance insuficiente
    })
    void testValidarRetiro(double balanceInicial, double valorRetiro, boolean resultadoEsperado) {
        Cuenta cuenta = new Cuenta();
        cuenta.setBalance(balanceInicial);

        boolean resultado = cuenta.validarRetiro(valorRetiro);

        assertEquals(resultadoEsperado, resultado);
    }

    @ParameterizedTest
    @CsvSource({
            "200.0, 50.0, 150.0", // Balance inicial 200, retiro 50, balance esperado 150
            "100.0, 100.0, 0.0",  // Balance inicial 100, retiro 100, balance esperado 0
            "500.0, 200.0, 300.0" // Balance inicial 500, retiro 200, balance esperado 300
    })
    void testRetirarDinero(double balanceInicial, double valorRetiro, double balanceEsperado) {
        Cuenta cuenta = new Cuenta();
        cuenta.setBalance(balanceInicial);

        cuenta.retirarDinero(valorRetiro);

        assertEquals(balanceEsperado, cuenta.getBalance());
    }

    @Test
    void testDepositarDinero() {
        // Crear una nueva cuenta con balance inicial
        Cuenta cuenta = new Cuenta("Cuenta Prueba", "12345", new Usuario(), 100.0);

        // Depositar dinero
        cuenta.depositarDinero(50.0);

        // Verificar que el balance se actualiza correctamente
        assertEquals(150.0, cuenta.getBalance(), "El balance no se actualizó correctamente después del depósito.");
    }
}
