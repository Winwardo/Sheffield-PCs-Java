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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import data.jsonIn.OverallData;
import data.jsonIn.PC_info;
import data.jsonIn.SpaceInfoIn;
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
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
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
		Map data = (Map) getGson().fromJson(json, Object.class);
		OverallData result = new OverallData();

		List<Map> results = (List<Map>) data.get("results");
		result.setCount(results.size());

		List<SpaceInfoIn> pcInfos = new LinkedList<>();
		for (Map thing : results) {
			// System.out.println(thing);
			SpaceInfoIn space = new SpaceInfoIn();
			space.setBuilding((String) thing.get("coltitle_value"));
			space.setCurrent((String) thing.get("key_value"));
			space.setLocation((String) thing.get("showonmap_link"));
			space.setPhoto((String) thing.get("imgrounded_image"));

			Integer i = ((Double) thing.get("colblock_value_numbers")).intValue();
			space.setTotal_text(String.format("%s %s", i, thing.get("colblock_value")));

			pcInfos.add(space);
		}

		PC_info pcInfo = new PC_info();
		pcInfo.setPc_info(pcInfos);

		result.setResults(pcInfo);
		result.setName("SheffieldPCs");
		return result;
	}
}
