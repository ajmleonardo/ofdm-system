package ufba.ofdm.config;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import java.util.Map;

public class NetworkMap {

    private Map<String, Object> networks;

    public Map<String, Object> getNetworks() {
        return networks;
    }

    public void setName( Map<String, Object>  networks) {
        this.networks = networks;
    }

    public static List<NetworkConfig> getNetworksList(NetworkMap networkMap){

        HashMap<String, Object> networksMap = (HashMap<String, Object>) networkMap.getNetworks();
        LinkedList<NetworkConfig> networkList = new LinkedList<NetworkConfig>();
        
        String[] parsedArray;
        for( String key : networksMap.keySet() ){

            String parsedNetworkConfig = new String();
            parsedNetworkConfig = networksMap.get(key).toString();
            parsedNetworkConfig = parsedNetworkConfig.replace("{", "");
            parsedNetworkConfig = parsedNetworkConfig.replace("}", "");
            parsedNetworkConfig = parsedNetworkConfig.replace(", ", "=");

            parsedArray = parsedNetworkConfig.split("=");

            NetworkConfig network = new NetworkConfig( parsedArray[1], 
                                                        Integer.parseInt(parsedArray[3]), 
                                                        Integer.parseInt(parsedArray[5]), 
                                                        parsedArray[7],
                                                        parsedArray[9],
                                                        Boolean.parseBoolean(parsedArray[11]) );


            if(network.isEnabled()) // Doesn't add network to the list if the network is not enabled in the configuration file
                 networkList.add(network);
            
        }
        
        return networkList;
    }
    
    
}