package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import databaseAccess.DatabasePostgres;

public class DataRetriever {
    public ScrapeData getNewestScrape() {
	ScrapeData result = new ScrapeData();

	Connection connection = DatabasePostgres.getConnection();
	PreparedStatement pStatement;

	try {
	    pStatement = connection
		    .prepareStatement("SELECT MIN(timestamp) AS date, count(*) AS count FROM current_pcs WHERE timestamp IS NOT NULL GROUP BY timestamp ORDER BY timestamp DESC LIMIT 1;");

	    ResultSet results = pStatement.executeQuery();

	    if (results.next()) {
		result.scrapeDate = results.getTimestamp("date");
		result.scrapedComputers = results.getInt("count");
	    }

	    pStatement.close();
	    pStatement = connection
		    .prepareStatement("SELECT buildingid, current FROM current_pcs WHERE timestamp = ?");
	    pStatement.setTimestamp(1,
		    new Timestamp(result.scrapeDate.getTime()));

	    results = pStatement.executeQuery();

	    while (results.next()) {
		PCs_info info = new PCs_info();
		info.buildingId = results.getLong("buildingid");
		info.current = results.getInt("current");
		result.pcsInfo.add(info);
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
}
