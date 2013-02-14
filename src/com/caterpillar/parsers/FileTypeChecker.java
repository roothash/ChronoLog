package com.caterpillar.parsers;

/* @author kaivnlp@gmail.com
 * */
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import com.caterpillar.launcher.LoggerCat;
public class FileTypeChecker {
	public final static Map<String, Object> LogTypeNodes = new HashMap<String, Object>();
	private static Logger logger = Logger
			.getLogger("com.caterpillar.launcher.FileTypeChecker");
	private String fileName = null;
	private String sampledatelog4j = null;
	private String sampledatew3svc = null;
	private String sampledateaccess = null;
	/**
	 * If you are adding more parsers make entries here and you will have add
	 * logic also . Logic in this file is just to check a valid file.
	 * This dependency will be removed some time .Not now though , I am getting little time eat food :)
	 **/
	enum LogType {
		LOG4J, W3SVC, WLACCESS, // Weblogic access
		UNKNOWN;
	}
	static {
		logger.setLevel(Level.ALL);
		logger.addHandler(LoggerCat.HANDLER);
		SAXBuilder builder = new SAXBuilder();
		InputStream is = FileTypeChecker.class
				.getResourceAsStream("/definitions.xml");

		try {

			Document document = (Document) builder.build(is);
			Element rootNode = document.getRootElement();
			List<Element> list = rootNode.getChildren("logtype");

			for (Element node : list) {

				if (node.getAttributeValue("name").equalsIgnoreCase("common")) {
					LogTypeNodes.put(node.getAttributeValue("name") + "_"
							+ "maxcheckcounter",
							node.getChildText("maxcheckcounter"));
					LogTypeNodes
							.put(node.getAttributeValue("name") + "_"
									+ "acceptedfile",
									node.getChildText("acceptedfile"));
				} else {
					LogTypeNodes.put(node.getAttributeValue("name") + "_"
							+ "regex", node.getChildText("regex"));
					LogTypeNodes.put(node.getAttributeValue("name") + "_"
							+ "hastack", node.getChildText("hastack"));
					LogTypeNodes.put(node.getAttributeValue("name") + "_"
							+ "delim",
							node.getChildText("delim").replace("lt", "<")
									.replace("gt", ">"));
					LogTypeNodes.put(node.getAttributeValue("name") + "_"
							+ "dateformat", node.getChildText("dateformat"));
					LogTypeNodes.put(node.getAttributeValue("name") + "_"
							+ "defaultTZ", node.getChildText("defaultTZ"));
					LogTypeNodes.put(node.getAttributeValue("name") + "_"
							+ "ignoreline", node.getChildText("ignoreline"));
					LogTypeNodes.put(node.getAttributeValue("name") + "_"
							+ "textpatterns", node.getChildren("textpattern"));
					LogTypeNodes.put(node.getAttributeValue("name") + "_"
							+ "parserclass", node.getChildText("parserclass"));
				}

			}

			logger.log(Level.SEVERE, "Properties Read from XML", LogTypeNodes);

		} catch (IOException io) {
			logger.log(Level.SEVERE, "Stracktrace", io.getStackTrace());
		} catch (JDOMException jdomex) {
			logger.log(Level.SEVERE, "Stracktrace", jdomex.getStackTrace());
		}

	}

	public FileTypeChecker(String fname) {

		fileName = fname;

	}

	private static Object getPropertyValue(final String logtype,
			final String propertyName) {
		Object pVal = null;
		if (propertyName != null && logtype != null)
			pVal = LogTypeNodes.get(logtype + "_" + propertyName);// !=null &&
																	// !LogTypeNodes.get(logtype+"_"+propertyName).trim().equals(""))
																	// ?
																	// LogTypeNodes.get(logtype+"_"+propertyName)
																	// : null;

		return pVal;
	}

	public ArrayList<String> getFileType() {

		int readcounter = 0;

		// Following counters need to written for every file added fresh
		int typeCounterlog4j = 0;
		int typeCounterIIS = 0;
		int typeCounterAccess = 0;
		Scanner scnr = null;
		LogType logType = LogType.UNKNOWN;

		int maxcheckCounter = Integer.valueOf((String) getPropertyValue(
				"common", "maxcheckcounter"));

		if (isAcceptable(fileName))

			try {

				{
					scnr = new Scanner(new File(fileName));
					String currLine = null;

					while (scnr.hasNextLine() && readcounter < maxcheckCounter) {
						currLine = scnr.nextLine();
						switch (logType) {
							case LOG4J :
								if (log4JLine(
										currLine,
										(String) getPropertyValue("LOG4J",
												"regex"))) {
									typeCounterlog4j++;
								} else {
									logType = LogType.UNKNOWN;
								}
								break;
							case W3SVC :
								if (W3SVCLine(
										currLine,
										(String) getPropertyValue("W3SVC",
												"regex"))) {
									typeCounterIIS++;
								} else {
									logType = LogType.UNKNOWN;
								}
								break;
							case WLACCESS :
								if (AccessLine(
										currLine,
										(String) getPropertyValue("WLACCESS",
												"regex"))) {
									typeCounterAccess++;
								} else {
									logType = LogType.UNKNOWN;
								}
								break;
							case UNKNOWN :
								if (log4JLine(
										currLine,
										(String) getPropertyValue("LOG4J",
												"regex"))) {
									logType = LogType.LOG4J;
									typeCounterlog4j++;
								} else if (W3SVCLine(
										currLine,
										(String) getPropertyValue("W3SVC",
												"regex"))) {
									logType = LogType.W3SVC;
									typeCounterIIS++;
								} else if (AccessLine(
										currLine,
										(String) getPropertyValue("WLACCESS",
												"regex"))) {
									logType = LogType.WLACCESS;
									typeCounterAccess++;
								}
								break;
						}
						readcounter++;
					}
				}
			}
			// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

			catch (Exception e) {
				logger.log(Level.SEVERE, "Stracktrace", e.getStackTrace());

			} finally {

				try {
					scnr.close();

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		HashMap<Integer, String> ltt = new HashMap<Integer, String>();
		ltt.put(typeCounterlog4j, "LOG4J");
		ltt.put(typeCounterIIS, "W3SVC");
		ltt.put(typeCounterAccess, "WLACCESS");
		String lType = ltt.get(Collections.max(ltt.keySet()));
		logger.log(Level.SEVERE, "Found the logtype for file " + this.fileName
				+ " as " + lType);

		ArrayList<String> alldefaultProp = new ArrayList<String>();
		if (Collections.max(ltt.keySet()) > 0) {
			alldefaultProp.add(lType);
			alldefaultProp.add(lType.equalsIgnoreCase("LOG4J")
					? sampledatelog4j
					: (lType.equalsIgnoreCase("W3SVC"))
							? sampledatew3svc
							: (lType.equalsIgnoreCase("WLACCESS"))
									? sampledateaccess
									: "GMT");// (sampledatelog4j!=null)?sampledatelog4j:"defaultTZ"));
			alldefaultProp.add((String) getPropertyValue(lType, "hastack"));
			alldefaultProp.add((String) getPropertyValue(lType, "delim"));
			alldefaultProp.add((String) getPropertyValue(lType, "regex"));
			alldefaultProp.add((String) getPropertyValue(lType, "dateformat"));
			alldefaultProp.add((String) getPropertyValue(lType, "ignoreline"));
		}
		return alldefaultProp;

	}

	private boolean log4JLine(String currline, String dateParser) {

		boolean goodLine = false;
		boolean dateMatch = false;

		try {

			Pattern p = Pattern.compile(dateParser);
			Matcher m = p.matcher(currline);
			if (m.find()) {
				dateMatch = true;
				if (sampledatelog4j == null)
					sampledatelog4j = getDateOffset(m.group());

			}

		} catch (Exception e) {

			e.printStackTrace();

		}
		goodLine = HasMatchingPattern("LOG4J", currline) && dateMatch;
		return goodLine;
	}

	private boolean W3SVCLine(String currline, String dateParser) {

		boolean goodLine = false;
		boolean dateMatch = false;
		try {
			sampledatew3svc = "GMT";
			Pattern p = Pattern.compile(dateParser);
			Matcher m = p.matcher(currline);
			if (m.find()) {
				dateMatch = true;

			}

		} catch (Exception e) {

			e.printStackTrace();

		}
		goodLine = HasMatchingPattern("W3SVC", currline) && dateMatch;
		return goodLine;
	}
	private boolean AccessLine(String currline, String dateParser) {
		boolean goodLine = false;
		boolean dateMatch = false;
		try {

			Pattern p = Pattern.compile(dateParser);
			Matcher m = p.matcher(currline);
			if (m.find()) {
				dateMatch = true;
				sampledateaccess = "GMT";
			}

		} catch (Exception e) {

			e.printStackTrace();

		}
		goodLine = HasMatchingPattern("WLACCESS", currline) && dateMatch;
		return goodLine;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FileTypeChecker fg = new FileTypeChecker("dummy.log");
		System.out.println(fg.getFileType());
	}

	private String getDateOffset(String dateStr) {
		String tzStr = "GMT";
		try {
			String tzinfo = dateStr.substring(dateStr.length() - 8,
					dateStr.length());
			tzStr = tzinfo;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return tzStr;
	}

	public static boolean HasMatchingPattern(String logType, String currline) {
		boolean isLoggerLine = false;
		@SuppressWarnings("unchecked")
		Element patternList = ((List<Element>) getPropertyValue(logType,
				"textpatterns")).get(0);

		for (Element e : patternList.getChildren("pattern")) {
			if (currline.indexOf(e.getText().replace("lt", "<")
					.replace("gt", ">")) > 0) {
				isLoggerLine = true;
				break;
			}
		}

		return isLoggerLine;
	}

	public static boolean HasMatchingPattern(String logType,
			StringBuffer currline) {
		boolean isLoggerLine = false;
		@SuppressWarnings("unchecked")
		Element patternList = ((List<Element>) getPropertyValue(logType,
				"textpatterns")).get(0);

		for (Element e : patternList.getChildren("pattern")) {
			if (currline.indexOf(e.getText().replace("lt", "<")
					.replace("gt", ">")) > 0) {
				isLoggerLine = true;
				break;
			}
		}

		return isLoggerLine;
	}

	private boolean isAcceptable(final String fileName) {
		boolean isAcceptable = false;
		@SuppressWarnings("unchecked")
		String[] acceptedfileArray = ((String) getPropertyValue("common",
				"acceptedfile")).split(",");
		for (String suffix : acceptedfileArray) {

			if (fileName.length() > 0 && fileName.indexOf(suffix) > 0)
				isAcceptable = true;
		}

		logger.log(Level.SEVERE, "file " + fileName + " isAcceptable? "
				+ isAcceptable);
		return isAcceptable;
	}

}
