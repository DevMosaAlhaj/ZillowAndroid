package com.mosaalhaj.zillow.model;

public class MyRes<T> {

    // This class is a static server response

    private final boolean succeeded;
    private final String message;
    private final T data;

    public MyRes(boolean succeeded, String message, T data) {
        this.succeeded = succeeded;
        this.message = message;
        this.data = data;
    }

    public boolean isSucceeded() {
        return succeeded;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
