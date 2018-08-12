package com.example.webservice.controller;

import java.util.Collections;
import java.util.Map;

public class Response
{
    private int result;
    private Map<String, Object> extras;

    public Response(int result, Map<String, Object> extras) {
        this.result = result;
        this.extras = extras;
    }

    public Response(int result) {
        this(result, Collections.emptyMap());
    }

    public int getResult() {
        return result;
    }

    public Map<String, Object> getExtras() {
        return extras;
    }
}
