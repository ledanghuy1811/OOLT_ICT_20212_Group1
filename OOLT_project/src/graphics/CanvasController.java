package graphics;
import algorithms.Algorithm;
import algorithms.BFS;
import algorithms.BellmanFord;
import algorithms.Dijkstra;
import com.jfoenix.controls.*;
import context.Context;
import graph.Graph;
import input.*;
import javafx.animation.*;
import javafx.event.ActionEvent;
import main.Main;
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
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.util.Duration;
import org.controlsfx.control.HiddenSidesPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CanvasController implements Initializable {
    Graph myGraph;
    @FXML
    protected HiddenSidesPane hiddenPane;
    @FXML
    protected AnchorPane anchorRoot;
    @FXML
    protected StackPane stackRoot;
    @FXML
    protected JFXButton canvasBackButton, clearButton, resetButton, initButton, canvasChangeTime;
    @FXML
    protected JFXToggleButton addNodeButton, addEdgeButton, bfsButton, dijkstraButton,
            belFordButton;
    @FXML
    protected ToggleGroup algoToggleGroup;

    @FXML
    protected Label stepLabel;
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
            calculated = false, pinned = false;;
    protected List<Label> distances = new ArrayList<Label>();
    protected boolean weighted = Panel1Controller.weighted,
            directed = Panel1Controller.directed,
            bfs = true, dijkstra = true, bf = true;

    public AnchorPane hiddenRoot = new AnchorPane();
    protected NodeFX selectedNode = null;
    protected List<NodeFX> circles = new ArrayList<>();
    protected Arrow arrow;

    public Group textFlow = new Group();
    public ArrayList<Label> textFlowLabels = new ArrayList<Label>();

    public ScrollPane textContainer = new ScrollPane();
    private Context context;
    protected SequentialTransition st = new SequentialTransition();

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
            dijkstraButton.setDisable(true);
            belFordButton.setDisable(true);
        }
        // intialize graph
        System.out.println("Directed: " + directed + "Weight: " + weighted);
        myGraph = InputGraph.setGraph(directed, weighted);
        canvasBackButton.setOnAction(e -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("Panel1FXML.fxml"));
                Scene scene = new Scene(root);
                Main.primaryStage.setScene(scene);
            } catch (IOException ex) {
                Logger.getLogger(CanvasController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        hiddenRoot.setPrefWidth(Utils.slidePanelSizeX);
        hiddenRoot.setPrefHeight(581);
        hiddenRoot.setCursor(Cursor.DEFAULT);

        //Set Label "Detail"
        Label detailLabel = new Label("Pseudo");
        detailLabel.setPrefSize(hiddenRoot.getPrefWidth() - 20, 38);
        detailLabel.setAlignment(Pos.CENTER);
        detailLabel.setFont(new Font("Roboto", 20));
        detailLabel.setPadding(new Insets(7, 40, 3, -10));
        detailLabel.setStyle("-fx-background-color:  #FF9F29 !important;");
        detailLabel.setStyle("-fx-color:  white !important;");
        detailLabel.setLayoutX(35);
        //Set TextFlow pane properties
        textFlow.setLayoutY(39);
        Pane textFlowPane = new Pane();
        textFlowPane.setPrefHeight(603);
        textFlowPane.setPrefWidth(Utils.slidePanelSizeX);
        textFlow.getChildren().add(textFlowPane);
        textContainer.setLayoutY(textFlow.getLayoutY());
        //textFlow.setPadding(new Insets(5, 0, 0, 5));
        textContainer.setContent(textFlow);

        //Set Pin/Unpin Button
        JFXButton pinUnpin = new JFXButton();
        pinUnpin.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

        ImageView imgPin = new ImageView(new Image(getClass().getResourceAsStream("res/pinned.png")));
        imgPin.setFitHeight(20);
        imgPin.setFitWidth(20);
        ImageView imgUnpin = new ImageView(new Image(getClass().getResourceAsStream("res/unpinned.png")));
        imgUnpin.setFitHeight(20);
        imgUnpin.setFitWidth(20);
        pinUnpin.setGraphic(imgPin);

        pinUnpin.setPrefSize(20, 39);
        pinUnpin.setButtonType(JFXButton.ButtonType.FLAT);
        pinUnpin.setStyle("-fx-background-color: #dcdde1;");
        pinUnpin.setOnMouseClicked(e -> {
            if (pinned) {
                pinUnpin.setGraphic(imgPin);
                hiddenPane.setPinnedSide(null);
                pinned = false;
            } else {
                pinUnpin.setGraphic(imgUnpin);
                hiddenPane.setPinnedSide(Side.RIGHT);
                pinned = true;
            }
        });
        //Add Label and TextFlow to hiddenPane
        hiddenRoot.getChildren().addAll(pinUnpin, detailLabel, textContainer);
        hiddenPane.setRight(hiddenRoot);
        hiddenRoot.setOnMouseEntered(e -> {
            hiddenPane.setPinnedSide(Side.RIGHT);
            e.consume();
        });
        hiddenRoot.setOnMouseExited(e -> {
            if (!pinned) {
                hiddenPane.setPinnedSide(null);
            }
            e.consume();
        });
        hiddenPane.setTriggerDistance(60);

    }
    @FXML
    public void changeTimeAnimation(ActionEvent ev){
        TextInputDialog timeChange = new TextInputDialog("500");
        timeChange.setTitle(null);
        timeChange.setHeaderText("Enter time animation (miliseconds):");
        timeChange.setContentText(null);

        Optional<String> result = timeChange.showAndWait();

        try {
            if (result.isPresent()) {
                Utils.timeAnimation = Integer.parseInt(result.get());
            } else {
                Utils.timeAnimation = 500;
            }
        }catch(Exception e){
            Utils.timeAnimation = 500;
        }
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
                        menuBool = false;
                        return;
                    }
                    nNode++;

                    NodeFX circle = new NodeFX(ev.getX(), ev.getY(), 1.2, String.valueOf(nNode - 1) , myGraph, canvasGroup);
                    circles.add(circle);
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
        ClearHandle(null);
        nNode = 0;
        canvasGroup.getChildren().clear();
        canvasGroup.getChildren().addAll(viewer);

        selectedNode = null;
        circles = new ArrayList<NodeFX>();
        distances = new ArrayList<Label>();
        addNode = true;
        addEdge = false;
        calculate = false;
        calculated = false;
        addNodeButton.setSelected(true);
        addEdgeButton.setSelected(false);
        addEdgeButton.setDisable(true);
        addNodeButton.setDisable(false);
        clearButton.setDisable(true);
        bfsButton.setDisable(true);
        initButton.setDisable(false);
        dijkstraButton.setDisable(true);
        belFordButton.setDisable(true);
        myGraph = InputGraph.setGraph(directed, weighted);
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
        if (textFlowLabels != null){
            for (Label textFlowLabel: textFlowLabels){
                textFlow.getChildren().remove(textFlowLabel);
            }
            textFlowLabels = new ArrayList<Label>();
        }
    }

    @FXML
    public void InitializeData(ActionEvent event){
        if (bfs){
            ResetHandle(null);
            myGraph = InputGraph.setGraph(directed, weighted);
            context = new Context(myGraph,canvasGroup,circles,edgeLine, arrow, 1,directed, mouseHandler);
            nNode += 8;
        }
        else if (dijkstra){
            ResetHandle(null);
            myGraph = InputGraph.setGraph(directed, weighted);
            context = new Context(myGraph,canvasGroup,circles,edgeLine, arrow, 2,directed, mouseHandler);
            nNode += 4;
        }
        else {
            ResetHandle(null);
            myGraph = InputGraph.setGraph(directed, weighted);
            context = new Context(myGraph,canvasGroup,circles,edgeLine, arrow, 3,directed, mouseHandler);
            nNode += 5;
        }

        initButton.setDisable(true);
        addEdgeButton.setDisable(false);
        addNodeButton.setDisable(false);
    }
    @FXML
    public void ClearHandle(ActionEvent event) {
        if(st != null && st.getStatus() != Animation.Status.STOPPED)
        {
            System.out.println("Run clear");
            st.stop();
        }
        if(st != null){
            st.getChildren().clear();
        }
        stepLabel.setText("");
        menuBool = false;
        selectedNode = null;
        calculated = false;
        for (NodeFX n : circles) {
            n.isSelected = false;
            FillTransition ft1 = new FillTransition(Duration.millis(300), n);
            ft1.setToValue(Color.BLACK);
            ft1.play();
        }
        for (Label x : distances) {
            x.setText("Distance : INFINITY");
            canvasGroup.getChildren().remove(x);
        }
        distances = new ArrayList<>();
        addNodeButton.setDisable(false);
        addEdgeButton.setDisable(false);
        initButton.setDisable(true);
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
        bfs = false;
        dijkstra = false;
        bf = false;
        if (textFlowLabels != null){
            for (Label textFlowLabel: textFlowLabels){
                textFlow.getChildren().remove(textFlowLabel);
            }
            textFlowLabels = new ArrayList<Label>();
        }


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

    public Algorithm inputAlgorithms(Graph myGraph, InputAlgorithm algo, int sourceId, Label stepLabel, Group canvasGroup, List<NodeFX> circles, Group textFlow, List<Label> textFlowLabels) {
		return algo.inputAlgorithm(myGraph, sourceId, stepLabel, canvasGroup, circles, textFlow, textFlowLabels);
	}
    protected EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent mouseEvent) {
            NodeFX circle = (NodeFX) mouseEvent.getSource();
            System.out.println("Circle " + circle.id.getText());
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
                            if (Integer.parseInt(weight.getText()) < 0){
                                belFordButton.setDisable(true);
                            }
                            System.out.printf("Add edge %s to %s in graph", selectedNode.id.getText(), circle.id.getText());

                        }

                        if (addNode || (calculate && !calculated) || addEdge) {
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
                        // Add Infomation of dist
                        for (NodeFX n : circles) {
                            System.out.println("Run" + n.id.getText());
                            distances.add(n.distance);
                            n.distance.setLayoutX(n.point.x + 20);
                            n.distance.setLayoutY(n.point.y);
                            if (!canvasGroup.getChildren().contains(n.distance)) {
                                canvasGroup.getChildren().add(n.distance);
                            }
                        }
                        circle.distance.setText("Dist. : 0");
                        Algorithm algo;
                        context = new Context();
                        if (bfs) {
                            // SEED DATA
                            algo = inputAlgorithms(myGraph, new InputBFS(), Integer.parseInt(circle.id.getText()), stepLabel, canvasGroup, circles, textFlow, textFlowLabels);
                            context.setUpAlgorithm((BFS) algo, st);
                            context.play((BFS) algo);
                        } else if (dijkstra) {
                            algo = inputAlgorithms(myGraph, new InputDijkstra(), Integer.parseInt(circle.id.getText()), stepLabel, canvasGroup, circles, textFlow, textFlowLabels);
                            context.setUpAlgorithm((Dijkstra) algo, st);
                            context.play((Dijkstra) algo);
                        } else if (bf) {
                            algo = inputAlgorithms(myGraph, new InputBellmanFord(), Integer.parseInt(circle.id.getText()), stepLabel, canvasGroup, circles, textFlow, textFlowLabels);
                            context.setUpAlgorithm((BellmanFord) algo, st);
                            context.play((BellmanFord) algo);
                        }
                        // belmond ford in here
                        calculated = true;
                    }
                } else {
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
