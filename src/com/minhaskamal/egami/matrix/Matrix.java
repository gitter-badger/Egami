/****************************************************************************************************************
* Developer: Minhas Kamal(minhaskamal024@gmail.com)																*
* Date: 10-Mar-2015																								*
* Modification Date: 30-Dec-2015																				*
* Modification Date: 22-Jan-2016																				*
****************************************************************************************************************/

package com.minhaskamal.egami.matrix;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Matrix {
	public static int BLACK_WHITE = 1,
			BLACK_WHITE_ALPHA = 2,
			RED_GREEN_BLUE = 3,
			RED_GREEN_BLUE_ALPHA = 4;
	
	/**
	 * image container
	 */
	int[][][] pixels;
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * When invalid info is provided Matrix is created with minimal size.
	 * @param row height of the image
	 * @param col width of the image
	 * @param type <code>BLACK_WHITE</code>, <code>BLACK_WHITE_ALPHA</code>, 
	 * <code>RED_GREEN_BLUE</code>, <code>RED_GREEN_BLUE_ALPHA</code>
	 */
	public Matrix( int row, int col, int type){
		if(row<1){
			row=1;
		}if(col<1){
			col=1;
		}if(type<Matrix.BLACK_WHITE || type>Matrix.RED_GREEN_BLUE_ALPHA){
			type = Matrix.BLACK_WHITE;
		}
		
		this.pixels = new int[row][col][type];
	}
	
	public Matrix(String imageFilePath, int type) throws IOException {
		this(ImageIO.read(new File(imageFilePath)), type);
	}
	
	public Matrix(BufferedImage bufferedImage, int type){
		this.pixels = bufferedImageToMatrix(bufferedImage, type).pixels;
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * @return height of image
	 */
	public int getRows(){
		return this.pixels.length;
	}
	
	/**
	 * @return width of image
	 */
	public int getCols(){
		return this.pixels[0].length;
	}
	
	/**
	 * @return 1 -> <code>BLACK_WHITE</code>,<br/>
	 * 2 -> <code>BLACK_WHITE_ALPHA</code>,<br/>
	 * 3 -> <code>RED_GREEN_BLUE</code>,<br/>
	 * 4 -> <code>RED_GREEN_BLUE_ALPHA</code>
	 */
	public int getType(){
		return this.pixels[0][0].length;
	}
	
	/**
	 * @param rowNo 0 =< rowNo < number of rows
	 * @param colNo 0 =< colNo < number of columns
	 */
	public int[] getPixel(int rowNo, int colNo){
		if(rowNo>=0 && colNo>=0 && rowNo<this.pixels.length && colNo<this.pixels[0].length){
			return this.pixels[rowNo][colNo].clone();
		}else{
			return null;
		}
	}
	
	/**
	 * @param rowNo 0 =< rowNo < number of rows
	 * @param colNo 0 =< colNo < number of columns
	 */
	public void setPixel(int rowNo, int colNo, int[] value){
		if(rowNo>=0 && colNo>=0 && rowNo<this.pixels.length && colNo<this.pixels[0].length && 
				value.length==this.pixels[0][0].length){
			this.pixels[rowNo][colNo] = value.clone();
		}
	}

	
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * 
	 * @param mat
	 * @return
	 */
	public static BufferedImage matrixToBufferedImage(Matrix mat){
		BufferedImage bufferedImage = new BufferedImage(mat.pixels[0].length, mat.pixels.length,
				BufferedImage.TYPE_4BYTE_ABGR);
		
		for(int i=0, j; i<mat.pixels.length; i++){
			for(j=0; j<mat.pixels[0].length; j++){
				bufferedImage.setRGB(j, i, rGBToInteger(mat.pixels[i][j]));
			}
		}
		
		return bufferedImage;
	}
	
	public static int rGBToInteger(int[] rGBInt){
		Color color;
		//int col = (255 << 24) | (rGBInt[0] << 16) | (rGBInt[0] << 8) | rGBInt[0];
		
		if(rGBInt.length==BLACK_WHITE){
			color = new Color(rGBInt[0], rGBInt[0], rGBInt[0], 255);
		}else if(rGBInt.length==BLACK_WHITE_ALPHA){
			color = new Color(rGBInt[0], rGBInt[0], rGBInt[0], rGBInt[1]);
		}else if(rGBInt.length==RED_GREEN_BLUE){
			color = new Color(rGBInt[0], rGBInt[1], rGBInt[2], 255);
		}else{
			color = new Color(rGBInt[0], rGBInt[1], rGBInt[2], rGBInt[3]);
		}
		
		return color.getRGB();
	}
	
	/**
	 * 
	 * @param bufferedImage
	 * @param type
	 * @return
	 */
	public static Matrix bufferedImageToMatrix(BufferedImage bufferedImage, int type){
		int row = bufferedImage.getHeight();
		int col = bufferedImage.getWidth();
		
		if(type<Matrix.BLACK_WHITE || type>Matrix.RED_GREEN_BLUE_ALPHA){
			type = Matrix.BLACK_WHITE;
		}
		
		Matrix matrix = new Matrix(row, col, type);
		
		for(int i=0, j; i<row; i++){
			for(j=0; j<col; j++){
				matrix.pixels[i][j] = integerToRGB(bufferedImage.getRGB(j, i), matrix.pixels[0][0].length);
			}
		}
		
		return matrix;
	}
	
	public static int[] integerToRGB(int colorInt, int type){
		Color color = new Color(colorInt, true);
		
		int[] colorRGB = new int[type];
		
		if(type==BLACK_WHITE){
			colorRGB[0] = (int) (color.getRed() * 0.3 +
					color.getGreen() * 0.59 +
					color.getBlue() * 0.11);
		}else if(type==BLACK_WHITE_ALPHA){
			colorRGB[0] = (int) (color.getRed() * 0.3 +
					color.getGreen() * 0.59 +
					color.getBlue() * 0.11);
			colorRGB[1] = color.getAlpha();
		}else if(type==RED_GREEN_BLUE){
			colorRGB[0] = color.getRed();
			colorRGB[1] = color.getGreen();
			colorRGB[2] = color.getBlue();
		}else{
			colorRGB[0] = color.getRed();
			colorRGB[1] = color.getGreen();
			colorRGB[2] = color.getBlue();
			colorRGB[3] = color.getAlpha();
		}
		
		return colorRGB;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * 
	 * @param rowStart 0 =< rowStart < rowEnd
	 * @param rowEnd rowStart < rowEnd < numberOfRows
	 * @param colStart 0 =< colStart < colEnd
	 * @param colEnd colStart < colEnd < numberOfCols
	 * @return
	 */
	public Matrix subMatrix(int rowStart, int rowEnd, int colStart, int colEnd){
		if(rowStart>=0 && colStart>=0 && rowEnd<getRows() && colEnd<getCols() &&
			rowEnd>rowStart && colEnd>colStart){
			
			int row = rowEnd-rowStart;
			int col = colEnd-colStart;
			
			Matrix matrix = new Matrix(row, col, this.getType());
			
			for(int i=0; i<row; i++){
				for(int j=0; j<col; j++){
					matrix.pixels[i][j] = this.pixels[rowStart+i][colStart+j].clone();
				}
			}
			
			return matrix;
			
		}else{
			return null;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	public Matrix clone(){
		return subMatrix(0, getRows()-1, 0, getCols()-1);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////

	public void write(String filePath) throws IOException{
		BufferedImage bufferedImage = matrixToBufferedImage(this);
		
		ImageIO.write(bufferedImage, filePath.substring(filePath.lastIndexOf('.')+1), new File(filePath));
	}
	
	/**
	 * @return image as text
	 */
	public String dump(){
		int row = this.pixels.length;
		int col = this.pixels[0].length;
		int type = this.pixels[0][0].length;
		
		String string = "(" + row + "," + col + "," + type + ")\n";
		string += "[";
		
		String pixelString, rowPixelsString;
		for(int i=0, j; i<row; i++){
			
			rowPixelsString = "";
			for(j=0; j<col; j++){
				
				pixelString = "";
				for(int k=0; k<type; k++){
					pixelString += this.pixels[i][j][k] + ",";
				}
				
				rowPixelsString += pixelString;
			}
			
			string += rowPixelsString;
		}
		
		string += "]";
		
		return string;
	}
	
	/**
	 * creates image from test (output of dump)
	 * @param string text string
	 */
	public static Matrix load(String string){
		int startIndex=0, stopIndex=0;
		int row=1, col=1, type=1;
		
		startIndex = 1;
		stopIndex = string.indexOf(',', startIndex);
		row = Integer.valueOf(string.substring(startIndex, stopIndex));
		
		startIndex = stopIndex+1;
		stopIndex = string.indexOf(',', startIndex);
		col = Integer.valueOf(string.substring(startIndex, stopIndex));
		
		startIndex = stopIndex+1;
		stopIndex = string.indexOf(')', startIndex);
		type = Integer.valueOf(string.substring(startIndex, stopIndex));
		
		///
		
		Matrix matrix = new Matrix(row, col, type);
		
		stopIndex = string.indexOf('[', stopIndex);
		for(int i=0; i<row; i++){
			for(int j=0; j<col; j++){
				
				for(int k=0; k<type; k++){
					startIndex = stopIndex+1;
					stopIndex = string.indexOf(',', startIndex);
					
					matrix.pixels[i][j][k] = Integer.valueOf(string.substring(startIndex, stopIndex));
				}
			}
		}
		
		return matrix;
	}
	
	/**
	 * @return text image
	 */
	public String toString(){
		int row = this.pixels.length;
		int col = this.pixels[0].length;
		int type = this.pixels[0][0].length;
		
		String string = "(" + row + "," + col + "," + type + ")\n";
		string += "[\n";
		
		String pixelString, rowPixelsString;
		for(int i=0, j, k; i<row; i++){
	
			rowPixelsString = "[";
			for(j=0; j<col; j++){
				
				pixelString = "("+this.pixels[i][j][0];
				for(k=1; k<type; k++){
					pixelString += "." + this.pixels[i][j][k];
				}
				
				rowPixelsString += pixelString + ") ";
			}
			
			string += rowPixelsString+"]\n";
		}
		
		string += "]";
		
		return string;
	}
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**TEST_ONLY**/
	public static void main(String[] args) {
		try {
			Matrix matrix = new Matrix("C:\\Users\\admin\\Desktop\\a.jpg", Matrix.RED_GREEN_BLUE);
			System.out.println(matrix.toString());
			//System.out.println(matrix.dump());
			//matrix.write("C:\\Users\\admin\\Desktop\\b.png");
			//Matrix mat2 = Matrix.load(matrix.dump());
//			//mat2 = MatrixUtil.rotate(mat2, 30);
			
			//System.out.println(mat2.pixels[10][10] + ", " + mat2.getPixel(10, 10));
			//mat2.write("C:\\Users\\admin\\Desktop\\1.png");
			
			System.out.println("OPERATION SUCCESSFUL!!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
