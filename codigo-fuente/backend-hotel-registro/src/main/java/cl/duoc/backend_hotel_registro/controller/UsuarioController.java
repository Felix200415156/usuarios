package cl.duoc.backend_hotel_registro.controller;
import cl.duoc.backend_hotel_registro.model.Usuario;
import cl.duoc.backend_hotel_registro.service.UsuarioService;
import jakarta.validation.Valid;
import cl.duoc.backend_hotel_registro.dto.UsuarioCreateDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cl.duoc.backend_hotel_registro.dto.Usuariodto;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Usuarios", description = "Operaciones de gestión de usuarios")
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    
private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

   @Operation(summary = "Obtener usuario por RUT", description = "Busca un usuario registrado utilizando su RUT único")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario encontrado y retornado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado con el RUT proporcionado")
    })
    @GetMapping("/{rut}")
    public ResponseEntity<?> getUsuario(@PathVariable String rut) {
        Usuariodto dto = usuarioService.findDtoByRut(rut);
        if (dto != null) {
            return ResponseEntity.ok(dto);
        }
        return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
    }

   @Operation(summary = "Listar todos los usuarios", description = "Obtiene una lista de todos los usuarios registrados")
    @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        return new ResponseEntity<>(usuarioService.obtenerTodos(), HttpStatus.OK);
    }

    @Operation(summary = "Registrar nuevo usuario", description = "Crea un nuevo usuario con los datos proporcionados")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o usuario con RUT ya existente")
    })
    @PostMapping("/registrar")
    public ResponseEntity<Usuariodto> crearUsuario(@Valid @RequestBody UsuarioCreateDTO dto) {
        Usuariodto creado = usuarioService.registrarNuevoUsuario(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @Operation(summary = "Enviar datos a compañero", description = "Envía los datos del usuario hacia la AWS de tu compañero")  
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Datos enviados exitosamente a la AWS del compañero"),
        @ApiResponse(responseCode = "500", description = "Error al conectar con el servidor externo o procesar los datos")
    })
    @PostMapping("/enviar-compañero")
    public ResponseEntity<String> enviarAlCompañero(@RequestBody Usuariodto dto) {
        try {
            usuarioService.procesarYEnviarCliente(dto);
            return ResponseEntity.ok("¡Datos mapeados y despachados hacia la AWS de tu compañero con éxito!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al conectar con el servidor externo: " + e.getMessage());
        }
    }

    @Operation(summary = "Actualizar usuario", description = "Actualiza los datos de un usuario existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado con el RUT proporcionado"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PutMapping("/actualizar/{rut}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable String rut, @Valid @RequestBody Usuario usuarioData) {
        try {
            Usuario usuarioActualizado = usuarioService.actualizarPorRut(rut, usuarioData);
            if (usuarioActualizado != null) {
                return new ResponseEntity<>(usuarioActualizado, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Usuario con RUT " + rut + " no encontrado.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario registrado utilizando su RUT único")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado con el RUT proporcionado")
    })
    @DeleteMapping("/{rut}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable String rut) {
        boolean eliminado = usuarioService.eliminarPorRut(rut);
        if (eliminado) {
            return new ResponseEntity<>("Usuario eliminado", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }
    }
}



