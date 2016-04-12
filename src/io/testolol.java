package io;

import gui.EventPlanner;

import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

public class testolol {

	public static void main(String[] args) {
		
//		fileChooser();

	}
	
	private static void fileChooser() {
		
		try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());} catch (Exception e) {}
		JFileChooser fc = new JFileChooser();
		
		FileNameExtensionFilter fnef = new FileNameExtensionFilter("MS Word only", "doc", "docx");
		
		fc.setFileFilter(fnef);
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int thing = fc.showOpenDialog(EventPlanner.FRAME);
		String asd = fc.getSelectedFile().getAbsolutePath();
		
		System.out.println(thing);
		System.out.println(asd);
		
		System.out.println();
		
		int thing2 = fc.showSaveDialog(EventPlanner.FRAME);
		String asd2 = fc.getSelectedFile().getAbsolutePath();
		
		System.out.println(thing2);
		System.out.println(asd2);
		
	}

}
