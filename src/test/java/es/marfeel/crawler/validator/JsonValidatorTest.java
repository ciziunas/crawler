package es.marfeel.crawler.validator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import junit.framework.AssertionFailedError;

public class JsonValidatorTest {

	private static final String VALID_JSON = "[]";

	private JsonValidatorByRegexImpl testee;

	@Before
	public void setUp() {
		testee = new JsonValidatorByRegexImpl();
	}

	/**
	 * given: valid unformatted Json
	 * when: validation is executed
	 * then: result is valid json
	 */
	@Test
	public void testValidUnformattedJson() {
		String json = getStringFromFile("json/validUnformatted.json");
		Assert.assertTrue(testee.isValid(json));
	}
	
	/**
	 * given: json with a big data inside
	 * when: validation is executed
	 * then: result is valid, but too big for the heap
	 */
	@Test(expected = StackOverflowError.class)  
	public void testBigDataJson() {
		String json = getStringFromFile("json/bigData.json");
		Assert.assertTrue(testee.isValid(json));
	}
	
	/**
	 * given: null as an argument
	 * when: validation is executed
	 * then: result is invalid json
	 */
	@Test
	public void testNullArgument() {
		Assert.assertFalse(testee.isValid(null));
	}
	
	/**
	 * given: empty string as an argument
	 * when: validation is executed
	 * then: result is invalid json
	 */
	@Test
	public void testEmptyStringArgument() {
		Assert.assertFalse(testee.isValid(""));
	}
	
	/**
	 * given: json with invalid keys inside
	 * when: validation is executed
	 * then: result is invalid json
	 */
	@Test
	public void testInvalidKeys() {
		String json = getStringFromFile("json/invalidKey.json");
		Assert.assertFalse(testee.isValid(json));
	}

	/**
	 * given: invalid json string with missing brackets inside
	 * when: validation is executed
	 * then: result is invalid json
	 */
	@Test
	public void testMissingBrackets() {
		String json = getStringFromFile("json/missingBracket.json");
		Assert.assertFalse(testee.isValid(json));
	}
	
	/**
	 * given: invalid json string with too much brackets inside
	 * when: validation is executed
	 * then: result is invalid json
	 */
	@Test
	public void testTooMuchBrackets() {
		String json = getStringFromFile("json/tooMuchBrackets.json");
		Assert.assertFalse(testee.isValid(json));
	}

	private String getStringFromFile(String fileName) {
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(getClass().getClassLoader().getResourceAsStream(fileName)))) {
			return br.lines().collect(Collectors.joining(System.lineSeparator()));
		} catch (IOException e) {
			e.printStackTrace();
			Assert.fail("Resource file is not found " + fileName);
		}
		return "";
	}

}