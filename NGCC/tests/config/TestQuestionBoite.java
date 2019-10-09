package config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.jupiter.api.Test;

public class TestQuestionBoite {
	@Test
	void testQuestionBoite() {
		QuestionBoite q=new QuestionBoite("Quelles sont les bonnes réponses",false,3);
		assertEquals(3,q.getNbligne());
		assertEquals(false,q.isMultiple());
		assertEquals("Quelles sont les bonnes réponses",q.getTitre());
		assertNotEquals("quelles sont les bonnes réponses",q.getTitre());
	}
}
