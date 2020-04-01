package com.agk.berenj.payload;

import com.agk.berenj.model.Address;

import java.util.Set;

public class UserInfo {
    private String username;
    private String name;
    private Set<Address> addresses ;

    public UserInfo(String username, String name, Set<Address> addresses) {
        this.username = username;
        this.name = name;
        this.addresses = addresses;
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

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }
}
