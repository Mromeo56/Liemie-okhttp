package com.example.liemie;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RendezVousActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rendez_vous);
        listView = findViewById(R.id.rdv_listView);
        VisiteAdaptater arrayAdapter = new VisiteAdaptater(getApplicationContext(), getListeVisite());
        listView.setAdapter(arrayAdapter);
    }

    public ArrayList<Visite> getListeVisite() {
        ArrayList<Visite> vRetour = new ArrayList<Visite>();

        String url = "http://waraliens.ddns.net/api/visite/";

        MyThread myThread = new MyThread();

        try {
            JSONArray lesVisites = new JSONArray(myThread.execute(url).get());
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

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return vRetour;
    }

    public void AlertMsg(String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(RendezVousActivity.this);
        builder.setTitle(title).setMessage(msg);
        builder.setPositiveButton(R.string.general_ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
