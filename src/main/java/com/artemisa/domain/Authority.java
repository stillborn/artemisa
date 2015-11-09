package com.artemisa.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "authority")
public class Authority extends BaseEntity implements Serializable, GrantedAuthority {
    
    private static final long serialVersionUID = 727437591010934530L;
    
    private String authority;
    
    private List<User> userList;
    
    public Authority(){
        
    }
    
    public void setAuthority(String authority) {
        this.authority = authority;
    }
    
    @Column(name = "authority", nullable = false, length = 45)
    @Override
    public String getAuthority() {
        return this.authority;
    }
    
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "user_has_authority", joinColumns = {
        @JoinColumn(name = "user_id", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "authority_id",
                    nullable = false, updatable = false) })
    public List<User> getUserList() {
        return userList;
    }
    
    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
    
    @Override
    public String toString() {
        return authority;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        if(getClass() != obj.getClass())
            return false;
        final Authority other = (Authority)obj;
        if(!Objects.equals(this.authority, other.getAuthority()))
            return false;
        return true;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.authority);
        return hash;
    }
}
