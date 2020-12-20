
package org.inria.restlet.mta.backend;

import org.inria.restlet.mta.database.api.impl.Ocean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * created by Bonaventure Gbehe && Rebecca Ehua
 */
public class Zone {
    private Ocean ocean ;
    private int x;
    private int y;
    private  int id;
    private String nom ="" ;
    private int nb_sardines;
    private Requin requin ;
    //nombre de poisson pilotes par defaut
    private final  int nombrePoisonPilote = 2 ;
    //la liste des poissons pilotes de la zone
    private List<PoisonPilote> listDesPoisonPilotes = new ArrayList<PoisonPilote>() ;

    /**
     * Le construction
     * @param ocean chaque zone se trrouve bien sûr dans un océan
     * @param x  coordonnées x de la zone
     * @param y coordonnées y de la zone
     * @param nb_sardines nombre de sardines dans la zone. variable dans le temps
     * @param numero  le numero de la zone. identifiant
     */
    public Zone(Ocean ocean , int x,int y,int nb_sardines, int numero){
        this.ocean = ocean ;
        this.x=x;
        this.y=y;
        this.id = numero ;
        this.nom = "("+x+","+y+")";
        this.nb_sardines=nb_sardines;

        //les zones de nummero multiple de 25 auront un requin par  defaut et les autres zones n'auront pas de requin
        //cela va permmetre d'avoir moins de requins dans l'océan, du coup, une circulation fluide
        if (numero%25 == 0   ){
            this.requin = new Requin(this,numero) ;
        }
        createPilote() ;
    }



    public String getNom(){
        return nom ;
    }

    public Requin getRequin(){
        return  this.requin ;
    }

    public Ocean getOcean(){ return this.ocean ;}
    public int getNb_sardines(){ return nb_sardines ;}

    public int getX() {
        return x;
    }
    public int getY() { return y; }

    public int getId() {
        return id;
    }

    /**
     * demarrer le requin de la zone, si elle en a.
     */
    public   void startRequin(){  if(requin != null){this.requin.start();} }

    public   void joinRequin(){
        if (requin != null) {
            try { this.requin.join(); } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }

    /**
     * Cette méthode sera appelée dans API pour créer un reqyuin dans la zone à la reception d'une requête HTTP de type POST
     * @param vie le cycle de vie du requin
     * @return le nouveau requin créé
     */
    public Requin createRequin(int vie) {
        this.requin = new Requin(this,this.id, vie) ;
        this.requin.start();
        try { this.requin.join(); } catch (InterruptedException e) {e.printStackTrace();}

        return this.requin;
    }

    /**un requin entre dans la zone
     * @param req le requin qui veut entrer dans la zone
     */
    public synchronized void entrer(Requin req){
        while ((requin != null)&& (!req.equals(requin) )){
            //System.out.println("Le requin "+requin.getNom()+" mis en attente dans la zone Z"+this.nom);
            try {wait();} catch (InterruptedException e) { e.printStackTrace();}
        }
        this.requin = req ;
        req.setZone(this) ;

        System.out.println("Le requin "+requin.getNom()+" vient d'arriver dans la zone Z"+this.nom);

        synchronized (this.requin){
            req.notifyAll();
        }

        manger() ;
    }

    /**Pour manger
     */
    private void manger() {
        Random rand = new Random();

        //un nombre aléatoire sardines à manger
        int aManger =  rand.nextInt(nb_sardines+1) ;

        if(aManger < nb_sardines){
            nb_sardines -= aManger ;
        }else {
            aManger = nb_sardines ;
            nb_sardines = 0 ;
        }
       System.out.println("Le requin "+requin.getNom()+" a mangé "+aManger+" sardines dans la zone Z"+this.nom);

    }

    /** un requin sort de la zone
     */
    public synchronized void   sortir(){
       // System.out.println("Le requin "+requin.getNom()+" sort de  la zone Z"+this.nom);
        this.requin = null ;
        notifyAll();
    }

    /**
     * Créer les poissons pilote de la zone
     */
    private void createPilote(){
        for(int i = 1; i <= nombrePoisonPilote ; i++ ){
            this.listDesPoisonPilotes.add(new PoisonPilote(this,i) ) ;
        }
    }


    /**Appeler start() de tous les poissons pilotes de la zone
     */
    public void pilotesStart(){
        for (PoisonPilote pilote:listDesPoisonPilotes) {
            pilote.start();
        }
    }

    /**Appeler start() de tous les poissons pilotes de kla zone
     */
    public void pilotesJoin(){
        for (PoisonPilote pilote:listDesPoisonPilotes) {
            try { pilote.join(); } catch (InterruptedException e) { e.printStackTrace();  }
        }
    }


}

