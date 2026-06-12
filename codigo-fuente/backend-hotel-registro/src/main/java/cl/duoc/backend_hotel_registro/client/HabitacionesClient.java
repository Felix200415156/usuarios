package cl.duoc.backend_hotel_registro.client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import cl.duoc.backend_hotel_registro.dto.HabitacionDto;

@FeignClient(name = "habitaciones-service", url = "http://172.31.89.29:8080")
public interface HabitacionesClient {

    @GetMapping("/api/habitaciones/{id}")
    HabitacionDto getHabitacion(@PathVariable("id") Long id);

}
