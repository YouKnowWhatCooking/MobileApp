package com.payload;

import java.io.Serializable;

public class Response implements Serializable {

    private String response;

    public Response(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }
}
