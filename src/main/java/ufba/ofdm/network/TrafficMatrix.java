package ufba.ofdm.network;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;

import ufba.ofdm.config.NetworkConfig;

public class TrafficMatrix{

    private List<Traffic> trafficList;

    public TrafficMatrix(){

        LinkedList<Traffic> trafficList = new LinkedList<Traffic>();
        setTrafficList(trafficList);

    }

    public TrafficMatrix(NetworkConfig netConfig, float traffic){

        LinkedList<Traffic> trafficList = new LinkedList<Traffic>();

        for(int origNode = 0; origNode < netConfig.getNodesNumber(); origNode++)
            for(int destNode = 0; destNode < netConfig.getNodesNumber(); destNode++)
                if(origNode != destNode)    trafficList.add( new Traffic( origNode, destNode, traffic ) );

        setTrafficList(trafficList);

    }

    public TrafficMatrix(NetworkConfig netConfig, String fileName){

        LinkedList<Traffic> trafficList = new LinkedList<Traffic>();

        try {
            BufferedReader in = new BufferedReader(new FileReader(netConfig.getFileMatrixDescriptionLocation() + fileName));

            String line = in.readLine();

            int i = 0;
            while (line != null) {
                String[] lineString = line.split("\\s+");
                for(int j = 0; j < lineString.length; j++)
                    if( Float.parseFloat(lineString[j]) != 0)
                        trafficList.add( new Traffic( i, j, Float.parseFloat(lineString[j])  )  );
                line = in.readLine();
                i++;
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        setTrafficList(trafficList);

    }

    public List<Traffic> getTrafficList() {
        return trafficList;
    }

    public void setTrafficList(List<Traffic> trafficList) {
        this.trafficList = trafficList;
    }
    
}