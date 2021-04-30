package main;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class RenderToScreenMethods {
	
	// Creates a buffered img with the correct coloring and placement of the passed triangles
	public static BufferedImage renderColoredTris(ArrayList<Triangle> triangles, int width, int height) {
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		
		// Creates a starter deapth buffer with really far away values
		float[] zBuffer = new float[width * height];
		// this z-buffer is commonly used to keep track of each pixle's depth. 
		// To properly use this we will initialize it with very distant depths.
		for (int i=0; i<zBuffer.length; i++) {
			zBuffer[i] = Float.NEGATIVE_INFINITY;
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
			int maxX = (int) Math.max(Math.max(p1.x, p2.x), p3.x);
			int minY = (int) Math.min(Math.min(p1.y, p2.y), p3.y);
			int maxY = (int) Math.max(Math.max(p1.y, p2.y), p3.y);
			
			// I can't get my own algorithm working so I am using Dr. Rogach's again
			float triangleArea =
		       (p1.y - p3.y) * (p2.x - p3.x) + (p2.y - p3.y) * (p3.x - p1.x);

		    for (int y = minY; y <= maxY; y++) {
		        for (int x = minX; x <= maxX; x++) {
		        	float b1 = 
		              ((y - p3.y) * (p2.x - p3.x) + (p2.y - p3.y) * (p3.x - x)) / triangleArea;
		        	float b2 =
		              ((y - p1.y) * (p3.x - p1.x) + (p3.y - p1.y) * (p1.x - x)) / triangleArea;
		        	float b3 =
		              ((y - p2.y) * (p1.x - p2.x) + (p1.y - p2.y) * (p2.x - x)) / triangleArea;
		            if (b1 >= 0 && b1 <= 1 && b2 >= 0 && b2 <= 1 && b3 >= 0 && b3 <= 1) {
		            	float pixleDeapth = b1*p1.z + b2*p2.z + b3*p3.z;
		            	int index = y* img.getWidth() + x;
		            	if(zBuffer[index] < pixleDeapth) {
		            		img.setRGB(x, y, tri.color.getRGB());
		            		zBuffer[index] = pixleDeapth;
		            	}
		            }
		        }
		    }

		}
		return img;
	}
	
	/*
			// For every line
			for(int y=minY; y<=maxY; y++) {
				
				ArrayList<Float> startStop = findIntersectionsForTri(new Triangle(p1, p2, p3, tri.color), y);
				startStop.remove(Float.NaN);
				float xStart = Collections.min(startStop);
				float xEnd = Collections.max(startStop);
				
				for(int x=minX; x<=maxX; x++) {
					// For a line y=y* what x do we start at and end at
					if(x>=(int)xStart && x<=(int)xEnd) {
						img.setRGB(x, y, tri.color.getRGB());
					}     
				}
			}

	 */
	
	private static void shadingBasedOnLight() {
		Point norm = new Point(
		         ab.y * ac.z - ab.z * ac.y,
		         ab.z * ac.x - ab.x * ac.z,
		         ab.x * ac.y - ab.y * ac.x
		    );
		    double normalLength =
		        Math.sqrt(norm.x * norm.x + norm.y * norm.y + norm.z * norm.z);
		    norm.x /= normalLength;
		    norm.y /= normalLength;
		    norm.z /= normalLength;
	}
	
	// This is derived from a conversion from sRGB to linear RGB and isn't correct. 
	// This is an aproximation I found online.
	private static Color gradientShader(Color color, double shaderVal) {
		double redLinear = Math.pow(color.getRed(), 2.4) * shaderVal;
	    double greenLinear = Math.pow(color.getGreen(), 2.4) * shaderVal;
	    double blueLinear = Math.pow(color.getBlue(), 2.4) * shaderVal;

	    int red = (int) Math.pow(redLinear, 1/2.4);
	    int green = (int) Math.pow(greenLinear, 1/2.4);
	    int blue = (int) Math.pow(blueLinear, 1/2.4);
		return new Color(red, green, blue);
	}
	
	private static ArrayList<Float> findIntersectionsForTri(Triangle tri, int yLine) {
		ArrayList<Float> startStop = new ArrayList<Float>();
		Point p1 = tri.p1;
		Point p2 = tri.p2;
		Point p3 = tri.p2;
		
		Point horizontalP1 = new Point(0, yLine, 0);
		Point horizontalP2 = new Point(1, (float) (yLine+.00001), 0);
		
		// Test Data
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
	
}
















