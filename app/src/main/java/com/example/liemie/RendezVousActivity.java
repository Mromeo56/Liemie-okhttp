package com.example.liemie;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.ListView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
import java.util.List;

import android.os.Bundle;

public class RendezVousActivity extends AppCompatActivity {

    private ListView listView;
    private List<Visite> listeVisite​;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rendez_vous);
    }
}
