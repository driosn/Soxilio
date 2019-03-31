package com.example.soxilio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class p2pActivity extends AppCompatActivity {

    ListView lista;

    List<String> dispositivos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p2p);

        lista = (ListView) findViewById(R.id.peerListView);

        dispositivos = new ArrayList<String>();
        dispositivos.add("Dispositivo 1");
        dispositivos.add("Dispositivo 2");
        dispositivos.add("Dispositivo 3");
        dispositivos.add("Dispositivo 4");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, dispositivos);
        lista.setAdapter(arrayAdapter);
    }
}
