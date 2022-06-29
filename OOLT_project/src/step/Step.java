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
	
	//constructor
	public Step() {
		
	}
	
	//getter
	public ArrayList<StepInfo> getStepDetail() {
		return stepDetail;
	}
	
	// method
	public void addStep(int nodeSource, int nodeTarget, double weight, String detail) {
		this.stepDetail.add(new StepInfo(nodeSource, nodeTarget, weight, detail));
	}

	public void addStep(int nodeSource, int nodeTarget, double weight, String detail, Label stepLabel, Group canvasGroup, List<NodeFX> circles, SequentialTransition st, boolean isSwap, TextArea textFlow) {
		System.out.println(circles);
		this.stepDetail.add(new StepInfo(nodeSource, nodeTarget, weight, detail, stepLabel, canvasGroup, circles, st, isSwap, textFlow));
	}
}
