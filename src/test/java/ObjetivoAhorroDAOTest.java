import static org.junit.jupiter.api.Assertions.*;
import com.monedero.dao.ObjetivoAhorroDAO;
import com.monedero.model.ObjetivoAhorro;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
class ObjetivoAhorroDAOTest {
    private ObjetivoAhorroDAO objetivoAhorroDAO;
    private ObjetivoAhorro objetivo;
    @BeforeEach
    void setUp() {
        objetivoAhorroDAO = new ObjetivoAhorroDAO();
        objetivo = new ObjetivoAhorro();
        objetivo.setDescripcion("Ahorro para Emergencias");
        objetivo.setMontoObjetivo(2000.0);
        objetivo.setMontoActual(1500.0);
        // Guardamos el objetivo en la base de datos simulada
        objetivoAhorroDAO.save(objetivo);
    }
    // Prueba: Verificar que el retiro se realiza exitosamente cuando hay saldo suficiente
    @Test
    void testRetirarDineroConSaldoSuficiente() {
        // Obtenemos el objetivo desde el DAO
        ObjetivoAhorro objetivoGuardado = objetivoAhorroDAO.findById(objetivo.getId());
        // Realizamos el retiro
        boolean resultado = objetivoGuardado.retirarDinero(500.0);
        // Actualizamos en el DAO el nuevo estado del objetivo
        objetivoAhorroDAO.update(objetivoGuardado);
        // Verificamos que el retiro fue exitoso y el monto actual es el esperado
        assertEquals(1000.0, objetivoGuardado.getMontoActual(), "El monto actual debería ser 1000 después de retirar 500");
    }
    // Método para limpiar después de cada prueba
    @AfterEach
    void tearDown() {
        // Eliminamos el objetivo creado en el setup
        objetivoAhorroDAO.delete(objetivo);
    }
}