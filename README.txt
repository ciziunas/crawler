The purpose of the application is to create a web crawler that automatically detects Marfeelizable sites. To simplify the requirements we’ll consider a site is Marfeelizable if the <TITLE> tag contains the keywords “news” or “noticias”.

MySql DB is used. You need to specify your url and credentials in the file "application.properties".

Rest interface is exposed in http://yourhost:port/crawler/check-sites url, which receives JSON data in format:
[
	{
		"url" : "www.website1.com"
	},
	{
		"url" : "www.website2.com"
	}
	...
]

if you pass a valid request, you only get a status as a response. Application saves results to DB asynchrounously while response to the caller is sent.

Test json file "test.json" is provided.

- Solution is provided in the form of maven (http://maven.apache.org/download.cgi) project. 
- Application is developed by Java 1.8 language
- Application uses Servlet 3.1 API
- Solution is packaged to war archive and can be run on Tomcat 8 version
- Configuration is provided in Java configuration (no xml configuration)


If you have any questions - contact me: mr.marius.c@gmail.com

#EnjoyIT