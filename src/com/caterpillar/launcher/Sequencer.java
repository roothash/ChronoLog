package com.caterpillar.launcher;

/* @author kaivnlp@gmail.com
 * */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

import com.caterpillar.comparators.DateComparator;
import com.caterpillar.dataobject.FileData;
import com.caterpillar.dataobject.LineData;
import com.caterpillar.parsers.FileTypeChecker;
import com.caterpillar.parsers.IParsers;

public class Sequencer extends SwingWorker<Void, Void> {

	private List<FileData> flist = new ArrayList<FileData>();
	private String opFile = "";
	private JLabel statusText = null;
	private JButton jButton = null;
	private static Logger logger = Logger
			.getLogger("com.caterpillar.launcher.Sequencer");
	public Sequencer(final List<FileData> flist, final String opFil, JLabel s1,
			JButton delButton) {
		logger.setLevel(Level.ALL);
		logger.addHandler(LoggerCat.HANDLER);
		this.flist = flist;
		this.opFile = opFil;
		this.statusText = s1;
		this.jButton = delButton;

	}

	@Override
	protected Void doInBackground() throws Exception {
		List<LineData> listGlobal = new ArrayList<LineData>();

		int aCounter = 0;
		for (FileData f : flist) {
			setProgress(100 * aCounter / flist.size());
			statusText.setText(f.get_fname() + "..Size-"
					+ fileSize(new File(f.get_fpath())) + " Mb");
			logger.log(
					Level.FINE,
					f.get_fname() + " " + f.get_gmtOff() + "  " + f.get_type()
							+ " " + f.is_hasStacks() + " "
							+ f.get_ignorePrefix() + " ");

			try {

				if (f.get_type() != null && f.get_type().trim().length() > 0) {
					String parserClassName = (String) FileTypeChecker.LogTypeNodes
							.get(f.get_type() + "_" + "parserclass");

					logger.log(Level.SEVERE, "parser for " + f.get_type()
							+ "found is " + parserClassName);
					Class<?> ip = Class.forName(parserClassName);
					IParsers ip2 = (IParsers) ip.newInstance();
					ip2.parseLog(f, listGlobal);

				}
			} catch (Exception e) {

				logger.log(Level.SEVERE, e.getMessage());
			}

			aCounter++;
		}
		statusText
				.setText("File Processing Done..Now arranging chronologically.."
						+ listGlobal.size());
		logger.log(Level.SEVERE,
				"File Processing Done..Now arranging chronologically.."
						+ listGlobal.size());

		Collections.sort(listGlobal, new DateComparator());
		BufferedWriter out;
		try {

			out = new BufferedWriter(new FileWriter(new File(opFile)));
			for (LineData ldata : listGlobal) {

				out.write("[" + ldata.getFileName() + ", " + ldata.getLineNo()
						+ "]          " + ldata.getActualLine() + "\n");

			}

			out.flush();
			out.close();
			statusText
					.setText("Done.Processed " + listGlobal.size() + " Lines");
			logger.log(Level.SEVERE, "Done.Processed " + listGlobal.size()
					+ " Lines");
			setProgress(100);
			jButton.setEnabled(true);
		} catch (IOException e) {

			logger.log(Level.SEVERE, "Stracktrace", e.getStackTrace());
		}

		return null;
	}

	public String fileSize(File fo) {

		long filesize = fo.length();
		long filesizeInKB = filesize / (1024 * 1024);
		return String.valueOf(filesizeInKB);

	}

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
