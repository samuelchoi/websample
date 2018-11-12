package learning.advanced.dbutil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Servlet Filter implementation class TranscationFilter
 */
public class TranscationFilter implements Filter {

	private static Logger LOG = LogManager.getLogger(TranscationFilter.class);

	
    public void init(FilterConfig fConfig) throws ServletException {}
    
	public void destroy() {}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		Connection conn = null;
		
		try {
			// get connection
			conn = JDBCUtils.getConnection();
			conn.setAutoCommit(false);
			
			// bind connection to context
			ConnectionContext.getInstance().bind(conn);
					
			// pass the request along the filter chain
			chain.doFilter(request, response);
			
			// commit
			conn.commit();
		} catch (Exception e) {
			try {
				// rollback
				conn.rollback();
				LOG.error("transcation rollback when error happen . ", e);
				
				// show error
				HttpServletRequest req = (HttpServletRequest)request; 
				HttpServletResponse res = (HttpServletResponse)response;
				res.sendRedirect(req.getContextPath() + "/error.jsp");
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
			
		} finally {
			// release bind
			ConnectionContext.getInstance().remove(conn);
			JDBCUtils.close();
		}
	}
}
