package com.caterpillar.parsers;

/* @author kaivnlp@gmail.com
 * */
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.caterpillar.dataobject.FileData;
import com.caterpillar.dataobject.LineData;
import com.caterpillar.launcher.LoggerCat;

public class AccessLogParser implements IParsers {
	private static Logger logger = Logger
			.getLogger("com.caterpillar.launcher.AccessLogParser");
	private final static String WLACCESS = "WLACCESS";

	public AccessLogParser() {
		logger.setLevel(Level.SEVERE);
		logger.addHandler(LoggerCat.HANDLER);
		// logger.log(Level.SEVERE,"AccessLogParser Called");

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
				if (actualAccessLogLine(currLine)) {
					Pattern p = Pattern.compile((fileTuple.get_regextoparse()));
					if (strace.length() == 0) {

						Matcher m = p.matcher(currLine);
						if (m.find()) {

							String foundDate = m.group();

							SimpleDateFormat accessdateFormatter = new SimpleDateFormat(
									fileTuple.get_simpledateformat());
							Calendar cal = Calendar.getInstance(TimeZone
									.getTimeZone(fileTuple.get_gmtOff()));

							accessdateFormatter.setCalendar(cal);
							logger.log(Level.FINE, "Found Date " + foundDate
									+ "  " + fileTuple.get_simpledateformat());
							Date actual_date = (Date) accessdateFormatter
									.parse(foundDate);

							LineData accessLog = new LineData();
							accessLog.set_inDateUTC(actual_date);
							accessLog.set_inDateFile(foundDate);
							accessLog.setActualLine(currLine.toString());
							accessLog.setFileName(fileTuple.get_fname());
							accessLog.setLineNo(String.valueOf(linecounter));

							dataRepo.add(accessLog);

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
							Date sortDate = Log4JParser.getDateTime(foundDate,
									fileTuple.get_simpledateformat());
							LineData accessLog = new LineData();
							accessLog.set_inDateUTC(sortDate);
							accessLog.set_inDateFile(foundDate);
							accessLog.setActualLine(currLine.toString());
							accessLog.setFileName(fileTuple.get_fname());
							accessLog.setLineNo(String.valueOf(linecounter));
							dataRepo.add(accessLog);
							strace = new StringBuffer("");
						}
					}
				} else {
					strace = strace.append("\n").append(currLine);

				}
				linecounter = linecounter + 1;

			}

		} catch (Exception e) {
			logger.log(Level.SEVERE, "Stracktrace ", e.getStackTrace());
		} finally {
			scanner.close();
		}
		return dataRepo;
	}

	public static boolean actualAccessLogLine(StringBuffer currline) {

		boolean isAccessLine = FileTypeChecker.HasMatchingPattern(WLACCESS,
				currline);
		return isAccessLine;
	}
}
