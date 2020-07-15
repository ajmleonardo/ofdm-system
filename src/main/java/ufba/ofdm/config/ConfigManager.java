package ufba.ofdm.config;

import java.io.File;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class ConfigManager {

    private HeuristicConfig heurConfig;
    private FiberLinkConfig linkConfig;
    private TrafficConfig trafficConfig;
    private List<NetworkConfig> netConfigList;
    private List<ModulationConfig> modulationList;
    

    public ConfigManager() {

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        try {

            HeuristicConfig heurConfig = mapper.readValue(new File("src" + File.separatorChar + "resources" + File.separatorChar + "config" + File.separatorChar + "heuristics.yaml"), HeuristicConfig.class);  
            FiberLinkConfig linkConfig = mapper.readValue(new File("src" + File.separatorChar + "resources" + File.separatorChar +  "config" + File.separatorChar + "fiberlinks.yaml"), FiberLinkConfig.class);
            TrafficConfig trafficConfig = mapper.readValue( new File("src" + File.separatorChar + "resources" + File.separatorChar +  "config" + File.separatorChar + "traffics.yaml"), TrafficConfig.class);
            NetworkMap networkMap = mapper.readValue(new File("src" + File.separatorChar + "resources" + File.separatorChar +  "config" + File.separatorChar + "networks.json"), NetworkMap.class);
            List<NetworkConfig> networkList = NetworkMap.getNetworksList(networkMap);
            ModulationMap modulationMap = mapper.readValue(new File("src" + File.separatorChar + "resources" + File.separatorChar +  "config" + File.separatorChar + "modulations.json"), ModulationMap.class);
            List<ModulationConfig> modulationList = ModulationMap.getModulationList(modulationMap);

            setHeurConfig(heurConfig);
            setTrafficConfig(trafficConfig);
            setLinkConfig(linkConfig);
            setNetConfigList(networkList);
            setModulationList(modulationList);

        } catch (Exception e) {

            // TODO Auto-generated catch block
            e.printStackTrace();

        }

    }

    public HeuristicConfig getHeurConfig() {
        return heurConfig;
    }

    public void setHeurConfig(HeuristicConfig heurConfig) {
        this.heurConfig = heurConfig;
    }

    public FiberLinkConfig getLinkConfig() {
        return linkConfig;
    }

    public void setLinkConfig(FiberLinkConfig linkConfig) {
        this.linkConfig = linkConfig;
    }

    public TrafficConfig getTrafficConfig() {
        return trafficConfig;
    }

    public void setTrafficConfig(TrafficConfig trafficConfig) {
        this.trafficConfig = trafficConfig;
    }

    public List<NetworkConfig> getNetConfigList() {
        return netConfigList;
    }

    public void setNetConfigList(List<NetworkConfig> netConfigList) {
        this.netConfigList = netConfigList;
    }

    public List<ModulationConfig> getModulationList() {
        return modulationList;
    }

    public void setModulationList(List<ModulationConfig> modulationList) {
        this.modulationList = modulationList;
    }

}