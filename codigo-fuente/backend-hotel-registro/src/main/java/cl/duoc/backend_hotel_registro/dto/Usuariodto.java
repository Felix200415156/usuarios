package cl.duoc.backend_hotel_registro.dto;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class Usuariodto {
private Long id;
private String rut;
private String nombreCompleto;
private String gmail;
private String telefono;
}
