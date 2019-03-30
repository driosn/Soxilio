package com.example.soxilio;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ConfigurarActivity extends AppCompatActivity implements View.OnClickListener {

    private Button aceptar;
    private EditText numero;
    private String celular;

    private List<String> numeros;
    private ListView lsNumeros;
    private ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurar);

        numero = findViewById(R.id.edit_numero);
        aceptar = findViewById(R.id.btn_aceptar);
        numeros = new ArrayList<>();

        lsNumeros = findViewById(R.id.lista_numeros);
        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, numeros);
        lsNumeros.setAdapter(arrayAdapter);

        aceptar.setOnClickListener(this);




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_aceptar:
                celular = "+591" + numero.getText();
                numeros.add(celular);
                numero.setText("");
                break;
        }

    }
}
