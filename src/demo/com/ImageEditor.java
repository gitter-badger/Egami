package demo.com;

import java.io.IOException;

import com.minhaskamal.egami.matrix.*;
import com.minhaskamal.egami.view.ImageViewer;

public class ImageEditor {
	public static void main(String[] args) throws IOException {
		///create matrix object
		Matrix matrix = new Matrix("src/demo/res/imgs/real.png", Matrix.RED_GREEN_BLUE_ALPHA);
//		Matrix matrix = new Matrix("src/demo/res/imgs/shapes.png", Matrix.BLACK_WHITE);
		
		///convert RGB image to Grayscale image
		matrix = MatrixUtilitiesPrimary.convertTo(matrix, Matrix.BLACK_WHITE);
		
		///test load, dump & clone
//		Matrix newMatrix = Matrix.load(matrix.dump());
//		matrix = newMatrix.clone();
		
		///create border
		matrix = MatrixUtilitiesPrimary.border(matrix, 4, matrix.getPixel(0, 0));
		
		///skew border
//		matrix = MatrixUtilitiesPrimary.skewVertical(matrix, 30);
				
		///rotate
		matrix = MatrixUtilitiesSecondary.rotate(matrix, 20);
			
		///rotate method 2
//		matrix = MatrixUtilitiesSecondary.rotate2(matrix, 15);
		
		///resizing
//		matrix = MatrixUtilitiesSecondary.resize(matrix, 200);
				
		///masking
//		double[][] mask = {
//				{-1, 0, 1},
//				{-1, 0, 1},
//				{-1, 0, 1}
//		};
//		double[][] mask = {
//				{4.0/33, 4.0/33, 4.0/33},
//				{4.0/33, 1.0/18, 4.0/33},
//				{4.0/33, 4.0/33, 4.0/33}
//		};
//		matrix = MatrixUtilitiesSecondary.masking(matrix, mask);
		
		
		///show in image viewer
		ImageViewer.viewImage(Matrix.matrixToBufferedImage(matrix));
	}
}
