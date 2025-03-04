package org.techtricks.artisanPlatform.models;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseUser {
    private String email;
    private String password;
    private Role role;
}
