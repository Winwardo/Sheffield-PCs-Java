package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import databaseAccess.DatabasePostgres;

public class DataRetriever {
    public ScrapeData getNewestScrape() {
	ScrapeData result = new ScrapeData();

	Connection connection = DatabasePostgres.getConnection();
	PreparedStatement pStatement;

	try {
	    pStatement = connection
		    .prepareStatement("SELECT MIN(date) AS date, count(*) AS count FROM current_pcs GROUP BY date ORDER BY date DESC LIMIT 1;");

	    ResultSet results = pStatement.executeQuery();

	    if (results.next()) {
		result.scrapeDate = results.getDate("date");
		result.scrapedComputers = results.getInt("count");
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
