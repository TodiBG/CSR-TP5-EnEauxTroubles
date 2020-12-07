package fr.istic.csr.tp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Zone {
    Ocean ocean ;
    private int x;
    private int y;
    private String nom ="" ;
    private int nb_sardines;
    private Requin requin ;
    private final  int nombrePoisonPilote = 20 ;

    private List<PoisonPilote> listDesPoisonPilotes = new ArrayList<>() ;


    public Zone(Ocean ocean , int x,int y,int nb_sardines, int numero){
        this.ocean = ocean ;
        this.x=x;
        this.y=y;
        this.nom = "("+x+","+y+")";
        this.nb_sardines=nb_sardines;

        //les zones de nummero pair auront un requin par  defaut
        //et celles de nulero impair n'auront pas de requin pardeut
        if (numero%2 == 0 ){
            this.requin = new Requin(this) ;
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

    public int getX() {
        return x;
    }
    public int getY() { return y; }

    public   void startRequin(){  if(requin != null){this.requin.start();} }

    public   void joinRequin(){
        if (requin != null) {
            try { this.requin.join(); } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }

    //un requin entre dans la zone
    public synchronized void entrer(Requin req){
        while ((requin != null)&& (!req.equals(requin) )){
            try {wait();} catch (InterruptedException e) { e.printStackTrace();}
        }
        this.requin = req ;
        req.setZone(this) ;

        System.out.println("Le requin "+requin.getNom()+" vient d'arriver dans la zone Z"+this.nom);

        synchronized (this.requin){req.notifyAll(); }

        manger() ;
    }

    private void manger() {
        Random rand = new Random();

        int aManger =  rand.nextInt(nb_sardines+1) ;



        if(aManger < nb_sardines){
            nb_sardines -= aManger ;
        }else {
            aManger = nb_sardines ;
            nb_sardines = 0 ;
        }
        System.out.println("Le requin "+requin.getNom()+" a mangé "+aManger+" sardines dans la zone Z"+this.nom);

    }

    // un requin sort de la zone
    public synchronized void   sortir(){
        System.out.println("Le requin "+requin.getNom()+" sort de  la zone Z"+this.nom);
        this.requin = null ;
        notifyAll();
    }


    private void createPilote(){
        for(int i = 1; i <= nombrePoisonPilote ; i++ ){
            this.listDesPoisonPilotes.add(new PoisonPilote(this,i) ) ;
        }
    }


    //Appeler start() de tous les poissons pilotes de la zone
    public void pilotesStart(){
        for (PoisonPilote pilote:listDesPoisonPilotes) {
            pilote.start();
        }
    }

    //Appeler start() de tous les poissons pilotes de kla zone
    public void pilotesJoin(){
        for (PoisonPilote pilote:listDesPoisonPilotes) {
            try { pilote.join(); } catch (InterruptedException e) { e.printStackTrace();  }
        }
    }


}

