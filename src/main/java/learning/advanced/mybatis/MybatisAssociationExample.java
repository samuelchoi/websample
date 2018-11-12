package learning.advanced.mybatis;

import java.io.IOException;
import java.io.Reader;

import learning.advanced.mybatis.entity.Classes;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MybatisAssociationExample {
	
	private static Logger logger = LogManager.getLogger();
	
	public static void main(String[] args) throws IOException{
		
		String mybatisConfig = "mybatis-conf.xml";
		Reader reader = Resources.getResourceAsReader(mybatisConfig);
		SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
		SqlSession session = null;
		
		try {
			session = sessionFactory.openSession();
			String statement = "org.snow.snippet.mybatis.mapper.classMapper.getClass";
			Classes classes = (Classes)session.selectOne(statement, 1);
			logger.info("Class info:{}", classes);
		} finally {
			if(session != null) { 
				session.close(); 
			}
		}
		
	}
}
