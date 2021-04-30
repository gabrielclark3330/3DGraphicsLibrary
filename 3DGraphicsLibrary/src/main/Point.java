package main;

// Class for representing a point in a frendly way.
public class Point {
	float x;
	float y;
	float z;
    
    // NOTE: This is not a super efficiant way to store point info and might
    // change in the future
	public Point(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
}
