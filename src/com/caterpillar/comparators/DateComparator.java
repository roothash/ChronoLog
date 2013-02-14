package com.caterpillar.comparators;

/* @author kaivnlp@gmail.com
 * */

import java.util.Comparator;

import com.caterpillar.dataobject.LineData;

/*Used for comparing dates in lines of log represented by linedata */

public class DateComparator implements Comparator<LineData> {

	@Override
	public int compare(LineData line1, LineData line2) {
		// TODO
		return line1.get_inDateUTC().compareTo(line2.get_inDateUTC());
	}

}
