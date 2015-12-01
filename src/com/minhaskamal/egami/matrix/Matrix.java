/****************************************************************************************************************
* Developer: Minhas Kamal(BSSE-0509, IIT, DU)																	*
* Date: 10-Mar-2015																								*
* Modification Date: 30-Dec-2015																				*
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
	
	int[][][] pixels;
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
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
		}if(type<1 || type>4){
			type=4;
		}
		
		this.pixels = new int[row][col][type];
	}
	
	/**
	 * Type is- <code>RED_GREEN_BLUE_ALPHA</code>
	 */
	public Matrix(String imageFilePath) throws IOException {
		this(ImageIO.read(new File(imageFilePath)));
	}
	
	public Matrix(BufferedImage bufferedImage){
		this.pixels = bufferedImageToMatrix(bufferedImage).pixels;
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * @return height of image
	 */
	public int getRows(){
		return pixels.length;
	}
	
	/**
	 * @return width of image
	 */
	public int getCols(){
		return pixels[0].length;
	}
	
	/**
	 * @return 1 -> <code>BLACK_WHITE</code>,<br/>
	 * 2 -> <code>BLACK_WHITE_ALPHA</code>,<br/>
	 * 3 -> <code>RED_GREEN_BLUE</code>,<br/>
	 * 4 -> <code>RED_GREEN_BLUE_ALPHA</code>
	 */
	public int getType(){
		return pixels[0][0].length;
	}
	
	/**
	 * @param rowNo 0 =< rowNo < number of rows
	 * @param colNo 0 =< colNo < number of columns
	 */
	public int[] getPixel(int rowNo, int colNo){
		if(rowNo>=0 && colNo>=0 && rowNo<pixels.length && colNo<pixels[0].length){
			return pixels[rowNo][colNo].clone();
		}else{
			return null;
		}
	}
	
	/**
	 * @param rowNo 0 =< rowNo < number of rows
	 * @param colNo 0 =< colNo < number of columns
	 */
	public void setPixel(int rowNo, int colNo, int[] value){
		if(rowNo>=0 && colNo>=0 && rowNo<pixels.length && colNo<pixels[0].length && 
				value.length==pixels[0][0].length){
			pixels[rowNo][colNo] = value.clone();
		}
	}

	
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Type is converted to <code>RED_GREEN_BLUE_ALPHA</code>
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
	 * @return <code>RED_GREEN_BLUE_ALPHA<code> type <code>Matrix</code>
	 */
	public static Matrix bufferedImageToMatrix(BufferedImage bufferedImage){
		int row = bufferedImage.getHeight();
		int col = bufferedImage.getWidth();
		
		Matrix matrix = new Matrix(row, col, RED_GREEN_BLUE_ALPHA);
		
		for(int i=0, j; i<row; i++){
			for(j=0; j<col; j++){
				matrix.pixels[i][j] = integerToRGB(bufferedImage.getRGB(j, i));
			}
		}
		
		return matrix;
	}
	
	public static int[] integerToRGB(int colorInt){
		Color color = new Color(colorInt, true);
		
		int[] colorRGB = {color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()};
		
		return colorRGB;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Matrix subMatrix(int rowStart, int rowEnd, int colStart, int colEnd){
		if(rowStart>=0 && colStart>=0 && rowEnd<pixels.length && colEnd<pixels[0].length &&
			rowEnd>rowStart && colEnd>colStart){
			
			int row = rowEnd-rowStart;
			int col = colEnd-colStart;
			
			Matrix matrix = new Matrix(row, col, this.getType());
			
			for(int i=0; i<row; i++){
				for(int j=0; j<col; j++){
					matrix.pixels[i][j] = this.pixels[rowStart+i][colStart+j];
				}
			}
			
			return matrix;
			
		}else{
			return null;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////

	public void write(String filePath) throws IOException{
		BufferedImage bufferedImage = matrixToBufferedImage(this);
		
		ImageIO.write(bufferedImage, filePath.substring(filePath.lastIndexOf('.')+1), new File(filePath));
	}
	
	/**
	 * @return text string
	 */
	public String dump(){
		int row = pixels.length;
		int col = pixels[0].length;
		int type = pixels[0][0].length;
		
		String string = "(" + row + "," + col + "," + type + ")\n";
		string += "[";
		
		String pixelString;
		for(int i=0, j; i<row; i++){
			for(j=0; j<col; j++){
				
				pixelString = "";
				for(int k=0; k<type; k++){
					pixelString += pixels[i][j][k] + ",";
				}
				string += pixelString;
			}
		}
		string += "]";
		
		return string;
	}
	
	/**
	 * 
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
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**TEST_ONLY**/
	public static void main(String[] args) {
		try {
			Matrix matrix = new Matrix("C:\\Users\\admin\\Desktop\\a.jpg");
			
			Matrix mat2 = Matrix.load(matrix.dump());
			//System.out.println(mat2.dump());
//			//mat2 = MatrixUtil.rotate(mat2, 30);
			
			System.out.println(mat2.pixels[10][10] + ", " + mat2.getPixel(10, 10));
			mat2.write("C:\\Users\\admin\\Desktop\\1.png");
			
			System.out.println("OPERATION SUCCESSFUL!!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
