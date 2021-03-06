package algorithms;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import graph.*;
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
	
	//setter and getter
	public LinkedList<Vertex>[] getAdj() {
		return adj;
	}
	
	
	//override method
	@Override
	public void execute() {
		ArrayList<String> pseudoContent = new ArrayList<String>();
		pseudoContent.add("BFS(u), Q = {u}");
		pseudoContent.add("while !Q.empty // Q is a normal queue");
		pseudoContent.add("for each neighbor v of u = Q.front, Q.pop");
		pseudoContent.add("if v is unvisited, tree edge, Q.push(v)");
		pseudoContent.add("else if v is visited, we ignore this edge");
		this.getStep().getPseudoStep().setPseudo(pseudoContent);
		
		nIndex = 0;
		Vertex s = this.getSource();
		// Mark all the vertices as not visited(By default
        // set as false)
		boolean visited[] = new boolean [this.getNumVertex()];
		
		// Create a queue for BFS
		LinkedList<Vertex> queue = new LinkedList<Vertex>();
		
		// Mark the current node as visited and enqueue it
		visited[this.getSource().getId()] = true;
		queue.add(s);
		this.getStep().getPseudoStep().setIndex(0);
		
		while(queue.size() != 0) {
			// Dequeue a vertex from queue and print it
			s = queue.poll();
			this.setOneDist(nIndex, s.getId());
			nIndex ++;
			this.getStep().addStep(s.getId(), s.getId(), 0, "Verify node " + s.getId());
			this.getStep().getPseudoStep().setIndex(1);
			
			// Get all adjacent vertices of the dequeued vertex s
            // If a adjacent has not been visited, then mark it
            // visited and enqueue it
			Iterator<Vertex> i = this.adj[s.getId()].listIterator();
			while (i.hasNext()) {
				this.getStep().getPseudoStep().setIndex(2);
                Vertex n = i.next();
                if (!visited[n.getId()])
                {
                    visited[n.getId()] = true;
                    queue.add(n);
                    this.getStep().addStep(s.getId(), n.getId(), 1, "Check node " + n.getId());
                    this.getStep().getPseudoStep().setIndex(3);
                }
                else {
                	this.getStep().getPseudoStep().setIndex(4);
                }
            }
		}
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
