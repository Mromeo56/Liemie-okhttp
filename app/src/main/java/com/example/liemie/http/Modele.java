package com.example.liemie.http;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.liemie.MainActivity;
import com.example.liemie.R;
import com.example.liemie.Visite;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Modele {
    //http
    private Http http;
    private Async async;
    private Response response;

    // token
    private String token;

    // cookie
    private String cookie;


    public Modele() {
        http = new Http();
    }

    public String getCookie() {
        return this.cookie;
    }

    public boolean Connexion(String username, String password) {
        async = new Async();

        http.addUrl("http://192.168.1.32:8000/auth/")
                .addBody("{\"mail\": \"" + username + "\", \"password\": \"" + password + "\"}", Http.Methode.POST);

        try {
            response = async.execute(http.build()).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String json = response.getBody();
        try {
            JSONObject jsonObject = new JSONObject(json);
            if (jsonObject.getString("Auth").equals("Ok")) {
                this.token = response.getHeader("Authorization");
                this.cookie = response.getHeader("Set-Cookie");
                return true;
            }
        }
        catch (JSONException e) {
        }
        catch (Exception e) {
        }

        return false;
    }

    public String getListeVisite() {
        async = new Async();

        http.addUrl("http://192.168.1.32:8000/visite/")
                .addHeader("Authorization", token);

        try {
            response = async.execute(http.build()).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return response.getBody();
    }
}
