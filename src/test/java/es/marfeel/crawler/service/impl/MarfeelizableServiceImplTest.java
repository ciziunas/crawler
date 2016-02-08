package es.marfeel.crawler.service.impl;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import es.marfeel.crawler.entity.Website;

public class MarfeelizableServiceImplTest {
	
	private static final String WEBSITE_URL = "http://www.google.lt";
	
	private MarfeelizableServiceImpl testee;
	
	@Before
	public void setUp() {
		testee = new MarfeelizableServiceImpl();
	}
	
	/**
	 * given: testee object with mocked object 'Document' inside. 
	 * 		  'Document' object contains title tag with String 'news' inside
	 * when: MarfeelizableServiceImpl.marfeelizable() is executed
	 * then: result is marfeelizable website
	 */
	@Test
	public void testMarfeelizeIsMarfeelizable() throws IOException {
		MarfeelizableServiceImpl testeeSpy = Mockito.spy(testee);
		Document document = new Document(WEBSITE_URL);
		document.html("<head><title>news</title></head>");
		Mockito.doReturn(document).when(testeeSpy).getDocument(WEBSITE_URL);
        
		Website result = testeeSpy.marfeelize(WEBSITE_URL);
		
		Assert.assertTrue(result.isMarfeelizable());
		Assert.assertEquals(WEBSITE_URL, result.getUrl());
	}
	
	/**
	 * given: testee object with mocked object 'Document' inside. 
	 * 		  'Document' object contains title tag with String 'just title' inside
	 * when: MarfeelizableServiceImpl.marfeelizable() is executed
	 * then: result is not marfeelizable website
	 */
	@Test
	public void testMarfeelizeIsNotMarfeelizable() throws IOException {
		MarfeelizableServiceImpl testeeSpy = Mockito.spy(testee);
		Document document = new Document(WEBSITE_URL);
		document.html("<head><title>just title</title></head>");
		Mockito.doReturn(document).when(testeeSpy).getDocument(WEBSITE_URL);
        
		Website result = testeeSpy.marfeelize(WEBSITE_URL);
		
		Assert.assertFalse(result.isMarfeelizable());
		Assert.assertEquals(WEBSITE_URL, result.getUrl());
	}
	
	/**
	 * given: testee object with mocked object 'Document' inside. 
	 * 		  'Document' object contains no title tag inside
	 * when: MarfeelizableServiceImpl.marfeelizable() is executed
	 * then: result is not marfeelizable website
	 */
	@Test
	public void testMarfeelizeNoTitleTag() throws IOException {
		MarfeelizableServiceImpl testeeSpy = Mockito.spy(testee);
		Document document = new Document(WEBSITE_URL);
		document.html("<head></head>");
		Mockito.doReturn(document).when(testeeSpy).getDocument(WEBSITE_URL);
        
		Website result = testeeSpy.marfeelize(WEBSITE_URL);
		
		Assert.assertFalse(result.isMarfeelizable());
		Assert.assertEquals(WEBSITE_URL, result.getUrl());
	}
	
	/**
	 * given: testee object with null argument 
	 * when: MarfeelizableServiceImpl.marfeelizable() is executed
	 * then: result is null
	 */
	@Test
	public void testMarfeelizeNullArgument() {
		Website result = testee.marfeelize(null);
		Assert.assertNull(result);
	}
	
	/**
	 * given: testee object with empty String argument 
	 * when: MarfeelizableServiceImpl.marfeelizable() is executed
	 * then: result is null
	 */
	@Test
	public void testMarfeelizeEmptyStringArgument() {
		Website result = testee.marfeelize(null);
		Assert.assertNull(result);
	}

}
