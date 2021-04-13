package main;

public class CollectionsOfPoints {
	
	public static float[][] cubePoints = new float[][] {
		{1,1,0},
		{1,0,0},
		{0,1,0},
		{0,0,0},
		{1,1,1},
		{1,0,1},
		{0,1,1},
		{0,0,1}
	};

	public CollectionsOfPoints() {
		cubePoints = new float[][] {
			{1,1,0},
			{1,0,0},
			{0,1,0},
			{0,0,0},
			{1,1,1},
			{1,0,1},
			{0,1,1},
			{0,0,1}
		};
	}
	
	public static Point getCubePoints() {
		Point cube = new Point(cubePoints);
		return cube;
	}
	
	public static void setCubePoints(float[][] points) {
		cubePoints = points;
	}
}
