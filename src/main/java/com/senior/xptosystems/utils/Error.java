package com.senior.xptosystems.utils;

import java.util.Date;

public class Error {

    private Integer status;
    private String message;
    private Date timestamp;

    public Error() {
        timestamp = new Date();
    }

    public Error(Integer status, String message) {
        this.timestamp = new Date();
        this.status = status;
        this.message = message;
    }

    public static Error NO_CONTENT(String message) {
        Error error = new Error(204, message);
        return error;
    }

    public static Error BAD_REQUEST(String message) {
        Error error = new Error(400, message);
        return error;

    }

    public static Error NOT_FOUND(String message) {
        Error error = new Error(404, message);
        return error;
    }

    public static Error INTERNAL_SERVER_ERROR(String message) {
        Error error = new Error(500, message);
        return error;
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

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

}
