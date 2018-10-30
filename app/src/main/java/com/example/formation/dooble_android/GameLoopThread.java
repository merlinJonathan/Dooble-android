package com.example.formation.dooble_android;

/**
 * Created by kurzen on 16/02/2018.
 */
import android.graphics.Canvas;

public class GameLoopThread extends Thread
{
    private final static int FRAMES_PER_SECOND = 30;
    private final static int SKIP_TICKS = 1000 / FRAMES_PER_SECOND;

    private MenuView view = null; // l'objet SurfaceView que nous verrons plus bas
    private EnJeuView enJeuView = null;
    private boolean menu = false, enJeu = false;

    private boolean running = false; // état du thread, en cours ou non

    // constructeur de l'objet, on lui associe l'objet view passé en paramètre
    public GameLoopThread(MenuView view) {
        this.view = view;
        menu = true;
    }
    public GameLoopThread(EnJeuView view) {
        this.enJeuView = view;
        enJeu = true;
    }

    // défini l'état du thread : true ou false
    public void setRunning(boolean run) {
        running = run;
    }

    // démarrage du thread
    @Override
    public void run()
    {
        long startTime;
        long sleepTime;

        while (running)
        {
            startTime = System.currentTimeMillis();

            if(menu)
                runMenu();
            if(enJeu)
                runEnJeu();

            sleepTime = SKIP_TICKS-(System.currentTimeMillis() - startTime);
            try {
                if (sleepTime >= 0) {sleep(sleepTime);}
            }
            catch (Exception e) {}
        }
    }


    public void runMenu()
    {
        Canvas c = null;
        try {
            c = view.getHolder().lockCanvas();
            synchronized (view.getHolder()) {view.doDraw(c);}
        }
        finally
        {
            if (c != null) {view.getHolder().unlockCanvasAndPost(c);}
        }
    }

    public void runEnJeu()
    {
        synchronized (enJeuView.getHolder()) {enJeuView.updateChrono();}

        Canvas c = null;
        try {
            c = enJeuView.getHolder().lockCanvas();
            synchronized (enJeuView.getHolder()) {enJeuView.doDraw(c);}
        }
        finally
        {
            if (c != null) {enJeuView.getHolder().unlockCanvasAndPost(c);}
        }
    }

    public static int getFramesPerSecond() {
        return FRAMES_PER_SECOND;
    }
} // class GameLoopThread