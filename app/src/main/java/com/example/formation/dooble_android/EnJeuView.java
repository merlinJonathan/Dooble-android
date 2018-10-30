package com.example.formation.dooble_android;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by kurzen on 16/02/2018.
 */

public class EnJeuView extends SurfaceView implements SurfaceHolder.Callback{

    private GameLoopThread gameLoopThread;
    private BitmapDrawable imageFond;
    private int ecranLargeur, ecranHauteur;
    private EnJeuActivity mContext;
    private Manche laPartie;
    private long debutChrono;

    public EnJeuView(EnJeuActivity c, int ecranLargeur, int ecranHauteur)
    {
        super(c);
        getHolder().addCallback(this);

        gameLoopThread = new GameLoopThread(this);
        this.ecranHauteur = ecranHauteur;
        this.ecranLargeur = ecranLargeur;
        mContext = c;
        laPartie = new Manche(mContext, ecranLargeur, ecranHauteur);
        debutChrono = System.currentTimeMillis();
    }


    public void updateChrono()
    {
        long timer = System.currentTimeMillis() - debutChrono;
        laPartie.setChrono(timer);
    }

    // Methode pour les images
    public void doDraw(Canvas canvas) {
        if(canvas==null) {return;}

        // on dessine les objets
        laPartie.drawCartes(canvas,gameLoopThread.getFramesPerSecond(), imageFond, laPartie.getCarteBas(), laPartie.getCarteHaut());
    }

    public BitmapDrawable setImage(final Context c, final int ressource, final int w, final int h)
    {
        Drawable dr = c.getResources().getDrawable(ressource);
        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
        return new BitmapDrawable(c.getResources(), Bitmap.createScaledBitmap(bitmap, w, h, true));
    }

    // Methode des surfaceview
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        // création du processus GameLoopThread si cela n'est pas fait
        if (gameLoopThread.getState() == Thread.State.TERMINATED) {
            gameLoopThread = new GameLoopThread(this);
        }
        gameLoopThread.setRunning(true);
        gameLoopThread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        gameLoopThread.setRunning(false);
        while (retry) {
            try {
                gameLoopThread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int w, int h) {
        resize(w, h);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int currentX = (int)event.getX();
        int currentY = (int)event.getY();

        switch (event.getAction()) {

            // code exécuté lorsque le doigt touche l'écran.
            case MotionEvent.ACTION_DOWN:
                int numSymbole = 0;

                // Carte du bas
                for(numSymbole = 0 ; numSymbole < 8; numSymbole++)
                {
                    if(currentX >= laPartie.getCarteBas().getSymboles(numSymbole).getX() &&
                            currentX <= laPartie.getCarteBas().getSymboles(numSymbole).getX() + laPartie.getCarteBas().getSymboles(numSymbole).getSymboleW() &&
                            currentY >= laPartie.getCarteBas().getSymboles(numSymbole).getY() &&
                            currentY <= laPartie.getCarteBas().getSymboles(numSymbole).getY() + laPartie.getCarteBas().getSymboles(numSymbole).getSymboleH())
                    {
                        if(laPartie.comparaisonSymboles(laPartie.getCarteHaut(), laPartie.getCarteBas().getSymboles(numSymbole)))
                        {
                            laPartie.setPartieTermine(true);
                        }
                    }
                }

                // Carte du haut
                numSymbole = 0;
                for(numSymbole = 0 ; numSymbole < 8; numSymbole++)
                {
                    if(currentX >= laPartie.getCarteHaut().getSymboles(numSymbole).getX() &&
                            currentX <= laPartie.getCarteHaut().getSymboles(numSymbole).getX() + laPartie.getCarteHaut().getSymboles(numSymbole).getSymboleW() &&
                            currentY >= laPartie.getCarteHaut().getSymboles(numSymbole).getY() &&
                            currentY <= laPartie.getCarteHaut().getSymboles(numSymbole).getY() + laPartie.getCarteHaut().getSymboles(numSymbole).getSymboleH())
                    {
                        if(laPartie.comparaisonSymboles(laPartie.getCarteBas(), laPartie.getCarteHaut().getSymboles(numSymbole)))
                        {
                            laPartie.setPartieTermine(true);
                        }
                    }
                }
                break;

        }
        if(laPartie.isPartieTermine())
        {
            laPartie.nouvelleCarte();
            laPartie.setPartieTermine(false);
        }
        return true;  // On retourne "true" pour indiquer qu'on a géré l'évènement
    }

    public void resize(int wScreen, int hScreen)
    {
        ecranLargeur = wScreen;
        ecranHauteur = hScreen;

        imageFond = setImage(mContext, R.mipmap.en_jeu_sans_carte, ecranLargeur, ecranHauteur);
    }
}