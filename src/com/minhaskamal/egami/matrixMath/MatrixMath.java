package com.minhaskamal.egami.matrixMath;

public class MatrixMath {
	public static double[][] multiplyMatrixByMatrix(int[][] matrix1, double[][] matrix2){
		double[][] resultMatrix = new double [matrix1.length][matrix1[0].length];
		
		for(int i=0; i<resultMatrix.length; i++){
			for(int j=0; j<resultMatrix[0].length; j++){
				resultMatrix[i][j] = matrix1[i][j]*matrix2[i][j];
			}
		}
		
		return resultMatrix;
	}
	
	
	public static int[] addArrByArr(int[] array1, int[] array2){
		int[] resultArray = new int [array1.length];
		
		for(int i=0; i<resultArray.length; i++){
			resultArray[i] = array1[i]+array2[i];
		}
		
		return resultArray;
	}
	
	public static int[] subtracArrByArr(int[] array1, int[] array2){
		int[] resultArray = new int [array1.length];
		
		for(int i=0; i<resultArray.length; i++){
			resultArray[i] = array2[i]-array1[i];
		}
		
		return resultArray;
	}

	
	public static double[] divideArrByNum(int[] array, double num){
		double[] resultArray = new double [array.length];
		
		for(int i=0; i<resultArray.length; i++){
			resultArray[i] = array[i]/num;
		}
		
		return resultArray;
	}
	
	public static double[] multiplyArrByNum(int[] array, double num){
		double[] resultArray = new double [array.length];
		
		for(int i=0; i<resultArray.length; i++){
			resultArray[i] = array[i]*num;
		}
		
		return resultArray;
	}
	
}
