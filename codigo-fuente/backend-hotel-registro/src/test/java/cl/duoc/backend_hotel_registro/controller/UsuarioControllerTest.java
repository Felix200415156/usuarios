package cl.duoc.backend_hotel_registro.controller;
import cl.duoc.backend_hotel_registro.dto.Usuariodto;
import cl.duoc.backend_hotel_registro.exception.GlobalExceptionHandler;
import cl.duoc.backend_hotel_registro.exception.RecursoNoEncontradoException;
import cl.duoc.backend_hotel_registro.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioControllerTest {

    @Mock
    private UsuarioService service;

    @InjectMocks
    private UsuarioController controller;

    private MockMvc mockMvc;

     @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }
     @Test
    @DisplayName("GET /api/usuarios - debe retornar 200 con la lista de usuarios")
    void debeRetornar200CuandoSePidenUsuarios() throws Exception {
        // Given
        when(service.findAll()).thenReturn(List.of(
                new Usuariodto(1L, "21.123.456-7", "Juan Pérez", "juan.perez@example.com", "123456789"),
                new Usuariodto(2L, "21.123.456-8", "María García", "maria.garcia@example.com", "987654321")
        ));
        // When
        mockMvc.perform(get("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @DisplayName("GET /api/usuarios/{rut} - debe retornar 404 cuando el usuario no existe")
    void debeRetornar404CuandoUsuarioNoExiste() throws Exception {
        when(service.findDtoByRut(any(String.class))).thenThrow(new RecursoNoEncontradoException("Usuario no encontrado"));
mockMvc.perform(get("/api/usuarios/21.123.456-9")
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print()) // <--- ESTO IMPRIMIRÁ TODA LA RESPUESTA EN LA CONSOLA
        .andExpect(status().isNotFound());

    }
    @Test
    @DisplayName("POST /api/usuarios - debe retornar 201 al crear un usuario válido")
    void debeRetornar201AlCrearUsuario() throws Exception {
        String json = """
                {
                    "rut": "21.123.456-7",
                    "nombreCompleto": "Juan Pérez",
                    "gmail": "juan.perez@example.com",
                    "telefono": "123456789"
                }
                """;
                when(service.registrarNuevoUsuario(any())).thenReturn(new Usuariodto(1L, "21.123.456-7", "Juan Pérez", "juan.perez@example.com", "123456789"));
                mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                        .andExpect(status().isCreated());
    }
    @Test
    @DisplayName("POST /api/usuarios - debe retornar 400 cuando el nombre está en blanco")
    void debeRetornar400CuandoNombreEstaVacio() throws Exception {
        String json = """
                {
                    "rut": "21.123.456-7",
                    "nombreCompleto": "",
                    "gmail": "juan.perez@example.com",
                    "telefono": "123456789"
                }
                """;
                mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                        .andExpect(status().isBadRequest());
    }
}