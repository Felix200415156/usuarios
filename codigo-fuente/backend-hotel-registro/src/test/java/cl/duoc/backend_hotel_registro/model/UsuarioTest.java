package cl.duoc.backend_hotel_registro.model;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.*;


public class UsuarioTest {


  @Test
    @DisplayName("Constructor vacío - debe crear una instancia no nula")
    void constructorVacioDebeCrearInstanciaNoNula() {
        Usuario usuario = new Usuario();
        assertNotNull(usuario);
    }
     @Test
    @DisplayName("toString - debe tener rut")
    void toStringDebeContenerRut() {
        Usuario usuario = new Usuario(1L, "12345678-9", "Juan Pérez", "juan.perez@example.com", "password123", "123456789");

        String texto = usuario.toString();

        assertNotNull(texto);
        assertTrue(texto.contains("12345678-9"));
    }
           @Test
    @DisplayName("Constructor completo - debe asignar todos los campos correctamente")
    void constructorCompletoDebeAsignarTodosLosCampos() {
        Usuario usuario = new Usuario(
            1L, "12345678-9", "Juan Pérez", "juan.perez@example.com", "password123", "123456789");
        assertNotNull(usuario);
        assertEquals(1L, usuario.getId());
        assertEquals("12345678-9", usuario.getRut());
        assertEquals("Juan Pérez", usuario.getNombreCompleto());
        assertEquals("juan.perez@example.com", usuario.getGmail());
        assertEquals("password123", usuario.getContraseña());
        assertEquals("123456789", usuario.getTelefono());}
        @Test
    @DisplayName("Setters - debe permitir modificar cada campo individualmente")
    void settersDebenPermitirModificarCampos() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setRut("12345678-9");
        usuario.setNombreCompleto("Juan Pérez");
        usuario.setGmail("juan.perez@example.com");
        usuario.setContraseña("password123");
        usuario.setTelefono("123456789");

        assertEquals(1L, usuario.getId());
        assertEquals("12345678-9", usuario.getRut());
        assertEquals("Juan Pérez", usuario.getNombreCompleto());
        assertEquals("juan.perez@example.com", usuario.getGmail());
        assertEquals("password123", usuario.getContraseña());
        assertEquals("123456789", usuario.getTelefono());}
           @Test
    @DisplayName("equals y hashCode - dos productos con los mismos datos deben ser iguales")
    void dosUsuariosConMismosDatosDebenSerIguales() {
        Usuario u1 = new Usuario(1L, "12345678-9", "Juan Pérez", "juan.perez@example.com", "password123", "123456789");
        Usuario u2 = new Usuario(1L, "12345678-9", "Juan Pérez", "juan.perez@example.com", "password123", "123456789");

        assertEquals(u1, u2);
        assertEquals(u1.hashCode(), u2.hashCode());
    }
       @Test
    @DisplayName("toString - debe contener el nombre del usuario en la representación")
    void toStringDebeContenerNombreDelUsuario() {
        Usuario usuario = new Usuario(1L, "12345678-9", "Juan Pérez", "juan.perez@example.com", "password123", "123456789");

        String texto = usuario.toString();

        assertNotNull(texto);
        assertTrue(texto.contains("Juan Pérez"));
    }
    }
