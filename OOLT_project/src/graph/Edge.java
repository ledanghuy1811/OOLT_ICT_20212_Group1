package graph;

public class Edge {
	//attribute
	private int nodeSource;
	private int nodeTarget;
	private double weight;
	
	//getter
	public int getNodeSource() {
		return nodeSource;
	}
	public int getNodeTarget() {
		return nodeTarget;
	}
	public double getWeight() {
		return weight;
	}
	
	//constructor
	public Edge() {
		
	}
	public Edge(int source, int target) {
		this.nodeSource = source;
		this.nodeTarget = target;
		this.weight = 0;
	}
	public Edge(int source, int target, double weight) {
		this.nodeSource = source;
		this.nodeTarget = target;
		this.weight = weight;
	}
}