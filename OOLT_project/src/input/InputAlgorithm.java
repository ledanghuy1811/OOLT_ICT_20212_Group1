package input;

import algorithms.Algorithm;
import graph.Graph;
import graphics.NodeFX;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.util.List;

public interface InputAlgorithm {
	public Algorithm inputAlgorithm(Graph aGraph, int sourceId, Label distances, Group canvasGroup, List<NodeFX> circles, Group textFlow, List<Label> textFlowLabels);
}
