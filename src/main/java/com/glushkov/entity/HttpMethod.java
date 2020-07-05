package com.glushkov.entity;

import com.glushkov.exception.ServerException;

public enum HttpMethod {
    GET("get"),
    POST("post"),
    PUT("put"),
    DELETE("delete");

    private final String name;

    HttpMethod(String name) {
        this.name = name;
    }

    public static HttpMethod getByName(String name) {
        HttpMethod[] httpMethods = values();
        for (HttpMethod httpMethod : httpMethods) {
            if (httpMethod.name.equalsIgnoreCase(name)) {
                return httpMethod;
            }
        }
        throw new ServerException(HttpStatus.METHOD_NOT_ALLOWED);
    }

    public String getName() {
        return name;
    }
}
