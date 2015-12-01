/****************************************************************************************************************
* Developer: Minhas Kamal(BSSE-0509, IIT, DU)																	*
* Date: 28-Aug-2015																								*
****************************************************************************************************************/

package com.minhaskamal.egami.view;

import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;

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
		imageFilePath="";
		imageWidth=0;
		imageHeight=0;
		
		initialComponent();
	}
	
	/**
	 * Method for Initializing all the GUI variables and placing them all to specific space on 
	 * the frame. It also specifies criteria of the main frame.
	 */
	private void initialComponent() {
		// GUI Initialization
		gui = new ImageViewerGui();
		gui.setVisible(true);
		
		//**
		// Assignation 																			#*******A*******#
		//**
		jLabel = gui.jLabel;
		jButtonLoadImage = gui.jButtonLoadImage;
		jButtonsZoom = gui.jButtonsZoom;
		// End of Assignation																	#_______A_______#

		//**
		// Adding Action Events & Other Attributes												#*******AA*******#
		//**
		jButtonLoadImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonLoadImagePerformed(evt);
			}
		});
		
		jButtonsZoom[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonsZoomActionPerformed(evt);
			}
		});
		jButtonsZoom[1].addActionListener(new ActionListener() {
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
		if(evt.getSource() == jButtonsZoom[0]){
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
			ImageIcon image = new ImageIcon(ImageIO.read(new File(imageFilePath)));
			this.imageFilePath = imageFilePath;
			this.imageWidth=image.getIconWidth();
			this.imageHeight=image.getIconHeight();
			//Thread.sleep(1000);///don't remember the reason
			
			if(gui.getWidth()-40 < imageWidth){
				int width = gui.getWidth()-40;
				imageHeight = imageHeight * ((double)width/imageWidth);
				this.imageWidth=width;
				
				jLabel.setIcon(new ImageIcon((image.getImage()).
					getScaledInstance((int)imageWidth, (int)imageHeight, Image.SCALE_SMOOTH)));
			}else{
				jLabel.setIcon(new ImageIcon( image.getImage() ));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			//new Message("Error opening image.", 420);
		}
	}
	
	private void resizeImage(double multiplier){
		
		double newImageWidth = imageWidth*multiplier;
		double newImageHeight = imageHeight*multiplier;
		
		if(newImageWidth<2 || newImageHeight<2 || newImageWidth>10000 || newImageHeight>10000 ){
			return;
		}
		
		if(jLabel.getIcon()!=null){
			imageWidth = newImageWidth;
			imageHeight = newImageHeight;

			ImageIcon imageIcon;
			try{
				imageIcon = new ImageIcon(ImageIO.read( new File(imageFilePath) ));
			}catch (IOException e) {
				imageIcon = (ImageIcon) jLabel.getIcon();
			}
			
			jLabel.setIcon(new ImageIcon((imageIcon.getImage()).
					getScaledInstance((int)imageWidth, (int)imageHeight, Image.SCALE_SMOOTH)));
		}
		
	}
	
	public void attachTo(JComponent jComponent) {
		jComponent.add(gui);
		jComponent.revalidate();
	}
	
	
	public static void viewImage(String imagePath){
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
		
		if(imagePath.contains(".")){
			imageViewer.loadImage(imagePath);
		}
	}
	// End of Auxiliary Methods 																#_______AM_______#
	
	//**
	// Overridden Methods 																		#*******OM*******#
	//**
	
	// End of Overridden Methods 																#_______OM_______#
	
	
	/********* Main Method *********/
	public static void main(String args[]) {
		/*// Set the NIMBUS look and feel //*/
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception ex) {
			// do nothing if operation is unsuccessful
		}

		viewImage("");
	}
}

