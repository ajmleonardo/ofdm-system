package ufba.ofdm.heuristic;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.List;

import ufba.ofdm.heuristic.IncrementalBMLM.EnvelopedPath;
import ufba.ofdm.network.FiberLink;
import ufba.ofdm.network.Traffic;
import ufba.ofdm.network.TransparentNetwork;
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

    public void writeAllocation( int timeStep, Traffic traffic, List<EnvelopedPath> ePathList, EnvelopedPath chosenPath, List<FiberLink> linkList, String netName ){

        // Creating directory first (necessary)
        File file = new File( "src" + File.separatorChar +  "resources" + File.separatorChar + "output" + File.separatorChar +  netName);

        file.mkdirs();

        // Creating File
        file = new File( "src" + File.separatorChar +  "resources" + File.separatorChar + "output" + File.separatorChar + 
        netName + File.separatorChar + "step-by-step.txt");
        
        try {
            if(file.exists() && timeStep == 0)
                file.delete();
            file.createNewFile();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        try{


            BufferedWriter output =  new BufferedWriter( new FileWriter(file, true) );
            // Logging Information
            output.append( logTimeStep(timeStep) );
            output.append("\t");
            output.append( logTraffic(traffic) );
            output.append( logPaths(ePathList) );
            output.append("Chosen -> ");
            output.append( logPath(chosenPath) );
            output.append( logLinks(linkList) );
            output.newLine();
            
            output.flush();
            output.close();

        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

    public void writeMaxSlotUsed(int timeStep, TransparentNetwork network){

        System.out.println("\n\n" + network.getName() + " network -" + " time-Step: " + (timeStep+1) + "\n" + "Maxslot used: " + (network.getMaxSlotUsed()+1) );

         // Creating directory first (necessary)
         File file = new File( "src" + File.separatorChar +  "resources" + File.separatorChar + "output" + File.separatorChar +  network.getName());

        file.mkdirs();
         // Creating File
         file = new File( "src" + File.separatorChar +  "resources" + File.separatorChar + "output" + File.separatorChar + 
         network.getName() + File.separatorChar + "max_slots_used.txt");
         
         try {
            if(file.exists() && timeStep == 0)
                file.delete();
             file.createNewFile();
         } catch (IOException e1) {
             // TODO Auto-generated catch block
             e1.printStackTrace();
         }

         try{

            BufferedWriter output =  new BufferedWriter( new FileWriter(file, true) );
            // Logging Information
            output.append(network.getName() + " network -" + " time-Step: " + (timeStep+1) + "\n" + "Maxslot used: " + (network.getMaxSlotUsed()+1) +"\n" );
            output.newLine();
            output.flush();
            output.close();

        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



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
            linksLog = linksLog + "Link " + linkList.get(l).getFromNode() + "-" + linkList.get(l).getToNode() + ":\t" ;
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