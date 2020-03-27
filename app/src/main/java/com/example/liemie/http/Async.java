package com.example.liemie.http;

import android.os.AsyncTask;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class Async extends AsyncTask<Request, Void, Response> {

    @Override
    protected Response doInBackground(Request... requests) {
        OkHttpClient client = new OkHttpClient();
        Response response = null;
        try {
            response = new Response(client.newCall(requests[0]).execute());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }
}
