package com.restaurant.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "user")
@SQLDelete(sql = "UPDATE user SET deleted = true WHERE id = ?")
@FilterDef(name = "deletedItemFilter", parameters = @ParamDef(name = "isDeleted", type = "boolean"))
@Filter(name = "deletedItemFilter", condition = "deleted = :isDeleted")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
public abstract class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "first_name", nullable = false)
    protected String firstName;

    @Column(name = "last_name", nullable = false)
    protected String lastName;

    @Column(name = "email", nullable = false)
    protected String email;

    @Column(name = "phone_number", nullable = false)
    protected String phoneNumber;

    @Column(name = "role", insertable = false, updatable = false)
    protected String role;

    @Column(name = "deleted", nullable = false)
    protected Boolean deleted;

    @JsonIgnore
    private Timestamp lastPasswordResetDate;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<Authority> authorities = new ArrayList<>();
        authorities.add(new Authority("ROLE_USER"));

        if (this instanceof Admin) {
            authorities.add(new Authority("ROLE_ADMIN"));
        }
        if (this instanceof Manager) {
            authorities.add(new Authority("ROLE_MANAGER"));
        }
        if (this instanceof Staff) {
            authorities.add(new Authority("ROLE_STAFF"));
        }
        if (this instanceof Waiter) {
            authorities.add(new Authority("ROLE_WAITER"));
        }
        if (this instanceof Barman) {
            authorities.add(new Authority("ROLE_BARMAN"));
        }
        if (this instanceof Cook) {
            authorities.add(new Authority("ROLE_COOK"));
        }

        return authorities;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
