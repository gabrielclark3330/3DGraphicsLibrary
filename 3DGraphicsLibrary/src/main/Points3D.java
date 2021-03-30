package main;


public class Points3D {
	// A list of points where each point is an array with the [x,y,z] coordinates
	private float[][] points;

	public Points3D(float[][] points) {
		this.points = points;
	}
	
	public float[][] getPoints() {
		return this.points;
	}
	
	public void addPoint(float[] point) {
		int length = this.points.length;
		float[][] temporaryArr = new float[length+1][3];
		for(int i=0; i<length; i++) {
			temporaryArr[i] = this.points[i];
		}
		temporaryArr[length] = point; 
		this.points = temporaryArr;
	}
	
	public void setPoints(float[][] points) {
		this.points = points;
	}
}
