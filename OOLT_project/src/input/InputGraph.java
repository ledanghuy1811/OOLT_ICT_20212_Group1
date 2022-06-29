package input;

import java.util.Scanner;

import graph.Graph;

public class InputGraph {
	public static Graph setGraph() {
		Scanner keyBoard = new Scanner(System.in);
		
		System.out.println("Is graph directed(0-1): ");
		int choice = keyBoard.nextInt();
		boolean isDirected;
		if(choice == 1)
			isDirected = true;
		else
			isDirected = false;
		System.out.println("Is graph weighted(0-1): ");
		choice = keyBoard.nextInt();
		boolean isWeighted;
		if(choice == 1)
			isWeighted = true;
		else
			isWeighted = false;
		Graph aGraph = new Graph(isDirected, isWeighted);
		
		//set vertex and edge
		System.out.println("Input vertex:");
		while(true) {
			System.out.println("Enter vertex id: ");
			int id = keyBoard.nextInt();
			aGraph.addVertex(id);
			System.out.println("Enter 0 to exit: ");
			int isExit = keyBoard.nextInt();
			if(isExit == 0)
				break;
		}
		System.out.println("Input edge:");
		while(true) {
			System.out.println("Enter edge node source id: ");
			int idSource = keyBoard.nextInt();
			System.out.println("Enter edge node target id: ");
			int idTarget = keyBoard.nextInt();
			if(isWeighted) {
				System.out.println("Enter weight: ");
				double weight = keyBoard.nextDouble();
				aGraph.addEdge(idSource, idTarget, weight);
			}
			else 
				aGraph.addEdge(idSource, idTarget, 0);
			System.out.println("Enter 0 to exit: ");
			int isExit = keyBoard.nextInt();
			if(isExit == 0)
				break;
		}
		return aGraph;
	}
}
