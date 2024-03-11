package org.ahuatay.springcloud.msvc.usuarios.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-cursos", url = "localhost:8002/api/curso")
public interface CursoClientRest {

    @DeleteMapping("eliminar-cursouser/{id}")
   void eliminarCursoUsuarioPorId(@PathVariable Long id);
}
