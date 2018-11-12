package learning.advanced.dbutil;

import java.sql.Connection;

public class ConnectionContext {
	
	private static ConnectionContext connectionContext = new ConnectionContext();
	
	private ConnectionContext(){}
	
	public static ConnectionContext getInstance() {
		return connectionContext;
	}

	private ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();
	
	public void bind(Connection conn) {
		threadLocal.set(conn);
	}
	
	public Connection getConnection() {
		return threadLocal.get();
	}
	
	public void remove(Connection conn) {
		threadLocal.remove();
	}
	
}
