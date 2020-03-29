package com.example.liemie.http;

import android.util.Log;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.ResponseBody;

public class Response {

    private Headers headers;
    private String body;
    private Integer code;

    public Response(okhttp3.Response response) throws IOException {
        setHeaders(response.headers());
        setBody(response.body());
        setCode(response.code());
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setHeaders(Headers headers) {
        this.headers = headers;
    }

    public void setBody(ResponseBody body) throws IOException {
        this.body = body.string();
    }

    public Integer getCode() {
        return this.code;
    }

    public String getBody() {
        return this.body;
    }

    public String getHeader(String key) {
        return this.headers.get(key);
    }
}
