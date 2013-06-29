package com.mordrum.mchat.util.linkshorteners;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: Jesse
 * Date: 6/9/13
 * Time: 6:08 PM
 */
public class ShortifyUtility {
	public static String getUrlSimple(String uri, String srv) {
		String inputLine = null;
		try {
			BufferedReader in = getUrl(uri);
			String s = "";
			while ((inputLine = in.readLine()) != null)
				s += inputLine;
			in.close();
			return s;
		} catch (IOException e) {
		}
		return null;
	}

	public static BufferedReader getUrl(String toread) throws IOException {
		return new BufferedReader(new InputStreamReader(
				new URL(toread).openStream()));
	}
}
