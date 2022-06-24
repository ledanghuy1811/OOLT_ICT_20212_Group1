package graph;

import java.util.Comparator;

public class Edge implements Comparator<Edge> {
	//attribute
	private Vertex nodeSource;
	private Vertex nodeTarget;
	private double weight;
	
	//getter
	public Vertex getNodeSource() {
		return nodeSource;
	}
	public Vertex getNodeTarget() {
		return nodeTarget;
	}
	public double getWeight() {
		return weight;
	}
	
	//constructor
	public Edge() {
		
	}
	public Edge(int source, int target) {
		this.nodeSource = new Vertex(source);
		this.nodeTarget = new Vertex(target);
		this.weight = 0;
	}
	public Edge(int source, int target, double weight) {
		this.nodeSource = new Vertex(source);
		this.nodeTarget = new Vertex(target);
		this.weight = weight;
	}
	
	//override
	@Override
	public int compare(Edge e1, Edge e2)
	{
		if (e1.getWeight() < e2.getWeight())
			return -1;

		if (e1.getWeight() > e2.getWeight())
			return 1;

		return 0;
	}
}
