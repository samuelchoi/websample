package learning.advanced.jdbc;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JdbcOptExample {
	
	// database connection information
	public static String connectionKey	= "jdbc:mysql://localhost:3306/jdbc-example";
	public static String userName		= "root";
	public static String password		= "root";
	
	// database operation object
	public static Connection connection		= null;
	public static Statement statement		= null;
	public static ResultSet resultSet		= null;
	
	public static Logger LOG = LogManager.getLogger(JdbcOptExample.class);
	
	public static void main(String[] consoleArgs) {
		
		LOG.debug(" jdbcExaple porgram star now. ");
		
		// sql business logic
		String queryUser = "select * from user";
		
		// get args value to user console
		String firstConsoleValue = consoleArgs[0];
		
		// column value of name
		String nameColumn = "";
		
		try {
			
			// built database connection
			connection = DriverManager.getConnection( connectionKey, userName, password );
			LOG.debug(" succeed to get database connection.");

			// create database statement to fetch name value
			statement = connection.createStatement();
			resultSet = statement.executeQuery(queryUser);
			
			while (resultSet.next()) {
				nameColumn = resultSet.getString("name");
			}
			
		} catch (SQLException e) {
			
			// exception handle block
			e.printStackTrace();
			
		} finally {
			
			// release resource in finally
			if (connection != null) {
				
				try {
					LOG.debug( " connection resource be release. " );
					resultSet.close();
					statement.close();
					connection.close();
				} catch (SQLException e) {
					// no handler everything
				}
			}
		}
		
		LOG.debug(" jdbcExample program is over exit now.");
	}

}
