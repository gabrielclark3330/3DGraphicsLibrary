package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;

public class TriangleModels {
	public static ArrayList<Triangle> pyramid;
	
	public TriangleModels() {
		pyramid = getPiramid();
	}
	
	public static ArrayList<Triangle> getPiramid() {
		ArrayList<Triangle> pyramid = new ArrayList<Triangle>();
		
		pyramid.add(new Triangle(new Point(100, 100, 100),
	            new Point(-100, -100, 100),
	            new Point(-100, 100, -100),
	            Color.WHITE));
		pyramid.add(new Triangle(new Point(100, 100, 100), 
								 new Point(-100, -100, 100), 
								 new Point(100, -100, -100), 
								 Color.RED));
		pyramid.add(new Triangle(new Point(-100, 100, -100),
		                      new Point(100, -100, -100),
		                      new Point(100, 100, 100),
		                      Color.GREEN));
		pyramid.add(new Triangle(new Point(-100, 100, -100),
		                      new Point(100, -100, -100),
		                      new Point(-100, -100, 100),
		                      Color.BLUE));
		return pyramid;
	}
	
}
