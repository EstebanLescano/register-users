package com.dmh.registerusers.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {

    private String nombreyapellido;
    private String dni;
    private String email;
    private String telefono;
    private String password;
    private String cvu;
    private String alias;

    public UserDTO(String nombreyapellido, String dni, String email, String telefono, String password, String cvu, String alias) {
        this.nombreyapellido = nombreyapellido;
        this.dni = dni;
        this.email = email;
        this.telefono = telefono;
        this.password = password;
        this.cvu = cvu;
        this.alias = alias;
    }
}
