package main;

public class MatrixLibrary {
	
	public static float[] rotatePointsAboutAxis(float[] inputPoint, float rotationInDegrees, String axis) {
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
			{inputPoint[0]},
			{inputPoint[1]},
			{inputPoint[2]}
		};
		
		if(axis.toLowerCase()=="x") {
			result3D = multiplyMatrices(rotateX, inputVector);
		}else if(axis.toLowerCase()=="y") {
			result3D = multiplyMatrices(rotateY, inputVector);
		}else if(axis.toLowerCase()=="z") {
			result3D = multiplyMatrices(rotateZ, inputVector);
		}
		
		float[] resultFlat = new float[3];
		resultFlat[0] = result3D[0][0];
		resultFlat[1] = result3D[1][0];
		resultFlat[2] = result3D[2][0];
		return resultFlat;
	}
	
	// Takes in a point and projects it othographicly
	public static float[] orthographicProjection(float[] inputPoint) {
		float[][] result3D = new float[3][1];
		float[][] projectionMatrix = new float[][] {
			{1,0,0},
			{0,1,0}
		};
		
		float[][] inputVector = new float[][] {
			{inputPoint[0]},
			{inputPoint[1]},
			{inputPoint[2]}
		};
		
		result3D = multiplyMatrices(projectionMatrix, inputVector);
		
		float[] resultFlat = new float[2];
		resultFlat[0] = result3D[0][0];
		resultFlat[1] = result3D[1][0];
		return resultFlat;
	}
	
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
	public static float[] scalorMultiplication(float[] inputMatrix, float scaleFactor) {
		
		float[] result = new float[inputMatrix.length];

	    for (int col = 0; col < result.length; col++) {
            result[col] = inputMatrix[col] * scaleFactor;
	    }

	    return result;
	}

	// Overloaded methods for different dimension matrixes
	public static float[][] scalorMultiplication(float[][] inputMatrix, float scaleFactor) {
		
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


