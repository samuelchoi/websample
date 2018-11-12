package learning.advanced.dbutil;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DbUtilsTranExample {
	
	public static DataSource ds = null;
	public static Connection conn = null;
	public static Logger LOG = LogManager.getLogger(DbUtilsTranExample.class);
	/* 数据源 */
	public static DataSource dataSource = null;
	
	/* 数据连接 */
	public static Connection connection = null;
	
	/* logger */
	public static Logger LOGGER = LogManager.getLogger(DbUtilsTranExample.class);
	
	public static void main(String[] arg) throws SQLException {
		dataSource = setupDataSource();
		transfer("A","B",100);
	}

	public static void transfer(String sourceName, String targetName, float money) throws SQLException{
	    
		try {
	        conn = ds.getConnection();
	        
	        // 开启事务
	        conn.setAutoCommit(false);
	        
			// 在创建QueryRunner对象时，不传递数据源给它，是为了保证这两条SQL在同一个事务中进行，
			// 我们手动获取数据库连接，然后让这两条SQL使用同一个数据库连接执行
	        QueryRunner runner = new QueryRunner();
	        String sql1 = "update account set money=money-100 where name=?";
	        String sql2 = "update account set money=money+100 where name=?";
	        Object[] paramArr1 = {sourceName};
	        Object[] paramArr2 = {targetName};
	        
	        // 第一笔交易
	        runner.update(conn,sql1,paramArr1);
	        // 模拟程序出现异常让事务回滚
	        int x = 1/0;
	        // 第二笔交易
	        runner.update(conn,sql2,paramArr2);
	        
	        // Sql正常执行之后就提交事务
	        conn.commit();
	    } catch (Exception e) {
	        LOGGER.error("dbutils tran exception",e);
	    	
	        if(conn!=null){
	            //出现异常之后就回滚事务
	            conn.rollback();
	        }
	    }finally{
	        //关闭数据库连接
	        conn.close();
	    }
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
