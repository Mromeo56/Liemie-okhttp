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
    private Request.Builder request;


    public Http() {
        this.client = new OkHttpClient();
        this.JSON = MediaType.parse("application/json; charset=utf-8");
        this.methode = Methode.GET;
        this.request = new Request.Builder();
    }

    public Http setMethode(Methode methode) {
        this.methode = methode;
        return this;
    }

    public Http addUrl(String url) {
        this.request.url(url);
        return this;
    }

    public Http addBody(String data) {
        switch (this.methode) {
            case POST:
                this.request.post(RequestBody.create(JSON, data));
                break;
            case PUT:
                this.request.put(RequestBody.create(JSON, data));
                break;
            case DELETE:
                this.request.delete(RequestBody.create(JSON, data));
                break;
            default:
                break;
        }
        return this;
    }

    public Http addCookie(String data) {
        this.addHeader("Set-Cookie", data);
        return this;
    }

    public Http addHeader(String name, String value) {
        this.request.addHeader(name, value);
        return this;
    }

    public Request build() {
        Request request = this.request.build();
        Clean();
        return request;
    }

    private void Clean() {
        this.request = new Request.Builder();
    }
}
