package data.pcs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.google.gson.Gson;

import databaseaccess.DatabasePostgres;

public class PredictionRequest {
	static private Map<Long, PredictionRequest> cache = new ConcurrentHashMap<>();

	int hour;
	int current;
	int dayofweek;
	int month;
	Map<Integer, Long> pastValues;
	DateTime dateTime;

	public static PredictionRequest getOrMake(String dateStringIn, long buildingId) {
		DateTime date = new DateTime(dateStringIn);

		PredictionRequest pr = cache.get(buildingId);
		boolean isValid = false;

		if (pr != null) {
			if (pr.dateTime.isAfter(new DateTime().minusMinutes(30))) {
				System.out.println("Using cache");
				isValid = true;
			}
		}

		if (!isValid) {
			pr = new PredictionRequest(date, buildingId);
			cache.put(buildingId, pr);
		}

		return pr;
	}

	private PredictionRequest(DateTime date, long buildingId) {
		this.hour = date.getHourOfDay();
		this.current = 0;
		this.dayofweek = date.getDayOfWeek();
		this.month = date.getMonthOfYear();
		this.pastValues = new HashMap<>();
		this.dateTime = date;

		Connection connection = DatabasePostgres.getConnection();
		PreparedStatement pStatement = null;
		ResultSet results = null;

		for (int hour = 0; hour < 24; hour++) {
			try {
				DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
				String dateStr = date.toString(fmt);
				System.out.println("dateStr: " + dateStr);
				String query = String.format(
						"SELECT buildingid, current, timestamp FROM current_pcs WHERE buildingid = ? AND timestamp > (timestamp '%s' - '%d hour'::INTERVAL - '1 day'::INTERVAL) ORDER BY timestamp ASC LIMIT 1",
						dateStr, hour);
				pStatement = connection.prepareStatement(query);
				pStatement.setLong(1, buildingId);
				System.out.println(pStatement);

				results = pStatement.executeQuery();

				if (results.next()) {
					this.pastValues.put(hour, results.getLong("current"));
				} else {
					System.out.println("whooops");
				}
				results.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Some serious SQL error.");
				e.printStackTrace();
			}
		}

		try {
			pStatement.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Failed to closed pStatement / connection.");
			e.printStackTrace();
		}

	}

	public String jsonBlock() {
		return new Gson().toJson(this);
	}
}
