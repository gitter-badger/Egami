/************************************************************************************************************
* Developer: Minhas Kamal(minhaskamal024@gmail.com)															*
* Date: 14-Dec-2014																							*
* Modification Date: 02-Jan-2016																			*
************************************************************************************************************/

package com.minhaskamal.egami.matrixUtil;

import com.minhaskamal.egami.matrix.Matrix;

public class MatrixUtilitiesAdvanced extends MatrixUtilitiesSecondary{
	///convert to color point/////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Implements image quantization technique & resets pixels of a grey scale
	 * image to specific pixels. Works with only <b>grey scale</b> image.
	 * @param mat input <code>Matrix</code>.
	 * @param numberOfColors 
	 * @return converted <code>Matrix</code>
	 */
	public static Matrix applyQuantization(Matrix mat, int numberOfColors){
		mat = MatrixUtilitiesPrimary.convertTo(mat, Matrix.BLACK_WHITE);

		int rows = mat.getRows(),
			cols = mat.getCols();
		
		//create new mat//
		Matrix newMat = new Matrix(rows, cols, Matrix.BLACK_WHITE);
		
		int limitMin = 0,
			limitMax = 255,
			range = (limitMax-limitMin)/(numberOfColors-1);
		
		double mediumPixel = getMediumPixel(mat);
		
		int adaptiveLimitMin=limitMin,
			adaptiveLimitMax=limitMax;
		if(mediumPixel>128){
			adaptiveLimitMin = (int) (limitMin + 2*(mediumPixel-128));
		}else{
			adaptiveLimitMax = (int) (limitMax - 2*(128-mediumPixel));
		}
		
		int adaptiveRange = (adaptiveLimitMax-adaptiveLimitMin) / numberOfColors;
		
		int pixel=0;
		for(int x=0, y; x<rows; x++){
			for(y=0; y<cols; y++){
				
				pixel = (int) mat.pixels[x][y][0];
				for(int i=0; i<numberOfColors; i++){
					if( pixel < (adaptiveLimitMin+(adaptiveRange*(i+1))) ){
						pixel=range*i;
						break;
					}
				}
				if(pixel==(int) mat.pixels[x][y][0]){
					System.out.println(pixel + " " + numberOfColors);
				}
				newMat.pixels[x][y] = new int[]{pixel};
			}
		}
		
		return newMat;
	}
	
	///convert to edge pixel//////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Shows edges on an grey scale image. Works nice on quantized images.
	 * @param mat input <code>Matrix</code> of type grey scale
	 * @param threshold least difference of two pixel to be recognized as a border
	 * @return <code>Matrix</code> containing edges only
	 */
	public static Matrix applyEdgeDetecton(Matrix mat, int threshold){
		mat = MatrixUtilitiesPrimary.convertTo(mat, Matrix.BLACK_WHITE);
		
		int rows = mat.getRows()-1,
			cols = mat.getCols()-1;
			
		//create new mat//
		Matrix newmat = new Matrix(rows, cols, Matrix.BLACK_WHITE);
		
		int[] pixel = new int[1];
		for(int x=1, y; x<rows; x++){
			for(y=1; y<cols; y++){
				
				if(absolute( mat.pixels[x][y][0]-mat.pixels[x+1][y][0] ) > threshold){
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
				
				newmat.pixels[x][y] =  pixel.clone();
			}
		}
		
		return newmat;
	}
	
	/**
	 * converts double into absolute value.
	 * @param a input value
	 * @return absolute value of a
	 */
	private static double absolute(double a){
		if(a>=0){
			return a;
		}else{
			return -a;
		}
	}
	
	///convert to LBP/////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Applies LBP over grey scale image.
	 * @param mat
	 * @return
	 */
	public static Matrix applyLocalBinaryPattern(Matrix mat){
		mat = MatrixUtilitiesPrimary.convertTo(mat, Matrix.BLACK_WHITE);
		
		int rows = mat.getRows()-1,
			cols = mat.getCols()-1;
			
		//create new matrix//
		Matrix newmat = new Matrix(rows-2, cols-2, Matrix.BLACK_WHITE);
		
		int[] pixel = new int[1];
		int[] LocalBinaryValue = new int[8];
		for(int x=1, y; x<rows-2; x++){
			for(y=1; y<cols-2; y++){
				
				LocalBinaryValue[0] = comaprePixel(mat.pixels[x+1][y+1][0], mat.pixels[x][y][0]);
				LocalBinaryValue[1] = comaprePixel(mat.pixels[x+1][y+1][0], mat.pixels[x+1][y][0]);
				LocalBinaryValue[2] = comaprePixel(mat.pixels[x+1][y+1][0], mat.pixels[x+2][y][0]);

				LocalBinaryValue[3] = comaprePixel(mat.pixels[x+1][y+1][0], mat.pixels[x][y+1][0]);
				LocalBinaryValue[4] = comaprePixel(mat.pixels[x+1][y+1][0], mat.pixels[x+2][y+1][0]);

				LocalBinaryValue[5] = comaprePixel(mat.pixels[x+1][y+1][0], mat.pixels[x][y+2][0]);
				LocalBinaryValue[6] = comaprePixel(mat.pixels[x+1][y+1][0], mat.pixels[x+1][y+2][0]);
				LocalBinaryValue[7] = comaprePixel(mat.pixels[x+1][y+1][0], mat.pixels[x+2][y+2][0]);

				int value = 0;
				for(int i=0; i<8; i++){
					value += LocalBinaryValue[i]*Math.pow(2, 7-i);
				}
				
				pixel[0] = value;
				
				newmat.pixels[x][y] = pixel.clone();
			}
		}
		
		return newmat;
	}
	
	private static int comaprePixel(int pixel1, int pixel2){
		//if(pixel2-pixel1 > Math.sqrt(pixel1)){
		if(pixel2-pixel1 > 0){
			return 1;
		}
		
		return 0;
	}
	
	///brightness/////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Resets brightness of an grey scale image to a medial.
	 * @param mat grey scale image only
	 * @return
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
				mediumValue[0] = (int) ((mat.pixels[x][y][0]+1) * ratio);
				
				if(mediumValue[0]>255){
					mediumValue[0]=255;
				}
				
				mat2.pixels[x][y] = mediumValue.clone();
			}
		}
		
		return mat2;
	}

	///histogram/////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * grey-scale image only
	 * @param mat
	 * @return
	 */
	public static Matrix histogramEqualizer(Matrix mat){
		mat = MatrixUtilitiesPrimary.convertTo(mat, Matrix.BLACK_WHITE);
		
		int rows = mat.getRows(),
			cols = mat.getCols();
		double totalPixelCount = rows*cols;
		int[] pixelFreq = countPixelFreq(mat)[0];
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
				newPixel[0] = (int) cumulativeDistributiveValues[ mat.pixels[x][y][0] ];
				
				mat2.pixels[x][y] = newPixel.clone();
			}
		}
		
		return mat2;
	}
	
	//masking///////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * all types of images.
	 * @param mat
	 * @param level
	 * @return
	 */
	public static Matrix applyBlurring(Matrix mat, int level){
		int dimention = level*2+1;
		double[][] mask = new double[dimention][dimention];
		
		for(int i=0; i<dimention; i++){
			for(int j=0; j<dimention; j++){
				mask[i][j] = 1.0/(dimention*dimention);
			}
		}
		
		Matrix mat2 = MatrixUtilitiesSecondary.applyMask(mat, mask);
		
		return mat2;
	}
	
	//other necessary methods////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the average of all pixels of a <b>gray-scale</b> image.
	 * @param mat matrix should be <code>Matrix.BLACK_WHITE</code> type
	 * @return
	 */
	private static double getMediumPixel(Matrix mat){
		int rows = mat.getRows(),
			cols = mat.getCols();
		
		//find the ration//
		double sumOfPixelByRow = 0;
		double sumOfPixelByColInRow = 0;
		for(int x=0, y; x<rows; x++){
			sumOfPixelByRow=0;
			for(y=0; y<cols; y++){
				sumOfPixelByRow += mat.pixels[x][y][0];
			}
			
			sumOfPixelByColInRow += sumOfPixelByRow/cols;
		}
		
		return sumOfPixelByColInRow/rows;
	}
	
	
	///test/////////////////////////////////////////////////////////////////////////////////////////////
	public static void main(String[] args) {
		try {
			String inputImageFilePath = "C:\\Users\\admin\\Desktop\\l.jpg";
			String outputImageFilePath = "C:\\Users\\admin\\Desktop\\yl.png";
	
			Matrix mat = new Matrix(inputImageFilePath, Matrix.BLACK_WHITE);
			
			//mat = spatialBrightnessEqualizer(mat, 4);
			//mat = convertToEdgePixels(mat, 30);
			mat = applyQuantization(mat, 2);
			//mat = convertToLocalBinaryPattern(mat);
			//mat = applyBlurring(mat, 3);
			
			mat.write(outputImageFilePath);
			
			System.out.println("OPERATION SUCCESSFUL!!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
