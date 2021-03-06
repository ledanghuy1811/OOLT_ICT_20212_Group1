package step;

import java.util.ArrayList;

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
	
	// method
	public void addStep(int nodeSource, int nodeTarget, double weight, String detail) {
		this.stepDetail.add(new StepInfo(nodeSource, nodeTarget, weight, detail));
	}
}
