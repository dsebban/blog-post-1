package daniels.reactive.blog.time;


import java.util.Calendar;
import java.util.Date;

/**
 * Abstraction for system time.
 * 
 * @author Lasse Koskela
 */
public class SystemTime {

	private static daniels.reactive.blog.time.TimeSource defaultSource = new TimeSource() {
		public long millis() {
			return System.currentTimeMillis();
		}
	};

	private static daniels.reactive.blog.time.TimeSource source = defaultSource;

	public static long asMillis() {
		return getTimeSource().millis();
	}

	public static Calendar asCalendar() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(getTimeSource().millis());
		return cal;
	}

	public static Date asDate() {
		return new Date(getTimeSource().millis());
	}

	public static void reset() {
		setTimeSource(null);
	}

	public static void setTimeSource(daniels.reactive.blog.time.TimeSource source) {
		SystemTime.source = source;
	}

	private static TimeSource getTimeSource() {
		return (source != null ? source : defaultSource);
	}

}
