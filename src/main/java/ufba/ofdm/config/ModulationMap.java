package ufba.ofdm.config;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import java.util.Map;

public class ModulationMap {

    private Map<String, Object> modulations;

    public Map<String, Object> getModulations() {
        return modulations;
    }

    public void setName( Map<String, Object>  modulations) {
        this.modulations = modulations;
    }


    public static List<ModulationConfig> getModulationList( ModulationMap modulationMap){

        HashMap<String, Object> modulationsMap = (HashMap<String, Object>) modulationMap.getModulations();
        LinkedList<ModulationConfig> modulationList = new LinkedList<ModulationConfig>();
        
        String[] parsedArray;
        for( String key : modulationsMap.keySet() ){

            String parsedModulationConfig = new String();
            parsedModulationConfig = modulationsMap.get(key).toString();
            parsedModulationConfig = parsedModulationConfig.replace("{", "");
            parsedModulationConfig = parsedModulationConfig.replace("}", "");
            parsedModulationConfig = parsedModulationConfig.replace(", ", "=");

            parsedArray = parsedModulationConfig.split("=");

            ModulationConfig modulation = new ModulationConfig( Float.parseFloat( parsedArray[1] ), 
                                                                Integer.parseInt( parsedArray[3] ), 
                                                                Integer.parseInt( parsedArray[5] ), 
                                                                Integer.parseInt( parsedArray[7] ),
                                                                Float.parseFloat( parsedArray[9] ),
                                                                Boolean.parseBoolean( parsedArray[11] ) );

            modulationList.add(modulation);

        }
        
        return modulationList;
    }
    

}