package learning.advanced.dbutil;

public class Account {

	private int id;				// id 
	private String Name;		// user name
	private String Money;		// user money

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getMoney() {
		return Money;
	}

	public void setMoney(String money) {
		Money = money;
	}

}
