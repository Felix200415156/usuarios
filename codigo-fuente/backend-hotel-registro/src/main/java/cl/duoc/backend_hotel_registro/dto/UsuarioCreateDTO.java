package cl.duoc.backend_hotel_registro.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioCreateDTO {
@NotBlank(message = "El rut no puede estar vacio")
private String rut;

@NotBlank(message = "El nombre no puede estar vacio")
private String nombreCompleto;

@NotBlank(message = "Su Gmail no puede estar vacio.")
private String gmail;

@NotBlank(message = "La contraseña no puede estar vacía.")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres.")
    private String contraseña;



@NotBlank(message = "El telefono no puede estar vacio.")
private String telefono;

}
