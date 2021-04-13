package main;

import java.awt.Color;
import java.util.ArrayList;

public class TriangleModels {
	// Creates a arraylist that holds the triangles for a pyramid.
	public static ArrayList<Triangle> pyramid;
	
	public TriangleModels() {
		pyramid = getPyramid();
	}
	
	public static ArrayList<Triangle> getPyramid() {
		ArrayList<Triangle> pyramid = new ArrayList<Triangle>();
		
		Triangle t1 = new Triangle(new Point(100, 100, 100),
	            new Point(-100, -100, 100),
	            new Point(-100, 100, -100),
	            Color.WHITE);
		pyramid.add(t1);
		Triangle t2 = new Triangle(new Point(100, 100, 100), 
				 new Point(-100, -100, 100), 
				 new Point(100, -100, -100), 
				 Color.RED);
		pyramid.add(t2);
		Triangle t3 = new Triangle(new Point(-100, 100, -100),
                new Point(100, -100, -100),
                new Point(100, 100, 100),
                Color.GREEN);
		pyramid.add(t3);
		Triangle t4 = new Triangle(new Point(-100, 100, -100),
                new Point(100, -100, -100),
                new Point(-100, -100, 100),
                Color.BLUE);
		pyramid.add(t4);
		return pyramid;
	}
	
}
