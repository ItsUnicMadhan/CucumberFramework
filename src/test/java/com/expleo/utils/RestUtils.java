package com.expleo.utils;

import java.util.Base64;

public class RestUtils {
	
	/*
	 * public static void main(String[] args) { //Note:Copy the token value here and
	 * generate encoded string String originalInput = ""; String encodedString =
	 * Base64.getEncoder().encodeToString(originalInput.getBytes());
	 * System.out.println(encodedString);
	 * 
	 * }
	 */

	public String decodeToken(String token) {
	byte[] decodedBytes = Base64.getDecoder().decode(token); 
	String decodedString = new String(decodedBytes);
	return decodedString;
	}
	 
}
