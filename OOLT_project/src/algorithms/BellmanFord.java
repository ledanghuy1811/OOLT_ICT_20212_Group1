package algorithms;

import graph.*;
import graphics.NodeFX;
import javafx.animation.SequentialTransition;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import step.StepInfo;

import java.util.List;
import java.util.ArrayList;

public class BellmanFord extends Algorithm {
	//constructor
	public BellmanFord() {
		super();
	}
	public BellmanFord(Graph graph, int numVertex, Vertex source) {
		super(graph, numVertex, source);
	}

    public BellmanFord(Graph graph, int numVertex, Vertex source, Label distances, Group canvasGroup, List<NodeFX> circles, Group textFlow, List<Label> textFlowLabels) {
        super(graph, numVertex, source);
        this.stepLabel = distances;
        this.canvasGroup = canvasGroup;
        this.circles = circles;
        this.textFlow = textFlow;
        this.textFlowLabels = textFlowLabels;
    }

    // override
	@Override
	public void execute(SequentialTransition st) {
        //st = new SequentialTransition();
        ArrayList<String> pseudoContent = new ArrayList<String>();
        pseudoContent.add("initSSSP");
        pseudoContent.add("for i = 1 to |V|-1");
        pseudoContent.add("for each edge(u, v) in E // in Edge List order");
        pseudoContent.add("relax(u, v, w(u, v))");
        pseudoContent.add("for each edge(u, v) in E");
        pseudoContent.add("if can still relax that edge, -∞ cycle found");
        this.getStep().getPseudoStep().setPseudo(pseudoContent, this.textFlow, this.textFlowLabels);
        Vertex s = this.getSource();
		//Initialize distances from src to all other
        // vertices as INFINITE
		for (int i = 0; i < this.getNumVertex(); ++i)
            this.setOneDist(i, Double.MAX_VALUE);
		this.setOneDist(this.getSource().getId(), 0);
		
		//Relax all edges |V| - 1 times. A simple
        // shortest path from src to any other vertex can
        // have at-most |V| - 1 edges
        this.getStep().getPseudoStep().setIndex(0, this.textFlowLabels, st);
        for (int i = 1; i < this.getNumVertex(); ++i) {
            this.getStep().getPseudoStep().setIndex(1,  this.textFlowLabels, st);
            for (int j = 0; j < this.getNumVertex(); ++j) {
            	this.getStep().addStep(j, j, 0, "Verify node " + j, this.stepLabel, this.canvasGroup, this.circles, st, false, this.textFlow);
                for(Edge e : this.getGraph().getEdge()[j]) {
                    this.getStep().getPseudoStep().setIndex(2,  this.textFlowLabels, st);
                	int u = e.getNodeSource().getId();
                    int v = e.getNodeTarget().getId();
                    System.out.printf("%d - %d\n", u ,v);
                    double weight = e.getWeight();
                    this.getStep().addStep(u, v, weight, "Check node " + u + " and " + v + " with weight: " + weight,  this.stepLabel, this.canvasGroup, this.circles, st, false, this.textFlow);
                    this.getStep().getPseudoStep().setIndex(3,  this.textFlowLabels, st);
                    if (
                    	this.getDist()[u] != Double.MAX_VALUE && 
                    	this.getDist()[u] + weight < this.getDist()[v]
                    ) {
                        this.setOneDist(v, this.getDist()[u] + weight);
                        this.getStep().addStep(u, v, this.getDist()[u] + weight, "Settle node " + u + " and " + v + " with weight: " + weight,  this.stepLabel, this.canvasGroup, this.circles, st, true, this.textFlow);
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
                this.getStep().getPseudoStep().setIndex(4,  this.textFlowLabels, st);
            	int u = e.getNodeSource().getId();
                int v = e.getNodeTarget().getId();
                double weight = e.getWeight();
                if (
                    this.getDist()[u] != Double.MAX_VALUE && 
                    this.getDist()[u] + weight < this.getDist()[v]
                ) {
                    this.getStep().getPseudoStep().setIndex(5,  this.textFlowLabels, st);
                    System.out.println("Graph contains negative weight cycle");
                    this.getStep().addStep(u, v, weight, "Graph contains negative weight cycle", this.stepLabel, this.canvasGroup, this.circles, st, false, this.textFlow);
                    return;
                }
            }
        }
        st.onFinishedProperty();
        st.play();
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