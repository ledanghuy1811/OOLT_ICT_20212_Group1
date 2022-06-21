package algorithms;

import graph.*;

public abstract class Algorithms {
	//attributes
	private Graph graph = new Graph();
	private int numVertex;
	private int numEdge;
	
	//constructor
	public Algorithms() {
		
	}
	public Algorithms(Graph graph, int numVertex, int numEdge) {
		this.graph = graph;
		this.numVertex = numVertex;
		this.numEdge = numEdge;
	}
	
	//getter and setter
	public int getNumVertex() {
		return numVertex;
	}
	public int getNumEdge() {
		return numEdge;
	}
	public Graph getGraph() {
		return graph;
	}
	
	public void setGraph(Graph graph) {
		this.graph = graph;
	}
	//to override
	public abstract void excute();
}
