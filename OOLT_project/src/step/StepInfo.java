package step;

public class StepInfo {
	//attribute
	private int nodeSource;
	private int nodeTarget;
	private double weight;
	private String detail;
	
	//constructor
	public StepInfo() {
		
	}
	public StepInfo(int nodeSource, int nodeTarget, double weight, String detail) {
		this.nodeSource = nodeSource;
		this.nodeTarget = nodeTarget;
		this.weight = weight;
		this.detail = detail;
	}
	
	//getter
	public int getNodeSource() {
		return nodeSource;
	}
	public int getNodeTarget() {
		return nodeTarget;
	}
	public double getWeight() {
		return weight;
	}
	public String getDetail() {
		return detail;
	}
}
