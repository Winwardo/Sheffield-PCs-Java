package data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.google.gson.Gson;

public class WebScraper {
    Gson gson;

    public String getJson(String url) {
	String result = "";

	URL targetUrl = null;
	try {
	    targetUrl = new URL(url);
	} catch (MalformedURLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	URLConnection connection = null;
	try {
	    connection = targetUrl.openConnection();
	    BufferedReader in;
	    in = new BufferedReader(new InputStreamReader(
		    connection.getInputStream()));
	    String inputLine;

	    while ((inputLine = in.readLine()) != null)
		result += inputLine;
	    in.close();

	} catch (IOException e) {
	    System.out.println("Failed to get Json");
	    e.printStackTrace();
	}

	return result;
    }

    public Gson getGson() {
	if (gson == null) {
	    gson = new Gson();
	}

	return gson;
    }

    public PCsData getData(String json) {
	if (json == null || "".equals(json)) {
	    return null;
	}
	return getGson().fromJson(json, PCsData.class);
    }
}
