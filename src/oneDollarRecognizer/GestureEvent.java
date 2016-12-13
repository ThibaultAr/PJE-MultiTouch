package oneDollarRecognizer;

import java.util.EventObject;

public class GestureEvent extends EventObject {
	protected String templateName;
	protected double score;
	protected int nbDoigts;
	
	public GestureEvent(Object source, String templateName, double score) {
		super(source);
		this.templateName = templateName;
		this.score = score;
		this.nbDoigts = 0;
	}

	public String getTemplateName() {
		return templateName;
	}

	public double getScore() {
		return score;
	}
	
	public void setNbDoigts(int n) {
		this.nbDoigts = n; 
	}
	
	public int getNbDoigts() {
		return this.nbDoigts;
	}
	
	public void setSource(Object o) {
		this.source = o;
	}
}
