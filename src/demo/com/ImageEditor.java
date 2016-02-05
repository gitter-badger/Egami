/****************************************************************************************************************
* Developer: Minhas Kamal(minhaskamal024@gmail.com)																*
* Date: 01-Feb-2016																								*
****************************************************************************************************************/

package demo.com;

import java.io.IOException;

import javax.swing.UIManager;

import com.minhaskamal.egami.matrix.*;
import com.minhaskamal.egami.matrixUtil.*;
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
//		matrix = MatrixUtilitiesPrimary.border(matrix, 4);
		
		///skew border
//		matrix = MatrixUtilitiesPrimary.skewVertical(matrix, 30);
				
		///rotate
		//matrix = MatrixUtilitiesSecondary.rotate(matrix, 20);
			
		///rotate method 2
//		matrix = MatrixUtilitiesSecondary.rotate2(matrix, 15);
		
		///resizing
//		matrix = MatrixUtilitiesSecondary.resize(matrix, 200);
				
		///masking
		double[][] mask = {
				{-1, 0, 1},
				{-1, 0, 1},
				{-1, 0, 1}
		};
//		double[][] mask = {
//				{4.0/33, 4.0/33, 4.0/33},
//				{4.0/33, 1.0/33, 4.0/33},
//				{4.0/33, 4.0/33, 4.0/33}
//		};
		matrix = MatrixUtilitiesSecondary.applyMask(matrix, mask);
		
		
		///show result in image viewer
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch(Exception e){/*do nothing*/}
		ImageViewer.viewImage(Matrix.matrixToBufferedImage(matrix));
	}
}
