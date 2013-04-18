package chalmers.dax021308.ecosystem.model.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for saving and printing various data for testing and development.
 * 
 * TODO: Put inputs to the historyList.
 * @author Erik Ramqvist
 *
 */
public class Log {
	
	private static List<String> printHistory;
	
	/* Disable for performance increase. */
	private static boolean enableLog = true;
	
	/* Log level constants */
	public static final int LEVEL_VERBOSE = 1;
	public static final int LEVEL_INFO    = 2;
	public static final int LEVEL_ERROR   = 3;
	
	private static int currentLogLevel;
	
	static {
		printHistory = new ArrayList<String>(256);
		currentLogLevel = 1;
	}
	
	
	/**
	 * Informational printout.
	 * @param s
	 */
	public static void i(String s) {
		if(enableLog && (currentLogLevel == LEVEL_VERBOSE || currentLogLevel == LEVEL_INFO)) {
			System.out.println(s);
		}
	}
	
	/**
	 * Verbose printout.
	 * @param s
	 */
	public static void v(String s) {
		if(enableLog && currentLogLevel == LEVEL_VERBOSE) {
			System.out.println(s);
		}
	}

	/**
	 * Verbose printout.
	 * @param s
	 */
	public static void v(Object o) {
		if(enableLog && currentLogLevel == LEVEL_VERBOSE) {
			if(o != null)	System.out.println(o.toString());
			else 			System.out.println("null");
		}
	}
	
	/**
	 * Error printout.
	 * @param s
	 */
	public static void e(String s) {
		if(enableLog) {
			System.err.println(s);
		}
	}
	
	
	/**
	 * Prints the String with the {@link System#currentTimeMillis()} and {@link Thread#currentThread()}.
	 * 
	 * @param s
	 */
	public static void printWithThreadAndTime(String s) {
		if(enableLog)
			System.out.println(System.currentTimeMillis() + " - "+ Thread.currentThread().toString() + " - " + s);
	}
	
	public static void setLogLevel(int level) {
		if(level == LEVEL_ERROR || level == LEVEL_INFO || level == LEVEL_VERBOSE) {
			currentLogLevel = level;
		}
	}
	
	public static int getLogLevel() {
		return currentLogLevel;
	}
	
	/**
	 * Clear out the stored Log data.
	 */
	public static void clearLogHistory() {
		printHistory.clear();
	}
	
	/**
	 * Dump the stored Log data to the given filename. 
	 * <P>
	 * The file will be stored in the local folder of the JRE.
	 * <p>
	 * Not implemented.
	 * @param fileName
	 * @param level
	 */
	public static void dumpLogToTextfile(String fileName, int level) {
		//TODO: Implement
	}
}
