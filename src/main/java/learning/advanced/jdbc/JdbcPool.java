package learning.advanced.jdbc;

import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.LinkedList;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JdbcPool implements DataSource {

	private static Logger LOGGER = LogManager.getLogger(JdbcPool.class);
    private static LinkedList<Connection> listConnections = new LinkedList<Connection>();
    
    static {
    	
        // 在静态代码块中加载db.properties数据库配置文件
        ClassLoader cl = JdbcPool.class.getClassLoader();
        InputStream in = cl.getSystemResourceAsStream("db.properties");
        
        Properties prop = new Properties();
        try {
            prop.load(in);
            String driver = prop.getProperty("driver");
            String url = prop.getProperty("url");
            String username = prop.getProperty("username");
            String password = prop.getProperty("password");
            int jdbcPoolInitSize =Integer.parseInt(prop.getProperty("jdbcPoolInitSize"));
            
            // 加载数据库驱动
            Class.forName(driver);
            for (int i = 0; i < jdbcPoolInitSize; i++) {
                Connection conn = DriverManager.getConnection(url, username, password);
                LOGGER.debug("获取到了链接:" + conn);
                // 将获取到的数据库连接加入到listConnections集合中，listConnections集合此时就是一个存放了数据库连接的连接池
                listConnections.add(conn);
            }
            
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }


    public Connection getConnection() throws SQLException {
        
    	// 如果数据库连接池中的连接对象的个数大于0
        if (listConnections.size()>0) {
            // 从listConnections集合中获取一个数据库连接
            final Connection conn = listConnections.removeFirst();
            LOGGER.debug("listConnections 数据库连接池大小是:" + listConnections.size());
            
            LOGGER.debug("connection: " + conn.getClass());
            LOGGER.debug("interfaces: " + conn.getClass().getInterfaces().length);
            
            // 返回Connection对象的代理对象
            // getInterfaces 取值失败
            return (Connection) Proxy.newProxyInstance(JdbcPool.class.getClassLoader(), 
            		new Class[] { Connection.class }, new InvocationHandler(){
 
                public Object invoke(Object proxy, Method method, Object[] args)
                        throws Throwable {
                    if(!method.getName().equals("close")){
                    	// connection is not close function 
                        return method.invoke(conn, args);
                    }else{
                        // put into pool when connection be closed
                        listConnections.add(conn);
                        LOGGER.debug(conn + "被还给listConnections数据库连接池了");
                        LOGGER.debug("listConnections数据库连接池大小为 " + listConnections.size());
                        return null;
                    }
                }
            });
        }else {
            throw new RuntimeException("对不起，数据库忙");
        }
    }

    public Connection getConnection(String username, String password)
            throws SQLException {
        return null;
    }

	public java.util.logging.Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return null;
	}

	public PrintWriter getLogWriter() throws SQLException {
		return null;
	}

	public int getLoginTimeout() throws SQLException {
		return 0;
	}

	public void setLogWriter(PrintWriter paramPrintWriter) throws SQLException {
		
	}

	public void setLoginTimeout(int paramInt) throws SQLException {
		
	}

	public boolean isWrapperFor(Class<?> paramClass) throws SQLException {
		return false;
	}

	public <T> T unwrap(Class<T> paramClass) throws SQLException {
		return null;
	}

}