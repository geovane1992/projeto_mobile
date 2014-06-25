package br.ufg.UFGNotify.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Form {

	/**
	 * Validando Email
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isValidEmail(String email) {
		String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	/**
	 * Validando tamanho da senha
	 * 
	 * @param pass
	 * @return
	 */
	public static boolean isValidPassword(String pass) {
		if (pass != null && pass.length() > 5) {
			return true;
		}
		return false;
	}

	/**
	 * Validando tamanho da senha
	 * 
	 * @param pass
	 * @return
	 */
	public static boolean isValidName(String name) {
		if (name != null && name.length() > 5) {
			return true;
		}
		return false;
	}
}
