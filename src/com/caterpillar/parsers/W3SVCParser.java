package com.caterpillar.parsers;

/* @author kaivnlp@gmail.com
 * */
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.charset.Charset;
import java.text.ParseException;
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
public class W3SVCParser implements IParsers {

	private static Logger logger = Logger
			.getLogger("com.caterpillar.launcher.Log4JParser");
	public W3SVCParser() {
		logger.setLevel(Level.SEVERE);
		logger.addHandler(LoggerCat.HANDLER);

	}
	public List<LineData> parseLog(FileData fileTuple, List<LineData> dataRepo)
			throws FileNotFoundException {

		Charset.forName("UTF-8");
		Scanner scanner = new Scanner(new FileReader(fileTuple.get_fpath()));
		try {
			// first use a Scanner to get each line
			int linecounter = 0;
			while (scanner.hasNextLine()) {
				StringBuffer currLine = new StringBuffer(scanner.nextLine());
				if (currLine.indexOf(fileTuple.get_ignorePrefix()) == -1) {
					Pattern p = Pattern.compile((fileTuple.get_regextoparse()));
					Matcher m = p.matcher(currLine);
					if (m.find()) {

						String foundDate = m.group();
						SimpleDateFormat w3svcdateFormatter = new SimpleDateFormat(
								fileTuple.get_simpledateformat());
						Calendar cal = Calendar.getInstance(TimeZone
								.getTimeZone(fileTuple.get_gmtOff()));

						w3svcdateFormatter.setCalendar(cal);
						logger.log(Level.FINE, "Found Date " + foundDate + "  "
								+ fileTuple.get_simpledateformat());
						Date actual_date = (Date) w3svcdateFormatter
								.parse(foundDate);

						LineData lw3svc = new LineData();
						lw3svc.set_inDateUTC(actual_date);
						lw3svc.set_inDateFile(foundDate);
						lw3svc.setActualLine(currLine.toString());
						lw3svc.setFileName(fileTuple.get_fname());
						lw3svc.setLineNo(String.valueOf(linecounter));
						dataRepo.add(lw3svc);

					}
				}
				linecounter++;
			}
		} catch (ParseException e) {
			logger.log(Level.SEVERE, "Stracktrace ", e.getStackTrace());
		} finally {

			scanner.close();
		}
		return dataRepo;
	}

}
