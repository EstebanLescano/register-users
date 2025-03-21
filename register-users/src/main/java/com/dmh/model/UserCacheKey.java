package com.dmh.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

/**
 * crear una clave compuesta o personalizada para la caché
 */

@Data
@NoArgsConstructor
public class UserCacheKey implements Serializable {

    private String id;

    public UserCacheKey(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        UserCacheKey that = (UserCacheKey) obj;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}