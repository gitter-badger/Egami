/********************************************************************************************************
* Developer: Minhas Kamal(minhaskamal024@gmail.com)														*
* Date: 14-Dec-2014																						*
* Modification Date: 02-Jan-2016																		*
********************************************************************************************************/

package com.minhaskamal.egami.processes;

import java.io.IOException;

import com.minhaskamal.egami.matrix.Matrix;
import com.minhaskamal.egami.matrix.MatrixUtilitiesPrimary;

public class ImageTransformer {
	//convert to color point/////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Rests pixels of an image to specific pixels. image quantization.
	 * @param mat input mat.
	 * @param noOfColorPoints
	 * @return converted <code>Mat</code>
	 */
	public static Matrix convertToColorPoints(Matrix mat, int noOfColorPoints){
		mat = MatrixUtilitiesPrimary.convertTo(mat, Matrix.BLACK_WHITE);

		int rows = mat.getRows(),
			cols = mat.getCols();
		
		//create new mat//
		Matrix newMat = new Matrix(rows, cols, Matrix.BLACK_WHITE);
		
		int limitMin = 0,
			limitMax = 255,
			range = (limitMax-limitMin)/(noOfColorPoints-1);
		
		double mediumPixel = getMediumPixel(mat);
		
		int adaptiveLimitMin=limitMin,
			adaptiveLimitMax=limitMax;
		if(mediumPixel>128){
			adaptiveLimitMin = (int) (limitMin + 2*(mediumPixel-128));
		}else{
			adaptiveLimitMax = (int) (limitMax - 2*(128-mediumPixel));
		}
		
		int adaptiveRange = (adaptiveLimitMax-adaptiveLimitMin) / noOfColorPoints;
		
		int pixel=0;
		for(int x=0, y; x<rows; x++){
			for(y=0; y<cols; y++){
				
				pixel = (int) mat.getPixel(x, y)[0];
				for(int i=0; i<noOfColorPoints; i++){
					if( pixel < adaptiveLimitMin+(adaptiveRange*(i+1)) ){
						pixel=range*i;
						break;
					}
				}
				
				newMat.setPixel(x, y, new int[]{pixel});
			}
		}
		
		return newMat;
	}
	
	//convert to edge pixel//////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Shows edges on an image, something like a sketch. Works nice on images converted to
	 * color points.
	 * @param mat input <code>Matrix</code>
	 * @param limit least difference of two pixel to be recognized as a border
	 * @return <code>Matrix</code> containing edges only
	 */
	public static Matrix convertToEdgePixels(Matrix mat, int limit){
		mat = MatrixUtilitiesPrimary.convertTo(mat, Matrix.BLACK_WHITE);
		
		int rows = mat.getRows()-1,
			cols = mat.getCols()-1;
			
		//create new mat//
		Matrix newmat = new Matrix(rows, cols, Matrix.BLACK_WHITE);
		
		int[] pixel = new int[1];
		for(int x=1, y; x<rows; x++){
			for(y=1; y<cols; y++){
				//pixel = (int) mat.get(x, y)[0];
				if(absolute( mat.getPixel(x, y)[0]-mat.getPixel(x+1, y)[0] ) > limit){
					pixel[0]=254;
//				}else if(absolute( mat.getPixel(x-1, y)[0]-mat.getPixel(x+1, y)[0] ) > limit){
//					pixel[0]=254;
//				}else if(absolute( mat.getPixel(x-1, y-1)[0]-mat.getPixel(x+1, y+1)[0] ) > limit){
//					pixel[0]=254;
//				}else if(absolute( mat.getPixel(x-1, y+1)[0]-mat.getPixel(x+1, y-1)[0] ) > limit){
//					pixel[0]=254;
				}else{
					pixel[0]=0;
				}
				
				newmat.setPixel(x, y, pixel);
			}
		}
		
		return newmat;
	}
	
	//convert to LBP/////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * 
	 * @param mat
	 * @return
	 */
	public static Matrix convertToLocalBinaryPattern(Matrix mat){
		mat = MatrixUtilitiesPrimary.convertTo(mat, Matrix.BLACK_WHITE);
		
		int rows = mat.getRows()-1,
			cols = mat.getCols()-1;
			
		//create new matrix//
		Matrix newmat = new Matrix(rows-2, cols-2, Matrix.BLACK_WHITE);
		
		int[] pixel = new int[1];
		int[] LocalBinaryValue = new int[8];
		for(int x=1, y; x<rows-2; x++){
			for(y=1; y<cols-2; y++){
				
				LocalBinaryValue[0] = comaprePixel(mat.getPixel(x+1, y+1)[0], mat.getPixel(x, y)[0]);
				LocalBinaryValue[1] = comaprePixel(mat.getPixel(x+1, y+1)[0], mat.getPixel(x+1, y)[0]);
				LocalBinaryValue[2] = comaprePixel(mat.getPixel(x+1, y+1)[0], mat.getPixel(x+2, y)[0]);
				
				LocalBinaryValue[3] = comaprePixel(mat.getPixel(x+1, y+1)[0], mat.getPixel(x, y+1)[0]);
				LocalBinaryValue[4] = comaprePixel(mat.getPixel(x+1, y+1)[0], mat.getPixel(x+2, y+1)[0]);
				
				LocalBinaryValue[5] = comaprePixel(mat.getPixel(x+1, y+1)[0], mat.getPixel(x, y+2)[0]);
				LocalBinaryValue[6] = comaprePixel(mat.getPixel(x+1, y+1)[0], mat.getPixel(x+1, y+2)[0]);
				LocalBinaryValue[7] = comaprePixel(mat.getPixel(x+1, y+1)[0], mat.getPixel(x+2, y+2)[0]);

				int value = 0;
				for(int i=0; i<8; i++){
					value += LocalBinaryValue[i]*Math.pow(2, 7-i);
				}
				
				pixel[0] = value;
				
				newmat.setPixel(x, y, pixel);
			}
		}
		
		return newmat;
	}
	
	//brightness/////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Resets brightness of an image to a medial.
	 * @param mat input mat.
	 * @return output mat
	 */
	public static Matrix spatialBrightnessEqualizer(Matrix mat, int spatialRange){
		mat = MatrixUtilitiesPrimary.convertTo(mat, Matrix.BLACK_WHITE);
		
		int rows = mat.getRows(),
			cols = mat.getCols();
		
		//create new mat//
		Matrix mat2 = new Matrix(rows, cols, mat.getType());
		
		double ratio;
		int[] mediumValue = new int[1];
		for(int x=spatialRange, y; x<rows-spatialRange; x++){
			for(y=spatialRange; y<cols-spatialRange; y++){
				
				ratio = 128 / getMediumPixel(mat
						.subMatrix(x-spatialRange, x+spatialRange, y-spatialRange, y+spatialRange));
				mediumValue[0] = (int) ((mat.getPixel(x, y)[0]+1) * ratio);
				
				if(mediumValue[0]>255){
					mediumValue[0]=255;
				}
				
				mat2.setPixel(x, y, mediumValue);
			}
		}
		
		return mat2;
	}

	//histogram/////////////////////////////////////////////////////////////////////////////////////////

	public static Matrix histogramEqualizer(Matrix mat){
		mat = MatrixUtilitiesPrimary.convertTo(mat, Matrix.BLACK_WHITE);
		
		int rows = mat.getRows(),
			cols = mat.getCols();
		double totalPixelCount = rows*cols;
		int[] pixelFreq = countPixelFreq(mat);
		double[] cumulativeDistributiveValues =  new double[256];
		
		cumulativeDistributiveValues[0] = (pixelFreq[0]/totalPixelCount)*255;
		for(int i=1; i<256; i++){
			cumulativeDistributiveValues[i] = cumulativeDistributiveValues[i-1] + (pixelFreq[i]/totalPixelCount)*255;
		}
		
		//create new mat//
		Matrix mat2 = new Matrix(rows, cols, mat.getType());
		
		int[] newPixel = new int[1];
		for(int x=0, y; x<rows; x++){
			for(y=0; y<cols; y++){
				newPixel[0] = (int) cumulativeDistributiveValues[ mat.getPixel(x, y)[0] ];
				
				mat2.setPixel(x, y, newPixel);
			}
		}
		
		return mat2;
	}
	
	//masking///////////////////////////////////////////////////////////////////////////////////////////
	/*public static Matrix histogramEqualizer(Matrix mat){
		mat = MatrixUtilities.convertTo(mat, Matrix.BLACK_WHITE);
		
		int[] pixelFreq = countPixelFreq(mat);
		
		int rows = mat.getRows(),
			cols = mat.getCols();
		
		//create new mat//
		Matrix mat2 = new Matrix(rows, cols, mat.getType());
		
		int[] newPixel = new int[1];
		for(int x=2, y; x<rows-2; x++){
			for(y=2; y<cols-2; y++){
				newPixel[0] = mat.getPixel(x, y)[0];
				
				mat2.setPixel(x, y, newPixel);
			}
		}
		
		return mat2;
	}*/
	
	//other necessary methods////////////////////////////////////////////////////////////////////////////
	
	public static int comaprePixel(int pixel1, int pixel2){
		//if(pixel2-pixel1 > Math.sqrt(pixel1)){
		if(pixel2-pixel1 > 0){
			return 1;
		}
		
		return 0;
	}
	
	/**
	 * returns the average of all pixels of a gray-scale image.
	 * @param mat matrix should be Matrix.BLACK_WHITE type
	 * @return
	 */
	public static double getMediumPixel(Matrix mat){
		int rows = mat.getRows(),
			cols = mat.getCols();
		
		//find the ration//
		double sumOfPixelByRow = 0;
		double sumOfPixelByColInRow = 0;
		for(int x=0, y; x<rows; x++){
			sumOfPixelByRow=0;
			for(y=0; y<cols; y++){
				sumOfPixelByRow += mat.getPixel(x, y)[0];
			}
			
			sumOfPixelByColInRow += sumOfPixelByRow/cols;
		}
		
		return sumOfPixelByColInRow/rows;
	}
	
	/**
	 * 
	 * @param matrix matrix should be Matrix.BLACK_WHITE type
	 * @return
	 */
	public static int[] countPixelFreq(Matrix matrix){
		int[] pixelFreq = new int[256];
		for(int i=0; i<matrix.getRows(); i++) {
			for(int j=0; j<matrix.getCols(); j++) {
				pixelFreq[matrix.getPixel(i, j)[0]]++;				
			}
		}
		
		return pixelFreq;
	}
	
	/**
	 * converts double into absolute value.
	 * @param a input value
	 * @return absolute value of a
	 */
	public static double absolute(double a){
		if(a>=0){
			return a;
		}else{
			return -a;
		}
	}
	
	///test
	public static void main(String[] args) {
		try {
			String inputImageFilePath = "C:\\Users\\admin\\Desktop\\4.png";
			String outputImageFilePath = "C:\\Users\\admin\\Desktop\\5.png";
	
			Matrix mat = new Matrix(inputImageFilePath, Matrix.BLACK_WHITE);
			
			//mat = spatialBrightnessEqualizer(mat, 4);
			//mat = convertToEdgePixels(mat, 30);
			//mat = histogramEqualizer(mat);
			//mat = convertToLocalBinaryPattern(mat);
			mat = convertToColorPoints(mat, 2);
			
			mat.write(outputImageFilePath);
			
			System.out.println("OPERATION SUCCESSFUL!!!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
