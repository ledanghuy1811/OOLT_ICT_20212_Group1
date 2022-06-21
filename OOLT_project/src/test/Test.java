package test;

import java.util.ArrayList;
import algorithms.Algorithms;
import algorithms.*;
import graph.*;

public class Test {
	public static void main(String[] args) {
		Graph aGraph = new Graph(false);
		aGraph.addVertex(0);
		aGraph.addVertex(1);
		aGraph.addVertex(2);
		aGraph.addVertex(3);
		aGraph.addEdge(0, 1, 0);
		aGraph.addEdge(0, 2, 0);
		aGraph.addEdge(1, 2, 0);
		aGraph.addEdge(2, 0, 0);
		aGraph.addEdge(2, 3, 0);
		aGraph.addEdge(3, 3, 0);
		
		BFS aBFS = new BFS(aGraph, 
				aGraph.getVertex().size(), 
				aGraph.getEdge().length, 
				aGraph.getOneVertex(2)
		);
		aBFS.setAdj();
		aBFS.excute();
		
	}
}