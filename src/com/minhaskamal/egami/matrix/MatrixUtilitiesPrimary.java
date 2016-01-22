/****************************************************************************************************************
* Developer: Minhas Kamal(minhaskamal024@gmail.com)																*
* Date: 10-Mar-2015																								*
* Modification Date: 01-Jan-2016																				*
****************************************************************************************************************/

package com.minhaskamal.egami.matrix;

public class MatrixUtilitiesPrimary {
	///converter//////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * 
	 * @param matrix
	 * @param convertType
	 * @return
	 */
	public static Matrix convertTo(Matrix matrix, int convertType){
		int row = matrix.getRows();
		int col = matrix.getCols();
		int type = matrix.getType();
		
		Matrix matrix2 = new Matrix(row, col, convertType);
		
		
		if(type==Matrix.BLACK_WHITE){
			
			if(convertType==Matrix.BLACK_WHITE){
				matrix2 = matrix;
				
			}else if(convertType==Matrix.BLACK_WHITE_ALPHA){
				for(int i=0, j; i<row; i++){
					for(j=0; j<col; j++){
						matrix2.pixels[i][j][0] = matrix.pixels[i][j][0];
						matrix2.pixels[i][j][1] = 255;
					}
				}
				
			}else if(convertType==Matrix.RED_GREEN_BLUE){
				for(int i=0, j; i<row; i++){
					for(j=0; j<col; j++){
						matrix2.pixels[i][j][0] = matrix.pixels[i][j][0];
						matrix2.pixels[i][j][1] = matrix.pixels[i][j][0];
						matrix2.pixels[i][j][2] = matrix.pixels[i][j][0];
					}
				}
				
			}else{
				for(int i=0, j; i<row; i++){
					for(j=0; j<col; j++){
						matrix2.pixels[i][j][0] = matrix.pixels[i][j][0];
						matrix2.pixels[i][j][1] = matrix.pixels[i][j][0];
						matrix2.pixels[i][j][2] = matrix.pixels[i][j][0];
						matrix2.pixels[i][j][3] = 255;
					}
				}
				
			}
			
			
		}else if(type==Matrix.BLACK_WHITE_ALPHA){
			
			if(convertType==Matrix.BLACK_WHITE){
				for(int i=0, j; i<row; i++){
					for(j=0; j<col; j++){
						matrix2.pixels[i][j][0] = matrix.pixels[i][j][0];
					}
				}
				
			}else if(convertType==Matrix.BLACK_WHITE_ALPHA){
				matrix2 = matrix;
				
			}else if(convertType==Matrix.RED_GREEN_BLUE){
				for(int i=0, j; i<row; i++){
					for(j=0; j<col; j++){
						matrix2.pixels[i][j][0] = matrix.pixels[i][j][0];
						matrix2.pixels[i][j][1] = matrix.pixels[i][j][0];
						matrix2.pixels[i][j][2] = matrix.pixels[i][j][0];
					}
				}
				
			}else{
				for(int i=0, j; i<row; i++){
					for(j=0; j<col; j++){
						matrix2.pixels[i][j][0] = matrix.pixels[i][j][0];
						matrix2.pixels[i][j][1] = matrix.pixels[i][j][0];
						matrix2.pixels[i][j][2] = matrix.pixels[i][j][0];
						matrix2.pixels[i][j][3] = matrix.pixels[i][j][1];
					}
				}
				
			}
			
		}else if(type==Matrix.RED_GREEN_BLUE){
			
			if(convertType==Matrix.BLACK_WHITE){
				for(int i=0, j; i<row; i++){
					for(j=0; j<col; j++){
						matrix2.pixels[i][j][0] = (int) (matrix.pixels[i][j][0] * 0.3 +
														matrix.pixels[i][j][1] * 0.59 +
														matrix.pixels[i][j][2] * 0.11);
					}
				}
				
			}else if(convertType==Matrix.BLACK_WHITE_ALPHA){
				for(int i=0, j; i<row; i++){
					for(j=0; j<col; j++){
						matrix2.pixels[i][j][0] = (int) (matrix.pixels[i][j][0] * 0.3 +
														matrix.pixels[i][j][1] * 0.59 +
														matrix.pixels[i][j][2] * 0.11);
						matrix2.pixels[i][j][1] = 255;
					}
				}
				
			}else if(convertType==Matrix.RED_GREEN_BLUE){
				matrix2 = matrix;
				
			}else{
				for(int i=0, j; i<row; i++){
					for(j=0; j<col; j++){
						matrix2.pixels[i][j][0] = matrix.pixels[i][j][0];
						matrix2.pixels[i][j][1] = matrix.pixels[i][j][1];
						matrix2.pixels[i][j][2] = matrix.pixels[i][j][2];
						matrix2.pixels[i][j][3] = 255;
					}
				}
				
			}
			
		}else{
			if(convertType==Matrix.BLACK_WHITE){
				for(int i=0, j; i<row; i++){
					for(j=0; j<col; j++){
						matrix2.pixels[i][j][0] = (int) (matrix.pixels[i][j][0] * 0.3 +
														matrix.pixels[i][j][1] * 0.59 +
														matrix.pixels[i][j][2] * 0.11);
					}
				}
				
			}else if(convertType==Matrix.BLACK_WHITE_ALPHA){
				for(int i=0, j; i<row; i++){
					for(j=0; j<col; j++){
						matrix2.pixels[i][j][0] = (int) (matrix.pixels[i][j][0] * 0.3 +
														matrix.pixels[i][j][1] * 0.59 +
														matrix.pixels[i][j][2] * 0.11);
						matrix2.pixels[i][j][1] = matrix.pixels[i][j][3];
					}
				}
				
			}else if(convertType==Matrix.RED_GREEN_BLUE){
				for(int i=0, j; i<row; i++){
					for(j=0; j<col; j++){
						matrix2.pixels[i][j][0] = matrix.pixels[i][j][0];
						matrix2.pixels[i][j][1] = matrix.pixels[i][j][1];
						matrix2.pixels[i][j][2] = matrix.pixels[i][j][2];
					}
				}
				
			}else{
				matrix2 = matrix;
			}
			
		}
		
		return matrix2;
	}
	
	///rotator////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Rotate 90 degree to left.
	 * @param matrix
	 * @return
	 */
	public static Matrix rotateLeft(Matrix matrix){
		int newRow = matrix.getCols();
		int newCol = matrix.getRows();
		
		Matrix matrix2 = new Matrix(newRow, newCol, matrix.getType());
		
		for(int i=0; i<newRow; i++){
			for(int j=0; j<newCol; j++){
				matrix2.pixels[newRow-1-i][j] = matrix.pixels[j][i].clone();
			}
		}
		
		return matrix2;
	}
	
	/**
	 * Rotate 90 degree to right.
	 * @param matrix
	 * @return
	 */
	public static Matrix rotateRight(Matrix matrix){
		int newRow = matrix.getCols();
		int newCol = matrix.getRows();
		
		Matrix matrix2 = new Matrix(newRow, newCol, matrix.getType());
		
		for(int i=0; i<newRow; i++){
			for(int j=0; j<newCol; j++){
				matrix2.pixels[i][newCol-1-j] = matrix.pixels[j][i].clone();
			}
		}
		
		return matrix2;
	}
	
	///flipper////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * 
	 * @param matrix
	 * @return
	 */
	public static Matrix flipVertical(Matrix matrix){
		int row = matrix.getRows();
		int col = matrix.getCols();
		
		Matrix matrix2 = new Matrix(row, col, matrix.getType());
		
		for(int i=0; i<row; i++){
			for(int j=0; j<col; j++){
				matrix2.pixels[i][col-j-1] = matrix.pixels[i][j].clone();
			}
		}
		
		return matrix2;
	}
	
	/**
	 * 
	 * @param matrix
	 * @return
	 */
	public static Matrix flipHorizontal(Matrix matrix){
		int row = matrix.getRows();
		int col = matrix.getCols();
		
		Matrix matrix2 = new Matrix(row, col, matrix.getType());
		
		for(int i=0; i<row; i++){
			for(int j=0; j<col; j++){
				matrix2.pixels[row-i-1][j] = matrix.pixels[i][j].clone();
			}
		}
		
		return matrix2;
	}

	///skewer/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * 
	 * @param matrix
	 * @param angle
	 * @return
	 */
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
				matrix2.pixels[i][j+skew+x] = matrix.pixels[i][j].clone();
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
				matrix2.pixels[i+skew+x][j] = matrix.pixels[i][j].clone();
			}
		}
		
		return matrix2;
	}
	
	///cropper////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * 
	 * @param matrix
	 * @param left
	 * @param right
	 * @param top
	 * @param down
	 * @return
	 */
	public static Matrix crop(Matrix matrix, int left, int right, int top, int down){
		return matrix.subMatrix(top, matrix.getRows()-down, left, matrix.getCols()-right);
	}
	
	///bordering//////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @param matrix
	 * @param breadth
	 * @param pixel
	 * @return
	 */
	public static Matrix border(Matrix matrix, int breadth, int pixel[]){
		if(breadth<0 || pixel.length!=matrix.getType()){
			return null;
		}
		
		int rows = matrix.getRows(),
			cols = matrix.getCols();
			
		int newRows = rows+(breadth*2),
			newCols = cols+(breadth*2);
		
		Matrix matrix2 = new Matrix(newRows, newCols, matrix.getType());
		
		for(int i=0; i<newRows; i++){
			for(int j=0; j<newCols; j++){
				matrix2.pixels[i][j] = pixel;
			}
		}
		
		for(int i=0; i<rows; i++){
			for(int j=0; j<cols; j++){
				matrix2.pixels[i+breadth][j+breadth] = matrix.pixels[i][j];
			}
		}
		
		return matrix2;
	}
	
	
	///test only
	public static void main(String[] args) throws Exception {
		Matrix matrix = new Matrix("C:\\Users\\admin\\Desktop\\c.png", Matrix.RED_GREEN_BLUE_ALPHA);
		
		//matrix = rotate(matrix, 20);
		
//		MatrixUtil.write(matrix, "C:\\Users\\admin\\Desktop\\d.png");
		
		//matrix = convertTo(matrix, Matrix.RED_GREEN_BLUE_ALPHA);
		
//		matrix = skewHorizontal(matrix, 89);
//		matrix = flipVertical(matrix);
//		matrix = crop(matrix, 5, 2, 10, 15);
//		matrix = rotateLeft(matrix);
//		matrix = rotateRight(matrix);
		
		matrix.write("C:\\Users\\admin\\Desktop\\d.png");
		
		System.out.println("OPERATION SUCCESSFUL!!");
	}
}
