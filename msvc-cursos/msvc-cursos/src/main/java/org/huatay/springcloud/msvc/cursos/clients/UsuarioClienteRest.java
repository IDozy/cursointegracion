package org.huatay.springcloud.msvc.cursos.clients;

import org.huatay.springcloud.msvc.cursos.models.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "msvc-usuarios", url = "localhost:8001/api/usuario")
public interface UsuarioClienteRest {
    @GetMapping("/{id}")
    Usuario detalle(@PathVariable Long id);
    @PostMapping
    Usuario crear(@RequestBody Usuario usuario);

    @GetMapping("/usuario-por-curso")
    List<Usuario> alumnosPorIds(@RequestParam Iterable<Long> ids);


}
