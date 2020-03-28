package com.example.liemie.http;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Http {

    public enum Methode {
        GET, POST, PUT, DELETE
    }

    private OkHttpClient client;
    private MediaType JSON;
    private String url;
    private RequestBody body;
    private Methode methode;
    private String cookie;
    private Response response;


    public Http() {
        this.client = new OkHttpClient();
        this.JSON = MediaType.parse("application/json; charset=utf-8");
        this.methode = Methode.GET;

    }

    public Http setMethode(Methode methode) {
        this.methode = methode;
        return this;
    }

    public Http addUrl(String url) {
        this.url = url;
        return this;
    }

    public Http addBody(String data) {
        this.body = RequestBody.create(JSON, data);
        return this;
    }

    public Http addCookie(String data) {
        return this;
    }

    public Http addHeader(String header) {
        return this;
    }

    public Request build() {
        Request request = null;

        switch (this.methode) {
            case POST:
                request = new Request.Builder()
                        .url(this.url)
                        .post(this.body)
                        .build();
                break;
            case PUT:

                break;
            case DELETE:

                break;
            default:
                request = new Request.Builder()
                        .url(this.url)
                        .build();
                break;
        }

        return request;
    }

}
