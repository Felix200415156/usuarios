package cl.duoc.backend_hotel_registro.service;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import cl.duoc.backend_hotel_registro.model.Usuario;
import cl.duoc.backend_hotel_registro.repository.UsuarioRepository;
import cl.duoc.backend_hotel_registro.dto.Usuariodto;
import cl.duoc.backend_hotel_registro.exception.RecursoNoEncontradoException;
import cl.duoc.backend_hotel_registro.dto.UsuarioCreateDTO;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Test Find All Usuarios")
    void debeRetornarListaDeUsuarios() {
     List<Usuario> usuariosSimulados = List.of(
            new Usuario(1L, "11111111-1", "Juan", "juan.perez@example.com", "123456789","56949426630"),
            new Usuario(2L, "22222222-2", "María", "maria.gomez@example.com", "987654321","56949426631")
        );
        when(usuarioRepository.findAll()).thenReturn(usuariosSimulados);
        List<Usuariodto> resultado = usuarioService.findAll();
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Juan", resultado.get(0).getNombreCompleto());
        assertEquals("María", resultado.get(1).getNombreCompleto());
        verify(usuarioRepository, times(1)).findAll();
    }
    @Test
    @DisplayName("findAll - debe retornar lista vacía cuando no hay Usuarios")
    void debeRetornarListaVaciaSiNoHayUsuarios() {
        // Given
        when(usuarioRepository.findAll()).thenReturn(List.of());
        // When
        List<Usuariodto> resultado = usuarioService.findAll();
        // Then
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }
 @Test
    @DisplayName("findDtoByRut - debe retornar el DTO correcto cuando el usuario existe")
    void debeRetornarUsuarioPorRut() {
        // Given
        String rut = "12345678-9";
        Usuario usuario = new Usuario(1L, rut, "Juan Pérez", "juan.perez@example.com", "password123", "123456789");
        
       
        when(usuarioRepository.findByRut(rut)).thenReturn(Optional.of(usuario));

        // When
        Usuariodto resultado = usuarioService.findDtoByRut(rut);

        // Then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Juan Pérez", resultado.getNombreCompleto());
        assertEquals(rut, resultado.getRut());
    }
    @Test
    @DisplayName("findById - debe lanzar RecursoNoEncontradoException cuando el usuario no existe")
    void debeLanzarExcepcionCuandoUsuarioNoExiste() {
        // Given
        when(usuarioRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RecursoNoEncontradoException.class, () ->
            usuarioService.findById(999L)
        );
    }
   @Test
    @DisplayName("registrarNuevoUsuario - debe persistir y retornar el usuario creado correctamente")
    void debeCrearUsuarioCorrectamente() {
        // Given
        UsuarioCreateDTO dto = new UsuarioCreateDTO(
            "12345678-9", "Juan Pérez", "juan.perez@example.com", "password123", "123456789"
        );
        Usuario guardado = new Usuario(3L, "12345678-9", "Juan Pérez", "juan.perez@example.com", "password123", "123456789");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(guardado);

        // When
        Usuariodto resultado = usuarioService.registrarNuevoUsuario(dto);

        // Then
        assertNotNull(resultado);
        assertEquals(3L, resultado.getId());
        assertEquals("Juan Pérez", resultado.getNombreCompleto());
        assertEquals("12345678-9", resultado.getRut());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }
@Test
    @DisplayName("eliminarPorRut - debe lanzar excepción al intentar eliminar un Rut inexistente")
    void debeLanzarExcepcionAlEliminarUsuarioInexistente() {
       
        String rutInexistente = "12345678-9";
        when(usuarioRepository.existsByRutIgnoreCase(rutInexistente)).thenReturn(false);

  
        assertThrows(RecursoNoEncontradoException.class, () ->
            usuarioService.eliminarPorRut(rutInexistente) 
        );
        
        verify(usuarioRepository, never()).deleteByRutIgnoreCase(anyString());
    }

    
}
