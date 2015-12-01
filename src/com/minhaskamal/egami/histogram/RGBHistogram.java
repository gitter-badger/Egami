/****************************************************************************************************************
* Developer: Minhas Kamal(BSSE-0509, IIT, DU)																	*
* Date: 28-Jan-2015																								*
* Modification Date: 01-Jan-2016																				*
****************************************************************************************************************/

package com.minhaskamal.egami.histogram;

import java.io.IOException;

import com.minhaskamal.egami.matrix.Matrix;
import com.minhaskamal.egami.matrix.MatrixUtilities;

public class RGBHistogram {
	private final int colorValues = 256;
	
	public void showRGBHistogram(Matrix matrix, int barWidth, int height){
		if(matrix.getType()!=Matrix.RED_GREEN_BLUE){
			MatrixUtilities.convertTo(matrix, Matrix.RED_GREEN_BLUE);
		}
		
		int verticalBuffer = barWidth;
		int horizontalBuffer = barWidth;
		
		int[][] rGBFreq = countRGBFreq(matrix);
		int highestFreq = getHighestFreq(rGBFreq);

		double multiplier = (height-1)/(double)highestFreq;
		
		Matrix matrixHistogram = new Matrix(height+verticalBuffer, colorValues*barWidth + horizontalBuffer,
				Matrix.RED_GREEN_BLUE);
		int[] rGBDot = new int[3];
		int[] initDot = {60, 60, 60};

		for (int i=0, j; i<colorValues; i++) {
			for(j=0; j<height; j++){
				for(int m=0; m<barWidth; m++){
					matrixHistogram.setPixel(j, i*barWidth + m + horizontalBuffer, initDot);
				}
			}
			
			for(int k=0; k<3; k++){
				j = (int) (height-( rGBFreq[k][i]*multiplier ));
				
				for ( ; j<height; j++) {
					rGBDot = matrixHistogram.getPixel(j, i*barWidth + horizontalBuffer);
					rGBDot[k] = 250;
					
					for(int m=0; m<barWidth; m++){
						matrixHistogram.setPixel(j, i*barWidth + m + horizontalBuffer, rGBDot);
					}
				}
			}
		}
		
		try {
			matrixHistogram.write("C:\\Users\\admin\\Desktop\\hist.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private int[][] countRGBFreq(Matrix matrix){
		int[][] rGBFreq = new int[3][colorValues];
		int[] tempDouble = new int[3];
		for(int i=0; i<matrix.getRows(); i++) {
			for(int j=0; j<matrix.getCols(); j++) {
				tempDouble = matrix.getPixel(i, j);

				for(int k=0; k<3; k++){
					rGBFreq[k][tempDouble[k]]++;
				}
			}
		}
		
		return rGBFreq;
	}
	
	private int getHighestFreq(int[][] rGBFreq) {
		int highestFreq=0;
		
		for(int i=0; i<3; i++){
			for(int j=0; j<256; j++){
				if(highestFreq<rGBFreq[i][j]){
					highestFreq=rGBFreq[i][j];
				}
			}
		}
		
		return highestFreq;
	}
	
	public static void main(String[] args) {
		try {
			Matrix matrix = new Matrix("C:\\Users\\admin\\Desktop\\a.jpg");
			new RGBHistogram().showRGBHistogram(matrix, 3, 350);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
