package com.example.liemie;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import android.app.AlertDialog;
import android.content.DialogInterface;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import okhttp3.OkHttpClient;


public class MainActivity extends AppCompatActivity {

    // OkHttp
    private OkHttpClient client;

    // login frgm
    private Fragment frgm_login;
    // // login Button
    private Button login_connec;
    private Button login_cancel;
    // // login EditText
    private EditText login_util;
    private EditText login_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // login frgm
        frgm_login = (Fragment) getSupportFragmentManager().findFragmentById(R.id.login_frgm);
        frgm_login.getView().setVisibility(View.GONE);
        // // login EditText
        login_util = (EditText) frgm_login.getView().findViewById(R.id.login_util);
        login_password = (EditText) frgm_login.getView().findViewById(R.id.login_password);
        // // login button
        login_connec = (Button) frgm_login.getView().findViewById(R.id.login_connec);
        login_connec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (login_util.length() > 2 && login_password.length() > 2) {
                    connexion(login_util.getText().toString(), login_password.getText().toString());
                }
                else {
                    AlertMsg(getString(R.string.general_error), getString(R.string.login_entryError));
                }
            }
        });
        login_cancel = (Button) frgm_login.getView().findViewById(R.id.login_cancel);
        login_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frgm_login.getView().setVisibility(View.GONE);
            }
        });
    }

    private boolean connexion(String userName, String password) {
        OkHttpClient client = new OkHttpClient();

        String url = "http://waraliens.ddns.net/api/";

        MyThread myThread = new MyThread();

        try {
            AlertMsg("test", myThread.execute(url).get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return true;
    }

    public void AlertMsg(String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(title).setMessage(msg);
        builder.setPositiveButton(R.string.general_ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_connexion:
                frgm_login.getView().setVisibility(View.VISIBLE);
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
