package Examples;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Class ExampleBehavior
 */
public class ExampleBehavior {
	public static boolean run = true;

	/**
	 * @param message Logged message
	 */
	public static void Log (String message) {
		System.out.println("[" + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(Calendar.getInstance().getTime()) + "] [thread: " + java.lang.Thread.currentThread().getId() + "] " + message);
	}

	/**
	 * Pause run
	 */
	public static void _sleep () {
		try {
			java.lang.Thread.sleep(10);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}