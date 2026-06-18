package cl.duoc.backend_hotel_registro.repository;
import cl.duoc.backend_hotel_registro.model.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UsuarioRepositoryTest {
@Autowired
    private UsuarioRepository repository;

    @Test
    @DisplayName("save - debe persistir el usuario y asignar un ID generado automáticamente")
    void debePersistirUsuarioYAsignarIdGenerado() {
        Usuario usuario = new Usuario( null, 
            "21.123.456-7", 
            "Juan Pérez", 
            "juan.perez@example.com", 
            "password123", 
            "123456789"
        );
        Usuario usuarioGuardado = repository.save(usuario);

        
        assertNotNull(usuarioGuardado, "El usuario guardado no debería ser nulo");
        assertNotNull(usuarioGuardado.getId(), "JPA debería haber generado y asignado un ID automáticamente");
        assertEquals("21.123.456-7", usuarioGuardado.getRut(), "El RUT debe coincidir con el ingresado");
        assertEquals("Juan Pérez", usuarioGuardado.getNombreCompleto());
    }
    
    @Test
    @DisplayName("findAll - debe retornar todos los usuarios guardados en la BD")
    void debeRetornarTodosLosUsuariosGuardados() {
        repository.save(new Usuario(null, "21.123.456-7", "Juan Pérez", "juan.perez@example.com", "password123", "123456789"));
        repository.save(new Usuario(null, "21.123.456-8", "María García", "maria.garcia@example.com", "password456", "987654321"));
        List<Usuario> usuarios = repository.findAll();
        assertNotNull(usuarios);
        assertEquals(2, usuarios.size());
    }
    @Test
    @DisplayName("findById - debe retornar el usuario correcto cuando el ID existe")
    void debeEncontrarUsuarioPorIdExistente() {
    Usuario usuario = repository.save(new Usuario(null, "21.123.456-7", "Juan Pérez", "juan.perez@example.com", "password123", "123456789"));
    Optional<Usuario> usuarioEncontrado = repository.findById(usuario.getId());
        assertTrue(usuarioEncontrado.isPresent());
        assertEquals("Juan Pérez", usuarioEncontrado.get().getNombreCompleto());
        assertEquals("21.123.456-7", usuarioEncontrado.get().getRut());

    }
     @Test
    @DisplayName("findById - debe retornar Optional vacío cuando el ID no existe")
    void debeRetornarOptionalVacioCuandoIdNoExiste() {
        Optional<Usuario> usuarioEncontrado = repository.findById(999L);
        assertFalse(usuarioEncontrado.isPresent());
    }

     @Test
    @DisplayName("deleteById - debe eliminar el usuario de la base de datos")
    void debeEliminarUsuarioPorId() {
        Usuario usuario = repository.save(new Usuario(null, "21.123.456-7", "Juan Pérez", "juan.perez@example.com", "password123", "123456789"));
      Long id = usuario.getId();
      repository.deleteById(id);
      assertFalse(repository.findById(id).isPresent());
    }
}