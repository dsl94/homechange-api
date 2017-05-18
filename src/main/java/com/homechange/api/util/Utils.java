package com.homechange.api.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Nemanja on 5/18/17.
 *
 * Class that contains utility methods, all these methods are static
 * This class is used in entire project
 */
public class Utils {

	/**
	 * Helper method for date formatting
	 * @param date Date for formatting
	 * @return Formatted date as String
	 */
	public static String formatDate(Date date){
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		String formatedDate = format.format(date);
		return formatedDate;
	}
}
