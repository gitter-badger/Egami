/************************************************************************************************************
 * Developer: Minhas Kamal(minhaskamal024@gmail.com)														*
 * Date : 11-04-2014																						*
 ***********************************************************************************************************/


package com.minhaskamal.egami.view;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.Dimension;
import java.io.File;

@SuppressWarnings("serial")
public class FileChooser extends JFileChooser{
	private static String rootPath = "";
	private String currentFilePath;
	private String[] extentions;

	//**  Constructor  **//
	public FileChooser(){
		this(rootPath);
	}
	
 	public FileChooser(String currentFilePath){
 		this(currentFilePath, new String[]{"*"});
	}
 	
 	public FileChooser(String[] extentions){
 		this(rootPath, extentions);
	}
 	
 	public FileChooser(String currentFilePath, String[] extentions){
 		this.currentFilePath = currentFilePath;
 		this.extentions = extentions;
 		
 		initialComponent();
	}
	
 	private void initialComponent(){
 		//configuring the file chooser
		setDialogTitle("Choose File");
		setPreferredSize(new Dimension(600, 500));
		setDragEnabled(true);
		setCurrentDirectory(new File(currentFilePath));
		
		setFileFilter(new FileFilter() {
			@Override
			public String getDescription() {
				String string = new String();
				
				for(String extention: extentions){
					string += "*."+extention+" ";
				}
				
				return string;
			}
			
			@Override
			public boolean accept(File file) {
				if(!file.isFile()){
					return true;
				}
				
				if(extentions[0].equals("*")){
					return true;
				}
				
				
				String fileExtention=file.getName().substring(file.getName().lastIndexOf('.')+1);
				for(String extention: extentions){
					if(fileExtention.equalsIgnoreCase(extention)){
						return true;
					}
				}
				
				return false;
			}
		});
 	}
 	
	public File[] chooseFilesFromComputer(){
		setMultiSelectionEnabled(true);
		
		int response = showOpenDialog(null);
		
		File[] selectedFiles = new File[]{};
		if (response == JFileChooser.APPROVE_OPTION){
			selectedFiles = getSelectedFiles();
		}
		
		rootPath = selectedFiles[0].getAbsoluteFile().getParent();
		
		return selectedFiles;
	}
	
	public File chooseFileFromComputer(){
		setMultiSelectionEnabled(false);
		
		int response = showOpenDialog(null);
		
		File selectedFile = new File("");
		if (response == JFileChooser.APPROVE_OPTION){
			selectedFile = getSelectedFile();
		}
		
		rootPath = selectedFile.getAbsoluteFile().getParent();
		
		return selectedFile;
	}
	
	public File chooseDirectoryFromComputer(){
		setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		return chooseFileFromComputer();
	}
	
	public File[] chooseDirectoriesFromComputer(){
		setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		return chooseFilesFromComputer();
	}
	
	public String chooseFilePathFromComputer(){
		return chooseFileFromComputer().getAbsolutePath();
	}
	
	
//	//**  Main Method  **//test only
//	public static void main(String args[]){
//		/* Set the NIMBUS look and feel */
//    	try {
//			javax.swing.UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");					
//		} catch (Exception ex) {
//			//do nothing if operation is unsuccessful
//		}
//			
//		File file[] = new FileChooser(new String[]{"tai"}).chooseDirectoriesFromComputer();
//		System.out.println(file[0].toString());
//		System.out.println(file[1].toString());
//	}
}
