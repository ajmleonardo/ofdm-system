package ufba.ofdm.heuristic;
import ufba.ofdm.network.*;

public interface RMSAHeuristic {

    
    public TransparentNetwork allocate( TransparentNetwork network, TrafficMatrix trafficMatrix, TrafficSorter sorter, 
                                        TrafficFitter fitter );
    
}