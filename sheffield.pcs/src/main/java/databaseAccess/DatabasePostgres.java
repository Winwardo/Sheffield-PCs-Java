package databaseaccess;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

public class DatabasePostgres {
	static DataSource datasource;

	public static Connection getConnection() {
		if (datasource == null) {
			connectToPool();
		}

		try {
			return datasource.getConnection();
		} catch (final SQLException e) {
			System.out.println("No --");
			e.printStackTrace();
			System.out.println("No database connection.");
		}

		System.out.println("Null connection!");
		return null;
	}

	private synchronized static void connectToPool() {
		if (datasource == null) {
			System.out.println("Setting up new Postgres datasource");
		} else {
			return;
		}

		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Not workinginging");
			e.printStackTrace();
		}

		final PoolProperties p = new PoolProperties();
		String url = "jdbc:postgresql://" + DatabaseLoginInfo.host + "/sheffieldpcs";
		System.out.println("Using: " + url);
		p.setUrl(url);
		p.setDriverClassName("org.postgresql.Driver");
		p.setUsername(DatabaseLoginInfo.username);
		p.setPassword(DatabaseLoginInfo.password);
		p.setTestWhileIdle(false);
		p.setTestOnBorrow(true);
		p.setValidationQuery("SELECT 1");
		p.setTestOnReturn(false);
		p.setValidationInterval(30000);
		p.setTimeBetweenEvictionRunsMillis(30000);
		p.setMaxActive(100);
		p.setInitialSize(10);
		p.setMaxWait(10000);
		p.setRemoveAbandonedTimeout(60);
		p.setMinEvictableIdleTimeMillis(30000);
		p.setMinIdle(10);
		p.setLogAbandoned(true);
		p.setRemoveAbandoned(true);
		p.setJdbcInterceptors("org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"
				+ "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
		datasource = new DataSource();
		datasource.setPoolProperties(p);

		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				System.out.println("Closing Postgres datasource.");
				datasource.close();
			}
		});
	}

}