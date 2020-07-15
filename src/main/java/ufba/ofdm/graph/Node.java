//package edu.ufl.cise.bsmock.graph;
package ufba.ofdm.graph;

/**
 * The Node class implements a node in a directed graph keyed on a label of type String, with adjacency lists for
 * representing edges.
 *
 * Created by brandonsmock on 5/31/15.
 */

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

public class Node {
    protected String label;
    protected HashMap<String,Double> neighbors; // adjacency list, with HashMap for each edge weight

    public Node() {
        this.neighbors = new HashMap<String, Double>();
    }

    public Node(final String label) {
        this.label = label;
        neighbors = new HashMap<String, Double>();
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(final String label) {
        this.label = label;
    }

    public HashMap<String, Double> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(final HashMap<String, Double> neighbors) {
        this.neighbors = neighbors;
    }

    public void addEdge(final String toNodeLabel,final Double weight) {
        neighbors.put(toNodeLabel, weight);
    }

    public double removeEdge(final String toNodeLabel) {
        if (neighbors.containsKey(toNodeLabel)) {
            final double weight = neighbors.get(toNodeLabel);
            neighbors.remove(toNodeLabel);
            return weight;
        }

        return Double.MAX_VALUE;
    }

    public Set<String> getAdjacencyList() {
        return neighbors.keySet();
    }

    public LinkedList<Edge> getEdges() {
        final LinkedList<Edge> edges = new LinkedList<Edge>();
        for (final String toNodeLabel : neighbors.keySet()) {
            edges.add(new Edge(label,toNodeLabel,neighbors.get(toNodeLabel)));
        }

        return edges;
    }
    
    public String toString() {
        final StringBuilder nodeStringB = new StringBuilder();
        nodeStringB.append(label);
        nodeStringB.append(": {");
        final Set<String> adjacencyList = this.getAdjacencyList();
        final Iterator<String> alIt = adjacencyList.iterator();
        final HashMap<String, Double> neighbors = this.getNeighbors();
        while (alIt.hasNext()) {
            final String neighborLabel = alIt.next();
            nodeStringB.append(neighborLabel.toString());
            nodeStringB.append(": ");
            nodeStringB.append(neighbors.get(neighborLabel));
            if (alIt.hasNext())
                nodeStringB.append(", ");
        }
        nodeStringB.append("}");
        nodeStringB.append("\n");

        return nodeStringB.toString();
    }
}
