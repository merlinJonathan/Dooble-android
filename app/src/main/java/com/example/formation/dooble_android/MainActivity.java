package com.example.formation.dooble_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;

public class MainActivity extends AppCompatActivity {
    private MenuView menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int ecranLargeur = metrics.widthPixels, ecranHauteur = metrics.heightPixels;
        menu = new MenuView(this, ecranLargeur, ecranHauteur);

        setContentView(menu);
    }

    public void lancerPartie()
    {
        Intent intent = new Intent(MainActivity.this, EnJeuActivity.class);
        startActivity(intent);
    }
}
