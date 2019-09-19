package config;

import static org.junit.Assert.*;


import org.junit.jupiter.api.Test;

public class TestQuestion {
	@Test
	void testQuestion() {
		Question q=new Question("Je suis le titre",false);
		 assertEquals("Je suis le titre",q.getTitre());
		 assertEquals(false,q.isMultiple());
		 
		 Question q2=new Question("Je suis Q2",true);
		 assertNotEquals("Je suis q2",q2.getTitre());
		 assertEquals("Je suis Q2",q2.getTitre());
		 assertNotEquals(false,q2.isMultiple());
		 assertEquals(true,q2.isMultiple());
	}
	@Test
	void testAddReponse() {
		Reponse r1=new Reponse("+ Au collège des ninjas",true);
		Question q=new Question("Selon la serie diffusée en 1991 sur TF1, où le petit Nicolas doit il travailler et s'appliquer ?",false);
		q.addReponse(r1.getIntitulé());
		assertEquals("Au collège des ninjas",q.getReponses().get(0).getIntitulé());
		Reponse r2=new Reponse("- A l'académie des ninjas",false);
		q.addReponse(r2.getIntitulé());
		assertEquals("A l'académie des ninjas",q.getReponses().get(1).getIntitulé());
		q.addReponse("Suite de la question");
		assertEquals("Selon la serie diffusée en 1991 sur TF1, où le petit Nicolas doit il travailler et s'appliquer ?\nSuite de la question",q.getTitre());
		
	}
	
}
