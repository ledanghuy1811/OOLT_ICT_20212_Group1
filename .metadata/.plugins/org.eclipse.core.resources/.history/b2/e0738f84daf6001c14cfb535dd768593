package context;

import java.util.Scanner;

import algorithms.*;
import graph.*;

public class SetBFS implements SetAlgorithms {
	@Override
	public Algorithms setAlgorithms() {
		Scanner keyBoard = new Scanner(System.in);
		
		Graph aGraph = SetGraph.setGraph();
		int numVertex = aGraph.getVertex().size();
		System.out.println("Enter node source id: ");
		int sourceId = keyBoard.nextInt();
		
		return new BFS(aGraph, numVertex, new Vertex(sourceId));
	}
}
