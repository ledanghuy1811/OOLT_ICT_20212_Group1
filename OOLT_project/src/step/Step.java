package step;

import java.util.ArrayList;

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
}
