package com.senior.xptosystems.utils;

import java.io.Serializable;

// @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Result implements Serializable {

    private Integer status_code;
    private String status;
    private Object result;

    public Result() {
        this.status_code = 1;
        this.status = "success";
        this.result = null;
    }

    public Result(Integer status_code, String status, Object result) {
        this.status_code = status_code;
        this.status = status;
        this.result = result;
    }

    public Integer getStatus_code() {
        return status_code;
    }

    public void setStatus_code(Integer status_code) {
        this.status_code = status_code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

}
