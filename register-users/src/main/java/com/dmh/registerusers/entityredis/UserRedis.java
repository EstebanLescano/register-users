package com.dmh.registerusers.entityredis;

import lombok.*;

@Data
@NoArgsConstructor

public class UserRedis {

    private String id;
    private String nombreyapellido;
    private String dni;
    private String email;
    private String telefono;
    private String password;
    private String cvu;
    private String alias;
}
