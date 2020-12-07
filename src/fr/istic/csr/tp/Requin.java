package fr.istic.csr.tp;

import java.util.Random;

/***
 * Crée par Bonaventure Gbehe et Rebecca Ehua
 */

public class Requin extends Thread {

    //La zone precedente du requin
    private Zone previousZone ; ;
    //La zone actuelle du requin
    private Zone currentZone ; ;
    //La nouvelle zone du requin
    private Zone nextZone ; ;
    //L'océan dans lequel évolue le requin
    private Ocean ocean ;
    private String nom ;
    private  int cycleDeVie = 4 ;
    private static final int NB_PILOTE_MAX =  10 ;
    private  int nb_poisson_pilote =  0 ;

    public  Requin (Zone zone ){
        this.currentZone = zone ;
        this.nom ="R"+zone.getNom() ;
        this.ocean = currentZone.getOcean() ;
    }

    public String getNom() { return  this.nom ; }

    public Zone getZone() { return this.currentZone ;}

    //le requin se deplace
    private  void move(){
        int nextZoneX = currentZone.getX() ;
        int nextZoneY = currentZone.getY() ;


        Random rand = new Random();
        int direction = rand.nextInt(2);
        int sens = rand.nextInt(2);

        /*choisir dans quelle direction se deplacer (X ou Y) */

        // direction X
        if(direction == 0){
            //choisir dans quel sens se deplacer (- ou +)

            if (sens == 0){   //dans le sens positif
                nextZoneX ++ ;
            }else { //dans le sens negatif
                nextZoneX -- ;
            }

            // Control d'un nouvelles coordonnées qu'on vient de definir
            if(nextZoneX< 0){
                nextZoneX = ocean.dimension - 1 ;
            }else if (nextZoneX > ocean.dimension - 1 ){
                nextZoneX = 0 ;
            }


        }else{ //direction Y

            //choisir dans quel sens se deplacer (- ou +)
            if (sens == 0){ //dans le sens positif
                nextZoneY ++ ;
            }else { //dans le sens negatif
                nextZoneY -- ;
            }

            // Control d'un nouvelles coordonnées qu'on vient de definir
            if(nextZoneY< 0){
                nextZoneY = ocean.dimension - 1 ;
            }else if (nextZoneY > ocean.dimension - 1 ){
                nextZoneY = 0 ;
            }
        }

        //Trouver la prochaine zone
        nextZone =  ocean.findZone(nextZoneX,nextZoneY ) ;

        if(nextZone == null){ move(); }

        nextZone.entrer(this);

    }

    /**
     * Cette methode doit être appeler dans la nouvelle zone où le requin veut entrer
     * @param zone la nouvelle
     */
    public void setZone(Zone zone){
        //Sortir de la zone actuelle
        if( currentZone != null){
            currentZone.sortir();
        }
        //une nouvelle zone
        this.currentZone = zone ;
        cycleDeVie -- ;
    }


    public synchronized void prendrePoisonPilote(PoisonPilote poisonPilote) {
        while (nb_poisson_pilote>=NB_PILOTE_MAX){
            try {wait(); } catch (InterruptedException e) { e.printStackTrace(); }
        }
        poisonPilote.setRequin(this);
        nb_poisson_pilote++ ;
        System.out.println("Le poison pilote "+poisonPilote.getNom()+" a commencé à suivre le requin "+nom+" dans la zone "+currentZone.getNom());

    }

    public synchronized void laisserPoisonPilote(PoisonPilote poisonPilote) {
        if (  (!poisonPilote.getZone().equals(this.currentZone) && (currentZone != null) ) ){

            poisonPilote.setRequin(null);
            poisonPilote.setZone(this.currentZone);
            nb_poisson_pilote -- ;
            notifyAll();

            System.out.println("Le poison pilote "+poisonPilote.getNom()+" a arreté de suivre le requin "+nom+" dans la zone "+currentZone.getNom());

        }
    }

    private void demeurerDansLaZone(){
        Random rand = new Random();
        int temps = rand.nextInt(5) + 1;
        temps = temps*100 ;

        try { sleep(temps); } catch (InterruptedException e) { e.printStackTrace(); }

    }


    @Override
    public void run() {

        while ( cycleDeVie > 0){
            move();

            demeurerDansLaZone() ;

            

        }







    }



}