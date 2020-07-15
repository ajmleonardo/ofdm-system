package ufba.ofdm.heuristic;

import java.util.List;

import ufba.ofdm.heuristic.IncrementalBMLM.EnvelopedPath;
import ufba.ofdm.network.FiberLink;
import ufba.ofdm.network.Traffic;
import ufba.ofdm.network.FiberLink.SubCarrier;

public class AllocationLogger {

    public void printAllocation( int timeStep, Traffic traffic, List<EnvelopedPath> ePathList, EnvelopedPath chosenPath, List<FiberLink> linkList ){

        // Logging Information
        System.out.println( logTimeStep(timeStep) );
        System.out.print("\t");
        System.out.println( logTraffic(traffic) );

        System.out.println( logPaths(ePathList) );

        System.out.print("Chosen -> ");
        System.out.println( logPath(chosenPath) );
        System.out.println( logLinks(linkList) );
        System.out.println("");

    }

    public void writeAllocation( int timeStep, Traffic traffic, List<EnvelopedPath> ePathList, EnvelopedPath chosenPath, List<FiberLink> linkList ){

        // Logging Information
        System.out.println( logTimeStep(timeStep) );
        System.out.print("\t");
        System.out.println( logTraffic(traffic) );

        System.out.println( logPaths(ePathList) );

        System.out.print("Chosen -> ");
        System.out.println( logPath(chosenPath) );
        System.out.println( logLinks(linkList) );
        System.out.println("");

    }


    public String logTimeStep( int timeStep ){

        String tsLog = new String();
        tsLog = "TimeStep: " + (timeStep+1) + " : ";

        return tsLog;
    }

    public String logTraffic( Traffic traffic ){

        String tfLog = new String();
        tfLog = "Demand " + (traffic.getOrigin()+1) + "-" + (traffic.getDestination()+1) + " : "  + traffic.getDemand() + " Gbps";

        return tfLog;
    
    }
    
    public String logPaths( List<EnvelopedPath> ePathList ){
    
        String pathLog = new String();
        for( int p = 0; p < ePathList.size(); p++ ){
            pathLog = pathLog + "\t\t"; // Identation
            pathLog = pathLog + logPath(ePathList.get(p));
        }
    
        return pathLog;
    }
    
    public String logPath( EnvelopedPath ePath ){
    
        String logPath = new String();

        logPath = "Path option -> Distance: " + ePath.getPath().size() + " | Load: " + ePath.gettLoad() + " | Min-A-S: " + 
                  (ePath.gettMinAllocatableSlot()+1) + " : " + "Path: " + ePath.toString() + "\n";
    
        return logPath;
      
    }
    
    public String logLinks( List<FiberLink> linkList ){
    
        String linksLog = new String();
        for(int l = 0; l < linkList.size(); l++){
            linksLog = linksLog + "Link " + linkList.get(l).getFromNode() + "-" + linkList.get(l).getToNode() + ": " ;
            linksLog = linksLog + logCarriers(linkList.get(l).getCarrierList()) + "\n";
        }

        return linksLog;
    
    }
    
    public String logCarriers( List<SubCarrier> sCarrierList ){
    
    
        String subCarriers = new String();
        subCarriers = "[ ";
        for(int c = 0; c < sCarrierList.size(); c++){
    
            if(sCarrierList.get(c).isBandGuard())
            subCarriers = subCarriers + "g";
            else if(sCarrierList.get(c).isUsed())
                subCarriers = subCarriers + "o";
            else
            subCarriers = subCarriers + " ";
    
            if(c!= sCarrierList.size()-1)
                subCarriers = subCarriers + " | ";
        }
    
        subCarriers = subCarriers + " ]";
    
        return subCarriers;
    
    }
    
}