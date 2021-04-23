package main;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
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
			
			// For every line
			for(int y=minY; y<=maxY; y++) {
				
				ArrayList<Float> startStop = findIntersectionsForTri(new Triangle(p1, p2, p3, tri.color), y);
				startStop.remove(Float.NaN);
				float xStart = Collections.min(startStop);
				float xEnd = Collections.max(startStop);
				//System.out.println("S:" + xStart + " E:" + xEnd);
				
				//System.out.println("END");
				
				for(int x=minX; x<=maxX; x++) {
					// For a line y=y* what x do we start at and end at
					if(x>=(int)xStart && x<=(int)xEnd) {
						img.setRGB(x, y, tri.color.getRGB());
					}     
				}
			}

		}
		return img;
	}
	
	
	private static ArrayList<Float> findIntersectionsForTri(Triangle tri, int yLine) {
		ArrayList<Float> startStop = new ArrayList<Float>();
		Point p1 = tri.p1;
		Point p2 = tri.p2;
		Point p3 = tri.p2;
		
		Point horizontalP1 = new Point(0, yLine, 0);
		Point horizontalP2 = new Point(1, (float) (yLine), 0);
		
		/*
		Point horizP1 = new Point(0, (float) .751, 0);
		Point horizP2 = new Point(1, (float) .75, 0);
		Point vertP1 = new Point(1, 0, 0);
		Point vertP2 = new Point(1, 1, 0);
		float xAtGivenY0 = pointSlopeAlgebraicIntersect(vertP1, vertP2, horizP1, horizP2);
		System.out.println(xAtGivenY0);
		*/
		
		float xAtGivenY1 = LineIntersectionX(p1, p2, horizontalP1, horizontalP2);
		float xAtGivenY2 = LineIntersectionX(p2, p3, horizontalP1, horizontalP2);
		float xAtGivenY3 = LineIntersectionX(p3, p1, horizontalP1, horizontalP2);
		System.out.println(xAtGivenY1);
		System.out.println(xAtGivenY2);
		System.out.println(xAtGivenY3);
		System.out.println("END1");
		
		
		
		startStop.add( xAtGivenY1);
		startStop.add( xAtGivenY2);
		startStop.add( xAtGivenY3);
		
		return startStop;
	}
	
	
	// Takes in the line made by points AB and line CD and returns the x coordinate of their intersection.
	// This will return NaN if lines are parallel
	private static float pointSlopeAlgebraicIntersect(Point a, Point b, Point c, Point d) {
		float intersectX = (float) 0.0;
		float ax = a.x;
		float ay = a.y;
		float bx = b.x;
		float by = b.y;
		float cx = c.x;
		float cy = c.y;
		float dx = d.x;
		float dy = d.y;
		
		intersectX = ((((cy - dy)/(cx - dx))*cx)-cy+ay-((ay - by)/(ax - bx))*ax)/
							(((cy - dy)/(cx - dx)) - ((ay - by)/(ax - bx)));
		
		//float intersectY = ((ay - by)/(ax - bx))*(intersectX-ax)+ay;
		
		return intersectX;
	}
	
	private static float LineIntersectionX(Point A, Point B, Point C, Point D)
    {
        // Line AB represented as a1x + b1y = c1
		float a1 = B.y - A.y;
        float b1 = A.x - B.x;
        float c1 = a1*(A.x) + b1*(A.y);
       
        // Line CD represented as a2x + b2y = c2
        float a2 = D.y - C.y;
        float b2 = C.x - D.x;
        float c2 = a2*(C.x)+ b2*(C.y);
       
        float determinant = a1*b2 - a2*b1;
       
        if (determinant == 0)
        {
            // The lines are parallel. This is simplified
            // by returning a pair of FLT_MAX
            return Float.MAX_VALUE;
        }
        else
        {
            float x = (b2*c1 - b1*c2)/determinant;
            float y = (a1*c2 - a2*c1)/determinant;
            return x;
        }
    }
	
	
	// BELLOW FUNCTIONS ARE NOT IN USE. JUST FOR REFERENCE
	
	// Give the points on a line and a Y value and it will give you the corresponding X
	private static int pointSlope(int x1, int x2, int y1, int y2, int inputY) {
		int returnX = 0;
		if((y1-y2) == 0) {
			return 0+x1;
		}
		returnX = (inputY-y1)*((x1-x2)/(y1-y2))+x1;
		return returnX;
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
















