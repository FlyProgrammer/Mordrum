package com.mordrum.mchat.util.linkshorteners;

/**
 * Created with IntelliJ IDEA.
 * User: Jesse
 * Date: 6/9/13
 * Time: 5:56 PM
 */
public class IsGdShortener implements LinkShortener {
	@Override
	public String shortenURL(String URL) {
		return ShortifyUtility
				.getUrlSimple("http://is.gd/create.php?format=simple&url="
						+ URL, "is.gd");
	}
}
