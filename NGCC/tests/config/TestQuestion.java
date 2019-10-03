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
		Reponse r1=new Reponse("+ Au coll�ge des ninjas",true);
		Question q=new Question("Selon la serie diffus�e en 1991 sur TF1, o� le petit Nicolas doit il travailler et s'appliquer ?",false);
		q.addReponse(r1.getIntitule());
		assertEquals("Au coll�ge des ninjas",q.getReponses().get(0).getIntitule());
		Reponse r2=new Reponse("- A l'acad�mie des ninjas",false);
		q.addReponse(r2.getIntitule());
		assertEquals("A l'acad�mie des ninjas",q.getReponses().get(1).getIntitule());
		q.addReponse("Suite de la question");
		assertEquals("Selon la serie diffus�e en 1991 sur TF1, o� le petit Nicolas doit il travailler et s'appliquer ?\nSuite de la question",q.getTitre());
		
	}
	
}
