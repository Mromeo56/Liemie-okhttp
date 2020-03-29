package com.example.liemie.http;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.liemie.MainActivity;
import com.example.liemie.R;
import com.example.liemie.Visite;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    public String getToken() { return this.token; }

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

    public ArrayList<Visite> getListeVisite() {
        ArrayList<Visite> vRetour = new ArrayList<Visite>();

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

        try {
            JSONArray lesVisites = new JSONArray(response.getBody());
            for (int i = 0; i < lesVisites.length(); i++) {
                JSONObject visite = lesVisites.getJSONObject(i);
                Date date = null;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    date = sdf.parse(visite.getString("date"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                int id = visite.getInt("id");
                int id_patient = visite.getInt("id_patient");
                int duree = visite.getInt("duree");
                vRetour.add(new Visite(id, id_patient, date, duree));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return vRetour;
    }
}
