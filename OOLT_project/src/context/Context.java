package context;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import algorithms.*;
import graph.*;
import graphics.Arrow;
import graphics.NodeFX;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import utils.Utils;

import javax.swing.*;
import java.util.Scanner;
import java.util.List;
public class Context {
	//constructor
	public Context() {
		
	}

	public Context(Graph myGraph, Group canvasGroup, List<NodeFX> circles,  Line edgeLine, Arrow arrow, int type, boolean directed, EventHandler<MouseEvent> mouseHandler) {
		String nameFile = "bfs.txt";
		if (type == 1){
			nameFile = "bfs.txt";
		}
		else if (type == 2){
			nameFile = "dijikstra.txt";
		}
		else {
			nameFile = "bf.txt";
		}
		try {
			File myObj = new File(Utils.uri + nameFile);
			Scanner myReader = new Scanner(myObj);
			String vertexName;
			double vertexX;
			double vertexY;
			boolean isInputVertex = false;
			boolean isDirected = myGraph.getIsDirected();
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();

				if (data.equalsIgnoreCase(new String("Vertex:"))){
					isInputVertex = true;
					continue;
				}
				if (data.equalsIgnoreCase(new String("Edges:"))){
					isInputVertex = false;
					continue;
				}
				String[] parts = data.split(" ");
				if (isInputVertex){
					NodeFX circle = new NodeFX(Double.parseDouble(parts[1]), Double.parseDouble(parts[2]), 1.2, String.valueOf(Integer.parseInt(parts[0])) , myGraph, canvasGroup);
					circle.setOnMousePressed(mouseHandler);
					circle.setOnMouseReleased(mouseHandler);
					circle.setOnMouseDragged(mouseHandler);
					circle.setOnMouseExited(mouseHandler);
					circle.setOnMouseEntered(mouseHandler);
					circles.add(circle);
					canvasGroup.getChildren().add(circle);
					ScaleTransition tr = new ScaleTransition(Duration.millis(100), circle);
					tr.setByX(15f);
					tr.setByY(15f);
					tr.setInterpolator(Interpolator.EASE_OUT);
					tr.play();
				}
				else {
					System.out.println(parts[0]+ " " +parts[1]+ " " +parts[2]);
					myGraph.addEdge(Integer.parseInt(parts[0]),Integer.parseInt(parts[1]), Double.parseDouble(parts[2]));
					if (isDirected){
						arrow = new Arrow(circles.get(Integer.parseInt(parts[0])).point.x, circles.get(Integer.parseInt(parts[0])).point.y, circles.get(Integer.parseInt(parts[1])).point.x, circles.get(Integer.parseInt(parts[1])).point.y);
						arrow.setStrokeWidth(1.5);
						canvasGroup.getChildren().add(arrow);
					}
					else {
						edgeLine = new Line(circles.get(Integer.parseInt(parts[0])).point.x, circles.get(Integer.parseInt(parts[0])).point.y, circles.get(Integer.parseInt(parts[1])).point.x, circles.get(Integer.parseInt(parts[1])).point.y);
						edgeLine.setStrokeWidth(1.5);
						canvasGroup.getChildren().add(edgeLine);
					}
					if (parts.length >= 2){
						Label weight = new Label();
						weight.setLayoutX((circles.get(Integer.parseInt(parts[0])).point.x+ (circles.get(Integer.parseInt(parts[1])).point.x)) / 2);
						weight.setLayoutY(((circles.get(Integer.parseInt(parts[0])).point.y) + (circles.get(Integer.parseInt(parts[1])).point.y)) / 2);
						weight.setStyle("-fx-font-size: 16px");
						weight.setTextFill(Color.BROWN);
						weight.setText(parts[2]);
						canvasGroup.getChildren().add(weight);
					}

				}
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

	}
	
	// method
	public void setUpAlgorithm(Algorithm algo, SequentialTransition st) {
		algo.execute(st);
	}
	public void play(Algorithm algo) {
		algo.play();
	}
}
