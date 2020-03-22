package com.example.liemie;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import android.app.AlertDialog;
import android.content.DialogInterface;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;


public class MainActivity extends AppCompatActivity {
    // SharedPref
    SharedPreferences sharedPreferences;
    public static final String PREF_NAME = "identifier";

    // toolBar
    private Toolbar toolbar;
    // // toolBar menu
    private MenuItem menuConnec;
    private MenuItem menuExport;
    private MenuItem menuImport;
    private MenuItem menuList;
    private MenuItem menuInfo;

    // utilCourant
    private Utilisateur utilCourant;

    // OkHttp
    private OkHttpClient client;

    // login frgm
    private Fragment frgm_login;
    // // login Button
    private Button login_connec;
    private Button login_cancel;
    // // login EditText
    private EditText login_email;
    private EditText login_password;

    // profil frgm
    private Fragment frgm_profil;
    // // profil TextView
    private TextView profil_id;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // toolBar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // permissions
        checkPermission();

        // SharedPred Init
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        // login frgm
        frgm_login = (Fragment) getSupportFragmentManager().findFragmentById(R.id.login_frgm);
        frgm_login.getView().setVisibility(View.GONE);
        // // login EditText
        login_email = (EditText) frgm_login.getView().findViewById(R.id.login_util);
        login_password = (EditText) frgm_login.getView().findViewById(R.id.login_password);
        // // login button
        login_connec = (Button) frgm_login.getView().findViewById(R.id.login_connec);
        login_connec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (login_email.length() > 2 && login_password.length() > 2) {
                    connexion(login_email.getText().toString(), login_password.getText().toString());
                } else {
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


        // profil frgm
        frgm_profil = (Fragment) getSupportFragmentManager().findFragmentById(R.id.profil_frgm);
        frgm_profil.getView().setVisibility(View.GONE);

        // auto Connexion
        if (sharedPreferences.getString("username", "") != "" && sharedPreferences.getString("password", "") != "") {
            connexion(sharedPreferences.getString("username", ""), sharedPreferences.getString("password", ""));
        }
    }

    private boolean connexion(String userName, String password) {
        String url = "http://waraliens.ddns.net/api/user/";

        MyThread myThread = new MyThread();

        try {
            JSONArray lesUtil = new JSONArray(myThread.execute(url).get());
            for (int i = 0; i < lesUtil.length(); i++) {
                JSONObject util = lesUtil.getJSONObject(i);
                if (util.getString("mail").equals(userName) && util.getString("password").equals(password)) {
                    utilCourant = new Utilisateur(util.getInt("id"), util.getString("mail"), util.getString("password"));
                    InitConnexion();
                    break;
                }
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return true;
    }

    public void InitConnexion() {
        // instantiate sharedPref
        if(sharedPreferences.getString("username", "").isEmpty() && sharedPreferences.getString("password", "").isEmpty()) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("username", utilCourant.getMail());
            editor.putString("password", utilCourant.getPassWord());
        }

        // toolBar menu
        LogMenu();

        // // profil TextView
        profil_id = (TextView) frgm_profil.getView().findViewById(R.id.profil_id);
        profil_id.setText(utilCourant.getMail());
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void checkPermission() {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS) == true && shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) == true && shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE) == true) {
            } else {
                askForPermission();
            }
        } else {
            return;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void askForPermission() {
        requestPermissions(new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
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

    public void HiddenAllFrgm() {
        frgm_profil.getView().setVisibility(View.GONE);
        frgm_login.getView().setVisibility(View.GONE);
    }

    public void NoLogMenu() {
        menuConnec.setVisible(true);
        menuExport.setVisible(false);
        menuImport.setVisible(false);
        menuList.setVisible(false);
        menuInfo.setVisible(false);
    }

    public void LogMenu() {
        menuConnec = toolbar.getMenu().findItem(R.id.action_connexion);
        menuExport = toolbar.getMenu().findItem(R.id.action_export);
        menuImport = toolbar.getMenu().findItem(R.id.action_import);
        menuList = toolbar.getMenu().findItem(R.id.action_liste_rdv);
        menuInfo = toolbar.getMenu().findItem(R.id.action_profil);
        menuConnec.setVisible(false);
        menuExport.setVisible(true);
        menuImport.setVisible(true);
        menuList.setVisible(true);
        menuInfo.setVisible(true);
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
            case R.id.action_profil:
                HiddenAllFrgm();
                frgm_profil.getView().setVisibility(View.VISIBLE);
                return true;
            case R.id.action_connexion:
                HiddenAllFrgm();
                frgm_login.getView().setVisibility(View.VISIBLE);
                return true;
            case R.id.action_export:
                Toast.makeText(getApplicationContext(), "clic sur act2", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_import:
                Toast.makeText(getApplicationContext(), "clic sur act3", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_liste_rdv:
                HiddenAllFrgm();
                startActivity(new Intent(this, RendezVousActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
