package learning.advanced.dbutil;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JDBCUtils {
	
	/* data source */
	private static BasicDataSource dataSource = null;
	
	/* thread local save connection in current thread */
	private static ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();
	
	/* LOG system  */
	private static Logger LOG = LogManager.getLogger(JDBCUtils.class);
	
	/* data source connection  */
	private static final String DRIVER_CLASS_NAME 		= "com.mysql.jdbc.Driver";
	private static final String DATASOURCE_USER_NAME 	= "root";
	private static final String DATASOURCE_URL 			= "jdbc:mysql://localhost:3306/jdbc-example";
	
	static {
		
		try {
			dataSource = new BasicDataSource();
			dataSource.setDriverClassName(DRIVER_CLASS_NAME);
			dataSource.setUsername(DATASOURCE_USER_NAME);
			dataSource.setPassword(DATASOURCE_USER_NAME);
			dataSource.setUrl(DATASOURCE_URL);
		} catch (Exception ex) {
			LOG.error("datasource init example ", ex);
		}
	}
	
	/* get connection by threadLocal */
	public static Connection getConnection() {
		
		Connection conn = threadLocal.get();
		
		if (conn == null) {
			try {	
				conn = dataSource.getConnection();
				threadLocal.set(conn);
			} catch (SQLException e) {
				LOG.error("get connection error ", e);
			}
		}
		
		return conn;
	}
	
	/* start transaction */
	public static void startTransaction() {
		
		Connection conn = threadLocal.get();
		if (conn == null) {
			conn = getConnection();
		}
		
		try {
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			LOG.error("start transcation error ", e);
			throw new RuntimeException(e);
		}
	}
	
	/* commit statement */
	public static void commit() {
		
		try {
			Connection conn = threadLocal.get();
			if (conn != null) {
				conn.commit();
			}
		} catch (SQLException e) {
			LOG.error("commit error", e);
			throw new RuntimeException(e);
		}
	}
	
	/* rollback when exception keep ACID */
	public static void rollback() {
		
		try {
			Connection conn = threadLocal.get();
			if (conn != null) {
				conn.rollback();
			}
		} catch (Exception e) {
			LOG.error("rollback error", e);
			throw new RuntimeException(e);
		}
	}
	
	/* close connection */
	public static void close() {
		
		try {
			Connection conn = threadLocal.get();
			if (conn != null) {
				conn.close();
				threadLocal.remove();
			}
		} catch (Exception e) {
			LOG.error("close error", e);
			throw new RuntimeException(e);
		}
	}
	
	/* get data source  */
	public static DataSource getDataSource() {
		return dataSource;
	}

}
