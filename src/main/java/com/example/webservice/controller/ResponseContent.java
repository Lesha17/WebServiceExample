package com.example.webservice.controller;

import java.util.Collections;
import java.util.Map;

/**
 * Content of response of {@link UserController}'s methods and may be some actions on this server.
 * Used as {@link javax.ws.rs.core.Response.ResponseBuilder#entity(Object)}.
 */
public class ResponseContent {
    public static int SUCCESS_CODE = 0;
    public static int SERVER_ERROR_CODE = 2;

    private int result;
    private Map<String, Object> extras;

    /**
     * Constructs new instance with {@link #SUCCESS_CODE} and empty extras.
     */
    public ResponseContent() {
        this(0);
    }

    /**
     * Constructs new instance with given result code and empty extras.
     *
     * @param result given result code
     */
    public ResponseContent(int result) {
        this(result, Collections.emptyMap());
    }


    /**
     * Constructs new instance with given result code and extras.
     *
     * @param result given result code
     * @param extras given extras
     */
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
