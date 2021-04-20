package main;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

public class RenderToScreenMethods {
	
	// Creates a buffered img with the correct coloring and placement of the passed triangles
	public static BufferedImage renderColoredTris(ArrayList<Triangle> triangles, int width, int height) {
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		// Creates a starter deapth buffer with really far away values
		double[] zBuffer = new double[img.getWidth() * img.getHeight()];
		for (int q = 0; q < zBuffer.length; q++) {
		    zBuffer[q] = Double.NEGATIVE_INFINITY;
		}
		
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
			int minY = (int) Math.min(Math.min(p1.y, p2.y), p3.y);
			int maxX = (int) Math.max(Math.max(p1.x, p2.x), p3.x);
			int maxY = (int) Math.max(Math.max(p1.y, p2.y), p3.y);
			
			
			// Credit for this algorithm goes to Dr. Rogach
			// I couldn't figure out how to do filling manually until reading a paper by him.
			// I just used his algorithm while developing but will replace it with mine below when it is finished.
			double Area =
				       (p1.y - p3.y) * (p2.x - p3.x) + (p2.y - p3.y) * (p3.x - p1.x);

			for(int y=minY; y<=maxY; y++) {
				for(int x=minX; x<=maxX; x++) {
					double b1 = 
		              ((y - p3.y) * (p2.x - p3.x) + (p2.y - p3.y) * (p3.x - x)) / Area;
		            double b2 =
		              ((y - p1.y) * (p3.x - p1.x) + (p3.y - p1.y) * (p1.x - x)) / Area;
		            double b3 =
		              ((y - p2.y) * (p1.x - p2.x) + (p1.y - p2.y) * (p2.x - x)) / Area;
		            if (b1 >= 0 && b1 <= 1 && b2 >= 0 && b2 <= 1 && b3 >= 0 && b3 <= 1) {
		            	double depth = b1 * p1.z + b2 * p2.z + b3 * p3.z;
		            	int zIndex = y * img.getWidth() + x;
		    		    if (zBuffer[zIndex] < depth) {
		    		        img.setRGB(x, y, tri.color.getRGB());
		    		        zBuffer[zIndex] = depth;
		    		    }
		                //img.setRGB(x, y, tri.color.getRGB());
		            }
					
				}
			}
			
			// EXPERIMENTAL
			
		    
			
		}
		
		return img;
	}
	
	// Give the points on a line and a Y value and it will give you the corresponding X
	private int pointSlope(int x1, int x2, int y1, int y2, int inputY) {
		int returnX = 0;
		returnX = (inputY-y1)*((x1-x2)/(y1-y2))+x1;
		return returnX;
	}
	
	// Given a y value, this function will give two lines in points that represent what two lines the y=1 line intersects.
	private Point[] findLinesIntersected(int yLine, int minX, int maxX, int minY, int maxY, Triangle tri) {
		Point[] returnLines = new Point[4];
		ArrayList<Point> allPoints = new ArrayList<>();
		allPoints.add(tri.p1);
		allPoints.add(tri.p2);
		allPoints.add(tri.p3);
		
		// We want the farthest left point and the lines that it has connected to it.
		// The order will be the line that is highest first and then the lowest one.
		
		// Find leftmost Point
		Point leftMostPoint = new Point(0,0,0);
		float lowestX = Float.POSITIVE_INFINITY;
		for(Point point:allPoints) {
			if(point.x<lowestX) {
				lowestX = point.x;
				leftMostPoint = point;
			}
		}
		allPoints.remove(leftMostPoint);
		
		// Take other two points and sort by Y. The highest will come next and the lowest will follow
		Point unknownP1 = allPoints.remove(0);
		Point unknownP2 = allPoints.remove(0);
		Point highestRemainingPoint = (unknownP1.y >= unknownP2.y) ? unknownP1 : unknownP2;
		Point lowestRemainingPoint = (unknownP1.y < unknownP2.y) ? unknownP1 : unknownP2;
		
		returnLines[0] = leftMostPoint;
		returnLines[1] = highestRemainingPoint;
		returnLines[2] = lowestRemainingPoint;
		
		if(yLine>=leftMostPoint.y) {
			// Line one or the starting line of the fill
			returnLines[0] = leftMostPoint;
			returnLines[1] = highestRemainingPoint;
			// Line two or the ending line of the fill
			returnLines[2] = leftMostPoint;
			returnLines[3] = lowestRemainingPoint;
		} else {
			// Line one or the starting line of the fill
			returnLines[0] = leftMostPoint;
			returnLines[1] = lowestRemainingPoint;
			// Line two or the ending line of the fill
			returnLines[2] = leftMostPoint;
			returnLines[3] = highestRemainingPoint;
		}
		return returnLines;
	}
	
}
















