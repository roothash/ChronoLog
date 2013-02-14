package com.caterpillar.launcher;

/* @author kaivnlp@gmail.com
 * */
import java.io.File;
import java.io.FileFilter;

public class CaterPillarFileFilter implements FileFilter {
	private final String[] acceptedFileTypes = new String[]{"jpg", "png", "gif"};

	@Override
	public boolean accept(File file) {
		// TODO Auto-generated method stub
		for (String extension : acceptedFileTypes) {
			if (file.getName().toLowerCase().endsWith(extension)) {
				return true;
			}
		}
		return false;
	}
}