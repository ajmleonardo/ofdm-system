package ufba.ofdm.network;

import ufba.ofdm.graph.util.Path;

public class LightPath {

    private int origin;
    private int destination;
    private int firstSlot;
    private int lastSlot;
    private float totalBandwidth;
    private Path path;

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

    public int getFirstSlot() {
        return firstSlot;
    }

    public void setFirstSlot(int firstSlot) {
        this.firstSlot = firstSlot;
    }

    public int getLastSlot() {
        return lastSlot;
    }

    public void setLastSlot(int lastSlot) {
        this.lastSlot = lastSlot;
    }

    public float getTotalBandwidth() {
        return totalBandwidth;
    }

    public void setTotalBandwidth(float totalBandwidth) {
        this.totalBandwidth = totalBandwidth;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

}
