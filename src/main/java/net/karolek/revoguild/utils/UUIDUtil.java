package net.karolek.revoguild.utils;

import java.util.UUID;

public class UUIDUtil {
	
	public static UUID fromString(String s) {
		return UUID.fromString(s.replaceFirst("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5"));
	}
	
	public static String toString(UUID u) {
		return u.toString().replace("-", "");
	}

}
