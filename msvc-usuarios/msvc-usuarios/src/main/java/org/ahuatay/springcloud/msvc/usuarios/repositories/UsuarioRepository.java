package org.ahuatay.springcloud.msvc.usuarios.repositories;

import org.ahuatay.springcloud.msvc.usuarios.models.entity.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository  extends CrudRepository<Usuario, Long > {

    Optional<Usuario> findByEmail(String email);

}
