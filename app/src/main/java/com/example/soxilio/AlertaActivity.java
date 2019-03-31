package com.example.soxilio;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class AlertaActivity extends AppCompatActivity implements View.OnClickListener, LocationListener {

    CardView cardOne, cardTwo, cardThree, cardFour;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private String mensaje = "";
    private String keyIterator_string = "";
    private int keyIterator_int = 0;
    List<String> numeros;
    TextView txtTip;

    //gps
    protected LocationManager locationManager;

    //Tiempo
    private Handler mHandler = new Handler();

    public String glink = "";
    public int aux = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerta);

        cardOne = findViewById(R.id.card_one);
        cardTwo = findViewById(R.id.card_two);
        cardThree = findViewById(R.id.card_three);
        cardFour = findViewById(R.id.card_four);

        cardOne.setOnClickListener(this);
        cardTwo.setOnClickListener(this);
        cardThree.setOnClickListener(this);
        cardFour.setOnClickListener(this);

        numeros = new ArrayList<>();

        txtTip = findViewById(R.id.txt_tip);

        createArrayNumbers();

        // Pidiendo permiso para mandar sms
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {

            if (checkSelfPermission(Manifest.permission.SEND_SMS)
                    == PackageManager.PERMISSION_DENIED) {

                Log.d("permission", "permission denied to SEND_SMS - requesting it");
                String[] permissions = {Manifest.permission.SEND_SMS};

                requestPermissions(permissions, PERMISSION_REQUEST_CODE);

            }
        }

        // GPS
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
        } else {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,  this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            //Incendios
            case R.id.card_one:
                aux = 0;
                startRepeating();
                enviarWhatsApp(v,getString(R.string.incendio_ayuda)+glink,"+591" + numeros.get(0));
                mandarSmsContactos(getString(R.string.incendio_ayuda));
                txtTip.setText(R.string.envio_activado);
                break;

            //Inundaciones
            case R.id.card_two:
                aux = 0;
                startRepeating();
                enviarWhatsApp(v,getString(R.string.inundacion_ayuda)+glink,"+591" + numeros.get(0));
                mandarSmsContactos(getString(R.string.inundacion_ayuda));
                txtTip.setText(R.string.envio_activado);
                break;

            //Terremotos
            case R.id.card_three:
                aux = 0;
                startRepeating();
                enviarWhatsApp(v,getString(R.string.terremoto_ayuda)+glink,"+591" + numeros.get(0));
                mandarSmsContactos(getString(R.string.terremoto_ayuda));
                txtTip.setText(R.string.envio_activado);
                break;

            //Detener
            case R.id.card_four:
                stopRepeating();
                aux = 0;
                txtTip.setText(R.string.envio_desactivado);
                break;

        }
    }

    private void mandarSms(String mensaje, String destinatario){
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(destinatario,null,"" +
                    mensaje,null,null);
            Toast.makeText(getApplicationContext(),"Mensaje Enviado",Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(),"Mensaje no enviado"
                    + e.getMessage().toString(),Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    public void enviarWhatsApp(View view,String mensaje, String destinatario){

        try {
            String texto = mensaje;
            String numero = destinatario;
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+numero +"&text="+texto));
            startActivity(intent);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    // GPS

    @Override
    public void onLocationChanged(Location location) {
        mensaje = "Mi ubicacion es: "+"\nLatitud: " +location.getLatitude()+"\n"
                +"Longitud: "+location.getLongitude();

        glink = "https://www.google.com.bo/maps/@"+location.getLatitude()+","+location.getLongitude()+",14z?hl=es";
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    // Repetidor

    public void startRepeating(){
        mToasRunnable.run();
    }

    public void stopRepeating(){
        mHandler.removeCallbacks(mToasRunnable);
    }

    private Runnable mToasRunnable = new Runnable() {
        @Override
        public void run() {
            if(aux >=1){
//                mandarSms("Auxilio! "+mensaje+"\n"+ glink,"+59167305722");
                mandarSmsContactos("Auxilio");
            }else{
                aux++;
            }
//            Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_SHORT).show();
            mHandler.postDelayed(this,600000);

        }
    };

    private void mandarSmsContactos(String texto){
        for(int i=0; i<numeros.size(); i++){
            mandarSms(texto+"\n"+mensaje+"\n"+ glink,"+591" + numeros.get(i));
        }
    }

    public void createArrayNumbers(){
        SharedPreferences keyPreferences = getSharedPreferences("keyValue", Context.MODE_PRIVATE);
        SharedPreferences numbers = getSharedPreferences("numbers", Context.MODE_PRIVATE);

        keyIterator_string = keyPreferences.getString("keyVal", "");
        if(keyIterator_string.length() != 0){
            keyIterator_int = Integer.parseInt(keyIterator_string);
            for(int i=1; i<=keyIterator_int; i++){
                //Toast.makeText(this,  numbers.getString(Integer.toString(i), ""), Toast.LENGTH_SHORT).show();
                numeros.add(numbers.getString(Integer.toString(i), ""));
                Log.d("numbers: ", numbers.getString(Integer.toString(i), ""));
            }
        }
    }
}
