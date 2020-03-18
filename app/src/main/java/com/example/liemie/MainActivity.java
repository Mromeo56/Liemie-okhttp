package com.example.liemie;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import android.Manifest;
import android.content.pm.PackageManager;
import java.util.List;
import java.util.ArrayList;
import android.app.AlertDialog;
import android.content.DialogInterface;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import okhttp3.OkHttpClient;


public class MainActivity extends AppCompatActivity implements LoginFragment.OnFragmentInteractionListener{

    private TextView mTextViewResult;
    private OkHttpClient client;
    private Fragment login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        login = (Fragment) getSupportFragmentManager().findFragmentById(R.id.login_frgm);
        login.getView().setVisibility(View.GONE);

        Button bOk=(Button) login.getView().findViewById(R.id.button_login);
        bOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText id;
                EditText mdp;
                id = findViewById(R.id.editText_id);
                mdp = findViewById(R.id.editText_password);
                Toast.makeText(getApplicationContext(),    "clic sur ok", Toast.LENGTH_SHORT
                ).show();
            }
        });
        Button bCancel=(Button) login.getView().findViewById(R.id.button_cancel);
        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login.getView().setVisibility(View.GONE);
            }
        });

        OkHttpClient client = new OkHttpClient();

        /*String url = "http://waraliens.ddns.net/api/";
        //http://www.btssio-carcouet.fr/ppe4/public/connect2/

        MyThread myThread = new MyThread();

        try {
            mTextViewResult.setText(myThread.execute(url).get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void OnFragmentInteraction(Uri uri){

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_connexion:
                login.getView().setVisibility(View.VISIBLE);
                return true;
            case R.id.action_export:
                Toast.makeText(getApplicationContext(),	"clic sur act2",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_import:
                Toast.makeText(getApplicationContext(),	"clic sur act3",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_liste_rdv:
                Toast.makeText(getApplicationContext(),	"clic sur act4",Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
