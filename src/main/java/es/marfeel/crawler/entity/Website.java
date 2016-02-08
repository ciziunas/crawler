package es.marfeel.crawler.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Website {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String url;
	private boolean isMarfeelizable;
	private String errorMessage;
	
	public Website(String url, boolean isMarfeelizable) {
		this.url = url;
		this.isMarfeelizable = isMarfeelizable;
	}
	
	public long getId() {
		return id;
	}
	public String getUrl() {
		return url;
	}
	public boolean isMarfeelizable() {
		return isMarfeelizable;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	@Override
	public String toString() {
		return "Website [id=" + id + ", url=" + url + ", isMarfeelizable=" + isMarfeelizable + ", errorMessage="
				+ errorMessage + "]";
	}
	
}
