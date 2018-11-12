package learning.advanced.log4j;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log4jExample {

	private static final Logger LOGGER = LogManager.getLogger(Log4jExample.class);
	
	public static void main(String[] args) {
		String thing = args.length > 0 ? args[0] : "world";
        LOGGER.info("Hello, {}!", thing);
        LOGGER.debug("Got calculated value only if debug enabled: {}", doSomeCalculation());
        
        // log level ALL < TRACE < DEBUG < INFO < WARN < ERROR < FATAL < OFF
        LOGGER.trace("Trace Message!");
        LOGGER.debug("Debug Message!");
        LOGGER.info("Info Message!");
        LOGGER.warn("Warn Message!");
        LOGGER.error("Error Message!");
        LOGGER.fatal("Fatal Message!");
	}

	private static Object doSomeCalculation() {
	    return null;
	}
}
