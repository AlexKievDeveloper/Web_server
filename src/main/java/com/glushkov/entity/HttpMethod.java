package com.glushkov.entity;

public enum HttpMethod {
    GET("get"),
    POST("post"),
    PUT("put"),
    DELETE("delete");

    private final String name;

    HttpMethod(String name) {
        this.name = name;
    }

    public static HttpMethod getByNameOrNull(String name) {
        HttpMethod[] httpMethods = values();
        for (HttpMethod httpMethod : httpMethods) {
            if (httpMethod.name.equalsIgnoreCase(name)) {
                return httpMethod;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }
}
