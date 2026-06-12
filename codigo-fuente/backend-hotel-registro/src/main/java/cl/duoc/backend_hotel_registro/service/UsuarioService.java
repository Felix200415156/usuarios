package cl.duoc.backend_hotel_registro.service;
import cl.duoc.backend_hotel_registro.client.HabitacionesClient;

import cl.duoc.backend_hotel_registro.dto.UsuarioCreateDTO;
import cl.duoc.backend_hotel_registro.dto.Usuariodto;
import cl.duoc.backend_hotel_registro.model.Usuario;
import cl.duoc.backend_hotel_registro.repository.UsuarioRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

@Service
public class UsuarioService {

        @Autowired
      private  UsuarioRepository usuarioRepository;
        public Optional<Usuario> findByRut(String rut) {
            return usuarioRepository.findByRut(rut);
}

    private HabitacionesClient hotelClient;
    private static final Logger log = LoggerFactory.getLogger(UsuarioService.class);
        public void procesarYEnviarCliente(Usuariodto dto) {
        log.info("Procesando cliente localmente: {}", dto.getNombreCompleto());
        

        
        log.info("Enviando datos a la AWS del servicio de hoteles...");
        hotelClient.getHabitacion(dto.getId());
        log.info("¡Datos enviados con éxito!");
    }

    
 public Usuariodto findDtoByRut(String rut) {
   
    return usuarioRepository.findByRut(rut).map(usuario -> {

        return new Usuariodto(
            usuario.getId(),
            usuario.getRut(),
            usuario.getNombreCompleto(),
            usuario.getGmail(),
            usuario.getTelefono()
        );
    }).orElse(null);
    }
public Usuariodto registrarNuevoUsuario(UsuarioCreateDTO dto) {
  
    Usuario usuario = new Usuario();
    usuario.setRut(dto.getRut());
    usuario.setNombreCompleto(dto.getNombreCompleto());
    usuario.setGmail(dto.getGmail());
    usuario.setTelefono(dto.getTelefono());
    usuario.setContraseña(dto.getContraseña());

 
    Usuario guardado = usuarioRepository.save(usuario);


    return new Usuariodto(
        guardado.getId(),
        guardado.getRut(),
        guardado.getNombreCompleto(),
        guardado.getGmail(),
        guardado.getTelefono()
    );
}
  
    public Usuariodto entidadADto(Usuario usuario) {
    Usuariodto dto = new Usuariodto();
    dto.setRut(usuario.getRut());
    dto.setNombreCompleto(usuario.getNombreCompleto());
    dto.setGmail(usuario.getGmail());
    dto.setTelefono(usuario.getTelefono());
    return dto;
}


    public UsuarioService(UsuarioRepository usuarioRepository){
        this.usuarioRepository= usuarioRepository;
    }


    public List <Usuario> obtenerTodos(){
        return usuarioRepository.findAll();
    }
    public List <String> actualizarUsuario(Usuario usuario){
        List<String>errores = new ArrayList<>();
        if(usuario.getRut()== null || usuario.getRut().trim().isEmpty()){
            errores.add("El rut no puede estar vacio");}
        if(usuario.getContraseña() == null || usuario.getContraseña().trim().length() < 8) {
            errores.add("La contraseña debe tener un mínimo de 8 caracteres.");}
        if(usuario.getNombreCompleto() == null || usuario.getNombreCompleto().trim().isEmpty()){
            errores.add("El nombre no puede estar vacio");}
   if (usuario.getGmail() == null || usuario.getGmail().trim().isEmpty()) {
    errores.add("Su Gmail no puede estar vacio.");
    } else if (!usuario.getGmail().contains("@") || !usuario.getGmail().contains(".")) {
    errores.add("El formato del correo electrónico no es válido.");
    }
    if(usuario.getTelefono() == null || usuario.getTelefono().trim().isEmpty()){
        errores.add("El telefono no puede estar vacio.");
    }
    return errores;
    }
    public Usuario guardarUsuario(Usuario usuario){
        return usuarioRepository.save(usuario);

    }
public Usuario actualizarPorRut(String rut, Usuario nuevosDatos) {
    return usuarioRepository.findByRut(rut).map(usuario -> {
    
        usuario.setNombreCompleto(nuevosDatos.getNombreCompleto());
        usuario.setGmail(nuevosDatos.getGmail());
        usuario.setTelefono(nuevosDatos.getTelefono());
        
       
        usuario.setContraseña((nuevosDatos.getContraseña())); 
        
        return usuarioRepository.save(usuario);
    }).orElse(null);
}

    public boolean eliminarPorRut(String rut){
        if(usuarioRepository.existsByRutIgnoreCase(rut)){
            usuarioRepository.deleteByRutIgnoreCase(rut);
            return true;

        }
        return false;
    }

}

