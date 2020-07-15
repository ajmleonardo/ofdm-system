package ufba.ofdm.network;

import java.util.LinkedList;
import java.util.List;

import ufba.ofdm.config.FiberLinkConfig;
import ufba.ofdm.graph.Edge;

public class FiberLink extends Edge {

    private int linkID;
    private List<SubCarrier> carrierList;
    int[] carrierArray;
    private int lastSlotUsed;
    private int totalLoad;

    public FiberLink() {
        super();
        this.carrierList = new LinkedList<SubCarrier>();
    }

    public FiberLink(Edge edge, FiberLinkConfig config, int linkID) {

        super( edge.getFromNode(), edge.getToNode(), edge.getWeight() );
        this.carrierArray = new int[config.getSubcarrierNumber()];

        // Setting up the subcarriers
        List<SubCarrier> sCarrierList = new LinkedList<SubCarrier>();
        for( int s=0; s < config.getSubcarrierNumber(); s++)
            sCarrierList.add( new SubCarrier(s, config.getCarrierBandwidth()) );
        setCarrierList( sCarrierList );

        // Setting up the link
        setLinkID(linkID);

    }

    public class SubCarrier {

        private int index;
        private boolean used;
        private boolean bandGuard;
        private float bandwidth;

        public SubCarrier(int index, float bandwidth) {
            this.index = index;
            this.used = false;
            this.bandGuard = false;
            this.bandwidth = bandwidth;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public boolean isUsed() {
            return used;
        }

        public void setUsed(boolean used) {
            this.used = used;
        }

        public boolean isBandGuard() {
            return bandGuard;
        }

        public void setBandGuard(boolean bandGuard) {
            this.bandGuard = bandGuard;
        }

        public float getBandwidth() {
            return bandwidth;
        }

        public void setBandwidth(float bandwidth) {
            this.bandwidth = bandwidth;
        }

    }

    public int getLinkID() {
        return linkID;
    }

    public void setLinkID(int linkID) {
        this.linkID = linkID;
    }

    public List<SubCarrier> getCarrierList() {
        return carrierList;
    }

    public void setCarrierList(List<SubCarrier> carrierList) {
        this.carrierList = carrierList;
    }

    public int getLastSlotUsed() {
        return lastSlotUsed;
    }

    public void setLastSlotUsed(int lastSlotUsed) {
        this.lastSlotUsed = lastSlotUsed;
    }

    public int getTotalLoad() {
        return totalLoad;
    }

    public void setTotalLoad(int totalLoad) {
        this.totalLoad = totalLoad;
    }

}