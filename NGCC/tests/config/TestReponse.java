package config;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class TestReponse {
	@Test
	void testReponse() {
	Reponse r=new Reponse("Je suis la r�ponse",true);
	 assertEquals("Je suis la r�ponse",r.getIntitule());
	 assertEquals(true,r.isJuste());
	 
	}
	
}
