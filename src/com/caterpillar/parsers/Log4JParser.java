package com.caterpillar.parsers;

/* @author kaivnlp@gmail.com
 * */
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.caterpillar.dataobject.FileData;
import com.caterpillar.dataobject.LineData;
import com.caterpillar.launcher.LoggerCat;

public class Log4JParser implements IParsers {
	private static Logger logger = Logger
			.getLogger("com.caterpillar.launcher.Log4JParser");
	private final static String LOG4J = "LOG4J";
	public Log4JParser() {
		logger.setLevel(Level.SEVERE);
		logger.addHandler(LoggerCat.HANDLER);
		// logger.log(Level.SEVERE,"Log4JParser() Called");
	}

	public List<LineData> parseLog(FileData fileTuple, List<LineData> dataRepo)
			throws FileNotFoundException {

		Scanner scanner = new Scanner(new FileReader(fileTuple.get_fpath()));

		try {
			// first use a Scanner to get each line
			StringBuffer strace = new StringBuffer("");
			int linecounter = 0;
			while (scanner.hasNextLine()) {

				StringBuffer currLine = new StringBuffer(scanner.nextLine());

				if (actuallog4JLine(currLine)) {
					Pattern p = Pattern.compile((fileTuple.get_regextoparse()));
					if (strace.length() == 0) {

						Matcher m = p.matcher(currLine);
						if (m.find()) {

							String foundDate = m.group();

							Date sortDate = getDateTime(foundDate,
									fileTuple.get_simpledateformat());
							LineData log4j = new LineData();
							log4j.set_inDateUTC(sortDate);
							log4j.set_inDateFile(foundDate);
							log4j.setActualLine(currLine.toString());
							log4j.setFileName(fileTuple.get_fname());
							log4j.setLineNo(String.valueOf(linecounter));

							dataRepo.add(log4j);

						}
					} else {

						LineData last_added = dataRepo.get(dataRepo.size() - 1);
						StringBuffer oldLine = new StringBuffer(
								last_added.getActualLine());
						oldLine = oldLine.append("\n").append(strace);
						last_added.setActualLine(oldLine.toString());
						Matcher m = p.matcher(currLine);
						if (m.find()) {
							String foundDate = m.group();
							Date sortDate = getDateTime(foundDate,
									fileTuple.get_simpledateformat());
							LineData log4j = new LineData();
							log4j.set_inDateUTC(sortDate);
							log4j.set_inDateFile(foundDate);
							log4j.setActualLine(currLine.toString());
							log4j.setFileName(fileTuple.get_fname());
							log4j.setLineNo(String.valueOf(linecounter));
							dataRepo.add(log4j);
							strace = new StringBuffer("");
						}
					}
				} else {
					strace = strace.append("\n").append(currLine);

				}
				linecounter = linecounter + 1;

			}

		} catch (Exception e) {
			logger.log(Level.SEVERE, "Stracktrace ", e);
		} finally {
			scanner.close();
		}
		return dataRepo;

	}

	public static boolean actuallog4JLine(StringBuffer currline) {

		boolean isActuallog4JLine = FileTypeChecker.HasMatchingPattern(LOG4J,
				currline);
		return isActuallog4JLine;

	}

	public static String PatternMatch(String currStr, String xpattern) {
		String matchgroup = "";
		Pattern p = Pattern.compile(xpattern);
		Matcher m = p.matcher(currStr);
		if (m.find()) {
			matchgroup = m.group();
		}
		return matchgroup;
	}

	public static Date getDateTime(String dateStr, String datepattern) {
		Date finalDate = null;
		try {
			SimpleDateFormat log4jdateFormatter = new SimpleDateFormat(
					datepattern);
			finalDate = log4jdateFormatter.parse(dateStr);
		} catch (ParseException e) {
			logger.log(Level.SEVERE, "Stracktrace ", e);
		}

		return finalDate;
	}
	
}
