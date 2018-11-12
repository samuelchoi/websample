package learning.advanced.dbutil;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AccountService {
	
	public static Logger LOG = LogManager.getLogger(AccountService.class);
	
	public static void transfer(int sourceId, int targetId, float money) throws SQLException {
		
		AccountDao dao = new AccountDao();
		
		Account source = dao.find(sourceId);
		Account target = dao.find(targetId);
		
		// transfer money from source to target
		float sourceMoney = Float.valueOf(source.getMoney());
		float targetMoney = Float.valueOf(target.getMoney());
		float sourceMoneyChanged = sourceMoney - money;
		float targetMoneyChanged = targetMoney + money;
		
		source.setMoney(String.valueOf(sourceMoneyChanged));
		target.setMoney(String.valueOf(targetMoneyChanged));
		
		// updata database 
		dao.updata(source);
		int x = 1/0; 
		dao.updata(target);		
	}
	
	public static void main(String[] arg) {
		

	}

}
