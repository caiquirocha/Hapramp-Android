package com.hapramp.utils;

public class StringUtils {
		// replace with single escape character
		public static String stringify(String s){
				s = s.replace("\"","\\\"");
				s = s.replace("\\\\","\\\\\\");
				return s;
		}

}
