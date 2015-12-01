/****************************************************************************************************************
* Developer: Minhas Kamal(BSSE-0509, IIT, DU)																	*
* Date: 10-Mar-2015																								*
* Modification Date: 01-Jan-2016																				*
****************************************************************************************************************/

package com.minhaskamal.egami.matrix;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class MatrixAdvancedUtilities extends MatrixUtilities{

	/**
	 * @return may not be the same type as the input <code>Matrix</code>
	 */
	public static Matrix resize(Matrix matrix, int scaledWidth, int scaledHeight){
		BufferedImage originalBufferedImage = Matrix.matrixToBufferedImage(matrix);
		BufferedImage scaledBufferedImage = new BufferedImage(scaledWidth, scaledHeight, originalBufferedImage.getType());
	
		Graphics2D g = scaledBufferedImage.createGraphics();
		g.drawImage(originalBufferedImage, 0, 0, scaledWidth, scaledHeight, null); 
		g.dispose();
		
		return Matrix.bufferedImageToMatrix(scaledBufferedImage);
	}

	/**
	 * @return may not be the same type as the input <code>Matrix</code>
	 */
	public static Matrix rotate3(Matrix matrix, double degree){
		if(degree<0){
			degree = 360-degree;
		}
		
		BufferedImage originalBufferedImage = Matrix.matrixToBufferedImage(matrix);
		BufferedImage rotatedBufferedImage = new BufferedImage
				(originalBufferedImage.getWidth(), originalBufferedImage.getHeight(), originalBufferedImage.getType());
		AffineTransform affineTransform = new AffineTransform();
		affineTransform.rotate(Math.toRadians(degree), 0, rotatedBufferedImage.getHeight());
	    AffineTransformOp affineTransformOp = new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_BILINEAR);
	    rotatedBufferedImage = affineTransformOp.filter(originalBufferedImage, null);
	    
		Matrix newMatrix = Matrix.bufferedImageToMatrix(rotatedBufferedImage);
	    int cropTop = (int) ( matrix.getRows() * (1-Math.cos(Math.toRadians(degree))) );

	    newMatrix = newMatrix.subMatrix(cropTop, newMatrix.getRows()-1, 0, newMatrix.getCols()-1);
		return newMatrix;
	}
	
	public static Matrix rotate2(Matrix matrix, double angle){
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
		
		angle = -angle%90;
		if(angle==0){
			return matrix;
		}
		System.out.println(angle);
		
		///////////////////////////////////////////
		double sinX = Math.sin(Math.toRadians(angle));
		double cosX = Math.cos(Math.toRadians(angle));
		
		int rotatedRows = (int) ( matrix.getRows()*Math.abs(cosX) + matrix.getCols()*Math.abs(sinX) );
		int rotatedCols = (int) ( matrix.getRows()*Math.abs(sinX) + matrix.getCols()*Math.abs(cosX) );
		
		///////////////////////////////////////////
		matrix = skewVertical(matrix, angle/2);
		
		///////////////////////////////////////////
		int row = matrix.getRows();
		int col = matrix.getCols();
		
		Matrix matrix2 = new Matrix(row, (int)(col + ( row*Math.abs(sinX) )), matrix.getType());
		
		int x=0;
		if(angle<0){
			x = (int) (row*Math.abs(sinX));
		}
		for(int i=0; i<row; i++){
			int skew = (int) (i*sinX);
			for(int j=0; j<col; j++){
				matrix2.setPixel(i, j+skew+x, matrix.getPixel(i, j));
			}
		}
		
		//////////////////////////////////////////
		matrix2 = skewVertical(matrix2, angle/2);
		
		//////////////////////////////////////////
		row = matrix2.getRows();
		col = matrix2.getCols();
		
		int horizontalCropSize = (int) ( (col-rotatedCols)/2 );
		int verticalCropSize = (int) ( (row-rotatedRows)/2 );
		
		matrix2 = crop(matrix2, horizontalCropSize, horizontalCropSize, verticalCropSize, verticalCropSize);

		return matrix2;
	}
	
	public static Matrix skewHorizontal(Matrix matrix, double angle){
		angle = angle%90;
		
		int row = matrix.getRows();
		int col = matrix.getCols();
		
		double tanX = Math.tan(Math.toRadians(angle));
		
		Matrix matrix2 = new Matrix(row, (int)(col + ( row*Math.abs(tanX) )), matrix.getType());
		
		int x=0;
		if(angle<0){
			x = (int) (row*Math.abs(tanX));
		}
		for(int i=0; i<row; i++){
			int skew = (int) (i*tanX);
			for(int j=0; j<col; j++){
				matrix2.setPixel(i, j+skew+x, matrix.getPixel(i, j));
			}
		}
		
		return matrix2;
	}
	
	public static Matrix skewVertical(Matrix matrix, double angle){
		angle = angle%90;
		
		int row = matrix.getRows();
		int col = matrix.getCols();
		
		double tanX = Math.tan(Math.toRadians(angle));
		
		Matrix matrix2 = new Matrix((int)(row + ( col*Math.abs(tanX) )), col, matrix.getType());
		
		int x=0;
		if(angle<0){
			x = (int) (col*Math.abs(tanX));
		}
		for(int j=0; j<col; j++){
			int skew = (int) ((col-1-j)*tanX);
			for(int i=0; i<row; i++){
				matrix2.setPixel(i+skew+x, j, matrix.getPixel(i, j));
			}
		}
		
		return matrix2;
	}
	
	public static void main(String[] args) throws Exception {
		Matrix matrix = new Matrix("C:\\Users\\admin\\Desktop\\a.jpg");
		
//		matrix = skewHorizontal(matrix, -30);
//		matrix = skewVertical(matrix, -30);
		
		matrix = rotate3(matrix, 30);
		
		matrix.write("C:\\Users\\admin\\Desktop\\c.png");
		
		System.out.println("OPERATION SUCCESSFUL!!");
	}
}
