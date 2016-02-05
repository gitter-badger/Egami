/****************************************************************************************************************
* Developer: Minhas Kamal(minhaskamal024@gmail.com)																*
* Date: 10-Mar-2015																								*
* Modification Date: 01-Jan-2016																				*
****************************************************************************************************************/


package com.minhaskamal.egami.matrixUtil;

import com.minhaskamal.egami.matrix.Matrix;
import com.minhaskamal.egami.matrixMath.MatrixMath;

public class MatrixUtilitiesSecondary extends MatrixUtilitiesPrimary{
	
	///rotator////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * works with simple algorithm
	 * @param angle in degree
	 */
	public static Matrix rotate(Matrix matrix, double angle){
		angle = primaryRotation(matrix, angle);
		if(angle==0){
			return matrix;
		}
		
		int row = matrix.getRows();
		int col = matrix.getCols();
		
		double sinX = Math.sin(Math.toRadians(angle));
		double cosX = Math.cos(Math.toRadians(angle));
		
		double height_sinX = row*sinX;
		
		Matrix matrix2 = new Matrix( (int)(row*cosX+col*sinX), (int)(height_sinX+col*cosX), matrix.getType() );
		
		for(int i=0, j, newI, newJ; i<row; i++){
			for(j=0; j<col; j++){
				newI = (int) Math.floor(i*cosX + j*sinX);
				newJ = (int) Math.floor(-i*sinX + j*cosX + height_sinX);
				
				matrix2.pixels[newI][newJ] =  matrix.pixels[i][j].clone();
			}
		}
		
		return matrix2;
	}
	
	/**
	 * 
	 * @param matrix
	 * @param angle
	 * @return
	 */
	public static Matrix rotate2(Matrix matrix, double angle){
		angle = -primaryRotation(matrix, angle);
		if(angle==0){
			return matrix;
		}
		
		///////////////////////////////////////////
		double sinX = Math.sin(Math.toRadians(angle));
		double cosX = Math.cos(Math.toRadians(angle));
		
		int row = matrix.getRows();
		int col = matrix.getCols();
		
		int rotatedRows = (int) ( row*Math.abs(cosX) + col*Math.abs(sinX) );
		int rotatedCols = (int) ( row*Math.abs(sinX) + col*Math.abs(cosX) );
		
		///////////////////////////////////////////
		matrix = skewVertical(matrix, angle/2);
		
		///////
		row = matrix.getRows();
		col = matrix.getCols();
		
		Matrix matrix2 = new Matrix(row, (int)(col + ( row*Math.abs(sinX) )), matrix.getType());
		
		int x=0;
		if(angle<0){
			x = (int) (row*Math.abs(sinX));
		}
		for(int i=0; i<row; i++){
			int skew = (int) (i*sinX);
			for(int j=0; j<col; j++){
				matrix2.pixels[i][j+skew+x] = matrix.pixels[i][j].clone();
			}
		}
		
		///////
		matrix2 = skewVertical(matrix2, angle/2);
		
		//////////////////////////////////////////
		
		int horizontalCropSize = (int) ( (matrix2.getCols()-rotatedCols)/2 );
		int verticalCropSize = (int) ( (matrix2.getRows()-rotatedRows)/2 );
	
		matrix2 = crop(matrix2, horizontalCropSize, horizontalCropSize, verticalCropSize, verticalCropSize);

		return matrix2;
	}
	
	/**
	 * 
	 * @param matrix
	 * @param angle
	 * @return
	 */
	private static double primaryRotation(Matrix matrix, double angle){
		angle = angle%360;
		if(angle<0){
			angle = 360+angle;
		}
		
		if(angle>=270){
			matrix = rotateLeft(matrix);
		}else if(angle>=180){
			matrix = rotateRight(rotateRight(matrix));
		}else if(angle>=90){
			matrix = rotateRight(matrix);
		}
		
		angle = angle%90;
		
		return angle;
	}
	
	///masking////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * 
	 * @param matrix 
	 * @param mask has to be square MATRIX
	 * @return
	 */
	public static Matrix applyMask(Matrix matrix, double[][] mask) {
		Matrix matrix2 = new Matrix(matrix.getRows(), matrix.getCols(), matrix.getType());
		
		int border = (mask.length-1)/2;
		matrix = MatrixUtilitiesPrimary.border(matrix, border);
		
		int rows = matrix.getRows(),
			cols = matrix.getCols();
		int[] newpixel = new int[matrix.getType()];

		for(int i=border; i<rows-border; i++) {
			for(int j=border; j<cols-border; j++) {

				for(int k=0; k<newpixel.length; k++) {
					newpixel[k] = 0;

					for(int m=0; m<mask.length; m++) {
						for(int n=0; n<mask[0].length; n++) {
							newpixel[k] += (mask[m][n] * matrix.pixels[i+m-border][j+n-border][k]);
						}
					}
					if(newpixel[k] > 255){
						newpixel[k]=255;
					}else if(newpixel[k] < 0){
						newpixel[k]=0;
					}
				}
				matrix2.pixels[i-border][j-border] =  newpixel.clone();
				
			}
		}

		return matrix2;
	}
	
	///resizing//////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * 
	 * @param matrix
	 * @param percentage
	 * @return
	 */
	public static Matrix horizontallyStretch(Matrix matrix, double percentage){
		int rows = matrix.getRows(),
			cols = matrix.getCols();
			
		double multiplier = percentage/100;
		
		int newRows = rows,
			newCols = (int)(cols*multiplier);
		Matrix matrix2 = new Matrix(newRows, newCols, matrix.getType());
		
		multiplier += multiplier/(cols-1);	//mild correction
		
		double[] pixelDiff = new double[matrix.getType()];
		for(int i=0, j; i<rows; i++){
			for(j=1; j<cols; j++){
				int[] initialPixel = matrix.pixels[i][j-1].clone();
				int initialColumn = (int) ((j-1)*multiplier);
				double finalColumn = j*multiplier;
				
				pixelDiff = MatrixMath.divideArrByNum(
						MatrixMath.subtracArrByArr(initialPixel, matrix.pixels[i][j]),
						multiplier);
				
				matrix2.pixels[i][initialColumn] = initialPixel;
				for(int k=initialColumn+1; k<finalColumn; k++){
					matrix2.pixels[i][k] = arr2arrAddition(matrix2.pixels[i][k-1], pixelDiff);
				}
			}
		}
		
		return matrix2;
	}
	
	/**
	 * 
	 * @param matrix
	 * @param percentage
	 * @return
	 */
	public static Matrix verticallyStretch(Matrix matrix, double percentage){
		int rows = matrix.getRows(),
			cols = matrix.getCols();
			
		double multiplier = percentage/100;
		
		int newRows = (int)(rows*multiplier),
			newCols = cols;
		Matrix matrix2 = new Matrix(newRows, newCols, matrix.getType());
		
		multiplier += multiplier/(rows-1);	//mild correction
		
		double[] pixelDiff = new double[matrix.getType()];
		for(int i=1, j; i<rows; i++){
			for(j=0; j<cols; j++){
				int[] initialPixel = matrix.pixels[i-1][j];
				int initialRow = (int) ((i-1)*multiplier);
				double finalRow = i*multiplier;
				
				pixelDiff = MatrixMath.divideArrByNum(
						MatrixMath.subtracArrByArr(initialPixel, matrix.pixels[i][j]),
						multiplier);
				
				matrix2.pixels[initialRow][j] = initialPixel;
				for(int k=initialRow+1; k<finalRow; k++){
					matrix2.pixels[k][j] = arr2arrAddition(matrix2.pixels[k-1][j], pixelDiff);
				}
				
			}
		}
		
		return matrix2;
	}
	
	private static int[] arr2arrAddition(int[] array1, double[] array2){
		int[] resultArray = new int [array1.length];
		
		for(int i=0; i<resultArray.length; i++){
			resultArray[i] = (int) (array1[i]+array2[i]);
			if(resultArray[i]>255){
				resultArray[i]=255;
			}else if(resultArray[i]<0){
				resultArray[i]=0;
			}
		}
		
		return resultArray;
	}
	
	
	/**
	 * 
	 * @param matrix
	 * @param horizontalPercentage
	 * @param verticalPercentage
	 * @return
	 */
	public static Matrix resize(Matrix matrix, double horizontalPercentage, double verticalPercentage){
		matrix = horizontallyStretch(matrix, horizontalPercentage);
		matrix = verticallyStretch(matrix, verticalPercentage);
		
		return matrix;
	}
	
	public static Matrix resize(Matrix matrix, double percentage){
		return resize(matrix, percentage, percentage);
	}
	
	/**
	 * 
	 * @param matrix
	 * @param length
	 * @param width
	 * @return
	 */
	public static Matrix resize(Matrix matrix, int length, int width){
		
		double horizontalPercentage = length*100/matrix.getCols(),
			verticalPercentage = width*100/matrix.getRows();
		
		return resize(matrix, horizontalPercentage, verticalPercentage);
	}
	
	
	
	///test only
	public static void main(String[] args) throws Exception {
		Matrix matrix = new Matrix("C:\\Users\\admin\\Desktop\\shapes.png", Matrix.RED_GREEN_BLUE_ALPHA);
		
//		matrix = horizontalResizing(matrix, 814);
//		matrix = verticalResizing(matrix, 814);
		
//		for(int i=600; i<1300; i+=3){
//			resize(matrix, i);
//			System.out.println(i);
//		}
		
		matrix = resize(matrix, 30);
		
//			MatrixUtil.write(matrix, "C:\\Users\\admin\\Desktop\\d.png");
		
//			matrix = convertTo(matrix, Matrix.RED_GREEN_BLUE);
		
//		double[][] mask = {
//				{1.0/25, 1.0/25, 1.0/25, 1.0/25, 1.0/25},
//				{1.0/25, 1.0/25, 1.0/25, 1.0/25, 1.0/25},
//				{1.0/25, 1.0/25, 1.0/25, 1.0/25, 1.0/25},
//				{1.0/25, 1.0/25, 1.0/25, 1.0/25, 1.0/25},
//				{1.0/25, 1.0/25, 1.0/25, 1.0/25, 1.0/25}
//		};
		
//		double[][] mask = {
//				{1.0/9, 1.0/9, 1.0/9},
//				{1.0/9, 1.0/9, 1.0/9},
//				{1.0/9, 1.0/9, 1.0/9}
//		};
		
//		double[][] mask = {
//				{4.0/33, 4.0/33, 4.0/33},
//				{4.0/33, 1.0/18, 4.0/33},
//				{4.0/33, 4.0/33, 4.0/33}
//		};
		
//		matrix = masking(matrix, mask);
//		matrix = border(matrix, 5, new int[]{0,0,0,255});
		
//			matrix = flipVertical(matrix);
//			matrix = crop(matrix, 5, 2, 10, 15);
//			matrix = rotateLeft(matrix);
//			matrix = rotateRight(matrix);
		
		matrix.write("C:\\Users\\admin\\Desktop\\output.png");
		
		System.out.println("OPERATION SUCCESSFUL!!");
	}
}
