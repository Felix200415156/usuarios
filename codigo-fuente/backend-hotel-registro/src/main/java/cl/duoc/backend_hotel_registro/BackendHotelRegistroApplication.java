package cl.duoc.backend_hotel_registro;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableFeignClients
public class BackendHotelRegistroApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendHotelRegistroApplication.class, args);
	}

}
