package com.example.soxilio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnAlerta,btnConfigurar,btnComoUsar,btnInformacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAlerta = findViewById(R.id.btn_alerta);
        btnConfigurar = findViewById(R.id.btn_configurar);
        btnComoUsar = findViewById(R.id.btn_como_usar);
        btnInformacion = findViewById(R.id.btn_informacion);

        btnAlerta.setOnClickListener(this);
        btnConfigurar.setOnClickListener(this);
        btnComoUsar.setOnClickListener(this);
        btnInformacion.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_alerta:
                Intent intent = new Intent(getApplicationContext(),AlertaActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_configurar:
                Intent intent2 = new Intent(getApplicationContext(),ConfigurarActivity.class);
                startActivity(intent2);
                break;

            case R.id.btn_como_usar:
                Intent intent3 = new Intent(getApplicationContext(),ComoUsarActivity.class);
                startActivity(intent3);
                break;

            case R.id.btn_informacion:
                Intent intent4 = new Intent(getApplicationContext(),InformacionActivity.class);
                startActivity(intent4);
                break;

        }
    }
}
