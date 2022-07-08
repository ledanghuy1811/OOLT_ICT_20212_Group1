package step;

import graphics.NodeFX;
import javafx.animation.SequentialTransition;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.util.ArrayList;
import java.util.List;

public class Step {
	// attribute
	private ArrayList<StepInfo> stepDetail = new ArrayList<StepInfo>();
	private StepPseudo pseudoStep = new StepPseudo();
	//constructor
	public Step() {
		
	}

	//getter
	public ArrayList<StepInfo> getStepDetail() {
		return stepDetail;
	}
	public StepPseudo getPseudoStep() {
		return this.pseudoStep;
	}

	public void addStep(int nodeSource, int nodeTarget, double weight, String detail, Label stepLabel, Group canvasGroup, List<NodeFX> circles, SequentialTransition st, boolean isSwap, Group textFlow) {
		this.stepDetail.add(new StepInfo(nodeSource, nodeTarget, weight, detail, stepLabel, canvasGroup, circles, st, isSwap, textFlow));
	}
}
