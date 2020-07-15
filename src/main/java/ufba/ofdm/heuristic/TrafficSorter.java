package ufba.ofdm.heuristic;

import java.util.Collections;
import java.util.List;

import ufba.ofdm.network.Traffic;

public class TrafficSorter {

    public List<Traffic> sortTraffic( List<Traffic> trafficList ){

        return trafficList;
    }

    public List<Traffic> sortMinToMax( List<Traffic> trafficList ){

        Collections.sort(trafficList);

        return trafficList;
    }

    public List<Traffic> sortMaxToMin( List<Traffic> trafficList ){

        Collections.reverse(trafficList);

        return trafficList;
    }

    public List<Traffic> sortRandom( List<Traffic> trafficList ){

        Collections.shuffle(trafficList);

        return trafficList;
    }
  
}