package fr.istic.csr.tp;

import java.util.Random;

/***
 * Crée par Bonacventure Gbehe et Rebecca Ehua
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

    public  Requin (Zone zone ){
        this.currentZone = zone ;
        this.ocean = currentZone.getOcean() ;
    }

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
                nextZoneX = ocean.nombreZone - 1 ;
            }else if (nextZoneX > ocean.nombreZone - 1 ){
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
                nextZoneY = ocean.nombreZone - 1 ;
            }else if (nextZoneY > ocean.nombreZone - 1 ){
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
        currentZone.sortir();
        //la zone habituelle est devenue ancienne
        this.previousZone = currentZone ;
        //une nouvelle zone
        this.currentZone = zone ;
    }


    @Override
    public void run() {
        move();
    }
}
