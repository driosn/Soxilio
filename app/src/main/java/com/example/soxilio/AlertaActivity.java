package com.example.soxilio;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

public class AlertaActivity extends AppCompatActivity implements View.OnClickListener, LocationListener {

    CardView cardOne, cardTwo, cardThree, cardFour;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private String mensaje = "";

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
                startRepeating();
                enviarWhatsApp(v,"Incendio ayuda "+glink,"+59167305722");
                mandarSms("Hay un Incendio ayuda "+"\n"+mensaje+"\n"+ glink,"+59167305722");
                break;

            //Inundaciones
            case R.id.card_two:
                startRepeating();
                enviarWhatsApp(v,"Inundacion ayuda "+glink,"+59167305722");
                mandarSms("Hay un Inundacion ayuda "+"\n"+mensaje+"\n"+ glink,"+59167305722");
                startRepeating();
                break;

            //Terremotos
            case R.id.card_three:
                startRepeating();
                enviarWhatsApp(v,"Terremoto ayuda "+glink,"+59167305722");
                mandarSms("Hay un Terremoto ayuda "+"\n"+mensaje+"\n"+ glink,"+59167305722");
                break;

            //Detener
            case R.id.card_four:
                stopRepeating();
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
                mandarSms("Auxilio! "+mensaje+"\n"+ glink,"+59167305722");
            }else{
                aux++;
            }
//            Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_SHORT).show();
            mHandler.postDelayed(this,600000);

        }
    };

}
