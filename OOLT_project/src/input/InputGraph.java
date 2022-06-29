package input;

import java.util.Scanner;

import graph.Graph;

public class InputGraph {
	public static Graph setGraph(boolean isDirected, boolean isWeighted) {
		Scanner keyBoard = new Scanner(System.in);
		Graph aGraph = new Graph(isDirected, isWeighted);
		return aGraph;
	}
}
