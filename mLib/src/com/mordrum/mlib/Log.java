package com.mordrum.mlib;

import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Jesse
 * Date: 2/24/13
 * Time: 3:00 PM
 */
public class Log {

	private static Logger log = Logger.getLogger("minecraft");

	public static void info(String message) {
		log.info("[INFO|" + mLib.name + "]" + message);
	}

	public static void warning(String message) {
		log.info("[WARNING|" + mLib.name + "]" + message);
	}

	public static void severe(String message) {
		log.info("[SEVERE|" + mLib.name + "]" + message);
	}
}
