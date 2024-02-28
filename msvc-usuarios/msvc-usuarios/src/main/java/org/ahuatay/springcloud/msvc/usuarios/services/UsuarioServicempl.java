package org.ahuatay.springcloud.msvc.usuarios.services;

import org.ahuatay.springcloud.msvc.usuarios.models.entity.Usuario;
import org.ahuatay.springcloud.msvc.usuarios.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServicempl implements UsuarioService {

    @Autowired // inyecta la dependencia de una clase que tiene metodos a otra clase
    private UsuarioRepository repository;

    @Override
    @Transactional(readOnly = true) //solo lectura
    public List<Usuario> listar() {
        return (List<Usuario>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> porId(Long id) {
        return repository.findById(id);
    }

    @Override
    public Usuario guardar(Usuario usuario) {
        return repository.save(usuario);
    }

    @Override
    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Usuario> porEmail(String email)  { return repository.findByEmail(email);}
}
