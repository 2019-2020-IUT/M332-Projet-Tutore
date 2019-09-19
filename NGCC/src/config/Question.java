package config;

import java.util.ArrayList;

public class Question {
	private String titre; // intitul� de la question
	private boolean multiple = false; // boolean pour savoir si c'est une question � choix multiple
	private ArrayList<Reponse> reponses = new ArrayList<Reponse>(); // liste des reponses

	public Question(String t, boolean b) {
		titre = t;
		multiple = b;
	}

	public ArrayList<Reponse> getReponses() {
		return reponses;
	}

	public void setReponses(ArrayList<Reponse> reponses) {
		this.reponses = reponses;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public boolean isMultiple() {
		return multiple;
	}

	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}

//rajout des reponses � la question
//prend en parametre un string qui commence par + ou -
//si le string commence pas par + ou - alors c'est la suite du titre
	public void addReponse(String ligne) {
		switch (ligne.substring(0, 1)) {
		case "+":
			// reponse correcte
			reponses.add(new Reponse(ligne.substring(2, ligne.length()), true));

			break;

		case "-":
			// reponse fausse
			reponses.add(new Reponse(ligne.substring(2, ligne.length()), false));
			break;

		default:
			// si pas + ou - alors c'est la suite � la ligne du titre
			titre = (titre + "\n" + ligne);
		}
	}

// TODO : methode pour verifier si la question a au moins une bonne reponse ET une mauvaise reponse

}
