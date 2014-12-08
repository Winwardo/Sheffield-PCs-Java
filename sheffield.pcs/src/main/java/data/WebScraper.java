package data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.google.gson.Gson;

import data.jsonIn.OverallData;
import databaseAccess.DatabasePostgres;

public class WebScraper {
    Gson gson;

    public Date mostRecentScrapeTime() {
	Connection connection = DatabasePostgres.getConnection();
	PreparedStatement pStatement;
	Date result = null;

	try {
	    pStatement = connection
		    .prepareStatement("SELECT timestamp FROM current_pcs ORDER BY timestamp DESC LIMIT 1");

	    ResultSet results = pStatement.executeQuery();

	    if (results.next()) {
		result = results.getDate("timestamp");
	    }

	    results.close();
	    pStatement.close();
	    connection.close();
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	return result;
    }

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

    public OverallData getData(String json) {
	if (json == null || "".equals(json)) {
	    return null;
	}
	return getGson().fromJson(json, OverallData.class);
    }
}
