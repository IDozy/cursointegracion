package org.huatay.springcloud.msvc.cursos.services;

import org.apache.juli.logging.Log;
import org.huatay.springcloud.msvc.cursos.models.entity.Curso;

import java.util.List;
import java.util.Optional;

public interface CursoService {

    List<Curso> listar();
    Optional<Curso> porId(Long id);
    Curso guardar (Curso curso);
    void eliminar (Long id);
}
