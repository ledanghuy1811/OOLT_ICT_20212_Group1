package algorithms;

import java.util.List;

import graph.*;
import graphics.NodeFX;
import javafx.animation.SequentialTransition;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import step.*;
import javafx.scene.Group;

public abstract class Algorithm {
	//FX attributes
	Label stepLabel;
	Group canvasGroup;
	List<NodeFX> circles;
	List<Label> textFlowLabels;
	Group textFlow;
	//attributes

	private Graph graph;
	private int numVertex;
	private Vertex source;
	private double dist[];
	private Step step = new Step();
	
	//constructor
	public Algorithm() {
		
	}
	public Algorithm(Graph graph, int numVertex, Vertex source){
		this.graph = graph;
		this.numVertex = numVertex;
		this.source = source;
		this.dist = new double[numVertex];
	}

	public Algorithm(Graph graph, int numVertex, Vertex source, Label stepLabel, Group canvasGroup, List<NodeFX> circles, Group textFlow, List<Label> textFlowLabels) {
		this.graph = graph;
		this.numVertex = numVertex;
		this.source = source;
		this.stepLabel = stepLabel;
		this.canvasGroup = canvasGroup;
		this.textFlow = textFlow;
		this.circles = circles;
		this.dist = new double[numVertex];
		this.textFlowLabels = textFlowLabels;
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
	public abstract void execute(SequentialTransition st);
	public abstract void play();
}
