package es.marfeel.crawler.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import es.marfeel.crawler.service.Marshaller;

@Component
public class JsonMarshallerServiceImpl implements Marshaller {

	private static final String UNMARSHAL_JSON_REGEXP = "\"url\"\\s*.*\\s*\"";

	public String[] unMarshal(String json) {
		List<String> websites = new ArrayList<String>();
		if (!StringUtils.isEmpty(json)) {
			Pattern pattern = Pattern.compile(UNMARSHAL_JSON_REGEXP);
			Matcher matcher = pattern.matcher(json);

			while (matcher.find()) {
				int valueStartIndex = nthIndexOf(matcher.group(), '\"', 3) + 1;
				int valueEndIndex = matcher.group().length() - 1;
				String value = matcher.group().substring(valueStartIndex, valueEndIndex);
				websites.add(value);
			}
		}
		String[] result = new String[websites.size()];
		return websites.toArray(result);
	}

	private int nthIndexOf(String s, char c, int n) {
		int i = -1;
		while (n-- > 0) {
			i = s.indexOf(c, i + 1);
			if (i == -1)
				break;
		}
		return i;
	}

}