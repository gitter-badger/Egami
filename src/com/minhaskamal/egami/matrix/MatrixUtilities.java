/****************************************************************************************************************
* Developer: Minhas Kamal(BSSE-0509, IIT, DU)																	*
* Date: 10-Mar-2015																								*
* Modification Date: 01-Jan-2016																				*
****************************************************************************************************************/

package com.minhaskamal.egami.matrix;

public class MatrixUtilities {
	public static Matrix convertTo(Matrix matrix, int convertType){
		int row = matrix.getRows();
		int col = matrix.getCols();
		int type = matrix.getType();
		
		Matrix matrix2 = new Matrix(row, col, convertType);
		
		
		if(type==Matrix.BLACK_WHITE){
			
			if(convertType==Matrix.BLACK_WHITE){
				for(int i=0, j; i<row; i++){
					for(j=0; j<col; j++){
						matrix2.pixels[i][j][0] = matrix.pixels[i][j][0];
					}
				}
				
			}else if(convertType==Matrix.BLACK_WHITE_ALPHA){
				for(int i=0, j; i<row; i++){
					for(j=0; j<col; j++){
						matrix2.pixels[i][j][0] = matrix.pixels[i][j][0];
						matrix2.pixels[i][j][1] = 0;
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
						matrix2.pixels[i][j][3] = 0;
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
				for(int i=0, j; i<row; i++){
					for(j=0; j<col; j++){
						matrix2.pixels[i][j][0] = matrix.pixels[i][j][0];
						matrix2.pixels[i][j][1] = matrix.pixels[i][j][1];
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
						matrix2.pixels[i][j][3] = matrix.pixels[i][j][1];
					}
				}
				
			}
			
		}else if(type==Matrix.RED_GREEN_BLUE){
			
			if(convertType==Matrix.BLACK_WHITE){
				for(int i=0, j; i<row; i++){
					for(j=0; j<col; j++){
						matrix2.pixels[i][j][0] = (matrix.pixels[i][j][0]+matrix.pixels[i][j][1]+matrix.pixels[i][j][2])/3;
					}
				}
				
			}else if(convertType==Matrix.BLACK_WHITE_ALPHA){
				for(int i=0, j; i<row; i++){
					for(j=0; j<col; j++){
						matrix2.pixels[i][j][0] = (matrix.pixels[i][j][0]+matrix.pixels[i][j][1]+matrix.pixels[i][j][2])/3;
						matrix2.pixels[i][j][1] = 0;
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
				for(int i=0, j; i<row; i++){
					for(j=0; j<col; j++){
						matrix2.pixels[i][j][0] = matrix.pixels[i][j][0];
						matrix2.pixels[i][j][1] = matrix.pixels[i][j][1];
						matrix2.pixels[i][j][2] = matrix.pixels[i][j][2];
						matrix2.pixels[i][j][3] = 0;
					}
				}
				
			}
			
		}else{
			if(convertType==Matrix.BLACK_WHITE){
				for(int i=0, j; i<row; i++){
					for(j=0; j<col; j++){
						matrix2.pixels[i][j][0] = (matrix.pixels[i][j][0]+matrix.pixels[i][j][1]+matrix.pixels[i][j][2])/3;
					}
				}
				
			}else if(convertType==Matrix.BLACK_WHITE_ALPHA){
				for(int i=0, j; i<row; i++){
					for(j=0; j<col; j++){
						matrix2.pixels[i][j][0] = (matrix.pixels[i][j][0]+matrix.pixels[i][j][1]+matrix.pixels[i][j][2])/3;
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
				for(int i=0, j; i<row; i++){
					for(j=0; j<col; j++){
						matrix2.pixels[i][j][0] = matrix.pixels[i][j][0];
						matrix2.pixels[i][j][1] = matrix.pixels[i][j][1];
						matrix2.pixels[i][j][2] = matrix.pixels[i][j][2];
						matrix2.pixels[i][j][3] = matrix.pixels[i][j][3];
					}
				}
			}
			
		}
		
		return matrix2;
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * @param angle in degree
	 */
	public static Matrix rotate(Matrix matrix, double angle){
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
		if(angle==0){
			return matrix;
		}
		
		////////////////
		
		int row = matrix.getRows();
		int col = matrix.getCols();
		
		double sinX = Math.sin(Math.toRadians(angle));
		double cosX = Math.cos(Math.toRadians(angle));
		
		double height_sinX = row*sinX;
		
		Matrix matrix2 = new Matrix( (int)(row*cosX+col*sinX), (int)(height_sinX+col*cosX), matrix.getType() );
		
		
		for(int i=0; i<row; i++){
			for(int j=0; j<col; j++){
				int newI = (int) (i*cosX + j*sinX);
				int newJ = (int) (-i*sinX + j*cosX + height_sinX);
				
				//System.out.println(newI + " " + newJ + " - " + i + " " + j);///test
				matrix2.setPixel(newI, newJ, matrix.getPixel(i, j));
			}
		}
		
		return matrix2;
	}
	
	public static Matrix rotateLeft(Matrix matrix){
		int newRow = matrix.getCols();
		int newCol = matrix.getRows();
		
		Matrix matrix2 = new Matrix(newRow, newCol, matrix.getType());
		
		for(int i=0; i<newRow; i++){
			for(int j=0; j<newCol; j++){
				matrix2.setPixel(newRow-1-i, j, matrix.getPixel(j, i));
			}
		}
		
		return matrix2;
	}
	
	public static Matrix rotateRight(Matrix matrix){
		int newRow = matrix.getCols();
		int newCol = matrix.getRows();
		
		Matrix matrix2 = new Matrix(newRow, newCol, matrix.getType());
		
		for(int i=0; i<newRow; i++){
			for(int j=0; j<newCol; j++){
				matrix2.setPixel(i, newCol-1-j, matrix.getPixel(j, i));
			}
		}
		
		return matrix2;
	}
	
	public static Matrix flipVertical(Matrix matrix){
		int row = matrix.getRows();
		int col = matrix.getCols();
		
		Matrix matrix2 = new Matrix(row, col, matrix.getType());
		
		for(int i=0; i<row; i++){
			for(int j=0; j<col; j++){
				matrix2.setPixel(i, col-j-1, matrix.getPixel(i, j));
			}
		}
		
		return matrix2;
	}
	
	public static Matrix flipHorizontal(Matrix matrix){
		int row = matrix.getRows();
		int col = matrix.getCols();
		
		Matrix matrix2 = new Matrix(row, col, matrix.getType());
		
		for(int i=0; i<row; i++){
			for(int j=0; j<col; j++){
				matrix2.setPixel(row-i-1, j, matrix.getPixel(i, j));
			}
		}
		
		return matrix2;
	}

	public static Matrix crop(Matrix matrix, int left, int right, int top, int down){
		return matrix.subMatrix(top, matrix.getRows()-down, left, matrix.getCols()-right);
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static void main(String[] args) throws Exception {
		Matrix matrix = new Matrix("C:\\Users\\admin\\Desktop\\a.jpg");
		
		//matrix = rotate(matrix, 20);
		
//		MatrixUtil.write(matrix, "C:\\Users\\admin\\Desktop\\d.png");
		
//		matrix = convertTo(matrix, Matrix.RED_GREEN_BLUE);
		
		matrix = rotate(matrix, 120);
		
//		matrix = flipHorizontal(matrix);
//		matrix = flipVertical(matrix);
//		matrix = crop(matrix, 5, 2, 10, 15);
//		matrix = rotateLeft(matrix);
//		matrix = rotateRight(matrix);
		
		matrix.write("C:\\Users\\admin\\Desktop\\b.png");
		
		System.out.println("OPERATION SUCCESSFUL!!");
	}
}
