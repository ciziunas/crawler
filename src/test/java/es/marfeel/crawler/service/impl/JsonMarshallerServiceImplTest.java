package es.marfeel.crawler.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class JsonMarshallerServiceImplTest {
	
	private static final String VALID_FORMATTED_JSON_FILE_NAME = "json/validFormatted.json";
	private static final int VALID_FORMATTED_JSON_OBJECTS_COUNT = 144;
	private static final String VALID_FORMATTED_JSON_FIRST_EL_VALUE =  "centrallecheraasturiana.es";
	
	private static final String MISSING_BRACKET_JSON_FILE_NAME = "json/invalid.json";
	private static final int MISSING_BRACKET_VALID_OBJECTS_COUNT = 1;
	
	private static final String INVALID_KEY_JSON_FILE_NAME = "json/invalidKey.json";
	
	private JsonMarshallerServiceImpl testee;
	
	@Before
	public void setUp() {
		testee = new JsonMarshallerServiceImpl();
	}
	
	/**
	 * given: valid, unformatted json String
	 * when: unmarshalling is executed
	 * then: the length is equal 144 and the first element is checked as is as expected
	 */
	@Test
	public void testValidUnMarshal() {
		String json = getJsonStringFromFile(VALID_FORMATTED_JSON_FILE_NAME);
		String[] result = testee.unMarshal(json);
		Assert.assertEquals(VALID_FORMATTED_JSON_OBJECTS_COUNT, result.length);
		Assert.assertEquals(VALID_FORMATTED_JSON_FIRST_EL_VALUE, result[0]);
	}
	
	/**
	 * given: invalid json String
	 * when: unmarshalling is executed
	 * then: the length is equal 1 because input has 1 valid fragment
	 */
	@Test
	public void testInvalidJsonUnMarshal() {
		String json = getJsonStringFromFile(MISSING_BRACKET_JSON_FILE_NAME);
		String[] result = testee.unMarshal(json);
		Assert.assertEquals(MISSING_BRACKET_VALID_OBJECTS_COUNT, result.length);
	}
	
	/**
	 * given: null as an argument
	 * when: unmarshalling is executed
	 * then: the length is equal to 0
	 */
	@Test
	public void testNullArgumentUnMarshal() {
		String[] result = testee.unMarshal(null);
		Assert.assertEquals(0, result.length);
	}
	
	/**
	 * given: empty String as an argument
	 * when: unmarshalling is executed
	 * then: the length is equal to 0
	 */
	@Test
	public void testEmptyStringArgumentUnMarshal() {
		String[] result = testee.unMarshal("");
		Assert.assertEquals(0, result.length);
	}
	
	/**
	 * given: json with invalid keys
	 * when: unmarshalling is executed
	 * then: the length is equal to 0
	 */
	@Test
	public void testInvalidKeys() {
		String json = getJsonStringFromFile(INVALID_KEY_JSON_FILE_NAME);
		String[] result = testee.unMarshal(json);
		Assert.assertEquals(0, result.length);
	}
	
	private String getJsonStringFromFile(String fileName) {
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
