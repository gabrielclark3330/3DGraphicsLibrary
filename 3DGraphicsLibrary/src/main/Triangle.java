package main;

import java.awt.Color;

// Class that holds triangles for the rendering pipline. 
public class Triangle {
	// Note: these points are listed in counterClockwise order.
	Point p1;
	Point p2;
	Point p3;
	Color color;
	
	Triangle(Point p1, Point p2, Point p3, Color color) {
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		this.color = color;
	}
	
}
