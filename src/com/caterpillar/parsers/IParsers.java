package com.caterpillar.parsers;

/* @author kaivnlp@gmail.com
 * */
import java.io.FileNotFoundException;
import java.util.List;

import com.caterpillar.dataobject.FileData;
import com.caterpillar.dataobject.LineData;

public interface IParsers {
	public List<LineData> parseLog(FileData fileTuple, List<LineData> dataRepo)
			throws FileNotFoundException;
}