package com.example.soxilio;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class ConfigurarActivity extends AppCompatActivity implements View.OnClickListener {

    private Button aceptar;
    private EditText numero;
    private String celular;
    private List<String> numeros;
    private ListView lsNumeros;
    public int keyNumber = 0;
    public String keyIterator_string = "";
    public int keyIterator_int = 0;
    int position_item;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurar);

        numero = findViewById(R.id.edit_numero);
        aceptar = findViewById(R.id.btn_aceptar);
        numeros = new ArrayList<>();

        actualizarLista();

        lsNumeros = findViewById(R.id.lista_numeros);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, numeros);
        lsNumeros.setAdapter(arrayAdapter);
        lsNumeros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Toast.makeText(getApplicationContext(), "You have clicked on: " + numeros.get(position), Toast.LENGTH_SHORT).show();
                position_item = position;


                PopupMenu menu = new PopupMenu (getApplicationContext(), view);
                menu.setOnMenuItemClickListener (new PopupMenu.OnMenuItemClickListener ()
                {
                    @Override
                    public boolean onMenuItemClick (MenuItem item)
                    {
                        int id = item.getItemId();
                        switch (id)
                        {
                            //Arreglar el delete del telefono
                            case R.id.del_phone:
                                Log.i ("Options", "delete phone");
                                Toast.makeText(getApplicationContext(), "The position of the item is " + Integer.toString(position_item), Toast.LENGTH_SHORT).show();
                                SharedPreferences numbers = getSharedPreferences("numbers", Context.MODE_PRIVATE);
                                SharedPreferences.Editor numbers_editor = numbers.edit();
                                numbers_editor.remove(Integer.toString(position_item+1));
                                numbers_editor.commit();
                                SharedPreferences keyPreferencies = getSharedPreferences("keyValue", Context.MODE_PRIVATE);
                                int key_iterator = Integer.parseInt(keyPreferencies.getString("keyVal", "0"));
                                for(int i=position_item+1; i<=key_iterator-1; i++){
                                    numbers_editor.putString(Integer.toString(i), numeros.get(i));
                                    numbers_editor.commit();
                                }
                                numbers_editor.remove(Integer.toString(numeros.size()));
                                numbers_editor.commit();
                                numeros.remove(numeros.size()-1);
                                SharedPreferences.Editor keyPreferencies_editor = keyPreferencies.edit();
                                keyPreferencies_editor.putString("keyVal", Integer.toString(key_iterator-1));
                                break;
                            case R.id.edit_phone:
                                Log.i ("Options", "edit phone");
                                break;
                        }
                        return true;
                    }
                });
                menu.inflate (R.menu.menu_layout);
                menu.show();
            }
        });


        aceptar.setOnClickListener(this);


    }

    /*public void showMenu (View view, int position)
    {
        PopupMenu menu = new PopupMenu (this, view);
        menu.setOnMenuItemClickListener (new PopupMenu.OnMenuItemClickListener ()
        {
            @Override
            public boolean onMenuItemClick (MenuItem item)
            {
                int id = item.getItemId();
                switch (id)
                {
                    case R.id.del_phone:
                        Log.i ("Options", "delete phone");
                        Toast.makeText(this, "The position of the item is " + g, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.edit_phone:
                        Log.i ("Options", "edit phone");
                        break;
                }
                return true;
            }
        });
        menu.inflate (R.menu.menu_layout);
        menu.show();
    }*/

    public void actualizarLista(){
        SharedPreferences keyPreferences = getSharedPreferences("keyValue", Context.MODE_PRIVATE);
        SharedPreferences numbers = getSharedPreferences("numbers", Context.MODE_PRIVATE);

        keyIterator_string = keyPreferences.getString("keyVal", "");
        if(keyIterator_string.length() != 0){
            keyIterator_int = Integer.parseInt(keyIterator_string);
            for(int i=1; i<=keyIterator_int; i++){
                //Toast.makeText(this,  numbers.getString(Integer.toString(i), ""), Toast.LENGTH_SHORT).show();
                if(numbers.getString(Integer.toString   (i), "").length() > 1){
                    numeros.add(numbers.getString(Integer.toString(i), ""));
                }
            }
        }
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

    public void reiniciarContactos(View view) {
        SharedPreferences keyPreferences = getSharedPreferences("keyValue", Context.MODE_PRIVATE);
        SharedPreferences numbers = getSharedPreferences("numbers", Context.MODE_PRIVATE);
        SharedPreferences.Editor keyPreferences_editor = keyPreferences.edit();
        SharedPreferences.Editor numbers_editor = numbers.edit();
        keyPreferences_editor.clear();
        keyPreferences_editor.commit();
        numbers_editor.clear();
        numbers_editor.commit();
        actualizarLista();
    }
}
