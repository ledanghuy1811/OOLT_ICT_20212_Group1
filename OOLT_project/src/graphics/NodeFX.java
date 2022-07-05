package graphics;

import graph.Graph;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.effect.BlendMode;
import javafx.scene.shape.Circle;

import java.awt.*;

public class NodeFX extends Circle {
    public Point point;
    public javafx.scene.control.Label distance = new javafx.scene.control.Label("Dist. : INFINITY");
    public javafx.scene.control.Label id;
    public boolean isSelected = false;

    public NodeFX(double x, double y, double rad, String name, Graph myGraph, Group canvasGroup) {
        super(x, y, rad);
        point = new Point((int) x, (int) y);
        System.out.printf("x: %f - y: %f\n", x, y   );
        id = new Label(name);
        myGraph.addVertex(Integer.parseInt(id.getText()));
        System.out.println(id.getText());
        System.out.printf("Add vertex %s to graph\n", id.getText());
        canvasGroup.getChildren().add(id);
        id.setLayoutX(x - 3);
        id.setLayoutY(y - 9);
        this.setOpacity(0.5);
        this.setBlendMode(BlendMode.MULTIPLY);
        this.setId("node");
    }
}
