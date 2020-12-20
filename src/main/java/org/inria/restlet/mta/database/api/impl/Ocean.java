

package org.inria.restlet.mta.database.api.impl;

import org.inria.restlet.mta.backend.Requin;
import org.inria.restlet.mta.backend.Zone;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Ocean {

    public static final int dimension = 10 ;
    //public  static final   int  nombreZone = dimension*dimension ;
    private static final int Nb_sardines_by_Zone = 45;

    private List<Zone> listZone = new ArrayList<Zone>() ;


    /** on met en place le design pattern singleton pour avoir une unique instance de la base de  donnée
     * pour cela on met le constructeur en private et crée une methode getInstance() pour renvoyer l'unisue instance de la BD.
     */

    private static Ocean instance;
    private Ocean ocean ;


    private Ocean(){ }


    public static Ocean getInstance (){
        if( instance == null){ instance = new Ocean() ;  }
        return  instance ;
    }

    /**
     * @param X coordonnée X de la zone
     * @param Y coordonnée Y de la zone
     * @return la zone trouvée.  renvoie null si aucune zone n'est rouvée
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


    public void fonctionner (){
        int numero = 0 ;
        for (int x = 0 ; x < dimension ; x ++ ){
            for (int y = 0 ; y < dimension ; y++ ){
                listZone.add(new Zone(this,x,y,Nb_sardines_by_Zone, numero )) ;
                numero ++ ;
            }
        }
        for (Zone zone: listZone) { zone.startRequin();}
        for (Zone zone: listZone) { zone.pilotesStart();}

        for (Zone zone: listZone) { zone.joinRequin() ; }
        for (Zone zone: listZone) { zone.pilotesJoin(); ; }
    }

    public List<Zone> getListZones(){
        return  listZone ;
    }

    public Zone getZoneById(int id ){
        Zone zone = null ;
        for (Zone z: listZone) {
            if ( z.getId() == id  ){
                zone = z ;
                break;
            }
        }
        return  zone ;
    }


    public Requin getRequinById(int reqId) {
        Requin req = null ; // new Requin(listZone.get(0),listZone.get(0).getId()) ;
        for (Zone z: listZone) {
            if ( z.getRequin() != null  ){
                if (z.getRequin().getIdReq() == reqId ){
                    req = z.getRequin() ;
                }
            }
        }
        return req ;
    }


    public List<Requin> getRequins() {
        List<Requin> requins = new ArrayList<Requin>() ;
        for (Zone z: listZone) {
            if ( z.getRequin() != null  ){
                requins.add(z.getRequin() ) ;
            }
        }
        return requins ;
    }

    /**
     * Créer et demarrer un requin au hazard dans une zone
     */
    public Requin createRequin(int vie){
        Random rand = new Random();
        int index = 0 ;
        Zone zone ;

        do{
            index = rand.nextInt(listZone.size()-1) ;
            zone =  listZone.get(index) ;
        }while ( zone.getRequin() != null ) ;

        return zone.createRequin(vie) ;
    }




}
