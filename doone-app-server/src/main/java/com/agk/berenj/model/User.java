package com.agk.berenj.model;

import com.agk.berenj.model.audit.DateAudit;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by rajeevkumarsingh on 01/08/17.
 */

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "username"
        })
//        ,
//        @UniqueConstraint(columnNames = {
//            "email"
//        })
})
public class User extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @NotBlank
    @Size(max = 40)
    private String name;

    /**
     * username is same phoneNumber
     */
    @NotBlank
    @Size(max = 11)
    private String username;
    @NotBlank
    @Size(max = 11)
    private String purePassword;


//    @NaturalId
//    @NotBlank
//    @Size(max = 40)
//    @Email
//    private String email;

//    @NotBlank
    @Size(max = 100)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_addresses",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "address_id"))
    private Set<Address> addresses = new HashSet<>();


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User() {

    }

    public User(String name, String username, String purePassword, String password) {
        this.name = name;
        this.username = username;
        this.purePassword = purePassword;
        this.password = password;
    }

    public User(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.purePassword = purePassword;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public String getPurePassword() {
        return purePassword;
    }

    public void setPurePassword(String purePassword) {
        this.purePassword = purePassword;
    }
}