package config;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestConfig {

	@Test
	void testMakeQuestion() {
		Config c = new Config("test");
		Question q = new Question("Je suis le titre", false);
		q = c.makeQuestion(
				"* Dans M.A.S.K. qui sont les pilotes ou copilote de Rhino (un camion tracteur Kenworth w900)?");
		assertEquals("Dans M.A.S.K. qui sont les pilotes ou copilote de Rhino (un camion tracteur Kenworth w900)?",
				q.getTitre());

		Question q2 = new Question(
				"Selon la serie diffusée en 1991 sur TF1, où le petit Nicolas doit il travailler et s'appliquer ?",
				false);
		q2 = c.makeQuestion(
				"** Dans M.A.S.K. qui sont les pilotes ou copilote de Rhino (un camion tracteur Kenworth w900)?");
		assertEquals("Dans M.A.S.K. qui sont les pilotes ou copilote de Rhino (un camion tracteur Kenworth w900)?",
				q2.getTitre());

		Question q3 = new Question("Quelles sont les bonnes réponses", false);
		q3 = c.makeQuestion(
				"*<lines=1> Cette jeune fille vient d'emménager à Sunnydale avec sa mère et rencontre son nouvel observateur. Quel est le nom de ce dernier ?");
		assertEquals(
				"Cette jeune fille vient d'emménager à Sunnydale avec sa mère et rencontre son nouvel observateur. Quel est le nom de ce dernier ?",
				q3.getTitre());

	}

	@Test
	void testClearString() {
		String string = "  \ntest \r testù   ";
		assertEquals("test  testù", Config.clearString(string));

		// TODO: improve tests with a string containing
		// all non desired characters
		/**
		 * String string2 = "\n\r\\u000A\\uFFFF"; assertEquals("",
		 * Config.clearString(string2));
		 * assertTrue(Config.clearString(string2).isEmpty());
		 */
	}
}
