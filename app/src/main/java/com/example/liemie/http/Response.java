package com.example.liemie.http;

import android.util.Log;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.ResponseBody;

public class Response {

    private Headers headers;
    private String body;

    public Response(okhttp3.Response response) throws IOException {
        setHeaders(response.headers());
        setBody(response.body());
        Log.i("OKHTTP", response.body().string());
    }

    public void setHeaders(Headers headers) {
        this.headers = headers;
    }

    public void setBody(ResponseBody body) {
        this.body = body.toString();
    }

    public String getBody() {
        return this.body;
    }

    public String getHeader(String key) {
        return this.headers.get(key);
    }
}
