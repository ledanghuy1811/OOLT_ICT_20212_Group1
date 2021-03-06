package algorithms;

import java.util.Scanner;

import graph.*;
import step.*;

public abstract class Algorithm {
	//attributes
	private Graph graph;
	private int numVertex;
	private Vertex source;
	private double dist[];
	private Step step = new Step();
	
	//constructor
	public Algorithm() {
		
	}
	public Algorithm(Graph graph, int numVertex, Vertex source) {
		this.graph = graph;
		this.numVertex = numVertex;
		this.source = source;
		this.dist = new double[numVertex];
	}
	
	//getter and setter
	public int getNumVertex() {
		return numVertex;
	}
	public Graph getGraph() {
		return graph;
	}
	public Vertex getSource() {
		return source;
	}
    public double[] getDist() {
		return dist;
	}
    public void setOneDist(int index, double value) {
		this.dist[index] = value;
	}    
    public Step getStep() {
    	return step;
    }
	
    
	//to override
	public abstract void execute();
	public abstract void play();
}
