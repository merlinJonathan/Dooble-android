package com.example.formation.dooble_android;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * Created by kurzen on 16/02/2018.
 */

public class BoutonJouer {
    private BitmapDrawable mImg = null;
    private int x, y ;
    private int boutonW, boutonH;
    private int ecranLargeur, ecranHauteur;
    private MainActivity mContext;


    public BoutonJouer(MainActivity c, int largeurEcran, int hauteurEcran)
    {

        boutonH = hauteurEcran/7;
        boutonW = largeurEcran/3;
        x = largeurEcran/2 - boutonW/2;
        y = hauteurEcran/2 - boutonH/2;
        ecranHauteur = hauteurEcran;
        ecranLargeur = largeurEcran;

        mContext = c;
        mImg = setImage(mContext, R.mipmap.boutonjouer, boutonW, boutonH);
    }

    public BitmapDrawable setImage(final Context c, final int ressource, final int w, final int h)
    {
        Drawable dr = c.getResources().getDrawable(ressource);
        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
        return new BitmapDrawable(c.getResources(), Bitmap.createScaledBitmap(bitmap, w, h, true));
    }

    public void resize(int wScreen, int hScreen) {
        // wScreen et hScreen sont la largeur et la hauteur de l'écran en pixel
        // on sauve ces informations en variable globale, car elles serviront
        // à détecter les collisions sur les bords de l'écran
        ecranLargeur=wScreen;
        ecranHauteur=hScreen;

        // on définit (au choix) la taille de la balle à 1/5ème de la largeur de l'écran
        boutonW=wScreen/3;
        boutonH=hScreen/7;
        mImg = setImage(mContext, R.mipmap.boutonjouer,boutonW,boutonH);
    }

    public void draw(Canvas canvas)
    {
        if(mImg==null) {return;}
        canvas.drawBitmap(mImg.getBitmap(), x, y, null);

    }

    public void apresClic()
    {
        mContext.lancerPartie();
    }

    // Getters & Setters

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getBoutonW() {
        return boutonW;
    }

    public void setBoutonW(int boutonW) {
        this.boutonW = boutonW;
    }

    public int getBoutonH() {
        return boutonH;
    }

    public void setBoutonH(int boutonH) {
        this.boutonH = boutonH;
    }
}