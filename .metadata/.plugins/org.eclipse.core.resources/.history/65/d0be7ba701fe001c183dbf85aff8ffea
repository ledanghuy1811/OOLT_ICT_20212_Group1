package algorithms;

import graph.*;
import step.StepInfo;

public class BellmanFord extends Algorithm {
	//constructor
	public BellmanFord() {
		super();
	}
	public BellmanFord(Graph graph, int numVertex, Vertex source) {
		super(graph, numVertex, source);
	}
	
	// override
	@Override
	public void execute() {
		//Initialize distances from src to all other
        // vertices as INFINITE
		for (int i = 0; i < this.getNumVertex(); ++i)
            this.setOneDist(i, Double.MAX_VALUE);
		this.setOneDist(this.getSource().getId(), 0);
		
		//Relax all edges |V| - 1 times. A simple
        // shortest path from src to any other vertex can
        // have at-most |V| - 1 edges
        for (int i = 1; i < this.getNumVertex(); ++i) {
            for (int j = 0; j < this.getNumVertex(); ++j) {
            	this.getStep().addStep(j, j, 0, "Verify node " + j);
                for(Edge e : this.getGraph().getEdge()[j]) {
                	int u = e.getNodeSource().getId();
                    int v = e.getNodeTarget().getId();
                    double weight = e.getWeight();
                    this.getStep().addStep(u, v, weight, "Check node " + u + " and " + v + " with weight: " + weight);
                    if (
                    	this.getDist()[u] != Double.MAX_VALUE && 
                    	this.getDist()[u] + weight < this.getDist()[v]
                    ) {
                        this.setOneDist(v, this.getDist()[u] + weight);
                        this.getStep().addStep(u, v, weight, "Settle node " + u + " and " + v + " with weight: " + weight);
                    }
                }
            }
        }
        //check for negative-weight cycles. The above
        // step guarantees shortest distances if graph doesn't
        // contain negative weight cycle. If we get a shorter
        // path, then there is a cycle.
        for (int j = 0; j < this.getNumVertex(); ++j) {
            for(Edge e : this.getGraph().getEdge()[j]) {
            	int u = e.getNodeSource().getId();
                int v = e.getNodeTarget().getId();
                double weight = e.getWeight();
                if (
                    this.getDist()[u] != Double.MAX_VALUE && 
                    this.getDist()[u] + weight < this.getDist()[v]
                ) {
                    System.out.println("Graph contains negative weight cycle");
                    this.getStep().addStep(u, v, weight, "Graph contains negative weight cycle");
                    return;
                }
            }
        }
	}
	@Override
	public void play() {
		System.out.println("Vertex Distance from Source");
        for (int i = 0; i < this.getNumVertex(); ++i)
            System.out.println(i + "\t\t" + this.getDist()[i]);
        System.out.println();
		for(StepInfo i : this.getStep().getStepDetail()) {
			System.out.println(i.getDetail());
		}
	}
}