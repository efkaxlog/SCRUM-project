
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public abstract class Utilities {

	/**
	 * returns string without it's last character
	 */
	public static String removeLastChar(String str) {
		return str.substring(0, str.length() - 1);
	}

	public static Timestamp getCurrentTimestamp() {
		Calendar c = Calendar.getInstance();
		java.util.Date now = c.getTime();
		return new Timestamp(now.getTime());
	}

	public static Timestamp getTimestampFromString(String timestampString) {
		String pattern = "yyyy-MM-dd hh:mm:ss.SSS";
		Timestamp timestamp = null;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
			Date parsedDate = dateFormat.parse(timestampString);
			timestamp = new Timestamp(parsedDate.getTime());
		} catch (Exception e) {
			System.out.println("Failed converting string to timestamp");
			e.printStackTrace();
		}
		return timestamp;
	}
}
