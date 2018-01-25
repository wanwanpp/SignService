package com.site.model.login;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "member")
@Setter
@Getter
public class Member implements UserDetails { //1

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Integer id;
    private Long stuId;

    @Column(name = "names")
    private String name;
    private Integer grade;
    private String loginName;
    private String pwd;
    @Column(name = "groups")
    private Integer group;
    private Integer isleader;
    private Integer isstart;
    private Long recordId;
    private Integer sex;
    private String phone;

    @ManyToMany(mappedBy = "members")
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore   //禁止此字段序列化
    private List<Role> roles = new ArrayList<>();

//    获取所有的权限
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        SimpleGrantedAuthority继承自GrantedAuthority
        List<SimpleGrantedAuthority> auths = new ArrayList<SimpleGrantedAuthority>();
        List<Role> roles = this.getRoles();
        for (Role role : roles) {
            auths.add(new SimpleGrantedAuthority(role.getMark()));
        }
        return auths;
    }

    @Override
    public String getPassword() {
        return pwd;
    }

    @Override
    public String getUsername() {
        return name;
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
