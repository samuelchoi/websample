package learning.advanced.dbutil;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

public class AccountDao {
	
	public void updata(Account account) throws SQLException {
		
		String SQL = "UPDATE account SET name = ?, money = ? WHERE id = ?";
		QueryRunner qr = new QueryRunner();
		Object[] params = new Object[] {account.getName(), account.getMoney(), account.getId()};
		qr.update(ConnectionContext.getInstance().getConnection(), SQL, params);
	}

	public Account find(int id) throws SQLException {
		
		String SQL = "SELECT * FROM account WHERE id = ?";
		QueryRunner qr = new QueryRunner();
		Account rt = (Account) qr.query(ConnectionContext.getInstance().getConnection(), SQL, new BeanHandler(Account.class), new Object[] {id});
		
		return rt;
	}
}
