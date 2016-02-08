package es.marfeel.crawler.service.impl;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.util.StringUtils;

import es.marfeel.crawler.entity.Website;
import es.marfeel.crawler.service.Marfeelizable;

public class MarfeelizableServiceImpl implements Marfeelizable {

	private static final String TITLE_TAG_NEWS = "news";
	private static final String TITLE_TAG_NOTICIAS = "noticias";
	private static final String HTTP_PROTOCOL = "http:";

	@Override
	public Website marfeelize(String website) {
		if (StringUtils.isEmpty(website)) {
			return null;
		}
		try {
			if (!website.startsWith(HTTP_PROTOCOL)) {
				website = HTTP_PROTOCOL + "//" + website;
			}
			Document doc = getDocument(website);
			System.out.println(doc);
			String title = doc.title();
			boolean isMarfeelizable = (!StringUtils.isEmpty(title) && (title.toLowerCase().contains(TITLE_TAG_NEWS)
					|| title.toLowerCase().contains(TITLE_TAG_NOTICIAS)));
			Website result = new Website(website, isMarfeelizable);
			return result;
		} catch (IOException e) {
			Website result = new Website(website, false);
			result.setErrorMessage(e.getMessage());
			return result;
		}
	}
	
	public Document getDocument(String website) throws IOException {
		return Jsoup.connect(website).get();
	}

}
