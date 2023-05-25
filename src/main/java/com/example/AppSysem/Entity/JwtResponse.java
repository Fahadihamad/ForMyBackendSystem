package com.example.AppSysem.Entity;

public class JwtResponse {

    private Users users;
    private String jwtToken;

    public JwtResponse(Users users, String jwtToken) {
        this.users = users;
        this.jwtToken = jwtToken;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
