  package resources;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Utils {
	public static RequestSpecification req;
	
	public RequestSpecification requestSpecification() throws IOException
	{
		if(req==null)
		{
		PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));
		 req =new RequestSpecBuilder().setBaseUri(getGlobalValues("baseurl")).addQueryParam("key", "qaclick123")
				 .addFilter(RequestLoggingFilter.logRequestTo(log))
				 .addFilter(ResponseLoggingFilter.logResponseTo(log))
		.setContentType(ContentType.JSON).build();
		 return req;
		}
		return req;
	}
	public static String getGlobalValues(String key) throws IOException
	{
		Properties prop = new Properties();
		FileInputStream fils = new FileInputStream("C:\\Users\\Sumit\\eclipse-workspace\\APIFrameWork\\src\\test\\java\\resources\\global.properties");
		prop.load(fils);
		return prop.getProperty(key);
		 
	}
	public String getJsonPath(Response response, String Key)
	{
		String resp = response.asString();
		JsonPath JS = new JsonPath(resp);
		return JS.get(Key).toString();
	}

}
