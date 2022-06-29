package input;

import java.util.List;
import java.util.Scanner;

import algorithms.*;
import graph.*;
import graphics.NodeFX;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class InputBFS implements InputAlgorithm {
	@Override
	public Algorithm inputAlgorithm(Graph aGraph, int sourceId, Label distances, Group canvasGroup, List<NodeFX> circles, TextArea textFlow) {
		Scanner keyBoard = new Scanner(System.in);

		int numVertex = aGraph.getVertex().size();
		System.out.println("Size: " +  numVertex);
		return new BFS(aGraph, numVertex, new Vertex(sourceId), distances, canvasGroup, circles, textFlow);
	}
}
