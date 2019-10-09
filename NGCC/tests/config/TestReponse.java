package config;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class TestReponse {
	@Test
	void testReponse() {
	Reponse r=new Reponse("Je suis la réponse",true);
	 assertEquals("Je suis la réponse",r.getIntitule());
	 assertEquals(true,r.isJuste());
	 
	}
	
}
