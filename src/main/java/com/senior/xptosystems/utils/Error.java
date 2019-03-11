package com.senior.xptosystems.utils;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.collections.map.LinkedMap;

public class Error {

    private Integer status;
    private String message;

    public Error() {

    }

    public Error(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static Map NO_CONTENT(String message) {
        Map map = new HashMap();
        Error error = new Error(204, message);
        map.put("error", error);
        return map;
    }

    public static Map BAD_REQUEST(String message) {
        Map map = new HashMap();
        Error error = new Error(400, message);
        map.put("error", error);
        return map;

    }

    public static Map NOT_FOUND(String message) {
        Map map = new HashMap();
        Error error = new Error(404, message);
        map.put("error", error);
        return map;
    }

    public static Map INTERNAL_SERVER_ERROR(String message) {
        Map map = new HashMap();
        Error error = new Error(500, message);
        map.put("error", error);
        return map;
    }

}
