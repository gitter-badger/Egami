package demo.com;

import java.io.IOException;

import com.minhaskamal.egami.matrix.*;
import com.minhaskamal.egami.view.ImageViewer;

public class ImageEditor2 {
	public static void main(String[] args) throws IOException {
		///create matrix object
//		Matrix matrix = new Matrix("src/demo/res/imgs/real.png", Matrix.BLACK_WHITE);
		Matrix matrix = new Matrix("src/demo/res/imgs/squares.png", Matrix.RED_GREEN_BLUE_ALPHA);
		
		///convert RGB image to Grayscale image
		//matrix = MatrixUtilitiesPrimary.convertTo(matrix, Matrix.BLACK_WHITE);
		
		ImageViewer.viewImage(Matrix.matrixToBufferedImage(matrix));
	}
}
