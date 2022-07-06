package algorithms;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import graph.*;
import graphics.NodeFX;
import javafx.animation.SequentialTransition;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import step.*;

public class BFS extends Algorithm {
	private LinkedList<Vertex> adj[];
	private static int nIndex = 0;
	
	//constructor
	public BFS() {
		super();
	}
	public BFS(Graph graph, int numVertex, Vertex source) {
		super(graph, numVertex, source);
		this.adj = new LinkedList[numVertex];
		for(int i = 0; i < numVertex; i++) {
			adj[i] = new LinkedList();
			for(Vertex v : graph.getAdjacent(i)) {
				adj[i].add(v);
			}
		}
	}

	public BFS(Graph graph, int numVertex, Vertex source, Label stepLabel, Group canvasGroup, List<NodeFX> circles, TextArea textFlow) {
		super(graph, numVertex, source, stepLabel, canvasGroup, circles, textFlow);
		this.adj = new LinkedList[numVertex];
		for(int i = 0; i < numVertex; i++) {
			adj[i] = new LinkedList();
			for(Vertex v : graph.getAdjacent(i)) {
				adj[i].add(v);
			}
		}
	}
	
	//setter and getter
	public LinkedList<Vertex>[] getAdj() {
		return adj;
	}
	
	
	//override method
	@Override
	public void execute(SequentialTransition st) {
		nIndex = 0;
		//st = new SequentialTransition();
		Vertex s = this.getSource();
		// Mark all the vertices as not visited(By default
		// set as false)
		boolean visited[] = new boolean [this.getNumVertex()];

		// Create a queue for BFS
		LinkedList<Vertex> queue = new LinkedList<Vertex>();

		// Mark the current node as visited and enqueue it
		visited[this.getSource().getId()] = true;
		queue.add(s);

		while(queue.size() != 0) {
			// Dequeue a vertex from queue and print it
			s = queue.poll();
//			System.out.print(s.getId() + " ");
			System.out.println("Node index: " +  nIndex + "Id source: " + s.getId());
			this.setOneDist(nIndex, s.getId());
			nIndex ++;
			this.getStep().addStep(s.getId(), s.getId(), 0, "Verify node " + s.getId(), this.stepLabel, this.canvasGroup, this.circles, st,false, this.textFlow);

			// Get all adjacent vertices of the dequeued vertex s
			// If a adjacent has not been visited, then mark it

			// visited and enqueue it
			Iterator<Vertex> i = this.adj[s.getId()].listIterator();
			while (i.hasNext())
			{
				Vertex n = i.next();
				if (!visited[n.getId()])
				{
					visited[n.getId()] = true;
					queue.add(n);
					this.getStep().addStep(s.getId(), n.getId(), 1, "Check node " + n.getId(), this.stepLabel, this.canvasGroup, this.circles, st,true, this.textFlow);
				}
			}
		}
		st.onFinishedProperty();
		st.play();
	}
	@Override
	public void play() {
		System.out.println("BFS is:");
		for(int i = 0; i < this.getDist().length; i++) {
			System.out.print((int)this.getDist()[i] + "\t");
		}
		System.out.println();
		for(StepInfo i : this.getStep().getStepDetail()) {
			System.out.println(i.getDetail());
		}
	}
}
