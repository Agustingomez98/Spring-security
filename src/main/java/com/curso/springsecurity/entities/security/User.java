package com.curso.springsecurity.entities.security;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tbl_user")
public class User implements UserDetails { //Importante esta implementacion, ya que representa al usuario logueado y posee metodos para saber datos particulares del usuario

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String email;
    @Column(name = "fullname")
    private String fullName;
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { //Devuelve los permisos concebidos de acuerdo a mi rol
       if (role ==null) return null;

       if (role.getPermissions()==null) return null;

       List<SimpleGrantedAuthority> authorities = role.getPermissions().stream()
               .map(each -> each.getOperation().getName())
               .map(each -> new SimpleGrantedAuthority(each)
               ).collect(Collectors.toList());

       authorities.add(new SimpleGrantedAuthority("ROLE_" + this.role.getName()));
       return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
