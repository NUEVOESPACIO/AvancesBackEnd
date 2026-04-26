package com.mycompany.mavenproject4.dto;

public class GeneralResponseOk<T> {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
    private T data;

    public GeneralResponseOk(String message, T data) {
        this.message = message;
        this.data = data;
    }
}
