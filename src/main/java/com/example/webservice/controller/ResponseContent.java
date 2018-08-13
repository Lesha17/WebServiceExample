package com.example.webservice.controller;

import java.util.Collections;
import java.util.Map;

public class ResponseContent
{
    public static int SUCCESS_CODE = 0;
    public static int SERVER_ERROR_CODE = 2;
    private int result;
    private Map<String, Object> extras;

    public ResponseContent() {
    }

    public ResponseContent(int result) {
        this(result, Collections.emptyMap());
    }


    public ResponseContent(int result, Map<String, Object> extras) {
        this.result = result;
        this.extras = extras;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public Map<String, Object> getExtras() {
        return extras;
    }

    public void setExtras(Map<String, Object> extras) {
        this.extras = extras;
    }
}
