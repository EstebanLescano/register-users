package com.dmh.registerusers.model;


import jakarta.validation.constraints.Email;

public class UserDTO {

    private String nombreyapellido;
    private String dni;
    @Email(message = "El email debe tener un formato válido.")
    private String email;
    private String telefono;
    private String password;
    private String cvu;
    private String alias;

    public UserDTO() {
    }

    public UserDTO(String nombreyapellido, String dni, String email, String telefono, String password, String cvu, String alias) {
        this.nombreyapellido = nombreyapellido;
        this.dni = dni;
        this.email = email;
        this.telefono = telefono;
        this.password = password;
        this.cvu = cvu;
        this.alias = alias;
    }

    public String getNombreyapellido() {
        return nombreyapellido;
    }

    public void setNombreyapellido(String nombreyapellido) {
        this.nombreyapellido = nombreyapellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCvu() {
        return cvu;
    }

    public void setCvu(String cvu) {
        this.cvu = cvu;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
