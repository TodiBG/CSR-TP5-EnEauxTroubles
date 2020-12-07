package fr.istic.csr.tp;

import java.util.ArrayList;
import java.util.List;

public class Ocean {

    public static final int dimension = 10 ;
    //public  static final   int  nombreZone = dimension*dimension ;
    private static final int Nb_sardines_by_Zone = 50;

    private List<Zone> listZone = new ArrayList<Zone>() ;

    /**
     * @param X coordonnée X de la zone
     * @param Y coordonnée Y de la zone
     * @@return la zone trouvée.  renvoie null si aucune zone n'est rouvée
     */
    public Zone findZone(int X, int Y){
        Zone zone = null ;

        for (Zone z :listZone) {
            if (z.getX() == X && z.getY() == Y){
                zone = z ;
                break;
            }
        }

        return  zone ;
    }


    private void fonctionner (){
        int numero = 0 ;
        for (int x = 0 ; x < dimension ; x ++ ){
            for (int y = 0 ; y < dimension ; y++ ){
                listZone.add(new Zone(this,x,y,Nb_sardines_by_Zone, numero )) ;
                numero ++ ;
                if(numero < 3){}

            }
        }

        for (Zone zone: listZone) { zone.startRequin();}
        for (Zone zone: listZone) { zone.pilotesStart();}

        for (Zone zone: listZone) { zone.pilotesJoin(); ; }
        for (Zone zone: listZone) { zone.joinRequin() ; }
    }

    public static void main(String[] args) {

        new Ocean().fonctionner () ;



    }
}
