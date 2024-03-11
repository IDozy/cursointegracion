package org.huatay.springcloud.msvc.cursos.services;

import org.apache.juli.logging.Log;
import org.huatay.springcloud.msvc.cursos.models.Usuario;
import org.huatay.springcloud.msvc.cursos.models.entity.Curso;

import java.util.List;
import java.util.Optional;

public interface CursoService {

    List<Curso> listar();
    Optional<Curso> porId(Long id);
    Curso guardar (Curso curso);
    void eliminar (Long id);

    //metodos remotos relacionados al cliente http

    Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId);
    Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId);
    Optional<Usuario> deleteUsuarioCurso(Usuario usuario, Long cursoId);
    Optional<Curso> porIDconUsuario(Long id);

    void eliminarCursoUsuarioPorID(Long id);
}
