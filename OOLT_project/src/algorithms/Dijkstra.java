package algorithms;

import java.util.*;

import graph.*;
import graphics.NodeFX;
import javafx.animation.SequentialTransition;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import step.StepInfo;

public class Dijkstra extends Algorithm {
	// Member variables of this class
    private Set<Vertex> settled;
    private PriorityQueue<Edge> pq;
	
    //constructor
    public Dijkstra() {
    	super();
    }
	public Dijkstra(Graph graph, int numVertex, Vertex source) {
    	super(graph, numVertex, source);
    	this.settled = new HashSet<Vertex>();
    	this.pq = new PriorityQueue<Edge>(numVertex, new Edge());
    }

	public Dijkstra(Graph graph, int numVertex, Vertex source, Label stepLabel, Group canvasGroup, List<NodeFX> circles, TextArea textFlow) {
		super(graph, numVertex, source);
		this.stepLabel = stepLabel;
		this.textFlow = textFlow;
		this.canvasGroup = canvasGroup;
		this.circles = circles;
		this.settled = new HashSet<Vertex>();
		this.pq = new PriorityQueue<Edge>(numVertex, new Edge());
	}

		//method
    private void e_Neighbours(int u, ArrayList<Edge>[] adj, SequentialTransition st)
	{
		double edgeDistance = -1.0;
		double newDistance = -1.0;

		// All the neighbors of v
		for (int i = 0; i < adj[u].size(); i++) {
			Edge v = adj[u].get(i);

			// If current node hasn't already been processed
			if (!settled.contains(v.getNodeTarget())) {
				edgeDistance = v.getWeight();
				newDistance = this.getDist()[u] + edgeDistance;

				// If new distance is cheaper in cost
				if (newDistance < this.getDist()[v.getNodeTarget().getId()]) {
					this.setOneDist(v.getNodeTarget().getId(), newDistance);
					this.getStep().addStep(
						u, 
						v.getNodeTarget().getId(), 
						newDistance, 
						"Distance from " + u + " to " + v.getNodeTarget().getId() + " is: " + newDistance
							, this.stepLabel, this.canvasGroup, this.circles, st, true, this.textFlow
					);
				}

				// Add the current node to the queue
				pq.add(new Edge(
						v.getNodeTarget().getId(), 
						v.getNodeTarget().getId(), 
						this.getDist()[v.getNodeTarget().getId()])
				);
			}
		}
	}
    
	//override
	@Override
	public void execute(SequentialTransition st) {
		//st = new SequentialTransition();

		Vertex src = this.getSource();
		ArrayList<Edge> adj[]= this.getGraph().getEdge();
		for (int i = 0; i < this.getNumVertex(); i++)
			this.setOneDist(i, Double.MAX_VALUE);

		// Add source node to the priority queue
		pq.add(new Edge(src.getId(), src.getId(), 0));

		// Distance to the source is 0
		this.setOneDist(this.getSource().getId(), 0);

		while (settled.size() != this.getNumVertex()) {

			// Terminating condition check when
			// the priority queue is empty, return
			if (pq.isEmpty())
				return;

			// Removing the minimum distance node
			// from the priority queue
			int u = pq.remove().getNodeTarget().getId();
			this.getStep().addStep(u, u, this.getDist()[u], "Verify node " + u, this.stepLabel, this.canvasGroup, this.circles, st, false, this.textFlow);

			// Adding the node whose distance is
			// finalized
			if (settled.contains(new Vertex(u)))

				// Continue keyword skips execution for
				// following check
				continue;

			// We don't have to call e_Neighbors(u)
			// if u is already present in the settled set.
			settled.add(new Vertex(u));
			this.getStep().addStep(u, u, this.getDist()[u], "Settle node " + u, this.stepLabel, this.canvasGroup, this.circles, st, true, this.textFlow);

			e_Neighbours(u, adj, st);
		}
		st.onFinishedProperty();
		st.play();
	}
	@Override
	public void play() {
		System.out.println("Dijkstra is: ");
		for (int i = 0; i < this.getDist().length; i++)
			System.out.println(0 + " to " + i + " is " + this.getDist()[i]);
		System.out.println();
		for(StepInfo i : this.getStep().getStepDetail()) {
			System.out.println(i.getDetail());
		}
	}
}
