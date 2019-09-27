package log4j;

import static org.junit.jupiter.api.Assertions.*;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

class TestLog4j {

	 
	 private static Logger logger = LogManager.getLogger();

	  public static void main(String[] args) {
		  
	    logger.debug("msg de debogage");
	    logger.info("msg d'information");
	    logger.warn("msg d'avertissement");
	    logger.error("msg d'erreur");
	    logger.fatal("msg d'erreur fatale");  

	  }

}
