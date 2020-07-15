package ufba.ofdm.heuristic;

import java.util.LinkedList;
import java.util.List;

import ufba.ofdm.config.HeuristicConfig;
import ufba.ofdm.graph.Edge;
import ufba.ofdm.graph.util.Path;
import ufba.ofdm.network.FiberLink;
import ufba.ofdm.network.Traffic;
import ufba.ofdm.network.TransparentNetwork;
import ufba.ofdm.network.FiberLink.SubCarrier;

public class FirstFit implements TrafficFitter{

    private int GuardBandNumber;

    public FirstFit(HeuristicConfig hConfig)
    {
        setGBNumber(hConfig.getGuardBandNumber());
    }


    public TransparentNetwork fit( int orig, int dest, int load, TransparentNetwork network, Path path ){

        int first = findFirstAllocatableSlotIndex(path.getEdges(), network.getNetLinks(), load );

        if(first >= network.getNetLinks().get(0).getCarrierList().size()) return network;

        List<Edge> edgeList = path.getEdges();
        List<FiberLink> linkList = network.getNetLinks();
        // For each edge inside inside this path...
        for( int e = 0; e < edgeList.size(); e++){

            // Searching for the link, inside the network, correponding to this edge
            for (int l = 0; l < linkList.size(); l++){

                FiberLink link = linkList.get(l);
                // If the link "l" is corresponds to path "p"...
                if( link.getFromNode().equals( edgeList.get(e).getFromNode() )  &&  link.getToNode().equals( edgeList.get(e).getToNode() ) ){

                    List<SubCarrier> carrierList = link.getCarrierList();

                    if( first == 0 ) // is s1 such that the load fits in the last slots?
                        for( int s1 = first; s1 < first + load && !carrierList.get(s1).isUsed(); s1++ )
                            carrierList.get(s1).setUsed(true);

                    else{
                        for( int s1 = first; s1 < first + getGBNumber() && !carrierList.get(s1).isUsed(); s1++ ){
                            carrierList.get(s1).setUsed(true);
                            carrierList.get(s1).setBandGuard(true);
                        }
                            
                        for( int s1 = first + getGBNumber(); s1 < first + load + getGBNumber() && !carrierList.get(s1).isUsed(); s1++ )
                            carrierList.get(s1).setUsed(true);
                    }

                    break; // Found the link and can exit this for-loop
                }

                network.getNetLinks().set(l,link);
            }
        }

        network.setNetLinks(linkList);
        return network;
    }
    

    public int findFirstAllocatableSlotIndex( List<Edge> edgeList, List<FiberLink> linkList, int load )    {

        List<FiberLink> fiberPath = new LinkedList<FiberLink>();

        int first = linkList.get(0).getCarrierList().size(); // Unallocatable
        

        for( int e = 0; e < edgeList.size(); e++){
            Edge edge = edgeList.get(e);
            // Searching for the link, inside the network, correponding to this edge
            for (int l = 0; l < linkList.size(); l++){
                FiberLink link = linkList.get(l);
                    // If the link "l" doesn't corresponds to path "p"...
                    if( link.getFromNode().equals( edge.getFromNode() ) && link.getToNode().equals( edge.getToNode() ) )
                        fiberPath.add( link );
            }
        }

        int necessarySpace;
        for (int s1 = 0; s1 < fiberPath.get(0).getCarrierList().size(); s1++){

            if(s1==0) necessarySpace = load;
            else necessarySpace = load + getGBNumber();

            int s2;
            for( s2 = s1; s2 < fiberPath.get(0).getCarrierList().size() && !fiberPath.get(0).getCarrierList().get(s2).isUsed() && s2-s1 < necessarySpace; s2++);
            s2--;// Volta um subcarrier

            if (s2 - s1 + 1 >= necessarySpace)
                for( int l = 0; l < fiberPath.size() && areTheseCarriersFree(fiberPath.get(l).getCarrierList() , s1, s2) ; l++)
                    if( l == fiberPath.size()-1 )
                        return s1;

        }

        return first;

    }

    public boolean areTheseCarriersFree(List<SubCarrier> subCarrierList, int s1, int s2){

        boolean free = true;

        for(int s = s1; s<=s2; s++)
            if( subCarrierList.get(s).isUsed() )
                return false;

        return free;

    }


	public int getGBNumber() {
		return GuardBandNumber;
	}

	public void setGBNumber(int guardBandNumber) {
		GuardBandNumber = guardBandNumber;
	}
    
}