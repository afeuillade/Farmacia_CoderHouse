package com.example.FarmaciaData;

import lombok.Data;

@Data
public class ApiResponse <T> {

    private String msg;
    private T data;
    private String error;

    public ApiResponse() {
    }

    public ApiResponse(String msg, T data, String error) {
        this.msg = msg;
        this.data = data;
        this.error = error;
    }

}
