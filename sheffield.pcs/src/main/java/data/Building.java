package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import databaseAccess.DatabasePostgres;

public class Building {
    public long id;
    public int maximum;
    public String name;
    public String photo;
    public double latitude;
    public double longitude;
    public List<PCs_info> pcsInfo;

    public Building() {
	pcsInfo = new LinkedList<PCs_info>();
    }

    public static Building getRecent(long buildingId) {
	Building building = new Building();

	Connection connection = DatabasePostgres.getConnection();
	PreparedStatement pStatement;
	Long result = null;

	try {
	    pStatement = connection
		    .prepareStatement("SELECT buildingid, current, timestamp FROM current_pcs WHERE buildingid = ? AND timestamp IS NOT NULL ORDER BY timestamp DESC LIMIT 192");
	    pStatement.setLong(1, buildingId);

	    ResultSet results = pStatement.executeQuery();

	    while (results.next()) {
		PCs_info info = new PCs_info(results.getLong("buildingid"),
			results.getInt("current"),
			results.getTimestamp("timestamp"));
		building.pcsInfo.add(info);
	    }

	    results.close();
	    pStatement.close();
	    connection.close();
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	return building;
    }

    public static long insertNew(Connection connection, String name,
	    Integer maximum, String photo, double latitude, double longitude)
	    throws SQLException {
	PreparedStatement pStatement;
	long result = -1l;

	pStatement = connection
		.prepareStatement("INSERT INTO building (name, maximum, photo, latitude, longitude) "
			+ "VALUES (?, ?, ?, ?, ?) RETURNING id");
	pStatement.setString(1, name);
	pStatement.setInt(2, maximum);
	pStatement.setString(3, photo);
	pStatement.setDouble(4, latitude);
	pStatement.setDouble(5, longitude);

	ResultSet newId = pStatement.executeQuery();

	if (newId.next()) {
	    result = newId.getLong("id");
	}

	newId.close();
	pStatement.close();

	return result;
    }

    public static Long findIdFromName(String buildingName) {
	Connection connection = DatabasePostgres.getConnection();
	PreparedStatement pStatement;
	Long result = null;

	try {
	    pStatement = connection
		    .prepareStatement("SELECT * FROM building WHERE name = ?");
	    pStatement.setString(1, buildingName);

	    ResultSet results = pStatement.executeQuery();

	    if (results.next()) {
		result = results.getLong("id");
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

    public static List<Long> getAllIds() {
	List<Long> result = new LinkedList<Long>();

	Connection connection = DatabasePostgres.getConnection();
	PreparedStatement pStatement;

	try {
	    pStatement = connection.prepareStatement("SELECT id FROM building");

	    ResultSet results = pStatement.executeQuery();

	    while (results.next()) {
		result.add(results.getLong("id"));
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
