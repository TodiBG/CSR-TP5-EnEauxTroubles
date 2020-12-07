package fr.istic.csr.tp;

public class PoisonPilote extends Thread {

    //Le requin suivi par le poison
    private Requin requin ;
    private Zone zone ;
    private String nom ;

    public PoisonPilote(Zone zone,int numero){
        this.zone = zone;
        this.nom = "P"+numero+"z"+this.zone.getNom() ;
    }

    /**
     * L methode permet au poisson de suivre u requin
     * @param requin, le requi à suivre
     */
    private void suivre(Requin requin){
        if (requin != null){
            requin.prendrePoisonPilote(this);
        }
    }

    public String getNom() {
        return nom;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public void setRequin(Requin requin) {
        this.requin = requin;
    }

    /**
     * Le poison arrêter de suis son requin
     */
    public void laisserLeRequi(){
        if( (this.zone != null) && (this.requin != null) ){

            if( !(this.zone.equals(requin.getZone())) ){
                requin.laisserPoisonPilote(this);
            }
        }
    }

    @Override
    public void run() {

        while (true){
            suivre(zone.getRequin()) ;

            laisserLeRequi() ;
        }
    }
}
