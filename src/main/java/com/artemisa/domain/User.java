package com.artemisa.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "user")
public class User implements UserDetails, Serializable{
    
    private static final long serialVersionUID = 3421612619206388488L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long userId;
    
    @Column(name = "name", nullable = false, length = 45)
    private String name;
    
    @Column(name = "lastname", nullable = false, length = 45)
    private String lastname;
    
    @Column(name = "username", nullable = false, length = 25, unique = true)
    private String username;
    
    @Column(name = "password", nullable = false, length = 40)
    private String password;
        
    
    @Column(name = "accountNonExpired")
    @Type(type = "yes_no")
    private boolean accountNonExpired;
    
    @Column(name = "credentialsNonExpired")
    @Type(type = "yes_no")
    private boolean credentialsNonExpired;
    
    @Column(name = "enabled")
    @Type(type = "yes_no")
    private boolean enabled;
    
    @Column(name = "accountNonLocked")
    @Type(type = "yes_no")
    private boolean accountNonLocked;
    
    @Column(name = "failedAuthenticationAttemps")
    private int failedAuthenticationAttemps;
    
    @Column(name = "isLogged")
    @Type(type = "yes_no")
    private boolean isLogged;
    
    @Column(name = "lastLoginDate")
    private Date lastLoginDate;
    
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "userList")
    private List<Authority> roles;
    
    public void setRoles(List<Authority> authorities) {
        this.roles = authorities;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return (Collection<? extends GrantedAuthority>) roles;
    }
    
    public List<Authority> getRoles(){
        return roles;
    }
    
    @Override
    public String getPassword() {
        return this.password;
    }
    
    @Override
    public String getUsername() {
        return this.username;
    }
    
    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }
    
    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }
    
    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }
    
    @Override
    public boolean isAccountNonLocked() {
        
        return accountNonLocked;
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        
        return credentialsNonExpired;
    }
    
    @Override
    public boolean isEnabled() {
        return enabled;
    }
    
    public Long getId() {
        return userId;
    }
    
    public void setId(Long userId) {
        this.userId = userId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getLastname() {
        return lastname;
    }
    
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public int getFailedAuthenticationAttemps() {
        return failedAuthenticationAttemps;
    }
    
    public void setFailedAuthenticationAttemps(int failedAuthenticationAttemps) {
        this.failedAuthenticationAttemps = failedAuthenticationAttemps;
    }
    
    public boolean isLogged() {
        return isLogged;
    }
    
    public void setLogged(boolean isLogged) {
        this.isLogged = isLogged;
    }
    
    public Date getLastLoginDate() {
        return lastLoginDate;
    }
    
    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        if(getClass() != obj.getClass())
            return false;
        final User other = (User)obj;
        if(!Objects.equals(this.username, other.getUsername()))
            return false;
        return true;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.username);
        return hash;
    }
}