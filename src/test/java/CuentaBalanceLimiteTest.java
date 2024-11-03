import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.monedero.model.Cuenta;
import com.monedero.model.Usuario;

public class CuentaBalanceLimiteTest {

    private Cuenta cuenta;
    private Usuario usuario;

    @BeforeEach
    public void setUp() {
        usuario = new Usuario();  // Asumiendo que tienes una clase Usuario
        cuenta = new Cuenta("Cuenta Prueba", "12345", usuario, 500.0, 1000.0);
    }

    @Test
    public void testInicializacionBalanceLimite() {
        assertEquals(1000.0, cuenta.getBalanceLimite(), "El balance límite inicial no coincide con el valor esperado.");
    }

    @Test
    public void testSetBalanceLimite() {
        cuenta.setBalanceLimite(1500.0);
        assertEquals(1500.0, cuenta.getBalanceLimite(), "El balance límite no se actualizó correctamente.");
    }

    @Test
    public void testRetiroBajaBalanceCercaDelLimite() {
        // Simula un retiro que deja el saldo cerca del límite.
        cuenta.retirarDinero(250.0);
        assertTrue(cuenta.getBalance() < cuenta.getBalanceLimite() + 50,
                "El balance debería estar cerca del límite de advertencia.");
    }

    @Test
    public void testRetiroExcedeLimiteYGeneraAdvertencia() {
        // Configura un límite y retira dinero para superar el balance límite
        cuenta.setBalanceLimite(200.0);
        cuenta.retirarDinero(350.0);
        assertTrue(cuenta.getBalance() < cuenta.getBalanceLimite(),
                "El balance debería estar bajo el límite después del retiro.");
    }

    @Test
    public void testBalanceExactamenteEnLimite() {
        // Realiza un retiro que deja el saldo exactamente en el límite
        cuenta.setBalanceLimite(200.0);
        cuenta.retirarDinero(300.0);
        assertEquals(cuenta.getBalance(), cuenta.getBalanceLimite(),
                "El balance debería estar exactamente en el límite después del retiro.");
    }

    @Test
    public void testRetiroValidoConBalancePorEncimaDelLimite() {
        // Prueba un retiro permitido cuando el balance está cómodamente por encima del límite
        cuenta.setBalanceLimite(100.0);
        assertTrue(cuenta.validarRetiro(100.0),
                "El retiro debería permitirse ya que el balance es suficiente.");
    }

    @Test
    public void testRetiroNoPermitidoPorBalanceInsuficiente() {
        // Configura un límite y prueba que el retiro no se permita si el balance está insuficiente
        cuenta.setBalanceLimite(200.0);
        cuenta.retirarDinero(450.0);  // deja el balance en 50
        assertFalse(cuenta.validarRetiro(100.0),
                "El retiro no debería permitirse si el balance es menor al monto de retiro solicitado.");
    }
}