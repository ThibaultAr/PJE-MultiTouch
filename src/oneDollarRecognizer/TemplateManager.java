package oneDollarRecognizer;

/**
 * @author <a href="mailto:gery.casiez@lifl.fr">Gery Casiez</a>
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import mygeom.Tuple2;

public class TemplateManager
{
	private Vector<Template> theTemplates;
	private Document document;
	private Element racine;
	private String fileName;
	
	TemplateManager(String filename) {
		theTemplates = new Vector<Template>();
		this.fileName = filename;
		loadFile(filename);
	}	

	void loadFile(String filename) {
		File fXmlFile = new File(filename);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		Document doc;
		
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("template");
			for (int i = 0; i < nList.getLength(); i++) {
				Node nNode = nList.item(i);
				Element eElement = (Element) nNode;
				String name = eElement.getAttribute("name").toString();
				
				Vector<Tuple2> pts = new Vector<Tuple2>();
				
				NodeList nListPoints = eElement.getElementsByTagName("Point");
				for (int j = 0; j < nListPoints.getLength(); j++) {
					Node nNodepoints = nListPoints.item(j);
					Element eElementPoint = (Element) nNodepoints;
					double x = Double.parseDouble(eElementPoint.getAttribute("x").toString());
					double y = Double.parseDouble(eElementPoint.getAttribute("y").toString());
					pts.add(new Tuple2(x, y));
				}
				theTemplates.add(new Template(name, pts));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public void AddTemplate( String name, Tuple2 [] points)
	{
		Vector<Tuple2> pts = new Vector<Tuple2>();
		for (int i = 0; i < points.length; i++) {
			pts.add(points[i]);
		}
		theTemplates.add(new Template(name, pts));
	}
	
	Vector<Template> getTemplates() {
		return theTemplates;
	}
	
	public void writeRawPoints2XMLFile(String name, Vector<Tuple2> points) {
		try {
		FileWriter fstream = new FileWriter("out.xml", true);
		BufferedWriter out = new BufferedWriter(fstream);
		out.write("	<template name=\"" + name + "\" nbPts=\"" + points.size() +"\">\n");
		for (int i=0; i<points.size();i++) {
			out.write("		<Point x=\"" + (int)points.get(i).getX() + "\" y=\"" +
					(int)points.get(i).getY()  +"\"/>\n");
		}
		
		out.write("	</template>\n");
		out.close();
		} catch (Exception e){//Catch exception if any
		      System.err.println("Error: " + e.getMessage());
	    }

	}	
	
}