package config;

import static org.junit.Assert.*;


import org.junit.jupiter.api.Test;

public class TestQuestion {
	@Test
	public void testQuestion() {
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
	public void testAddReponse() {
		Reponse r1=new Reponse("Au collège des ninjas",true);
		Question q=new Question("Selon la serie diffusée en 1991 sur TF1, où le petit Nicolas doit il travailler et s'appliquer ?",false);
		q.addReponse("+ "+r1.getIntitule());
		assertEquals("Au collège des ninjas",q.getReponses().get(0).getIntitule());
		Reponse r2=new Reponse("- A l'académie des ninjas",false);
		q.addReponse(r2.getIntitule());
		assertEquals("A l'académie des ninjas",q.getReponses().get(1).getIntitule());
		q.addReponse("Suite de la question");
		assertEquals("Selon la serie diffusée en 1991 sur TF1, où le petit Nicolas doit il travailler et s'appliquer ?\nSuite de la question",q.getTitre());
		
	}
	@Test
	public void testVerifQuestion() {
		Question q=new Question("D'après le générique de 1978, jusqu'où bondit le merveilleux génie ?",false);
		Reponse r1=new Reponse("Jupiter",true);
		Reponse r2=new Reponse("La Terre",false);
		Reponse r3=new Reponse("Le fond de l'univers",false);
		Reponse r4=new Reponse("Neugebauer",false);
		q.addReponse("+ "+r1.getIntitule());
		q.addReponse("- "+r2.getIntitule());
		q.addReponse("- "+r3.getIntitule());
		q.addReponse("- "+r4.getIntitule());
		assertTrue(q.verifQuestion());
		Question q2=new Question("D'après le générique de 1978, jusqu'où bondit le merveilleux génie ?",false);
		q2.addReponse("- "+r1.getIntitule());
		q2.addReponse("- "+r2.getIntitule());
		q2.addReponse("- "+r3.getIntitule());
		q2.addReponse("- "+r4.getIntitule());
		assertFalse(q2.verifQuestion());
				
		
	}
	
	
}
