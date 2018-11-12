package learning.advanced.dbutil;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Dbutils master feature example
 * 
 * https://www.cnblogs.com/xdp-gacl/p/4007225.html
 */
public class DBUtilsExample {

	public static DataSource ds = null;
	
	private static Logger LOG = LogManager.getLogger(DBUtilsExample.class);
	
	public static void main(String[] args) throws SQLException {
		
		// setup data source 
		ds = setupDataSource();
		
		// add user data 
		addUser();

		// del user data
		deleteUser(1);
		
		// update user data
		update();
		
		// find user data
		find();
		findAll();
		
		// find user data use arrayHandler
		arrayHandler();
		
		// find all user data use arrayListHandler
		arrayListHandler();
		
		// find all user data use mayHandler
		mapHandler();
		
		// find all user data use mayListHandler
		mapListHandler();
		
		// count user data
		scalarHandler();
	}
	
	public static void addUser() throws SQLException {
		
		QueryRunner qr = new QueryRunner(ds);
		
        String sql = "insert into users (name, password, email, birthday) values (?, ?, ?, ?)";
        Object params[] = {"samuelchoi","samuelchoi", "samuelchoi@163.com", "2018-07-22"};
        qr.update(sql, params);
	}
	
	public static void deleteUser(int id) throws SQLException {
		
        QueryRunner qr = new QueryRunner(ds);
        
        String sql = "delete from users where id= ?";
        qr.update(sql, id);	
	}
	
    public static void update() throws SQLException {
    	
        QueryRunner qr = new QueryRunner(ds);
        
        String sql = "update users set name=? where id=?";
        Object params[] = { "ddd", 3 };
        qr.update(sql, params);
    }
    
    public static void find() throws SQLException {
    	
        QueryRunner qr = new QueryRunner(ds);
        String sql = "select * from users where id=?";
        Object params[] = { 2 };
        User user = (User)qr.query(sql, params, new BeanHandler(User.class));
        
        LOG.debug("user name : " + user.getName());
    }

    public static void findAll() throws SQLException {
    	
        QueryRunner qr = new QueryRunner(ds);
        String sql = "select * from users";
        List<User> list = (List<User>) qr.query(sql, new BeanListHandler(User.class));
        
        // show list size
        LOG.debug("list size : " + list.size());
        
        // show user name
        for (User user : list) {
        	LOG.debug("user name {}, email is {}." , user.getName() , user.getEmail());
        }
    }
    
    public static void arrayHandler() throws SQLException{
    	
        QueryRunner qr = new QueryRunner(ds);
        String sql = "select * from users";
        Object[] user = (Object[]) qr.query(sql, new ArrayHandler());
        
        LOG.debug( Arrays.asList(user) );
    }
    
    public static void arrayListHandler() throws SQLException{
        
        QueryRunner qr = new QueryRunner(ds);
        String sql = "select * from users";
        List<Object[]> users = (List<Object[]>) qr.query(sql, new ArrayListHandler());
        
        for (Object[] user : users){
           	LOG.debug( Arrays.asList(user) );
        }
    }
    
    public static void mapHandler() throws SQLException{
        
        QueryRunner qr = new QueryRunner(ds);
        String sql = "select * from users";
        Map<String,Object> users = (Map) qr.query(sql, new MapHandler());
        
        for (Map.Entry<String, Object> user : users.entrySet()) {
            LOG.debug( "[ {} = {} ]" , user.getKey(), user.getValue() );
        }
    }    
    
    public static void mapListHandler() throws SQLException{
        
    	QueryRunner qr = new QueryRunner(ds);
    	String sql = "select * from users";
        List<Map> usersList = (List) qr.query(sql, new MapListHandler());
        
        for ( Map<String,Object> userMap : usersList ) {
            for ( Map.Entry<String, Object> user : userMap.entrySet() ) {
            	LOG.debug( "[ {} = {} ]", user.getKey(), user.getValue());
            }
        }
    }
    
    public static void scalarHandler() throws SQLException{
     
    	QueryRunner qr = new QueryRunner(ds);
        String sql = "select count(*) from users";
        int count = ((Long)qr.query(sql, new ScalarHandler(1))).intValue();
        
        LOG.debug("all row count is {}.", count);
    }
	
	public static DataSource setupDataSource() {
		
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUsername("root");
        ds.setPassword("root");
        ds.setUrl("jdbc:mysql://localhost:3306/jdbc-example");
        return ds;
    }
}
