package algorithms;

import java.util.Iterator;
import java.util.LinkedList;
import graph.*;

public class BFS extends Algorithms {
	private LinkedList<Vertex> adj[] = new LinkedList[this.getNumVertex()];
	private Vertex source;
	
	//constructor
	public BFS() {
		
	}
	public BFS(Graph graph, int numVertex, int numEdge, Vertex source) {
		super(graph, numVertex, numEdge);
		this.source = source;
	}
	
	//setter and getter
	public void setAdj() {
		for(int i = 0; i < this.getNumVertex(); i++) {
			adj[i] = new LinkedList();
			for(Vertex v : this.getGraph().getAdjacent(i)) {
				adj[i].add(v);
			}
		}
	}
	public LinkedList<Vertex>[] getAdj() {
		return adj;
	}
	public Vertex getSource() {
		return source;
	}
	
	//override method
	@Override
	public void excute() {
		Vertex s = this.source;
		// Mark all the vertices as not visited(By default
        // set as false)
		boolean visited[] = new boolean [this.getNumVertex()];
		
		// Create a queue for BFS
		LinkedList<Vertex> queue = new LinkedList<Vertex>();
		
		// Mark the current node as visited and enqueue it
		visited[this.source.getId()] = true;
		queue.add(s);
		
		while(queue.size() != 0) {
			// Dequeue a vertex from queue and print it
			s = queue.poll();
			System.out.print(s.getId() + " ");
			
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
                }
            }
		}
	}
}
