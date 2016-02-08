package es.marfeel.crawler.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

public class JsonValidatorByRegexImpl implements JsonValidator {

	private static final String VALID_JSON_PATTERN = "\\s*\\[\\s*(\\s*\\{\\s*\"url\"\\s*:\\s*\".+\"\\s*\\}\\s*,?\\s*)*\\s*\\]\\s*";

	public boolean isValid(String jsonString) {
		if (!StringUtils.isEmpty(jsonString)) {
			Pattern pattern = Pattern.compile(VALID_JSON_PATTERN);
			Matcher matcher = pattern.matcher(jsonString);
			return matcher.matches();
		}
		return false;
	}

}