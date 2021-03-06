package graph;

import java.util.*;

public class Graph {
	public static final int MAX_NUMBERS_EDGE = 1000;
	
	// attribute
	private ArrayList<Vertex> vertex = new ArrayList<Vertex>();
	private ArrayList<Edge>[] edge = new ArrayList[MAX_NUMBERS_EDGE];
	private boolean isDirected;
	private boolean isWeight;
	
	//constructor
	public Graph() {
		
	}
	public Graph(boolean isDirected, boolean isWeighted) {
		this.isDirected = isDirected;
		this.isWeight = isWeighted;
		for(int i = 0; i < MAX_NUMBERS_EDGE; i++)
			this.edge[i] = new ArrayList<Edge>();
	}
	
	//getter and setter
	public ArrayList<Vertex> getVertex() {
		return this.vertex;
	}
	public ArrayList<Edge>[] getEdge() {
		return this.edge;
	}
	public boolean getIsDirected() {
		return this.isDirected;
	}
	public boolean getIsWeight() {
		return isWeight;
	}
	
	// method
	public void addVertex(int vertex) {
		Vertex temp = new Vertex(vertex);
		this.vertex.add(temp);
	}
	public void addEdge(int source, int target, double weight) {
		Edge temp;
		if(this.getIsDirected()) {
			temp = new Edge(source, target, weight);
		}
		else {
			if(this.getIsWeight()) {
				temp = new Edge(source, target, weight);
				this.edge[target].add(new Edge(target, source, weight));
			}
			else {
				temp = new Edge(source, target, weight);
				this.edge[target].add(new Edge(target, source, weight));
			}
		}
		this.edge[source].add(temp);
	}
	public ArrayList<Vertex> getAdjacent(int id) {
		ArrayList<Vertex> temp = new ArrayList<Vertex>();
		for(Edge edge: this.getEdge()[id]) {
			temp.add(edge.getNodeTarget());
		}
		return temp;
	}
	public Vertex getOneVertex(int id) {
		for(Vertex v : this.getVertex()) 
			if(id == v.getId())
				return v;
		return null;
	}
	public Edge getOneEdge(int source, int target) {
		for(Edge e : this.getEdge()[source])
			if(target == e.getNodeTarget().getId())
				return e;
		return null;
	}
}
