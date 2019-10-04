package config;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestConfig {

	@Test
	void testMakeQuestion() {
		Config c=new Config("test");
		Question q=new Question("Je suis le titre",false);
		q=c.makeQuestion("* Dans M.A.S.K. qui sont les pilotes ou copilote de Rhino (un camion tracteur Kenworth w900)?");
		assertEquals("Dans M.A.S.K. qui sont les pilotes ou copilote de Rhino (un camion tracteur Kenworth w900)?",q.getTitre());
		
		Question q2=new Question("Selon la serie diffus�e en 1991 sur TF1, o� le petit Nicolas doit il travailler et s'appliquer ?",false);
		q2=c.makeQuestion("** Dans M.A.S.K. qui sont les pilotes ou copilote de Rhino (un camion tracteur Kenworth w900)?");
		assertEquals("Dans M.A.S.K. qui sont les pilotes ou copilote de Rhino (un camion tracteur Kenworth w900)?",q2.getTitre());
		
		Question q3=new Question("Quelles sont les bonnes r�ponses",false);
		q3=c.makeQuestion("*<lines=1> Cette jeune fille vient d'emm�nager � Sunnydale avec sa m�re et rencontre son nouvel observateur. Quel est le nom de ce dernier ?");
		assertEquals("Cette jeune fille vient d'emm�nager � Sunnydale avec sa m�re et rencontre son nouvel observateur. Quel est le nom de ce dernier ?",q3.getTitre());
		
	}
}
