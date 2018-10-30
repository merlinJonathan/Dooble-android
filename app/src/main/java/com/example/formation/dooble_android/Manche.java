package com.example.formation.dooble_android;


/**
 * Created by kurzen on 17/02/2018.
 */

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;

import java.util.Random ;
import java.util.Vector ;

public class Manche {


    /***** Attributs *****/

    private Symbole[] tab_symbole = new Symbole[57] ;
    private Carte carteBas;
    private Carte carteHaut; // carte du haut de l'ecran
    private Vector<Integer> symboleDispo = new Vector<Integer>() ;
    private Canvas mCanvas;
    private int nbCarteParManche, nbCarteFaite;
    private String compteurCarte;
    private boolean partieTermine = false;
    private long chrono;
    private String texteChrono;
    private boolean animationCarteTermine;
    private int compteurfps = 0;

    /** attributs pour la gestion d'image **/
    private EnJeuActivity mContext;
    private int ecranLargeur, ecranHauteur;
    private Paint p;

    /***** Constructeurs *****/

    public Manche(EnJeuActivity c, int largeurEcran, int hauteurEcran) {

        mContext = c;
        ecranLargeur = largeurEcran;
        ecranHauteur = hauteurEcran;

        // attributs du textes
        nbCarteFaite = 0;
        nbCarteParManche = 10;
        compteurCarte = new String(nbCarteFaite + "/" + nbCarteParManche);

        chrono = 0;
        texteChrono = new String("00:00");
        animationCarteTermine = false;

        p = new Paint();
        p.setColor(Color.WHITE);
        p.setStyle(Paint.Style.FILL);
        p.setTextSize(ecranHauteur/25);
        chargement();
    }


    /***** Methodes *****/
    public void nouvelleCarte() {
        Vector<Integer> vect;
        Vector<Integer> symboleChoisit = new Vector<Integer>() ;
        Random alea = new Random() ;
        int nbAlea = 8;
        animationCarteTermine = false;
        carteHaut = new Carte(mContext, ecranLargeur, ecranHauteur, 1) ;

        // initialisation du vector
        vect = new Vector<Integer>();
        for(int i = 8; i < 57; i++)
            vect.add(i);

        // On selectionne 7 symboles parmis ceux qui ne sont pas sur la carte du bas
        for(int i = 0; i < 7; i++)
        {
            nbAlea = alea.nextInt(vect.size()) ;
            symboleChoisit.add(vect.elementAt(nbAlea));
            vect.removeElementAt(nbAlea);
        }

        // on choisit le dernier symbole qui sera sur la carte du bas
        nbAlea = alea.nextInt(8) ;
        symboleChoisit.add(nbAlea);

        // on melange les symboles puis les pose sur la carte
        for(int i = 0; i < 8; i++)
        {
            nbAlea = alea.nextInt(symboleChoisit.size()) ;
            carteHaut.setSymbole(tab_symbole[symboleChoisit.elementAt(nbAlea)]);
            symboleChoisit.removeElementAt(nbAlea);
        }

        // on redessine la nouvelle carte
        compteurCarte = new String(nbCarteFaite + "/" + nbCarteParManche);
    }

    private void chargement() {
        initialisationSymbole() ;
        initialisationCarte() ;
    }

    public void drawCartes(Canvas canvas, int fps, BitmapDrawable imgFond, Carte c1, Carte c2) {
        mCanvas = canvas;

        // On dessine l'image de fond
        canvas.drawBitmap(imgFond.getBitmap(), 0, 0, null);

        // on dessine la carte du bas
        placerSymbole(c1);
        c1.draw(mCanvas);

        // on dessine la carte du haut
        if(!animationCarteTermine)
        {
            c2.setX(c2.getX() + ((ecranLargeur/2 - c2.getCarteW()/2) - (0-c2.getCarteW()))/fps);
            if(c2.getX() == (ecranLargeur/2 - c2.getCarteW()/2))
                animationCarteTermine = true;
        }
        placerSymbole(c2);
        c2.draw(canvas);

        // on met a jours le texte
        canvas.drawText(compteurCarte, ecranLargeur/10, ecranHauteur/10, p);
        canvas.drawText(texteChrono, ecranLargeur/10*8, ecranHauteur/10, p);
    }

    public void placerSymbole(Carte c) {
        // placement des symboles sur les cartes
        // placement symbole 0
        c.getSymboles(0).setX(c.getX() + c.getCarteW()/2 - c.getSymboles(0).getSymboleW()/2);
        c.getSymboles(0).setY(c.getY() + c.getCarteH()/2 - c.getSymboles(0).getSymboleH()/2);

        // placement symbole 1
        c.getSymboles(1).setX(c.getX() + (c.getCarteW()/5)*4 - c.getSymboles(1).getSymboleW()/2);
        c.getSymboles(1).setY(c.getY() + c.getCarteH()/2 - c.getSymboles(1).getSymboleH()/2);

        // placement symbole 2
        c.getSymboles(2).setX(c.getX() + (c.getCarteW()/5) - c.getSymboles(2).getSymboleW()/2);
        c.getSymboles(2).setY(c.getY() + c.getCarteH()/2 - c.getSymboles(2).getSymboleH()/2);

        // placement symbole 3
        c.getSymboles(3).setX(c.getX() + c.getCarteW()/2 - c.getSymboles(3).getSymboleW()/2);
        c.getSymboles(3).setY(c.getY() + c.getCarteH()/6 - c.getSymboles(3).getSymboleH()/2);

        // placement symbole 4
        c.getSymboles(4).setX(c.getX() + c.getCarteW()/2 - c.getSymboles(4).getSymboleW()/2);
        c.getSymboles(4).setY(c.getY() + (c.getCarteH()/6)*5 - c.getSymboles(4).getSymboleH()/2);

        // placement symbole 5
        c.getSymboles(5).setX(c.getX() + (c.getCarteW()/7)*2 - c.getSymboles(5).getSymboleW()/2);
        c.getSymboles(5).setY(c.getY() + (c.getCarteH()/7)*5 - c.getSymboles(5).getSymboleH()/2);

        // placement symbole 6
        c.getSymboles(6).setX(c.getX() + (c.getCarteW()/7)*5 - c.getSymboles(6).getSymboleW()/2);
        c.getSymboles(6).setY(c.getY() + (c.getCarteH()/7)*2 - c.getSymboles(6).getSymboleH()/2);

        // placement symbole 7
        c.getSymboles(7).setX(c.getX() + (c.getCarteW()/7)*2 - c.getSymboles(7).getSymboleW()/2);
        c.getSymboles(7).setY(c.getY() + (c.getCarteH()/7)*2 - c.getSymboles(7).getSymboleH()/2);

    }

    private void initialisationSymbole() {
        //lecture du fichier texte
        Vector<Integer> vect = new Vector<Integer>() ;
        Random alea = new Random() ;
        int next ;

        vect.add(R.mipmap.baleine2) ;
        vect.add(R.mipmap.voile) ;
        vect.add(R.mipmap.algue_rose) ;
        vect.add(R.mipmap.baleine) ;
        vect.add(R.mipmap.bateau) ;
        vect.add(R.mipmap.bouee) ;
        vect.add(R.mipmap.bouee2) ;
        vect.add(R.mipmap.canard) ;
        vect.add(R.mipmap.canard2) ;
        vect.add(R.mipmap.coquillage) ;
        vect.add(R.mipmap.coquille) ;
        vect.add(R.mipmap.corail) ;
        vect.add(R.mipmap.crabe) ;
        vect.add(R.mipmap.croco) ;
        vect.add(R.mipmap.croco_ballon) ;
        vect.add(R.mipmap.crustace) ;
        vect.add(R.mipmap.dauphin) ;
        vect.add(R.mipmap.dauphin_casque) ;
        vect.add(R.mipmap.dauphin_violet) ;
        vect.add(R.mipmap.elephant_rose) ;
        vect.add(R.mipmap.espadon) ;
        vect.add(R.mipmap.etoile) ;
        vect.add(R.mipmap.grenouille) ;
        vect.add(R.mipmap.gros_poisson) ;
        vect.add(R.mipmap.gros_poisson2) ;
        vect.add(R.mipmap.hippocampe) ;
        vect.add(R.mipmap.homard) ;
        vect.add(R.mipmap.homard2) ;
        vect.add(R.mipmap.hypo) ;
        vect.add(R.mipmap.hypocampe) ;
        vect.add(R.mipmap.hypocampotame) ;
        vect.add(R.mipmap.hypo_rose) ;
        vect.add(R.mipmap.jet) ;
        vect.add(R.mipmap.manchot) ;
        vect.add(R.mipmap.meduse) ;
        vect.add(R.mipmap.meduse1) ;
        vect.add(R.mipmap.mouette) ;
        vect.add(R.mipmap.nemo_friend) ;
        vect.add(R.mipmap.phoque) ;
        vect.add(R.mipmap.pinqouin) ;
        vect.add(R.mipmap.voile3) ;
        vect.add(R.mipmap.poisson_jaune) ;
        vect.add(R.mipmap.poisson_rouge) ;
        vect.add(R.mipmap.poisson_vert) ;
        vect.add(R.mipmap.poulpe) ;
        vect.add(R.mipmap.poulpe2) ;
        vect.add(R.mipmap.requin) ;
        vect.add(R.mipmap.serpent) ;
        vect.add(R.mipmap.snake) ;
        vect.add(R.mipmap.soleil) ;
        vect.add(R.mipmap.sousmarin) ;
        vect.add(R.mipmap.tiplouf) ;
        vect.add(R.mipmap.tortue) ;
        vect.add(R.mipmap.tortue2) ;
        vect.add(R.mipmap.voile2) ;
        vect.add(R.mipmap.triton) ;
        vect.add(R.mipmap.crapaud) ;

        symboleDispo = new Vector<Integer>(vect);
        for(int i = 0 ; i < 57 ; i++){
            next = alea.nextInt(vect.size()) ;
            tab_symbole[i] = new Symbole(mContext, ecranLargeur, ecranHauteur, vect.elementAt(next)) ;
            vect.removeElementAt(next);
        }
    }

    private void initialisationCarte() {
        Vector<Integer> vect;
        Vector<Integer> symboleChoisit = new Vector<Integer>() ;
        Random alea = new Random() ;
        int nbAlea = 8;

        carteBas = new Carte(mContext, ecranLargeur, ecranHauteur, 0) ;
        carteHaut = new Carte(mContext, ecranLargeur, ecranHauteur, 1) ;

        for(int j = 0 ; j < 8 ; j++)
        {
            carteBas.setSymbole(tab_symbole[j]);
        }

        // initialisation du vector
        vect = new Vector<Integer>();
        for(int i = 8; i < 57; i++)
            vect.add(i);

        // On selectionne 7 symboles parmis ceux qui ne sont pas sur la carte du bas
        for(int i = 0; i < 7; i++)
        {
            nbAlea = alea.nextInt(vect.size()) ;
            symboleChoisit.add(vect.elementAt(nbAlea));
            vect.removeElementAt(nbAlea);
        }

        // on choisit le dernier symbole qui sera sur la carte du bas
        nbAlea = alea.nextInt(8) ;
        symboleChoisit.add(nbAlea);

        // on melange les symboles puis les pose sur la carte
        for(int i = 0; i < 8; i++)
        {
            nbAlea = alea.nextInt(symboleChoisit.size()) ;
            carteHaut.setSymbole(tab_symbole[symboleChoisit.elementAt(nbAlea)]);
            symboleChoisit.removeElementAt(nbAlea);
        }
    }

    public boolean comparaisonSymboles(Carte c, Symbole s) {
        // s = Symbole clique et c = carte principal
        boolean result = false ;
        int i = 0 ;

        while(i < 8 && result == false)
        {
            if(c.getSymboles(i).equals(s) == true)
                result = true ;
            i++;
        }
        return result ;
    }

    /***** Getteurs *****/

    public Carte getCarteHaut() {
        return this.carteHaut;
    }

    public Carte getCarteBas() {
        return this.carteBas ;
    }


    /***** Setteurs *****/

    public void setPartieTermine(boolean partieTermine) {
        this.partieTermine = partieTermine;
        if(partieTermine)
            nbCarteFaite++;
        if(nbCarteFaite == 10)
            mContext.revenirMenu();
    }

    public boolean isPartieTermine() {
        return partieTermine;
    }

    public void setChrono(long timer)
    {
        long seconde, minute;
        String txtSeconde = new String(), txtMinute = new String();

        // gestion du temps
        this.chrono = timer;
        chrono /= 1000;
        seconde = chrono%60;
        chrono /= 60;
        minute = chrono;
        boolean minuteTermine = false;
        compteurfps++;
        // gestion de l'affichage
        if(minute < 10)
            txtMinute = new String("0" + minute);
        else
            txtMinute = new String("" + minute);

        if(seconde < 10)
            txtSeconde = new String("0" + seconde);
        else
            txtSeconde = new String("" + seconde);

        texteChrono = new String(txtMinute + ":" + txtSeconde);
    }
}
