package com.dmh.registerusers.controller;

import com.dmh.registerusers.entity.UserEntity;
import com.dmh.registerusers.service.UserService;
import com.dmh.registerusers.model.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Crear un usuario
    @PostMapping("/create")
    public ResponseEntity<String> createUser(@Validated @RequestBody UserDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error de validación: " + errorMessage);
        }
        try {
            userService.createUser(userDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuario creado exitosamente");
        } catch (IllegalArgumentException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: el usuario ya existe " + userDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el usuario");
        }
    }


    @PostMapping("/bulk")
    public ResponseEntity<String> createUsers(@RequestBody List<UserDTO> userDTOs) {
        userService.createUsersBulk(userDTOs);
        return ResponseEntity.status(HttpStatus.CREATED).body("Todos Usuarios creados exitosamente");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional> getUser(@PathVariable String id) {
        try {
            Optional<UserDTO> userDTO = Optional.ofNullable(userService.getUserById(id));
            return ResponseEntity.ok(userDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Obtener todos los usuarios
    @GetMapping
    public List<UserEntity> getUsers() {
        return userService.getAllUsers();
    }


    // Eliminar un usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.status(HttpStatus.OK).body("Usuario eliminado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(String.format("Error al eliminar el usuario con ID: %s no fue encontrado", id));
        }
    }
}
