package com.caterpillar.launcher;

/* @author kaivnlp@gmail.com
 * */
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerCat {
	private static final String LOG_FILE_NAME = "caterpillar.log";
	public static FileHandler HANDLER = null;

	public static void initLogger() throws IOException {
		HANDLER = new FileHandler(LOG_FILE_NAME);
		HANDLER.setFormatter(new SimpleFormatter());

	}

}
