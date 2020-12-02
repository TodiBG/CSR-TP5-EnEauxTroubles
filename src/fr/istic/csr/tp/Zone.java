package fr.istic.csr.tp;

import java.util.Random;

public class Zone {
    Ocean ocean ;
    private int x;
    private int y;
    private int nb_sardines;
    private Requin requin ;

    public Zone(Ocean ocean , int x,int y,int nb_sardines){
        this.x=x;
        this.y=y;
        this.nb_sardines=nb_sardines;
        this.requin = new Requin(this) ;
        this.ocean = ocean ;
    }

    public Ocean getOcean(){ return this.ocean ;}

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public int getNb_sardines() {
        return nb_sardines;
    }

    public void setNb_sardines(int nb_sardines) {
        this.nb_sardines = nb_sardines;
    }

    public synchronized void entrer(Requin req){
        while (requin != null){
            try {wait();} catch (InterruptedException e) { e.printStackTrace();}
        }
        this.requin = req ;
        req.setZone(this) ;

        manger() ;
    }

    private void manger() {
        Random rand = new Random();

        int aManger =  rand.nextInt(nb_sardines+1) ;

        if(aManger < nb_sardines){
            nb_sardines -= aManger ;
        }else {
            nb_sardines = 0 ;
        }

    }


    public synchronized void  sortir(){
        this.requin = null ;
        notifyAll();
    }


}

