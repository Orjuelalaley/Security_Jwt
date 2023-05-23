package com.auth.security.auth;

import com.auth.security.Exceptions.UserExceptions.UsuarioFoundException;
import com.auth.security.Exceptions.UserExceptions.UsuarioNotFoundException;
import com.auth.security.entity.User;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Operation(summary = "Add a new User received as a parameter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created", content = { @Content(mediaType = "application/json",schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioNotFoundException.class))),
            @ApiResponse(responseCode = "404", description = "User with given id already exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioFoundException.class))),
            @ApiResponse(responseCode = "406", description = "User nickname unavaliable", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioNotFoundException.class))) })
    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody RegisterRequest registerRequest) {
        if (registerRequest == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo registrar el usuario");
        }
        if (registerRequest.getEmail() == null || registerRequest.getEmail().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El email es obligatorio");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body( "Usuario Creado con exito, este es su token:  " + authenticationService.register(registerRequest));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(
            @RequestBody AuthenticationRequest authenticationRequest) {
        if (authenticationRequest == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo autenticar el usuario");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Usuario autenticado con exito, este es su token:  " + authenticationService.authenticate(authenticationRequest));
    }
}