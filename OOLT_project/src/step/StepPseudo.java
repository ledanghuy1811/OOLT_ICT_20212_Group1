package step;

import java.util.ArrayList;
import java.util.List;

import graphics.CanvasController;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.util.Duration;
import utils.Utils;

public class StepPseudo {
    private ArrayList<String> pseudo = new ArrayList<String>();
    private int index;

    public StepPseudo() {

    }

    public ArrayList<String> getPseudo() {
        return this.pseudo;
    }
    public void setPseudo(ArrayList<String> pseudo, Group textFlow, List<Label> textFlowLabels) {
        this.pseudo = pseudo;
        int idx = 0;
        for (String ps: pseudo){
            Label aLabel = new Label(ps);
            aLabel.setPrefWidth(Utils.slidePanelSizeX);
            aLabel.setPrefHeight(Utils.slidePanelSizeY);
            textFlowLabels.add(aLabel);
            aLabel.setLayoutY(idx * Utils.slidePanelSizeY);
            textFlow.getChildren().add(aLabel);
            idx++;
        }
    }
    public int getIndex() {
        return this.index;
    }
    public void setIndex(int index, List<Label> textFlowLabels, SequentialTransition st) {
        Label idxLabel = textFlowLabels.get(index);
        FadeTransition fd = new FadeTransition(Duration.millis(Utils.stepAnimation), idxLabel);
        fd.setOnFinished(e -> {
            int idx = 0;
            for (Label textFlowLabel: textFlowLabels){
                if (idx != index){
                    textFlowLabel.setStyle("-fx-background-color: white");
                }
                idx++;
            }
            idxLabel.setStyle("-fx-background-color:  #FFDEAD");
        });
        fd.onFinishedProperty();
        st.getChildren().add(fd);
        this.index = index;
    }

    public void printPseudo() {
        System.out.println(this.getPseudo().get(this.getIndex()));
    }
}