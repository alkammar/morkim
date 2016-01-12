package lib.morkim.mfw.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

public class MTimer {

	public static final long ONE_SECOND = 1000;
	public static final long ONE_MINUTE = 60 * ONE_SECOND;
	public static final long ONE_HOUR = 60 * ONE_MINUTE;
	public static final long ONE_DAY = 24 * ONE_HOUR;
	public static final long UNINITIALIZED_TIMESTAMP = -1L;
	
	private static long nowTest;
	private static boolean test;

	private Timer timer;
	private int period;

	public MTimer() {

		timer = new Timer();
	}

	public MTimer(int period) {

		timer = new Timer();
		this.period = period;
	}

	public void start(TimerTask task) {

		timer.schedule(task, 0, period);
	}

	public void start(TimerTask task, int delay) {

		timer.schedule(task, delay);
	}

	public void start(TimerTask task, int delay, int period) {

		timer.scheduleAtFixedRate(task, delay, period);
	}

	public void stop() {

		timer.cancel();
	}

	public static long now() {

		return (test) ? nowTest : System.currentTimeMillis();
	}

	public static long nowLocal() {
		
		long now = now();

		return now + Calendar.getInstance().getTimeZone().getOffset(now);
	}
	
	public static void setNowTest(long now) {
		MTimer.nowTest = now;
	}
	
	public static void addNowTest(long added) {
		MTimer.nowTest += added;
	}

	public static String getRelativeDayName(long time) {

		time /= ONE_DAY;
//		long now = now();
//		now /= ONE_DAY;

		// ContentManager cm = ContentManager.getInstance();
		// if (time == now)
		// return cm.getString(Strings.shiftoverviewCategory, Strings.today);
		// else if (time == now - 1)
		// return cm.getString(Strings.shiftoverviewCategory,
		// Strings.yesterday);
		// else if (time == now + 1)
		// return cm
		// .getString(Strings.shiftoverviewCategory, Strings.tomorrow);

		return "";
	}

	public static long getRelativeDayMilli(int daysFromToday) {

		long today = System.currentTimeMillis();

		// remove fractions of a day
		today /= ONE_DAY;
		today *= ONE_DAY;

		return today + (daysFromToday * ONE_DAY);
	}

	public static String millisecToUtc(long millisec) {

		Calendar calendar = Calendar.getInstance();

		calendar.setTimeInMillis(millisec);

		Date date = calendar.getTime();

		SimpleDateFormat dateformat = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss", Locale.US);
		dateformat.setTimeZone(TimeZone.getTimeZone("UTC"));

		return dateformat.format(date.getTime());
	}

	public static long utcToMillisec(String date) {

		long millisec = 0;

		SimpleDateFormat dateformat = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss", Locale.US);
		dateformat.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date millisecDate = null;
		try {
			if (date != null) {
				millisecDate = dateformat.parse(date);
				millisec = millisecDate.getTime();
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return millisec;
	}

	// private static long getTimeZone() {
	// TimeZone tz = TimeZone.getDefault();
	//
	// Date now = new Date();
	// long offset = tz.getOffset(now.getTime());
	//
	// return offset;
	// }

	public static int getHours(long duration) {
		return (int) (duration % ONE_DAY / ONE_HOUR);
	}

	public static int getMinutes(long duration) {
		return (int) (duration % ONE_HOUR / ONE_MINUTE);
	}

	public static int getSeconds(long duration) {
		return (int) (duration % ONE_MINUTE / ONE_SECOND);
	}

	public static int getMilliSeconds(long duration) {
		return (int) (duration % ONE_SECOND);
	}
	
	public static void test(boolean test) {
		MTimer.test = test;
		nowTest = System.currentTimeMillis();
	}
}
