package data.pcs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import data.jsonin.OverallData;
import data.jsonin.SpaceInfoIn;
import databaseaccess.DatabasePostgres;

public class DataEntry {
    /**
     * Pull a latitude out of an url. e.g.
     * "https://www.sheffield.ac.uk/cics/findapc/map.php?lat=53.382692&lon=-1.487194&loc=Arts%20Tower%20-%2010.12"
     * => 53.382692
     * 
     * @param url
     * @return
     */
    public double getLatitude(String url) {
	String matchedTotal = null;
	Pattern pattern = Pattern.compile(".*lat=(.*?)&");
	Matcher matcher = pattern.matcher(url);
	if (matcher.find()) {
	    matchedTotal = matcher.group(1);
	}

	try {
	    return Double.parseDouble(matchedTotal);
	} catch (Exception e) {
	    return 0.0;
	}
    }

    /**
     * Pull a longitude out of an url. e.g.
     * "https://www.sheffield.ac.uk/cics/findapc/map.php?lat=53.382692&lon=-1.487194&loc=Arts%20Tower%20-%2010.12"
     * => -1.487194
     * 
     * @param url
     * @return
     */
    public double getLongitude(String url) {
	String matchedTotal = null;
	Pattern pattern = Pattern.compile(".*lon=(.*?)&");
	Matcher matcher = pattern.matcher(url);
	if (matcher.find()) {
	    matchedTotal = matcher.group(1);
	}

	try {
	    return Double.parseDouble(matchedTotal);
	} catch (Exception e) {
	    return 0.0;
	}
    }

    /**
     * Get the maximum number of PCs out of a given string.
     * "48 out of 48 PCs available" => 48 "Room booked." => null
     * 
     * @param total_text
     * @return
     */
    public Integer getMax(String total_text) {
	String matchedTotal = null;
	Pattern pattern = Pattern.compile(".* out of (.*) PCs available");
	Matcher matcher = pattern.matcher(total_text);
	if (matcher.find()) {
	    matchedTotal = matcher.group(1);
	}

	try {
	    return Integer.parseInt(matchedTotal);
	} catch (Exception e) {
	    return null;
	}
    }

    /**
     * Get the current number of PCs out of a given string. "48" => 48
     * "Room booked." => 0
     * 
     * @param total_text
     * @return
     */
    public int getCurrent(String current) {
	try {
	    return Integer.parseInt(current);
	} catch (Exception e) {
	    return 0;
	}
    }

    private boolean insertPCsRow(Connection connection, long buildingId,
	    int currentPCs, java.sql.Date date) throws SQLException {
	PreparedStatement pStatement = connection
		.prepareStatement("INSERT INTO current_pcs (buildingid, current, timestamp) "
			+ "VALUES (?, ?, ?)");
	pStatement.setLong(1, buildingId);
	pStatement.setInt(2, currentPCs);
	pStatement.setTimestamp(3, new Timestamp(date.getTime()));

	boolean result = pStatement.execute();

	pStatement.close();

	return result;
    }

    /**
     * Look through our given set of data. If a row does not have an existing
     * Building associated with it, create it. Afterwards, if there are existing
     * Buildings with no data for this scrape, insert a dummy row with 0 free
     * spaces.
     * 
     * @param totalData
     *            As scraped by WebScraper.
     */
    public boolean enter(OverallData totalData) {
	// Set up database details
	Connection connection = DatabasePostgres.getConnection();
	try {
	    connection.setAutoCommit(false); // We want all or nothing for these
					     // rows

	    // To group all of these rows by one date
	    Date currentDate = new Date();
	    java.sql.Date sqlDate = new java.sql.Date(currentDate.getTime());

	    //
	    List<Long> currentIds = Building.getAllIds();

	    // Look through our scraped rows
	    for (SpaceInfoIn info : totalData.getResults().getPc_info()) {
		// Get base information
		Long buildingId = Building.findIdFromName(info.getBuilding());
		int currentPCs = getCurrent(info.getCurrent());

		// No current existing building - create a new one
		if (buildingId == null) {
		    double latitude = getLatitude(info.getLocation());
		    double longitude = getLongitude(info.getLocation());
		    Integer maxPCs = getMax(info.getTotal_text());

		    buildingId = Building.insertNew(connection,
			    info.getBuilding(), maxPCs, info.getPhoto(),
			    latitude, longitude);
		}

		assert (buildingId != null) : "No buildingId returned from the database.";

		// Insert this row into the database
		boolean inserted = insertPCsRow(connection, buildingId,
			currentPCs, sqlDate);
		assert (inserted == true) : "New PCs row failed to insert.";
		currentIds.remove(buildingId);
	    }

	    // Now insert dummy rows into any buildings that weren't included in
	    // this scrape
	    for (Long buildingId : currentIds) {
		boolean inserted = insertPCsRow(connection, buildingId, 0,
			sqlDate);
		assert inserted : "Dummy PCs row failed to insert.";
	    }

	    connection.commit();
	    connection.close();

	} catch (SQLException e) {
	    try {
		connection.rollback();
		connection.close();
	    } catch (SQLException e1) {
		System.err.println("SEVERE ERROR.");
		e1.printStackTrace();
	    }
	    e.printStackTrace();

	    return false;
	}

	return true;
    }
}
