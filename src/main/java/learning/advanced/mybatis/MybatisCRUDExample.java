package learning.advanced.mybatis;

import java.io.IOException;
import java.io.Reader;

import learning.advanced.mybatis.entity.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MybatisCRUDExample {
	
	private static Logger logger = LogManager.getLogger();
	
	public static void main(String[] args) throws IOException {
	
		String mybatisConfig = "mybatis-conf.xml";
		Reader reader = Resources.getResourceAsReader(mybatisConfig);
		SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
		
		SqlSession session = sessionFactory.openSession();
		String statement = "mapper.userMapper.getUser";
		User user = (User)session.selectOne(statement, 1);
		logger.info("UserId:{} UserName:{}", user.getId(), user.getName());
	}
}
