package com.example.formation.dooble_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;

public class EnJeuActivity extends AppCompatActivity {

    private EnJeuView partie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int ecranLargeur = metrics.widthPixels, ecranHauteur = metrics.heightPixels;
        partie = new EnJeuView( this, ecranLargeur, ecranHauteur);

        setContentView(partie);
    }

    public void revenirMenu()
    {
        Intent intent = new Intent(EnJeuActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
