package ufba.ofdm.network;

import java.util.LinkedList;
import ufba.ofdm.graph.Node;
import ufba.ofdm.graph.Edge;

public class WSS extends Node { //Wavelength Selective Switch (WSS)

    // Wavelength Selective Switches contains a ROADM. (A ROADM is an Optical Cross-Connect)

    public WSS() {
        super();
    }

    public WSS(Node node) {
        super( node.getLabel() );
    }

    public LinkedList<FiberLink> getLinks() {

        final LinkedList<FiberLink> links = new LinkedList<FiberLink>();
        final LinkedList<Edge> edges = super.getEdges();

        for (int i = 0; i < edges.size(); i++)
            links.add( new FiberLink() );

        return links;
    }

}