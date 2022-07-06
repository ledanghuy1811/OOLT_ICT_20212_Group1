package step;

import graphics.CanvasController;
import graphics.NodeFX;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.animation.*;
import javafx.scene.control.TextArea;
import javafx.util.Duration;
import javafx.scene.paint.Color;
import java.util.List;

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

	public StepInfo(int nodeSource, int nodeTarget, double weight, String detail, Label stepLabel, Group canvasGroup, List<NodeFX> circles, SequentialTransition st, boolean isSwap, TextArea textFlow) {
		this.nodeSource = nodeSource;
		this.nodeTarget = nodeTarget;
		this.weight = weight;
		this.detail = detail;
		FillTransition ft = new FillTransition(Duration.millis(CanvasController.timeAnimation), circles.get(nodeSource));
		if (circles.get(nodeSource).getFill() == Color.BLACK) {
			ft.setToValue(Color.CHOCOLATE);
		}

		FadeTransition fd = new FadeTransition(Duration.millis(10), stepLabel);
		fd.setOnFinished(e -> {
			stepLabel.setText(this.getDetail());
		});
		fd.onFinishedProperty();
		st.getChildren().add(fd);

		st.getChildren().add(ft);
		FillTransition ft1 = new FillTransition(Duration.millis(CanvasController.timeAnimation), circles.get(nodeTarget));
		if (circles.get(nodeTarget).getFill() == Color.FORESTGREEN){
			ft1.setToValue(Color.PURPLE);
		}
		else {
			ft1.setToValue(Color.FORESTGREEN);
		}
		ft1.setOnFinished(ev -> {
			if (isSwap){
				circles.get(nodeTarget).distance.setText("Dist. : " + weight);
			}
			ft1.setToValue(Color.BLACK);

		});
		ft1.onFinishedProperty();
		st.getChildren().add(ft1);
		FadeTransition fd2 = new FadeTransition(Duration.millis(10), textFlow);
		fd2.setOnFinished(ev -> {
			textFlow.appendText(this.getDetail() + "\n");
		});
		fd2.onFinishedProperty();
		st.getChildren().add(fd2);
		st.setOnFinished(ev -> {
			for (NodeFX n : circles) {
				FillTransition ft2 = new FillTransition(Duration.millis(CanvasController.timeAnimation), n);
				ft2.setToValue(Color.BLACK);
				ft2.play();
			}
			FillTransition ft3 = new FillTransition(Duration.millis(CanvasController.timeAnimation), circles.get(nodeSource));
			ft3.setToValue(Color.RED);
			ft3.play();
		});
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
