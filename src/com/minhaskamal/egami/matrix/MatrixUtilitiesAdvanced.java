/****************************************************************************************************************
* Developer: Minhas Kamal(minhaskamal024@gmail.com)																*
* Date: 10-Mar-2015																								*
* Modification Date: 01-Jan-2016																				*
****************************************************************************************************************/

package com.minhaskamal.egami.matrix;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class MatrixUtilitiesAdvanced extends MatrixUtilitiesSecondary{

	/**
	 * 
	 * @param matrix
	 * @param scaledWidth
	 * @param scaledHeight
	 * @return
	 */
	public static Matrix resize2(Matrix matrix, int scaledWidth, int scaledHeight){
		BufferedImage originalBufferedImage = Matrix.matrixToBufferedImage(matrix);
		BufferedImage scaledBufferedImage = new BufferedImage(scaledWidth, scaledHeight, originalBufferedImage.getType());
	
		Graphics2D g = scaledBufferedImage.createGraphics();
		g.drawImage(originalBufferedImage, 0, 0, scaledWidth, scaledHeight, null); 
		g.dispose();
		
		return Matrix.bufferedImageToMatrix(scaledBufferedImage, matrix.getType());
	}

	/**
	 * 
	 * @param matrix
	 * @param degree
	 * @return
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
	    
		Matrix newMatrix = Matrix.bufferedImageToMatrix(rotatedBufferedImage, matrix.getType());
	    int cropTop = (int) ( matrix.getRows() * (1-Math.cos(Math.toRadians(degree))) );

	    newMatrix = newMatrix.subMatrix(cropTop, newMatrix.getRows()-1, 0, newMatrix.getCols()-1);
		return newMatrix;
	}
	
	
	
	
	public static void main(String[] args) throws Exception {
		Matrix matrix = new Matrix("C:\\Users\\admin\\Desktop\\y.png", Matrix.RED_GREEN_BLUE_ALPHA);
		
//		matrix = skewHorizontal(matrix, 30);
		matrix = resize2(matrix, 100, 100);
		
		//matrix = rotate3(matrix, 30);
		
		matrix.write("C:\\Users\\admin\\Desktop\\c.png");
		
		System.out.println("OPERATION SUCCESSFUL!!");
	}
}
