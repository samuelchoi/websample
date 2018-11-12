package learning.advanced.dbutil;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Servlet implementation class AccountControler
 */
public class AccountControler extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
	AccountService accountService = new AccountService();
	
	private static Logger LOG = LogManager.getLogger(AccountControler.class);
	
    public AccountControler() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		try {
			
			LOG.info("do get transfer money");
			accountService.transfer(1, 2, 55);
		
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
