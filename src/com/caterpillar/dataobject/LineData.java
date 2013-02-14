package com.caterpillar.dataobject;

/* @author kaivnlp@gmail.com
 * */
import java.util.Date;

public class LineData {
	/**
	 * @return the _inDateUTC
	 */
	public Date get_inDateUTC() {
		return _inDateUTC;
	}
	/**
	 * @param _inDateUTC
	 *            the _inDateUTC to set
	 */
	public void set_inDateUTC(Date _inDateUTC) {
		this._inDateUTC = _inDateUTC;
	}
	/**
	 * @return the _inDateFile
	 */
	public String get_inDateFile() {
		return _inDateFile;
	}
	/**
	 * @param _inDateFile
	 *            the _inDateFile to set
	 */
	public void set_inDateFile(String _inDateFile) {
		this._inDateFile = _inDateFile;
	}
	/**
	 * @return the actualLine
	 */
	public String getActualLine() {
		return actualLine;
	}
	/**
	 * @param actualLine
	 *            the actualLine to set
	 */
	public void setActualLine(String actualLine) {
		this.actualLine = actualLine;
	}

	public String toString() {
		return this._inDateFile + "**" + this._inDateUTC + "**"
				+ this.actualLine;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getLineNo() {
		return lineNo;
	}
	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}
	private Date _inDateUTC; // Date used in file in UTC
	private String _inDateFile; // date as present in file
	private String actualLine; // Actual line Data

	private String fileName; // name of File
	private String lineNo; // Line number

}
