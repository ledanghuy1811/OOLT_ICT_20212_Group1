package algorithms;

import graph.*;

public class BellmanFord extends Algorithms {
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
            for (int j = 0; j < this.getGraph().getEdge().length; ++j) {
                for(Edge e : this.getGraph().getEdge()[j]) {
                	int u = e.getNodeSource();
                    int v = e.getNodeTarget();
                    double weight = e.getWeight();
                    if (
                    	this.getDist()[u] != Double.MAX_VALUE && 
                    	this.getDist()[u] + weight < this.getDist()[v]
                    )
                        this.setOneDist(v, this.getDist()[u] + weight);
                }
            }
        }
        //check for negative-weight cycles. The above
        // step guarantees shortest distances if graph doesn't
        // contain negative weight cycle. If we get a shorter
        // path, then there is a cycle.
        for (int j = 0; j < this.getGraph().getEdge().length; ++j) {
            for(Edge e : this.getGraph().getEdge()[j]) {
            	int u = e.getNodeSource();
                int v = e.getNodeTarget();
                double weight = e.getWeight();
                if (
                    this.getDist()[u] != Double.MAX_VALUE && 
                    this.getDist()[u] + weight < this.getDist()[v]
                ) {
                    System.out.println("Graph contains negative weight cycle");
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
	}
}