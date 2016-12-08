package oneDollarRecognizer;

import java.awt.AWTEvent;

public class GestureEvent extends AWTEvent {
	protected String templateName;
	protected double score;
	
	public GestureEvent(Object source, int id, String templateName, double score) {
		super(source, id);
		this.templateName = templateName;
		this.score = score;
	}

	public String getTemplateName() {
		return templateName;
	}

	public double getScore() {
		return score;
	}
}
