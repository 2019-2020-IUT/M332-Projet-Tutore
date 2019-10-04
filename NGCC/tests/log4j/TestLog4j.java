package log4j;

import static org.junit.jupiter.api.Assertions.*;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

class TestLog4j {

	 
	 private static Logger logger = LogManager.getFormatterLogger("log4j");

	  public static void main(String[] args) {
		  
	    logger.debug("msg de debogage");
	    logger.info("msg d'information");
	    logger.warn("msg d'avertissement");
	    logger.error("msg d'erreur");
	    logger.fatal("msg d'erreur fatale");  

	  }
	  
	  /* Idee verbosité
	   * 
	   * Logger logger;
	   * 
	   * if (v >= 0 && v <= 2) {
	   * 	logger = LogManager.getLogger(LogManager.getLogger("fatalLogger"));
	   * }
	   * else if (v >= 3 && v <= 4) {
	   * 	logger = LogManager.getLogger(LogManager.getLogger("errorLogger"));
	   * }
	   * else if (v >= 5 && v <= 6) {
	   * 	logger = LogManager.getLogger(LogManager.getLogger("warnLogger"));
	   * }
	   * else if (v >= 7 && v <= 8) {
	   * 	logger = LogManager.getLogger(LogManager.getLogger("infoLogger"));
	   * }
	   * else {
	   * 	logger = LogManager.getLogger(LogManager.getLogger("debugLogger"));
	   * }
	   * 
	   * -> On met quand même logger.warn(...) et il apparaitra ou non selon le logger
	   */

}
