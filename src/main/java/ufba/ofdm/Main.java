package ufba.ofdm;

import java.util.LinkedList;
import java.util.List;

import ufba.ofdm.config.ConfigManager;
import ufba.ofdm.config.FiberLinkConfig;
import ufba.ofdm.config.HeuristicConfig;
import ufba.ofdm.config.ModulationConfig;
import ufba.ofdm.config.NetworkConfig;
import ufba.ofdm.config.TrafficConfig;
import ufba.ofdm.graph.ksp.KSPAlgorithm;
import ufba.ofdm.graph.ksp.Yen;
import ufba.ofdm.graph.util.Path;
import ufba.ofdm.heuristic.FirstFit;
import ufba.ofdm.heuristic.GlobalBMLM;
import ufba.ofdm.heuristic.IncrementalBMLM;
import ufba.ofdm.heuristic.TrafficSorter;
import ufba.ofdm.network.IncrementalTraffic;
import ufba.ofdm.network.TransparentNetwork;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;    //Necessary for ReflectionToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle;                //Necessary for ReflectionToStringBuilder

public class Main {

    private static ConfigManager cfgManager;
    private static List<NetworkConfig> netConfigList;
    private static List<ModulationConfig> modConfigList;
    private static FiberLinkConfig linkConfig;
    private static HeuristicConfig heurConfig;
    private static TrafficConfig trafConfig;

    private static LinkedList<TransparentNetwork> netList;
    private static TrafficSorter sorter;
    private static FirstFit fFit;
    private static List<IncrementalTraffic> incTrafficList;
    private static Yen yen;
    private static GlobalBMLM bmlm;
    private static IncrementalBMLM incBMLM;

    public static void main(String[] args) {

        // Initiates the configuration instances
        initConfig();

        // Instantiate the list of networks
        initNetwoks( trafConfig, heurConfig );

        // Instatiate Logger
        initLogger( cfgManager, netConfigList );

        // Instantiate incremental traffic matrices
        incTrafficList = getIncrementalTrafficList( trafConfig, netConfigList );

        netList = allocateAllNetworks( netList, incTrafficList, modConfigList , yen , sorter, fFit );

        //List<Path> pathList = main.getYen().ksp(main.getNetworkList().get(1), "1", "2", 3);
        //System.out.println(ReflectionToStringBuilder.toString( pathList.get(1), ToStringStyle.MULTI_LINE_STYLE));
        
    }

    private static void initConfig() {                              // Initiate the configuration instances
        
        cfgManager = new ConfigManager();
        netConfigList = cfgManager.getNetConfigList();
        modConfigList = cfgManager.getModulationList();
        linkConfig = cfgManager.getLinkConfig();
        heurConfig = cfgManager.getHeurConfig();
        trafConfig = cfgManager.getTrafficConfig();
        
    }

    private static LinkedList<TransparentNetwork> getNetworkList(){

        LinkedList<TransparentNetwork> netList = new LinkedList<TransparentNetwork>();
        for (int net = 0; net < netConfigList.size() ; net++ )
            if( netConfigList.get(net).isEnabled() )
                netList.add(  new TransparentNetwork(netConfigList.get(net), linkConfig)  );
                

        return netList;
    }

    private static LinkedList<IncrementalTraffic> getIncrementalTrafficList( TrafficConfig trafConfig, List<NetworkConfig> netConfigList ){

        LinkedList<IncrementalTraffic> incTrafficList = new LinkedList<IncrementalTraffic>();
        for (int net = 0; net < netConfigList.size() ; net++ )
            incTrafficList.add(  new IncrementalTraffic( trafConfig, netConfigList.get(net) )  );

        return incTrafficList;
    }

    private static LinkedList<TransparentNetwork> allocateAllNetworks( LinkedList<TransparentNetwork> netList, List<IncrementalTraffic> incTrafficList,
                                                               List<ModulationConfig> modulationList, KSPAlgorithm ksp, TrafficSorter sorter,
                                                               FirstFit fFit ){

        for(int net=0; net < netConfigList.size(); net++ )
            netList.set(net, incBMLM.allocate(netList.get(net), incTrafficList.get(net), modulationList, ksp, sorter, fFit) );

        return netList;

    }

    private static void initNetwoks( TrafficConfig trafficConfig, HeuristicConfig hConfig){
        netList = getNetworkList();
        sorter = new TrafficSorter();
        fFit = new FirstFit(hConfig);
        yen =  new Yen();
        bmlm = new GlobalBMLM(hConfig);
        incBMLM = new IncrementalBMLM(hConfig);
    }

    private static void initLogger( ConfigManager cfgManager, List<NetworkConfig> netConfigList ){

        LinkedList<Integer> childrenAmountList = new LinkedList<Integer>(); // The list of integers to instantiate each Embeddable logger

        for (int i = 0; i < netConfigList.size() ; i++ ){

            childrenAmountList.add(  cfgManager.getTrafficConfig().getNumSteps()  ); // Number of incremental step
            childrenAmountList.add( (int) Math.pow(netConfigList.get(i).getNodesNumber(), 2) ) ; // Number of traffic demands
            childrenAmountList.add(  cfgManager.getHeurConfig().getRoutesNumber()  ); // Number of routes
            childrenAmountList.add(  cfgManager.getHeurConfig().getHopsNumber()  ); // Maximum number of links in the path
            
            childrenAmountList.clear();

        }

    }


}