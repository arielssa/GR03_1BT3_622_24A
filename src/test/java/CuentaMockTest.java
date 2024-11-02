import com.monedero.model.Cuenta;
import com.monedero.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CuentaMockTest {

    private Cuenta cuenta;

    @BeforeEach
    public void setUp() {

        // Crear un mock de la clase Usuario
        Usuario usuarioMock = Mockito.mock(Usuario.class);

        // Definir el comportamiento del mock
        when(usuarioMock.getNombre()).thenReturn("Usuario Mock");

        // Inicializar la cuenta con el mock de Usuario
        cuenta = new Cuenta("Cuenta Mock", "111222333", usuarioMock, 500.00);
    }

    @Test
    public void testDepositarDinero() {
        cuenta.depositarDinero(100.00);
        assertEquals(600.00, cuenta.getBalance());
    }

    @Test
    public void testRetirarDinero() {
        cuenta.retirarDinero(100.00);
        assertEquals(400.00, cuenta.getBalance());
    }

    @Test
    public void testValidarRetiroConBalanceSuficiente() {
        boolean esValido = cuenta.validarRetiro(300.00);
        assertEquals(true, esValido);
    }

    @Test
    public void testValidarRetiroConBalanceInsuficiente() {
        boolean esValido = cuenta.validarRetiro(600.00);
        assertEquals(false, esValido);
    }

    @Test
    public void testUsuarioMockNombre() {
        // Verificar el nombre del mock de Usuario
        assertEquals("Usuario Mock", cuenta.getUsuario().getNombre());
    }
}
