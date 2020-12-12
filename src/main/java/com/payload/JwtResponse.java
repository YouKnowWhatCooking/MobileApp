package com.payload;

import java.io.Serializable;

public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private final String jwttoken;
    private boolean isAdmin;
    private String name;

    public JwtResponse(String jwttoken, boolean isAdmin, String name) {
        this.jwttoken = jwttoken;
        this.isAdmin = isAdmin;
        this.name = name;
    }

    public String getToken() {
        return this.jwttoken;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public String getName() {
        return name;
    }
}
