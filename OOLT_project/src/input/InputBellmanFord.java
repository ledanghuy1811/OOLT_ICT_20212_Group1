package input;

import java.util.Scanner;

import algorithms.*;
import graph.*;

public class InputBellmanFord implements InputAlgorithm {
	@Override
	public Algorithm inputAlgorithm() {
		Scanner keyBoard = new Scanner(System.in);
		
		Graph aGraph = InputGraph.setGraph();
		int numVertex = aGraph.getVertex().size();
		System.out.println("Enter node source id: ");
		int sourceId = keyBoard.nextInt();
		
		return new BellmanFord(aGraph, numVertex, new Vertex(sourceId));
	}
}
