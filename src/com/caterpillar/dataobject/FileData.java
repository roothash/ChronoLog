package com.caterpillar.dataobject;

/* @author kaivnlp@gmail.com
 * */
public class FileData {

	public FileData() {

	}

	public FileData(String delimitedValues) {
		// <filename,filepath,timezone,logtype,hasstacktrace,delimiter,regex
		if (delimitedValues != null && delimitedValues != "") {

			String[] filedata = delimitedValues.split(",");

			this.set_fname(filedata[0]);
			this.set_fpath(filedata[1]);
			this.set_gmtOff(filedata[2]);
			this.set_type(filedata[3]);
			this.set_hasStacks(Boolean.getBoolean(filedata[4]));
			this.set_delim(filedata[5]);
			this.set_regextoparse(filedata[6]);
			this.set_simpledateformat(filedata[7]);
			this.set_ignorePrefix(filedata[8]);
			// this.set_ignorePrefix(filedata[8]);
			this.set_isValid((filedata[9]));

		}
	}

	/**
	 * @return the _fname
	 */
	public String get_fname() {
		return _fname;
	}
	/**
	 * @param _fname
	 *            the _fname to set
	 */
	public void set_fname(String _fname) {
		this._fname = _fname;
	}
	/**
	 * @return the _fpath
	 */
	public String get_fpath() {
		return _fpath;
	}
	/**
	 * @param _fpath
	 *            the _fpath to set
	 */
	public void set_fpath(String _fpath) {
		this._fpath = _fpath;
	}
	/**
	 * @return the _gmtOff
	 */
	public String get_gmtOff() {
		return _gmtOff;
	}
	/**
	 * @param _gmtOff
	 *            the _gmtOff to set
	 */
	public void set_gmtOff(String _gmtOff) {
		this._gmtOff = _gmtOff;
	}
	/**
	 * @return the _type
	 */
	public String get_type() {
		return _type;
	}
	/**
	 * @param _type
	 *            the _type to set
	 */
	public void set_type(String _type) {
		this._type = _type;
	}
	/**
	 * @return the _hasStacks
	 */
	public boolean is_hasStacks() {
		return _hasStacks;
	}
	/**
	 * @param _hasStacks
	 *            the _hasStacks to set
	 */
	public void set_hasStacks(boolean _hasStacks) {
		this._hasStacks = _hasStacks;
	}
	/**
	 * @return the _delim
	 */
	public String get_delim() {
		return _delim;
	}
	/**
	 * @param _delim
	 *            the _delim to set
	 */
	public void set_delim(String _delim) {
		this._delim = _delim;
	}
	/**
	 * @return the _regextoparse
	 */
	public String get_regextoparse() {
		return _regextoparse;
	}
	/**
	 * @param _regextoparse
	 *            the _regextoparse to set
	 */
	public String get_simpledateformat() {
		return _simpledateformat;
	}

	public void set_simpledateformat(String _simpledateformat) {
		this._simpledateformat = _simpledateformat;
	}

	public void set_regextoparse(String _regextoparse) {
		this._regextoparse = _regextoparse;
	}

	public String get_ignorePrefix() {
		return _ignorePrefix;
	}

	public void set_ignorePrefix(String _ignorePrefix) {
		this._ignorePrefix = _ignorePrefix;
	}

	private String _fname; // used for file name.
	private String _fpath; // Used for file path
	private String _gmtOff; // Used to decide GMT offset +530 etc
	private String _type; // possible value :"log4j,w3svc"
	private boolean _hasStacks; // Does the log have stack trace
	private String _delim; // delimiter in log file
	private String _regextoparse; // Regular expression to parse the log line to
									// check it has date or not
	private String _simpledateformat; // Date format which is used for parsing
	private String _ignorePrefix;
	private String _isValid; // Is a parseable file or not
	public String get_isValid() {
		return _isValid;
	}

	public void set_isValid(String _isValid) {
		this._isValid = _isValid;
	}

	public String toString() {

		StringBuffer sb = new StringBuffer(this._fname);
		sb.append("==").append(this._fpath).append("==")
				.append(this._simpledateformat).append("==").append(this._type)
				.append(this._delim);
		return sb.toString();

	}
}
