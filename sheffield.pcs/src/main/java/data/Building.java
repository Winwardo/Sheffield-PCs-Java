package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import databaseAccess.DatabasePostgres;

public class Building {
    public long id;
    public int current;
    public int maximum;
    public String name;
    public String photo;
    public double latitude;
    public double longitude;
    public List<PCs_info> pcsInfo;

    public Building() {
	pcsInfo = new LinkedList<PCs_info>();
    }

    public static List<Building> buildingsInTheIC() {
	List<Building> result = new LinkedList<Building>();

	List<Long> ids = new LinkedList<Long>();
	ids.add(Building.findIdFromName("Information Commons L1"));
	ids.add(Building.findIdFromName("Information Commons L2"));
	ids.add(Building.findIdFromName("Information Commons L3"));
	ids.add(Building.findIdFromName("Information Commons L3 Classroom"));
	ids.add(Building.findIdFromName("Information Commons L3 Silent Study"));
	ids.add(Building.findIdFromName("Information Commons L4"));
	ids.add(Building.findIdFromName("Information Commons L4 Classroom"));
	ids.add(Building.findIdFromName("Information Commons L5"));

	for (Long id : ids) {
	    result.add(Building.getRecent(id));
	}

	return result;
    }

    public static int spacesInTheIC() {
	int result = 0;
	List<Building> buildings = buildingsInTheIC();

	for (Building building : buildings) {
	    result += building.current;
	}

	return result;
    }

    public static List<Building> buildingsInMappin() {
	List<Building> result = new LinkedList<Building>();

	List<Long> ids = new LinkedList<Long>();
	ids.add(Building.findIdFromName("Mappin Building - F110"));
	ids.add(Building.findIdFromName("Mappin Building IT Centre - ME03"));
	ids.add(Building.findIdFromName("Mappin Building IT Centre - ME04"));

	for (Long id : ids) {
	    result.add(Building.getRecent(id));
	}

	return result;
    }

    public static int spacesInMappin() {
	int result = 0;
	List<Building> buildings = buildingsInMappin();

	for (Building building : buildings) {
	    result += building.current;
	}

	return result;
    }

    public static List<Building> buildingsInWesternBank() {
	List<Building> result = new LinkedList<Building>();

	List<Long> ids = new LinkedList<Long>();
	ids.add(Building.findIdFromName("Library - Western Bank L3 Oasis"));
	ids.add(Building.findIdFromName("Library - Western Bank L4 Oasis"));
	ids.add(Building
		.findIdFromName("Library - Western Bank L4 Wolfson Suite"));
	ids.add(Building
		.findIdFromName("Library - Western Bank L5 Architecture"));
	ids.add(Building
		.findIdFromName("Library - Western Bank L6 Architecture"));

	for (Long id : ids) {
	    result.add(Building.getRecent(id));
	}

	return result;
    }

    public static int spacesInWesternBank() {
	int result = 0;
	List<Building> buildings = buildingsInWesternBank();

	for (Building building : buildings) {
	    result += building.current;
	}

	return result;
    }

    public static Map<String, Object> getRecentForNvd3(long buildingId) {
	Map<String, Object> result = new HashMap<String, Object>();
	Building building = getRecent(buildingId);
	Collections.reverse(building.pcsInfo);

	result.put("key", building.name);
	result.put("values", building.pcsInfo);

	return result;
    }

    public static Building get(long buildingId) {
	Building building = new Building();

	building.id = buildingId;

	Connection connection = DatabasePostgres.getConnection();
	PreparedStatement pStatement;

	try {
	    pStatement = connection
		    .prepareStatement("SELECT building.*, current, timestamp FROM current_pcs, building "
			    + "WHERE buildingid = ? AND building.id = current_pcs.buildingid "
			    + "AND timestamp IS NOT NULL "
			    + "ORDER BY timestamp DESC LIMIT 1");
	    pStatement.setLong(1, buildingId);

	    ResultSet results = pStatement.executeQuery();

	    if (results.next()) {
		building.name = results.getString("name");
		building.photo = results.getString("photo");
		building.current = results.getInt("current");
		building.latitude = results.getDouble("latitude");
		building.longitude = results.getDouble("longitude");
		building.maximum = results.getInt("maximum");
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

    public static Building getRecent(long buildingId) {
	Building building = new Building();

	building.id = buildingId;
	building.name = Building.getNameFromId(buildingId);

	Connection connection = DatabasePostgres.getConnection();
	PreparedStatement pStatement;

	try {
	    pStatement = connection
		    .prepareStatement("SELECT buildingid, current, timestamp FROM current_pcs WHERE buildingid = ? AND timestamp IS NOT NULL ORDER BY timestamp DESC LIMIT 96");
	    pStatement.setLong(1, buildingId);

	    ResultSet results = pStatement.executeQuery();

	    while (results.next()) {
		PCs_info info = new PCs_info();
		info.buildingId = buildingId;
		building.current = results.getInt("current");
		info.current = results.getInt("current");
		info.timeStamp = results.getTimestamp("timestamp").getTime();
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

    public static String getNameFromId(long buildingId) {
	Connection connection = DatabasePostgres.getConnection();
	PreparedStatement pStatement;
	String result = null;

	try {
	    pStatement = connection
		    .prepareStatement("SELECT name FROM building WHERE id = ?");
	    pStatement.setLong(1, buildingId);

	    ResultSet results = pStatement.executeQuery();

	    if (results.next()) {
		result = results.getString("name");
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

    public static Long findIdFromName(String buildingName) {
	Connection connection = DatabasePostgres.getConnection();
	PreparedStatement pStatement;
	Long result = null;

	try {
	    pStatement = connection
		    .prepareStatement("SELECT id FROM building WHERE name = ?");
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
	    pStatement = connection
		    .prepareStatement("SELECT id FROM building ORDER BY name");

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
