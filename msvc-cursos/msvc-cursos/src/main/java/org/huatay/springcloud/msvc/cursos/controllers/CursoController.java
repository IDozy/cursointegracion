package org.huatay.springcloud.msvc.cursos.controllers;

import feign.FeignException;
import org.huatay.springcloud.msvc.cursos.models.Usuario;
import org.huatay.springcloud.msvc.cursos.models.entity.Curso;
import org.huatay.springcloud.msvc.cursos.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

@RestController
@RequestMapping("/api/curso")

public class CursoController {

    @Autowired
    private CursoService service;

    @GetMapping
    public ResponseEntity<List<Curso>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id) {
        Optional<Curso> cursoOptional = service.porId(id);
        if (cursoOptional.isPresent()) {
            return ResponseEntity.ok(cursoOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping("/")
    public ResponseEntity<?> crear(@RequestBody Curso curso) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(curso));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@RequestBody Curso curso, @PathVariable Long id) {
        Optional<Curso> op = service.porId(id);
        if (op.isPresent()) {
            Curso cursoDB = op.get();
            cursoDB.setNombre(curso.getNombre());

            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(curso));
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Curso> op = service.porId(id);
        if (op.isPresent()) {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //metodos remotos

    @PutMapping("/asignar-usuario/{cursoId}")
    public ResponseEntity<?> asignarUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId) {
        return manejarDatos(service.asignarUsuario(usuario, cursoId), "asignar");
    }

    @PostMapping("/crear-usuario/{cursoId}")
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId) {
        return manejarDatos(service.crearUsuario(usuario, cursoId), "crear");
    }

    @DeleteMapping("/desasignar/{cursoId}")
    public ResponseEntity<?> eliminarUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId) {
        return  manejarDatos(service.deleteUsuarioCurso(usuario, cursoId), "eliminar");
    }


    private ResponseEntity<?> manejarDatos(Optional<Usuario> optionalUsuario, String operation) {
        if (optionalUsuario.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(optionalUsuario.get());
        } else {
            return manejarException(operation);
        }
    }

    private ResponseEntity<?> manejarException(String operation) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Collections.singletonMap("Mensaje: ", String.format("No se pudo %s el usuario o error en la comunicación", operation)));
    }

    @GetMapping("/cursouser/{cursoId}")
    public ResponseEntity<?> DetalleCursoUsers(@PathVariable Long cursoId) {
        Optional<Curso> op = service.porIDconUsuario(cursoId);
        if (op.isPresent()) {
            return ResponseEntity.ok(op.get());
        }
        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("eliminar-cursouser/{id}")
    public ResponseEntity<?> eliminarCursoUsuarioPorId(@PathVariable Long id) {
        service.eliminarCursoUsuarioPorID(id);
        return ResponseEntity.noContent().build();

    }

}

/*
    @PutMapping("/asignar-usuario/{cursoId}")
    public ResponseEntity<?> asignarUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId) {
        Optional<Usuario> o;
        try {
            o = service.asignarUsuario(usuario, cursoId);
        } catch (FeignException feignException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).
                    body(Collections.singletonMap("Mensaje: ", "No existe el usuario o error en la comunicacion " +
                            feignException.getMessage()));
        }
        if (o.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/crear-usuario/{cursoId}")
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId) {
        Optional<Usuario> o;
        try {
            o = service.crearUsuario(usuario, cursoId);
        } catch (FeignException feignException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).
                    body(Collections.singletonMap("Mensaje: ", "No se creo el usuario o error en la comunicacion " +
                            feignException.getMessage()));
        }
        if (o.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/desasignar/{cursoId}")
    public ResponseEntity<?> eliminarUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId) {
        Optional<Usuario> o;
        try {
            o = service.deleteUsuarioCurso(usuario, cursoId);
        } catch (FeignException feignException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).
                    body(Collections.singletonMap("Mensaje: ", "No se existe el usuario o error en la comunicacion " +
                            feignException.getMessage()));
        }
        if (o.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }
*/
