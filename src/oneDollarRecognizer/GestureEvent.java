package oneDollarRecognizer;

import java.util.EventObject;

import mygeom.Path;

public class GestureEvent extends EventObject {
	protected String templateName;
	protected double score;
	protected int nbDoigts;
	protected Path path;
	
	public GestureEvent(Object source, String templateName, double score) {
		super(source);
		this.templateName = templateName;
		this.score = score;
		this.nbDoigts = 0;
		this.path = new Path();
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
	
	public void setPath(Path path) {
		this.path = path;
	}
	
	public Path getPath() {
		return this.path;
	}
}
