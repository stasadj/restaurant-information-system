package com.restaurant.backend.domain;

import org.springframework.security.core.GrantedAuthority;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Authority implements GrantedAuthority {

    private static final long serialVersionUID = 1L;

    String name;

    @Override
    public String getAuthority() {
        return name;
    }
}
