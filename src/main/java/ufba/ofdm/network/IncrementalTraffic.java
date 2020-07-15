package ufba.ofdm.network;

import java.util.LinkedList;
import java.util.List;

import ufba.ofdm.config.NetworkConfig;
import ufba.ofdm.config.TrafficConfig;

public class IncrementalTraffic {

    int numStep;
    List<TrafficMatrix> trafficMatrixList;

    public IncrementalTraffic(TrafficConfig trafficConfig, NetworkConfig netConfig){

        LinkedList<TrafficMatrix> trafficMatrixList = new LinkedList<TrafficMatrix>();
        float initDemand = trafficConfig.getInitDemand();
        float incRate = trafficConfig.getIncRate();

        if(trafficConfig.isCustomEnabled())
            for(int numStep = 0; numStep < trafficConfig.getNumSteps(); numStep++)
                trafficMatrixList.add( new TrafficMatrix(netConfig, "traffic-matrix.txt" ) );

        else
            for(int numStep = 0; numStep < trafficConfig.getNumSteps(); numStep++){

                //The traffic demand, for a given time-step, is found using the simple interest formula (linear growth)
                float trafficDemandInc = initDemand * (1 + (numStep * incRate)); 
                trafficMatrixList.add( new TrafficMatrix( netConfig, trafficDemandInc ) );

            }
            
        setNumStep(trafficConfig.getNumSteps());
        setTrafficMatrixList(trafficMatrixList);
    }

    public int getNumStep() {
        return numStep;
    }

    public void setNumStep(int numStep) {
        this.numStep = numStep;
    }

    public List<TrafficMatrix> getTrafficMatrixList() {
        return trafficMatrixList;
    }

    public void setTrafficMatrixList(List<TrafficMatrix> trafficMatrixList) {
        this.trafficMatrixList = trafficMatrixList;
    }
    
}