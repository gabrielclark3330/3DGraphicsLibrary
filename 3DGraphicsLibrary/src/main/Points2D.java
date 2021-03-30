package main;

import java.util.Arrays;

public class Points2D {
	// A list of points where each point is an array with the [x,y] coordinates
	private float[][] points;

	public Points2D(float[][] points) {
		this.points = points;
	}
	
	public float[][] getPoints() {
		return this.points;
	}
	
	public void addPoint(float[] point) {
		float[][] temporaryArr = new float[point.length+1][2];
		temporaryArr = Arrays.copyOf(this.points, temporaryArr.length);
		this.points = temporaryArr;
	}
	
	public void setPoints(float[][] points) {
		this.points = points;
	}
}
