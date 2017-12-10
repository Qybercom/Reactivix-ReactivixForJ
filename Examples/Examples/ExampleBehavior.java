package Examples;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Class ExampleBehavior
 *
 * https://stackoverflow.com/a/19218035/2097055
 * https://stackoverflow.com/questions/3294293/how-to-get-thread-id-from-a-thread-pool
 * https://stackoverflow.com/questions/7065402/how-to-add-external-library-in-intellij-idea
 * https://stackoverflow.com/questions/15727356/intellij-idea-cannot-resolve-anything-in-maven
 * https://stackoverflow.com/questions/11454822/import-maven-dependencies-in-intellij-idea
 * https://stackoverflow.com/questions/42588051/maven-dependency-for-gson-2-8-0-in-intellij-idea
 * https://stackoverflow.com/questions/3990372/maven-artifact-search-is-always-empty
 *
 * https://stackoverflow.com/questions/3612567/how-to-create-my-own-java-libraryapi
 * https://stackoverflow.com/a/17844854/2097055
 * https://www.jetbrains.com/help/idea/packaging-a-module-into-a-jar-file.html
 * https://stackoverflow.com/questions/1082580/how-to-build-jars-from-intellij-properly
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