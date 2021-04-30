package main;

public class CollectionsOfPoints {
	// This are the points needed to form a cube.
    // The points are stored in an array for easy access.
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

	// Setter for the cube points. This method is needed to slowly change the
    // points placement in complicated scenes.
	public static void setCubePoints(float[][] points) {
		cubePoints = points;
	}
}
