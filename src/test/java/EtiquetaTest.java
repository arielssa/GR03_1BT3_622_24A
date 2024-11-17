import com.monedero.model.Etiqueta;
import com.monedero.model.Transaccion;
import com.monedero.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import static org.mockito.Mockito.*;

public class EtiquetaTest {

    private List<Etiqueta> etiquetas;
    private Etiqueta etiqueta1;
    private Etiqueta etiqueta2;

    @BeforeEach
    public void setUp() {
        etiquetas = new ArrayList<>();
        etiqueta1 = new Etiqueta("Compras");
        etiqueta2 = new Etiqueta("Ahorros");
        etiquetas.add(etiqueta1);
        etiquetas.add(etiqueta2);
    }

    @Test
    public void testBuscarEtiquetaPorNombre_Encontrada() {
        Optional<Etiqueta> resultado = Etiqueta.buscarEtiquetaPorNombre(etiquetas, "Compras");
        assertTrue(resultado.isPresent(), "La etiqueta debería encontrarse en la lista.");
        assertEquals("Compras", resultado.get().getNombre(), "El nombre de la etiqueta encontrada no coincide con el nombre buscado.");
    }

    @Test
    public void testBuscarEtiquetaPorNombre_NoEncontrada() {
        Optional<Etiqueta> resultado = Etiqueta.buscarEtiquetaPorNombre(etiquetas, "Inversiones");
        assertFalse(resultado.isPresent(), "La etiqueta no debería encontrarse en la lista.");
    }

    @Test
    public void testConstructorConNombre() {
        Etiqueta etiqueta = new Etiqueta("Viajes");
        assertEquals("Viajes", etiqueta.getNombre(), "El nombre de la etiqueta no es el esperado.");
    }

    @Test
    public void testConstructorConNombreYUsuario() {
        Usuario usuario = new Usuario();
        Etiqueta etiqueta = new Etiqueta("Educación", usuario);
        assertEquals("Educación", etiqueta.getNombre(), "El nombre de la etiqueta no es el esperado.");
        assertEquals(usuario, etiqueta.getUsuario(), "El usuario asociado a la etiqueta no es el esperado.");
    }

    @Test
    public void testSetNombre() {
        etiqueta1.setNombre("Gastos");
        assertEquals("Gastos", etiqueta1.getNombre(), "El nombre de la etiqueta no fue actualizado correctamente.");
    }

    @Test
    public void testGetIdPorDefecto() {
        assertEquals(0, etiqueta1.getId(), "El id por defecto debería ser 0 ya que aún no se ha guardado en la base de datos.");
    }

    @Test
    void buscarEtiquetaPorNombre_Existente() {
        Usuario usuarioMock = mock(Usuario.class); // Mock de Usuario
        Etiqueta etiqueta1 = new Etiqueta("Etiqueta1", usuarioMock);
        Etiqueta etiqueta2 = new Etiqueta("Etiqueta2", usuarioMock);
        List<Etiqueta> etiquetas = Arrays.asList(etiqueta1, etiqueta2);
        Optional<Etiqueta> resultado = Etiqueta.buscarEtiquetaPorNombre(etiquetas, "Etiqueta1");
        assertTrue(resultado.isPresent(), "La etiqueta debería estar presente");
        assertEquals("Etiqueta1", resultado.get().getNombre(), "El nombre de la etiqueta debería ser 'Etiqueta1'");
    }

    @Test
    void buscarEtiquetaPorNombre_NoExistente() {
        Usuario usuarioMock = mock(Usuario.class); // Mock de Usuario
        Etiqueta etiqueta1 = new Etiqueta("Etiqueta1", usuarioMock);
        Etiqueta etiqueta2 = new Etiqueta("Etiqueta2", usuarioMock);
        List<Etiqueta> etiquetas = Arrays.asList(etiqueta1, etiqueta2);
        Optional<Etiqueta> resultado = Etiqueta.buscarEtiquetaPorNombre(etiquetas, "EtiquetaInexistente");
        assertFalse(resultado.isPresent(), "La etiqueta no debería estar presente");
    }

}