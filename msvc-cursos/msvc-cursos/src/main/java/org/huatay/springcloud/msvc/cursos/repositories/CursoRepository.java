package org.huatay.springcloud.msvc.cursos.repositories;

import org.huatay.springcloud.msvc.cursos.models.entity.Curso;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CursoRepository extends CrudRepository<Curso, Long> {

    @Modifying
    @Query("delete from cursos_usuarios cu where cu.usuarioID = ?1")
    void eliminarCursoUsuarioPorId(Long id);


}
