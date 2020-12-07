package fr.istic.csr.tp;

public class PoisonPilote extends Thread {

    //Le requin suivi par le poison
    private Requin requin ;
    private Zone zone ;
    private String nom ;
    // Le pilote est libre (n'est pas occupé à suivre un requin actuellement)
    private boolean libre = true ;

    public PoisonPilote(Zone zone,int numero){
        this.zone = zone;
        this.nom = "P"+numero+"z"+this.zone.getNom() ;
    }

    /**
     * L methode permet au poisson de suivre u requin
     * @param requin, le requi à suivre
     */
    private void suivre(Requin requin){

        if(libre){
            if ((requin != null) && (this.zone.equals(requin.getZone()) ) ){
                requin.prendrePoisonPilote(this);
            }
        }
    }

    public void setLibre(boolean libre) {
        this.libre = libre;
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
    public void laisserLeRequin(){
        if (requin != null){
            requin.laisserPoisonPilote(this);
        }
    }

    @Override
    public void run() {

        while (true){
            suivre(zone.getRequin()) ;
            try {sleep(500); } catch (InterruptedException e) {e.printStackTrace(); }

            laisserLeRequin() ;
        }
    }
}
