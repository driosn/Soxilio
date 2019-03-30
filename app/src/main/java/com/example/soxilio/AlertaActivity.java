package com.example.soxilio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class AlertaActivity extends AppCompatActivity implements View.OnClickListener {

    CardView cardOne, cardTwo, cardThree, cardFour;

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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.card_one:

                break;

            case R.id.card_two:

                break;

            case R.id.card_three:

                break;

            case R.id.card_four:

                break;

        }
    }
}
