/****************************************************************************************************************
* Developer: Minhas Kamal(minhaskamal024@gmail.com)																*
* Date: 02-Feb-2016																								*
****************************************************************************************************************/

package demo.com;

import java.io.IOException;
import javax.swing.UIManager;
import com.minhaskamal.egami.matrix.*;
import com.minhaskamal.egami.matrixUtil.MatrixUtilitiesAdvanced;
import com.minhaskamal.egami.view.ImageViewer;

public class ImageEditor2 {
	public static void main(String[] args) throws IOException {
		///create matrix object
		Matrix matrix = new Matrix("src/demo/res/imgs/real.png", Matrix.BLACK_WHITE);
//		Matrix matrix = new Matrix("src/demo/res/imgs/shapes.png", Matrix.BLACK_WHITE);
		
		
		///image quantization
		matrix = MatrixUtilitiesAdvanced.applyQuantization(matrix, 5);
		//mat = spatialBrightnessEqualizer(mat, 4);
		//mat = convertToEdgePixels(mat, 30);
		//mat = histogramEqualizer(mat);
		//mat = convertToLocalBinaryPattern(mat);
		
		
		///show result in image viewer
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch(Exception e){/*do nothing*/}
		ImageViewer.viewImage(Matrix.matrixToBufferedImage(matrix));
	}
}
