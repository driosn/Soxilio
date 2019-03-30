package com.example.soxilio;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;



public class ConfigurarActivity extends AppCompatActivity implements View.OnClickListener {

    private Button aceptar;
    private EditText numero;
    private String celular;
    private List<String> numeros;
    private ListView lsNumeros;
    private ArrayAdapter arrayAdapter;
    public int keyNumber = 0;
    public String keyIterator_string = "";
    public int keyIterator_int = 0;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurar);

        numero = findViewById(R.id.edit_numero);
        aceptar = findViewById(R.id.btn_aceptar);
        numeros = new ArrayList<>();

        SharedPreferences keyPreferences = getSharedPreferences("keyValue", Context.MODE_PRIVATE);
        SharedPreferences numbers = getSharedPreferences("numbers", Context.MODE_PRIVATE);

        keyIterator_string = keyPreferences.getString("keyVal", "");
        if(keyIterator_string.length() != 0){
            keyIterator_int = Integer.parseInt(keyIterator_string);
            for(int i=1; i<=keyIterator_int; i++){
                //Toast.makeText(this,  numbers.getString(Integer.toString(i), ""), Toast.LENGTH_SHORT).show();
                numeros.add(numbers.getString(Integer.toString(i), ""));
            }
        }

        lsNumeros = findViewById(R.id.lista_numeros);
        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, numeros);
        lsNumeros.setAdapter(arrayAdapter);

        aceptar.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_aceptar:
                SharedPreferences preferencias = getSharedPreferences("numbers", Context.MODE_PRIVATE);
                SharedPreferences.Editor obj_editor = preferencias.edit();
                SharedPreferences keyPreferences = getSharedPreferences("keyValue", Context.MODE_PRIVATE);
                SharedPreferences.Editor obj_editor_key = keyPreferences.edit();

                keyNumber = Integer.parseInt(keyPreferences.getString("keyVal", "0"));
                keyNumber++;

                obj_editor.putString(Integer.toString(keyNumber), numero.getText().toString());
                obj_editor.commit();
                obj_editor_key.putString("keyVal", Integer.toString(keyNumber));
                obj_editor_key.commit();

                celular = "+591" + numero.getText();
                numeros.add(celular);
                numero.setText("");
                break;
        }

    }
}
