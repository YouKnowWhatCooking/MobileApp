package com.payload;

import java.io.Serializable;

public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private final String jwttoken;
    private boolean isAdmin;

    public JwtResponse(String jwttoken, boolean isAdmin) {
        this.jwttoken = jwttoken;
        this.isAdmin = isAdmin;
    }

    public String getToken() {
        return this.jwttoken;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}
