package fr.istic.csr.tp;

import java.util.ArrayList;
import java.util.List;

public class Ocean {

    public  static final   int  nombreZone = 25 ;

    private List<Zone> listZone = new ArrayList<Zone>() ;

    /**
     *
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

    public static void main(String[] args) {
	// write your code here
    }
}
