package ufba.ofdm.heuristic;

import java.util.LinkedList;
import java.util.List;

import ufba.ofdm.config.HeuristicConfig;
import ufba.ofdm.config.ModulationConfig;
import ufba.ofdm.graph.Edge;
import ufba.ofdm.graph.ksp.KSPAlgorithm;
import ufba.ofdm.graph.util.Path;
import ufba.ofdm.network.FiberLink;
import ufba.ofdm.network.IncrementalTraffic;
import ufba.ofdm.network.LightPath;
import ufba.ofdm.network.Traffic;
import ufba.ofdm.network.TrafficMatrix;
import ufba.ofdm.network.TransparentNetwork;
import ufba.ofdm.network.FiberLink.SubCarrier;

import org.w3c.dom.events.EventException;

public class IncrementalBMLM {

    private int hopsNumber;
    private int routesNumber;
    private int guardBandNumber;
    private boolean sortingEnabled;
    private boolean printingEnabled;
    private boolean writingEnabled;

    private AllocationLogger logger;

    public IncrementalBMLM(HeuristicConfig hConfig){

        setGuardBandNumber(hConfig.getGuardBandNumber());
        setRoutesNumber(hConfig.getRoutesNumber());
        setHopsNumber(hConfig.getHopsNumber());
        setSortingEnabled(hConfig.isSortingEnabled());
        setPrintingEnabled(hConfig.isPrintingEnabled());
        setWritingEnabled(hConfig.isWritingEnabled());

        setLogger(new AllocationLogger());

    }

    public static class EnvelopedPath {

        // Enveloped path is a possible path for a traffic. All of the attriutes of this classes exist within the context of that path

        private int tOrig; // Origin of the traffic for which this path is intended
        private int tDest; // Destination of the traffic for which this path is intended
        private int tLoad; // The Load, in number of slots, that a traffic will have if this path is chosen
        private int tMinAllocatableSlot; // Minimum allocatable slot, in this path, for the load of a particular traffic
        private Path path; // The path itself

        public static List<EnvelopedPath> envelopPaths( Traffic traffic,  List<Path> pathList, List<ModulationConfig> modList ){

            LinkedList<EnvelopedPath> envPathList = new LinkedList<EnvelopedPath>();
            for( int p = 0; p < pathList.size(); p++ ){
                
                EnvelopedPath envPath = new EnvelopedPath();
                envPath.setPath( pathList.get(p) );
                envPath.settOrig( traffic.getOrigin() ) ;
                envPath.settDest( traffic.getDestination() );
                envPath.settLoad( calculatePathLoad(traffic, modList, envPath.getPath() ));
                envPathList.add(envPath);
    
            }
            return envPathList;
        }

        public static int calculatePathLoad( Traffic traffic, List<ModulationConfig> modList, Path path ){ // Using number of Hops

            int numberOfSlots;
            int bestModEfficiency = 1;
            float bestModBandwidth = (float) 12.5; // GHz per subcarrier
            int pathSize = path.getEdges().size();
    
            for(int m = 0; m < modList.size(); m++)
                if( pathSize <= modList.get(m).getMaxDistHops() )
                    if( bestModEfficiency < modList.get(m).getEfficiency() )
                        bestModEfficiency = modList.get(m).getEfficiency();
                        
    
            numberOfSlots = (int) Math.ceil( traffic.getDemand()/(bestModEfficiency * bestModBandwidth ) );
    
            return numberOfSlots;
    
        }

        @Override
        public String toString(){

            String pathString = new String();
    
            pathString = "[ ";
            pathString = pathString + this.path.getEdges().get(0).getFromNode();
    
            for(int e = 0; e < this.path.getEdges().size(); e++)
                pathString = pathString + " -> " + this.path.getEdges().get(e).getToNode();
            pathString = pathString + " ]";
    
            return pathString;
        }

        public int gettOrig() {
            return tOrig;
        }

        public void settOrig(int tOrig) {
            this.tOrig = tOrig;
        }

        public int gettDest() {
            return tDest;
        }

        public void settDest(int tDest) {
            this.tDest = tDest;
        }

        public int gettLoad() {
            return tLoad;
        }

        public void settLoad(int tLoad) {
            this.tLoad = tLoad;
        }

        public int gettMinAllocatableSlot() {
            return tMinAllocatableSlot;
        }

        public void settMinAllocatableSlot(int tMinAllocatableSlot) {
            this.tMinAllocatableSlot = tMinAllocatableSlot;
        }

        public Path getPath() {
            return path;
        }

        public void setPath(Path path) {
            this.path = path;
        }

    }

    public TransparentNetwork allocate( TransparentNetwork network, IncrementalTraffic incTraffic, List<ModulationConfig> modulationList,
                                        KSPAlgorithm ksp, TrafficSorter sorter, FirstFit fFit ){

        for(int t=0; t< incTraffic.getNumStep(); t++){

            // Setting a single a traffic matrix from the incremental traffic matrices
            List<Traffic> trafficList = incTraffic.getTrafficMatrixList().get(t).getTrafficList();

            TrafficMatrix trafficMatrix = new TrafficMatrix();
            trafficMatrix.setTrafficList(trafficList);

            // Sorting the traffic
            if(isSortingEnabled())
                trafficMatrix.setTrafficList( sorter.sortMaxToMin(trafficMatrix.getTrafficList()) );

            // For each traffic inside the traffic matrix...
            for(int tf = 0; tf < trafficMatrix.getTrafficList().size(); tf++ ){

                Traffic traffic = trafficMatrix.getTrafficList().get(tf);

                // Finding the list of paths for this traffic
                List<Path> pathList;
                pathList = ksp.ksp( network,
                                    Integer.toString(trafficMatrix.getTrafficList().get(tf).getOrigin()+1),
                                    Integer.toString(trafficMatrix.getTrafficList().get(tf).getDestination()+1),
                                    getRoutesNumber()
                                );

                // Enveloping the paths in order to choose between them
                List<EnvelopedPath> ePathList = EnvelopedPath.envelopPaths( traffic, pathList, modulationList );
    
                // Sets up the minimum allocatable slot for each path for this traffic...
                for(int p = 0; p < ePathList.size(); p++){
                    int first = fFit.findFirstAllocatableSlotIndex( ePathList.get(p).getPath().getEdges(), 
                    network.getNetLinks(), ePathList.get(p).gettLoad());
                    ePathList.get(p).settMinAllocatableSlot( first );
                }

                // Choosing the best path
                int bestPathIndex = choosePath(ePathList);

                // Allocating spectrum within the links
                network = fFit.fit(traffic.getOrigin(), traffic.getDestination(), ePathList.get(bestPathIndex).gettLoad(), network, ePathList.get(bestPathIndex).getPath());
                
                // Logging allocation
                if(isPrintingEnabled())
                    getLogger().printAllocation(t, traffic, ePathList, ePathList.get(choosePath(ePathList)), network.getNetLinks() );
                if(isWritingEnabled())
                    getLogger().writeAllocation(t, traffic, ePathList, ePathList.get(choosePath(ePathList)), network.getNetLinks() );
                    
                
                }

                printMaxSlotUsed(network);
        }

        return network;
    }

    public void printMaxSlotUsed(TransparentNetwork network){

        network.setMetrics();
        System.out.println("\n\n" + network.getName() + "\n" + "Maxslot used: " + (network.getMaxSlotUsed()+1) );
       
    }


    public int choosePath( List<EnvelopedPath> ePathList ){

        int chosenPathIndex = ePathList.size();
        int minScore = Integer.MAX_VALUE;
        for( int p = 0; p < ePathList.size(); p++){
            if( ePathList.get(p).gettMinAllocatableSlot() + ePathList.get(p).tLoad < minScore ){
                chosenPathIndex = p;
                minScore = ePathList.get(p).gettMinAllocatableSlot() + ePathList.get(p).tLoad;
            }
        }

        return chosenPathIndex;
    }


    public int getHopsNumber() {
        return hopsNumber;
    }

    public void setHopsNumber(int hopsNumber) {
        this.hopsNumber = hopsNumber;
    }

    public int getRoutesNumber() {
        return routesNumber;
    }

    public void setRoutesNumber(int routesNumber) {
        this.routesNumber = routesNumber;
    }

    public int getGuardBandNumber() {
        return guardBandNumber;
    }

    public void setGuardBandNumber(int guardBandNumber) {
        this.guardBandNumber = guardBandNumber;
    }

    public boolean isSortingEnabled() {
        return sortingEnabled;
    }

    public void setSortingEnabled(boolean sortingEnabled) {
        this.sortingEnabled = sortingEnabled;
    }

    public boolean isPrintingEnabled() {
        return printingEnabled;
    }

    public void setPrintingEnabled(boolean printingEnabled) {
        this.printingEnabled = printingEnabled;
    }

    public boolean isWritingEnabled() {
        return writingEnabled;
    }

    public void setWritingEnabled(boolean writingEnabled) {
        this.writingEnabled = writingEnabled;
    }

    public AllocationLogger getLogger() {
        return logger;
    }

    public void setLogger(AllocationLogger logger) {
        this.logger = logger;
    }


}