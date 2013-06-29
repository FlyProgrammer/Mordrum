package com.mordrum.mchat.util.linkshorteners;

/**
 * Created with IntelliJ IDEA.
 * User: Jesse
 * Date: 5/18/13
 * Time: 7:39 PM
 */

import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class AdFlyShortener implements LinkShortener {
	public String shortenURL(String url) {
		try {
			InputStream i = new URL(url).openStream();
			Scanner scan = new Scanner(i);
			String s = scan.nextLine();
			scan.close();
			return s;
		} catch (Exception e) {
			return null;
		}
	}
}
