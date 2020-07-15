package ufba.ofdm.network;

public class Traffic implements Comparable<Traffic>{

    private int origin;
    private int destination;
    private float demand;

    public Traffic( int origin, int destination, float demand ){

        setOrigin(origin);
        setDestination(destination);
        setDemand(demand);

    }

    public int getOrigin() {
        return origin;
    }

    public void setOrigin(int origin) {
        this.origin = origin;
    }

    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    public float getDemand() {
        return demand;
    }

    public void setDemand(float demand) {
        this.demand = demand;
    }
    
    public int compareTo(Traffic o){

        if(this.getDemand() > o.getDemand()){
            return 1;}
        else if(this.getDemand() < o.getDemand()){
            return -1;}
        else {
            return 0;}
    }
}