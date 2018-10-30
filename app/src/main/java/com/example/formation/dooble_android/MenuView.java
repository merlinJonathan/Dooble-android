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

public class MenuView extends SurfaceView implements SurfaceHolder.Callback {
    private GameLoopThread gameLoopThread;
    private MainActivity mContext;
    private BitmapDrawable imageFond;
    private int ecranLargeur, ecranHauteur;
    private BoutonJouer jouer = null;

    public MenuView(MainActivity c, int ecranLargeur, int ecranHauteur)
    {
        super(c);
        getHolder().addCallback(this);

        this.ecranLargeur = ecranLargeur;
        this.ecranHauteur = ecranHauteur;
        jouer = new BoutonJouer(c, ecranLargeur, ecranHauteur);
        mContext = c;
        gameLoopThread = new GameLoopThread(this);
    }

    // Methode pour les images
    public void doDraw(Canvas canvas) {
        if(canvas==null) {return;}


        // on dessine les objets
        canvas.drawBitmap(imageFond.getBitmap(), 0, 0, null);
        jouer.draw(canvas);
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
                if(currentX >= jouer.getX() &&
                        currentX <= jouer.getX() + jouer.getBoutonW() &&
                        currentY >= jouer.getY() &&
                        currentY <= jouer.getY() + jouer.getBoutonH())
                {
                    jouer.apresClic();
                }
                break;

            // code exécuté lorsque le doight glisse sur l'écran.
            case MotionEvent.ACTION_MOVE:

                break;

            // lorsque le doigt quitte l'écran
            case MotionEvent.ACTION_UP:

        }

        return true;  // On retourne "true" pour indiquer qu'on a géré l'évènement
    }

    public void resize(int wScreen, int hScreen)
    {
        ecranLargeur = wScreen;
        ecranHauteur = hScreen;

        imageFond = setImage(mContext, R.mipmap.menu_sans_bouton, ecranLargeur, ecranHauteur);
    }
}