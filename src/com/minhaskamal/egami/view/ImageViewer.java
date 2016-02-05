/****************************************************************************************************************
* Developer: Minhas Kamal(minhaskamal024@gmail.com)																*
* Date: 28-Aug-2015																								*
****************************************************************************************************************/

package com.minhaskamal.egami.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * @author Minhas Kamal
 */
public class ImageViewer{
	// GUI Declaration
	private ImageViewerGui gui;
	
	//**
	// Variable Declaration 																	#*******D*******#
	//**
	private JLabel jLabel;
	private JButton jButtonLoadImage;
	private JButton[] jButtonsZoom;
	
	private String imageFilePath;
	
	private double imageWidth, imageHeight;
	// End of Variable Declaration 																#_______D_______#

	/***##Constructor##***/
	public ImageViewer(){
		this.imageFilePath="";
		this.imageWidth=0;
		this.imageHeight=0;
		
		initialComponent();
	}
	
	/**
	 * Method for Initializing all the GUI variables and placing them all to specific space on 
	 * the frame. It also specifies criteria of the main frame.
	 */
	private void initialComponent() {
		// GUI Initialization
		this.gui = new ImageViewerGui();
		this.gui.setVisible(true);
		
		//**
		// Assignation 																			#*******A*******#
		//**
		this.jLabel = this.gui.jLabel;
		this.jButtonLoadImage = this.gui.jButtonLoadImage;
		this.jButtonsZoom = this.gui.jButtonsZoom;
		// End of Assignation																	#_______A_______#

		//**
		// Adding Action Events & Other Attributes												#*******AA*******#
		//**
		this.jButtonLoadImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonLoadImagePerformed(evt);
			}
		});
		
		this.jButtonsZoom[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonsZoomActionPerformed(evt);
			}
		});
		this.jButtonsZoom[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonsZoomActionPerformed(evt);
			}
		});
		// End of Adding Action Events & Other Attributes										#_______AA_______#
	}

	//**
	// Action Events 																			#*******AE*******#
	//**
	private void jButtonLoadImagePerformed(ActionEvent evt){
		String[] extentions = new String[]{"jpg", "jpeg", "png", "gif", "bmp", "wbmp"};
		String filePath = new FileChooser(extentions).chooseFilePathFromComputer();
		if(!filePath.contains(".")){
			return ;
		}
		
		loadImage(filePath);
	}
	
	private void jButtonsZoomActionPerformed(ActionEvent evt){
		if(evt.getSource() == this.jButtonsZoom[0]){
			resizeImage(1.1);
		}else{
			resizeImage(.9);
		}
	}
	// End of Action Events 																	#_______AE_______#

	//**
	// Auxiliary Methods 																		#*******AM*******#
	//**
	
	
	public void loadImage(String imageFilePath){
		try {
			BufferedImage bufferedImage = ImageIO.read(new File(imageFilePath));
			this.imageFilePath = imageFilePath;
			
			loadImage(bufferedImage);
		} catch (Exception e) {
			e.printStackTrace();
			//new Message("Error opening image.", 420);
		}
	}
	
	public void loadImage(BufferedImage bufferedImage){
		ImageIcon image = new ImageIcon(bufferedImage);
		this.imageWidth=image.getIconWidth();
		this.imageHeight=image.getIconHeight();
		
		this.jLabel.setToolTipText("width: " + this.imageWidth + "px, height: " + this.imageHeight + "px");
		
		if(gui.getWidth()-40 < this.imageWidth){
			int width = gui.getWidth()-40;
			this.imageHeight = this.imageHeight * ((double)width/this.imageWidth);
			this.imageWidth=width;
			
			this.jLabel.setIcon(new ImageIcon((image.getImage()).
				getScaledInstance((int)this.imageWidth, (int)this.imageHeight, Image.SCALE_SMOOTH)));
		}else{
			this.jLabel.setIcon(new ImageIcon( image.getImage() ));
		}
	}
	
	private void resizeImage(double multiplier){
		
		double newImageWidth = this.imageWidth*multiplier;
		double newImageHeight = this.imageHeight*multiplier;
		
		if(newImageWidth<2 || newImageHeight<2 || newImageWidth>10000 || newImageHeight>10000 ){
			return;
		}
		
		if(this.jLabel.getIcon()!=null){
			this.imageWidth = newImageWidth;
			this.imageHeight = newImageHeight;

			ImageIcon imageIcon;
			try{
				imageIcon = new ImageIcon(ImageIO.read( new File(this.imageFilePath) ));
			}catch (IOException e) {
				imageIcon = (ImageIcon) this.jLabel.getIcon();
			}
			
			this.jLabel.setIcon(new ImageIcon((imageIcon.getImage()).
					getScaledInstance((int)this.imageWidth, (int)this.imageHeight, Image.SCALE_SMOOTH)));
		}
		
	}
	
	public void attachTo(JComponent jComponent) {
		jComponent.add(this.gui);
		jComponent.revalidate();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////
	
	public static void viewImage(String imagePath){
		ImageViewer imageViewer = create();
		
		if(imagePath.contains(".")){
			imageViewer.loadImage(imagePath);
		}
	}
	
	public static void viewImage(BufferedImage bufferedImage){
		ImageViewer imageViewer = create();
		imageViewer.loadImage(bufferedImage);
	}
	
	public static ImageViewer create(){
		JFrame jFrame = new JFrame("Image Viewer");
		jFrame.setBounds(30, 30, 400, 400);
		
		JLabel jLabel = new JLabel();
		jLabel.setLayout(new GridLayout());
		
		ImageViewer imageViewer = new ImageViewer();
		imageViewer.attachTo(jLabel);
		jFrame.add(jLabel);
		
		jFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
		jFrame.setTitle("Egami Image Viewer");
		jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jFrame.setVisible(true);
		
		return imageViewer;
	}
	// End of Auxiliary Methods 																#_______AM_______#
	
	//**
	// Overridden Methods 																		#*******OM*******#
	//**
	
	// End of Overridden Methods 																#_______OM_______#
	
//	///test only
//	/********* Main Method *********/
//	public static void main(String args[]) {
//		/*// Set the NIMBUS look and feel //*/
//		try {
//			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
//		} catch (Exception ex) {
//			// do nothing if operation is unsuccessful
//		}
//
////		ImageViewer.create();
//		ImageViewer.viewImage("src/demo/res/imgs/real.png");
//	}
}

