package ufba.ofdm.network;

import java.util.LinkedList;
import java.util.List;

import ufba.ofdm.config.FiberLinkConfig;
import ufba.ofdm.config.NetworkConfig;
import ufba.ofdm.graph.Graph;

public class TransparentNetwork extends Graph{

    private String name;
    private List<WSS> netOXCs;
    private List<FiberLink> netLinks;
    private List<LightPath> netLightPaths;
    private int maxLoad;
    private int maxSlotUsed;

    public TransparentNetwork(NetworkConfig netConfig, FiberLinkConfig linkConfig) {

        // Calling the superclass's constructor
        super( netConfig.getFileEdgeDescriptionLocation() + "edge_description.txt" );
        

        // Initiating member parameters
        setName(netConfig.getName());
        this.netOXCs = new LinkedList<WSS>();
        this.netLinks = new LinkedList<FiberLink>();
        
        // Setting up the list of OXCs
        LinkedList<WSS> oxcList = new LinkedList<WSS>();

        for( String label : this.getNodeLabels())
            oxcList.add(  new WSS(this.getNode(label))  ); 

        // Setting up the list of fiber links
        LinkedList<FiberLink> linkList = new LinkedList<FiberLink>();

        for(int linkIndex = 1; linkIndex <= linkConfig.getFiberNumber(); linkIndex++)
            for( int linkID = 0; linkID < this.numEdges(); linkID++)
                linkList.add( new FiberLink( this.getEdgeList().get(linkID), linkConfig, linkID+1 )  );


        // Passing the setted up lists to the member parameters
        this.netOXCs = oxcList;
        this.netLinks = linkList;

    }

    public void setMetrics(){

        int maxSlotUsed = 0;
        int maxLoad = 0;

        for (int l = 0; l < getNetLinks().size(); l++)
            for(int c = 0; c < getNetLinks().get(l).getCarrierList().size(); c++)
                if( getNetLinks().get(l).getCarrierList().get(c).isUsed() && c > maxSlotUsed )
                    maxSlotUsed = c;
    
        setMaxSlotUsed(maxSlotUsed);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<WSS> getNetOXCs() {
        return netOXCs;
    }

    public void setNetOXCs(List<WSS> netOXCs) {
        this.netOXCs = netOXCs;
    }

    public List<FiberLink> getNetLinks() {
        return netLinks;
    }

    public void setNetLinks(List<FiberLink> netLinks) {
        this.netLinks = netLinks;
    }

    public List<LightPath> getNetLightPaths() {
        return netLightPaths;
    }

    public void setNetLightPaths(List<LightPath> netLightPaths) {
        this.netLightPaths = netLightPaths;
    }

    public int getMaxLoad() {
        return maxLoad;
    }

    public void setMaxLoad(int maxLoad) {
        this.maxLoad = maxLoad;
    }

    public int getMaxSlotUsed() {
        return maxSlotUsed;
    }

    public void setMaxSlotUsed(int maxSlotUsed) {
        this.maxSlotUsed = maxSlotUsed;
    }



    
}