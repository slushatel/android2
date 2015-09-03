package com.example.stas.myapplication;


class FunctionResult<T> {
    public T result;
    public String error;

    public FunctionResult(T result1, String error1) {
        this.result = result1;
        if (error1 == "") {
            error1 = null;
        }
        this.error = error1;
    }

    public FunctionResult(T result) {
        this(result, "");
    }
}
