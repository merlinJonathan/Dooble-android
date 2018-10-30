package com.example.formation.dooble_android;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * Created by kurzen on 17/02/2018.
 */

public class Symbole {

    /***** Attributs *****/

    private BitmapDrawable mImg;
    private int x, y;
    private int ecranLargeur, ecranHauteur;
    private EnJeuActivity mContext;
    private int symboleW, symboleH;
    private int ressource;

    /***** Constructeurs *****/
    public Symbole(Symbole s){
        this.x = s.x;
        this.y = s.y;
        this.ecranLargeur = s.ecranLargeur;
        this.ecranHauteur = s.ecranHauteur;
        this.mContext = s.mContext;
        this.symboleH = s.symboleH;
        this.symboleW = s.symboleW;
        this.ressource = s.ressource;
        this.mImg = s.mImg;
    }

    public Symbole(EnJeuActivity c, int largeurEcran, int hauteurEcran, int ressource) {
        this.mContext = c;
        this.ecranLargeur = largeurEcran;
        this.ecranHauteur = hauteurEcran;
        this.x = 0;
        this.y = 0;
        symboleW = (largeurEcran/2)/5;
        symboleH = (hauteurEcran/3)/5;
        this.ressource = ressource;

        this.mImg = setImage(mContext, ressource, symboleW, symboleH);
    }

    /***** Methodes *****/

    public boolean equals(Symbole symboleExterne) {
        if (this.ressource == symboleExterne.ressource)
            return true;
        else
            return false;
    }

    public void draw(Canvas canvas)
    {
        if(mImg==null) {return;}
        canvas.drawBitmap(mImg.getBitmap(), x, y, null);
    }

    public void resize(int wScreen, int hScreen) {

        ecranHauteur = hScreen;
        ecranLargeur = wScreen;

        symboleW = (ecranLargeur/2)/5;
        symboleH = (ecranHauteur/3)/5;

        mImg = setImage(mContext, ressource, symboleW, symboleH);
    }

    public BitmapDrawable setImage(final Context c, final int ressource, final int w, final int h)
    {
        Drawable dr = c.getResources().getDrawable(ressource);
        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
        return new BitmapDrawable(c.getResources(), Bitmap.createScaledBitmap(bitmap, w, h, true));
    }

    /***** Getteurs & Setters *****/

    public BitmapDrawable getmImg(){
        return this.mImg;
    }

    public void setmImg(BitmapDrawable s){
        this.mImg = s ;
    }

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

    public void setSymboleW(int symboleW) {
        this.symboleW = symboleW;
    }

    public void setSymboleH(int symboleH) {
        this.symboleH = symboleH;
    }

    public int getSymboleW() {
        return symboleW;
    }

    public int getSymboleH() {
        return symboleH;
    }
}

