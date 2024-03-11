package org.ahuatay.springcloud.msvc.usuarios.controllers;


import jakarta.validation.Valid;
import org.ahuatay.springcloud.msvc.usuarios.models.entity.Usuario;
import org.ahuatay.springcloud.msvc.usuarios.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequestMapping("/api/usuario")
@RestController
public class UsuarioController {
    @Autowired
    private UsuarioService service;

    @GetMapping
    public List<Usuario> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id) {
        Optional<Usuario> usuarioOptional = service.porId(id);
        if (usuarioOptional.isPresent()) {
            return ResponseEntity.ok(usuarioOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Usuario usuario, BindingResult result) {
        if(service.porEmail(usuario.getEmail()).isPresent()){
            return  ResponseEntity.badRequest().body(Collections.singletonMap("HuatayCasas ","Ups!! ese correo ya esta siendo usado :c"));
        }
        if (result.hasErrors()) {
            return getMapResponseEntity(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar( @Valid @RequestBody Usuario usuario, BindingResult result, @PathVariable Long id ) {
        if (result.hasErrors()) {
            return getMapResponseEntity(result);
        }
        Optional<Usuario> op = service.porId(id);
        if (op.isPresent()) {
            Usuario usuarioDB = op.get();
            if(!usuario.getEmail().equalsIgnoreCase(usuarioDB.getEmail()) && service.porEmail(usuario.getEmail()).isPresent()){
                return ResponseEntity.badRequest().body(Collections.singletonMap("HuatayCasas", "Ya Existe el email en otro ususario"));
            }
            usuarioDB.setName(usuario.getName());
            usuarioDB.setEmail(usuario.getEmail());
            usuarioDB.setPassword(usuario.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(usuarioDB));
        } else {
            return ResponseEntity.notFound().build();
        }

    }


    private ResponseEntity<Map<String, String>> getMapResponseEntity(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "Huatay Casas " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Usuario> op = service.porId(id);
        if (op.isPresent()) {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/usuario-por-curso")
    public ResponseEntity<List<Usuario>>  listarAlumnosporCurso(@RequestParam List<Long> ids){
        return  ResponseEntity.ok(service.listarPorIds(ids));
    }


  /*  @DeleteMapping("deletecurso/{id}")
    public ResponseEntity<?> Desasignare(@PathVariable Long id) {
        Optional<Usuario> op = service.porId(id);
        if (op.isPresent()) {
            service.eliminarCurso(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
*/
}
