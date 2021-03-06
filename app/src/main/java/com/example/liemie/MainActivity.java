package com.example.liemie;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.liemie.http.Modele;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.io.File;


public class MainActivity extends AppCompatActivity {
    // mainActivity
    private ProgressBar main_progressBar;

    // modele
    Modele modele;

    // SharedPref
    public static final String PREF_NAME = "identifier";
    private SharedPreferences sharedPreferences;

    // fab
    private FloatingActionButton fab;

    // toolBar
    private Toolbar toolbar;
    // // toolBar menu
    private MenuItem menuConnec;
    private MenuItem menuExport;
    private MenuItem menuImport;
    private MenuItem menuList;
    private MenuItem menuInfo;

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

    // settings frgm
    private Fragment frgm_settings;
    // // settings Button
    private Button settings_removeSharedPref;
    private Button settings_removeCookie;
    // // settings switch
    private Switch settings_darkSwitch;

    // visite frgm
    private Fragment frgm_visite;
    // // visite listView
    private ListView visite_listview;
    // // visite refresh
    private SwipeRefreshLayout visite_refresh;

    // patient frgm
    private Fragment frgm_patient;
    // // patient TextView
    private TextView patient_name;
    private TextView patient_age;
    // // patient Button
    private Button patient_back;
    private Button patient_call;
    private Button patient_goTo;
    // // patient MapView
    private MapView patient_mapAddress;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // toolBar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // modele
        modele = new Modele();

        // fab
        fab = findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(getString(R.string.login_disconnect)).setMessage(getString(R.string.login_disconnectMessage));
                builder.setPositiveButton(R.string.general_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Disconnect();
                    }
                });
                builder.setNegativeButton(R.string.general_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        // permissions
        checkPermission();

        // SharedPref Init
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
                    if (connexion(login_email.getText().toString(), login_password.getText().toString())) {
                        InitConnexion();
                    }
                    else {
                        AlertMsg(getString(R.string.general_error), getString(R.string.login_connexionError));
                    }
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

        // settings frgm
        frgm_settings = (Fragment) getSupportFragmentManager().findFragmentById(R.id.settings_frgm);
        frgm_settings.getView().setVisibility(View.GONE);
        // settings Button
        settings_removeSharedPref = (Button) frgm_settings.getView().findViewById(R.id.settings_removeSharedPref);
        settings_removeSharedPref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(getString(R.string.general_warning)).setMessage(getString(R.string.settings_removeWarning));
                builder.setPositiveButton(R.string.general_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove("username");
                        editor.remove("password");
                        editor.commit();
                    }
                });
                builder.setNegativeButton(R.string.general_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        settings_removeCookie = (Button) frgm_settings.getView().findViewById(R.id.settings_removeCookie);
        settings_removeCookie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(getString(R.string.general_warning)).setMessage(getString(R.string.settings_removeWarning));
                builder.setPositiveButton(R.string.general_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove("Set-Cookie");
                        editor.commit();
                    }
                });
                builder.setNegativeButton(R.string.general_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        settings_darkSwitch = (Switch) frgm_settings.getView().findViewById(R.id.settings_darkSwitch);
        if(Integer.valueOf(android.os.Build.VERSION.SDK) < 29) {
            settings_darkSwitch.setEnabled(false);
            settings_darkSwitch.setClickable(false);
        }
        else {
            settings_darkSwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (settings_darkSwitch.isChecked()) {

                    }
                    else {

                    }
                }
            });
        }

        // patient frgm
        frgm_patient = (Fragment) getSupportFragmentManager().findFragmentById(R.id.patient_frgm);
        frgm_patient.getView().setVisibility(View.GONE);
        // patient TextView
        patient_name = frgm_patient.getView().findViewById(R.id.visite_patientName);
        patient_age = frgm_patient.getView().findViewById(R.id.visite_patientAge);
        // // patient Button
        patient_back = frgm_patient.getView().findViewById(R.id.visite_back);
        patient_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HiddenAllFrgm();
                frgm_visite.getView().setVisibility(View.VISIBLE);
            }
        });
        patient_call = frgm_patient.getView().findViewById(R.id.visite_call);
        patient_goTo = frgm_patient.getView().findViewById(R.id.visite_goTo);
        // // patient MapView
        org.osmdroid.config.IConfigurationProvider osmConf = org.osmdroid.config.Configuration.getInstance();
        File basePath = new File(getCacheDir().getAbsolutePath(), "osmdroid");
        osmConf.setOsmdroidBasePath(basePath);
        File tileCache = new File(osmConf.getOsmdroidBasePath().getAbsolutePath(), "tile");
        osmConf.setOsmdroidTileCache(tileCache);
        patient_mapAddress = (MapView) frgm_patient.getView().findViewById(R.id.visite_patientAddress);
        patient_mapAddress.setTileSource(TileSourceFactory.MAPNIK);
        patient_mapAddress.setBuiltInZoomControls(false);
        patient_mapAddress.setMultiTouchControls(true);

        // visite frgm
        frgm_visite = (Fragment) getSupportFragmentManager().findFragmentById(R.id.visite_frgm);
        frgm_visite.getView().setVisibility(View.GONE);
        // // visite listview
        visite_listview = (ListView) frgm_visite.getView().findViewById(R.id.visite_listView);
        visite_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Visite visite = (Visite) parent.getItemAtPosition(position);
                final Patient patient = (Patient) modele.GetPatientById(visite.getIdPatient());
                HiddenAllFrgm();
                frgm_patient.getView().setVisibility(View.VISIBLE);
                patient_name.setText(patient.getPrenom() + " " + patient.getNom());
                patient_age.setText(patient.getAge() + " Ans");

                MapController mapController = (MapController) patient_mapAddress.getController();
                mapController.setZoom(15);
                Marker marker = new Marker(patient_mapAddress);
                marker.setPosition(modele.GetGeoPointByAdress(patient.getAddress()));
                mapController.setCenter(marker.getPosition());
                patient_mapAddress.getOverlays().add(marker);
                patient_goTo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse("https://www.google.fr/maps/place/" + patient.getAddress().replace(' ', '+'));
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                });
                patient_call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent phoneIntent = new Intent(Intent.ACTION_CALL);
                        phoneIntent.setData(Uri.parse("tel:" + patient.getNumTel()));
                        startActivity(phoneIntent);
                    }
                });
            }
        });
        // // visite refresh
        visite_refresh = (SwipeRefreshLayout) frgm_visite.getView();
        visite_refresh.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        RefreshVisiteList();
                        visite_refresh.setRefreshing(false);
                    }
                }
        );

        // mainActivity
        main_progressBar = findViewById(R.id.main_progressBar);
        main_progressBar.setVisibility(View.GONE);
    }


    public boolean connexion(String username, String password) {
        main_progressBar.setVisibility(View.VISIBLE);
        if(modele.Connexion(username, password)) {
            // instantiate sharedPref
            if (sharedPreferences.getString("username", "") == "" || sharedPreferences.getString("password", "") == "") {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username", username);
                editor.putString("password", password);
                editor.commit();
            }
            if (sharedPreferences.getString("Set-Cookie", "") == "") {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Set-Cookie", modele.getCookie());
                editor.commit();
            }
            return true;
        }

        main_progressBar.setVisibility(View.GONE);
        return false;
    }

    public void InitConnexion() {
        // hide function
        LogMenu();
        HiddenAllFrgm();

        // // profil TextView
        profil_id = (TextView) frgm_profil.getView().findViewById(R.id.profil_id);
        //profil_id.setText(utilCourant.getMail());

        // // visite listView
        RefreshVisiteList();

        main_progressBar.setVisibility(View.GONE);
    }

    public void Disconnect() {

    }

    public void RefreshVisiteList() {
        // // visite listView
        VisiteAdaptater arrayAdapter = new VisiteAdaptater(getApplicationContext(), modele.getListeVisite());
        visite_listview.setAdapter(arrayAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void checkPermission() {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS) == true &&
                    shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) == true &&
                    shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE) == true &&
                    shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE) == true) {
            } else {
                askForPermission();
            }
        } else {
            return;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void askForPermission() {
        requestPermissions(new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CALL_PHONE}, 2);
    }

    public void AlertMsg(String title, String msg) {
        final AlertDialog alertDialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(title).setMessage(msg);
        builder.setPositiveButton(R.string.general_ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        alertDialog = builder.create();
        alertDialog.show();
    }

    public void HiddenAllFrgm() {
        frgm_profil.getView().setVisibility(View.GONE);
        frgm_login.getView().setVisibility(View.GONE);
        frgm_settings.getView().setVisibility(View.GONE);
        frgm_patient.getView().setVisibility(View.GONE);
        frgm_visite.getView().setVisibility(View.GONE);
    }

    public void LogMenu() {
        fab.setVisibility(View.VISIBLE);
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
            case R.id.action_settings:
                HiddenAllFrgm();
                frgm_settings.getView().setVisibility(View.VISIBLE);
                return true;
            case R.id.action_profil:
                HiddenAllFrgm();
                frgm_profil.getView().setVisibility(View.VISIBLE);
                return true;
            case R.id.action_connexion:
                HiddenAllFrgm();
                // semi-auto Connexion
                if (sharedPreferences.getString("username", "") != "" && sharedPreferences.getString("password", "") != "") {
                    if (connexion(sharedPreferences.getString("username", ""), sharedPreferences.getString("username", ""))) {
                        AlertMsg(getString(R.string.general_warning), getString(R.string.login_autoConnexion));
                        InitConnexion();
                    }
                    else {
                        AlertMsg(getString(R.string.general_error), getString(R.string.login_connexionError));
                    }
                }
                else {
                    frgm_login.getView().setVisibility(View.VISIBLE);
                }
                return true;
            case R.id.action_export:
                Toast.makeText(getApplicationContext(), "clic sur act2", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_import:
                Toast.makeText(getApplicationContext(), "clic sur act3", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_liste_rdv:
                HiddenAllFrgm();
                frgm_visite.getView().setVisibility(View.VISIBLE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
