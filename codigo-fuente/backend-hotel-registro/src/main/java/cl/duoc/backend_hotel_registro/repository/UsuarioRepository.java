package cl.duoc.backend_hotel_registro.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.transaction.annotation.Transactional;

import cl.duoc.backend_hotel_registro.model.Usuario;

import java.util.Optional;






public interface UsuarioRepository extends JpaRepository<Usuario,Long>{

    Optional<Usuario>findByGmail(String gmail);
    Optional<Usuario> findByRut(String rut);
    boolean existsByRutIgnoreCase(String rut);

   @Transactional
   void deleteByRutIgnoreCase(String rut);
}
