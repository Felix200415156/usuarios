package cl.duoc.backend_hotel_registro.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Usuario {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@NotBlank(message = "El rut no puede estar vacio")
private String rut;

@NotBlank(message = "El nombre no puede estar vacio")
private String nombreCompleto;

@NotBlank(message = "Su Gmail no puede estar vacio.")
private String gmail;

@NotBlank(message = "La contraseña es obligatoria.")
    @Size(min = 8, message = "Su contraseña debe tener un minimo de 8 caracteres.")
    private String contraseña;

@NotBlank(message = "El telefono no puede estar vacio.")
private String telefono;
}
