package fr.istic.csr.tp;

public class Zone {
    private int x;
    private int y;
    private int nb_sardines;

    public Zone(int x,int y,int nb_sardines){
        this.x=x;
        this.y=y;
        this.nb_sardines=nb_sardines;
    }
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }

    public int getNb_sardines() {
        return nb_sardines;
    }

    public void setNb_sardines(int nb_sardines) {
        this.nb_sardines = nb_sardines;
    }

    public void  accessZoneRequin(){

    }
}

