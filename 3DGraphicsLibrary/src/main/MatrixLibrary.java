package main;

public class MatrixLibrary {
	
	public enum Axis {
		X,
		Y,
		Z
	}
	
	// Takes in a Point, a rotation in degrees, and an 
	// axis and returns the coordinates of the point with that rotation.
	public static Point rotatePointsAboutAxis(Point inputPoint, float rotationInDegrees, Axis axis) {
		float[][] result3D = new float[3][1];
		
		float[][] rotateX = new float[][] {
			{1,0,0},
			{0,(float) Math.cos(Math.toRadians(rotationInDegrees)),(float) - Math.sin(Math.toRadians(rotationInDegrees))},
			{0,(float) Math.sin(Math.toRadians(rotationInDegrees)),(float) Math.cos(Math.toRadians(rotationInDegrees))}
		};
		float[][] rotateY = new float[][] {
			{(float) Math.cos(Math.toRadians(rotationInDegrees)),0,(float) Math.sin(Math.toRadians(rotationInDegrees))},
			{0,1,0},
			{(float) -Math.sin(Math.toRadians(rotationInDegrees)),0,(float) Math.cos(Math.toRadians(rotationInDegrees))}
		};
		float[][] rotateZ = new float[][] {
			{(float) Math.cos(Math.toRadians(rotationInDegrees)),(float) - Math.sin(Math.toRadians(rotationInDegrees)),0},
			{(float) Math.sin(Math.toRadians(rotationInDegrees)),(float) Math.cos(Math.toRadians(rotationInDegrees)),0},
			{0,0,1},
		};
		
		float[][] inputVector = new float[][] {
			{inputPoint.x},
			{inputPoint.y},
			{inputPoint.z}
		};
		
		if(axis.equals(Axis.X)) {
			result3D = multiplyMatrices(rotateX, inputVector);
		}else if(axis.equals(Axis.Y)) {
			result3D = multiplyMatrices(rotateY, inputVector);
		}else if(axis.equals(Axis.Z)) {
			result3D = multiplyMatrices(rotateZ, inputVector);
		}
		
		Point resultPoint = new Point(result3D[0][0], result3D[1][0], result3D[2][0]);
		return resultPoint;
	}
	
	// Moves a point along a given axis.
	public static Point movePointAlongAxis(Point inputPoint, float movement, Axis axis) {
		float[][] result3D = new float[3][1];
		
		if(axis.equals(Axis.X)) {
			result3D = new float[][] {
				{inputPoint.x+movement},
				{inputPoint.y},
				{inputPoint.z}
			};
		}else if(axis.equals(Axis.Y)) {
			result3D = new float[][] {
				{inputPoint.x},
				{inputPoint.y+movement},
				{inputPoint.z}
			};
		}else if(axis.equals(Axis.Z)) {
			result3D = new float[][] {
				{inputPoint.x},
				{inputPoint.y},
				{inputPoint.z+movement}
			};
		}
		
		Point resultPoint = new Point(result3D[0][0], result3D[1][0], result3D[2][0]);
		return resultPoint;
	}
	
	// Takes in a point and projects it orthographically.
	public static Point orthographicProjection(Point inputPoint) {
		float[][] result3D = new float[3][1];
		float[][] projectionMatrix = new float[][] {
			{1,0,0},
			{0,1,0}
		};
		
		float[][] inputVector = new float[][] {
			{inputPoint.x},
			{inputPoint.y},
			{inputPoint.z}
		};
		
		result3D = multiplyMatrices(projectionMatrix, inputVector);
		
		Point resultPoint = new Point(result3D[0][0], result3D[1][0], (float) 0);
		return resultPoint;
	}
	
	// Takes a point and applies a perspective projection to it.
	public static Point perspectiveProjection(Point inputPoint, float fov, float nearClip, float farClip) {
		float[][] result3D = new float[3][1];
		
		float S = (float) (1/(Math.tan( (fov/2)*(Math.PI/180) )));
		float clipping = (float) (-farClip/(farClip-nearClip));
		float[][] perspectiveMatrix = new float[][] {
			{S,0,0,0},
			{0,S,0,0},
			{0,0,clipping,-1},
			{0,0,clipping,0}
		};
		
		float[][] inputVector = new float[][] {
			{inputPoint.x},
			{inputPoint.y},
			{inputPoint.z}
		};
		
		result3D = multiplyMatrices(perspectiveMatrix, inputVector);
		
		Point resultPoint = new Point(result3D[0][0], result3D[1][0], (float) 0);
		return resultPoint;
	}
	
	// Takes matrixes formated as 2D arrays and multiplies 
	// them together with matrix multiplication rules.
	public static float[][] multiplyMatrices(float[][] firstMatrix, float[][] secondMatrix) {
		float[][] result = new float[firstMatrix.length][secondMatrix[0].length];

	    for (int row = 0; row < result.length; row++) {
	        for (int col = 0; col < result[row].length; col++) {
	            result[row][col] = multiplyMatricesCell(firstMatrix, secondMatrix, row, col);
	        }
	    }

	    return result;
	}
	
	public static float multiplyMatricesCell(float[][] firstMatrix, float[][] secondMatrix, int row, int col) {
		float cell = 0;
	    for (int i = 0; i < secondMatrix.length; i++) {
	        cell += firstMatrix[row][i] * secondMatrix[i][col];
	    }
	    return cell;
	}
	
	// Overloaded methods for different dimension matrixes
	public static float[] scalarMultiplication(float[] inputMatrix, float scaleFactor) {
		
		float[] result = new float[inputMatrix.length];

	    for (int col = 0; col < result.length; col++) {
            result[col] = inputMatrix[col] * scaleFactor;
	    }

	    return result;
	}

	// Overloaded methods for different dimension matrixes
	public static float[][] scalarMultiplication(float[][] inputMatrix, float scaleFactor) {
		
		float[][] result = new float[inputMatrix.length][inputMatrix[0].length];
	
	    for (int row = 0; row < result.length; row++) {
	        for (int col = 0; col < result[row].length; col++) {
	            result[row][col] = inputMatrix[row][col] * scaleFactor;
	        }
	    }
	
	    return result;
	}
	
	// Overloaded methods for different dimension matrixes
	public static float[] elementwiseAddition(float[] inputMatrix, float scaleFactor) {
		
		float[] result = new float[inputMatrix.length];

	    for (int col = 0; col < result.length; col++) {
            result[col] = inputMatrix[col] + scaleFactor;
	    }

	    return result;
	}

	// Overloaded methods for different dimension matrixes
	public static float[][] elementwiseAddition(float[][] inputMatrix, float scaleFactor) {
		
		float[][] result = new float[inputMatrix.length][inputMatrix[0].length];
	
	    for (int row = 0; row < result.length; row++) {
	        for (int col = 0; col < result[row].length; col++) {
	            result[row][col] = inputMatrix[row][col] + scaleFactor;
	        }
	    }
	
	    return result;
	}

}


