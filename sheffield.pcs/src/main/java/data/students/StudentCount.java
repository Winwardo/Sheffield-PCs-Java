package data.students;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import databaseaccess.DatabasePostgres;

public class StudentCount {
	public final long current_students;
	public final String building;

	public StudentCount(String building, long current_students) {
		this.building = building;
		this.current_students = current_students;
	}

	public static StudentCount getFor(String building) {
		StudentCount result = null;

		Connection connection = DatabasePostgres.getConnection();
		PreparedStatement pStatement;

		try {
			pStatement = connection.prepareStatement(
					"SELECT current_students FROM current_students WHERE building = ? ORDER BY timestamp DESC LIMIT 1");
			pStatement.setString(1, building);
			ResultSet results = pStatement.executeQuery();

			if (results.next()) {
				result = new StudentCount(building, results.getLong("current_students"));
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

	public boolean update() {
		int result = 0;

		Connection connection = DatabasePostgres.getConnection();
		PreparedStatement pStatement;

		try {
			pStatement = connection.prepareStatement(
					"INSERT INTO current_students (timestamp, building, current_students) VALUES (now(), ?, ?)");
			pStatement.setString(1, this.building);
			pStatement.setLong(2, this.current_students);
			result = pStatement.executeUpdate();

			pStatement.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		return result > 0;
	}
}
