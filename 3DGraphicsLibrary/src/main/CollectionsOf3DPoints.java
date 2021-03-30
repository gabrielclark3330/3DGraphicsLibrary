package main;

public class CollectionsOf3DPoints {
	
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

	public CollectionsOf3DPoints() {
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
	
	public static Points3D getCubePoints() {
		/*
		float[][] cubeArr = new float[][] {
			{1,1,0},
			{1,0,0},
			{0,1,0},
			{0,0,0},
			{1,1,1},
			{1,0,1},
			{0,1,1},
			{0,0,1}
		};
		*/
		Points3D cube = new Points3D(cubePoints);
		return cube;
	}
	
	public static void setPoints(float[][] points) {
		cubePoints = points;
	}
}
