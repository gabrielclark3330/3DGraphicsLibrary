package main;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

public class RenderToScreenMethods {
	
	// Creates a buffered img with the correct coloring and placement of the passed triangles
	public static BufferedImage renderColoredTris(ArrayList<Triangle> triangles, int width, int height) {
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		
		// Creates a starter deapth buffer with really far away values
		
		
		for(Triangle tri : triangles) {
			Point p1 = tri.p1;
			Point p2 = tri.p2;
			Point p3 = tri.p3;
			
			// Moves the points so they are centered in screen without adjusting origin.
			p1.x += width/2;
			p1.y += height/2;
			p2.x += width/2;
			p2.y += height/2;
			p3.x += width/2;
			p3.y += height/2;
			
			// Finds the bounds of the smallest box that the triangle can reside in.
			int minX = (int) Math.min(Math.min(p1.x, p2.x), p3.x);
			int maxX = (int) Math.max(Math.max(p1.x, p2.x), p3.x);
			int minY = (int) Math.min(Math.min(p1.y, p2.y), p3.y);
			int maxY = (int) Math.max(Math.max(p1.y, p2.y), p3.y);
			
			Point A = new Point(0,1,0);
			Point B = new Point(0,4,0);
			Point C = new Point(1,8,0);
			Point D = new Point(1,4,0);
			
			
			System.out.println(pointSlopeAlgebraic(A, B, C, D));
			
			// For every line
			for(int y=minY; y<=maxY; y++) {
				int[] startStop = findLinesIntersected(y, minX, maxX, minY, maxY, new Triangle(p1, p2, p3, tri.color));
				//System.out.println("start: " + startStop[0] + " stop: " + startStop[1]);
				int xStart = startStop[0];
				int xEnd = startStop[1];
				for(int x=minX; x<=maxX; x++) {
					// For a line y=y* what x do we start at and end at
					if(x>=xStart && x<=xEnd) {
						img.setRGB(x, y, tri.color.getRGB());
					}		            
				}
			}
			
		}
		return img;
	}
	
	// Give the points on a line and a Y value and it will give you the corresponding X
	private static int pointSlope(int x1, int x2, int y1, int y2, int inputY) {
		int returnX = 0;
		if((y1-y2) == 0) {
			return 0+x1;
		}
		returnX = (inputY-y1)*((x1-x2)/(y1-y2))+x1;
		return returnX;
	}
	
	// Takes in the line made by points AB and line CD and returns the x coordinate of their intersection.
	// This will return NaN if lines are parallel
	private static float pointSlopeAlgebraic(Point a, Point b, Point c, Point d) {
		float intersectX = (float) 0.0;
		float ax = a.x;
		float ay = a.y;
		float bx = b.x;
		float by = b.y;
		float cx = c.x;
		float cy = c.y;
		float dx = d.x;
		float dy = d.y;
		
		intersectX = ((((cy - dy)/(cx - dx))*cx)-cy+ay-((ay - by)/(ax - bx))*ax)/(((cy - dy) /
									(cx - dx)) - ((ay - by)/(ax - bx)));
		
		//float intersectY = ((ay - by)/(ax - bx))*(intersectX-ax)+ay;
		
		return intersectX;
	}
	
	
	// Given a y value, this function will find the start and stop fill points .
	private static int[] findLinesIntersected(int yLine, int minX, int maxX, int minY, int maxY, Triangle tri) {
		int[] startStop = new int[2];
		// The three lines are
		// p1 to p2
		// p2 to p3
		// p3 to p1
		
		int xStart = Integer.MAX_VALUE;
		int xEnd = Integer.MIN_VALUE;
		
		// For a line y=y* what x do we start at and end at
		for(int x=minX; x<=maxX; x++) {
			
			// Using the point slop equation we are going to check when we intersect a given line.
			// This is done by plugging in your points that form the line and your yLine and checking when the xOutput is equal to xPosition.
			// This process is then done for each line and the intersections become the xStart and xEnd.
			int xAtGivenY1 = pointSlope((int) tri.p1.x, (int) tri.p2.x, (int) tri.p1.y, (int) tri.p2.y, yLine);
			int xAtGivenY2 = pointSlope((int) tri.p2.x, (int) tri.p3.x, (int) tri.p2.y, (int) tri.p3.y, yLine);
			int xAtGivenY3 = pointSlope((int) tri.p3.x, (int) tri.p1.x, (int) tri.p3.y, (int) tri.p1.y, yLine);
			
			if(xAtGivenY1 == x) {
				//System.out.println(xAtGivenY1);
				if(xStart == Integer.MAX_VALUE) {
					xStart = x;
				} else {
					xEnd = x;
					startStop[0] = xStart;
					startStop[1] = xEnd;
					return startStop;
				}
			}
			
			if(xAtGivenY2 == x) {
				//System.out.println(xAtGivenY2);
				if(xStart == Integer.MAX_VALUE) {
					xStart = x;
				} else {
					xEnd = x;
					startStop[0] = xStart;
					startStop[1] = xEnd;
					return startStop;
				}
			}
			
			if(xAtGivenY3 == x) {
				//System.out.println(xAtGivenY3);
				if(xStart == Integer.MAX_VALUE) {
					xStart = x;
				} else {
					xEnd = x;
					startStop[0] = xStart;
					startStop[1] = xEnd;
					return startStop;
				}
			}
		}
		
		startStop[0] = xStart;
		startStop[1] = xEnd;
		return startStop;
	}
	
}
















