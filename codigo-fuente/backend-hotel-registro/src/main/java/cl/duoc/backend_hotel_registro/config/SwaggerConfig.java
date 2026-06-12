package cl.duoc.backend_hotel_registro.config;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration 

public class SwaggerConfig {
     @Bean            // Spring ejecuta este método al iniciar y registra el resultado
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("API Registro de Usuarios")          // ← cambia por TU servicio
                .version("1.0")
                .description("Gestión de usuarios del sistema de registro"));
    }
}
