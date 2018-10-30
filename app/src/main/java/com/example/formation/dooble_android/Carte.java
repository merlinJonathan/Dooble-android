package com.example.formation.dooble_android;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * Created by kurzen on 17/02/2018.
 */

public class Carte {

    /** Attributs pour les images **/

    private BitmapDrawable mImg = null;
    private int x, y ;
    private int carteW, carteH;
    private int ecranLargeur, ecranHauteur;
    private EnJeuActivity mContext;

    /** Attributs pour la carte elle meme **/

    private Symbole[] symboles = new Symbole[8];
    private int nb_symbole ;

    /***** Constructeurs *****/

    public Carte(EnJeuActivity c, int largeurEcran, int hauteurEcran, int numCarte)
    {
        /** attributs de la carte **/
        nb_symbole = 0 ;

        /** attributs pour les images **/
        carteH = hauteurEcran/3;
        carteW = largeurEcran/2;

        ecranHauteur = hauteurEcran;
        ecranLargeur = largeurEcran;

        if(numCarte == 0)
        {
            x = ecranLargeur/2 - carteW/2;
            y = ecranHauteur/4*2 + ecranHauteur/20;
        }
        else
        {
            x = 0 - carteW;
            y = hauteurEcran/4 - carteH/2 + hauteurEcran/20;
        }

        mContext = c;
        mImg = setImage(mContext, R.mipmap.fondcarte, carteW, carteH);
    }

    public Carte(Carte c) {
        this.nb_symbole = c.nb_symbole ;
        this.carteH = c.carteH;
        this.carteW = c.carteW;

        this.ecranHauteur = c.ecranHauteur;
        this.ecranLargeur = c.ecranLargeur;
        this.x = c.x;
        this.y = c.y;

        this.mContext = c.mContext;
        this.mImg = c.mImg;
    }

    public BitmapDrawable setImage(final Context c, final int ressource, final int w, final int h)
    {
        Drawable dr = c.getResources().getDrawable(ressource);
        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
        return new BitmapDrawable(c.getResources(), Bitmap.createScaledBitmap(bitmap, w, h, true));
    }

    public void resize(int wScreen, int hScreen) {
        ecranLargeur=wScreen;
        ecranHauteur=hScreen;

        carteW =wScreen/3;
        carteH =hScreen/2;
        mImg = setImage(mContext,R.mipmap.fondcarte, carteW, carteH);
    }

    public void draw(Canvas canvas)
    {
        if(mImg==null) {return;}
        canvas.drawBitmap(mImg.getBitmap(), x, y, null);

        for(int i  = 0; i < 8; i++)
        {
            symboles[i].draw(canvas);
        }
    }

    /** Getters & Setters **/
    public Symbole getSymboles(int i){
        return symboles[i];
    }

    public void setSymbole(Symbole s) {
        symboles[nb_symbole] = new Symbole(s) ;
        nb_symbole++ ;
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

    public int getCarteW() {
        return carteW;
    }

    public int getCarteH() {
        return carteH;
    }
}
