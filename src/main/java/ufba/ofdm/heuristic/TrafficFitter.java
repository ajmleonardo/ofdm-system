package ufba.ofdm.heuristic;

import ufba.ofdm.graph.util.Path;
import ufba.ofdm.network.TransparentNetwork;

public interface TrafficFitter {

    public TransparentNetwork fit( int orig, int dest, int Load, TransparentNetwork network, Path path );

}