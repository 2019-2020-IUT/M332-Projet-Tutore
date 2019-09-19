package config;

public class QuestionBoite extends Question {
// Type de question differente du normal, c'est une question avec une boite de texte où l'étudiant saisira du texte
	private int nbligne; // nb de lignes dans la boite

	public QuestionBoite(String t, boolean b, int nb) {
		super(t, b);
		nbligne = nb;
	}

	public int getNbligne() {
		return nbligne;
	}

	public void setNbligne(int nbligne) {
		this.nbligne = nbligne;
	}
}