package databaseaccess;

public class DatabaseLoginInfo {
	static String host = System.getenv("OPENSHIFT_POSTGRESQL_DB_HOST");
	static String dbName = "sheffieldpcs";
	static String username = System.getenv("OPENSHIFT_POSTGRESQL_DB_USERNAME");
	static String password = System.getenv("OPENSHIFT_POSTGRESQL_DB_PASSWORD");

	static public String apiPassword = System.getenv("SHEF_API_PASSWORD");
}
