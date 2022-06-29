package graphics.controller;
import com.jfoenix.controls.*;
import graph.Graph;
import javafx.animation.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.*;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.util.Duration;
import org.controlsfx.control.HiddenSidesPane;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CanvasController implements Initializable {
    Graph myGraph;
    public class NodeFX extends Circle {
        public Point point;
        public Label distance = new Label("Dist. : INFINITY");
        public Label visitTime = new Label("Visit : 0");
        public Label lowTime = new Label("Low : 0");
        public Label id;
        public boolean isSelected = false;

        public NodeFX(double x, double y, double rad, String name) {
            super(x, y, rad);
            point = new Point((int) x, (int) y);
            id = new Label(name);
            myGraph.addVertex(Integer.parseInt(id.getText()));
            System.out.printf("Add vertex %s to graph\n", id.getText());
            canvasGroup.getChildren().add(id);
            id.setLayoutX(x - 3);
            id.setLayoutY(y - 9);
            this.setOpacity(0.5);
            this.setBlendMode(BlendMode.MULTIPLY);
            this.setId("node");

            circles.add(this);
        }
    }

    @FXML
    protected HiddenSidesPane hiddenPane;
    @FXML
    protected AnchorPane anchorRoot;
    @FXML
    protected StackPane stackRoot;
    @FXML
    protected JFXButton canvasBackButton, clearButton, resetButton, playPauseButton;
    @FXML
    protected JFXToggleButton addNodeButton, addEdgeButton, bfsButton, dijkstraButton,
            belFordButton;
    @FXML
    protected ToggleGroup algoToggleGroup;
    @FXML
    protected Pane viewer;
    @FXML
    protected Group canvasGroup;
    @FXML
    protected Line edgeLine;
    @FXML
    protected Label sourceText = new Label("Source"), weight;
    @FXML
    protected Pane border;

    @FXML
    protected JFXNodesList nodeList;


    protected boolean menuBool = false;
    protected ContextMenu globalMenu;

    protected int nNode = 0, time = 500;
    protected boolean addNode = true, addEdge = false, calculate = false,
            calculated = false;
    protected List<Label> distances = new ArrayList<Label>(), visitTime = new ArrayList<>(), lowTime = new ArrayList<Label>();
    protected boolean weighted = Panel1Controller.weighted,
            directed = Panel1Controller.directed,
            bfs = true, dijkstra = true, bf = true;

    public AnchorPane hiddenRoot = new AnchorPane();
    protected NodeFX selectedNode = null;
    protected List<NodeFX> circles = new ArrayList<>();
    protected Arrow arrow;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("In init");
        hiddenPane.setContent(canvasGroup);
        viewer.prefHeightProperty().bind(border.heightProperty());
        viewer.prefWidthProperty().bind(border.widthProperty());
        addEdgeButton.setDisable(true);
        addNodeButton.setDisable(true);
        clearButton.setDisable(true);
        if (weighted) {
            bfsButton.setDisable(true);
        }
        if (!weighted) {
            belFordButton.setDisable(true);
            dijkstraButton.setDisable(true);
        }
        // intialize graph
        myGraph = new Graph(directed, weighted);
        canvasBackButton.setOnAction(e -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("Panel1FXML.fxml"));
                Scene scene = new Scene(root);
                Main.primaryStage.setScene(scene);
            } catch (IOException ex) {
                Logger.getLogger(CanvasController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        hiddenRoot.setPrefWidth(220);
        hiddenRoot.setPrefHeight(581);
        hiddenRoot.setCursor(Cursor.DEFAULT);

        //Set Label "Detail"
        Label detailLabel = new Label("Detail");
        detailLabel.setPrefSize(hiddenRoot.getPrefWidth() - 20, 38);
        detailLabel.setAlignment(Pos.CENTER);
        detailLabel.setFont(new Font("Roboto", 20));
        detailLabel.setPadding(new Insets(7, 40, 3, -10));
        detailLabel.setStyle("-fx-background-color:  #2A2B36;");
        detailLabel.setStyle("-fx-color:  white;");
        detailLabel.setLayoutX(35);

    }

    @FXML
    public void handle(MouseEvent ev) {
        if (addNode) {
            if (nNode == 1) {
                addNodeButton.setDisable(false);
            }
            if (nNode == 2) {
                addEdgeButton.setDisable(false);
            }

            if (!ev.getSource().equals(canvasGroup)) {
                if (ev.getEventType() == MouseEvent.MOUSE_RELEASED && ev.getButton() == MouseButton.PRIMARY) {
                    if (menuBool == true) {
                        System.out.println("here" + ev.getEventType());
                        menuBool = false;
                        return;
                    }
                    nNode++;

                    NodeFX circle = new NodeFX(ev.getX(), ev.getY(), 1.2, String.valueOf(nNode));
                    canvasGroup.getChildren().add(circle);

                    circle.setOnMousePressed(mouseHandler);
                    circle.setOnMouseReleased(mouseHandler);
                    circle.setOnMouseDragged(mouseHandler);
                    circle.setOnMouseExited(mouseHandler);
                    circle.setOnMouseEntered(mouseHandler);

                    ScaleTransition tr = new ScaleTransition(Duration.millis(100), circle);
                    tr.setByX(15f);
                    tr.setByY(15f);
                    tr.setInterpolator(Interpolator.EASE_OUT);
                    tr.play();

                }
            }
        }
    }

    @FXML
    public void ResetHandle(ActionEvent event) {
    }

    @FXML
    public void ClearHandle(ActionEvent event) {
    }

    @FXML
    public void AddEdgeHandle(ActionEvent event) {
        addNode = false;
        addEdge = true;
        calculate = false;
        addNodeButton.setSelected(false);
        addEdgeButton.setSelected(true);

        if (!weighted) {
            bfsButton.setDisable(false);
            bfsButton.setSelected(false);
        }
        if (weighted) {
            dijkstraButton.setDisable(false);
            dijkstraButton.setSelected(false);
            belFordButton.setDisable(false);
            belFordButton.setSelected(false);
        }
    }

    @FXML
    public void AddNodeHandle(ActionEvent event) {
        addNode = true;
        addEdge = false;
        calculate = false;
        addNodeButton.setSelected(true);
        addEdgeButton.setSelected(false);
        selectedNode = null;

        if (!weighted) {
            bfsButton.setDisable(false);
            bfsButton.setSelected(false);
        }
        if (weighted) {
            dijkstraButton.setDisable(false);
            dijkstraButton.setSelected(false);
            belFordButton.setDisable(false);
            belFordButton.setSelected(false);
        }
    }

    @FXML
    public void BFSHandle(ActionEvent event) {
        addNode = false;
        addEdge = false;
        addNodeButton.setSelected(false);
        addEdgeButton.setSelected(false);
        addNodeButton.setDisable(true);
        addEdgeButton.setDisable(true);
        calculate = true;
        clearButton.setDisable(false);
        bfs = true;
        bf = false;
        dijkstra = false;
    }

    @FXML
    public void BFHandler(ActionEvent event) {
        addNode = false;
        addEdge = false;
        addNodeButton.setSelected(false);
        addEdgeButton.setSelected(false);
        addNodeButton.setDisable(true);
        addEdgeButton.setDisable(true);
        calculate = true;
        clearButton.setDisable(false);
        bf = true;
        bfs = false;
        dijkstra = false;
    }

    @FXML
    public void DijkstraHandle(ActionEvent event) {
        addNode = false;
        addEdge = false;
        addNodeButton.setSelected(false);
        addEdgeButton.setSelected(false);
        addNodeButton.setDisable(true);
        addEdgeButton.setDisable(true);
        calculate = true;
        clearButton.setDisable(false);
        bfs = false;
        bf = false;
        dijkstra = true;
    }

    protected EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent mouseEvent) {

            System.out.println("Handling...!");
            NodeFX circle = (NodeFX) mouseEvent.getSource();
            if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED && mouseEvent.getButton() == MouseButton.PRIMARY) {
                if (!circle.isSelected) {
                    if (selectedNode != null) {
                        if (addEdge && myGraph.getOneEdge(Integer.parseInt(selectedNode.id.getText()), Integer.parseInt(circle.id.getText())) == null) {
                            weight = new Label();
                            System.out.println("Adding Edge");
                            if (!directed) {
                                edgeLine = new Line(selectedNode.point.x, selectedNode.point.y, circle.point.x, circle.point.y);
                                edgeLine.setStrokeWidth(1.5);
                                canvasGroup.getChildren().add(edgeLine);
                                edgeLine.setId("line");
                            } else if (directed) {
                                arrow = new Arrow(selectedNode.point.x, selectedNode.point.y, circle.point.x, circle.point.y);
                                arrow.setStrokeWidth(1.5);
                                canvasGroup.getChildren().add(arrow);
                                arrow.setId("arrow");
                            }
                            //Adds weight between two selected nodes
                            if (weighted) {
                                weight.setLayoutX(((selectedNode.point.x) + (circle.point.x)) / 2);
                                weight.setLayoutY(((selectedNode.point.y) + (circle.point.y)) / 2);

                                TextInputDialog dialog = new TextInputDialog("0");
                                dialog.setTitle(null);
                                dialog.setHeaderText("Enter Weight of the Edge :");
                                dialog.setContentText(null);

                                Optional<String> result = dialog.showAndWait();
                                if (result.isPresent()) {
                                    weight.setText(result.get());
                                } else {
                                    weight.setText("0");
                                }
                                weight.setStyle("-fx-font-size: 16px");
                                weight.setTextFill(Color.BROWN);
                                canvasGroup.getChildren().add(weight);
                            } else if (!weighted) {
                                weight.setText("1");
                            }
                            //Adds the edge between two selected nodes
                            myGraph.addEdge(Integer.parseInt(selectedNode.id.getText()), Integer.parseInt(circle.id.getText()), Integer.parseInt(weight.getText()));
                            System.out.printf("Add edge %s to %s in graph", selectedNode.id.getText(), circle.id.getText());

                        }
                        System.out.println("Node: " + addNode + " Edge: " + addEdge);

                        if (addNode || (calculate && !calculated) || addEdge) {
                            System.out.println("Run also");
                            selectedNode.isSelected = false;
                            FillTransition ft1 = new FillTransition(Duration.millis(300), selectedNode, Color.RED, Color.BLACK);
                            ft1.play();
                        }
                        selectedNode = null;
                        return;
                    }

                    FillTransition ft = new FillTransition(Duration.millis(300), circle, Color.BLACK, Color.RED);
                    ft.play();
                    circle.isSelected = true;
                    selectedNode = circle;

                    // WHAT TO DO WHEN SELECTED ON ACTIVE ALGORITHM
                    if (calculate && !calculated) {
                        if (bfs) {
                        } else if (dijkstra) {
                        } else if (bf) {
                        }
                        // belmond ford in here

                        calculated = true;
                    } else if (calculate && calculated) {

                    }
                } else {
                    System.out.println("Disable select");
                    circle.isSelected = false;
                    FillTransition ft1 = new FillTransition(Duration.millis(300), circle, Color.RED, Color.BLACK);
                    ft1.play();
                    selectedNode = null;
                }

            }

        }

        ;;

    };
}
